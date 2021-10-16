/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blacksilicon.lackey;

/**
 *
 * @author stilesma
 */
public class Response {

    private boolean OK;
    private int code;
    private String message;
    private String body;

    public Response(int code) {
        this.code = code;
        this.OK = code >= 200 && code < 400;
    }

    public Response(int code, String body) {
        this.code = code;
        this.body = body;

        this.OK = code >= 200 && code < 400;
    }

    public Response(int code, String message, String body) {
        this.code = code;
        this.message = message;
        this.body = body;

        this.OK = code >= 200 && code < 400;
    }

    public boolean isOK() {
        return OK;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setOK(boolean oK) {
        this.OK = oK;
    }

}
