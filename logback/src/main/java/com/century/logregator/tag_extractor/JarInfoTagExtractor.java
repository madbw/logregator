package com.century.logregator.tag_extractor;


import com.century.logregator.model.JarInfo;
import com.century.logregator.model.MvnTag;

import java.io.File;

/**
 * Class for extraction of jar information
 */
public class JarInfoTagExtractor {
    /**
     * extract information about jar file
     * @param path path to file
     * @return jar information
     */
    public static JarInfo extractJarInformation(String path){
        MvnTag tag = MvnJarTagExtractor.extractFromJarFile(path);
        File file = new File(path);
        String fileName = file.getName();
        return new JarInfo(fileName, tag);
    }
}