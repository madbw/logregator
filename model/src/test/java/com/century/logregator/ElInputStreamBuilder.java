package com.century.logregator;

import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;
import org.junit.Test;
import org.springframework.util.StreamUtils;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class ElInputStreamBuilder {

    public InputStream spel(String filename,  Map<String, Object> reps) throws Exception {
        InputStream inputStream = getClass().getResourceAsStream(filename);
        String temp = StreamUtils.copyToString(inputStream, Charset.forName("utf-8"));
        ExpressionFactory factory = new ExpressionFactoryImpl();
        SimpleContext context = new SimpleContext();
        for (Map.Entry<String, Object> entry : reps.entrySet()) {
            context.setVariable(entry.getKey(), factory.createValueExpression(entry.getValue(), entry.getValue().getClass()));
        }
        ValueExpression valueExpression = factory.createValueExpression(context, temp, String.class);
        System.out.println(valueExpression.getValue(context));
        return null;
    }

    @Test
    public void test() throws Exception {
        ExpressionFactory factory = new ExpressionFactoryImpl();
        SimpleContext context = new SimpleContext();
        context.setVariable("id", factory.createValueExpression(1, int.class));
        ValueExpression valueExpression = factory.createValueExpression(context, "id=${id+1} id2=${id+2}", String.class);
        System.out.println(valueExpression.getExpressionString());
        System.out.println(valueExpression.getValue(context));
    }

    @Test
    public void test2() throws Exception {
        Map<String, Object> reps = new HashMap<>();
        reps.put("mvn_id", "1");
        reps.put("jar_info_id", "10");
        spel("dao/expected_application_save.xml", reps);
    }
}
