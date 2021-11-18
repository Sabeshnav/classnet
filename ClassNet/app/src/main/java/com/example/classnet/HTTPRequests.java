package com.example.classnet;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HTTPRequests
{
    public OkHttpClient client = new OkHttpClient();
    public Request request;

    public Request getRequest(String url)
    {
        return new Request.Builder()
                .url(url)
                .build();
    }
    public Request getRequest1(String url, String header_name, String header_value)
    {
        return new Request.Builder()
                .get()
                .url(url)
                .addHeader(header_name, header_value)
                .build();
    }
    public Request getRequest2(String url, String header_name1, String header_value1, String header_name2, String header_value2)
    {
        return new Request.Builder()
                .get()
                .url(url)
                .addHeader(header_name1, header_value1)
                .addHeader(header_name2, header_value2)
                .build();
    }
    public Request delete(String url)
    {
        return new Request.Builder()
                .delete()
                .url(url)
                .build();
    }
    public Request delete1(String url, String header_name, String header_value)
    {
        return new Request.Builder()
                .delete()
                .addHeader(header_name, header_value)
                .url(url)
                .build();
    }
    public Request post(String url, String json)
    {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }
    public Request put(String url, String json)
    {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        return request;
    }
}
