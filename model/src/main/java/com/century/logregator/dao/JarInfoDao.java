package com.century.logregator.dao;

import com.century.logregator.model.JarInfo;
import com.century.logregator.model.MvnTag;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class JarInfoDao {
    @Autowired
    @Setter
    private JdbcTemplate jdbcTemplate;
    @Autowired
    @Setter
    private MvnTagDao mvnTagDao;
    String INSERT_JAR_INFO_SQL = "INSERT INTO jar_info(file_name, mvn_tag_id, application_id) VALUES (?, ?, ?) RETURNING id";

    public void saveJarInfoDao(JarInfo jarInfo){
        if(jarInfo.getMvnTag() != null){
            mvnTagDao.save(jarInfo.getMvnTag());
        }
        Integer id = jdbcTemplate.queryForObject(INSERT_JAR_INFO_SQL, Integer.class, jarInfo.getFileName(), jarInfo.getMvnTagId(), jarInfo.getApplicationId());
        jarInfo.setId(id);
    }

}
