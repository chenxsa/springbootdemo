package com.xgstudio.springbootdemo.validator;

import com.xgstudio.springbootdemo.entity.Message;
import com.xgstudio.springbootdemo.service.ILocaleMessageSourceService;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 消息自定义校验
 * @author chenxsa
 */
public class MessageValidValidator implements ConstraintValidator<MessageValid, Object> {

//    private ILocaleMessageSourceService localeMessageSourceService;
//
//    @Resource
//    public void setLocaleMessageSourceService(ILocaleMessageSourceService messageSourceService) {
//        this.localeMessageSourceService = messageSourceService;
//    }
    @Override
    public void initialize(MessageValid alarmActionValid) {
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String message="";
        BeanWrapper beanWrapper = new BeanWrapperImpl( o);

        String context = (String)beanWrapper.getPropertyValue("context");
        if (!StringUtils.isEmpty(context)){
            //todo: 进行业务逻辑判断
            if ("demo".equals(context)) {
                message = "内容不能demo";
               // message = localeMessageSourceService.getMessage("MESSAGE.ERROR.E00001");
            }
        }else {
            message = "内容不能为空";
            // message=localeMessageSourceService.getMessage("COMM.ERROR.E00002");
        }
        if (message.length()>0){
            //如果存在异常，设置异常消息到对应的字段
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("context")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
