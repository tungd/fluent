package com.eventmap.fluent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eventmap.fluent.domain.Matches;
import com.eventmap.fluent.manager.GrammarCorrectionManagement;
import com.eventmap.fluent.manager.SynonymSuggestionManager;
import com.eventmap.fluent.domain.Matches;
import com.eventmap.fluent.domain.SummarizeResult;
import com.eventmap.fluent.manager.GrammarCorrectionManagement;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by huytran on 4/9/16.
 */
@RestController
@EnableAutoConfiguration
@ComponentScan("com.eventmap.fluent.manager")
@RequestMapping("/")
public class FluentMainController {

    @Autowired
    GrammarCorrectionManagement grammarCorrectionManagement;
    @Autowired
    SynonymSuggestionManager synonymManagement;

    static final Logger logger = Logger.getLogger(FluentMainController.class);

    @RequestMapping(value = "correction"/*, method = RequestMethod.POST*/)
    @ResponseBody
    public Matches grammarCheck(@RequestParam String sentence){
        return grammarCorrectionManagement.correctData(sentence);
    }
    
    @RequestMapping(value="synonym", method = RequestMethod.POST)
    public void synonymCheck(@RequestParam String text) {
    	 synonymManagement.suggestWordProcess(text);
    }

    @RequestMapping(value = "addition", method = RequestMethod.POST)
    @ResponseBody
    public String addRule(@RequestBody String ruleContent) {
        return grammarCorrectionManagement.addRule(ruleContent);
    }

    @RequestMapping(value = "summarize", method = RequestMethod.POST)
    @ResponseBody
    public SummarizeResult getSummarize(@RequestBody String finalResult){
        return grammarCorrectionManagement.getSummarize(finalResult);
    }

    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR, reason="Network")
    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception exception) {
        logger.error("Request: " + req.getRequestURL() + " raised " + exception);

        ModelAndView mav = new ModelAndView();
        mav.addObject("messages", "Troubles");
        mav.setViewName("error");
        return mav;
    }
}
