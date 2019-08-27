package com.miaoshaproject.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
/**
* @Description:    校验处理
* @Author:         taoxudong
* @CreateDate:     2019/8/27 10:46
* @Version:        1.0
*/
@Component
public class ValidatorImpl implements InitializingBean {

    private Validator validator;

    /**
     * 实现校验方法并返回校验结果
     * @param bean
     * @return
     */
    public ValidationResult validate(Object bean) {

        ValidationResult result = new ValidationResult();

        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);

        if (constraintViolationSet.size() > 0) {
            //有错误
            result.setHasErrors(true);

            constraintViolationSet.forEach(constraintViolation -> {
                String errMsg = constraintViolation.getMessage();
                String propertyName = constraintViolation.getPropertyPath().toString();
                result.getErrorMsgMap().put(propertyName, errMsg);
            });
        }
        return result;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        //将hibernate validator通过工厂的初始化方式使其实例化
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}
