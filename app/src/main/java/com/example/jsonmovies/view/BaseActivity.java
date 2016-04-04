package com.example.jsonmovies.view;

import android.support.v7.app.AppCompatActivity;
import com.example.jsonmovies.AndroidApplication;
import com.example.jsonmovies.di.ApplicationComponent;

public abstract class BaseActivity extends AppCompatActivity {

    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication)getApplication()).getApplicationComponent();
    }

}
