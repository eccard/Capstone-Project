package com.eccard.conquer.ui.tasks.insert;

import android.os.Bundle;
import android.view.View;

import com.eccard.conquer.BR;
import com.eccard.conquer.R;
import com.eccard.conquer.ViewModelProviderFactory;
import com.eccard.conquer.databinding.FragmentNewTaskBinding;
import com.eccard.conquer.ui.base.BaseFragment;
import com.eccard.conquer.ui.main.MainActivity;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class NewTaskFragment extends BaseFragment<FragmentNewTaskBinding,NewTaskViewModel> implements NewTaskNavigator {


    public static final String TAG = NewTaskFragment.class.getSimpleName();
    private static final String ARG_GOAL_ID_PARAM = "goalId";


    private FragmentNewTaskBinding fragmentNewTaskBinding;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;
    private NewTaskViewModel newTaskViewModel;


    public static NewTaskFragment newInstance(Long goalId) {
        Bundle args = new Bundle();
        args.putLong(ARG_GOAL_ID_PARAM,goalId);
        NewTaskFragment fragment = new NewTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_new_task;
    }

    @Override
    public NewTaskViewModel getViewModel() {
        newTaskViewModel = ViewModelProviders.of(this,viewModelProviderFactory).get(NewTaskViewModel.class);
        return newTaskViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newTaskViewModel.setNavigator(this);
        if (getArguments() != null && getArguments().containsKey(ARG_GOAL_ID_PARAM)){
            Long goalId = getArguments().getLong(ARG_GOAL_ID_PARAM);
            newTaskViewModel.setGoalId(goalId);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentNewTaskBinding = getViewDataBinding();
    }

    @Override
    public void goTasks(Long goalId) {
        ((MainActivity) getActivity()).goTasks(goalId);
    }
}
