package com.example.myapplication;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NoteDao {

    @Insert
    void insertNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Update
     void updateNote(Note note);

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
     LiveData<List<Note>> getAllNotes();

    @Query("delete from note_table")
    void deleteAllNotes();
}
