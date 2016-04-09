package com.eventmap.fluent.domain.json;

import java.util.List;

/**
 * Created by huytran on 4/9/16.
 */
public class Rule
{
    private List<String> message;

    private String id;

    private Pattern pattern;

    private String name;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private List<String> example;

    public List<String> getMessage ()
    {
        return message;
    }

    public void setMessage (List<String> message)
    {
        this.message = message;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public Pattern getPattern ()
    {
        return pattern;
    }

    public void setPattern (Pattern pattern)
    {
        this.pattern = pattern;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public List<String> getExample ()
    {
        return example;
    }

    public void setExample (List<String> example)
    {
        this.example = example;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", id = "+id+", pattern = "+pattern+", name = "+name+", example = "+example+"]";
    }
}
