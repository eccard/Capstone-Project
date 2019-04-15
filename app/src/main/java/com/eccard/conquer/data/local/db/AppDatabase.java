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

import com.eccard.conquer.data.local.db.dao.GoalDao;
import com.eccard.conquer.data.local.db.dao.TaskDao;
import com.eccard.conquer.data.model.db.Goal;
import com.eccard.conquer.data.model.db.Task;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Created by amitshekhar on 07/07/17.
 */

@Database(entities = {Task.class, Goal.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();


    public abstract GoalDao goalDao();


}
