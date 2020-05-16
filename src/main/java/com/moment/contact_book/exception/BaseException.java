package com.moment.contact_book.exception;

/**
 * @Description: 异常的基础类
 * @Author: Moment
 * @Date: 2020/5/16 9:59
 */
public class BaseException extends RuntimeException {
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }

    public BaseException(String message) {
        super(message);
        this.message = message;
    }

    public String getExceptionPath() {
        StackTraceElement[] stackTraces = getStackTrace();
        for (StackTraceElement stackTrace : stackTraces) {
            String exClass = stackTrace.getClassName();
            exClass = exClass.substring(exClass.lastIndexOf(".")+1);
            String exMethod = stackTrace.getMethodName();
            return exClass + "." + exMethod;
        }
        return "";
    }
}
