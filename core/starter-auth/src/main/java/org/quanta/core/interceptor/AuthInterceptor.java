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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * Description: 认证拦截器
 * Author: wzf
 * Date: 2023/10/5
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements IAuthInterceptor {
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
        if (request.getHeader(AuthConstants.TOKEN_KEY) == null || request.getHeader(AuthConstants.TOKEN_KEY).isEmpty()) {
            log.debug("");
            response.getWriter().write(JacksonUtils.toJsonStr(Response.unauthorized("缺少token参数")));
            return false;
        }

        // 提取请求头token
        String token = request.getHeader(AuthConstants.TOKEN_KEY).substring(7);

        HashMap<String, Object> userInfo = tokenUtils.retrieveToken(token);
        if (userInfo == null || userInfo.get("uid") == null || userInfo.get("role") == null) {
            // 登陆过期
            response.getWriter().write(JacksonUtils.toJsonStr(Response.loginExpire("登录过期")));
            return false;
        }
        int uid = (int) userInfo.get("uid");
        int role = (int) userInfo.get("role");
        Role roleEnum = Role.codeOf(role);
        if (!hasPermission(handler, roleEnum)) {
            // 没通过权限验证 返回权限不足
            response.getWriter().write(JacksonUtils.toJsonStr(Response.permissionDenied()));
            return false;
        }

        // 刷新token
        tokenUtils.refreshToken(token, uid, roleEnum);
        // 权限塞request里传给controller
        request.setAttribute("uid", uid);
        request.setAttribute("role", role);
        return true;
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
