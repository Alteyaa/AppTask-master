package com.apptask.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Comparator;

@Entity
public class Task implements Serializable {


   @PrimaryKey (autoGenerate = true)

    long id;
    public  String title;
    public String description;

    public Task() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }
        public static final Comparator<Task> BY_TITLE_ALPHABETICALLY = new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        };
    public static final Comparator<Task> BY_TITLE_DESC = new Comparator<Task>() {
        @Override
        public int compare(Task o1, Task o2) {
            return o1.getTitle().compareTo(o2.getTitle());
        }
    };
}
