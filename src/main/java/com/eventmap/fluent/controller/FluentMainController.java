package com.eventmap.fluent.controller;

import com.eventmap.fluent.domain.*;
import com.eventmap.fluent.domain.ResultHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eventmap.fluent.manager.GrammarCorrectionManagement;
import com.eventmap.fluent.manager.SynonymSuggestionManager;
import com.eventmap.fluent.domain.Matches;
import com.eventmap.fluent.exception.FluentException;
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
        try {
            return grammarCorrectionManagement.correctData(sentence);
        }catch (Exception e){
            throw new FluentException();
        }
    }

    @RequestMapping(value="synonym", method = RequestMethod.POST)
    @ResponseBody
    public Matches synonymCheck(@RequestParam String text) {
    	Matches matchList = synonymManagement.suggestWordProcess(text);
    	return matchList;
    }

    @RequestMapping(value = "addition", method = RequestMethod.POST)
    @ResponseBody
    public String addRule(@RequestBody String ruleContent) {
        try {
            return grammarCorrectionManagement.addRule(ruleContent);
        }catch(Exception e){
            throw new FluentException();
        }
    }

    @RequestMapping(value = "summarize")
    @ResponseBody
    public SummarizeResult getSummarize(@RequestParam String text, @RequestParam int duration){
        try {
            return grammarCorrectionManagement.getSummarize(text, duration);
        }catch(Exception e){
            throw new FluentException();
        }
    }

    @RequestMapping(value = "getResult")
    @ResponseBody
    public Result getResult(){
        try {
            return grammarCorrectionManagement.getResult();
        }catch(Exception e){
            throw new FluentException();
        }
    }

}
