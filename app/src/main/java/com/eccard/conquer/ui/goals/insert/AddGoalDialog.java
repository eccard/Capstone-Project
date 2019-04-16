package com.eccard.conquer.ui.goals.insert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eccard.conquer.ConquerApp;
import com.eccard.conquer.R;
import com.eccard.conquer.ViewModelProviderFactory;
import com.eccard.conquer.databinding.DialogAddGoalBinding;
import com.eccard.conquer.ui.base.BaseDialog;
import com.google.android.gms.analytics.Tracker;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.AndroidSupportInjection;

public class AddGoalDialog extends BaseDialog implements AddGoalCallback {


    private static final String TAG = AddGoalDialog.class.getSimpleName();

    @Inject
    ViewModelProviderFactory factory;
    private AddGoalViewModel mAddGoalViewModel;

    public static AddGoalDialog newInstance() {

        Bundle args = new Bundle();

        AddGoalDialog fragment = new AddGoalDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void dismissDialog() {
        dismissDialog(TAG);

        ConquerApp application = (ConquerApp) getActivity().getApplication();
        Tracker tracker = application.getDefaultTracker();


    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DialogAddGoalBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_goal, container, false);
        View view = binding.getRoot();

        AndroidSupportInjection.inject(this);
        mAddGoalViewModel = ViewModelProviders.of(this,factory).get(AddGoalViewModel.class);
        binding.setViewModel(mAddGoalViewModel);

        mAddGoalViewModel.setNavigator(this);

        return view;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }
}
