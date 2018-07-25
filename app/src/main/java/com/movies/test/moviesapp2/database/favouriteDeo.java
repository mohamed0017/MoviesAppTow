package com.movies.test.moviesapp2.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.movies.test.moviesapp2.model.results;

import java.util.List;


/**
 * Created by user on 7/14/2018.
 */
@Dao
public interface favouriteDeo {

    @Insert
    void insertFavorites(favoritesEntry favoriteentry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavorites(favoritesEntry favoriteentry);

    @Delete
    void deleteFavorites(favoritesEntry favoriteentry);

    @Query("SELECT * FROM favoritemovies ORDER BY id")
    LiveData<List<favoritesEntry>> loadAllFavMovies();

    @Query("SELECT * FROM favoritemovies WHERE id = :id")
    LiveData<favoritesEntry> loadFavoritesById(int id);

}
