package com.xgstudio.springbootdemo.validator;

import com.xgstudio.springbootdemo.entity.Message;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 消息校验注解
 * @author chenxsa
 */
@Documented
@Target({ ElementType.FIELD,ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy={ MessageValidValidator.class})
public @interface MessageValid {
    String message() default "{com.xgstudio.springbootdemo.MessageValid.defaultMessage}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}