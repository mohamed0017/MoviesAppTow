package com.movies.test.moviesapp2.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.movies.test.moviesapp2.R;
import com.movies.test.moviesapp2.activity.DetailsActivity;
import com.movies.test.moviesapp2.model.results;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 7/7/2018.
 */

public class MoiesAdapter extends RecyclerView.Adapter<MoiesAdapter.MoviesHolder> {


    List<results> moviesList = new ArrayList<>();
    Context context;


    public MoiesAdapter(List<results> moviesList, Context context) {
        this.moviesList = moviesList;
        this.context = context;
    }

    @Override
    public MoviesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_single_item, parent, false);
        MoviesHolder posterHolder = new MoviesHolder(view);

        return posterHolder;
    }

    @Override
    public void onBindViewHolder(MoviesHolder holder, int position) {


        final results movies = moviesList.get(position);

        Picasso.with(context).load("http://image.tmdb.org/t/p/w500" + movies.getPoster_path())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder).into(holder.imageView_poster);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("getTitle", movies.getTitle());
                intent.putExtra("getPoster_path", movies.getPoster_path());
                intent.putExtra("getOverview", movies.getOverview());
                intent.putExtra("getRelease_date", movies.getRelease_date());
                intent.putExtra("getVote_average", movies.getVote_average());
                intent.putExtra("get_bg", movies.getBackdrop_path());
                intent.putExtra("id", movies.getId());

                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    class MoviesHolder extends RecyclerView.ViewHolder {
        ImageView imageView_poster;
        LinearLayout linearLayout;

        public MoviesHolder(View itemView) {
            super(itemView);
            imageView_poster = itemView.findViewById(R.id.img_poster);
            linearLayout = itemView.findViewById(R.id.SingleMovie);
        }
    }
}