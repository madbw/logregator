package com.century.logregator.logback;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.LogbackException;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import ch.qos.logback.core.status.Status;
import com.century.logregator.model.JarInfo;
import com.century.logregator.model.MvnTag;
import com.century.logregator.tag_extractor.JarInfoTagExtractor;
import com.century.logregator.tag_extractor.MvnJarTagExtractor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Slf4j
public class LogregatorAppender extends AppenderBase {
    private final static String filePrefix = "file:";
    @Getter @Setter
    private String projectName = "undefind";

    @Override
    protected void append(Object eventObject) {
        if(eventObject instanceof LoggingEvent){
            doAppend((LoggingEvent) eventObject);
        }
        throw new IllegalStateException("Неожиданность, Where is logback?");
    }

    private void doAppend(LoggingEvent event) throws LogbackException{
        event.getLevel();
    }


    @Override
    public void start() {
        super.start();
        try {
            Map<String, String> env = System.getenv();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        try {
            Properties properties = System.getProperties();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        try {
            Collection<JarInfo> dependencies = getDependecies();
            System.out.println("Project " + projectName + " dependencies: \n" + StringUtils.join(dependencies,"\n"));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private Collection<JarInfo> getDependecies() {
        Collection<JarInfo> tags = new ArrayList<>();
        URL res = LogregatorAppender.class.getResource("LogregatorAppender.class");
        File path = new File(res.getPath());
        while(!testFileExist(path)){
            path = path.getParentFile();
        }
        File[] files = getFiles(path);
        for (File file : files) {
            JarInfo jarInfo = null;
            try {
                jarInfo = JarInfoTagExtractor.extractJarInformation(file.getPath());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            tags.add(jarInfo);
        }
        return tags;
    }

    private File[] getFiles(File path){
        String fileName = path.getPath();
        fileName = normalizeFileName(fileName);
        File file = new File(fileName);
        return file.listFiles();
    }

    private String normalizeFileName(String fileName){
        if(fileName.startsWith("file:/")){
            fileName = fileName.substring("file:".length());
        }
        return fileName;
    }

    private Boolean testFileExist(File path){
        String filePath = path.getPath();
        if(filePath.startsWith("file:/")){
            filePath = filePath.substring("file:".length());
        }
        File file = new File(filePath);
        return file.exists();
    }

}
