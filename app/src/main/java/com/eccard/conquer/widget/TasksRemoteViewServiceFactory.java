package com.eccard.conquer.widget;

import android.content.Context;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.eccard.conquer.data.DataManager;
import com.eccard.conquer.data.local.db.dao.TaskDao;

import timber.log.Timber;

public class TasksRemoteViewServiceFactory implements RemoteViewsService.RemoteViewsFactory {

    DataManager dataManager;
    final Context context;

    public TasksRemoteViewServiceFactory(Context applicationContext, DataManager dataManager) {
        this.context = applicationContext;
        this.dataManager = dataManager;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        int ret = dataManager.getWidgetList() != null ? dataManager.getWidgetList().size() : 0;
        Timber.d("getCount="+ret);

        return ret;

    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION || dataManager.getWidgetList() == null ||
                dataManager.getWidgetList().isEmpty()) {
            return null;
        }

        RemoteViews view = new RemoteViews(context.getPackageName(), android.R.layout.simple_list_item_1);
        TaskDao.TaskGoal task = dataManager.getWidgetList().get(position);
        view.setTextViewText(android.R.id.text1,task.taskName);


//        todo habilitar o click
//        Intent fillIntent = new Intent();
//        fillIntent.putExtra(Recip.class.getSimpleName(), recip);
//        views.setOnClickFillInIntent(R.id.textView_ingredient_title, fillIntent);

        return view;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
