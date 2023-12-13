package org.quanta.oos.config.properties;

import lombok.Data;

/**
 * Description: 通用配置
 * Param:
 * return:
 * Author: wzf
 * Date: 2023/12/13
 */
@Data
public class OOSProperties {
    String secretId;
    String secretKey;
    String bucketName;
    String urlPrefix;
}
