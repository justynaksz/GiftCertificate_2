package com.epam.esm.configuration;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Data source configuration class.
 */
@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:application.properties"})
public class PersistenceJPAConfig {

    @Autowired
    private Environment environment;

    public PersistenceJPAConfig() {
        super();
    }

    /**
     * Defines data source on production environment with properties specified in application.properties file.
     * @return dataSource    configured dataSource
     */
    @Bean
    @Profile("prod")
    public DataSource prodDataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(Preconditions.checkNotNull(environment.getProperty("prod.datasource.url")));
        dataSource.setUsername(Preconditions.checkNotNull(environment.getProperty("prod.datasource.username")));
        dataSource.setPassword(Preconditions.checkNotNull(environment.getProperty("prod.datasource.password")));
        dataSource.setDriverClassName(Preconditions.checkNotNull(environment.getProperty("prod.datasource.driverClassName")));
        return dataSource;
    }

    @Profile("prod")
    public Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        return properties;
    }

    /**
     * Defines data source on development environment with properties specified in application.properties file.
     * @return dataSource    configured dataSource
     */
    @Bean
    @Profile("dev")
    public DataSource devDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(Preconditions.checkNotNull(environment.getProperty("dev.datasource.url")));
        dataSource.setUsername(Preconditions.checkNotNull(environment.getProperty("dev.datasource.username")));
        dataSource.setPassword(Preconditions.checkNotNull(environment.getProperty("dev.datasource.password")));
        dataSource.setDriverClassName(Preconditions.checkNotNull(environment.getProperty("dev.datasource.driverClassName")));
        return dataSource;
    }

    @Profile("dev")
    public Properties additionalProdProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        return properties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setJpaDialect(new HibernateJpaDialect());
        em.setPackagesToScan("com.epam.esm");
        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
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