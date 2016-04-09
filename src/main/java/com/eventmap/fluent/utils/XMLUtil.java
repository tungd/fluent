package com.eventmap.fluent.utils;

import com.eventmap.fluent.domain.jaxb.Rules;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by huytran on 4/9/16.
 */
public class XMLUtil {

    public static Rules UnMarshalRulesFile() throws JAXBException{
        Rules rules;

        File file = new File(StaticStrings.RULES_PATH);
        JAXBContext jaxbContext = JAXBContext.newInstance(Rules.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        rules = (Rules) jaxbUnmarshaller.unmarshal(file);

        return rules;
    }

}
