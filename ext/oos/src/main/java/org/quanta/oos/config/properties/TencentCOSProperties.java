package org.quanta.oos.config.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Description: 腾讯云对象存储配置参数
 * Param:
 * return:
 * Author: wzf
 * Date: 2023/12/11
 */
@Data
@Configuration
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = "quanta.tencent.cos")
public class TencentCOSProperties extends OOSProperties {
    private String region;
}
