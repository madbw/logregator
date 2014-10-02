package com.century.logregator.dao;

import com.century.logregator.TestConfig;
import com.century.logregator.model.JarInfo;
import com.century.logregator.model.MvnTag;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.ext.hsqldb.HsqldbConnection;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class JarInfoDaoTest extends DbUnitTest {
    @Autowired
    JarInfoDao jarInfoDao;

    @Test
    public void testSaveJarInfoDao() throws Exception {
        MvnTag mvnTag = new MvnTag("apa", "che", "1.4-snapshot");
        JarInfo jarInfo = new JarInfo("apache-commons-1.3.0.jar", mvnTag);
        jarInfoDao.saveJarInfoDao(jarInfo);
        assertTablesEqual("jar_info");
        assertTablesEqual("mvn_tag");
    }

}