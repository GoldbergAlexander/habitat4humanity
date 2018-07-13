package com.agoldberg.hercules.constraint;

import com.agoldberg.hercules.dto.MatchingPassword;
import com.agoldberg.hercules.dto.RegistrationDTO;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchingValidator implements ConstraintValidator<PasswordMatching, MatchingPassword> {
   @Override
   public void initialize(PasswordMatching constraint) {
      /**
       * No initialization needed
       */
   }

   @Override
   public boolean isValid(MatchingPassword obj, ConstraintValidatorContext context) {
      return obj.getPassword().equals(obj.getConfirmPassword());
   }
}
