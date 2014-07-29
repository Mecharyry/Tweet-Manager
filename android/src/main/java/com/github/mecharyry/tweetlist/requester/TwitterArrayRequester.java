package com.github.mecharyry.tweetlist.requester;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TwitterArrayRequester implements Request<JSONArray> {

    private static final String TAG = "TwitterArrayRequester";

    @Override
    public JSONArray request(String signedUrl) {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(signedUrl);

        try {
            HttpResponse response = client.execute(get);

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                Log.i(TAG, "Retrieved Response!");
                InputStream inputStream = entity.getContent();
                String inputStreamString = inputStreamToString(inputStream);
                return convertStringToJson(inputStreamString);
            }
        } catch (ClientProtocolException e) {
            Log.e(TAG, "ClientProtocolException", e);
        } catch (IOException e) {
            Log.e(TAG, "IOException", e);
        }

        return new JSONArray();
    }

    private static String inputStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStream.close();
        } catch (IOException e) {
            Log.e(TAG, "IOException", e);
        }
        return stringBuilder.toString();
    }

    private static JSONArray convertStringToJson(String input) {
        try {
            return new JSONArray(input);
        } catch (JSONException e) {
            Log.e(TAG, "JSONException", e);
        }
        return new JSONArray();
    }

}
