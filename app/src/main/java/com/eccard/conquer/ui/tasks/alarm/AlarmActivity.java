package com.eccard.conquer.ui.tasks.alarm;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

import com.eccard.conquer.BR;
import com.eccard.conquer.R;
import com.eccard.conquer.ViewModelProviderFactory;
import com.eccard.conquer.databinding.ActivityAlarmBinding;
import com.eccard.conquer.ui.base.BaseActivity;
import com.eccard.conquer.utils.CommonUtils;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class AlarmActivity extends BaseActivity<ActivityAlarmBinding, AlarmViewModel>
    implements AlarmNavigator {


    public static final String ARG_GOAL_NAME   = "goalName";
    public static final String ARG_TASK_DESCRIPTION = "taskDescription";
    private Ringtone ringtone;

    public static Intent newIntent(Context context, String goalName, String taskDescription ){
        Intent intent = new Intent(context, AlarmActivity.class);
        intent.putExtra(ARG_GOAL_NAME,goalName);
        intent.putExtra(ARG_TASK_DESCRIPTION,taskDescription);
        return intent;
    }

    @Inject
    ViewModelProviderFactory factory;

    private ActivityAlarmBinding alarmBinding;
    private AlarmViewModel alarmViewModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_alarm;
    }

    @Override
    public AlarmViewModel getViewModel() {
        alarmViewModel = ViewModelProviders.of(this,factory).get(AlarmViewModel.class);
        return alarmViewModel;
    }

    @Override
    public void openDaySheet(int day) {

    }

    @Override
    public void stopAlarm() {
        ringtone.stop();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmBinding = getViewDataBinding();
        alarmBinding.setViewModel(alarmViewModel);
        alarmViewModel.setNavigator(this);

        setUp();
    }

    private void setUp() {
        if (CommonUtils.checkAlarmItent(getIntent())){
            alarmViewModel.setGoalName(getIntent().getExtras().getString(ARG_GOAL_NAME));
            alarmViewModel.setTaskDescription(getIntent().getExtras().getString(ARG_TASK_DESCRIPTION));

            //this will sound the alarm tone
            //this will sound the alarm once, if you wish to
            //raise alarm in loop continuously then use MediaPlayer and setLooping(true)
            Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (alarmUri == null) {
                alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }

            ringtone = RingtoneManager.getRingtone(AlarmActivity.this, alarmUri);
            ringtone.play();
        }
    }
}
