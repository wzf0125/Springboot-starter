package org.quanta.core.handler;

import org.quanta.core.Modle.Mail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Objects;

/**
 * Description: 邮件参数校验
 * Author: wzf
 * Date: 2023/10/13
 */
public class MailParamHandler extends MailHandler {

    @Override
    public void handle(Mail mail, MimeMessageHelper messageHelper) throws Exception {
        // 校验参数
        if (Objects.isNull(mail)
                || Objects.isNull(mail.getTo())
                || Objects.isNull(mail.getCc())
                || Objects.isNull(mail.getBody())
        ) {
            throw new RuntimeException("邮件下发缺少必填参数");
        }
        next(mail, messageHelper);
    }

}
