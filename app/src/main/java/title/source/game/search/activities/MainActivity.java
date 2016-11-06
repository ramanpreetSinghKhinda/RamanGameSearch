package title.source.game.search.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import butterknife.Bind;
import title.source.game.search.R;
import title.source.game.search.adapters.FragmentAdapter;
import title.source.game.search.fragments.FavoritesFragment;
import title.source.game.search.fragments.SearchFragment;

public class MainActivity extends BaseActivity implements TextView.OnEditorActionListener, AdapterView.OnItemClickListener {
    @Bind(R.id.screen_img_view)
    ViewPager mPager;

    @Bind(R.id.tabs_layout)
    TabLayout mTabLayout;

    @Bind(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;

    AutoCompleteTextView mSearchView;

    SearchFragment mSearchFragment;
    FavoritesFragment mFavoritesFragment;

    InputMethodManager mInputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor(R.color.primary_dark);

        // Search bar stuff
        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mSearchView = (AutoCompleteTextView) findViewById(R.id.search_atv);

        mSearchView.setOnEditorActionListener(this);
        mSearchView.setOnItemClickListener(this);

        // Initialize the fragments and the view pager adapter
        FragmentAdapter adapter = new FragmentAdapter(mFragmentManager);

        mSearchFragment = (SearchFragment) mFragmentManager.findFragmentByTag(adapter.getFragmentTag(R.id.screen_img_view, 0));
        mFavoritesFragment = (FavoritesFragment) mFragmentManager.findFragmentByTag(adapter.getFragmentTag(R.id.screen_img_view, 1));

        if (mSearchFragment == null) {
            mSearchFragment = SearchFragment.newInstance();
            mFavoritesFragment = FavoritesFragment.newInstance();
        }

        adapter.addFragment(mSearchFragment, getString(R.string.search_results));
        adapter.addFragment(mFavoritesFragment, getString(R.string.favorites));
        mPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mPager);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_SEARCH
                || (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            search(mSearchView.getText().toString());
            handled = true;
        }

        return handled;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        search((String) parent.getAdapter().getItem(position));
    }

    private void search(String searchTerm) {
        if (mPager.getCurrentItem() != 0) {
            mPager.setCurrentItem(0, true);
        }

        mSearchFragment.search(searchTerm, 1);
        mInputMethodManager.hideSoftInputFromWindow(mSearchView.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

}
