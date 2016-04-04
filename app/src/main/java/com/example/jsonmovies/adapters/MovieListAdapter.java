package com.example.jsonmovies.adapters;

import android.content.Context;
import android.graphics.Movie;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.jsonmovies.R;
import com.example.jsonmovies.data.MovieSummary;
import com.example.jsonmovies.view.MovieListActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private final MovieListActivity context;
    private List<MovieSummary> data;
    public MovieListAdapter(MovieListActivity context) {
        this.context = context;
        this.data = new ArrayList<>();
    }

    public void setData(List<MovieSummary> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view_movie, parent, false);
        view.setOnClickListener(context);
        MovieViewHolder holder = new MovieViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        MovieSummary cur = data.get(position);
        Picasso.with(context)
               .load(Uri.parse(cur.getPosterUrl()))
                .into(holder.posterArt);
        holder.title.setText(cur.getTitle());
        holder.year.setText("(" + cur.getYear() + ")");
        holder.rank.setText(String.valueOf(cur.getRank()));
        holder.votes.setText(String.valueOf(cur.getImbdVotes()));
        holder.rating.setText(String.valueOf(cur.getImbdRating()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.moviePosterArt) ImageView posterArt;
        @Bind(R.id.movieTitle) TextView title;
        @Bind(R.id.movieYear) TextView year;
        @Bind(R.id.movieRank) TextView rank;
        @Bind(R.id.movieVotes) TextView votes;
        @Bind(R.id.movieRating) TextView rating;

        private MovieSummary summary;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(MovieSummary summary) {
            this.summary = summary;
        }
    }
}
