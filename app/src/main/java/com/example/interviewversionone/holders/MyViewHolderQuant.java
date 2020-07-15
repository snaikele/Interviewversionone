package com.example.interviewversionone.holders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interviewversionone.R;
import com.example.interviewversionone.Utils.TextViewEx;
import com.squareup.picasso.Picasso;

public class MyViewHolderQuant extends RecyclerView.ViewHolder {


    View mview;
    public MyViewHolderQuant(@Nullable View itemView) {
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

    public void setDetails(Context ctx, String title, String ans,String optionone,String optiontwo,String optionthree,String optionfour,String imageurl){

        TextView question = mview.findViewById(R.id.tv_topicName);
        TextView answer = mview.findViewById(R.id.tv_viewAns);
        TextView one = mview.findViewById(R.id.tv_optionOne);
        TextView two = mview.findViewById(R.id.tv_optionTwo);
        TextView three = mview.findViewById(R.id.tv_OptionThree);
        TextView four = mview.findViewById(R.id.tv_optionFour);


        /*ImageView i1 =mview.findViewById(R.id.img_mainActivity);
        Picasso.get().load(imageurl).into(i1);*/

        question.setText(title);
        answer.setText(ans);
        one.setText(optionone);
        two.setText(optiontwo);
        three.setText(optionthree);
        four.setText(optionfour);

    }
    private MyViewHolderQuant.ClickListner mClickListner;


    public interface ClickListner {
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }
    public void setOnClickListner(MyViewHolderQuant.ClickListner clickListner){
        mClickListner = clickListner;
    }
}
