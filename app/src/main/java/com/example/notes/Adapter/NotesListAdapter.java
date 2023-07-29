package com.example.notes.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.Models.Notes;
import com.example.notes.Models.NotesClickListner;
import com.example.notes.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NoteViewHolder> {
    Context context;
    List<Notes> list;
    NotesClickListner listner;

    public NotesListAdapter(Context context, List<Notes> list, NotesClickListner listner) {
        this.context = context;
        this.list = list;
        this.listner = listner;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.note_list,parent,false);
       NoteViewHolder viewHolder=new NoteViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.tv_tittle.setText(list.get(position).getTittle());
        holder.tv_tittle.setSelected(true);

        holder.tv_note.setText(list.get(position).getNote());
        holder.tv_date.setText(list.get(position).getDate());

        if (list.get(position).isPin()) {
            holder.img_pin.setImageResource(R.drawable.ic_pin);
        } else {
            holder.img_pin.setImageResource(0);
        }
        int colour_code=getRandom();
        holder.note_container.setBackgroundColor(holder.itemView.getResources().getColor(colour_code,null));

        holder.note_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.onClick(list.get(holder.getAdapterPosition()));
            }
        });

        holder.note_container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listner.onLongClick(list.get(holder.getAdapterPosition()),holder.note_container);

                return true;
            }
        });
    }

    private int getRandom() {
        List<Integer> colourCode = new ArrayList<>();
        colourCode.add(R.color.c1);
        colourCode.add(R.color.c2);
        colourCode.add(R.color.c3);
        colourCode.add(R.color.c4);
        colourCode.add(R.color.c5);

        Random r=new Random();
        int random_colour=r.nextInt(colourCode.size());
        return colourCode.get(random_colour);
    }

    public void filteredList(List<Notes> filterList){
        list=filterList;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{
        CardView note_container;
        TextView tv_tittle,tv_note,tv_date;
        ImageView img_pin;
        public NoteViewHolder(@NonNull View itemView){
            super(itemView);
            note_container=itemView.findViewById(R.id.note_container);
            tv_tittle=itemView.findViewById(R.id.tv_tittle);
            tv_note=itemView.findViewById(R.id.tv_note);
            tv_date=itemView.findViewById(R.id.tv_date);
            img_pin=itemView.findViewById(R.id.img_pin);

        }
    }

}
