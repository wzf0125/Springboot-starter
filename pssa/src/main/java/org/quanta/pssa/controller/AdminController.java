package org.quanta.pssa.controller;

import io.swagger.annotations.Api;
import org.quanta.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/17
 */
@Api("顶级管理员模块")
@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController {
}
