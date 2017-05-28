package com.rohmanhakim.litebird;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.FeedViewHolder> {

    private List<Tweet> feeds = new ArrayList<>();

    FeedsAdapter(List<Tweet> feeds) {
        this.setFeeds(feeds);
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_item,parent,false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
        holder.textUserName.setText(getFeeds().get(position).user.screenName);
    }

    @Override
    public int getItemCount() {
        return getFeeds().size();
    }

    public List<Tweet> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<Tweet> feeds) {
        this.feeds = feeds;
        notifyDataSetChanged();
    }

    class FeedViewHolder extends RecyclerView.ViewHolder {

        ImageView imgAvatar;
        TextView textDisplayName;
        TextView textUserName;
        TextView textContent;

        public FeedViewHolder(View itemView) {
            super(itemView);
            imgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
            textDisplayName = (TextView) itemView.findViewById(R.id.textDisplayName);
            textUserName = (TextView) itemView.findViewById(R.id.textUserName);
            textContent = (TextView) itemView.findViewById(R.id.textContent);
        }
    }
}
