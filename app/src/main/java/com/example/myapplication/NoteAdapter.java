package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    private List<Note> noteList=new ArrayList<>();
    private onItemClickListener listener;
    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item,parent,false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote=noteList.get(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewPriority.setText(Integer.toString(currentNote.getPriority()));
        holder.textViewDescription.setText(currentNote.getDescription());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
        notifyDataSetChanged();
    }

    public Note getNoteAt(int position){
        return noteList.get(position);
    }

    public class NoteHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewPriority;
        private TextView textViewDescription;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textViewDescription=itemView.findViewById(R.id.NoteDescription);
            textViewPriority=itemView.findViewById(R.id.notePriority);
            textViewTitle=itemView.findViewById(R.id.noteTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(listener !=null && position !=RecyclerView.NO_POSITION){
                        listener.onItemClick(noteList.get(position));
                    }
                }
            });
        }

    }

    public interface onItemClickListener{
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener=listener;
    }
}
