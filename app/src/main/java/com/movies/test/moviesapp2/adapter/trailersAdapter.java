package com.movies.test.moviesapp2.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.movies.test.moviesapp2.R;
import com.movies.test.moviesapp2.model.VideosResults;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 7/13/2018.
 */

public class trailersAdapter extends RecyclerView.Adapter<trailersAdapter.TrailersHolder> {

    List<VideosResults> results = new ArrayList<>();
    Context context;

    public trailersAdapter(List<VideosResults> result, Context context) {
        this.results = result;
        this.context = context;
    }

    @Override
    public TrailersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_row, parent, false);
        TrailersHolder trailersHolder = new TrailersHolder(view);

        return trailersHolder;
    }

    @Override
    public void onBindViewHolder(TrailersHolder holder, int position) {

        final VideosResults movies = results.get(position);

        holder.textView.setText(movies.getName());

        holder.videoLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + movies.getKey()));
                context.startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class TrailersHolder extends RecyclerView.ViewHolder {

        TextView textView;
        LinearLayout videoLinear;

        public TrailersHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.trailers);
            videoLinear = itemView.findViewById(R.id.videoLinear);
        }
    }
}
