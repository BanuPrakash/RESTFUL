package com.adobe.demo;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class AppConfig {
    // factory method
    @Bean
    public DataSource getDataSource() throws Exception{
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass( "org.h2.Driver" ); //loads the jdbc driver
        cpds.setJdbcUrl( "jdbc:h2:mem:testdb" );
        cpds.setUser("sa");
        cpds.setPassword("password");
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
        return cpds;
    }
}
