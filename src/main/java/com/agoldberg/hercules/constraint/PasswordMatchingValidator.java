package com.agoldberg.hercules.constraint;

import com.agoldberg.hercules.dto.RegistrationDTO;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchingValidator implements ConstraintValidator<PasswordMatching, RegistrationDTO> {
   @Override
   public void initialize(PasswordMatching constraint) {
      /**
       * No initialization needed
       */
   }

   @Override
   public boolean isValid(RegistrationDTO obj, ConstraintValidatorContext context) {
      return obj.getPassword().equals(obj.getConfirmPassword());
   }
}
