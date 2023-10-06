package org.quanta.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quanta.core.annotations.Permission;
import org.quanta.core.beans.Response;
import org.quanta.core.constants.Role;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/6
 */
@Api( tags = "商品模块",value = "商品模块")
@Permission({Role.ADMIN, Role.USER})
@RequestMapping("/goods")
public interface IGoodsController {
    @GetMapping("/list")
    @ApiOperation(value = "获取商品列表")
    Response<?> getGoodsList();

    @GetMapping("/{id}")
    @ApiOperation(value = "获取商品详情")
    Response<?> getGoodsById(@PathVariable Long id);
}
