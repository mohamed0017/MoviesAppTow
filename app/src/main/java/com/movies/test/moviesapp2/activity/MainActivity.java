package com.movies.test.moviesapp2.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.movies.test.moviesapp2.R;
import com.movies.test.moviesapp2.adapter.FavAdapter;
import com.movies.test.moviesapp2.adapter.MoiesAdapter;
import com.movies.test.moviesapp2.api.ApiInterface;
import com.movies.test.moviesapp2.api.FullRestAdapter;
import com.movies.test.moviesapp2.broadcastReceiver.ConnectivityReceiver;
import com.movies.test.moviesapp2.database.AppDatabase;
import com.movies.test.moviesapp2.database.AppExecutors;
import com.movies.test.moviesapp2.database.MainViewModel;
import com.movies.test.moviesapp2.database.favoritesEntry;
import com.movies.test.moviesapp2.model.movie;
import com.movies.test.moviesapp2.ui.GridAutofitLayoutManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener, FavAdapter.ItemClickListener {

    @BindView(R.id.recycleViewMovies)
    RecyclerView recyclerViewMovies;
    @BindView(R.id.recycleViewFavorites)
    RecyclerView recycleViewFavorites;
    private static MainActivity mInstance;
    private AppDatabase mdatabase;
    private FavAdapter favAdapter;

    SharedPreferences sharedPreferences;

    public static String TAG = "Main_Log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInstance = this;
        ButterKnife.bind(this);
        checkConnection();
        getMoviesPoupler();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        sharedPreferences = getSharedPreferences("Movies", MODE_PRIVATE);

        mdatabase = AppDatabase.getInstance(getApplicationContext());

        // Initialize the adapter and attach it to the RecyclerView
        favAdapter = new FavAdapter(MainActivity.this, this);

        recycleViewFavorites.setVisibility(View.GONE);
           /*
         Add a touch helper to the RecyclerView to recognize when a user swipes to delete an item.
         An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
         and uses callbacks to signal when a user is performing these actions.
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();

                        List<favoritesEntry> favoritesEntries = favAdapter.getFavorites();
                        mdatabase.favouriteDeo().deleteFavorites(favoritesEntries.get(position));

                        int id = favoritesEntries.get(position).getId();
                        sharedPreferences.edit().putBoolean("favorites" + id, false).apply();
                    }
                });
            }
        }).attachToRecyclerView(recycleViewFavorites);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    recycleViewFavorites.setVisibility(View.GONE);
                    recyclerViewMovies.setVisibility(View.VISIBLE);
                    getMoviesPoupler();
                    Log.i(TAG, "getMoviesPoupler");
                    return true;
                case R.id.navigation_rated:
                    Log.i(TAG, "navigation_rated");
                    recycleViewFavorites.setVisibility(View.GONE);
                    recyclerViewMovies.setVisibility(View.VISIBLE);

                    getMoviesTopRated();
                    return true;
                case R.id.navigation_favorites:
                    Log.i(TAG, "navigation_favorites");
                    recyclerViewMovies.setVisibility(View.GONE);
                    recycleViewFavorites.setVisibility(View.VISIBLE);
                    getFavoritesMovies();
                    return true;
            }
            return false;
        }
    };

    public void getMoviesPoupler() {

        checkConnection();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(ApiInterface.BasicUrl)
                .appendPath("3")
                .appendPath("movie")
                .appendPath("popular");
        String FinalUrl = builder.build().toString();
        ApiInterface api = FullRestAdapter.createAPI(FinalUrl + "/");
        Call<movie> resultsCall = api.getPopular();
        resultsCall.enqueue(new Callback<movie>() {
            @Override
            public void onResponse(@NonNull Call<movie> call, @NonNull Response<movie> response) {
                if (response.isSuccessful()) {
                    GridAutofitLayoutManager gridAutofitLayoutManager = new GridAutofitLayoutManager(MainActivity.this, 480);
                    recyclerViewMovies.setLayoutManager(gridAutofitLayoutManager);
                    Log.e(TAG, response.message() + " ..");
                    MoiesAdapter postersMoviesAdapter = new MoiesAdapter(response.body().getResult(), MainActivity.this);
                    recyclerViewMovies.setAdapter(postersMoviesAdapter);
                }

            }

            @Override
            public void onFailure(@NonNull Call<movie> call, @NonNull Throwable t) {

                Log.e(TAG, t.getCause() + " ..");

            }
        });

    }

    public void getMoviesTopRated() {
        checkConnection();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(ApiInterface.BasicUrl)
                .appendPath("3")
                .appendPath("movie")
                .appendPath("top_rated");
        String FinalUrl = builder.build().toString();
        ApiInterface api = FullRestAdapter.createAPI(FinalUrl + "/");
        Call<movie> resultsCall = api.gettop_rated();
        resultsCall.enqueue(new Callback<movie>() {
            @Override
            public void onResponse(@NonNull Call<movie> call, @NonNull Response<movie> response) {
                if (response.isSuccessful()) {
                    GridAutofitLayoutManager gridAutofitLayoutManager = new GridAutofitLayoutManager(MainActivity.this, 480);
                    recyclerViewMovies.setLayoutManager(gridAutofitLayoutManager);
                    MoiesAdapter postersMoviesAdapter = new MoiesAdapter(response.body().getResult(), MainActivity.this);
                    recyclerViewMovies.setAdapter(postersMoviesAdapter);

                    Log.e(TAG, response.message() + " ..");

                }

            }

            @Override
            public void onFailure(@NonNull Call<movie> call, @NonNull Throwable t) {

                Log.e(TAG, t.getCause() + " ..");

            }
        });
    }

    public void getFavoritesMovies() {

        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mainViewModel.getFavorites().observe(this, new Observer<List<favoritesEntry>>() {
            @Override
            public void onChanged(@Nullable List<favoritesEntry> favoritesEntries) {

                favAdapter.setFavorites(favoritesEntries);

                recycleViewFavorites.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recycleViewFavorites.setAdapter(favAdapter);
            }
        });


    }

    public static synchronized MainActivity getInstance() {
        return mInstance;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected == false) {
            Toast.makeText(this, "No Internet Connection ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClickListener(int itemId) {

    }
}
