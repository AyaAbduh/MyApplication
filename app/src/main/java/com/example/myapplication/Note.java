package com.example.myapplication;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


//this class have a corresponding table in DB that Room will create

@Entity(tableName = "Note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "Title")
    private String title;
    private String Description;
    private int priority;


    public Note(String title, String Description, int priority) {
        this.title = title;
        this.Description = Description;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return Description;
    }

    public int getPriority() {
        return priority;
    }
}
