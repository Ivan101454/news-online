package ru.clevertec.newsonline.config;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.clevertec.newsonline.aspect.ControllerAspect;

@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties(prefix = "app.logging")
public class LoggingProperties {

    private boolean enabled;
    private final Logger logger = LoggerFactory.getLogger(LoggingProperties.class);

    void init() {
        logger.info("Start", this);
    }
}
