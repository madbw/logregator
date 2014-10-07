package com.century.logregator.dao;

import com.century.logregator.DbReplacement;
import com.century.logregator.DbTest;
import com.century.logregator.RepBuilder;
import com.century.logregator.TestConfig;
import com.century.logregator.model.MvnTag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(DbUnitRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class MvnTagDaoTest extends DbUnitTest {

    @Autowired
    private MvnTagDao mvnTagDao;

    @Test
    @DbTest(expected = "dao/expected.mvn_tag.xml", assertTables = {"mvn_tag"}, replacemnt = "mvn_id")
    public void testSave() throws Exception {
        MvnTag mvnTag = new MvnTag("apa", "che", "1.4-snapshot");
        mvnTagDao.save(mvnTag);
        DbReplacement.bind("mvn_id", mvnTag.getId());
    }

}