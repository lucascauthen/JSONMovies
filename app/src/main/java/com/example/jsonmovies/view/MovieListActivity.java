package com.example.jsonmovies.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.jsonmovies.R;
import com.example.jsonmovies.adapters.MovieListAdapter;
import com.example.jsonmovies.data.MovieSummary;
import com.example.jsonmovies.presenter.MovieListPresenter;

import javax.inject.Inject;
import java.util.List;

public class MovieListActivity extends BaseActivity implements MovieListPresenter.MovieListView, View.OnClickListener {
    private static final String PRESENTER_HOLDER_TAG = "movieListPresenterHolder";

    @Bind(R.id.movieRecyclerView) RecyclerView recyclerView;
    @Bind(R.id.progressBar) ProgressBar progressBar;

    @Inject MovieListPresenter presenter;
    private MovieListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);
        getApplicationComponent().inject(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MovieListAdapter(this);
        recyclerView.setAdapter(adapter);
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void setData(List<MovieSummary> data) {
        this.adapter.setData(data);
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
    public void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        MovieSummary movieSummary = presenter.getSummary(recyclerView.getChildAdapterPosition(v));
        Intent intent = new Intent(getBaseContext(), MovieDetailsActivity.class);
        intent.putExtra("movieSummary", movieSummary);
        startActivity(intent);
    }
}