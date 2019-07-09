package com.apptask.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.apptask.model.Task;


@Database(entities = {Task.class}, version = 1, exportSchema = false)
public  abstract class MyDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();

}
