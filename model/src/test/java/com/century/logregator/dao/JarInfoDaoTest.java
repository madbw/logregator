package com.century.logregator.dao;

import com.century.logregator.DbReplacement;
import com.century.logregator.DbTest;
import com.century.logregator.TestConfig;
import com.century.logregator.model.JarInfo;
import com.century.logregator.model.MvnTag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@RunWith(DbUnitRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class JarInfoDaoTest extends DbUnitTest {
    @Autowired
    JarInfoDao jarInfoDao;

    @Test
    @DbTest(expected = "dao/expected.xml", assertTables = {"mvn_tag", "jar_info"}, replacemnt = {"mvn_id", "jar_info_id"})
    public void testSaveJarInfoDao() throws Exception {
        MvnTag mvnTag = new MvnTag("apa", "che", "1.4-snapshot");
        JarInfo jarInfo = new JarInfo("apache-commons-1.3.0.jar", mvnTag);
        jarInfoDao.saveJarInfoDao(jarInfo);
        DbReplacement.bind("mvn_id", jarInfo.getMvnTagId());
        DbReplacement.bind("jar_info_id", jarInfo.getId());
    }

}