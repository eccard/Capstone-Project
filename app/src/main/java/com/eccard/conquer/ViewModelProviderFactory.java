package com.eccard.conquer;

import com.eccard.conquer.data.DataManager;
import com.eccard.conquer.ui.goals.GoalsViewModel;
import com.eccard.conquer.ui.goals.insert.AddGoalViewModel;
import com.eccard.conquer.ui.main.MainViewModel;
import com.eccard.conquer.ui.tasks.TasksViewModel;
import com.eccard.conquer.ui.tasks.alarm.AlarmViewModel;
import com.eccard.conquer.ui.tasks.insert.NewTaskViewModel;
import com.eccard.conquer.ui.timesheet.TimeSheetViewModel;
import com.eccard.conquer.ui.timesheet.day.DayViewModel;
import com.eccard.conquer.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {

  private final DataManager dataManager;
  private final SchedulerProvider schedulerProvider;

  @Inject
  public ViewModelProviderFactory(DataManager dataManager,
      SchedulerProvider schedulerProvider) {
    this.dataManager = dataManager;
    this.schedulerProvider = schedulerProvider;
  }


  @Override
  public <T extends ViewModel> T create(Class<T> modelClass) {
      if (modelClass.isAssignableFrom(TimeSheetViewModel.class)) {
          //noinspection unchecked
          return (T) new TimeSheetViewModel(dataManager,schedulerProvider);
      } else if (modelClass.isAssignableFrom(MainViewModel.class)) {
          //noinspection unchecked
          return (T) new MainViewModel(dataManager,schedulerProvider);
      }
      else if (modelClass.isAssignableFrom(DayViewModel.class)) {
          //noinspection unchecked
          return (T) new DayViewModel(dataManager,schedulerProvider);
      } else if (modelClass.isAssignableFrom(GoalsViewModel.class)){
          //noinspection unchecked
          return (T) new GoalsViewModel(dataManager,schedulerProvider);
      } else if (modelClass.isAssignableFrom(TasksViewModel.class)){
          //noinspection unchecked
          return (T) new TasksViewModel(dataManager,schedulerProvider);
      } else if (modelClass.isAssignableFrom(AddGoalViewModel.class)){
          //noinspection unchecked
          return (T) new AddGoalViewModel(dataManager,schedulerProvider);
      } else if (modelClass.isAssignableFrom(NewTaskViewModel.class)){
          //noinspection unchecked
          return (T) new NewTaskViewModel(dataManager,schedulerProvider);
      }
      else if (modelClass.isAssignableFrom(AlarmViewModel.class)){
          //noinspection unchecked
          return (T) new AlarmViewModel(dataManager,schedulerProvider);
      }

      throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
  }
}