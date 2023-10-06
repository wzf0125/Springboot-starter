package org.quanta.core.annotations;

import org.quanta.core.constants.Role;

import java.lang.annotation.*;

/**
 * Description: 权限拦截注解
 * Author: wzf
 * Date: 2023/10/3
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Permission {
    Role[] value();
}
