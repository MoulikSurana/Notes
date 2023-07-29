package com.example.notes.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notes.Models.Notes;

@Database(entities = Notes.class,version = 1,exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    private static RoomDB db;
    private static String DB_NAME="Notes_App";

     public synchronized static RoomDB getInstance(Context context){
         if(db==null){
             db= Room.databaseBuilder(context.getApplicationContext(),RoomDB.class,DB_NAME)
                     .allowMainThreadQueries()
                     .fallbackToDestructiveMigration()
                     .build();
         }
             return db;

     }

    public abstract MainDAO mainDAO();

}
