package com.eventmap.fluent.manager;

import com.eventmap.fluent.domain.Match;
import com.eventmap.fluent.domain.Matches;

import com.eventmap.fluent.domain.ResultHub;
import com.eventmap.fluent.domain.SummarizeResult;
import com.eventmap.fluent.domain.json.Rules;

import com.eventmap.fluent.exception.FluentException;
import com.eventmap.fluent.utils.JSONUtil;
import com.eventmap.fluent.utils.StaticStrings;
import com.eventmap.fluent.utils.XMLUtil;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.patterns.AbstractPatternRule;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by huytran on 4/9/16.
 */
@Component
@EnableAutoConfiguration
public class GrammarCorrectionManagement {

    private static final Logger logger = Logger.getLogger(GrammarCorrectionManagement.class);

    private JLanguageTool jLanguageTool;

    public Matches correctData(String inputString) throws Exception{
        // uppercase first character of inputString
        inputString = inputString.toUpperCase().charAt(0) +  inputString.substring(1);

        Matches matches = new Matches();
        jLanguageTool = new JLanguageTool(new BritishEnglish());

        loadCustomRules();
        List<RuleMatch> lsRuleMatch = jLanguageTool.check(inputString);
        List<Match> lsMatch = new ArrayList<Match>();

        for (RuleMatch ruleMatch : lsRuleMatch) {
            Match match = new Match();
            match.setMessages(ruleMatch.getMessage());
            match.setCorrection(ruleMatch.getSuggestedReplacements());
            match.setPosition(String.valueOf(ruleMatch.getColumn()));
            lsMatch.add(match);
        }
        matches.setMatches(lsMatch);

        return matches;
    }

    private void loadCustomRules() throws IOException{

        List<AbstractPatternRule> lsApbstractPatternRule = jLanguageTool.loadPatternRules(StaticStrings.RULES_PATH);
        for (AbstractPatternRule patternRule : lsApbstractPatternRule) {
            patternRule.setDefaultOn();
            jLanguageTool.addRule(patternRule);
        }
    }

    public String addRule(String inputString) throws Exception{

        Rules inputRule = JSONUtil.marshallRules(inputString);
        XMLUtil xmlUtil = new XMLUtil();
        String result = xmlUtil.readXMLRuleFile();
        xmlUtil.checkForUpdateNewRule(result, inputRule);

        return "Your rule is successfully added to library";
    }

    public SummarizeResult getSummarize(String finalResult){
        SummarizeResult summarizeResult = new SummarizeResult();
        return summarizeResult;
    }

    public ResultHub getResult() throws Exception{
        JSONUtil jsonUtil = new JSONUtil();
        return jsonUtil.readJSONfromResultFile();
    }
}
