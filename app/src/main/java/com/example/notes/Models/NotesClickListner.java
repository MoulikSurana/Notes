package com.example.notes.Models;

import androidx.cardview.widget.CardView;

public interface NotesClickListner {
    void onClick(Notes note);
    void onLongClick(Notes notes, CardView cardView);

}
