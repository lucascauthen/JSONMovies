package com.example.jsonmovies;

import android.app.Application;
import com.example.jsonmovies.di.ApplicationComponent;
import com.example.jsonmovies.di.ApplicationModule;
import com.example.jsonmovies.di.DaggerApplicationComponent;


public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                                                              .applicationModule(new ApplicationModule(this))
                                                              .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }



}
