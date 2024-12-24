// 
// Decompiled by Procyon v0.5.36
// 
package com.blacksilicon.lackey;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

abstract class AbstractRequest {

    public abstract HttpURLConnection headers(HttpURLConnection p0, Map<String, String> p1);

    public Response post(String url, String body, Map<String, String> config) throws IOException {
        final URL urlObject = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urlObject.openConnection();
        int timeout = config.get("timeout") != null ? Integer.parseInt(config.get("timeout")) : 30000;
        System.out.println("Caliing with " + timeout + " timeout");
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);
        config.remove("timeout");
        conn = this.headers(conn, config);

        conn.setRequestMethod("POST");
        final OutputStream os = conn.getOutputStream();
        os.write(body.getBytes());
        os.flush();
        return responseHandler(conn);
    }

    public Response post(String url, File file, Map<String, String> config) throws IOException {
        try {
            trustAllCertificates();
        } catch (Exception ex) {
            Logger.getLogger(AbstractRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        final URL urlObject = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urlObject.openConnection();
        int timeout = config.get("timeout") != null ? Integer.parseInt(config.get("timeout")) : 30000;
        System.out.println("Caliing with " + timeout + " timeout");
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);
        config.remove("timeout");
        conn = this.headers(conn, config);

        conn.setRequestMethod("POST");
        try ( OutputStream os = conn.getOutputStream()) {
            byte[] xmlData = Files.readAllBytes(file.toPath());
            os.write(xmlData);
            os.flush();
            System.out.println(conn.getResponseCode());
            System.out.println(conn.getResponseMessage().concat(" message"));
        }

        return responseHandler(conn);
    }

    public Response postXml(String url, String xml, Map<String, String> config) throws IOException {
        try {
            trustAllCertificates();
        } catch (Exception ex) {
            Logger.getLogger(AbstractRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        final URL urlObject = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urlObject.openConnection();
        int timeout = config.get("timeout") != null ? Integer.parseInt(config.get("timeout")) : 30000;
        System.out.println("Caliing with " + timeout + " timeout");
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);
        config.remove("timeout");
        conn = this.headers(conn, config);

        conn.setRequestMethod("POST");
        try ( OutputStream os = conn.getOutputStream()) {

            os.write(xml.getBytes());
            os.flush();
            System.out.println(conn.getResponseCode());
            System.out.println(conn.getResponseMessage().concat(" message"));
        }

        return responseHandler(conn);
    }

    public Response put(final String url, final String body, final Map<String, String> config) throws IOException {
        final URL urlObject = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urlObject.openConnection();
        int timeout = config.get("timeout") != null ? Integer.parseInt(config.get("timeout")) : 30000;
        System.out.println("Caliing with " + timeout + " timeout");
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);
        conn = this.headers(conn, config);
        config.remove("timeout");
        conn.setRequestMethod("PUT");
        final OutputStream os = conn.getOutputStream();
        os.write(body.getBytes());
        os.flush();
        return responseHandler(conn);
    }

    public Response get(final String url, final Map<String, String> config) throws MalformedURLException, IOException {
        final URL urlObject = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urlObject.openConnection();
        int timeout = config.get("timeout") != null ? Integer.parseInt(config.get("timeout")) : 30000;
        System.out.println("Caliing with " + timeout + " timeout");
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);
        conn = this.headers(conn, config);
        config.remove("timeout");
        conn.setRequestMethod("GET");
        return responseHandler(conn);
    }

    public Response delete(final String url, final Map<String, String> config) throws MalformedURLException, IOException {
        final URL urlObject = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urlObject.openConnection();
        int timeout = config.get("timeout") != null ? Integer.parseInt(config.get("timeout")) : 30000;
        System.out.println("Caliing with " + timeout + " timeout");
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);
        conn = this.headers(conn, config);
        config.remove("timeout");
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
        } else {
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

    public void trustAllCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
    }
}
