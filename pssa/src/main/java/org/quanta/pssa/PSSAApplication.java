package org.quanta.pssa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/16
 */
@EnableAsync
@SpringBootApplication
public class PSSAApplication {
    public static void main(String[] args) {
        SpringApplication.run(PSSAApplication.class, args);
    }
}
