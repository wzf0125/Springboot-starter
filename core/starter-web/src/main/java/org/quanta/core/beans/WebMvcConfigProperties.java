package org.quanta.core.beans;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/5
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "quanta.web")
public class WebMvcConfigProperties {
    private List<String> excludePath = new ArrayList<>();
    private List<String> interceptorPath = new ArrayList<>();
}
