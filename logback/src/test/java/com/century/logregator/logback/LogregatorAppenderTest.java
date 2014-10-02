package com.century.logregator.logback;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

@Slf4j
public class LogregatorAppenderTest {
    @Test
    public void testAppend() {
        log.debug("test");
    }


    @Test(timeout = 100 )
    public void testAppendTime(){
        for(int i = 0; i < 100; i++)
        log.error("writing error to database");
    }

    @Test(timeout = 10)
    public void testSingleAppendTime(){
        log.debug("writing debug to database");
    }

    @Test
    public void logUncheckedExeption(){
        try {
            uncheckedExceptionGenerator();
        }catch (RuntimeException e){
            log.error(e.getMessage(), e);
        }
    }

    @Test
    public void logCheckedException(){
        try {
            checkedExceptionGenerator();
        }catch (IOException e){
            log.error(e.getMessage(), e);
        }
    }

    private void uncheckedExceptionGenerator(){
        throw new RuntimeException("it`s a test exception");
    }

    private void checkedExceptionGenerator() throws IOException{
        throw new IOException("exception thrown by test");
    }
}