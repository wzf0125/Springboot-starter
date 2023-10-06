package org.quanta.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Description: 启动类
 * Author: wzf
 * Date: 2023/10/5
 */
@SpringBootApplication(scanBasePackages = "org.quanta")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
