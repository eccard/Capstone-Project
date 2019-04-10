package com.eccard.conquer.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.eccard.conquer.data.DataManager;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class TasksWidgetViewService extends RemoteViewsService {

    @Inject
    DataManager dataManager = null;

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new TasksRemoteViewServiceFactory(this.getApplicationContext(),dataManager);
    }
}
