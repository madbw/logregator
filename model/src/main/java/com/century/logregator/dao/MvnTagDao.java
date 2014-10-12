package com.century.logregator.dao;

import com.century.logregator.model.MvnTag;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class MvnTagDao {
    private static final String INSERT_MVN_TAG_SQL = "INSERT INTO mvn_tag(group_id, artifact_id, version)" +
            " VALUES(?,?,?) returning id";

    @Autowired
    @Setter
    private JdbcTemplate jdbcTemplate;


    public void save(MvnTag mvnTag){
        if(mvnTag == null){
            throw new RuntimeException("mvnTag cant be null");
        }
        if(mvnTag.getId() != null){
            throw new IllegalArgumentException("Cant save mvnTag twice, update is not possible");
        }
        Integer id = jdbcTemplate.queryForObject(INSERT_MVN_TAG_SQL, Integer.class, mvnTag.getGroupId(), mvnTag.getArtifactId(), mvnTag.getVersion());
        mvnTag.setId(id);
    }

}
