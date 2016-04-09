package com.eventmap.fluent.manager;

import com.eventmap.fluent.domain.Match;
import com.eventmap.fluent.domain.Matches;
import com.eventmap.fluent.utils.XMLUtil;
import org.apache.log4j.Logger;
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

    private JLanguageTool jLanguageTool;

    public Matches correctData(String inputString){
        // uppercase first character of inputString
        inputString = inputString.toUpperCase().charAt(0) +  inputString.substring(1);

        Matches matches = new Matches();
        jLanguageTool = new JLanguageTool(new BritishEnglish());

        try {
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
        }catch(IOException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        return matches;
    }

    private void loadCustomRules() throws  IOException{
        ClassLoader classLoader = getClass().getClassLoader();
        List<AbstractPatternRule> lsApbstractPatternRule = jLanguageTool.loadPatternRules("/Users/huytran/Documents/workspace/fluent/src/main/resources/grammar.xml");
        for (AbstractPatternRule patternRule : lsApbstractPatternRule) {
            patternRule.setDefaultOn();
            jLanguageTool.addRule(patternRule);
        }
    }

    public String addRule(){
        String abc = "";

        XMLUtil xmlUtil = new XMLUtil();

        return "Your rule is successfully added to library";
//        if (xmlUtil!= null){
//            return "Your rule is succesfully added to library";
//        }else{
//            return "Trouble";
//        }

    }

    @Test
    public void abc(){
        GrammarCorrectionManagement grammarCorrectionManagement = new GrammarCorrectionManagement();
        grammarCorrectionManagement.addRule();
        Assert.assertEquals(true, true);
    }

}
