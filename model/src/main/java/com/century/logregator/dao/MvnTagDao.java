package com.century.logregator.dao;

import com.century.logregator.model.Application;
import com.century.logregator.model.MvnTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class MvnTagDao {
    private static final String INSERT_MVN_TAG_SQL = "INSERT INTO mvn_tag(group_id, artifact_id, version)" +
            " VALUES(?,?,?) returning id";

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void save(MvnTag mvnTag){
        if(mvnTag == null){
            throw new RuntimeException("mvnTag cant be null");
        }
        if(mvnTag.getId() != null){
            throw new IllegalArgumentException("Cant save mvnTag twice, update is not possible");
        }
        Long id = jdbcTemplate.queryForObject(INSERT_MVN_TAG_SQL, Long.class, mvnTag.getGroupId(), mvnTag.getArtifactId(), mvnTag.getVersion());
        mvnTag.setId(id);
    }

}
