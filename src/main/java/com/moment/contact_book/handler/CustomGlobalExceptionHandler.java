package com.moment.contact_book.handler;

import com.alibaba.fastjson.JSON;
import com.moment.contact_book.exception.ControllerException;
import com.moment.contact_book.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

/**
 * @Description: 全局异常处理
 * @Author: Moment
 * @Date: 2020/5/15 23:47
 */
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> ConstraintViolationExceptionHandle(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());

        Map<String, String> error = new LinkedHashMap<>();
        for (ConstraintViolation<?> violation : violations) {
            error.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        body.put("errors", error);
        return new ResponseEntity<>(JSON.toJSONString(body), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<String> ServiceExceptionHandle(ServiceException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());

        Map<String, String> error = new LinkedHashMap<>();
        error.put(e.getExceptionPath(), e.getMessage());

        body.put("errors", error);
        return new ResponseEntity<>(JSON.toJSONString(body), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ControllerException.class)
    public ResponseEntity<String> ControllerExceptionHandle(ControllerException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", e.getStatus());

        Map<String, String> error = new LinkedHashMap<>();
        error.put(e.getExceptionPath(), e.getMessage());

        body.put("errors", error);
        return new ResponseEntity<>(JSON.toJSONString(body), HttpStatus.BAD_REQUEST);
    }


}
