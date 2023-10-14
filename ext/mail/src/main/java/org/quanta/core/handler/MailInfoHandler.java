package org.quanta.core.handler;

import org.quanta.core.Modle.Mail;
import org.springframework.mail.javamail.MimeMessageHelper;


/**
 * Description: 填充邮件信息
 * Author: wzf
 * Date: 2023/10/13
 */
public class MailInfoHandler extends MailHandler {

    @Override
    public void handle(Mail mail, MimeMessageHelper messageHelper) throws Exception {
        messageHelper.setTo(mail.getTo());
        messageHelper.setText(mail.getBody(), mail.isHtml());
        messageHelper.setFrom(mail.getFrom(), mail.getFromUser());
        if(mail.getCc()!=null) messageHelper.setCc(mail.getCc());
        if(mail.getBcc()!=null) messageHelper.setBcc(mail.getBcc());
        if(mail.getSubject()!=null) messageHelper.setSubject(mail.getSubject());
        if(mail.getReplyTo()!=null) messageHelper.setReplyTo(mail.getReplyTo());
        next(mail, messageHelper);
    }

}
