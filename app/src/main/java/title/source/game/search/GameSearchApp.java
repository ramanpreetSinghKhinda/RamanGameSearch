package title.source.game.search;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import title.source.game.search.network.GameSearchApi;
import title.source.game.search.network.GameSearchClient;

public class GameSearchApp extends Application {

    private static GameSearchApp sInstance;
    private GameSearchApi mGameApi;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        // Normal app init code...
        sInstance = this;
    }

    /**
     * @return The singleton instance of this Application
     */
    public static GameSearchApp getInstance() {
        return sInstance;
    }

    /**
     * @return A reference to the Api used by the App
     */
    public GameSearchApi getApi() {
        if (mGameApi == null) {
            mGameApi = GameSearchClient.initApi(this);
        }
        return mGameApi;
    }
}

