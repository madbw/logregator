package com.century.logregator;

import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;
import lombok.extern.slf4j.Slf4j;
import org.dbunit.Assertion;
import org.junit.Test;
import org.springframework.util.StreamUtils;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Slf4j
/**
 * helper class to load xml template and replace variables
 */
public class ElInputStreamBuilder {
    public Reader spel(String filename,  Map<String, Object> reps) throws Exception {
        InputStream inputStream = getClass().getResourceAsStream(filename);
        String temp = StreamUtils.copyToString(inputStream, Charset.forName("utf-8"));
        ExpressionFactory factory = new ExpressionFactoryImpl();
        SimpleContext context = new SimpleContext();
        for (Map.Entry<String, Object> entry : reps.entrySet()) {
            context.setVariable(entry.getKey(), factory.createValueExpression(entry.getValue(), entry.getValue().getClass()));
        }
        ValueExpression valueExpression = factory.createValueExpression(context, temp, String.class);
        String ret = valueExpression.getValue(context).toString();
        log.info("replaced dataset:\n{}", ret);
        return new StringReader(ret);
    }

}
