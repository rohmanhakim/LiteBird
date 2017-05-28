package com.rohmanhakim.litebird;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class FeedsActivity extends AppCompatActivity {

    TwitterApiClient twitterApiClient;
    StatusesService statusesService;
    RecyclerView listFeeds;
    FeedsAdapter feedsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);

        feedsAdapter = new FeedsAdapter(new ArrayList<Tweet>());
        listFeeds = (RecyclerView) findViewById(R.id.listFeeds);
        listFeeds.setLayoutManager(new LinearLayoutManager(this));
        listFeeds.setAdapter(feedsAdapter);

        twitterApiClient = TwitterCore.getInstance().getApiClient();
        statusesService = twitterApiClient.getStatusesService();

        statusesService.homeTimeline(200,null,null,null,true,null,null).enqueue(new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                Toast.makeText(FeedsActivity.this,String.valueOf(result.data.size()),Toast.LENGTH_SHORT).show();
                feedsAdapter.setFeeds(result.data);
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(FeedsActivity.this,exception.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
