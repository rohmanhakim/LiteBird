package com.rohmanhakim.litebird.litho;

import com.facebook.litho.Column;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.Row;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.Text;
import com.rohmanhakim.litebird.R;
import com.twitter.sdk.android.core.models.Tweet;

import static android.graphics.Typeface.BOLD;
import static com.facebook.yoga.YogaEdge.START;
import static com.facebook.yoga.YogaEdge.TOP;

@LayoutSpec
class FeedContentSpec {

    @OnCreateLayout
    static ComponentLayout onCreateLayout(
            ComponentContext componentContext,
            @Prop Tweet tweet) {
        return Column.create(componentContext)
                .marginDip(START, 8)
                .child(
                        userDisplayAndUserName(componentContext, tweet.user.name, tweet.user.screenName))
                .child(
                        Text.create(componentContext)
                                .text(tweet.text)
                                .textSizeSp(14)
                                .textColorRes(R.color.feed_item_content_color)
                                .withLayout()
                                .marginDip(TOP, 2))
                .build();
    }


    private static ComponentLayout.ContainerBuilder userDisplayAndUserName(ComponentContext componentContext, String userName, String displayName) {
        return Row.create(componentContext)
                .child(
                        Text.create(componentContext)
                                .text(displayName)
                                .textStyle(BOLD)
                                .textColorRes(R.color.feed_item_display_name_color)
                                .textSizeSp(14))
                .child(
                        Text.create(componentContext)
                                .text("@" + userName)
                                .textColorRes(R.color.feed_item_user_name_color)
                                .textSizeSp(14)
                                .withLayout()
                                .marginDip(START, 4));
    }
}
