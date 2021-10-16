// 
// Decompiled by Procyon v0.5.36
// 

package com.blacksilicon.lackey;

import java.util.Map;
import com.google.gson.Gson;

public class Configuration
{
    private static final Gson gson;
    private static final Request req;
    
    public static String toBody(final Map<String, Object> payload) {
        return Configuration.gson.toJson((Object)payload);
    }
    
    public static Request request() {
        return Configuration.req;
    }
    
    static {
        gson = new Gson();
        req = new Request();
    }
}