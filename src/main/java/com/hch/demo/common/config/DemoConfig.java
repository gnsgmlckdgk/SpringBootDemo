package com.hch.demo.common.config;

import com.hch.demo.enums.TimeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Slf4j
@Configuration
public class DemoConfig {

    @PostConstruct
    public void started() {
		final String TIME_ZONE = TimeEnum.TZ_ASIA_SEOUAL.getVal();
		log.info("TimeZone Setting... = {}", TIME_ZONE);
		TimeZone.setDefault(TimeZone.getTimeZone(TIME_ZONE));
    }

}
