package org.quanta.demo.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.quanta.api.controller.IMailTestController;
import org.quanta.base.utils.FileNameUtils;
import org.quanta.controller.BaseController;
import org.quanta.core.Modle.Mail;
import org.quanta.core.beans.Response;
import org.quanta.core.utils.MailUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/14
 */
@RestController
@RequiredArgsConstructor
public class MailTestController extends BaseController implements IMailTestController {
    private final MailUtils mailUtils;

    @Override
    public Response<?> mail() {
        boolean success = mailUtils.sendMail(Mail.builder()
                .to("974500760@qq.com")
                .cc("wzf0125@gmail.com")
                .subject("邮件测试")
                .body("这是一封测试邮件，测试邮件功能是否正常【" + DateUtil.now() + "】")
                .build());
        if (!success) {
            return Response.fail("发送邮件失败");
        }
        return Response.success();
    }

    @Override
    public Response<?> mailWithAttachment(List<MultipartFile> fileList) {
        // 处理文件
        List<File> attachmentList = fileList.stream().map(
                file -> {
                    String filename = FileNameUtils.getFileName(file.getOriginalFilename(), false);
                    String suffix = FileNameUtils.getFileNameSuffix(file.getOriginalFilename());
                    File tempFile = FileUtil.createTempFile(filename, suffix, true);
                    try {
                        file.transferTo(tempFile);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return tempFile;
                }
        ).collect(Collectors.toList());
        boolean success = mailUtils.sendMail(Mail.builder()
                .to("974500760@qq.com")
                .cc("wzf0125@gmail.com")
                .subject("邮件测试")
                .body("这是一封测试邮件，测试邮件功能是否正常【" + DateUtil.now() + "】")
                .attachment(attachmentList)
                .attachmentUrl(List.of("https://974500760-1303995467.cos.ap-guangzhou.myqcloud.com/PicGo/202310100050940.png"))
                .build());
        if (!success) {
            return Response.fail("发送邮件失败");
        }
        return Response.success();
    }

}
