package com.bikkadIt.electronicstore.ElectronicStore.sequrity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Component
public class JWTHelper {


        public static final long JWT_TOKEN_VALIDITY = 5*60*60;
        @Value("${jwt.secret}")
        private String secret ;

        // retrive username from jwt token

        public String getUsernameFromToken(String token) {

            return getClaimFromToken(token, Claims::getSubject);
        }

        // retrive expiration date from jwt token

        public Date getExpirationDateFromToken(String token) {


            return getClaimFromToken(token,Claims::getExpiration);

        }

        public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {

            final Claims claims = getAllClaimsFromToken(token);
            return claimsResolver.apply(claims);
        }

        // for retrie any information from tokenwe will need the secret key

        private Claims getAllClaimsFromToken(String token) {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }

        private Boolean isTokenExpired(String token) {

            final Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        }

        // generate token for user

        public String generateToken(UserDetails usrDetails) {

            Map<String, Object> claims = new HashMap<>();
            return docGenerateToken(claims, usrDetails.getUsername());
        }

        /* while creating the token
         * 1. Define claims of the token, like issuer, Expiration , Subjectand ID
         * 2. sign the JWT using the HSS12 algorithem and secrret key
         * 3. According to JWs Compact serialization (Http://tools.ietf.org/html/draft-ietf-jose
         * 4. compaction of the JWT to a URL safe string
         *
         */

        private String docGenerateToken(Map<String, Object> claims , String subject) {

            return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY*100*60*60))
                    .signWith(SignatureAlgorithm.HS512, secret).compact();
        }

        // valid token

        public Boolean validateToken(String token, UserDetails userDetails) {

            final String username = getUsernameFromToken(token);
            return (username.equals(userDetails.getUsername())&& ! isTokenExpired(token));
        }
    }


