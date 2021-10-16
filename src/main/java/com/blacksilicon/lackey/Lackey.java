/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blacksilicon.lackey;

import java.io.IOException;
import java.util.Map;

/**
 *
 * @author stilesma
 */
public class Lackey {

    public static Response POST(final String url, final Map<String, Object> body, final Map<String, String> config) throws IOException {
        return Configuration.request().post(url, Configuration.toBody(body), config);
    }

    public static Response POST(final String url, final String body, final Map<String, String> config) throws IOException {
        return Configuration.request().post(url, body, config);
    }

    public static Response GET(final String url, final Map<String, String> config) throws IOException {
        return Configuration.request().get(url, config);
    }

    public static Response PUT(final String url, final Map<String, Object> body, final Map<String, String> config) throws IOException {
        return Configuration.request().put(url, Configuration.toBody(body), config);
    }

    public static Response DELETE(final String url, final Map<String, String> config) throws IOException {
        return Configuration.request().delete(url, config);
    }
}
