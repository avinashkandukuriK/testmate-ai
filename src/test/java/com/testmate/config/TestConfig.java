package com.testmate.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.testmate.db.db2.repo",
    entityManagerFactoryRef = "db2EntityManagerFactory",
    transactionManagerRef = "db2TransactionManager"
)
public class TestConfig {

    // === DB2 Configuration ===
    @Bean(name = "db2DataSource")
    @Primary
    public DataSource db2DataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.ibm.db2.jcc.DB2Driver");
        dataSource.setUrl("jdbc:db2://localhost:50000/testdb");
        dataSource.setUsername("db2user");
        dataSource.setPassword("db2password");
        return dataSource;
    }

    @Bean(name = "db2EntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean db2EntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(db2DataSource())
                .packages("com.testmate.db.db2.entity")
                .persistenceUnit("db2")
                .build();
    }

    @Bean(name = "db2TransactionManager")
    @Primary
    public PlatformTransactionManager db2TransactionManager(
            @Qualifier("db2EntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
}
