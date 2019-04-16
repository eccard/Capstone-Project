package com.eccard.conquer.ui.tasks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eccard.conquer.data.model.db.Task;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {


    interface OnSelectedTask{
        void onSelectedTask(Task task);
    }


    private List<Task> data;
    private final LayoutInflater layoutInflater;
    private final OnSelectedTask onSelectedTaskListener;

    public void setData(List<Task> newData) {
        if (this.data != null){
            TaskDiffCallBack taskDiffCallBack = new TaskDiffCallBack(data,newData);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(taskDiffCallBack);

            data.clear();
            data.addAll(newData);
            diffResult.dispatchUpdatesTo(this);


        } else {
            this.data = newData;
        }

    }

    public TasksAdapter(Context context, OnSelectedTask onSelectedTaskListener) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = new ArrayList<>();
        this.onSelectedTaskListener = onSelectedTaskListener;

    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(android.R.layout.simple_list_item_2,parent,false);

        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class TaskViewHolder extends RecyclerView.ViewHolder{
        private TextView mTaskName;
        private TextView mTaskDescription;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mTaskName = itemView.findViewById(android.R.id.text1);
            this.mTaskDescription = itemView.findViewById(android.R.id.text2);


        }

        void bind(final Task task){
            if (task !=null){
                mTaskName.setText(task.name);
                mTaskDescription.setText(task.description);
                itemView.setOnClickListener(v -> {
                    // todo implemente show task details
                    onSelectedTaskListener.onSelectedTask(task);
                });
            }

        }
    }


    class TaskDiffCallBack extends DiffUtil.Callback{
        private final List<Task> oldList,newList;

        public TaskDiffCallBack(List<Task> oldList, List<Task> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).id.equals(newList.get(newItemPosition).id);
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).name.equals(newList.get(newItemPosition).name);
        }
    }
}
