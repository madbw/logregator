package com.century.logregator.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@Slf4j
public class AggregateController {

    @PostConstruct
    public void init(){
        log.info("LOGREGATOR SERVLET START");
    }

    @RequestMapping(value = "/agg", method = RequestMethod.POST)
    public void aggregateLog(@RequestBody Map<String, Object> logToAggregate){
        log.debug("aggregating log");
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody String status(){
        return "Servlet is working";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(HttpServletRequest request,Model model){
        return "test";
    }
}
