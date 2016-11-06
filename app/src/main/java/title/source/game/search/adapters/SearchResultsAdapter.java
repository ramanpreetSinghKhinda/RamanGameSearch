package title.source.game.search.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import title.source.game.search.utils.GameSearchUtils;
import title.source.game.search.utils.ItemCallback;
import title.source.game.search.R;
import title.source.game.search.models.SearchResultItem;
import title.source.game.search.view.SearchResultViewHolder;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultViewHolder> {

    private ItemCallback<View> mCallback;
    private List<SearchResultItem> mItems;


    public SearchResultsAdapter(@Nullable List<SearchResultItem> items, ItemCallback<View> callback) {
        if (items == null) {
            mItems = new ArrayList<>();
        } else {
            this.mItems = items;
        }

        this.mCallback = callback;
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new SearchResultViewHolder(view, mCallback);
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder holder, int position) {
        SearchResultItem item = mItems.get(position);

        if (null != item.image) {
            Glide.with(holder.itemView.getContext())
                    .load(item.image.thumb_url)
                    .error(R.drawable.img_game_sample)
                    .crossFade()
                    .into(holder.gameImage);
        }

        String strGameName = item.aliases;
        if (null == strGameName) {
            strGameName = item.name;
        }

        holder.gameName.setText(strGameName);
        holder.releaseDate.setText(GameSearchUtils.getNewDateFormat(item.date_added));
        holder.gameDescription.setText(item.deck);

        holder.itemView.setTag(R.id.asin, item);
    }

    public List<SearchResultItem> getItems() {
        return mItems;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void swapResults(List<SearchResultItem> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }

    public void addAll(List<SearchResultItem> items) {
        this.mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void reset() {
        this.mItems = new ArrayList<>();
        notifyDataSetChanged();
    }
}
