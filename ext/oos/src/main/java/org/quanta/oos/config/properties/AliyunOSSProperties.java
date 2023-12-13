package org.quanta.oos.config.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2023/12/11
 */
@Data
@Configuration
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = "quanta.aliyun.oss")
public class AliyunOSSProperties extends OOSProperties {
    private String endPoint;
}
