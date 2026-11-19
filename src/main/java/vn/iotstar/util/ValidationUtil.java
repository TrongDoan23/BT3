package vn.iotstar.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.Validation;

/** Validate form bean bằng Hibernate Validator; trả về Map(tên field -> thông báo lỗi). */
public class ValidationUtil {
    // ParameterMessageInterpolator: không cần thư viện EL
    private static final ValidatorFactory FACTORY = Validation.byProvider(HibernateValidator.class)
            .configure()
            .messageInterpolator(new ParameterMessageInterpolator())
            .buildValidatorFactory();
    private static final Validator VALIDATOR = FACTORY.getValidator();

    private ValidationUtil() {}

    public static <T> Map<String, String> validate(T form) {
        Map<String, String> errors = new LinkedHashMap<>();
        Set<ConstraintViolation<T>> violations = VALIDATOR.validate(form);
        for (ConstraintViolation<T> v : violations) {
            errors.putIfAbsent(v.getPropertyPath().toString(), v.getMessage());
        }
        return errors;
    }
}
