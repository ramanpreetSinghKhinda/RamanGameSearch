package title.source.game.search.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import title.source.game.search.R;
import title.source.game.search.activities.DetailActivity;
import title.source.game.search.models.SearchResultItem;


public class GameSearchUtils {

    public static String getNewDateFormat(String oldDateString) {
        final String OLD_FORMAT = "yyyy-MM-dd HH:mm:ss";
        final String NEW_FORMAT = "MMM dd, yyyy";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
            Date d = sdf.parse(oldDateString);
            sdf.applyPattern(NEW_FORMAT);

            return sdf.format(d);
        } catch (ParseException e) {
            return oldDateString;
        }
    }

    /**
     * Helper method to open the detail Activity.
     * If the version is Lollipop or above a cool transition is performed
     *
     * @param activity The activity to start the intent
     * @param itemView The View that was clicked
     */
    public static void openProductDetail(Activity activity, View itemView) {
        ImageView imageView = (ImageView) itemView.findViewById(R.id.game_image_view);
        SearchResultItem item = (SearchResultItem) itemView.getTag(R.id.asin);

        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(Globals.ASIN, Parcels.wrap(item));
        if (Globals.IS_LOLLIPOP) {
            performTransition(activity, imageView, intent);
        } else {
            activity.startActivity(intent);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void performTransition(Activity activity, View v, Intent intent) {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, v, Globals.TRANSITION_IMAGE);
        activity.startActivity(intent, options.toBundle());
    }

    /**
     * Utility method to colorize all the items on the Toolbar with a specified tint color
     *
     * @param toolbar           Toolbar to be colorized
     * @param toolbarIconsColor The color resource to be used to colorize the toolbar
     */
    public static void colorizeToolbar(Toolbar toolbar, @ColorRes int toolbarIconsColor) {

        Resources res = toolbar.getResources();

        int color = res.getColor(toolbarIconsColor);

        final PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY);


        //Step 3: Changing the color of title and subtitle.
        toolbar.setTitleTextColor(color);
        toolbar.setSubtitleTextColor(color);

    }
}
