package com.rohmanhakim.litebird.litho;

import com.facebook.litho.Column;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.Row;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.rohmanhakim.litebird.GlideImage;
import com.twitter.sdk.android.core.models.Tweet;

import static android.graphics.Color.TRANSPARENT;
import static com.facebook.yoga.YogaEdge.ALL;
import static com.facebook.yoga.YogaEdge.TOP;

@LayoutSpec
class FeedPictureItemSpec {
    @OnCreateLayout
    static ComponentLayout onCreateLayout(
            ComponentContext componentContext,
            @Prop Tweet tweet) {
        return Row.create(componentContext)
                .paddingDip(ALL, 8)
                .backgroundColor(TRANSPARENT)
                .child(
                        glideImage(componentContext, tweet.user.profileImageUrl))
                .child(
                        Column.create(componentContext)
                                .child(
                                        FeedContent.create(componentContext)
                                                .tweet(tweet))
                                .child(
                                        GlideImage.create(componentContext)
                                                .imageUrl(tweet.extendedEntities.media.get(0).mediaUrl)
                                                .fitCenter(true)
                                                .withLayout()
                                                .widthPercent(100)
                                                .marginPercent(ALL, 0)
                                                .paddingPercent(ALL, 0)
                                                .aspectRatio(((float) tweet.extendedEntities.media.get(0).sizes.small.w / (float) tweet.extendedEntities.media.get(0).sizes.small.h))
                                                .marginDip(TOP, 8))
                )
                .build();

    }

    private static ComponentLayout.Builder glideImage(ComponentContext componentContext, String avatarUrl) {
        return GlideImage.create(componentContext)
                .imageUrl(avatarUrl)
                .centerCrop(true)
                .withLayout()
                .widthDip(48)
                .heightDip(48);
    }
}
