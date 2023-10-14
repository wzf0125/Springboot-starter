package org.quanta.base.exception;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/14
 */
public class RetryException extends RuntimeException{
    public RetryException() {
    }

    public RetryException(String message) {
        super(message);
    }
}
