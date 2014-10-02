package com.century.logregator.model;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * application configuration. logged at start
 */
public class Application {

    @Getter @Setter
    private Long id;
    /**
     * Application start time
     */
    @Getter @Setter
    private Date startDate;

    /**
     * host name
     */
    @Getter @Setter
    private String hostName;

    /**
     * host ip
     */
    @Getter @Setter
    private String hostIp;

    /**
     * System.getEnv
     */
    @Getter @Setter
    private Map<String, String> environment;

    /**
     * System.getProperties
     */
    @Getter @Setter
    private Properties systemProps;

    /**
     * Information about application dependencies
     */
    @Getter
    private Collection<JarInfo> tags = new ArrayList<JarInfo>();

    public void addJarInfo(JarInfo jarInfo){
        tags.add(jarInfo);
        if(id != null){
            jarInfo.applicationId = id;
        }
    }


}
