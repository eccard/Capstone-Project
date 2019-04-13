package com.eccard.conquer.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.eccard.conquer.R;
import com.eccard.conquer.data.DataManager;
import com.eccard.conquer.data.local.db.dao.TaskDao;
import com.eccard.conquer.ui.feed.FeedActivity;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import dagger.android.AndroidInjection;

public class TasksAppWidget extends AppWidgetProvider {

    static boolean needToUpdate;
    Context context;

    static LiveData<List<TaskDao.TaskGoal>> observeTasks;

     Observer<List<TaskDao.TaskGoal>> observeForLiveDataTasks = taskGoals -> {
        needToUpdate = true;
        getDataManager().cacheWidgetList(taskGoals);
        updateWidget(context);

    };

    @Inject
    DataManager dataManager = null;

    public DataManager getDataManager() {
        return dataManager;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context =context;
        if (dataManager == null) {
            AndroidInjection.inject(this, context);
        }

        if (observeTasks == null){
            observeTasks = dataManager.loadTaskGoalsOfDayWithLiveData(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
            observeTasks.observeForever(observeForLiveDataTasks);
        }

        super.onReceive(context, intent);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

//        appSharePref = new AppSharePref(context);

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }


    private void updateAppWidget(Context context,
                                 AppWidgetManager appWidgetManager,
                                 int appWidgetId){

        RemoteViews views = getTasksListRemoteView(context);
        List<TaskDao.TaskGoal> tasksList = getDataManager().getWidgetList();
        if (tasksList != null && !tasksList.isEmpty()){
            views.setTextViewText(R.id.appwidget_recipe_title,context.getString(R.string.tasks_for_today));
        }
        appWidgetManager.updateAppWidget(appWidgetId,views);

    }

   public static void updateWidget(Context context){

        Intent intent = new Intent(context, TasksAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, TasksAppWidget.class);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

        context.sendBroadcast(intent);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_listView_ingredients);
    }

    private static RemoteViews getTasksListRemoteView(Context context){

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_tasks_of_day);

        Intent intent = new Intent(context, TasksWidgetViewService.class);

// when intents are compared, the extras are ignored, so we need to
// embed the extras into the data so that the extras will not be ignored
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));


        views.setRemoteAdapter(R.id.appwidget_listView_ingredients, intent);
        views.setEmptyView(R.id.appwidget_listView_ingredients, R.id.appwidget_empty_view);

        Intent intentPending = new Intent(context,FeedActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, 0, intentPending, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.appwidget_listView_ingredients,pendingIntent);

        return views;
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        observeTasks.removeObserver(observeForLiveDataTasks);
        super.onDeleted(context, appWidgetIds);
    }
}
