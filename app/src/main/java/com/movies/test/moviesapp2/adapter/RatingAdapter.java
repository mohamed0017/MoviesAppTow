package com.movies.test.moviesapp2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.movies.test.moviesapp2.R;
import com.movies.test.moviesapp2.model.RatingResults;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 7/24/2018.
 */

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.RatingHolder> {

    List<RatingResults> results = new ArrayList<>();
    Context context;

    public RatingAdapter(List<RatingResults> result, Context context) {
        this.results = result;
        this.context = context;
    }

    @Override
    public RatingAdapter.RatingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_row, parent, false);
        RatingHolder trailersHolder = new RatingHolder(view);

        return trailersHolder;
    }

    @Override
    public void onBindViewHolder(RatingAdapter.RatingHolder holder, int position) {

        final RatingResults ratingResults = results.get(position);

        holder.rating_author.setText(ratingResults.getAuthor());
        holder.rating_content.setText(ratingResults.getContent());

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class RatingHolder extends RecyclerView.ViewHolder {

        TextView rating_author;
        TextView rating_content;

        public RatingHolder(View itemView) {
            super(itemView);

            rating_author = itemView.findViewById(R.id.rating_author);
            rating_content = itemView.findViewById(R.id.rating_text);
        }
    }
}
