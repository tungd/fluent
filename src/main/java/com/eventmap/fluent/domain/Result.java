package com.eventmap.fluent.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huytran on 4/10/16.
 */
public class Result {
    List<ResultHub> resultHub = new ArrayList<ResultHub>();

    public List<ResultHub> getResultHub() {
        return resultHub;
    }

    public void setResultHub(List<ResultHub> resultHub) {
        this.resultHub = resultHub;
    }
}
