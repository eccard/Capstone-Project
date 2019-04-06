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

package com.eccard.conquer.data.local.db;

import com.eccard.conquer.data.model.db.Goal;
import com.eccard.conquer.data.model.db.Option;
import com.eccard.conquer.data.model.db.Question;
import com.eccard.conquer.data.model.db.Task;
import com.eccard.conquer.data.model.db.User;

import androidx.lifecycle.LiveData;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by amitshekhar on 07/07/17.
 */

public interface DbHelper {

    Observable<List<Question>> getAllQuestions();

    Observable<List<User>> getAllUsers();

    Observable<List<Option>> getOptionsForQuestionId(Long questionId);

    Observable<Boolean> insertUser(final User user);

    Observable<Boolean> isOptionEmpty();

    Observable<Boolean> isQuestionEmpty();

    Observable<Boolean> saveOption(Option option);

    Observable<Boolean> saveOptionList(List<Option> optionList);

    Observable<Boolean> saveQuestion(Question question);

    Observable<Boolean> saveQuestionList(List<Question> questionList);


    Observable<Boolean> saveTask(final Task task);
    Observable<Boolean> delete(final Task task);

    Observable<Boolean> isTaskEmpty();

    Observable<List<Task>> getTasksFromGoalId(Long goalId);
    LiveData<List<Task>> loadAllTasksWithLiveData();
    LiveData<List<Task>> loadTaskOfDayWithLiveData(int day);
    Observable<List<Task>> loadAllByGoalId(Long goalId);
    LiveData<List<Task>> loadAllByGoalIdWithLiveData(Long goalId);

    // todo add delete

    // add update ?

    Observable<Boolean> saveGoal(final Goal goal);

    Observable<Boolean> isGoalEmpty();

    Observable<List<Goal>> getAllGoals();

    LiveData<List<Goal>> getAllGoalsLiveData();



}
