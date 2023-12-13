package org.quanta.core.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quanta.base.exception.PermissionException;
import org.quanta.base.exception.ServiceException;
import org.quanta.core.beans.Response;
import org.quanta.base.config.SystemConfig;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Description: 全局异常处理
 * Author: wzf
 * Date: 2023/10/5
 */
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final SystemConfig systemConfig;

    @ExceptionHandler(Exception.class)
    public @ResponseBody
    Response<Object> errorResult(Exception e) {
        log.error(e.getMessage());
        // 非debug模式的话只返回服务器错误提示 不返回具体错误内容
        if (systemConfig.getDebug()) {
            return Response.serverError();
        }
        // debug模式控制台打印错误信息，响应内容提供错误信息
        e.printStackTrace();
        return Response.serverError(String.format("操作失败，请重试[%s]", e.getMessage()));
    }

    // 数据校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody
    Response<Object> validationErrorResult(MethodArgumentNotValidException e) {
        return buildParamErrorResp(e);
    }

    @ExceptionHandler(BindException.class)
    public @ResponseBody
    Response<Object> handleBindException(BindException ex) {
        return buildParamErrorResp(ex);
    }

    /**
     * 参数异常响应信息
     */
    private Response<Object> buildParamErrorResp(BindException ex) {
        BindingResult result = ex.getBindingResult();
        StringBuilder sb = new StringBuilder();
        if (result.getFieldErrorCount() > 0) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
        }
        return Response.paramError(String.format("参数校验错误[%s]", sb));
    }


    // 自定义异常处理类
    @ExceptionHandler(ServiceException.class)
    public @ResponseBody
    Response<Object> apiErrorResult(ServiceException e) {
        return Response.fail(e.getMessage());
    }


    // 权限异常校验类
    @ExceptionHandler(PermissionException.class)
    public @ResponseBody
    Response<Object> permissionError(PermissionException e) {
        return Response.permissionDenied(e.getMessage());
    }
}
