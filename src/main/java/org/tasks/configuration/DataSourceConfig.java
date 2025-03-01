package org.tasks.configuration;

import org.h2.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;

    @Bean
    @Profile("devh2")
    public DataSource dataSourceH2() {
        DriverManagerDataSource dataSource = getBaseConfiguredDataSource();
        dataSource.setDriverClassName(Driver.class.getName());
        return dataSource;
    }

    @Bean
    @Profile("dev")
    public DataSource dataSourcePostgres() {
        DriverManagerDataSource dataSource = getBaseConfiguredDataSource();
        dataSource.setDriverClassName(org.postgresql.Driver.class.getName());

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    private DriverManagerDataSource getBaseConfiguredDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    /*@EventListener
    public void fillDb(ContextRefreshedEvent event) {
        DataSource dataSource = event.getApplicationContext().getBean(DataSource.class);

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("scripts/schema.sql"));
        populator.execute(dataSource);
    }*/

}
