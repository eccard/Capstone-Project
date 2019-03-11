package com.eccard.conquer.ui.goals;

import android.os.Bundle;
import android.view.View;

import com.eccard.conquer.BR;
import com.eccard.conquer.R;
import com.eccard.conquer.ViewModelProviderFactory;
import com.eccard.conquer.data.model.db.Goal;
import com.eccard.conquer.databinding.FragmentGoalsBinding;
import com.eccard.conquer.ui.base.BaseFragment;
import com.eccard.conquer.ui.main.MainActivity;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

public class GoalsFragment extends BaseFragment<FragmentGoalsBinding,GoalsViewModel> implements GoalsNavigator, GoalsAdapter.OnSelectedGoal {

    public static final String TAG = GoalsFragment.class.getSimpleName();

    private FragmentGoalsBinding mFragmentGoalsBinding;

    @Inject
    ViewModelProviderFactory factory;
    private GoalsViewModel mGoalsViewModel;


    public static GoalsFragment newInstance() {
        Bundle args = new Bundle();
        GoalsFragment fragment = new GoalsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_goals;
    }

    @Override
    public GoalsViewModel getViewModel() {
        mGoalsViewModel = ViewModelProviders.of(this,factory).get(GoalsViewModel.class);
        return mGoalsViewModel;
    }

    @Override
    public void goTasks() {

    }

    @Override
    public void goAddNewGoal() {
        ((MainActivity)getActivity()).goAddNewGoal();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoalsViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentGoalsBinding = getViewDataBinding();

        setUp();

    }


    private GoalsAdapter goalsAdapter;
    private void setUp() {
        mFragmentGoalsBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAddNewGoal();
            }
        });

        goalsAdapter = new GoalsAdapter(getContext(),this);
        mGoalsViewModel.getGoalsData().observe(this, new Observer<List<Goal>>() {
            @Override
            public void onChanged(List<Goal> goals) {
                goalsAdapter.setData(goals);
            }
        });


        getViewDataBinding().recyleView.setLayoutManager(new LinearLayoutManager(getContext()));
        getViewDataBinding().recyleView.setHasFixedSize(true);
        getViewDataBinding().recyleView.setItemAnimator(new DefaultItemAnimator());
        getViewDataBinding().recyleView.setAdapter(goalsAdapter);

    }

    @Override
    public void onSelectedGoal(Goal goal) {
        ((MainActivity)getActivity()).goTasks(goal.id);
    }
}
