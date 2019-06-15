package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    private EditText title;
    private EditText Description;
    private NumberPicker numberPicker;
    public static final String Note_id_Extra="com.example.myapplication.noteId";
    public static final String Note_title_Extra="com.example.myapplication.noteTitle";
    public static final String Note_Description_Extra="com.example.myapplication.noteDescription";
    public static final String Note_priority_Extra="com.example.myapplication.notePriority";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        title = findViewById(R.id.EditText_Note_title);
        Description = findViewById(R.id.editText_note_description);
        numberPicker = findViewById(R.id.numberPicker);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);   //close icon
        if(getIntent().getExtras()!=null) {
            Intent intent=getIntent();
            setTitle("Edit Note");
            title.setText(intent.getStringExtra(Note_title_Extra));
            Description.setText(intent.getStringExtra(Note_Description_Extra));
            numberPicker.setValue(intent.getIntExtra(Note_priority_Extra,1));

        }else{
            setTitle("Add Note");
        }

        Log.i("AddNoteActivity","onCreate");
    }

    private void saveNote(){
        String note_title=title.getText().toString();
        String note_description=Description.getText().toString();
        int note_priority=numberPicker.getValue();
        if(note_title.trim().isEmpty() || note_description.trim().isEmpty()){
            Toast.makeText(this, "please add title and description", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent=new Intent();
            intent.putExtra(Note_title_Extra,note_title);
            intent.putExtra(Note_Description_Extra,note_description);
            intent.putExtra(Note_priority_Extra,note_priority);
            int id=getIntent().getIntExtra(Note_id_Extra,-1);
            if(id !=-1){
                intent.putExtra(Note_id_Extra,id);
            }
            setResult(RESULT_OK,intent);  //RESULT_OK integer constant
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("AddNoteActivity","onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("AddNoteActivity","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("AddNoteActivity","onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("AddNoteActivity","onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("AddNoteActivity","onResume");
    }
}
