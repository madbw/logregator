package com.century.logregator.tag_extractor;

import com.century.logregator.model.MvnTag;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Class for extracting maven information from jar file
 */
@Slf4j
public class MvnJarTagExtractor {
    final private static String pomFileRegex = "^META-INF/.*/pom.xml$";
    final private static Pattern pomFilePattern = Pattern.compile(pomFileRegex);

    /**
     * extract maven information of file
     * @param path path to file
     * @return maven information
     */
    public static MvnTag extractFromJarFile(String path) {
        if (path == null) {
            throw new RuntimeException("path cant be null");
        }
        if (!path.endsWith(".jar")) {
            throw new IllegalArgumentException("File name should end with .jar");
        }
        log.trace("searching for pom.xml in file {}", path);
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            ZipInputStream jarInputStream = new ZipInputStream(fileInputStream);
            while (true) {
                ZipEntry entry = jarInputStream.getNextEntry();
                if(entry == null){
                    break;
                }
                log.trace("found file {}", entry.getName());
                Matcher matcher = pomFilePattern.matcher(entry.getName());
                if(matcher.matches()){
                    byte[] buffer = new byte[(int)entry.getSize()];
                    jarInputStream.read(buffer);
                    String pomText = new String(buffer,0, findTerminator(buffer));
                    log.trace("found pom.xml:\n{}", pomText);
                    return XmlPomTagExtractor.extract(pomText);
                }
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new JarParseException(e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new JarParseException(e);
        }
        throw new WrongPomFileException("Pom file not found");
    }

    private static int findTerminator(byte[] buffer){
        int terminator = buffer.length;
        for(int i = 0; i < terminator; i++){
            if(buffer[i] == 0x0){
                terminator = i;
            }
        }
        return terminator;
    }
}
