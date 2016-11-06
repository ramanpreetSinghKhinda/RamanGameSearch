package title.source.game.search.utils;

import android.os.Build;

public class Globals {
    public static final String API_BASE_URL = "http://www.giantbomb.com/";
    public static final String API_KEY = "aa8967316ed5f9c097e5c79ad93662add01a8690";
    public static final String FORMAT_JSON = "json";
    public static final String RESOURCES_GAME = "game";
    public static final int SHARE = 999;

    public static final String ASIN = "asin";
    public static final String TRANSITION_IMAGE = "imageTransition";
    public static final boolean IS_LOLLIPOP = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
}
