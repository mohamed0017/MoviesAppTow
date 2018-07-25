package com.movies.test.moviesapp2.api;

import com.movies.test.moviesapp2.model.movie;
import com.movies.test.moviesapp2.model.rating;
import com.movies.test.moviesapp2.model.videos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by user on 7/7/2018.
 */

public interface ApiInterface {

    String Api_key = "d06277cedd76c9b43c7264b803e1fa64";
    String BasicUrl = "api.themoviedb.org";

    @GET("/3/movie/top_rated")
    Call<movie> getTopRated();

    @GET("/3/movie/popular")
    Call<movie> getPopular();

    @GET("/3/movie/top_rated")
    Call<movie> gettop_rated();

    @GET("/3/movie/{id}/videos")
    Call<videos> getVideo(@Path("id") int id);

    @GET("/3/movie/{id}/reviews")
    Call<rating> getReviews(@Path("id") int id);
}
