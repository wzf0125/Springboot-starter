package org.quanta.core.utils;

import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quanta.base.exception.RetryException;
import org.quanta.base.utils.RetryUtils;
import org.quanta.core.Modle.Mail;
import org.quanta.core.handler.MailHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/13
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MailUtils {
    @Value("${spring.mail.username}")
    private String fromEmail;
    private final JavaMailSender javaMailSender;

    /**
     * 发送邮件
     */
    @Async
    public void sendMailAsync(Mail mail) {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        process(mail, mimeMailMessage);
    }

    /**
     * 发送邮件
     */
    public boolean sendMail(Mail mail) {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        return process(mail, mimeMailMessage);
    }

    private boolean process(Mail mail, MimeMessage mimeMailMessage) {
        MailHandler handler = MailHandler.create();
        try {
            // 第二个参数为是否传附件
            MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage, hasAttachment(mail), "utf8");
            mail.setFrom(fromEmail);
            handler.handle(mail, helper);
            // 带重试的邮件下发
            RetryUtils.retry(() -> {
                try {
                    javaMailSender.send(mimeMailMessage);
                } catch (Exception e) {
                    log.error(ExceptionUtil.stacktraceToString(e));
                    throw new RetryException();
                }
            }, 3, 1);
        } catch (Exception e) {
            log.error(ExceptionUtil.stacktraceToString(e));
            return false;
        }
        return true;
    }

    /**
     * 判断是否需要发送附件
     */
    private boolean hasAttachment(Mail mail) {
        return (mail.getAttachment() != null && !mail.getAttachment().isEmpty()) ||
                mail.getAttachmentUrl() != null && !mail.getAttachmentUrl().isEmpty();
    }

}
