package com.example.notes;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.notes.Adapter.NotesListAdapter;
import com.example.notes.Database.RoomDB;
import com.example.notes.Models.Notes;
import com.example.notes.Models.NotesClickListner;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import kotlin.jvm.internal.Intrinsics;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    RecyclerView recyclerView;
    SearchView search_home;
    NotesListAdapter notesListAdapter;
    RoomDB db;
    FloatingActionButton fabadd;
    List<Notes> note=new ArrayList<>();
    Notes selectedNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recycler_home);
        fabadd=findViewById(R.id.fabadd);
        search_home=findViewById(R.id.search_home);

        db=RoomDB.getInstance(this);
        note=db.mainDAO().getAll();
        UpdatRecycleView(note);

        fabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Notes_Activity.class);
              startActivityForResult(intent,101);
            }
        });

        search_home.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filters(newText);
                return true;
            }
        });


    }

    private void filters(String newText) {
        List<Notes> filteredList=new ArrayList<>();
        for(Notes a:note){
            if(a.getTittle().toLowerCase().contains(newText.toLowerCase())
            ||a.getNote().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(a);
            }
        }
        notesListAdapter.filteredList(filteredList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101){
            if( resultCode== Activity.RESULT_OK){
                Notes new_notes=(Notes) data.getSerializableExtra("Note");
                db.mainDAO().insert(new_notes);
                note.clear();
                note.addAll(db.mainDAO().getAll());
                notesListAdapter.notifyDataSetChanged();

            }
        }
        else if(requestCode==102){
            if( resultCode== Activity.RESULT_OK){
                Notes new_notes=(Notes) data.getSerializableExtra("Note");
                db.mainDAO().update(new_notes.getId(),new_notes.getTittle(),new_notes.getNote());
                note.clear();
                note.addAll(db.mainDAO().getAll());
                notesListAdapter.notifyDataSetChanged();
            }
        }
    }

    private void UpdatRecycleView(List<Notes> note) {
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
    notesListAdapter=new NotesListAdapter(MainActivity.this,note,notesClickListner);
    recyclerView.setAdapter(notesListAdapter);

    }
    private final NotesClickListner notesClickListner=new NotesClickListner() {
        @Override
        public void onClick(Notes note) {
            Intent intent=new Intent(MainActivity.this,Notes_Activity.class);
            intent.putExtra("old_note",note);
            startActivityForResult(intent,102);
        }

        @Override
        public void onLongClick(Notes notes, CardView cardView) {

            selectedNote=new Notes();
            selectedNote=notes;
            showPopup(cardView);

        }
    };

    private void showPopup(CardView cardView){
        PopupMenu pm=new PopupMenu(this,cardView);
        pm.setOnMenuItemClickListener(this);
        pm.inflate(R.menu.popup_menu);
        pm.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.pin) {
            if(selectedNote.isPin()) {
                    db.mainDAO().pin(selectedNote.getId(),false);
                    Toast.makeText(MainActivity.this, "Unpinned!", Toast.LENGTH_SHORT).show();
                }
                else{
                    db.mainDAO().pin(selectedNote.getId(),true);
                    Toast.makeText(MainActivity.this, "Pinned!", Toast.LENGTH_SHORT).show();
                }
                note.clear();
                note.addAll(db.mainDAO().getAll());
                notesListAdapter.notifyDataSetChanged();
                return true;
        }
        else if (id == R.id.delete) {

            db.mainDAO().delete(selectedNote);
            note.remove(selectedNote);
            notesListAdapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "Note Deleted successfully ", Toast.LENGTH_SHORT).show();
            return true;
        }
        else return false;

//        switch (item.getItemId()){
//            case R.id.pin:
//                if(selectedNote.isPin()){
//                    db.mainDAO().pin(selectedNote.getId(),false);
//                    Toast.makeText(MainActivity.this, "Unpinned!", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    db.mainDAO().pin(selectedNote.getId(),true);
//                    Toast.makeText(MainActivity.this, "Pinned!", Toast.LENGTH_SHORT).show();
//                }
//                note.clear();
//                note.addAll(db.mainDAO().getAll());
//                notesListAdapter.notifyDataSetChanged();
//                return true;
//            case R.id.delete:
//                db.mainDAO().delete(selectedNote);
//                note.remove(selectedNote);
//                notesListAdapter.notifyDataSetChanged();
//                Toast.makeText(MainActivity.this, "Note Deleted successfully ", Toast.LENGTH_SHORT).show();
//                return true;
//            default:
//                return false;
//
//
//        }

    }
}