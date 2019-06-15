package com.example.myapplication;


import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

//use this class to access NoteDao method in background thread and also for more abstraction to our Database
//not part of android architecture component but consider as best practice

public class NoteRepository {

    private NoteDao noteDao;
    private NoteDatabase noteDatabase;
    private LiveData<List<Note>> AllNotes;

    //application is a subClass of Context
    public NoteRepository(Application application){
        noteDatabase=NoteDatabase.getNoteDatabase(application);
        noteDao=noteDatabase.getNoteDao();
        AllNotes=noteDao.getAllNotes();
    }

    public void insertNote(Note note){
        new insertNoteAsyncTask(noteDao).execute(note);

    }
    public void deleteNote(Note note){
        new deleteNoteAsyncTask(noteDao).execute(note);

    }
    public void updateNote(Note note){
        new updateNoteAsyncTask(noteDao).execute(note);
    }
    public void deleteAllNotes(){
        new deleteAllNotesAsyncTask(noteDao).execute();
    }

    //Room automatically execute the database operation set return(select data) in a background Thread
    public LiveData<List<Note>> getAllNotes() {
        return AllNotes;
    }

    private static class insertNoteAsyncTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;

        private insertNoteAsyncTask(NoteDao noteDao){
            this.noteDao=noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            this.noteDao.insertNote(notes[0]);
            return null;
        }
    }
    private static class deleteNoteAsyncTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;

        private deleteNoteAsyncTask(NoteDao noteDao){
            this.noteDao=noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            this.noteDao.deleteNote(notes[0]);
            return null;
        }
    }
    private static class updateNoteAsyncTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;

        private updateNoteAsyncTask(NoteDao noteDao){
            this.noteDao=noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            this.noteDao.updateNote(notes[0]);
            return null;
        }
    }
    private static class deleteAllNotesAsyncTask extends AsyncTask<Void,Void,Void>{
        private NoteDao noteDao;

        private deleteAllNotesAsyncTask(NoteDao noteDao){
            this.noteDao=noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            this.noteDao.deleteAllNotes();
            return null;
        }
    }
}
