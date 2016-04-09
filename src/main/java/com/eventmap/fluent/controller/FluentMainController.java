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

    @RequestMapping(value = "correction"/*, method = RequestMethod.POST*/)
    @ResponseBody
    public Matches grammarCheck(@RequestParam String sentence){
        return grammarCorrectionManagement.correctData(sentence);
    }
    
    @RequestMapping(value="synonym", method = RequestMethod.POST)
    public void synonymCheck(@RequestParam String text) {
    	 synonymManagement.suggestWordProcess(text);
    }

}
