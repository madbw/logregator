package com.century.logregator.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import java.util.Map;

@Controller
@RequestMapping(value = "/aggregate", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AggregateController {

    @PostConstruct
    public void init(){
        log.info("LOGREGATOR SERVLET START");
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void aggregateLog(@RequestBody Map<String, Object> logToAggregate){
        log.debug("aggregating log");
    }

}
