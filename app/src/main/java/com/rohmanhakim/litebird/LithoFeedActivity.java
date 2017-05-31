package com.rohmanhakim.litebird;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentInfo;
import com.facebook.litho.LithoView;
import com.facebook.litho.widget.LinearLayoutInfo;
import com.facebook.litho.widget.Recycler;
import com.facebook.litho.widget.RecyclerBinder;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;

import java.util.List;

public class LithoFeedActivity extends AppCompatActivity {

    TwitterApiClient twitterApiClient;
    StatusesService statusesService;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        final ComponentContext componentContext = new ComponentContext(this);

        final RecyclerBinder recyclerBinder = new RecyclerBinder(
                componentContext,
                new LinearLayoutInfo(this, OrientationHelper.VERTICAL, false));

        final Component component = Recycler.create(componentContext)
                .binder(recyclerBinder)
                .build();

        loadFeed(recyclerBinder, componentContext);

        setContentView(LithoView.create(componentContext, component));

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Feeds");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressNumberFormat(null);
        progressDialog.setProgressPercentFormat(null);
        progressDialog.setProgress(0);
        progressDialog.show();
    }

    private void loadFeed(final RecyclerBinder recyclerBinder, final ComponentContext componentContext) {
        twitterApiClient = TwitterCore.getInstance().getApiClient();
        statusesService = twitterApiClient.getStatusesService();

        statusesService.homeTimeline(200, null, null, null, true, null, null).enqueue(new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                progressDialog.dismiss();
                Toast.makeText(LithoFeedActivity.this, String.valueOf(result.data.size()), Toast.LENGTH_SHORT).show();
                addFeedResults(recyclerBinder, componentContext, result.data);
            }

            @Override
            public void failure(TwitterException exception) {
                progressDialog.dismiss();
                Toast.makeText(LithoFeedActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addFeedResults(RecyclerBinder recyclerBinder, ComponentContext componentContext, List<Tweet> feeds) {
        for (int i = 0; i < feeds.size(); i++) {
            ComponentInfo.Builder componentInfoBuilder = ComponentInfo.create();
            if (feeds.get(i).extendedEntities != null && feeds.get(i).extendedEntities.media != null) {
                switch (feeds.get(i).extendedEntities.media.get(0).type) {
                    case "photo":
                        createFeedItemPicture(componentInfoBuilder, componentContext, feeds.get(i));
                        break;
                    default:
                        createFeedItemText(componentInfoBuilder, componentContext, feeds.get(i));
                        break;
                }
            } else {
                createFeedItemText(componentInfoBuilder, componentContext, feeds.get(i));
            }
            recyclerBinder.insertItemAt(i, componentInfoBuilder.build());
        }
    }

    private void createFeedItemText(ComponentInfo.Builder builder, ComponentContext componentContext, Tweet feed) {
        builder.component(
                FeedItem.create(componentContext)
                        .tweet(feed)
                        .build());

    }

    private void createFeedItemPicture(ComponentInfo.Builder builder, ComponentContext componentContext, Tweet feed) {
        builder.component(
                FeedPictureItem.create(componentContext)
                        .tweet(feed)
                        .build());
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
