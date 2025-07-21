package com.adobe.demo;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

//    @Value("{DRIVER}")
//    String driver;
//    @Value("{URL}")
//    String url;

    // factory method
    @Lazy
    @Bean("h2")
    public DataSource getDataSource() throws Exception{
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass( "org.h2.Driver"); //loads the jdbc driver
        cpds.setJdbcUrl( "jdbc:h2:mem:testdb");
        cpds.setUser("sa");
        cpds.setPassword("password");
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
        return cpds;
    }

//    @Bean("oracle")
//    public DataSource getDataSourceOracle() throws Exception{
//        ComboPooledDataSource cpds = new ComboPooledDataSource();
//        cpds.setDriverClass( "org.h2.Driver" ); //loads the jdbc driver
//        cpds.setJdbcUrl( "jdbc:h2:mem:testdb" );
//        cpds.setUser("sa");
//        cpds.setPassword("password");
//        cpds.setMinPoolSize(5);
//        cpds.setAcquireIncrement(5);
//        cpds.setMaxPoolSize(20);
//        return cpds;
//    }
}
