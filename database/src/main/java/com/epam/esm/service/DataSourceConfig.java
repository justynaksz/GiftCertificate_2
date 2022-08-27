package com.epam.esm.service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Data source configuration class.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("com.epam.esm")
@PropertySource(value={"classpath:jdbc.properties"})
public class DataSourceConfig {

    private final Environment environment;

    @Autowired
    public DataSourceConfig(Environment environment) {
        this.environment = environment;
    }

    /**
     * Connection pool configuration class.
     * @return config    hikari connection pool configuration
     */
    @Bean
    @Profile("prod")
    public HikariConfig hikariConfig(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(environment.getProperty("jdbc.url"));
        config.setUsername(environment.getProperty("jdbc.username"));
        config.setPassword(environment.getProperty("jdbc.password"));
        config.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
        return config;
    }

    /**
     * Defines data source with properties specified in jdbc.properties file.
     * @return dataSource    configured dataSource
     */
    @Bean
    @Profile("prod")
    public static DataSource getDataSource(HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    /**
     * Generates manager for transactional operations.
     * @return datasource transaction manager
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * Creates embedded database of H2 type.
     * @return database     embedded database of H2 type
     */
    @Bean
    @Profile("dev")
    public static DataSource createH2DataSource() {
        DataSource dataBase = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("dbEntities.sql")
                .addScript("dbRecords.sql")
                .build();
        return dataBase;
    }

    /**
     * Generates the context for the application.
     * @return application context
     */
    @Bean
    public ApplicationContext getApplicationContext() {
        return new AnnotationConfigApplicationContext();
    }

    /**
     * Generates {@code jdbcTemplate} with given data source.
     * @return jdbcTemplate
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * Generates {@code namedParameterJdbcTemplate} with given data source.
     * @return namedParameterJdbcTemplate
     */
    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
