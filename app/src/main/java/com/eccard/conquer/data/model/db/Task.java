package com.eccard.conquer.data.model.db;

import com.eccard.conquer.utils.CommonUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import timber.log.Timber;

enum Days{
    Sunday(1),
    Monday(2),
    Tuesday(3),
    Wednesday(4),
    Thursday(5),
    Friday(6),
    Saturday(7);

    private final int value;

    Days(int i) {
        value = i;
    }

    public int getValue() {
        return value;
    }
}
@Entity(
        tableName = "tasks",
        foreignKeys = @ForeignKey(
                entity = Goal.class,
                parentColumns = "id",
                childColumns = "goal_id"
        )
)
public class Task {

    @Expose
    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    public String createdAt;

    @Expose
    @PrimaryKey
    public Long id;


    @Expose
    @SerializedName("goal_id")
    @ColumnInfo(name = "goal_id")
    public Long goalId;

    @Expose
    @SerializedName("updated_at")
    @ColumnInfo(name = "updated_at")
    public String updatedAt;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "time")
    public Long time;

    @ColumnInfo(name = "color")
    public String color;

    @ColumnInfo(name = "day_of_weekend")
    public Integer dayOfWeekend;


    @Override
    public String toString() {
        return "Task{" +
                "createdAt='" + createdAt + '\'' +
                ", id=" + id +
                ", goalId=" + goalId +
                ", updatedAt='" + updatedAt + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", time='" + time + '\'' +
                ", color='" + color + '\'' +
                ", dayOfWeekend=" + dayOfWeekend +
                '}';
    }

    public Task() {}

    public Task(Long goalId, String name, String description, int day,String time) {
        Days days = Days.values()[day-1];
        Timber.d("dayName="+days.name());

        Long timeLong = CommonUtils.getLongValueFromTime(time);

        Timber.d("Time="+time);
        Timber.d("TimeLong="+timeLong);

        this.goalId = goalId;
        this.name = name;
        this.description = description;
        this.time = timeLong;
        this.dayOfWeekend = day;
    }

}
