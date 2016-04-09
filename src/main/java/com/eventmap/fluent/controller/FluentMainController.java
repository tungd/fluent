package com.eventmap.fluent.controller;

import com.eventmap.fluent.domain.Matches;
import com.eventmap.fluent.manager.GrammarCorrectionManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "correction"/*, method = RequestMethod.POST*/)
    @ResponseBody
    public Matches grammarCheck(@RequestParam String sentence){
        return grammarCorrectionManagement.correctData(sentence);
    }

}
