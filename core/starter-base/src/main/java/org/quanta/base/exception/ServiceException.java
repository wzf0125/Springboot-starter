package org.quanta.base.exception;

/**
 * Description: 自定义业务异常
 * Author: wzf
 * Date: 2023/10/3
 */
public class ServiceException extends RuntimeException {

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }
}
