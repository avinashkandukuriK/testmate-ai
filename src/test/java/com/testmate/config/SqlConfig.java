package com.testmate.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.testmate.db.sql.repo",
    entityManagerFactoryRef = "sqlEntityManagerFactory",
    transactionManagerRef = "sqlTransactionManager"
)
public class SqlConfig {

    // === SQL Server Configuration ===
    @Bean(name = "sqlServerDataSource")
    public DataSource sqlServerDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl("jdbc:sqlserver://localhost:1433;databaseName=testdb");
        dataSource.setUsername("sqluser");
        dataSource.setPassword("sqlpassword");
        return dataSource;
    }

    @Bean(name = "sqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean sqlEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(sqlServerDataSource())
                .packages("com.testmate.db.sql.entity")
                .persistenceUnit("sql")
                .build();
    }

    @Bean(name = "sqlTransactionManager")
    public PlatformTransactionManager sqlTransactionManager(
            @Qualifier("sqlEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
}
