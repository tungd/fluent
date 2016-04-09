package com.eventmap.fluent.manager;

import com.eventmap.fluent.domain.Match;
import com.eventmap.fluent.domain.Matches;
import org.languagetool.JLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huytran on 4/9/16.
 */
@Component
@EnableAutoConfiguration
public class GrammarCorrectionManagement {

    public Matches correctData(String inputString){
        Matches matches = new Matches();

        JLanguageTool langTool = new JLanguageTool(new BritishEnglish());
        try {
            //"A sentence with a error in the Hitchhiker's Guide tot he Galaxy"
            List<RuleMatch> lsRuleMatch = langTool.check(inputString);
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

}
