package com.century.logregator.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class JarInfo {
    @Getter @Setter
    private int id;

    @Getter @Setter
    private String fileName;

    public JarInfo(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Optional field. Not all jars have pom information
     */
    @Getter @Setter
    private MvnTag mvnTag;

    public Integer getMvnTagId(){
        if(mvnTag == null){
            return null;
        }
        return mvnTag.getId();
    }
    /**
     * Application, this jar file belongs to
     */
    @Getter
    Integer applicationId;

    public JarInfo(String fileName, MvnTag mvnTag) {
        this.fileName = fileName;
        this.mvnTag = mvnTag;
    }
}
