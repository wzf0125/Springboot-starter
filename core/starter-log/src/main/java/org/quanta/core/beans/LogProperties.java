package org.quanta.core.beans;

import lombok.Data;
import org.quanta.core.constants.LogLevel;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/5
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "quanta.log")
public class LogProperties {
    private Integer level = 0;

    public LogLevel getLogLevel() {
        return LogLevel.codeOf(level);
    }
}
