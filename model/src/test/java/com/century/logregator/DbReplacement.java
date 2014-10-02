package com.century.logregator;

import junit.framework.AssertionFailedError;

import java.util.HashMap;
import java.util.Map;

/**
 * binding for database replacement
 */
public class DbReplacement {
    private final static Map<String, Object> replacements = new HashMap<>();

    /**
     * bind paramenter
     * @param key
     * @param value
     */
    public final static void bind(String key, Object value){
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
