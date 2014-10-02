package com.century.logregator;

import java.util.HashMap;
import java.util.Map;

/**
 * class for building replaces
 */
public class RepBuilder {
    Map<String, Object> ret = new HashMap<>();

    public Map<String, Object> build(){
        return ret;
    }

    public RepBuilder add(String key, Object value){
        ret.put(key, value);
        return this;
    }

    public static RepBuilder addS(String key, Object value){
        return new RepBuilder().add(key, value);
    }
}
