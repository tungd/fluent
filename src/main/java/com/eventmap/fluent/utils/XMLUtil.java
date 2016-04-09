package com.eventmap.fluent.utils;

import com.eventmap.fluent.domain.json.Rules;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
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

    public void write2XMLRuleFile(String result, Rules inputRule) throws Exception{

        String newString = "";
        if (result.toString().contains("category id=\"UserRule\"")){
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
            newString += "</>" + "\n";
        }

        PrintWriter writer =
            new PrintWriter(
                new File(StaticStrings.RULES_PATH));
        writer.write(newString);
        writer.close();

    }
}
