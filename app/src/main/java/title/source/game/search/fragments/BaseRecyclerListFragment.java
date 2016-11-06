package title.source.game.search.fragments;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jorgecastillo.FillableLoader;

import butterknife.Bind;
import title.source.game.search.utils.Path;
import title.source.game.search.R;

public abstract class BaseRecyclerListFragment extends BaseFragment {

    @Bind(R.id.game_list_recycler_view)
    protected RecyclerView mRecyclerView;

    @Bind(R.id.fillable_loader)
    protected FillableLoader mFillableLoader;

    @Bind(R.id.loading_layout)
    protected LinearLayout mLoadingLayout;

    @Bind(R.id.empty_text_view)
    protected TextView mEmptyText;

    protected GridLayoutManager mGridLayoutManager;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.game_list_recycler_view);
        mFillableLoader = (FillableLoader) view.findViewById(R.id.fillable_loader);
        mLoadingLayout = (LinearLayout) view.findViewById(R.id.loading_layout);
        mEmptyText = (TextView) view.findViewById(R.id.empty_text_view);

        loadPathIntoLoader(Path.SEARCH);
    }

    /**
     * Helper method to instantiate a recycler cards list with a grid
     * The number of columns should be specified in a integer variable
     * This allows to have different columns based on device orientation/size etc.
     *
     * @param adapter The adapter to be used by this List
     */
    protected void initGridCardsList(RecyclerView.Adapter adapter) {
        int numCols = getResources().getInteger(R.integer.num_cols);
        mGridLayoutManager = new GridLayoutManager(mRecyclerView.getContext(), numCols);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * Loads a path into the SVG path loader
     * This is a convenience method to make usage of Android Annotations support library to
     * get compile time safety for a String variable name
     *
     * @param path A String containing a SvgPath as specified in the interface Path.SvgPath
     */
    protected void loadPathIntoLoader(@Path.SvgPath String path) {
        mFillableLoader.setSvgPath(path);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.list_fragment;
    }

    /**
     * Swaps the loading indicator with the list
     *
     * @param listVisible Where the list should be visible or not
     */
    protected void swapViews(boolean listVisible) {
        mRecyclerView.setVisibility(listVisible ? View.VISIBLE : View.GONE);
        mLoadingLayout.setVisibility(listVisible ? View.GONE : View.VISIBLE);
    }
}
