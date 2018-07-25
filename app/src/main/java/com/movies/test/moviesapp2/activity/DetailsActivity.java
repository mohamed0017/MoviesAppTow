package com.movies.test.moviesapp2.activity;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.movies.test.moviesapp2.R;
import com.movies.test.moviesapp2.adapter.FavAdapter;
import com.movies.test.moviesapp2.adapter.RatingAdapter;
import com.movies.test.moviesapp2.adapter.trailersAdapter;
import com.movies.test.moviesapp2.api.ApiInterface;
import com.movies.test.moviesapp2.api.FullRestAdapter;
import com.movies.test.moviesapp2.database.AppDatabase;
import com.movies.test.moviesapp2.database.AppExecutors;
import com.movies.test.moviesapp2.database.favoritesEntry;
import com.movies.test.moviesapp2.model.rating;
import com.movies.test.moviesapp2.model.videos;
import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity implements FavAdapter.ItemClickListener{

    String title, release, overView, vote, movePoster, moveBg;
    int id;

    @BindView(R.id.textTitleDetails)
    TextView textViewTitle;
    @BindView(R.id.textReleaseDetails)
    TextView textViewReease;
    @BindView(R.id.ratingDetails)
    TextView textViewVote;
    @BindView(R.id.textOverViewDetails)
    TextView textViewOverView;
    @BindView(R.id.imagePosterDetails)
    ImageView imageViewPoster;
    @BindView(R.id.imageBgDetails)
    ImageView imageViewBg;
    @BindView(R.id.videosRecycler)
    RecyclerView videosRecycler;
    @BindView(R.id.favoriteRed)
    ImageView favoriteRed;
    @BindView(R.id.favoriteWhite)
    ImageView favoriteWhite;
    @BindView(R.id.RatingRecycler)
    RecyclerView RatingRecycler;

    private AppDatabase mdatabase;
    private SharedPreferences sharedPreferences;


    // Constant for logging
    private static final String TAG = DetailsActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences("Movies", MODE_PRIVATE);

        mdatabase = AppDatabase.getInstance(getApplicationContext());

        try {
            title = getIntent().getExtras().getString("getTitle");
            release = getIntent().getExtras().getString("getRelease_date");
            overView = getIntent().getExtras().getString("getOverview");
            vote = getIntent().getExtras().getString("getVote_average");
            movePoster = getIntent().getExtras().getString("getPoster_path");
            moveBg = getIntent().getExtras().getString("get_bg");
            id = getIntent().getExtras().getInt("id");

            textViewTitle.setText(title);
            textViewReease.setText(release);
            textViewOverView.setText(overView);
            textViewVote.setText(vote);

            Picasso.with(this).load("http://image.tmdb.org/t/p/w500" + movePoster)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder).into(imageViewPoster);
            Picasso.with(this).load("http://image.tmdb.org/t/p/original" + moveBg)
                    .placeholder(R.drawable.placeholder_image4)
                    .error(R.drawable.placeholder_image4).into(imageViewBg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getMovieVideo();
        getMovieRating();
        Boolean bool= sharedPreferences.getBoolean("favorites"+id,false);
        if (bool.equals(true))
        {
            favoriteRed.setVisibility(View.VISIBLE);
            favoriteWhite.setVisibility(View.GONE);
        }
        else
        {
            favoriteRed.setVisibility(View.GONE);
            favoriteWhite.setVisibility(View.VISIBLE);

        }
        // add a movie to a database
        favoriteWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoriteWhite.setVisibility(View.GONE);
                favoriteRed.setVisibility(View.VISIBLE);

                sharedPreferences.edit().putBoolean("favorites"+id,true).apply();

                final favoritesEntry favoritesEntry= new favoritesEntry(id, title,overView,vote,release);
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                            mdatabase.favouriteDeo().insertFavorites(favoritesEntry);
                            Log.i(TAG, "insertFavorites");
                    }
                });

                Toast.makeText(DetailsActivity.this, "Added To favorites", Toast.LENGTH_SHORT).show();

            }
        });

        // remove a movie from a database
        favoriteRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    public void getMovieVideo() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(ApiInterface.BasicUrl)
                .appendPath("3")
                .appendPath("movie")
                .appendPath(id + "")
                .appendPath("videos");
        String FinalUrl = builder.build().toString();
        ApiInterface api = FullRestAdapter.createAPI(FinalUrl + "/");
        Call<videos> resultsCall = api.getVideo(id);
        resultsCall.enqueue(new Callback<videos>() {
            @Override
            public void onResponse(@NonNull Call<videos> call, @NonNull Response<videos> response) {
                if (response.isSuccessful()) {
                    videosRecycler.setLayoutManager(new LinearLayoutManager(DetailsActivity.this));
                    trailersAdapter postersMoviesAdapter = new trailersAdapter(response.body().getResults(), DetailsActivity.this);
                    videosRecycler.setAdapter(postersMoviesAdapter);

                }
            }

            @Override
            public void onFailure(@NonNull Call<videos> call, @NonNull Throwable t) {

                Log.e("onFailure", t.getCause() + " ..");

            }
        });
    }

    public void getMovieRating() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(ApiInterface.BasicUrl)
                .appendPath("3")
                .appendPath("movie")
                .appendPath(id + "")
                .appendPath("reviews");
        String FinalUrl = builder.build().toString();
        ApiInterface api = FullRestAdapter.createAPI(FinalUrl + "/");
        Call<rating> resultsCall = api.getReviews(id);
        resultsCall.enqueue(new Callback<rating>() {
            @Override
            public void onResponse(@NonNull Call<rating> call, @NonNull Response<rating> response) {
                if (response.isSuccessful()) {
                    RatingRecycler.setLayoutManager(new LinearLayoutManager(DetailsActivity.this));
                    RatingAdapter ratingAdapter = new RatingAdapter(response.body().getResults(), DetailsActivity.this);
                    RatingRecycler.setAdapter(ratingAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<rating> call, @NonNull Throwable t) {

                Log.e("onFailure", t.getCause() + " ..");

            }
        });
    }

    @Override
    public void onItemClickListener(int itemId) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
