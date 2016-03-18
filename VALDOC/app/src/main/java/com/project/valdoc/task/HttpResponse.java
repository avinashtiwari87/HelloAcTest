package com.project.valdoc.task;

/**
 * Created by Avinash on 3/18/2016.
 */
public class HttpResponse {
    private int statusCode;
    private String result;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
