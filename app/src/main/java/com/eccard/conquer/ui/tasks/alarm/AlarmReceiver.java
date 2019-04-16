package com.eccard.conquer.ui.tasks.alarm;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.eccard.conquer.utils.CommonUtils;

import androidx.legacy.content.WakefulBroadcastReceiver;
import timber.log.Timber;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.d("*******onStartJob");

        if (CommonUtils.checkAlarmItent(intent)){
            Intent alarmIntent = AlarmActivity.newIntent(context,intent.getExtras().getString(AlarmActivity.ARG_GOAL_NAME),
                    intent.getExtras().getString(AlarmActivity.ARG_TASK_DESCRIPTION));

            context.startActivity(alarmIntent);
        } else {
            Timber.e("onReceive without alarmIntent");
        }

        ComponentName comp = new ComponentName(context.getPackageName(),
                AlarmService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));

        setResultCode(Activity.RESULT_OK);

    }

}
