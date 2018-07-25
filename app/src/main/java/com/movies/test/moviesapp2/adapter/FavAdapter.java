package com.movies.test.moviesapp2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.movies.test.moviesapp2.R;
import com.movies.test.moviesapp2.database.favoritesEntry;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * This FavAdapter creates and binds ViewHolders, that hold the description and priority of a movies,
 * to a RecyclerView to efficiently display data.
 */
public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavAdapterViewHolder> {

    // Constant for date format
    private static final String DATE_FORMAT = "dd/MM/yyy";

    // Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;
    // Class variables for the List that holds FavMovies data and the Context
    private List<favoritesEntry> mFavEntries;
    private Context mContext;
    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    /**
     * Constructor for the FavAdapter that initializes the Context.
     *
     * @param context  the current Context
     * @param listener the ItemClickListener
     */
    public FavAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new FavAdapterViewHolder that holds the view for each FavMove
     */
    @Override
    public FavAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the move_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.favorite_row, parent, false);

        return new FavAdapterViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(FavAdapterViewHolder holder, int position) {
        // Determine the values of the wanted data
        favoritesEntry results = mFavEntries.get(position);
        String description = results.getOverview();
        String titile= results.getTitle();
        String vote= results.getVote_average();
        String release= results.getRelease_date();

        holder.favoriteTitle.setText(titile);
        holder.favoriteOverView.setText(description);
        holder.favoriteRating.setText(vote);
        holder.favoriteRelease.setText(release);


    }

    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (mFavEntries == null) {
            return 0;
        }
        return mFavEntries.size();
    }

    public List<favoritesEntry> getFavorites() {
        return mFavEntries;
    }

    /**
     * When data changes, this method updates the list of FavEntries
     * and notifies the adapter to use the new values on it
     */
    public void setFavorites(List<favoritesEntry> favorites) {
        mFavEntries = favorites;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    // Inner class for creating ViewHolders
    class FavAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Class variables for the FavMovies description and priority TextViews
        TextView favoriteRelease;
        TextView favoriteRating;
        TextView favoriteOverView;
        TextView favoriteTitle;

        /**
         * Constructor for the FavViewHolder.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public FavAdapterViewHolder(View itemView) {
            super(itemView);

            favoriteRelease = itemView.findViewById(R.id.favoriteRelease);
            favoriteRating = itemView.findViewById(R.id.favoriteRating);
            favoriteOverView = itemView.findViewById(R.id.favoriteOverView);
            favoriteTitle = itemView.findViewById(R.id.favoriteTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = mFavEntries.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}