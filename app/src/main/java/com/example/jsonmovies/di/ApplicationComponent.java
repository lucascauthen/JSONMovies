package com.example.jsonmovies.di;

import com.example.jsonmovies.view.MovieDetailsActivity;
import com.example.jsonmovies.view.MovieListActivity;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(MovieListActivity movieListActivity);

    void inject(MovieDetailsActivity movieDetailsActivity);
}
