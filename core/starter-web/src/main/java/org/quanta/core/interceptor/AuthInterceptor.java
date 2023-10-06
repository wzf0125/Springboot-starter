package org.quanta.core.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quanta.base.constants.AuthConstants;
import org.quanta.base.utils.JacksonUtils;
import org.quanta.core.annotations.Permission;
import org.quanta.core.beans.Response;
import org.quanta.core.constants.Role;
import org.quanta.core.utils.TokenUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description: 认证拦截器
 * Author: wzf
 * Date: 2023/10/5
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private final TokenUtils tokenUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断是否是OPTIONS 是则放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            log.info("OPTIONS: ");
            return true;
        }
        // 设置响应头为json
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        // 设置响应头编码
        response.setCharacterEncoding("UTF-8");
        // 无token参数拒绝，放通的接口除外
        if (request.getHeader(AuthConstants.TOKEN_HEADER) == null || request.getHeader(AuthConstants.TOKEN_HEADER).isEmpty()) {
            log.debug("");
            response.getWriter().write(JacksonUtils.toJsonStr(Response.unauthorized("缺少token参数")));
            return false;
        }
        // 提取请求头token
        String token = request.getHeader(AuthConstants.TOKEN_HEADER);
        if (token == null) {
            response.getWriter().write(JacksonUtils.toJsonStr(Response.unauthorized()));
        }
        int roleCode;
        try {
            roleCode = tokenUtils.getTokenRole(token);
        } catch (Exception e) {
            response.getWriter().write(JacksonUtils.toJsonStr(Response.unauthorized(e.getMessage())));
            return false;
        }
        Role role = Role.codeOf(roleCode);
        if (this.hasPermission(handler, role)) {
            long uid = tokenUtils.getTokenUid(token);
            // 刷新token
            tokenUtils.refreshToken(token, uid, role);
            // 权限塞request里传给controller
            request.setAttribute("uid", uid);
            request.setAttribute("role", role);
            return true;
        }
        // 没通过权限验证 返回权限不足
        response.getWriter().write(JacksonUtils.toJsonStr(Response.permissionDenied()));
        return false;
    }

    /**
     * 权限验证方法
     */
    private boolean hasPermission(Object handler, Role role) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Permission permission = handlerMethod.getMethod().getAnnotation(Permission.class);
            if (permission == null) {
                permission = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Permission.class);
            }
            if (permission != null) {
                for (Role p : permission.value()) {
                    if (role.equals(p)) {
                        return true;
                    }
                }
            } else {
                return true;
            }
        }
        return false;
    }
}
