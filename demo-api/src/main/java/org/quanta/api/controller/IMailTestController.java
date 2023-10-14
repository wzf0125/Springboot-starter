package org.quanta.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.quanta.core.annotations.Permission;
import org.quanta.core.beans.Response;
import org.quanta.core.constants.Role;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/14
 */
@Api(tags = "邮件功能测试", value = "邮件功能测试")
@Permission({Role.ADMIN, Role.USER})
@RequestMapping("/test/mail")
public interface IMailTestController {

    @PostMapping
    @ApiOperation(value = "邮件发送")
    Response<?> mail();

    @PostMapping("/attachment")
    @ApiOperation(value = "邮件发送(带附件)")
    @ApiImplicitParam(name = "fileList", value = "上传文件", dataType = "java.io.File")
    Response<?> mailWithAttachment(@RequestPart("fileList") List<MultipartFile> fileList);
}
