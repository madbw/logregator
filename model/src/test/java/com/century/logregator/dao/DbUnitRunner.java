package com.century.logregator.dao;

import com.century.logregator.DbReplacement;
import com.century.logregator.DbTest;
import com.century.logregator.ElInputStreamBuilder;
import junit.framework.ComparisonFailure;
import lombok.extern.slf4j.Slf4j;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DbUnitRunner extends SpringJUnit4ClassRunner {

    public DbUnitRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    private DbTest dbTest = null;
    private DbUnitTest test = null;


    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        log.info("database test {} --> {}" , test.getClass(), method.getName());
        this.test = (DbUnitTest) test;
        dbTest = method.getAnnotation(DbTest.class);
        return super.methodInvoker(method, test);
    }

    @Override
    public void run(RunNotifier notifier) {
        super.run(notifier);
        if(dbTest != null){
            try {
                testByXml();
            } catch (ComparisonFailure e){
                notifier.fireTestFailure(new Failure(getDescription() , e));
            }
            catch (Exception e) {
                notifier.fireTestFailure(new Failure(getDescription(), e));
            }
        } else {
            log.debug("DbUnit test missing dbTest annotations");
        }
    }

    private void testByXml() {
        if (dbTest != null) {
            String expectedDataSet = dbTest.expected();
            String[] assertTables = dbTest.assertTables();
            String[] replacemnt = dbTest.replacemnt();
            log.debug("dbUnitTest: expectedDataSet={}", expectedDataSet);
            Map<String, Object> reps = new HashMap<>();
            for (String key : replacemnt) {
                Object value = DbReplacement.getBinded(key);
                log.info("binding {}={}", key, value);
                reps.put(key, value);
            }
            try {
                for (String assertTable : assertTables) {

                    Reader reader = new ElInputStreamBuilder().spel(expectedDataSet, reps);
                    IDataSet expected = new FlatXmlDataSetBuilder().build(reader);
                    IDataSet actual = test.getActual();
                    test.assertTablesEqual(assertTable, expected, actual);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }

        }
    }
}
