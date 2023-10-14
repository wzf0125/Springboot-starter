package org.quanta.core.handler;

import lombok.Data;
import org.quanta.core.Modle.Mail;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Objects;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/13
 */
@Data
public abstract class MailHandler {
    // 邮件处理器
    private MailHandler handlerHolder;

    // 初始化处理链条
    public static MailHandler create() {
        // 参数校验
        MailParamHandler handler = new MailParamHandler();
        handler.addHandler(new MailInfoHandler()) // 邮件信息填充
                .addHandler(new MailAttachmentHandler()); // 附件处理
        return handler;
    }

    // 邮件处理逻辑
    public abstract void handle(Mail mail, MimeMessageHelper messageHelper) throws Exception;


    public MailHandler addHandler(MailHandler nextHandler) {
        this.setHandlerHolder(nextHandler);
        return nextHandler;
    }

    public void next(Mail mail, MimeMessageHelper messageHelper) throws Exception {
        if (Objects.nonNull(handlerHolder)) {
            handlerHolder.handle(mail, messageHelper);
        }
    }
}
