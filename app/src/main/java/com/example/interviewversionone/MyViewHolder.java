package com.example.interviewversionone;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class MyViewHolder extends RecyclerView.ViewHolder {


    View mview;
    public MyViewHolder(@Nullable View itemView) {
        super(itemView);

        mview=itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListner.onItemClick(view, getAdapterPosition());
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListner.onItemLongClick(view, getAdapterPosition());
                return true;
            }
        });





    }

    public void setDetails(Context ctx, String title, String image, String desc){
        ImageView i1 = mview.findViewById(R.id.img_view);
        TextView t1 = mview.findViewById(R.id.tv_view);
       /* TextView t2 = mview.findViewById(R.id.tv_desViewActivity);*/

       /* t2.setText(desc);*/
        t1.setText(title);
        Picasso.get().load(image).into(i1);
    }
    private MyViewHolder.ClickListner mClickListner;


    public interface ClickListner {
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }
    public void setOnClickListner(MyViewHolder.ClickListner clickListner){
        mClickListner = clickListner;
    }
}
