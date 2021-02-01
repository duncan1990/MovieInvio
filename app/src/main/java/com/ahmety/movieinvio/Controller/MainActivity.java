package com.ahmety.movieinvio.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.ahmety.movieinvio.Adapter.MovieListAdapter;
import com.ahmety.movieinvio.Api.ApiClient;
import com.ahmety.movieinvio.Api.ApiErrorUtils;
import com.ahmety.movieinvio.Api.ApiInterface;
import com.ahmety.movieinvio.Model.Movie;
import com.ahmety.movieinvio.R;
import com.airbnb.lottie.LottieAnimationView;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    String movieTitle, aKey;
    ConstraintLayout constraintLayout;
    AutoCompleteTextView searchText;
    LottieAnimationView lottieAnimationProgress, lottieAnimationLoading;
    AppCompatButton searchButton;
    RecyclerView mainRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    Activity thisActivity=(Activity)this; // Adapter'dan sayfa geçiş animasyonu kullanabilmek için bu kodu yazdık.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        constraintLayout = findViewById(R.id.consLayout);
        searchText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.buttonSearch);
        lottieAnimationProgress = findViewById(R.id.animationViewProgress);
        lottieAnimationLoading = findViewById(R.id.animationViewLoading);
        mainRecyclerView = findViewById(R.id.main_recyclerview);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mainRecyclerView.setLayoutManager(mLayoutManager);
        registerHandlers();
        getSupportActionBar().setTitle(R.string.main_title);
        lottieAnimationProgress.setVisibility(View.VISIBLE);
        decreaseScreenOpacity();

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new   Runnable() {
                            public void run() {
                                lottieAnimationProgress.setVisibility(View.GONE);
                                increaseScreenOpacity();
                            }
                        });
                    }
                },
                5000
        );
    }

    private void decreaseScreenOpacity() {
        searchText.setAlpha(0.2f);
        searchText.setEnabled(false);
        searchButton.setAlpha(0.2f);
        searchButton.setEnabled(false);
    }
    private void increaseScreenOpacity() {
        searchText.setAlpha(1f);
        searchText.setEnabled(true);
        searchButton.setAlpha(1f);
        searchButton.setEnabled(true);
    }
    private void registerHandlers() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lottieAnimationLoading.setVisibility(View.VISIBLE);
                decreaseScreenOpacity();
                movieTitle = searchText.getText().toString();
                aKey = "ffe9063f";
                hideKeyboard((Button)v); // butona basıldıktan sonra açık olan keyboard gizlemek için
                if (movieTitle.equals("")){
                    lottieAnimationLoading.setVisibility(View.GONE);
                    increaseScreenOpacity();
                    Toast.makeText(MainActivity.this, R.string.search_text_null, Toast.LENGTH_SHORT).show();
                } else {
                    loadMovies();
                }
            }
        });

        //Keyboard açıkken başka consLayout'a basılırsa keyboard'un kapanması için yazdım bu kodu
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard((ConstraintLayout)v);
            }
        });
    }

    // Film verilerini Api'den aldığımız ve recyclerview adapter'ına yerleştirdiğimiz bölüm
    private void loadMovies() {
        ApiInterface apiInterface = ApiClient.getRetrofitInstance().create(ApiInterface.class);
        Call<Movie> call = apiInterface.getMovies(movieTitle,aKey);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    lottieAnimationLoading.setVisibility(View.GONE);
                    increaseScreenOpacity();
                    Movie movies_list = response.body();

                    MovieListAdapter myAdapter = new MovieListAdapter(getApplicationContext(), R.layout.item_movies, Collections.singletonList(movies_list), thisActivity);
                    DividerItemDecoration divider = new
                            DividerItemDecoration(mainRecyclerView.getContext(),
                            DividerItemDecoration.VERTICAL);
                    divider.setDrawable(
                            ContextCompat.getDrawable(getBaseContext(), R.drawable.line_divider)
                    );
                    mainRecyclerView.addItemDecoration(divider);
                    mainRecyclerView.setAdapter(myAdapter);


                } else {
                    lottieAnimationLoading.setVisibility(View.GONE);
                    increaseScreenOpacity();
                    ApiErrorUtils.parseError(response);
                    Toast.makeText(MainActivity.this, R.string.error_fail_service_connection, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                lottieAnimationLoading.setVisibility(View.GONE);
                increaseScreenOpacity();
                Toast.makeText(MainActivity.this, R.string.error_fail_service_connection, Toast.LENGTH_SHORT).show();
                //Log.d("response", "apiError");
            }
        });
    }

    // Keyboard gizlemek için kullanılan fonksiyon
    public void hideKeyboard(View view) {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch(Exception ignored) {
        }
    }

}