package com.wx.app.game.config.propertiesConfig;


import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource(value = "classpath:config/pay.properties")
public class PayConfig {
}
