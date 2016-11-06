package title.source.game.search.fragments;

import android.os.Bundle;
import android.view.View;

import com.github.jorgecastillo.State;
import com.github.jorgecastillo.listener.OnStateChangeListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import title.source.game.search.utils.Globals;
import title.source.game.search.GameSearchApp;
import title.source.game.search.utils.GameSearchUtils;
import title.source.game.search.utils.ItemCallback;
import title.source.game.search.utils.Path;
import title.source.game.search.R;
import title.source.game.search.adapters.SearchResultsAdapter;
import title.source.game.search.models.SearchResult;
import title.source.game.search.models.SearchResultItem;

public class SearchFragment extends BaseRecyclerListFragment implements ItemCallback<View>, OnStateChangeListener {
    SearchResultsAdapter mAdapter;
    String mCurrentSearchTerm;
    boolean mNewSearch = true;
    boolean isFirstOpen = false;

    int mCurrentPage = 1;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFillableLoader.setOnStateChangeListener(this);

        List<SearchResultItem> items = new ArrayList<>(0);
        mAdapter = new SearchResultsAdapter(items, this);
        initGridCardsList(mAdapter);
        swapViews(false);

        if (items.size() == 0) {
            isFirstOpen = true;
            loadPathIntoLoader(Path.SEARCH);
            mFillableLoader.start();
        }
    }

    /**
     * Convenience method to search the API
     * If the current search term is different than the previous one
     * The loading indicator is shown and the adapter is reset
     *
     * @param searchTerm The term to be searched
     * @param page       The page that we want to retrieve
     */
    public void search(String searchTerm, int page) {
        mCurrentPage = page;

        // If is not a new search we set a boolean
        // This will determine if we want to add items to the adapter
        // or reset the adapter itself
        if (searchTerm.equals(mCurrentSearchTerm)) {
            mNewSearch = false;
        } else {
            // Start the loading animation
            swapViews(false);
            mCurrentSearchTerm = searchTerm;
            mEmptyText.setText(getString(R.string.loading));
            loadPathIntoLoader(Path.SEARCH);
            mFillableLoader.start();
        }

        getGameResults(searchTerm);


    }

    public void getGameResults(String searchTerm) {
        Call<SearchResult> call = GameSearchApp.getInstance().getApi()
                .searchItems(Globals.API_KEY, Globals.FORMAT_JSON, searchTerm, Globals.RESOURCES_GAME);

        call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                SearchResult gameResult = response.body();

                if (gameResult.number_of_total_results == 0) {
                    mEmptyText.setText(getString(R.string.error_search));
                    mFillableLoader.setSvgPath(Path.CLOUD);
                    mFillableLoader.start();

                } else {
                    swapViews(true);
                    if (mNewSearch) {
                        mAdapter.swapResults(gameResult.results);
                    } else {
                        mNewSearch = true;
                        mAdapter.addAll(gameResult.results);
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                mEmptyText.setText(getString(R.string.error_search));
                mFillableLoader.setSvgPath(Path.CLOUD);
                mFillableLoader.start();
            }
        });
    }

    @Override
    public void onItemClick(View v) {
        GameSearchUtils.openProductDetail(getActivity(), v);
    }

    @Override
    public void onStateChange(int state) {
        if (state == State.FINISHED) {
            if (isFirstOpen) {
                isFirstOpen = false;
                loadPathIntoLoader(Path.SEARCH);
                mEmptyText.setText(getString(R.string.empty_search));
                mFillableLoader.start();
            }
        }
    }

}
