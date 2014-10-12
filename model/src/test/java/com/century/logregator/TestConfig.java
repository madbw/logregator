package com.century.logregator;

import com.century.logregator.dao.ApplicationDao;
import com.century.logregator.dao.JarInfoDao;
import com.century.logregator.dao.MvnTagDao;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.integration.spring.SpringLiquibase;
import liquibase.resource.FileSystemResourceAccessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp.BasicDataSource;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;

@Configuration
@ComponentScan(basePackages = "com.century.logregator.dao")

public class TestConfig {
    @PostConstruct
    public void initLiquibase() throws Exception{
        jdbcTemplate().update("DROP SCHEMA logregator cascade");
        jdbcTemplate().update("CREATE SCHEMA logregator");
        Connection c = jdbcTemplate().getDataSource().getConnection();
        try {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(c));
            database.setDefaultSchemaName("logregator");
            Liquibase liquibase = new Liquibase("src/test/resources/liquibase-test.properties", new FileSystemResourceAccessor(),database);
            liquibase.forceReleaseLocks();
            liquibase.dropAll();

            SpringLiquibase springLiquibase = springLiquibase();
            springLiquibase.afterPropertiesSet();
        } catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            System.exit(-5);
        } finally {
            c.close();
        }
    }

    @Bean
    SpringLiquibase springLiquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource());
        liquibase.setDefaultSchema("logregator");
        liquibase.setChangeLog("classpath:/liquibase/db.changelog-master.xml");
        return liquibase;
    }
    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        return jdbcTemplate;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("dbc:postgresql://localhost:5432/logregator_test");
        dataSource.setUsername("postgres");
        dataSource.setPassword("111111");
        dataSource.setMaxActive(50);
        Collection<String> sqls = new ArrayList<String>();
        sqls.add("set search_path TO logregator;");
        dataSource.setConnectionInitSqls(sqls);
        return dataSource;
    }

    @Bean
    public IDatabaseTester iDatabaseTester(){
        return new DataSourceDatabaseTester(dataSource(), "logregator");
    }

    @Bean
    public JarInfoDao jarInfoDao(){
        return new JarInfoDao();
    }

    @Bean
    public MvnTagDao mvnTagDao(){
        return new MvnTagDao();
    }

    @Bean
    public ApplicationDao applicationDao() {
        return new ApplicationDao();
    }
}
