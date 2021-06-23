package com.test.util;

import android.os.AsyncTask;

import com.test.util.interfaces.DBUtilAsyncTaskImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetItemAsyncTask  extends AsyncTask<String, Void, JSONObject> {


    private DBUtilAsyncTaskImpl asyncTaskInterface;
    private String strDbUrl;
    private OkHttpClient client;
    private RequestBody body;
    private Request request;
    private Response response;
    private JSONObject jsonResponse;

    public GetItemAsyncTask(DBUtilAsyncTaskImpl asyncTaskInterface, String strDbUrl) {
        this.asyncTaskInterface = asyncTaskInterface;
        this.strDbUrl = strDbUrl;
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected JSONObject doInBackground(String... strings) {

         client = new OkHttpClient().newBuilder()
                .build();
         request = new Request.Builder()
                .url("https://en.wikipedia.org//w/api.php?action=query&format=json&prop=pageimages%7Cpageterms&generator=prefixsearch&redirects=1&formatversion=2&piprop=thumbnail&pithumbsize=50&pilimit=10&wbptterms=description&gpssearch=Sachin+T&gpslimit=10")
                .method("GET", null)
                .addHeader("Cookie", "GeoIP=IN:KA:Bijapur:16.83:75.72:v4; WMF-Last-Access-Global=23-Jun-2021; WMF-Last-Access=23-Jun-2021")
                .build();

        try {
            response = client.newCall(request).execute();
            jsonResponse = new JSONObject(response.body().string());

            return jsonResponse;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    @Override
    public void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
        asyncTaskInterface.onBackgroundTaskCompleted(result);
    }
}
