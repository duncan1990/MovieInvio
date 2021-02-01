package com.ahmety.movieinvio.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmety.movieinvio.Adapter.MovieListAdapter;
import com.ahmety.movieinvio.Api.ApiClient;
import com.ahmety.movieinvio.Api.ApiErrorUtils;
import com.ahmety.movieinvio.Api.ApiInterface;
import com.ahmety.movieinvio.Model.Movie;
import com.ahmety.movieinvio.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {
   String mTtitle, mPoster, mGenre, mPlot, mYear, mRuntime, mCountry, mImdb, mRotten;
   ImageView imageViewMovieDetail;
   TextView textViewMGenre, textViewMPlot, textViewMYear, textMCountry, textMRuntime, textMImdb, textMRotten;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        imageViewMovieDetail = findViewById(R.id.imageViewMovieDetail);
        textViewMGenre = findViewById(R.id.textViewMGenre);
        textViewMPlot = findViewById(R.id.textViewMPlot);
        textViewMYear = findViewById(R.id.textViewMYear);
        textMCountry = findViewById(R.id.textViewMCountry);
        textMRuntime = findViewById(R.id.textViewMRuntime);
        textMImdb = findViewById(R.id.textViewMImdb);
        textMRotten = findViewById(R.id.textViewMRotten);
        // MovieLstAdapter'dan gelen film bilgilerini burada alıp değişkene atıyoruz.
        getMovieData();
        getSupportActionBar().setTitle(mTtitle);   //ActionBar'daki text'i değiştiriyoruz
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadMovies();

    }

    private void getMovieData() {
        Bundle bundleAdapter = getIntent().getExtras();
        mPoster = bundleAdapter.getString("mPoster");
        mTtitle = bundleAdapter.getString("mTitle");
        mGenre = bundleAdapter.getString("mGenre");
        mPlot = bundleAdapter.getString("mPlot");
        mYear = bundleAdapter.getString("mYear");
        mRuntime = bundleAdapter.getString("mRuntime");
        mCountry = bundleAdapter.getString("mCountry");
        mImdb = bundleAdapter.getString("mImdb");
        mRotten = bundleAdapter.getString("mRotten");
    }

    private void loadMovies() {
        Glide.with(getApplicationContext()).load(mPoster)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_glide_img).error(R.drawable.ic_glide_warning)).
                into(imageViewMovieDetail);
        textViewMGenre.setText(mGenre);
        textViewMPlot.setText(mPlot);
        textViewMYear.setText(getString(R.string.textYear) + " " + mYear);
        textMCountry.setText(getString(R.string.textCountry) + " " + mCountry);
        textMRuntime.setText(mRuntime);
        textMImdb.setText(getString(R.string.textImdb) + " " + mImdb);
        textMRotten.setText(getString(R.string.textRotten) + " " + mRotten);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_out_reverse, R.anim.right_in_reverse);
    }
}