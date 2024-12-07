package com;

import com.common.utils.HttpClientUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GuiyueApplication {

    public static void main(String[] args) {
        System.out.println("aaa");
        // oe初始化
        HttpClientUtils.init();
        SpringApplication.run(GuiyueApplication.class, args);
    }

}
