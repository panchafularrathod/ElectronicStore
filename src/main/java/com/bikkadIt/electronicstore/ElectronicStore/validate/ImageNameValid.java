package com.bikkadIt.electronicstore.ElectronicStore.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidetor.class)
public @interface  ImageNameValid {


        //error massege
        String message()default "Invalid Image name...";

        //represent group of constraints
        Class<?>[]groups()default{};

        //additional info about annotaion
        Class<?extends Payload>[] payload() default {};
    }


