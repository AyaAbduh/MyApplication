package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

//Main Activity doesn't store the data it self it only observe on it
public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    private LiveData<List<Note>> notes;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButtonAddNote;
    public static final int addNoteRequestCode=1;
    public static final int EditNoteRequestCode=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("MainActivity","onCreate");
        recyclerView=findViewById(R.id.noteList);
        floatingActionButtonAddNote=findViewById(R.id.fab);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NoteAdapter noteAdapter=new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);

        floatingActionButtonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AddNoteActivity.class);
                startActivityForResult(intent,addNoteRequestCode);
            }
        });
        noteViewModel=ViewModelProviders.of(this).get(NoteViewModel.class);
        notes= noteViewModel.getAllNotes();

        notes.observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
               // Toast.makeText(MainActivity.this, "on Changed", Toast.LENGTH_SHORT).show();
                noteAdapter.setNoteList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Note note = noteAdapter.getNoteAt(viewHolder.getAdapterPosition());
                noteViewModel.deleteNote(note);
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);  //need to attach so RecyclerView know about it
        noteAdapter.setOnItemClickListener(new NoteAdapter.onItemClickListener() {   //anonymous innerClass
            @Override
            public void onItemClick(Note note) {
                Intent intent=new Intent(getApplicationContext(),AddNoteActivity.class);
                intent.putExtra(AddNoteActivity.Note_id_Extra,note.getId());
                intent.putExtra(AddNoteActivity.Note_title_Extra,note.getTitle());
                intent.putExtra(AddNoteActivity.Note_Description_Extra,note.getDescription());
                intent.putExtra(AddNoteActivity.Note_priority_Extra,note.getPriority());
                startActivityForResult(intent,EditNoteRequestCode);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MainActivity","onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MainActivity","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MainActivity","onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MainActivity","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MainActivity","onPause");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==addNoteRequestCode && resultCode==RESULT_OK){
            Bundle bundle=data.getExtras();
           String title=bundle.getString(AddNoteActivity.Note_title_Extra);
           String Desctiption= bundle.getString(AddNoteActivity.Note_Description_Extra);
           int priority= bundle.getInt(AddNoteActivity.Note_priority_Extra,1);
           Note note=new Note(title,Desctiption,priority);
           noteViewModel.insertNote(note);
            Toast.makeText(this, "Note saved successfully", Toast.LENGTH_SHORT).show();
        }else if(requestCode==EditNoteRequestCode&& resultCode==RESULT_OK){
           int id= data.getIntExtra(AddNoteActivity.Note_id_Extra,-1);
           if(id==-1){
               Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
           }
            Bundle bundle=data.getExtras();
            String title=bundle.getString(AddNoteActivity.Note_title_Extra);
            String Desctiption= bundle.getString(AddNoteActivity.Note_Description_Extra);
            int priority= bundle.getInt(AddNoteActivity.Note_priority_Extra,1);
            Note note=new Note(title,Desctiption,priority);
            note.setId(id);
            noteViewModel.updateNote(note);
            Toast.makeText(this, "Note updated successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteAllNotes:
                noteViewModel.deleteAllNotes();
                Toast.makeText(this, "All Notes Deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
