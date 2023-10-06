package org.quanta.demo.controller;

import lombok.RequiredArgsConstructor;
import org.quanta.api.controller.IUserController;
import org.quanta.controller.BaseController;
import org.quanta.core.beans.Response;
import org.quanta.demo.service.IUserService;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/6
 */
@RestController
@RequiredArgsConstructor
public class UserController extends BaseController implements IUserController {
    private final IUserService userService;

    /**
     * 登录并获取token
     */
    @Override
    public Response<?> login(String username, String password) {
        return Response.success(userService.login(username, password));
    }

    @Override
    public Response<?> register(String username, String password) {
        userService.register(username, password);
        return Response.success();
    }
}
