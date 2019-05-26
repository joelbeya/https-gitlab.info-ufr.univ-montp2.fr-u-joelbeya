package com.example.easycourse;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;


public class Final_Adapter extends RecyclerView.Adapter<MyViewHolder> implements Filterable {

    Context c;
    ArrayList<Eleve> eleven, filterList;
    CustomFilter filter;

    public Final_Adapter(Context ctx, ArrayList<Eleve> eleven) {
        this.c = ctx;
        this.eleven = eleven;
        this.filterList = eleven;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model, null);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.posTxt.setText(eleven.get(i).getPos());
        myViewHolder.named.setText(eleven.get(i).getName());
        myViewHolder.imageView.setImageResource(eleven.get(i).getImg());
        myViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent(v.getContext(), CoursListActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eleven.size();
    }

    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter(filterList, this);
        }
        return filter;
    }
}

