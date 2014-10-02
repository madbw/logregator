package com.century.logregator.tag_extractor;

import com.century.logregator.model.MvnTag;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.*;

public class MvnJarTagExtractorTest {

    @Test
    public void testExtractFromJarFile() throws Exception {
        URL testJar = this.getClass().getResource("test.jar");
        String path = testJar.getPath();
        MvnTag actual = MvnJarTagExtractor.extractFromJarFile(path);
        MvnTag expected = new MvnTag("com.century", "logregator.logback", "1.0-SNAPSHOT");
        assertEquals("extracted maven tag is wrong", expected, actual);
    }
}