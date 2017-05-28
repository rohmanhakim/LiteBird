package com.rohmanhakim.litebird;

import android.app.Application;
import android.util.Log;

import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

public class LiteBirdApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(
                        new TwitterAuthConfig(
                                getResources().getString(R.string.twitter_consumer_key),
                                getResources().getString(R.string.twitter_consumer_secret)
                        )
                )
                .debug(true)
                .build();
        Twitter.initialize(config);
    }
}
