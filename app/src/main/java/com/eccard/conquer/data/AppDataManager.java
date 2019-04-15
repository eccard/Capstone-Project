/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.eccard.conquer.data;

import android.content.Context;

import com.eccard.conquer.data.local.db.DbHelper;
import com.eccard.conquer.data.local.db.dao.TaskDao;
import com.eccard.conquer.data.local.prefs.PreferencesHelper;
import com.eccard.conquer.data.model.api.BlogResponse;
import com.eccard.conquer.data.model.api.LoginRequest;
import com.eccard.conquer.data.model.api.LoginResponse;
import com.eccard.conquer.data.model.api.OpenSourceResponse;
import com.eccard.conquer.data.model.db.Goal;
import com.eccard.conquer.data.model.db.Task;
import com.eccard.conquer.data.remote.ApiHeader;
import com.eccard.conquer.data.remote.ApiHelper;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
public class AppDataManager implements DataManager {

    private final ApiHelper mApiHelper;

    private final Context mContext;

    private final DbHelper mDbHelper;

    private final Gson mGson;

    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public AppDataManager(Context context, DbHelper dbHelper, PreferencesHelper preferencesHelper, ApiHelper apiHelper, Gson gson) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
        mGson = gson;
    }

    @Override
    public Single<LoginResponse> doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest request) {
        return mApiHelper.doFacebookLoginApiCall(request);
    }

    @Override
    public Single<LoginResponse> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest request) {
        return mApiHelper.doGoogleLoginApiCall(request);
    }

    @Override
    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request) {
        return mApiHelper.doServerLoginApiCall(request);
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
    }

    @Override
    public Single<BlogResponse> getBlogApiCall() {
        return mApiHelper.getBlogApiCall();
    }

    @Override
    public String getCurrentUserEmail() {
        return mPreferencesHelper.getCurrentUserEmail();
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPreferencesHelper.setCurrentUserEmail(email);
    }

    @Override
    public Long getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(Long userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public String getCurrentUserName() {
        return mPreferencesHelper.getCurrentUserName();
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPreferencesHelper.setCurrentUserName(userName);
    }

    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPreferencesHelper.getCurrentUserProfilePicUrl();
    }

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPreferencesHelper.setCurrentUserProfilePicUrl(profilePicUrl);
    }

    @Override
    public Single<OpenSourceResponse> getOpenSourceApiCall() {
        return mApiHelper.getOpenSourceApiCall();
    }

    @Override
    public void updateApiHeader(Long userId, String accessToken) {
        mApiHelper.getApiHeader().getProtectedApiHeader().setUserId(userId);
        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }

    @Override
    public void updateUserInfo(
            String accessToken,
            Long userId,
            LoggedInMode loggedInMode,
            String userName,
            String email,
            String profilePicPath) {

        setAccessToken(accessToken);
        setCurrentUserId(userId);
        setCurrentUserLoggedInMode(loggedInMode);
        setCurrentUserName(userName);
        setCurrentUserEmail(email);
        setCurrentUserProfilePicUrl(profilePicPath);

        updateApiHeader(userId, accessToken);
    }

    @Override
    public Observable<Boolean> saveTask(Task task) {
        return mDbHelper.saveTask(task);
    }

    @Override
    public Observable<Boolean> deleteTask(Task task) {
        return mDbHelper.deleteTask(task);
    }

    @Override
    public Observable<Boolean> isTaskEmpty() {
        return mDbHelper.isTaskEmpty();
    }

    @Override
    public Observable<List<Task>> getTasksFromGoalId(Long goalId) {
        return mDbHelper.getTasksFromGoalId(goalId);
    }

    @Override
    public LiveData<List<TaskDao.TaskGoal>> loadTaskGoalsOfDayWithLiveData(int day) {
        return mDbHelper.loadTaskGoalsOfDayWithLiveData(day);
    }

    private List<TaskDao.TaskGoal> taskGoals;

    @Override
    public void cacheWidgetList(List<TaskDao.TaskGoal> taskGoals) {
        this.taskGoals = taskGoals;
    }

    @Override
    public List<TaskDao.TaskGoal> getWidgetList() {
        return this.taskGoals;
    }

    @Override
    public LiveData<List<Task>> loadAllTasksWithLiveData() {
        return mDbHelper.loadAllTasksWithLiveData();
    }

    @Override
    public Observable<Boolean> saveGoal(Goal goal) {
        return mDbHelper.saveGoal(goal);
    }

    @Override
    public Observable<Boolean> deleteGoal(Goal goal) {
        return mDbHelper.deleteGoal(goal);
    }

    @Override
    public Observable<Boolean> isGoalEmpty() {
        return mDbHelper.isGoalEmpty();
    }

    @Override
    public Observable<List<Goal>> getAllGoals() {
        return mDbHelper.getAllGoals();
    }

    @Override
    public LiveData<List<Goal>> getAllGoalsLiveData() {
        return mDbHelper.getAllGoalsLiveData();
    }

    @Override
    public Observable<List<Task>> loadAllByGoalId(Long goalId) {
        return mDbHelper.loadAllByGoalId(goalId);
    }

    @Override
    public LiveData<List<Task>> loadAllByGoalIdWithLiveData(Long goalId) {
        return mDbHelper.loadAllByGoalIdWithLiveData(goalId);
    }

    @Override
    public Observable<TaskDao.TaskGoal> loadTaskGoalFromTaskId(Long taskId) {
        return mDbHelper.loadTaskGoalFromTaskId(taskId);
    }

}
