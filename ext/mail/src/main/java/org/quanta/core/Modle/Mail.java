package org.quanta.core.Modle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.List;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mail {
    /**
     * 发件人信息(不能修改必须与发件账号邮箱相同)
     */
    private String from;

    /**
     * 发件人用户名
     */
    private String fromUser;

    /**
     * 收件人
     */
    private String to;

    /**
     * 抄送人
     */
    private String cc;

    /**
     * 隐私抄送人
     */
    private String bcc;

    /**
     * 回复地址
     */
    private String replyTo;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String body;

    /**
     * 是否为html文件
     */
    private boolean isHtml;

    /**
     * 附件url
     */
    private List<String> attachmentUrl;

    /**
     * 附件
     */
    private List<File> attachment;
}
