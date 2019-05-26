package com.example.easycourse;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;


public  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView imageView;
    TextView named, posTxt;


    ItemClickListener itemClickListener;

    public MyViewHolder(View itemView) {
        super(itemView);

        this.named = itemView.findViewById(R.id.text1);
        this.imageView = itemView.findViewById(R.id.imagePlayer);
        this.posTxt = itemView.findViewById(R.id.text2);


        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v, getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic) {
        this.itemClickListener = ic;
    }
}
