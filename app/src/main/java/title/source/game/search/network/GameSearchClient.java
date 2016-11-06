package title.source.game.search.network;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import title.source.game.search.utils.Globals;

public class GameSearchClient {
    /**
     * Using the Application class which is a Singleton by nature guarantees this client to be a
     * singleton. Will also keep the activities from holding references to it avoiding memory leaks
     *
     * @param application Application on which this client should be initialized
     * @return The Api on which to make calls
     */
    public static GameSearchApi initApi(Application application) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        return retrofit.create(GameSearchApi.class);
    }
}
