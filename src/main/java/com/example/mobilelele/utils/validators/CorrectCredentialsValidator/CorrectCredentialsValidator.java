package com.example.mobilelele.utils.validators.CorrectCredentialsValidator;

import com.example.mobilelele.repositories.UserRepository;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class CorrectCredentialsValidator implements ConstraintValidator<CredentialsValidator, Object> {
    private String firstField;
    private String secondField;
    private String message;
    private UserRepository userRepository;

    @Override
    public void initialize(CredentialsValidator constraintAnnotation) {
        this.firstField = constraintAnnotation.firstField();
        this.secondField = constraintAnnotation.secondField();
        this.message = constraintAnnotation.message();
    }

    public CorrectCredentialsValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);

        String firstValue = Objects.requireNonNull(wrapper.getPropertyValue(this.firstField)).toString();
        String secondValue = Objects.requireNonNull(wrapper.getPropertyValue(this.secondField)).toString();

        boolean isValid = this.userRepository.findByUsernameAndPassword(firstValue, secondValue).isPresent();

        if (!isValid) {
            appendFieldViolations(context, this.firstField);
            appendFieldViolations(context, this.secondField);
        }

        return isValid;
    }

    private void appendFieldViolations(ConstraintValidatorContext context, String field) {
        context
                .buildConstraintViolationWithTemplate(this.message)
                .addPropertyNode(field)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
    }
}
