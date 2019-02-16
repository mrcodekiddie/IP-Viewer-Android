package com.foss.ipinfo;

import java.io.Serializable;

public class IpInfo implements Serializable
{

    public String ip;
    public String city;
    public String region;
    public String country;
    public String loc;
    public String postal;
    public String org;
    private final static long serialVersionUID = -5634526491357658790L;

}