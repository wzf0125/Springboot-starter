package org.quanta.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.quanta.api.dto.AuthDTO;
import org.quanta.core.beans.Response;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/6
 */
@Api(tags = "用户模块", value = "用户模块")
@RequestMapping("/user")
@RestController
public interface IUserController {
    /**
     * 登录并获取token
     */
    @ApiOperation(value = "登录")
    @PostMapping("/login")
    Response<?> login(@RequestBody AuthDTO param);

    /**
     * 注册
     */
    @ApiOperation(value = "注册")
    @PutMapping("/register")
    Response<?> register(@RequestBody AuthDTO param);
}
