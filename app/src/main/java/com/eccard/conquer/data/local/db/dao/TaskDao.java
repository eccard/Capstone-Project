package com.eccard.conquer.data.local.db.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.eccard.conquer.data.model.db.Task;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Task task);

    @Delete
    int delete(Task task);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Task> tasks);

    @Query("SELECT * FROM tasks")
    List<Task> loadAll();

    @Query("SELECT * FROM tasks WHERE goal_id = :goalId")
    List<Task> loadAllByGoalId(Long goalId);

    @Query("SELECT * FROM tasks ")
    LiveData<List<Task>> loadAllTasksWithLiveData();

    @Query("SELECT * FROM tasks WHERE goal_id = :goalId")
    LiveData<List<Task>> loadAllByGoalIdWithLiveData(Long goalId);

    String query ="SELECT goals.name AS goalName, tasks.name AS taskName, tasks.description AS taskDescription," +
            " tasks.time AS taskTime,  tasks.color AS taskColor, tasks.day_of_weekend AS taskDayOfWeekend" +
            " FROM tasks INNER JOIN goals ON tasks.goal_id = goals.id" +
            " WHERE tasks.day_of_weekend =:day ORDER BY time";

    @Query(query)
    LiveData<List<TaskGoal>> loadTaskGoalsOfDayWithLiveData(int day);
    @Query(query)
    List<TaskGoal> loadTaskGoalsOfDay(int day);

    @Query("SELECT goals.name AS goalName, tasks.name AS taskName, tasks.description AS taskDescription," +
            " tasks.time AS taskTime,  tasks.color AS taskColor, tasks.day_of_weekend AS taskDayOfWeekend" +
            " FROM tasks INNER JOIN goals ON tasks.goal_id = goals.id" +
            " WHERE tasks.id =:taskId")
    TaskGoal loadTaskGoalFromTaskId(Long taskId);

    static class TaskGoal implements Parcelable {
        public String goalName;
        public String taskName;
        public String taskDescription;
        public Long taskTime;
        public String taskColor;
        public String taskDayOfWeekend;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.goalName);
            dest.writeString(this.taskName);
            dest.writeString(this.taskDescription);
            dest.writeValue(this.taskTime);
            dest.writeString(this.taskColor);
            dest.writeString(this.taskDayOfWeekend);
        }

        public TaskGoal() {
        }

        protected TaskGoal(Parcel in) {
            this.goalName = in.readString();
            this.taskName = in.readString();
            this.taskDescription = in.readString();
            this.taskTime = (Long) in.readValue(Long.class.getClassLoader());
            this.taskColor = in.readString();
            this.taskDayOfWeekend = in.readString();
        }

        public static final Parcelable.Creator<TaskGoal> CREATOR = new Parcelable.Creator<TaskGoal>() {
            @Override
            public TaskGoal createFromParcel(Parcel source) {
                return new TaskGoal(source);
            }

            @Override
            public TaskGoal[] newArray(int size) {
                return new TaskGoal[size];
            }
        };
    }
}
