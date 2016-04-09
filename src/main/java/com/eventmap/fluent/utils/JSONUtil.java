package com.eventmap.fluent.utils;

import com.eventmap.fluent.domain.ResultHub;
import com.eventmap.fluent.domain.SummarizeResult;
import com.eventmap.fluent.domain.json.Rules;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
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

    public void writeJSONToResultFile(ResultHub resulthub) throws Exception{
        FileWriter file = new FileWriter("c:\\test.json");
        file.write(resulthub.toString());
        file.flush();
        file.close();
    }

    public SummarizeResult readJSONFromSummarize(String inputString) throws Exception{

        ObjectMapper mapper = new ObjectMapper();
        // Convert JSON string from file to Object
        SummarizeResult summarizeResult = mapper.readValue(inputString, SummarizeResult.class);

        return summarizeResult;
    }
}
