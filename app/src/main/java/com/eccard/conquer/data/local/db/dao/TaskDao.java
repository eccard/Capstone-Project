package com.eccard.conquer.data.local.db.dao;

import com.eccard.conquer.data.model.db.Goal;
import com.eccard.conquer.data.model.db.Task;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Task task);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Task> tasks);

    @Query("SELECT * FROM tasks")
    List<Task> loadAll();

    @Query("SELECT * FROM tasks WHERE goal_id = :goalId")
    List<Task> loadAllByGoalId(Long goalId);

    @Query("SELECT * FROM tasks WHERE goal_id = :goalId")
    LiveData<List<Task>> loadAllByGoalIdWithLiveData(Long goalId);
}
