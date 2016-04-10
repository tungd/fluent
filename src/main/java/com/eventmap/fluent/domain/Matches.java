package com.eventmap.fluent.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huytran on 4/9/16.
 */
public class Matches implements Serializable {

    private List<Match> matches = new ArrayList<Match>();

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }
}
