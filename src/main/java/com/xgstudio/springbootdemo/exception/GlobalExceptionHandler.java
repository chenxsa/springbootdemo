package com.xgstudio.springbootdemo.exception;

import com.xgstudio.springbootdemo.util.RestResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.expression.Lists;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 全局异常捕获
 *  @author  chenxsa.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 系统异常处理，比如：404,500
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<AppError> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {

        logger.error(e.getMessage(), e);

        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            return new ResponseEntity<AppError>(RestResultGenerator.genError(e), HttpStatus.NOT_FOUND);
        }
        else if (e instanceof javax.validation.ConstraintViolationException) {

            // Build message
            StringBuilder builder = new StringBuilder();
            for (ConstraintViolation<?> error : ((javax.validation.ConstraintViolationException) e).getConstraintViolations()) {
                builder.append(error.getPropertyPath() + " : " + error.getMessage() + System.getProperty("line.separator"));
            }
            return new ResponseEntity<AppError>(RestResultGenerator.genError(builder.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else if (e instanceof MethodArgumentNotValidException) {

            // Build message
            StringBuilder builder = new StringBuilder();
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;
            BindingResult result = methodArgumentNotValidException.getBindingResult();
            final List<FieldError> fieldErrors = result.getFieldErrors(); 
            HashMap<String,List<String>> errors=new HashMap<>();
            for (FieldError error : fieldErrors) {
                if ( errors.containsKey(error.getField())){
                    errors.get(error.getField()).add( error.getDefaultMessage());
                }else
                {
                    List<String> details=new ArrayList<>();
                    details.add(error.getDefaultMessage());
                    errors.put(error.getField(),details);
                }
            }
            return new ResponseEntity<AppError>(RestResultGenerator.genError("validate failed",errors), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        else {
            return new ResponseEntity<AppError>(RestResultGenerator.genError(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}