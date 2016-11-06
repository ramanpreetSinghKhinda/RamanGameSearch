package title.source.game.search.models;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class SearchResultItem {
    public String aliases;
    public String api_detail_url;
    public String date_added;
    public String date_last_updated;
    public String deck;
    public String description;
    public int expected_release_day;
    public int expected_release_month;
    public int expected_release_quarter;
    public int expected_release_year;
    public int id;
    public ImageData image;
    public String name;
    public int number_of_user_reviews;
    public List<GameRatingData> original_game_rating;
    public String original_release_date;
    public List<PlatformData> platforms;
    public String site_detail_url;
    public String resource_type;

    public SearchResultItem(){}
}
