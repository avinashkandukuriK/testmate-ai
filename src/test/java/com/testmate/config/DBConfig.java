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
import java.util.HashMap;

@Configuration
public class DBConfig {

    // === DB2 Configuration ===
    @Bean(name = "db2DataSource")
    public DataSource db2DataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.ibm.db2.jcc.DB2Driver");
        dataSource.setUrl("jdbc:db2://localhost:50000/testdb");
        dataSource.setUsername("db2user");
        dataSource.setPassword("db2password");
        return dataSource;
    }

    @Primary
    @Bean(name = "db2EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean db2EntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(db2DataSource())
                .packages("com.testmate.db.db2.entity")
                .persistenceUnit("db2")
                .build();
    }

    @Primary
    @Bean(name = "db2TransactionManager")
    public PlatformTransactionManager db2TransactionManager(
            final @Qualifier("db2EntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }

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
            final @Qualifier("sqlEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
}
