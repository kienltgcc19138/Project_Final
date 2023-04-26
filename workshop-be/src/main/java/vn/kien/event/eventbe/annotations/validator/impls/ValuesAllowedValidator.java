package vn.kien.event.eventbe.annotations.validator.impls;


import vn.kien.event.eventbe.annotations.validator.ValuesAllowed;
import vn.kien.event.eventbe.utils.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;


public class ValuesAllowedValidator implements ConstraintValidator<ValuesAllowed, String> {

    private List<String> expectedValues;
    private String returnMessage;

    @Override
    public void initialize(ValuesAllowed requiredIfChecked) {
        expectedValues = Arrays.asList(requiredIfChecked.values());
        returnMessage = requiredIfChecked.message().concat(" " + expectedValues);
    }

    @Override
    public boolean isValid(String testValue, ConstraintValidatorContext context) {
        if (StringUtils.isBlankOrNull(testValue)) {
            return true;
        }
        boolean valid = expectedValues.contains(testValue);

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(returnMessage)
                    .addConstraintViolation();
        }
        return valid;
    }
}
