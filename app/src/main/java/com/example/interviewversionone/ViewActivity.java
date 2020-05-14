package com.example.interviewversionone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ViewActivity extends AppCompatActivity {
    ImageView imageView;
    TextView title, desc;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        imageView=findViewById(R.id.img_viewActivity);
        title=findViewById(R.id.tv_viewActivity);
        desc=findViewById(R.id.tv_desViewActivity);

        String ButtonViewKey= getIntent().getStringExtra("ButtonViewKey");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Button");
        databaseReference.child(ButtonViewKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String btnTitle =dataSnapshot.child("title").getValue().toString();
                    String btnImage =dataSnapshot.child("image").getValue().toString();
                    String descBtn =dataSnapshot.child("desc").getValue().toString();
                    Picasso.get().load(btnImage).into(imageView);
                    desc.setText(descBtn);
                    title.setText(btnTitle);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
