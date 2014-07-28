package com.github.mecharyry.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.mecharyry.R;
import com.github.mecharyry.tweetlist.AndroidDevTweetsActivity;

public class AuthenticationActivity extends Activity {

    private static final String TAG = "AuthenticationActivity";
    private AuthenticationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = AuthenticationManager.newInstance(this, onAccessTokenSaved);

        setContentView(R.layout.authentication_activity);
        findViewById(R.id.button_authorize).setOnClickListener(onAuthorizeButtonClicked);
        findViewById(R.id.button_android_dev_tweets).setOnClickListener(onAndroidDevTweetsButtonClicked);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.authentication, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_clear_credentials:
                manager.removeAccessToken();
                setButtonsEnabled(manager.hasAccessToken());
                break;
            default:
                Log.e(TAG, "Menu item not handled.");

        }
        return true;
    }

    private void setButtonsEnabled(boolean hasAccess) {
        findViewById(R.id.button_android_dev_tweets).setEnabled(hasAccess);
        findViewById(R.id.button_my_stream).setEnabled(hasAccess);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        manager.onActivityResult(requestCode, resultCode, data);
    }

    private final View.OnClickListener onAuthorizeButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(AuthenticationActivity.this, "Opening Browser", Toast.LENGTH_SHORT).show();
            manager.authenticate();
        }
    };

    private final View.OnClickListener onAndroidDevTweetsButtonClicked = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(AuthenticationActivity.this, AndroidDevTweetsActivity.class);
            startActivity(intent);
        }
    };

    private final View.OnClickListener onMyStreamClicked = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

        }
    };

    private final AuthenticationManager.Callback onAccessTokenSaved = new AuthenticationManager.Callback() {

        @Override
        public void onAuthenticated() {
            setButtonsEnabled(manager.hasAccessToken());
        }
    };
}
