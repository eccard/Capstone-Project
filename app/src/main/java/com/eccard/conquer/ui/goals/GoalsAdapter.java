package com.eccard.conquer.ui.goals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eccard.conquer.data.model.db.Goal;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.GoalViewHolder> {

    private List<Goal> data;
    private LayoutInflater layoutInflater;
    private OnSelectedGoal onSelectedGoalListener;
    interface OnSelectedGoal{
        void onSelectedGoal(Goal goal);
    }

    public void setData(List<Goal> newData) {
        if (this.data != null){
            GoalDiffCalback goalDiffCalback = new GoalDiffCalback(data,newData);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(goalDiffCalback);

            data.clear();
            data.addAll(newData);
            diffResult.dispatchUpdatesTo(this);
        } else {
            this.data = newData;
        }

    }

    public GoalsAdapter(Context context, OnSelectedGoal onSelectedGoalListener) {
        this.data = new ArrayList<>();
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.onSelectedGoalListener = onSelectedGoalListener;

    }

    @NonNull
    @Override
    public GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(android.R.layout.simple_list_item_1,parent,false);
        return new GoalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class GoalViewHolder extends RecyclerView.ViewHolder{
        private TextView mGoalname;

        public GoalViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mGoalname = itemView.findViewById(android.R.id.text1);
        }

        void bind(final Goal goal){
            if (goal !=null){
                mGoalname.setText(goal.name);
                itemView.setOnClickListener(v -> onSelectedGoalListener.onSelectedGoal(goal));
            }

        }
    }

    class GoalDiffCalback extends DiffUtil.Callback{

        private final List<Goal> oldGoals,newGoals;

        public GoalDiffCalback(List<Goal> oldGoals, List<Goal> newGoals) {
            this.oldGoals = oldGoals;
            this.newGoals = newGoals;
        }

        @Override
        public int getOldListSize() {
            return oldGoals.size();
        }

        @Override
        public int getNewListSize() {
            return newGoals.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldGoals.get(oldItemPosition).id.equals(newGoals.get(newItemPosition).id);
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldGoals.get(oldItemPosition).name.equals(newGoals.get(newItemPosition).name);
        }
    }
}
