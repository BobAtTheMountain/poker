package com.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class SpringDataConfig {

    @ReadingConverter
    enum LocalDateTimeToStringConverter implements Converter<LocalDateTime, String> {
        INSTANCE,
        ;

        @Override
        public String convert(LocalDateTime source) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return source.format(formatter);
        }
    }

}
