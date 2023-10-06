package org.quanta.base.exception;

/**
 * Description: 权限异常
 * Author: wzf
 * Date: 2023/10/3
 */
public class PermissionException extends RuntimeException{
    public PermissionException() {
        super();
    }

    public PermissionException(String message) {
        super(message);
    }
}
