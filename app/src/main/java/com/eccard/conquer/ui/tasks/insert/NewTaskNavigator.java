package com.eccard.conquer.ui.tasks.insert;

import com.eccard.conquer.data.local.db.dao.TaskDao;

public interface NewTaskNavigator {

    void goTasks(Long goalId);

    void startAlarm(Long taskTime, TaskDao.TaskGoal taskGoal);
}
