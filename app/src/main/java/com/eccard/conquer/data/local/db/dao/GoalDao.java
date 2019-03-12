package com.eccard.conquer.data.local.db.dao;

import com.eccard.conquer.data.model.db.Goal;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface GoalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Goal goal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Goal> goals);

    @Query("SELECT * FROM goals")
    LiveData<List<Goal>> loadAllWithLiveData();

    @Query("SELECT * FROM goals")
    List<Goal> loadAll();
}
