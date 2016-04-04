package com.example.jsonmovies.presenter;

public interface Presenter<T> {
    void attachView(T view);
    void detachView();
}
