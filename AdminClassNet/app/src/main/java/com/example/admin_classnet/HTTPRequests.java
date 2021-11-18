package com.example.admin_classnet;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
}
