package com.foss.ipinfo;

import java.io.Serializable;

public class Data implements Serializable
{
    public Data(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String key;
    public String value;
}
