package com.eventmap.fluent.utils;

import com.eventmap.fluent.domain.Result;
import com.eventmap.fluent.domain.ResultHub;
import com.eventmap.fluent.domain.SummarizeResult;
import com.eventmap.fluent.domain.json.Rules;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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

        Result result = readJSONfromResultFile();
        List<ResultHub> lsResultHub = result.getResultHub();
        lsResultHub.add(resulthub);
        result.setResultHub(lsResultHub);

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(result);

        File file =new File(StaticStrings.JSON_PATH);
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter fileWritter = new FileWriter(StaticStrings.JSON_PATH);
        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
        bufferWritter.write(jsonInString);
        bufferWritter.close();
    }

    public SummarizeResult readJSONFromSummarize(String inputString) throws Exception{

        ObjectMapper mapper = new ObjectMapper();
        // Convert JSON string from file to Object
        SummarizeResult summarizeResult = mapper.readValue(inputString, SummarizeResult.class);

        return summarizeResult;
    }

    public Result readJSONfromResultFile() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
         // Convert JSON string from file to Object
        Result result = mapper.readValue(new File(StaticStrings.JSON_PATH), Result.class);
        return result;

    }
}
