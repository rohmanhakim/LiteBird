package com.rohmanhakim.litebird;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
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

public class FeedsActivity extends AppCompatActivity {

    TwitterApiClient twitterApiClient;
    StatusesService statusesService;
    RecyclerView listFeeds;
    FeedsAdapter feedsAdapter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        feedsAdapter = new FeedsAdapter(new ArrayList<Tweet>(),FeedsActivity.this);
        listFeeds = (RecyclerView) findViewById(R.id.listFeeds);
        listFeeds.setLayoutManager(new LinearLayoutManager(this));
        listFeeds.setAdapter(feedsAdapter);

        twitterApiClient = TwitterCore.getInstance().getApiClient();
        statusesService = twitterApiClient.getStatusesService();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Feeds");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgress(0);
        progressDialog.setProgressNumberFormat(null);
        progressDialog.setProgressPercentFormat(null);
        progressDialog.show();

        statusesService.homeTimeline(200,null,null,null,true,null,null).enqueue(new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                progressDialog.dismiss();
                Toast.makeText(FeedsActivity.this,String.valueOf(result.data.size()),Toast.LENGTH_SHORT).show();
                feedsAdapter.setFeeds(result.data);
            }

            @Override
            public void failure(TwitterException exception) {
                progressDialog.dismiss();
                Toast.makeText(FeedsActivity.this,exception.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent homeIntent = new Intent(this, MainActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
