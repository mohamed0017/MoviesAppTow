package com.movies.test.moviesapp2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.movies.test.moviesapp2.R;
import com.movies.test.moviesapp2.model.results;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 7/18/2018.
 */

public class favoritesAdapter extends RecyclerView.Adapter<favoritesAdapter.favoriteHolder> {


    private List<results> moviesList = new ArrayList<>();
    private Context context;


    public favoritesAdapter(List<results> moviesList, Context context) {
        this.moviesList = moviesList;
        this.context = context;
    }

    @Override
    public favoritesAdapter.favoriteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_row, parent, false);
        favoriteHolder posterHolder = new favoriteHolder(view);

        return posterHolder;
    }

    @Override
    public void onBindViewHolder(favoritesAdapter.favoriteHolder holder, int position) {
        final results result = moviesList.get(position);

        holder.favoriteTitle.setText(result.getTitle());
        holder.favoriteOverView.setText(result.getOverview());
        holder.favoriteRelease.setText(result.getRelease_date());
        holder.favoriteRating.setText(result.getVote_average());
    }


    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    class favoriteHolder extends RecyclerView.ViewHolder {
        TextView favoriteTitle, favoriteRelease, favoriteRating, favoriteOverView;

        public favoriteHolder(View itemView) {
            super(itemView);
            favoriteTitle = itemView.findViewById(R.id.favoriteTitle);
            favoriteRelease = itemView.findViewById(R.id.favoriteRelease);
            favoriteRating = itemView.findViewById(R.id.favoriteRating);
            favoriteOverView = itemView.findViewById(R.id.favoriteOverView);
        }
    }
}