package com.century.logregator.tag_extractor;

import com.century.logregator.model.JarInfo;
import com.century.logregator.model.MvnTag;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class JarInfoTagExtractorTest {

    @Test
    public void testExtractJarInformation() throws Exception {
        URL testJar = this.getClass().getResource("test.jar");
        String path = testJar.getPath();
        MvnTag expectedMvnTag = new MvnTag("com.century", "logregator.logback", "1.0-SNAPSHOT");
        JarInfo expectedJarInfo = new JarInfo("test.jar", expectedMvnTag);
        JarInfo actualJarInfo = JarInfoTagExtractor.extractJarInformation(path);
        assertEquals("extracted jar tag is wrong", expectedJarInfo, actualJarInfo);
    }
}