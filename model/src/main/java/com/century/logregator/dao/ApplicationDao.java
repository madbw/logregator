package com.century.logregator.dao;

import com.century.logregator.model.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class ApplicationDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveApplication(Application application){

    }
}
