package com.github.mecharyry.tweetlist;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.github.mecharyry.AccessTokenPreferences;
import com.github.mecharyry.R;
import com.github.mecharyry.auth.oauth.AccessToken;
import com.github.mecharyry.tweetlist.adapter.TweetAdapter;
import com.github.mecharyry.tweetlist.adapter.mapping.Tweet;
import com.github.mecharyry.tweetlist.task.PerformGetTask;

import java.util.List;

public class ListViewActivity extends Activity {

    private static final String TAG = "ListViewActivity";
    private TweetAdapter tweetArrayAdapter;
    private RequestManager requestManager;
    private ListView listView;

    private final PerformGetTask.Callback updateListCallback = new PerformGetTask.Callback() {
        @Override
        public void onGetResponse(List<Tweet> tweets) {
            tweetArrayAdapter.updateTweets(tweets);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AccessTokenPreferences accessTokenPreferences = AccessTokenPreferences.newInstance(this);
        AccessToken accessToken = accessTokenPreferences.retrieveAccessToken();
        requestManager = RequestManager.newInstance(accessToken);
        this.tweetArrayAdapter = TweetAdapter.newInstance(this);
        setContentView(R.layout.activity_list_view);
        listView = (ListView) findViewById(R.id.listview_tweets);
        listView.setAdapter(tweetArrayAdapter);
        requestManager.requestAndroidDevTweets(updateListCallback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}