package com.eventmap.fluent.domain.json;

/**
 * Created by huytran on 4/9/16.
 */
public class Rules {
    private Category category;

    public Category getCategory ()
    {
        return category;
    }

    public void setCategory (Category category)
    {
        this.category = category;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [category = "+category+"]";
    }
}
