package title.source.game.search.view;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import title.source.game.search.utils.ItemCallback;
import title.source.game.search.R;


public class SearchResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView gameImage;
    public AppCompatTextView gameName;
    public AppCompatTextView releaseDate;
    public AppCompatTextView gameDescription;

    ItemCallback<View> callback;

    public SearchResultViewHolder(View itemView, ItemCallback<View> callback) {
        super(itemView);

        gameImage = (ImageView) itemView.findViewById(R.id.game_image_view);
        gameName = (AppCompatTextView) itemView.findViewById(R.id.game_name_compat_text_view);
        releaseDate = (AppCompatTextView) itemView.findViewById(R.id.date_added_compat_text_view);
        gameDescription = (AppCompatTextView) itemView.findViewById(R.id.game_desc_text_view);

        this.callback = callback;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        callback.onItemClick(v);
    }
}
