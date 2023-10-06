package org.quanta.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.quanta.core.beans.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/6
 */
@Api(tags = "用户模块",value = "用户模块")
@RequestMapping("/user")
@RestController
public interface IUserController {
    /**
     * 登录并获取token
     */
    @ApiOperation(value = "登录")
    @PostMapping("/login")
    Response<?> login(@ApiParam(name = "用户名") String username,
                      @ApiParam(name = "密码") String password);

    /**
     * 注册
     */
    @ApiOperation(value = "注册")
    @PutMapping("/register")
    Response<?> register(@ApiParam("用户名") String username,
                         @ApiParam(name = "密码") String password);
}
