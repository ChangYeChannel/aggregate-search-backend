package com.liujian.springbootinit.utils;

import com.liujian.springbootinit.common.ErrorCode;
import com.liujian.springbootinit.exception.BusinessException;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 参数正确性校验工具类
 * 利用静态方法 validator() 传入需要校验的对象 {对象的校验规则在实体类上已经标注}
 * 参数正确则返回true
 * 参数错误则在控制台打印错误信息，并抛出自定义参数错误异常
  */
public class ValidationUtils {
    private static Validator validator = Validation.byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory().getValidator();
    public static <T> boolean validator(T entity) {
        Set<ConstraintViolation<T>> validate = validator.validate(entity);
        if (validate.size() > 0) {
            System.out.println(validate.iterator().next().getMessage() + "参数校验失败！");
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return true;
    }
}
