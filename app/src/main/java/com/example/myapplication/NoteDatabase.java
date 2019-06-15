package com.example.myapplication;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

//Singleton class to create a single object of database that have our Entities
@Database(entities =Note.class,version = 1,exportSchema = false)  //what is export schema argument
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    //it's abstract cause you don't have to provide method body cause Room will do that for you
    public abstract NoteDao getNoteDao();  //use this method to access your Dao

    //only one thread access this function at a time in a Multithread environment
    public static synchronized NoteDatabase getNoteDatabase(Context context){
        //create database object using Room
        if(instance==null){
            instance= Room.databaseBuilder(context,NoteDatabase.class,"NoteDatabase")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback) //when instance is first created will execute onCreate Method and populate db
                    .build();
        }
        return instance;
    }


    //Callback is abstract static inner class in RoomDatabase
    private static RoomDatabase.Callback roomCallback =new RoomDatabase.Callback(){  //Question??
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new callBackAsysncTask(instance).execute();
        }
    };

    private static  class callBackAsysncTask extends AsyncTask<Void,Void,Void>{

        private NoteDao noteDao ;
        private callBackAsysncTask(NoteDatabase noteDatabase){
           noteDao=noteDatabase.getNoteDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insertNote(new Note("title1","Description1",1));
            noteDao.insertNote(new Note("title2","Description2",2));
            noteDao.insertNote(new Note("title3","Description3",1));
            return null;
        }
    }
}
