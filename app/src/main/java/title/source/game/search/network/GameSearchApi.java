package title.source.game.search.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import title.source.game.search.models.SearchResult;

public interface GameSearchApi {

    @GET("api/search/")
    Call<SearchResult> searchItems(
            @Query("api_key") String apiKey,
            @Query("format") String format,
            @Query("query") String searchTerm,
            @Query("resources") String resources);
}
