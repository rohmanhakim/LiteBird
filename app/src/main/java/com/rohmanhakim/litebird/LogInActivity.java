package com.rohmanhakim.litebird;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import static android.view.View.GONE;

public class LogInActivity extends AppCompatActivity {

    TwitterLoginButton twitterLoginButton;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(GONE);

        twitterLoginButton = (TwitterLoginButton) findViewById(R.id.btnLogin);
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {

            @Override
            public void success(Result<TwitterSession> result) {
                Toast.makeText(LogInActivity.this,result.data.getUserName(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LogInActivity.this,FeedsActivity.class);
                startActivity(intent);
                // Do something with result, which provides a TwitterSession for making API calls
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(LogInActivity.this,exception.getMessage(),Toast.LENGTH_SHORT).show();
                // Do something on failure
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }
}
