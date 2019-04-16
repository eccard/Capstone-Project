package com.eccard.conquer.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.eccard.conquer.data.DataManager;
import com.eccard.conquer.data.local.db.dao.TaskDao;
import com.eccard.conquer.ui.timesheet.TimeSheetActivity;
import com.eccard.conquer.utils.CommonUtils;

import java.util.Calendar;

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
        String text = CommonUtils.getStringValueFromTime(task.taskTime);
        view.setTextViewText(android.R.id.text1,text.concat(" - ").concat(task.taskName));


        Intent fillIntent = new Intent();
        fillIntent.putExtra(TimeSheetActivity.ARG_DAY,Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        view.setOnClickFillInIntent(android.R.id.text1,fillIntent);

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
