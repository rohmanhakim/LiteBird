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
    private static final int MEDIA_TYPE_VIDEO = 2;

    private List<Tweet> feeds = new ArrayList<>();
    private Context context;

    FeedsAdapter(List<Tweet> feeds, Context context) {
        this.setFeeds(feeds);
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(feeds.get(position).extendedEntities != null){
            if(feeds.get(position).extendedEntities.media != null) {
                switch (feeds.get(position).extendedEntities.media.get(0).type) {
                    case "photo":
                        return MEDIA_TYPE_PHOTO;
                    case "video":
                        return MEDIA_TYPE_VIDEO;
                    default:
                        return MEDIA_TYPE_TEXT;
                }
            }
        }
        return MEDIA_TYPE_TEXT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case MEDIA_TYPE_PHOTO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_item_picture,parent,false);
                return new FeedPictureViewHolder(view);
            case MEDIA_TYPE_TEXT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_item,parent,false);
                return new FeedViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_item,parent,false);
                return new FeedViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Tweet tweet = feeds.get(position);
        switch (holder.getItemViewType()){
            case MEDIA_TYPE_PHOTO:
                FeedPictureViewHolder feedPictureViewHolder = (FeedPictureViewHolder) holder;
                feedPictureViewHolder.textDisplayName.setText("[Photo]" + tweet.user.name);
                feedPictureViewHolder.textUserName.setText("@" + tweet.user.screenName);
                Glide.with(context).load(tweet.user.profileImageUrl).into(feedPictureViewHolder.imgAvatar);
                Glide.with(context).load(tweet.extendedEntities.media.get(0).mediaUrl).into(feedPictureViewHolder.imgAttachment);
                feedPictureViewHolder.textContent.setText(tweet.text);
                break;
            case MEDIA_TYPE_TEXT:
                FeedViewHolder feedViewHolder = (FeedViewHolder) holder;
                feedViewHolder.textDisplayName.setText("[Text]" + tweet.user.name);
                feedViewHolder.textUserName.setText("@" + tweet.user.screenName);
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
        switch (holder.getItemViewType()){
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
}
