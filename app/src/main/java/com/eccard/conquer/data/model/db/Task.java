package com.eccard.conquer.data.model.db;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

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
    public String time;

    @ColumnInfo(name = "color")
    public String color;

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
                '}';
    }
}
