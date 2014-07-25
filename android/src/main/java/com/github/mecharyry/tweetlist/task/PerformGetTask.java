package com.github.mecharyry.tweetlist.task;

import android.os.AsyncTask;

import com.github.mecharyry.tweetlist.JsonParsing;
import com.github.mecharyry.tweetlist.TwitterRequester;
import com.github.mecharyry.tweetlist.adapter.mapping.Tweet;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

public class PerformGetTask extends AsyncTask<String, Void, List<Tweet>> {

    private final WeakReference<Callback> callbackWeakReference;

    public interface Callback {
        void onGetResponse(List<Tweet> tweets);
    }

    public PerformGetTask(Callback callback) {
        callbackWeakReference = new WeakReference<Callback>(callback);
    }

    @Override
    protected List<Tweet> doInBackground(String... urls) {
        JSONObject jsonObject = TwitterRequester.newInstance().request(urls[0]);
        return JsonParsing.newInstance().TweetsByHashTag(jsonObject);
    }

    @Override
    protected void onPostExecute(List<Tweet> response) {
        super.onPostExecute(response);
        Callback callback = callbackWeakReference.get();
        if (callback != null) {
            callback.onGetResponse(response);
        }
    }
}