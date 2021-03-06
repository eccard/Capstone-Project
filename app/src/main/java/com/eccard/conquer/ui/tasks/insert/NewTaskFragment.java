package com.eccard.conquer.ui.tasks.insert;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import com.eccard.conquer.BR;
import com.eccard.conquer.R;
import com.eccard.conquer.ViewModelProviderFactory;
import com.eccard.conquer.data.local.db.dao.TaskDao;
import com.eccard.conquer.data.model.db.Task;
import com.eccard.conquer.databinding.FragmentNewTaskBinding;
import com.eccard.conquer.ui.base.BaseFragment;
import com.eccard.conquer.ui.main.MainActivity;
import com.eccard.conquer.ui.tasks.alarm.AlarmActivity;
import com.eccard.conquer.ui.tasks.alarm.AlarmReceiver;
import com.eccard.conquer.utils.CommonUtils;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import timber.log.Timber;

import static android.content.Context.ALARM_SERVICE;

public class NewTaskFragment extends BaseFragment<FragmentNewTaskBinding,NewTaskViewModel> implements NewTaskNavigator, TimePickerDialog.OnTimeSetListener {


    public static final String TAG = NewTaskFragment.class.getSimpleName();
    private static final String ARG_TASK_ID_PARAM = "taskId";
    private static final String ARG_TASK_GOAL_ID_PARAM = "taskGoalId";
    private static final String ARG_TASK_NAME_PARAM = "taskName";
    private static final String ARG_TASK_DESCRIPTION_PARAM = "taskDescription";
    private static final String ARG_TASK_TIME_PARAM = "taskTime";
    private static final String ARG_TASK_DAY_OF_WEEKEND = "dayOfWeekend";
    private static final String ARG_TASK_COLOR_PARAM = "taskColor";


    private FragmentNewTaskBinding fragmentNewTaskBinding;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;
    private NewTaskViewModel newTaskViewModel;
    private Task task;


