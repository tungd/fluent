package com.eventmap.fluent.domain;

import java.io.Serializable;

/**
 * Created by huytran on 4/10/16.
 */
public class ResultHub implements Serializable{
    private String recordDate;
    private SummarizeResult summarizeResult;

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public SummarizeResult getSummarizeResult() {
        return summarizeResult;
    }

    public void setSummarizeResult(SummarizeResult summarizeResult) {
        this.summarizeResult = summarizeResult;
    }
}
