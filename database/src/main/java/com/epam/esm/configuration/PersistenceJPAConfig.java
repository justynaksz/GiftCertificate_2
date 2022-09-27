package com.epam.esm.configuration;

import com.epam.esm.passwordutil.PasswordUtil;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

/**
 * Data source configuration.
 */
@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:application.properties"})
public class PersistenceJPAConfig {

    private final Environment environment;

    @Autowired
    public PersistenceJPAConfig(Environment environment) {
        this.environment = environment;
    }

    /**
     * Defines data source on a production environment with properties specified in application file.
     *
     * @return dataSource    configured dataSource
     */
    @Bean
    @Profile("prod")
    public DataSource prodDataSource() throws NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        var dataSource = new DriverManagerDataSource();
        dataSource.setUrl(Preconditions.checkNotNull(environment.getProperty("prod.datasource.url")));
        dataSource.setUsername(Preconditions.checkNotNull(environment.getProperty("prod.datasource.username")));
        dataSource.setPassword(PasswordUtil.decrypt(Preconditions.checkNotNull(environment.getProperty("prod.datasource.password"))));
        dataSource.setDriverClassName(Preconditions.checkNotNull(environment.getProperty("prod.datasource.driverClassName")));
        return dataSource;
    }

    /**
     * Adds dialect property to the prod data source.
     *
     * @return properties    datasource additional properties
     */
    @Profile("prod")
    public Properties additionalProdProperties() {
        var properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        return properties;
    }

    /**
     * Defines data source on a development environment with properties specified in application file.
     *
     * @return dataSource    configured dataSource
     */
    @Bean
    @Profile("dev")
    public DataSource devDataSource() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException,
            BadPaddingException, InvalidKeyException {
        var dataSource = new DriverManagerDataSource();
        dataSource.setUrl(Preconditions.checkNotNull(environment.getProperty("dev.datasource.url")));
        dataSource.setUsername(Preconditions.checkNotNull(environment.getProperty("dev.datasource.username")));
        dataSource.setPassword(PasswordUtil.decrypt(Preconditions.checkNotNull(environment.getProperty("dev.datasource.password"))));
        dataSource.setDriverClassName(Preconditions.checkNotNull(environment.getProperty("dev.datasource.driverClassName")));
        return dataSource;
    }

    /**
     * Adds dialect property to the dev data source.
     *
     * @return properties    datasource additional properties
     */
    @Profile("dev")
    public Properties additionalDevProperties() {
        var properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        return properties;
    }

    /**
     * Configures {@link EntityManagerFactory}.
     *
     * @param dataSource depending on the profile
     * @param properties additional properties depending on the profile
     * @return em               entity manager factory
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Properties properties) {
        final var em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setJpaDialect(new HibernateJpaDialect());
        em.setPackagesToScan("com.epam.esm");
        final var vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(properties);
        return em;
    }

    /**
     * Configures {@link PlatformTransactionManager}.
     *
     * @param entityManagerFactory depending on profile
     * @return transactionManager
     */
    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        var transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
