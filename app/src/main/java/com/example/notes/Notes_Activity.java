package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.notes.Models.Notes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Notes_Activity extends AppCompatActivity {
    EditText et_tittle,et_note;
    ImageView img_save;
    Notes note;
    boolean is_old=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        img_save=findViewById(R.id.img_save);
        et_tittle=findViewById(R.id.et_tittle);
        et_note=findViewById(R.id.et_note);

        note=new Notes();
        try {
       note=(Notes) getIntent().getSerializableExtra("old_note");
       et_tittle.setText(note.getTittle());
       et_note.setText(note.getNote());
       is_old=true;
        }catch (Exception e){
            e.printStackTrace();
        }

        img_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tittle=et_tittle.getText().toString();
                String desc=et_note.getText().toString();

                SimpleDateFormat format=new SimpleDateFormat("EEE ,d MMM yyyy HH:mm a");
                Date date=new Date();
                if(!is_old){
                note=new Notes();}

                note.setTittle(tittle);
                note.setNote(desc);
                note.setDate(format.format(date));

                Intent intent=new Intent();
                intent.putExtra("Note",note);
                setResult(Activity.RESULT_OK,intent);
                finish();;

            }
        });

    }
}