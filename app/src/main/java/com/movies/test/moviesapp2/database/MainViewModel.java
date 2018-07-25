package com.movies.test.moviesapp2.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<favoritesEntry>> Favorites;


    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());

        Favorites= database.favouriteDeo().loadAllFavMovies();
    }

    public LiveData<List<favoritesEntry>> getFavorites() {
        return Favorites;
    }
}
