// 
// Decompiled by Procyon v0.5.36
// 

package com.blacksilicon.lackey;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;
import java.net.HttpURLConnection;

abstract class AbstractRequest
{
    public abstract HttpURLConnection headers(final HttpURLConnection p0, final Map<String, String> p1);
    
    public Response post(final String url, final String body, final Map<String, String> config) throws IOException {
        final URL urlObject = new URL(url);
        HttpURLConnection conn = (HttpURLConnection)urlObject.openConnection();
        conn = this.headers(conn, config);
        conn.setRequestMethod("POST");
        final OutputStream os = conn.getOutputStream();
        os.write(body.getBytes());
        os.flush();
        return responseHandler(conn);
    }
    
     public Response put(final String url, final String body, final Map<String, String> config) throws IOException {
        final URL urlObject = new URL(url);
        HttpURLConnection conn = (HttpURLConnection)urlObject.openConnection();
        conn = this.headers(conn, config);
        conn.setRequestMethod("PUT");
        final OutputStream os = conn.getOutputStream();
        os.write(body.getBytes());
        os.flush();
        return responseHandler(conn);
    }
    
     public Response get(final String url, final Map<String, String> config) throws MalformedURLException, IOException {
        final URL urlObject = new URL(url);
        HttpURLConnection conn = (HttpURLConnection)urlObject.openConnection();
        conn = this.headers(conn, config);
        conn.setRequestMethod("GET");
        return responseHandler(conn);
    }
    
    public Response delete(final String url, final Map<String, String> config) throws MalformedURLException, IOException {
        final URL urlObject = new URL(url);
        HttpURLConnection conn = (HttpURLConnection)urlObject.openConnection();
        conn = this.headers(conn, config);
        conn.setRequestMethod("DELETE");
        return responseHandler(conn);
    }
    
    private Response responseHandler(final HttpURLConnection conn) throws IOException {
       StringBuilder resp = new StringBuilder();

		String output = "";
		BufferedReader br;
        System.out.println(conn.getResponseCode());
		Response response = new Response(conn.getResponseCode());

		if (response.isOK()) {
			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		}

		else {
			br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
		}

		while ((output = br.readLine()) != null) {
			resp.append(output);
		}

		conn.disconnect();
		String responseData = resp.toString();

		

		response.setBody(responseData);

		return response;
    }
}