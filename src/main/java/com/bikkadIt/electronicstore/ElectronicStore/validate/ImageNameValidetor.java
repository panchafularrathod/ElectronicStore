package com.bikkadIt.electronicstore.ElectronicStore.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageNameValidetor implements ConstraintValidator<ImageNameValid,String> {

    private Logger logger= LoggerFactory.getLogger(ImageNameValid.class);
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        logger.info("message from invalid :{}",value);

        return value == null || value.isBlank();
    }
}
