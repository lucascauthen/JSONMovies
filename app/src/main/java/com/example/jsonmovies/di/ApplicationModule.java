package com.example.jsonmovies.di;

import android.app.Application;
import android.content.Context;
import com.example.jsonmovies.AndroidApplication;
import com.example.jsonmovies.presenter.MovieDetailsPresenter;
import com.example.jsonmovies.presenter.MovieListPresenter;
import com.example.jsonmovies.util.BackgroundExecutor;
import com.example.jsonmovies.util.ForegroundExecutor;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

import javax.inject.Singleton;

@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    BackgroundExecutor provideBackgroundExecutor() {
        return new BackgroundExecutor();
    }

    @Provides
    @Singleton
    ForegroundExecutor provideForegroundExecutor() {
        return new ForegroundExecutor();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

    @Provides
    @Singleton
    MovieListPresenter provideMovieListPresenter(BackgroundExecutor backgroundExecutor, ForegroundExecutor foregroundExecutor, OkHttpClient okHttpClient) {
        return new MovieListPresenter(backgroundExecutor, foregroundExecutor, okHttpClient);
    }

    @Provides
    MovieDetailsPresenter provideMovieDetailsPresenter(BackgroundExecutor backgroundExecutor, ForegroundExecutor foregroundExecutor, OkHttpClient okHttpClient) {
        return new MovieDetailsPresenter(backgroundExecutor, foregroundExecutor, okHttpClient);
    }
}
