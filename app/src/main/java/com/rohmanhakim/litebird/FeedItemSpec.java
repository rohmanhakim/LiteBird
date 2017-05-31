package com.rohmanhakim.litebird;

import com.facebook.litho.Column;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.Row;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.twitter.sdk.android.core.models.Tweet;

import static android.graphics.Color.TRANSPARENT;
import static com.facebook.yoga.YogaEdge.ALL;

@LayoutSpec
class FeedItemSpec {

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
                )
                .build();

    }

    private static ComponentLayout.Builder glideImage(ComponentContext componentContext, String avatarUrl) {
        return GlideImage.create(componentContext)
                .imageUrl(avatarUrl)
                .aspectRatio(1f)
                .centerCrop(true)
                .withLayout()
                .widthDip(48)
                .heightDip(48);
    }

}
