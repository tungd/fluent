package com.eventmap.fluent.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huytran on 4/9/16.
 */
public class Match implements Serializable {

    private String position;

    private String messages;

    private List<String> correction;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public List<String> getCorrection() {
        return correction;
    }

    public void setCorrection(List<String> correction) {
        this.correction = correction;
    }
}
