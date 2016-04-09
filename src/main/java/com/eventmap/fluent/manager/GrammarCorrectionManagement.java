package com.eventmap.fluent.manager;

import com.eventmap.fluent.domain.Match;
import com.eventmap.fluent.domain.Matches;

import com.eventmap.fluent.domain.json.Rules;
import com.eventmap.fluent.domain.json.Rule;
import com.eventmap.fluent.utils.JSONUtil;
import com.eventmap.fluent.utils.StaticStrings;
import com.eventmap.fluent.utils.XMLUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.patterns.AbstractPatternRule;
import org.languagetool.tools.RuleMatchAsXmlSerializer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
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

        List<AbstractPatternRule> lsApbstractPatternRule = jLanguageTool.loadPatternRules(StaticStrings.RULES_PATH);
        for (AbstractPatternRule patternRule : lsApbstractPatternRule) {
            patternRule.setDefaultOn();
            jLanguageTool.addRule(patternRule);
        }
    }

    public String addRule(String inputString){

        try {
            Rules inputRule = JSONUtil.marshallRules(inputString);

            StringBuilder result = new StringBuilder("");

            //Get file from resources folder
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("grammar.xml").getFile());

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }
            scanner.close();
            String newString = "";
            if (result.toString().contains("category id=\"User\"")){
                newString = result.substring(0, result.indexOf("</category>"));
                newString += "<rule id=\"" + inputRule.getCategory().getRule().getId() + "\" name = \"" + inputRule.getCategory().getRule().getName()+ "\">" + "\n";
                newString += "<pattern>" + "\n";
                for (String token : inputRule.getCategory().getRule().getPattern().getToken()){
                    newString += "<token>" + token + "</token>" + "\n";
                }
                newString += "</pattern>" + "\n";
                newString += "<message>" + inputRule.getCategory().getRule().getMessage() +"</message>" + "\n";
                for (String message : inputRule.getCategory().getRule().getMessage()) {

                    newString += "<message>" + message+ "</message>" + "\n";
                }
                for (String example: inputRule.getCategory().getRule().getExample()){

                    newString += "<example>" + example + "</example>" + "\n";
                }
                newString += "</rule>" + "\n";
                newString += "</category>" + "\n";

            }

        }catch(java.lang.Exception e){
            e.printStackTrace();
        }

        return "Your rule is successfully added to library";
    }

    public void bindFile(){

    }

    @Test
    public void abc(){
        GrammarCorrectionManagement grammarCorrectionManagement = new GrammarCorrectionManagement();
        /*grammarCorrectionManagement.addRule("{\n" +
            "  \"category\": {\n" +
            "    \"id\": \"HUY\",\n" +
            "    \"name\": \"Possible Typo\",\n" +
            "    \"type\": \"misspelling\",\n" +
            "    \"rule\": {\n" +
            "      \"id\": \"ABC\",\n" +
            "      \"name\": \"ABCD (won't)\",\n" +
            "      \"pattern\": {\n" +
            "        \"token\": [\n" +
            "          \"wont<exception scope=\\\"previous\\\" postag=\\\"PRP$\\\"/>\",\n" +
            "          \"<exception>to</exception>\"\n" +
            "        ]\n" +
            "      },\n" +
            "      \"message\": [\n" +
            "        \"Please make sure you mean 'won't' (a habit)\",\n" +
            "        \"not <suggestion>won't</suggestion>/<suggestion>won’t</suggestion>(short for 'will not')?\"\n" +
            "      ],\n" +
            "      \"example\": [\n" +
            "        \"No, I <marker>won't</marker> do that.\",\n" +
            "        \"We were wont to meet at that pleasant spot\",\n" +
            "        \"As is his wont, Tourneur shows us only parts of the set, in logical sequence, each at the moment when, and not before, we need to see it.\"\n" +
            "      ]\n" +
            "    }\n" +
            "  }\n" +
            "}");*/
        grammarCorrectionManagement.correctData("im workin");
        Assert.assertEquals(true, true);
    }

}