    public static NewTaskFragment newInstance(Long goalId) {
        Bundle args = new Bundle();
        args.putLong(ARG_TASK_GOAL_ID_PARAM,goalId);
        NewTaskFragment fragment = new NewTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static NewTaskFragment newInstance(Task task) {
        Bundle args = new Bundle();
        args.putLong(ARG_TASK_ID_PARAM,task.id);
        args.putLong(ARG_TASK_GOAL_ID_PARAM,task.goalId);
        args.putString(ARG_TASK_NAME_PARAM,task.name);
        args.putString(ARG_TASK_DESCRIPTION_PARAM,task.description);
        args.putLong(ARG_TASK_TIME_PARAM,task.time);
        args.putInt(ARG_TASK_DAY_OF_WEEKEND,task.dayOfWeekend);
        args.putString(ARG_TASK_COLOR_PARAM,task.color);

        NewTaskFragment fragment = new NewTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_new_task;
    }

    @Override
    public NewTaskViewModel getViewModel() {
        newTaskViewModel = ViewModelProviders.of(this,viewModelProviderFactory).get(NewTaskViewModel.class);
        return newTaskViewModel;
    }

    private Task checkiFBundleHasTask(Bundle b){
        if (b !=null &&
            b.containsKey(ARG_TASK_ID_PARAM) &&
            b.containsKey(ARG_TASK_GOAL_ID_PARAM) &&
            b.containsKey(ARG_TASK_NAME_PARAM) &&
            b.containsKey(ARG_TASK_DESCRIPTION_PARAM) &&
            b.containsKey(ARG_TASK_TIME_PARAM) &&
            b.containsKey(ARG_TASK_DAY_OF_WEEKEND) &&
            b.containsKey(ARG_TASK_COLOR_PARAM)){

            Task task = new Task();
            task.id = b.getLong(ARG_TASK_ID_PARAM);
            task.goalId = b.getLong(ARG_TASK_GOAL_ID_PARAM);
            task.name = b.getString(ARG_TASK_NAME_PARAM);
            task.description = b.getString(ARG_TASK_DESCRIPTION_PARAM);
            task.time = b.getLong(ARG_TASK_TIME_PARAM);
            task.dayOfWeekend = b.getInt(ARG_TASK_DAY_OF_WEEKEND);
            task.color = b.getString(ARG_TASK_COLOR_PARAM);

            return task;
        } else {
            return null;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        newTaskViewModel.setNavigator(this);

        task = checkiFBundleHasTask(getArguments());
        if ( task != null ){
            newTaskViewModel.setGoalId(task.goalId);
            newTaskViewModel.setTaskId(task.id);
        } else if (getArguments() != null && getArguments().containsKey(ARG_TASK_GOAL_ID_PARAM)){
            Long goalId = getArguments().getLong(ARG_TASK_GOAL_ID_PARAM);
            newTaskViewModel.setGoalId(goalId);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentNewTaskBinding = getViewDataBinding();

        setupView();

    }

    private void setupView(){
        setUpDaySpinner();

        if (task != null){
            fragmentNewTaskBinding.inputLayoutTask.getEditText().setText(task.name);
            fragmentNewTaskBinding.inputLayoutTaskDescription.getEditText().setText(task.description);
            fragmentNewTaskBinding.editTextTaskTime.setText(CommonUtils.getStringValueFromTime(task.time));

            fragmentNewTaskBinding.spinnerDayOfWeekend.setSelection(task.dayOfWeekend);

            setEditable(false);
        }



        fragmentNewTaskBinding.editTextTaskTime.setOnClickListener(v -> {
            DialogFragment newFragment = new TimerPickFragment();
            newFragment.show(getChildFragmentManager(),TimerPickFragment.TAG);
        });

    }

    private void setUpDaySpinner() {
        String[] stringArray = getResources().getStringArray(R.array.daysOfWeekend);

        final List<String> dayList = new ArrayList<>(Arrays.asList(stringArray));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_item,dayList){
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view =  super.getDropDownView(position, convertView, parent);

                TextView tv = (TextView) view;
                if (position == 0){
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }};

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        fragmentNewTaskBinding.spinnerDayOfWeekend.setAdapter(spinnerArrayAdapter);
        fragmentNewTaskBinding.spinnerDayOfWeekend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                }
                Timber.d("onItemSelected position="+position);
                newTaskViewModel.setDayOfWeekend(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setEditable(boolean canEdit){
        fragmentNewTaskBinding.inputLayoutTask.getEditText().setEnabled(canEdit);
        fragmentNewTaskBinding.inputLayoutTaskDescription.getEditText().setEnabled(canEdit);
        fragmentNewTaskBinding.spinnerDayOfWeekend.setEnabled(canEdit);
        fragmentNewTaskBinding.editTextTaskTime.setEnabled(canEdit);
        fragmentNewTaskBinding.alarmCheckBox.setEnabled(canEdit);

        if (canEdit){
            fragmentNewTaskBinding.inputLayoutTask.getEditText().requestFocus();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        if (task != null){
            inflater.inflate(R.menu.frg_new_task_menu,menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Timber.d("called onOptionsItemSelected item = " + item.toString());

        switch (item.getItemId()){
            case R.id.action_edit:
                setEditable(true);
                break;
            case R.id.action_delete_task:

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(R.string.delete_task_confirm);
            builder.setPositiveButton(android.R.string.ok, (dialog, which) -> newTaskViewModel.deleteTask(task));
            builder.setNegativeButton(android.R.string.cancel,null);
            builder.show();

            break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        long time = CommonUtils.getLongValueFromTime(hourOfDay,minute);
        newTaskViewModel.setTaskTime(time);

        String timeString = CommonUtils.getStringValueFromTime(time);
        fragmentNewTaskBinding.editTextTaskTime.setText(timeString);
        fragmentNewTaskBinding.editTextTaskTime.clearFocus();

    }

    @Override
    public void startAlarm(Long taskTime, TaskDao.TaskGoal taskGoal) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Time(taskTime));

        AlarmManager alarmManager;
        if (getContext() != null){
            alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);

            Intent myIntent = new Intent(getContext(), AlarmReceiver.class);
            myIntent.putExtra(AlarmActivity.ARG_GOAL_NAME, taskGoal.goalName);
            myIntent.putExtra(AlarmActivity.ARG_TASK_DESCRIPTION,taskGoal.taskDescription);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, myIntent, 0);
            if (alarmManager != null) {
                alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
//                alarmManager.setInexactRepeating(AlarmManager.RTC,calendar.getTimeInMillis(), TimeUnit.DAYS.toMillis(7),pendingIntent);
//                alarmManager.setInexactRepeating(AlarmManager.RTC,calendar.getTimeInMillis(), TimeUnit.SECONDS.toMillis(30),pendingIntent);
            }
        }

    }

    @Override
    public void goTasks(Long goalId) {
        ((MainActivity) getActivity()).goTasks(goalId);
    }
}
