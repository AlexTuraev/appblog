package org.tasks.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;

@Configuration
@TestPropertySource("classpath:resources/application-test.properties")
public class DataSourceTestConfig {

    @Bean
    public DataSource dataSource(
            @Value("${spring.datasource.url.postgres}") String url,
            @Value("${spring.datasource.username.postgres}") String username,
            @Value("${spring.datasource.password.postgres}") String password
    ) {
        DriverManagerDataSource dataSource = getBaseConfiguredDataSource(url, username, password);
        dataSource.setDriverClassName(org.postgresql.Driver.class.getName());
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    private DriverManagerDataSource getBaseConfiguredDataSource(String url, String username, String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

}
