package com.century.logregator;

import junit.framework.AssertionFailedError;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * binding for database replacement
 */
public class DbReplacement {
    private final static Map<String, Object> replacements = new HashMap<>();
    private final static String timeFormat = "yyyy-MM-dd HH:mm:ss.SSS";
    /**
     * bind paramenter
     * @param key
     * @param value
     */
    public final static void bind(String key, Object value){
        if(value instanceof Date){
            Format format = new SimpleDateFormat(timeFormat);
            value = format.format(((Date) value).getTime());
        }
        replacements.put(key, value);
    }

    public final static Object getBinded(String key){
        if(! replacements.containsKey(key)){
            throw new AssertionFailedError("parameter {" + key + "} is not bound for test");
        }
        return replacements.get(key);
    }

    public final static void clear(){
        replacements.clear();
    }
}
