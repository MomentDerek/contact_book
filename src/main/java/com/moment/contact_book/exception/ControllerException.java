package com.moment.contact_book.exception;

import org.springframework.http.HttpStatus;

/**
 * @Description: Controller层的异常
 * @Author: Moment
 * @Date: 2020/5/16 9:24
 */
public class ControllerException extends BaseException {

    private final int status;

    public int getStatus() {
        return status;
    }

    public ControllerException(String message) {
        super(message);
        status = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public ControllerException(String message, int status) {
        super(message);
        this.status = status;
    }
}
