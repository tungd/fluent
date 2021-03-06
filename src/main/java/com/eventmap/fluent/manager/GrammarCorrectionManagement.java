package com.eventmap.fluent.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.eventmap.fluent.domain.Result;
import org.apache.log4j.Logger;
import org.languagetool.JLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.patterns.AbstractPatternRule;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

import com.eventmap.fluent.domain.Match;
import com.eventmap.fluent.domain.Matches;
import com.eventmap.fluent.domain.SummarizeResult;
import com.eventmap.fluent.domain.json.Rules;
import com.eventmap.fluent.utils.JSONUtil;
import com.eventmap.fluent.utils.StaticStrings;
import com.eventmap.fluent.utils.XMLUtil;

/**
 * Created by huytran on 4/9/16.
 */
@Component
@EnableAutoConfiguration
public class GrammarCorrectionManagement {

    private static final Logger logger = Logger.getLogger(GrammarCorrectionManagement.class);

    private static JLanguageTool jLanguageTool;

    public static synchronized Matches correctData(String inputString) throws Exception{
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
            int position = inputString.substring(0, ruleMatch.getColumn()).split(" ").length - 1;
            match.setPosition(position);
            lsMatch.add(match);
        }
        matches.setMatches(lsMatch);

        return matches;
    }

    private static void loadCustomRules() throws IOException{

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

    public SummarizeResult getSummarize(String finalResult, int speechTime) throws Exception{

        if (finalResult.length() != 0) {
            finalResult = finalResult.toUpperCase().charAt(0) + finalResult.substring(1);
        }

        Matches matches = new Matches();
        jLanguageTool = new JLanguageTool(new BritishEnglish());

        loadCustomRules();
        List<RuleMatch> lsRuleMatch = jLanguageTool.check(finalResult);

        int totalWords = finalResult.split(" ").length;
        int incorrectWords = lsRuleMatch.size();
        int correctWords = totalWords - incorrectWords;

        float speedAverage = totalWords * 1.0f / speechTime * 1000 * 60;

        SummarizeResult summarizeResult = new SummarizeResult();
        summarizeResult.setTotalWords(totalWords);
        summarizeResult.setCorrectWords(correctWords);
        summarizeResult.setIncorrectWords(incorrectWords);
        summarizeResult.setSpeechSpeedAverage(speedAverage);

        return summarizeResult;
    }

    public Result getResult() throws Exception{
        JSONUtil jsonUtil = new JSONUtil();
        return jsonUtil.readJSONfromResultFile();
    }

}
