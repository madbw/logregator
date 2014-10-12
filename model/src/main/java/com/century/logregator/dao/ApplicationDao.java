package com.century.logregator.dao;

import com.century.logregator.model.Application;
import com.century.logregator.model.JarInfo;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

/**
 * Dao for saving application information
 */
public class ApplicationDao {
    public static final String INSERT_ENV = "INSERT INTO application_environment(key, value, application_id) VALUES(?,?,?)";
    public static final String INSERT_PROPS = "INSERT INTO application_properties(key, value, application_id) VALUES(?,?,?)";
    public static final String INSERT_APP = "INSERT INTO application(mvn_tag_id, start_date, host_name, host_ip) values(?,?,?,?) RETURNING id";

    @Autowired
    @Setter
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Setter
    private MvnTagDao mvnTagDao;

    @Autowired
    @Setter
    private JarInfoDao jarInfoDao;

    /**
     * save application information to database
     */
    public void saveApplication(Application application){
        if(application.getAppTag() == null){
            throw new IllegalArgumentException("no logging for non maven apps, sorry");
        }
        mvnTagDao.save(application.getAppTag());
        Integer id = jdbcTemplate.queryForObject(INSERT_APP,
                Integer.class,application.getAppTag().getId(), application.getStartDate(), application.getHostName(), application.getHostIp());
        application.setId(id);
        saveJars(application);
        saveEnv(application);
        saveProps(application);
    }

    /**
     * save properties of application
     */
    private void saveProps(Application application) {
        Map<Object, Object> props = new TreeMap<>(application.getSystemProps());
        for (Map.Entry<Object, Object> entry : props.entrySet()) {
            jdbcTemplate.update(INSERT_PROPS, entry.getKey(), entry.getValue(), application.getId());
        }
    }

    /**
     * save environment of application
     */
    private void saveJars(Application application) {
        for (JarInfo jarInfo : application.getTags()) {
            jarInfo.setApplicationId(application.getId());
            jarInfoDao.saveJarInfoDao(jarInfo);
        }
    }

    private void saveEnv(Application application) {
        for (Map.Entry<String, String> entry : application.getEnvironment().entrySet()) {
            jdbcTemplate.update(INSERT_ENV, entry.getKey(), entry.getValue(), application.getId());
        }
    }

}
