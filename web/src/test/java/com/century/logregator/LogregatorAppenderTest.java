package com.century.logregator;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.PostConstruct;

/**
 * Tests for LogregatorAppender. assuming that LogregatorAppender is in jar file
 */
@Slf4j
public class LogregatorAppenderTest {

    @Test(timeout = 10)
    public void testSingleAppendTime(){
        log.debug("writing debug to database");
    }

}
