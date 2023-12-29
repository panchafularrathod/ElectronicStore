package com.bikkadIt.electronicstore.ElectronicStore.sequrity;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JWTHelper jwtHelper;
    @Autowired
    private UserDetailsService userDetailsService;

    private Logger logger= LoggerFactory.getLogger(OncePerRequestFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //Authorization
        String requestHeader = request.getHeader("Authorization");
        //Bearer 2152345235dfrsfgadfsdf
        logger.info("Header : {}", requestHeader);
        String username=null;
        String token=null;
        if (requestHeader!=null && requestHeader.startsWith("Bearer")){

            requestHeader.substring(7);
            try {
                 username = this.jwtHelper.getUsernameFromToken(token);
            }catch (IllegalArgumentException e){
                logger.info("illegal argument while fetch the username");
                e.printStackTrace();
            }catch (ExpiredJwtException e){
                logger.info("given token is expired");
                e.printStackTrace();
            }catch (MalformedJwtException e){
                logger.info("some change has don in token, invalid token");
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }

    }else {
            logger.info("invalid Header Value");
        }
        if (username !=null && SecurityContextHolder.getContext().getAuthentication()==null){

            //fetch user detail from from username
            UserDetails userDetails1 = this.userDetailsService.loadUserByUsername(username);
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails1);
            if (validateToken){
                // set authentication
                UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken
                        (userDetails1,null,userDetails1.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else {

                logger.info("validation fail....");
            }

        }
        filterChain.doFilter(request,response);

    }
}
