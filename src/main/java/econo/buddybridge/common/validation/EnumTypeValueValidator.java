package econo.buddybridge.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class EnumTypeValueValidator implements ConstraintValidator<EnumTypeValue, String> {

    private ValueEnum<?>[] enumValues;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return Arrays.stream(enumValues)
                .map(ValueEnum::getValue)
                .anyMatch(enumValue -> enumValue.equals(value));
    }

    @Override
    public void initialize(EnumTypeValue constraintAnnotation) {
        Class<? extends ValueEnum<?>> enumClass = constraintAnnotation.enumClass();
        enumValues = enumClass.getEnumConstants();
    }
}
