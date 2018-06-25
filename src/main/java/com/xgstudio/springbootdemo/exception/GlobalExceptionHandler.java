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

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
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
            for (FieldError error : fieldErrors) {
                builder.append(error.getField() +" : "+ error.getDefaultMessage() + System.getProperty("line.separator"));
            }
            return new ResponseEntity<AppError>(RestResultGenerator.genError(builder.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
//        else  if (e instanceof AccessDeniedException){
//            return new ResponseEntity<>(RestResultGenerator.genNoAuth(), HttpStatus.FORBIDDEN);
//        }
        else {
            return new ResponseEntity<AppError>(RestResultGenerator.genError(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}