package title.source.game.search.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.Bind;
import title.source.game.search.utils.GameSearchUtils;
import title.source.game.search.utils.Globals;
import title.source.game.search.R;
import title.source.game.search.models.ImageData;
import title.source.game.search.models.SearchResultItem;

public class DetailActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {
    @Bind(R.id.main_content)
    CoordinatorLayout mainLayout;

    @Bind(R.id.pager_layout)
    RelativeLayout mPagerLayout;

    @Bind(R.id.screen_img_view)
    ImageView mScreenImage;

    @Bind(R.id.game_image_view)
    ImageView mGameImage;

    @Bind(R.id.description)
    HtmlTextView mDescription;

    @Bind(R.id.game_name)
    AppCompatTextView mGameName;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Bind(R.id.app_bar_layout)
    AppBarLayout mAppBar;

    @Bind(R.id.fab_favorite)
    FloatingActionButton fabFavorite;

    SearchResultItem mItem;

    float initialY = -1f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        enableBackNav();
        setTitle("");

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            mItem = Parcels.unwrap(extras.getParcelable(Globals.ASIN));


            String strGameName = mItem.aliases;
            if (null == strGameName) {
                strGameName = mItem.name;
            }
            mGameName.setText(strGameName);

            String strDescription = mItem.description;
            if (null == strDescription) {
                strDescription = mItem.deck;
            }

            mDescription.setHtmlFromString(strDescription, new HtmlTextView.RemoteImageGetter());
            mDescription.setLinksClickable(false);
            mDescription.setClickable(false);

            loadItemImage(mItem.image);
        }

        mAppBar.addOnOffsetChangedListener(this);

        CollapsingToolbarLayout.LayoutParams detailsLP = (CollapsingToolbarLayout.LayoutParams) mPagerLayout.getLayoutParams();
        detailsLP.setParallaxMultiplier(0.9f);
        mPagerLayout.setLayoutParams(detailsLP);

        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.grey_800));
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.grey_200));
    }

    void loadItemImage(ImageData imageData) {
        if (null != imageData) {
            Glide.with(this)
                    .load(imageData.medium_url)
                    .error(R.drawable.img_game_sample)
                    .crossFade()
                    .into(mGameImage);

            Glide.with(this)
                    .load(imageData.screen_url)
                    .error(R.drawable.img_game_sample)
                    .crossFade()
                    .into(mScreenImage);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_detail;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(Globals.SHARE, Globals.SHARE, 0, R.string.share);
        item.setIcon(R.drawable.ic_social_share);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }

    // This method is used to hide the Thumbnail of the item and to change
    // the color of the toolbar items.
    // The colors is important because most of the images have a white background
    // Which is the default color of the toolbar items
    // Changing it to a dark grey makes the back arrow and share icon visible
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        if (initialY <= 0) {
            initialY = mGameImage.getY();
        }

        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;
        float scale = Math.abs(-1f + percentage);
        mGameImage.setY(initialY * scale);
        mGameImage.setScaleX(scale);
        mGameImage.setScaleY(scale);

        if (percentage > 0.8f) {
            GameSearchUtils.colorizeToolbar(mToolbar, R.color.grey_200);
        } else {
            GameSearchUtils.colorizeToolbar(mToolbar, R.color.grey_800);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == Globals.SHARE) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, mItem.site_detail_url);
            shareIntent.setType("text/plain");
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_the_love)));
        }

        return super.onOptionsItemSelected(item);
    }
}
