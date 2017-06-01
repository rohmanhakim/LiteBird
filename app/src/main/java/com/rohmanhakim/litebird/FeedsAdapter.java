package com.rohmanhakim.litebird;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

class FeedsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int MEDIA_TYPE_TEXT = 0;
    private static final int MEDIA_TYPE_PHOTO = 1;
    private static final int MEDIA_TYPE_RETWEET = 2;

    private List<Tweet> feeds = new ArrayList<>();
    private Context context;

    FeedsAdapter(List<Tweet> feeds, Context context) {
        this.setFeeds(feeds);
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (feeds.get(position).extendedEntities != null) {
            if (feeds.get(position).extendedEntities.media != null) {
                switch (feeds.get(position).extendedEntities.media.get(0).type) {
                    case "photo":
                        return MEDIA_TYPE_PHOTO;
                    default:
                        return MEDIA_TYPE_TEXT;
                }
            }
        }
        if (feeds.get(position).retweetedStatus != null)
            return MEDIA_TYPE_RETWEET;
        return MEDIA_TYPE_TEXT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case MEDIA_TYPE_PHOTO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_item_picture, parent, false);
                return new FeedPictureViewHolder(view);
            case MEDIA_TYPE_TEXT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_item, parent, false);
                return new FeedViewHolder(view);
            case MEDIA_TYPE_RETWEET:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_item_retweet, parent, false);
                return new FeedRetweetViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_item, parent, false);
                return new FeedViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Tweet tweet = feeds.get(position);
        switch (holder.getItemViewType()) {
            case MEDIA_TYPE_RETWEET:
                FeedRetweetViewHolder feedRetweetViewHolder = (FeedRetweetViewHolder) holder;
                feedRetweetViewHolder.textDisplayName.setText(tweet.user.name);
                feedRetweetViewHolder.textUserName.setText(context.getString(R.string.at_symbol, tweet.user.screenName));
                Glide.with(context).load(tweet.user.profileImageUrl).into(feedRetweetViewHolder.imgAvatar);
                feedRetweetViewHolder.textContent.setText(tweet.text);

                feedRetweetViewHolder.textRetweetDisplayName.setText(tweet.retweetedStatus.user.name);
                feedRetweetViewHolder.textRetweetUserName.setText(context.getString(R.string.at_symbol, tweet.retweetedStatus.user.screenName));
                feedRetweetViewHolder.textRetweetContent.setText(tweet.retweetedStatus.text);
                Glide.with(context).load(tweet.retweetedStatus.user.profileImageUrl).into(feedRetweetViewHolder.imgRetweetAvatar);
                feedRetweetViewHolder.textContent.setText(tweet.retweetedStatus.text);
                if (tweet.retweetedStatus.extendedEntities != null) {
                    if (tweet.retweetedStatus.extendedEntities.media != null) {
                        Glide.with(context).load(tweet.retweetedStatus.extendedEntities.media.get(0).mediaUrl).into(feedRetweetViewHolder.imgRetweetAttachment);
                    }
                }
                break;
            case MEDIA_TYPE_PHOTO:
                FeedPictureViewHolder feedPictureViewHolder = (FeedPictureViewHolder) holder;
                feedPictureViewHolder.textDisplayName.setText(tweet.user.name);
                feedPictureViewHolder.textUserName.setText(context.getString(R.string.at_symbol, tweet.user.screenName));
                Glide.with(context).load(tweet.user.profileImageUrl).into(feedPictureViewHolder.imgAvatar);
                Glide.with(context).load(tweet.extendedEntities.media.get(0).mediaUrl).into(feedPictureViewHolder.imgAttachment);
                feedPictureViewHolder.textContent.setText(tweet.text);
                break;
            case MEDIA_TYPE_TEXT:
                FeedViewHolder feedViewHolder = (FeedViewHolder) holder;
                feedViewHolder.textDisplayName.setText(tweet.user.name);
                feedViewHolder.textUserName.setText(context.getString(R.string.at_symbol, tweet.user.screenName));
                Glide.with(context).load(tweet.user.profileImageUrl).into(feedViewHolder.imgAvatar);
                feedViewHolder.textContent.setText(tweet.text);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return getFeeds().size();
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        switch (holder.getItemViewType()) {
            case MEDIA_TYPE_RETWEET:
                FeedRetweetViewHolder feedRetweetViewHolder = (FeedRetweetViewHolder) holder;
                feedRetweetViewHolder.textDisplayName.setText(null);
                feedRetweetViewHolder.textUserName.setText(null);
                feedRetweetViewHolder.imgAvatar.setImageDrawable(null);
                feedRetweetViewHolder.textContent.setText(null);

                feedRetweetViewHolder.textRetweetDisplayName.setText(null);
                feedRetweetViewHolder.textRetweetUserName.setText(null);
                feedRetweetViewHolder.imgRetweetAvatar.setImageDrawable(null);
                feedRetweetViewHolder.textContent.setText(null);
                feedRetweetViewHolder.imgRetweetAttachment.setImageDrawable(null);
                break;
            case MEDIA_TYPE_PHOTO:
                FeedPictureViewHolder feedPictureViewHolder = (FeedPictureViewHolder) holder;
                feedPictureViewHolder.textDisplayName.setText(null);
                feedPictureViewHolder.textUserName.setText(null);
                feedPictureViewHolder.imgAvatar.setImageDrawable(null);
                feedPictureViewHolder.imgAttachment.setImageDrawable(null);
                feedPictureViewHolder.textContent.setText(null);
                break;
            case MEDIA_TYPE_TEXT:
                FeedViewHolder feedViewHolder = (FeedViewHolder) holder;
                feedViewHolder.textDisplayName.setText(null);
                feedViewHolder.textUserName.setText(null);
                feedViewHolder.imgAvatar.setImageDrawable(null);
                feedViewHolder.textContent.setText(null);
                break;
        }
        super.onViewRecycled(holder);
    }

    private List<Tweet> getFeeds() {
        return feeds;
    }

    void setFeeds(List<Tweet> feeds) {
        this.feeds = feeds;
        notifyDataSetChanged();
    }

    private class FeedViewHolder extends RecyclerView.ViewHolder {

        ImageView imgAvatar;
        TextView textDisplayName;
        TextView textUserName;
        TextView textContent;

        FeedViewHolder(View itemView) {
            super(itemView);
            imgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
            textDisplayName = (TextView) itemView.findViewById(R.id.textDisplayName);
            textUserName = (TextView) itemView.findViewById(R.id.textUserName);
            textContent = (TextView) itemView.findViewById(R.id.textContent);
        }
    }

    private class FeedPictureViewHolder extends RecyclerView.ViewHolder {

        ImageView imgAvatar;
        ImageView imgAttachment;
        TextView textDisplayName;
        TextView textUserName;
        TextView textContent;

        FeedPictureViewHolder(View itemView) {
            super(itemView);
            imgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
            imgAttachment = (ImageView) itemView.findViewById(R.id.imgAttachment);
            textDisplayName = (TextView) itemView.findViewById(R.id.textDisplayName);
            textUserName = (TextView) itemView.findViewById(R.id.textUserName);
            textContent = (TextView) itemView.findViewById(R.id.textContent);
        }
    }

    private class FeedRetweetViewHolder extends RecyclerView.ViewHolder {

        ImageView imgAvatar;
        TextView textDisplayName;
        TextView textUserName;
        TextView textContent;

        ImageView imgRetweetAvatar;
        TextView textRetweetDisplayName;
        TextView textRetweetUserName;
        TextView textRetweetContent;
        ImageView imgRetweetAttachment;

        FeedRetweetViewHolder(View itemView) {
            super(itemView);
            imgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
            imgRetweetAttachment = (ImageView) itemView.findViewById(R.id.imgAttachment);
            textDisplayName = (TextView) itemView.findViewById(R.id.textDisplayName);
            textUserName = (TextView) itemView.findViewById(R.id.textUserName);
            textContent = (TextView) itemView.findViewById(R.id.textContent);

            imgRetweetAvatar = (ImageView) itemView.findViewById(R.id.imgRetweetAvatar);
            textRetweetDisplayName = (TextView) itemView.findViewById(R.id.textRetweetDisplayName);
            textRetweetUserName = (TextView) itemView.findViewById(R.id.textRetweettUserName);
            textRetweetContent = (TextView) itemView.findViewById(R.id.textRetweetContent);
            imgRetweetAttachment = (ImageView) itemView.findViewById(R.id.imgRetweetAttachment);
        }
    }
}
