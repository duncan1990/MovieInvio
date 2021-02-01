package com.ahmety.movieinvio.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.ahmety.movieinvio.Model.Movie;
import com.ahmety.movieinvio.Controller.MovieDetailActivity;
import com.ahmety.movieinvio.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;


public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {
    private Context mContext;
    int rawLayout;
    private List<Movie> moviesList;
    Activity mActivity;

    public MovieListAdapter(Context mContext, int rawLayout, List<Movie> moviesList, Activity mActivity) {
        this.mContext = mContext;
        this.rawLayout = rawLayout;
        this.moviesList = moviesList;
        this.mActivity=mActivity;
    }


    @NonNull
    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(rawLayout, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       Glide.with(mContext).load(moviesList.get(position).getPoster())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_glide_img).error(R.drawable.ic_glide_warning))
                .into(holder.imgMovie);
        holder.textMovieName.setText(moviesList.get(position).getTitle());
        holder.textMovieDescription.setText(moviesList.get(position).getGenre());
        holder.textMovieDescription.setText(moviesList.get(position).getPlot());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, MovieDetailActivity.class);
                i.putExtra("mPoster", moviesList.get(position).getPoster());
                i.putExtra("mTitle",moviesList.get(position).getTitle());
                i.putExtra("mGenre",moviesList.get(position).getGenre());
                i.putExtra("mPlot", moviesList.get(position).getPlot());
                i.putExtra("mYear", moviesList.get(position).getYear());
                i.putExtra("mRuntime",moviesList.get(position).getRuntime());
                i.putExtra("mCountry",moviesList.get(position).getCountry());
                i.putExtra("mImdb", moviesList.get(position).getImdbRating());
                if(moviesList.get(position).getRatings().size() > 1) {
                    i.putExtra("mRotten", moviesList.get(position).getRatings().get(1).getValue());
                }
                else{
                    i.putExtra("mRotten", "-");
                }
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
                //mActivity.overridePendingTransition(R.anim.left_out, R.anim.right_in);
                mActivity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgMovie;
        private TextView textMovieName, textMovieType, textMovieDescription;
        private CardView cardView;
        ViewHolder(View itemView){
            super(itemView);
            cardView = itemView.findViewById(R.id.CardView);
            imgMovie = itemView.findViewById(R.id.imageViewMovie);
            textMovieName = itemView.findViewById(R.id.textViewMovieName);
            textMovieType = itemView.findViewById(R.id.textViewMovieType);
            textMovieDescription = itemView.findViewById(R.id.textViewMovieDesc);
        }
    }
}
