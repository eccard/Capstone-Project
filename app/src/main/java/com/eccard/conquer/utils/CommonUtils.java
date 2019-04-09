/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.eccard.conquer.utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.util.Patterns;
import com.eccard.conquer.R;
import com.eccard.conquer.ui.tasks.alarm.AlarmActivity;
import com.eccard.conquer.ui.tasks.alarm.AlarmReceiver;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

import static com.eccard.conquer.ui.tasks.insert.NewTaskFragment.TAG;

/**
 * Created by amitshekhar on 07/07/17.
 */

public final class CommonUtils {

    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getTimestamp() {
        return new SimpleDateFormat(AppConstants.TIMESTAMP_FORMAT, Locale.US).format(new Date());
    }

    public static boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String loadJSONFromAsset(Context context, String jsonFileName) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream is = manager.open(jsonFileName);

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static Long getLongValueFromTime(String timeString){

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        long timeLong = 0;
        try {
            timeLong = sdf.parse(timeString).getTime();
            Timber.d("timeString="+timeString);
            Timber.d("timeLong="+timeLong);
        } catch (ParseException e) {
            Log.d(TAG,Log.getStackTraceString(e));
        }

        return timeLong;
    }

    public static Long getLongValueFromTime(int hourOfDay,int minutes){
        Calendar date = Calendar.getInstance();
        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
        date.set(Calendar.MINUTE, minutes);
        date.set(Calendar.AM_PM, date.get(Calendar.AM_PM));

        return date.getTime().getTime();
    }


    public static String getStringValueFromTime(Long timeLong){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date(timeLong));
    }

    public static void scheduleJob(Context context,Long timeForAlarm) {
        ComponentName serviceComponent = new ComponentName(context, AlarmReceiver.class);

        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            Timber.d("setting builder.setPeriodic");
//            builder.setPeriodic(TimeUnit.DAYS.toMillis(7),TimeUnit.SECONDS.toMillis(20));
//        }

        builder.setMinimumLatency(timeForAlarm);
        builder.setRequiresCharging(false);
        JobScheduler jobScheduler = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            jobScheduler = context.getSystemService(JobScheduler.class);
        } else {
            jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);;
        }

        if (jobScheduler != null) {
            jobScheduler.schedule(builder.build());
        }
    }

    public static boolean checkAlarmItent(Intent intent){
        return intent.getExtras() != null &&
                intent.getExtras().containsKey(AlarmActivity.ARG_GOAL_NAME) &&
                intent.getExtras().containsKey(AlarmActivity.ARG_TASK_DESCRIPTION);
    }
}
