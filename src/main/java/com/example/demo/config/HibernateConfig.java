package com.example.demo.config;

import org.hibernate.SessionFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
//@EnableJpaRepositories("com.spring_sql.sql_work.repository")
public class HibernateConfig {

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://eu-cdbr-west-02.cleardb.net/heroku_18b24e95552a849?reconnect=true");
        dataSourceBuilder.username("beecf4f9962b5b");
        dataSourceBuilder.password("94f86852");
        return dataSourceBuilder.build();
    }

    public Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return properties;
    }

//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
//        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
//        factoryBean.setDataSource(getDataSource());
//        factoryBean.setPackagesToScan("com.spring_sql.sql_work.model");
//        factoryBean.setJpaProperties(additionalProperties());
//        JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
//        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
//        return factoryBean;
//    }

    @Bean
    public SessionFactory getSessionFactory() throws IOException {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(getDataSource());
        factoryBean.setPackagesToScan("com.spring_sql.sql_work.model");
        factoryBean.setHibernateProperties(additionalProperties());
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory factory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(factory);
        return transactionManager;
    }
}

