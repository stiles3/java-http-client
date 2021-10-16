// 
// Decompiled by Procyon v0.5.36
// 
package com.blacksilicon.lackey;

import java.util.Map;
import java.net.HttpURLConnection;

public class Request extends AbstractRequest {


    @Override
    public HttpURLConnection headers(final HttpURLConnection conn, final Map<String, String> config) {
        conn.setDoOutput(true);
        config.forEach((key, value) -> conn.setRequestProperty(key, value));
        conn.setConnectTimeout(60000);
        conn.setReadTimeout(60000);
        return conn;
    }
}
