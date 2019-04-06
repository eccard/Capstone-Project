package com.eccard.conquer.data.local.db;

import android.content.Context;

import com.eccard.conquer.data.local.db.dao.GoalDao;
import com.eccard.conquer.data.local.db.dao.TaskDao;
import com.eccard.conquer.data.model.db.Goal;
import com.eccard.conquer.data.model.db.Task;
import com.eccard.conquer.utils.AppConstants;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class AppDbHelperTest {

    private AppDatabase appDatabase;

    private GoalDao goalDao;
    private TaskDao taskDao;

    @Before
    public void setUp() throws Exception {

        Context context = ApplicationProvider.getApplicationContext();
        appDatabase =
                Room.databaseBuilder(context, AppDatabase.class, AppConstants.DB_NAME).fallbackToDestructiveMigration()
                .build();

        goalDao = appDatabase.goalDao();
        taskDao = appDatabase.taskDao();
    }

    @After
    public void tearDown() throws Exception {
        appDatabase.close();
    }

    @Test
    public void createGoalsTest() throws Exception {
        long goalId = goalDao.insert(new Goal("Estudar biologia"));
        taskDao.insert(new Task(goalId,"Ecologia","Estudar umas 3 horas",1,"11:30"));
        taskDao.insert(new Task(goalId,"Genética","Estudar umas 3 horas",2,"11:30"));
        taskDao.insert(new Task(goalId,"Evolução","Estudar umas 3 horas",2,"11:40"));
        taskDao.insert(new Task(goalId,"Fisiologia e Parasitologia","Estudar umas 3 horas",3,"11:50"));
        taskDao.insert(new Task(goalId,"Orgãos"," Sistema Reprodutor Masculino",4,"11:30"));
        taskDao.insert(new Task(goalId,"Orgãos"," Pulmão",5,"11:30"));
        taskDao.insert(new Task(goalId,"Orgãos","  Intestino Grosso",6,"11:30"));
        taskDao.insert(new Task(goalId,"Orgãos","Cérebro",7,"11:30"));
        taskDao.insert(new Task(goalId,"Corpo Humano","Estudar umas 3 horas",7,"11:30"));
    }


}