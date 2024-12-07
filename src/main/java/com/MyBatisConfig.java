package com;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jdbc.repository.config.MyBatisJdbcConfiguration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@EnableJdbcRepositories
@Import(MyBatisJdbcConfiguration.class)
public class MyBatisConfig {

    @Bean
    DataSource dataSource() {
        DriverManagerDataSource ds =  new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/poker_db?useSSL=false&serverTimezone=UTC");
        ds.setPassword("111111");
        ds.setUsername("user");
        return ds;
    }



    @Bean
    SqlSessionFactoryBean sqlSessionFactoryBean() {
        SqlSessionFactoryBean f = new SqlSessionFactoryBean();
        f.setDataSource(dataSource());
        return f;
    }
}