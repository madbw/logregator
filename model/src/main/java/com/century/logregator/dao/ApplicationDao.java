package com.century.logregator.dao;

import com.century.logregator.model.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;
import java.util.Properties;

/**
 * Dao for saving application information
 */
public class ApplicationDao {
    public static final String INSERT_ENV = "INSERT INTO application_environment(key, value, application_id) VALUES(?,?,?)";
    public static final String INSERT_PROPS = "INSERT INTO application_properties(key, value, application_id) VALUES(?,?,?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MvnTagDao mvnTagDao;

    /**
     * save application information to database
     */
    public void saveApplication(Application application){
        mvnTagDao.save(application.getAppTag());
        Integer id = jdbcTemplate.queryForObject("INSERT INTO application(mvn_tag_id, start_date, host_name, host_ip) values(?,?,?,?)",
                Integer.class,application.getAppTag().getId(), application.getStartDate(), application.getHostName(), application.getHostIp());
        application.setId(id);

        throw new RuntimeException("todo");
    }

    /**
     * save environment of application
     */
    private void saveEnvironmet(Map<String, String> env, Integer applicationId){
        for (Map.Entry<String, String> entry : env.entrySet()) {
            jdbcTemplate.update(INSERT_ENV, entry.getKey(), entry.getValue(), applicationId);
        }
    }

    /**
     * save properties of application
     */
    private void saveProps(Properties props, Integer applicationId){
        for (Map.Entry<Object, Object> entry : props.entrySet()) {
            jdbcTemplate.update(INSERT_PROPS, entry.getKey(), entry.getValue(), applicationId);
        }
    }
}
