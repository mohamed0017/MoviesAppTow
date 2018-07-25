package com.movies.test.moviesapp2.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by user on 7/14/2018.
 */
@Entity(tableName = "FavoriteMovies")
public class favoritesEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String overview;
    private String release_date;
    private String vote_average;

    @Ignore
    public favoritesEntry(String title, String overview, String vote_average, String release_date) {
        this.title = title;
        this.overview=overview;
        this.vote_average=vote_average;
        this.release_date=release_date;
    }

    public favoritesEntry(int id, String title, String overview, String vote_average, String release_date) {
        this.id = id;
        this.title = title;
        this.overview=overview;
        this.vote_average=vote_average;
        this.release_date=release_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }



}
