package org.tasks.configuration;

import org.h2.Driver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @Profile("profileH2")
    public DataSource dataSourceH2(
            @Value("${spring.datasource.url.h2}") String url,
            @Value("${spring.datasource.username.h2}") String username,
            @Value("${spring.datasource.password.h2}") String password
    ) {
        DriverManagerDataSource dataSource = getBaseConfiguredDataSource(url, username, password);
        dataSource.setDriverClassName(Driver.class.getName());
        return dataSource;
    }

    @Bean
    @Profile("profilePostgres")
    public DataSource dataSourcePostgres(
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

    @EventListener
    public void fillDb(ContextRefreshedEvent event) {
        DataSource dataSource = event.getApplicationContext().getBean(DataSource.class);

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("scripts/schema.sql"));
        populator.execute(dataSource);
    }

}
