package com.example.jsonmovies.view;


import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.jsonmovies.R;
import com.example.jsonmovies.data.MovieDetails;
import com.example.jsonmovies.data.MovieSummary;
import com.example.jsonmovies.presenter.MovieDetailsPresenter;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class MovieDetailsActivity extends BaseActivity implements MovieDetailsPresenter.MovieDetailsView {
    private static final String PRESENTER_HOLDER_TAG = "movieListPresenterHolder";

    @Inject MovieDetailsPresenter presenter;
    @Bind(R.id.progressBar) ProgressBar progressBar;
    @Bind(R.id.moviePosterArt) ImageView posterArt;
    @Bind(R.id.movieTitle) TextView title;
    @Bind(R.id.movieYear) TextView year;
    @Bind(R.id.movieRank) TextView rank;
    @Bind(R.id.movieVotes) TextView votes;
    @Bind(R.id.movieRating) TextView rating;
    @Bind(R.id.movieRated)TextView rated;
    @Bind(R.id.movieReleased)TextView released;
    @Bind(R.id.movieRuntime)TextView runtime;
    @Bind(R.id.movieGenres)TextView genre;
    @Bind(R.id.movieDirector)TextView director;
    @Bind(R.id.movieWriter)TextView writer;
    @Bind(R.id.movieActors)TextView actors;
    @Bind(R.id.moviePlot)TextView plot;
    @Bind(R.id.movieLanguage)TextView language;
    @Bind(R.id.movieCountry)TextView country;
    @Bind(R.id.movieAwards)TextView awards;
    @Bind(R.id.movieMetaScore)TextView metaScore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        PresenterHolderFragment fragment = (PresenterHolderFragment)fragmentManager.findFragmentByTag(PRESENTER_HOLDER_TAG);

        if(fragment == null) {
            getIntent().getExtras();
            getApplicationComponent().inject(this);
            fragmentManager.beginTransaction().add(new PresenterHolderFragment(presenter), PRESENTER_HOLDER_TAG).commit();
            presenter.setData((MovieSummary) getIntent().getExtras().getParcelable("movieSummary"));
        } else {
            presenter = fragment.presenter;
        }
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setData(MovieDetails details) {
        Picasso.with(this)
               .load(Uri.parse(details.getSummary().getPosterUrl()))
               .into(posterArt);
        title.setText(details.getSummary().getTitle());
        year.setText("(" + details.getSummary().getYear() + ")");
        rank.setText(String.valueOf(details.getSummary().getRank()));
        votes.setText(String.valueOf(details.getSummary().getImbdVotes()));
        rating.setText(String.valueOf(details.getSummary().getImbdRating()));
        rated.setText(details.getRated());
        released.setText(details.getReleased());
        runtime.setText(details.getRuntime());
        genre.setText(details.getGenres());
        director.setText(details.getDirector());
        writer.setText(details.getWriter());
        actors.setText(details.getActors());
        plot.setText(details.getPlot());
        language.setText(details.getLanguages());
        country.setText(details.getCountry());
        awards.setText(details.getAwards());
        metaScore.setText(details.getMetaScore());
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @SuppressLint("ValidFragment")
    private class PresenterHolderFragment extends Fragment {
        final MovieDetailsPresenter presenter;

        public PresenterHolderFragment(MovieDetailsPresenter presenter) {
            this.presenter = presenter;
            setRetainInstance(true);
        }
    }
}
