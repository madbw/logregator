package com.century.logregator.dao;

import com.century.logregator.model.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Dao for saving application information
 */
public class ApplicationDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * save application information to database
     */
    public void saveApplication(Application application){
        throw new RuntimeException("todo");
    }
}
