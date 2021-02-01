package com.ahmety.movieinvio.Api;

import com.ahmety.movieinvio.Model.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET(".")
        Call<Movie> getMovies(@Query("t") String title,
                              @Query("apikey") String aKey);
}
