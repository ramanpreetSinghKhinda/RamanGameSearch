package title.source.game.search.models;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class SearchResult {
    public String error;
    public int limit;
    public int offset;
    public int number_of_page_results;
    public int number_of_total_results;
    public int status_code;
    public List<SearchResultItem> results;
    public String version;

    public SearchResult(){}
}
