package com.test.util;

import android.os.AsyncTask;

import com.test.util.interfaces.DBUtilAsyncTaskImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

public class DBUtilAsyncTask extends AsyncTask<String, Void, JSONObject> {

    DBUtilAsyncTaskImpl dbUtilAsyncTaskImpl;
    JSONObject response;
    Map<String, String> reqParams;
    BufferedReader reader = null;
    HttpURLConnection httpConn = null;
    URL url = null;
    String postData;
    InputStream inputStream = null;

    public DBUtilAsyncTask(Map<String, String> reqParams, DBUtilAsyncTaskImpl dbUtilAsyncTaskImpl) {
        this.reqParams = reqParams;
        this.dbUtilAsyncTaskImpl = dbUtilAsyncTaskImpl;
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected JSONObject doInBackground(String... params) {
        try {
            postData = ParameterStringBuilder.getParamsString(reqParams, false);

            url = new URL(StringConstants.DB_URL + "?" + postData);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.setUseCaches(false);
            httpConn.connect();
        } catch(MalformedURLException e) {
            e.printStackTrace();
        } catch(ProtocolException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }

        try {
            inputStream = httpConn.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            if (buffer.length() == 0) {
                return null;
            }
            response = new JSONObject(buffer.toString());
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch(JSONException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                }
            }
        }
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
        dbUtilAsyncTaskImpl.onBackgroundTaskCompleted(result);
    }

}
