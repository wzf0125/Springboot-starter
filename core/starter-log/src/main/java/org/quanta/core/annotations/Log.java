package org.quanta.core.annotations;

import org.quanta.core.constants.LogLevel;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Description: 注释切面注解
 * Author: wzf
 * Date: 2023/10/3
 */
@Inherited // 让子类也被切入
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    // 默认使用全局配置 ALL
    LogLevel value() default LogLevel.GLOBAL;
}
