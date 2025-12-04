package org.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.demo.testinfra.config.TestEnvConfig;

@SpringBootApplication
@EnableConfigurationProperties({TestEnvConfig.class})
public class SpringApplication {
    static void main() {
        org.springframework.boot.SpringApplication.run(SpringApplication.class);
    }
}
