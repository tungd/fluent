package com.eventmap.fluent.domain.json;

import java.util.List;

/**
 * Created by huytran on 4/9/16.
 */
public class Pattern
{
    private List<String> token;

    public List<String> getToken ()
    {
        return token;
    }

    public void setToken (List<String> token)
    {
        this.token = token;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [token = "+token+"]";
    }
}

