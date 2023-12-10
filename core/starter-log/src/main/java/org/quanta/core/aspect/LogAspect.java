package org.quanta.core.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.quanta.base.constants.AuthConstants;
import org.quanta.core.annotations.Log;
import org.quanta.core.beans.LogProperties;
import org.quanta.core.constants.LogLevel;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.UUID;

/**
 * Description: 日志切面
 * Author: wzf
 * Date: 2023/10/3
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {
    private final LogProperties logProperties;
    // 请求日志模板
    private static final String BEFORE_LOG_TEMPLATE = "REQUEST_LOG: LogId: {} | Request URL: {} | Http Method: {} | URL: {} | IP: {} | Class Method: {} | Args: {} | Token: {} | Msg: {}";

    // 响应日志模板日志模板
    private static final String AFTER_LOG_TEMPLATE = "RESPONSE_LOG: Token: {} | Uid: {} | Result: {} ";

    /**
     * 对log注解修饰的方法切入
     */
    @Pointcut("@within(org.quanta.core.annotations.Log)")
    public void cutPoint() {
    }

    @Before("cutPoint()")
    public void doBefore(JoinPoint joinPoint) {
        LogLevel logLevel = getLogLevel(joinPoint);
        if (!(LogLevel.ALL.equals(logLevel) || LogLevel.REQUEST.equals(logLevel))) {
            return;
        }
        String logId = String.valueOf(UUID.randomUUID());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            request.setAttribute("requestId", logId);
            String args = Arrays.toString(joinPoint.getArgs());
            request.setAttribute("args", args);
            log.info(BEFORE_LOG_TEMPLATE,
                    logId,
                    request.getRequestURL().toString(),
                    request.getMethod(),
                    request.getRemoteAddr(),
                    request.getHeader("X-Real-IP"),
                    joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(),
                    args,
                    request.getHeader("Token"),
                    "");
        } else {
            log.info(BEFORE_LOG_TEMPLATE, logId, "", "", "", "", "", "", "", "Empty Request!");
        }
    }

    @AfterReturning(returning = "result", pointcut = "cutPoint()")
    public void doAfter(JoinPoint joinPoint, Object result) {
        LogLevel logLevel = getLogLevel(joinPoint);
        if (!(LogLevel.ALL.equals(logLevel) || LogLevel.RESPONSE.equals(logLevel))) {
            return;
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        long uid = 0L;
        String token = null;
        if (attributes != null) {
            token = attributes.getRequest().getHeader(AuthConstants.TOKEN_KEY);
            try {
                uid = (long) attributes.getRequest().getAttribute("uid");
            } catch (Exception ignored) {
            }
        }
        log.info(AFTER_LOG_TEMPLATE, token, uid, result);
    }

    /**
     * 获取日志级别
     * 优先注解参数，否则使用全局配置默认ALL
     */
    private LogLevel getLogLevel(JoinPoint joinPoint) {
        Log logAnnotation = joinPoint.getClass().getAnnotation(Log.class);
        if (logAnnotation == null || LogLevel.GLOBAL.equals(logAnnotation.value())) {
            return logProperties.getLogLevel();
        }
        return logAnnotation.value();
    }
}
