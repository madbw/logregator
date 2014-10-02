package com.century.logregator.dao;

import com.century.logregator.TestConfig;
import com.century.logregator.model.Application;
import com.century.logregator.model.JarInfo;
import com.century.logregator.model.MvnTag;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class ApplicationDaoTest extends DbUnitTest {
    @Autowired
    private ApplicationDao applicationDao;

    @Test
    public void testSaveAppInfo() throws Exception{
        Application application = new Application();
        application.setHostIp("172.172.1.102");
        application.setHostName("test_server");
        application.setStartDate(DateUtils.parseDate("25-12-2014", "dd-MM-yyyy"));

        Properties properties = new Properties();
        properties.put("s1", "value1");
        properties.put("s2", "value2");
        properties.put("s3", "value3");
        application.setSystemProps(properties);

        Map<String, String> environment = new HashMap<>();
        environment.put("env-key1", "env-val1");
        environment.put("env-key2", "env-val2");
        environment.put("env-key3", "env-val3");
        environment.put("env-key4", "env-val4");
        application.setEnvironment(environment);

        MvnTag tag1 = new MvnTag("century", "test", "1.0");
        application.addJarInfo(new JarInfo("century.test-1.0.jar", tag1));
        application.addJarInfo(new JarInfo("test.another-2.0.jar"));
        MvnTag tag2 = new MvnTag("commons-logging", "commons-logging", "3.2.1");
        application.addJarInfo(new JarInfo("commons-logging-3.2.1.jar",tag2));

        applicationDao.saveApplication(application);
    }

}