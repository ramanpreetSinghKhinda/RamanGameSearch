package title.source.game.search.fragments;

import android.os.Bundle;
import android.view.View;

import title.source.game.search.utils.GameSearchUtils;
import title.source.game.search.utils.ItemCallback;
import title.source.game.search.utils.Path;
import title.source.game.search.R;
import title.source.game.search.adapters.SearchResultsAdapter;

public class FavoritesFragment extends BaseRecyclerListFragment implements ItemCallback<View> {

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    SearchResultsAdapter mAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEmptyText.setText(getString(R.string.empty_favourite));
        loadPathIntoLoader(Path.SEARCH);
        mFillableLoader.start();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onItemClick(View v) {
        GameSearchUtils.openProductDetail(getActivity(), v);
    }
}
