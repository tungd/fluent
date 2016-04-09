package com.eventmap.fluent.manager;

import com.eventmap.fluent.domain.Match;
import com.eventmap.fluent.domain.Matches;
import org.apache.log4j.Logger;
import org.apache.log4j.net.SyslogAppender;
import org.jetbrains.annotations.TestOnly;
import org.junit.Assert;
import org.junit.Test;
import org.languagetool.AnalyzedSentence;
import org.languagetool.JLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.Rule;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.patterns.AbstractPatternRule;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huytran on 4/9/16.
 */
@Component
@EnableAutoConfiguration
public class GrammarCorrectionManagement {

    private static final Logger logger = Logger.getLogger(GrammarCorrectionManagement.class);

    public Matches correctData(String inputString){
        // uppercase first character of inputString
        inputString = inputString.toUpperCase().charAt(0) +  inputString.substring(1);

        Matches matches = new Matches();
        JLanguageTool jLanguageTool = new JLanguageTool(new BritishEnglish());
        try {
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
        }catch(Exception e){
            e.printStackTrace();
        }
        return matches;
    }

    public void addRule(){

        JLanguageTool jLanguageTool = new JLanguageTool(new BritishEnglish());
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            List<AbstractPatternRule> lsApbstractPatternRule = jLanguageTool.loadPatternRules("/Users/huytran/Documents/workspace/fluent/src/main/resources/grammar.xml");
            for (AbstractPatternRule patternRule : lsApbstractPatternRule) {
                    patternRule.setDefaultOn();
                jLanguageTool.addRule(patternRule);
            }
            List<Rule> lsRule = jLanguageTool.getAllRules();
            for (Rule rule : lsRule){
                if (rule.getId().equals("ABC")){
                    System.out.print("abc");
                }
            }
        }catch(Exception e){

        }
    }

    @Test
    public void abc(){
        GrammarCorrectionManagement grammarCorrectionManagement = new GrammarCorrectionManagement();
        grammarCorrectionManagement.addRule();
        Assert.assertEquals(true, true);
    }

}
