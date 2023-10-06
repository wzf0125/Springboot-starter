package org.quanta.core.beans;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/5
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "quanta.system")
public class SystemConfig {
    Boolean debug = false;
}
