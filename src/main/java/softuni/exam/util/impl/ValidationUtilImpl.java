package softuni.exam.util.impl;

import org.springframework.beans.factory.annotation.Autowired;
import softuni.exam.util.ValidationUtil;

import javax.validation.Validator;

public class ValidationUtilImpl implements ValidationUtil {

    @Autowired
    private final Validator validator;

    public ValidationUtilImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <E> boolean isValid(E entity) {
        return validator.validate(entity).isEmpty();
    }
}
