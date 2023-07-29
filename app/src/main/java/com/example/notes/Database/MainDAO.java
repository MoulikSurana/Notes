package com.example.notes.Database;

import static androidx.room.OnConflictStrategy.REPLACE;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.notes.Models.Notes;

import java.util.List;

@Dao
public interface MainDAO {
    @Insert(onConflict = REPLACE)
    void insert(Notes notes);
    @Query("SELECT * FROM notes")
    List<Notes> getAll();
    @Query("UPDATE notes set tittle=:tittle,note=:note where id=:id")
    void update(int id,String tittle,String note);
    @Delete
    void delete(Notes notes);

    @Query("UPDATE notes set pin=:pin where id=:id")
    void pin(int id,boolean pin);




}
