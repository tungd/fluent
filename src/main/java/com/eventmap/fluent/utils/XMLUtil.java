package com.eventmap.fluent.utils;

import com.eventmap.fluent.domain.json.Rules;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by huytran on 4/9/16.
 */
public class XMLUtil {

    public String readXMLRuleFile() throws IOException{
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

        return result.toString();
    }

    public String addRuleOnly(String result, Rules inputRule){

        String newString = result.substring(0, result.indexOf("</category>"));
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
        newString += "</rules>" + "\n";

        return newString;
    }

    public String addRuleAndCategory(String result, Rules inputRule){
        String newString = result.substring(0, result.indexOf("</rules>"));
        newString += "<category id=\""+ inputRule.getCategory().getId()
            +"\" name=\""+inputRule.getCategory().getName()
            +" type=\""+inputRule.getCategory().getType() +"\">";
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
        newString += "</rules>" + "\n";

        return newString;
    }

    public void checkForUpdateNewRule(String result, Rules inputRule) throws Exception{

        String categoryId = "category id=\"" + inputRule.getCategory().getId() +"\"";
        String categoryName = "name=\"" + inputRule.getCategory().getName() + "\"";
        String categoryType = "type=\""+ inputRule.getCategory().getType()+"\"";
        if (result.toString().contains(categoryId) && result.toString().contains(categoryName) && result.toString().contains(categoryType)){
            write2XMLRuleFile(addRuleOnly(result, inputRule));
        }else{
            write2XMLRuleFile(addRuleAndCategory(result, inputRule));
        }
    }

    public void write2XMLRuleFile(String newString) throws Exception{
        PrintWriter writer =
            new PrintWriter(
                new File(StaticStrings.RULES_PATH));
        writer.write(newString);
        writer.close();

    }
}
