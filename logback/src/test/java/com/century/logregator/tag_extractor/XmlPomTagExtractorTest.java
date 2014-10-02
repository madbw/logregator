package com.century.logregator.tag_extractor;

import com.century.logregator.model.MvnTag;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.*;

public class XmlPomTagExtractorTest {
    private static String pom1;
    private static String pom2;
    private static String pom3;
    private static MvnTag tag1 = new MvnTag("test", "test", "0.0.5");
    private static MvnTag tag2 = new MvnTag("com.century", "logregator.logback", "1.0-SNAPSHOT");
    private static MvnTag tag3 = new MvnTag("test", "test","0.0.5");

    @BeforeClass
    public static void initStreams() throws Exception{
        pom1 = streamToString(XmlPomTagExtractorTest.class.getResourceAsStream("test_pom1.xml"));
        pom2 = streamToString(XmlPomTagExtractorTest.class.getResourceAsStream("test_pom2.xml"));
        pom3 = streamToString(XmlPomTagExtractorTest.class.getResourceAsStream("test_pom3.xml"));
    }

    private static String streamToString(InputStream stream) throws Exception{
        byte[] buf = new byte[stream.available()];
        stream.read(buf);
        return  new String(buf);
    }

    @Test
    public void testExtractNoParent() throws Exception {
        MvnTag actual = XmlPomTagExtractor.extract(pom1);
        assertEquals("wrong maven tag exctraction", tag1, actual);
    }

    @Test
    public void testPartialTag() throws Exception {
        MvnTag actual = XmlPomTagExtractor.extract(pom2);
        assertEquals("wrong maven tag exctraction", tag2, actual);
    }

    @Test
    public void testFullPom() throws Exception{
        MvnTag actual = XmlPomTagExtractor.extract(pom3);
        assertEquals("wrong maven tag exctraction", tag3, actual);
    }
}