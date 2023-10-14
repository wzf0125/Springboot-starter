package org.quanta.core.handler;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import org.quanta.base.utils.FileNameUtils;
import org.quanta.core.Modle.Mail;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeUtility;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Description: 处理附件逻辑
 * Author: wzf
 * Date: 2023/10/13
 */
public class MailAttachmentHandler extends MailHandler {

    @Override
    public void handle(Mail mail, MimeMessageHelper messageHelper) throws Exception {
        // 最终邮件列表
        List<File> attachmentList = mail.getAttachment();
        List<String> needDownLoadAttachment = mail.getAttachmentUrl();

        // 判断是否有附件需要处理
        if ((attachmentList == null || attachmentList.isEmpty()) &&
                (needDownLoadAttachment == null || needDownLoadAttachment.isEmpty())) {
            next(mail, messageHelper);
        }

        // 需要下发的附件统一塞到attachmentList
        if (attachmentList == null) {
            attachmentList = new ArrayList<>();
        }

        // 判断是否需要下载附件(url形式传入的附件需要这边做下载处理)
        if (needDownLoadAttachment != null && !attachmentList.isEmpty()) {
            List<File> tmpFile = needDownLoadAttachment.stream().parallel().map(url -> {
                String fileName = FileNameUtils.getFileName(url,false);
                String suffix = FileNameUtils.getFileNameSuffix(url);
                File tempFile = FileUtil.createTempFile(fileName,suffix,true);
                HttpUtil.downloadFile(url, tempFile);
                return tempFile;
            }).collect(Collectors.toList());
            attachmentList.addAll(tmpFile);
        }

        // 遍历放入邮件列表
        for (File attachment : attachmentList) {
            // 对文件名编码并拼接后缀
            String originFilename = FileNameUtils.getFileName(attachment.getName(),false);
            String suffix = FileNameUtils.getFileNameSuffix(attachment.getName());
            String filename = MimeUtility.encodeText(originFilename ,"UTF-8", "B")+suffix;
            messageHelper.addAttachment(filename, attachment);
        }

        // 下一步处理
        next(mail, messageHelper);
    }

}
