package com.example.myapplication;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

/*
- By putting the data in viewModel it doesn’t get lost and
  we don’t have to interrupt any thing when the configuration change happened

- The View Model only removed from the memory when
  the life cycle of the corresponding activity or fragment is over which means
  when the activity is finished or fragment is detached
*/


//AndroidViewModel is subClass of viewModel
public class NoteViewModel extends AndroidViewModel {
    private LiveData<List<Note>> AllNotes;
    private NoteRepository noteRepository;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository=new NoteRepository(application);
        AllNotes=noteRepository.getAllNotes();

    }

    public void insertNote(Note note){
       noteRepository.insertNote(note);

    }
    public void deleteNote(Note note){
       noteRepository.deleteNote(note);

    }
    public void updateNote(Note note){
        noteRepository.updateNote(note);
    }
    public void deleteAllNotes(){
        noteRepository.deleteAllNotes();
    }

    //Room automatically execute the database operation set return(select data) in a background Thread
    public LiveData<List<Note>> getAllNotes() {
        return AllNotes;
    }


}
