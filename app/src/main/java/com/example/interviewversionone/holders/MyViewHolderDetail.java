package com.example.interviewversionone.holders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interviewversionone.R;
import com.squareup.picasso.Picasso;

public class MyViewHolderDetail  extends RecyclerView.ViewHolder {



    View mview;
    public MyViewHolderDetail(@Nullable View itemView) {
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

    public void setDetails(Context ctx/*, String name*/, String task, String detailImg){

        TextView t1 = mview.findViewById(R.id.tv_DetailName);
        TextView t2 = mview.findViewById(R.id.tv_detailText);
        ImageView i1 = mview.findViewById(R.id.img_detailView);
        Picasso.get().load(detailImg).into(i1);

       /* t1.setText(name);*/
        t2.setText(task);
    }
    private MyViewHolderTopics.ClickListner mClickListner;


    public interface ClickListner {
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }
    public void setOnClickListner(MyViewHolderTopics.ClickListner clickListner){
        mClickListner = clickListner;
    }
}
