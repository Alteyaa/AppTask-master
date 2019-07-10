package com.apptask.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.apptask.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task ORDER BY id DESC")
   LiveData<List<Task>> getAll();

    @Update
    void update (Task task);

    @Insert
    void insert(Task task);

    @Delete
    void delete(Task task);


}

