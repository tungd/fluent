package com.eventmap.fluent.utils;

import com.eventmap.fluent.domain.json.Rules;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Created by huytran on 4/9/16.
 */
public class JSONUtil {

    public static Rules marshallRules(String inputString) throws JsonGenerationException, JsonMappingException, IOException{

        ObjectMapper mapper = new ObjectMapper();
        Rules rules = mapper.readValue(inputString, Rules.class);

        return rules;
    }
}
