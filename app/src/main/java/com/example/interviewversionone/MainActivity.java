package com.example.interviewversionone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.interviewversionone.holders.MainSliderAdapter;
import com.example.interviewversionone.holders.MyViewHolder;
import com.example.interviewversionone.model.Banner;
import com.example.interviewversionone.model.Team;
import com.example.interviewversionone.ui.ViewActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ss.com.bannerslider.Slider;
import ss.com.bannerslider.indicators.IndicatorShape;

public class MainActivity extends AppCompatActivity {
    /*ImageView img,img1,img2,img3,img4;*/
    ViewFlipper viewFlipper;
    String img1,img2,img3;
    private static final String TAG = "slider";
    TextView tips;
    ProgressBar progressBar;
    LinearLayoutManager mLinearLayoutManager;
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    private Slider slider;
    DatabaseReference mDatabaseReference;
    FirestoreRecyclerAdapter<Team, MyViewHolder> firebaseRecyclerAdapter;
    boolean b;
    FirestoreRecyclerOptions<Team> options;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLinearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true);
        mLinearLayoutManager.setReverseLayout(false);
        mLinearLayoutManager.setStackFromEnd(false);
        mRecyclerView=findViewById(R.id.rec_btnview);
        mFirebaseDatabase= FirebaseDatabase.getInstance();
        /*tips=findViewById(R.id.tv_tips);*/
        /*mDatabaseReference = mFirebaseDatabase.getReference("Button");*/

        Button button= findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,ListPreparationExam.class);
                startActivity(intent);
            }
        });


        Toolbar toolbar= findViewById(R.id.main_toolbaar);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference user=db.collection("slider").document("images");
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               //slider if-else
                if(task.isSuccessful())
                {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        ImageSlider imageSlider = findViewById(R.id.slider);
                        List<SlideModel> slideModels=new ArrayList<>();

                        String Image = document.getString("image");
                        if (Image==""){}
                        else {
                            slideModels.add(new SlideModel(Image));
                        }


                        String Image1 = document.getString("image1");
                        if(Image1==""){}
                        else {
                            slideModels.add(new SlideModel(Image1));
                        }


                        String Image2 = document.getString("image2");
                        if(Image2==""){}
                        else {
                            slideModels.add(new SlideModel(Image2));
                        }

                        String Image3 = document.getString("image3");
                        if(Image3==""){}
                        else {
                            slideModels.add(new SlideModel(Image3));
                        }

                        String Image4 = document.getString("image4");
                        if(Image4==""){}
                        else {
                            slideModels.add(new SlideModel(Image4));
                        }

                        imageSlider.setImageList(slideModels, true);

                        /*tips.setVisibility(View.VISIBLE);*/



                    } else {
                        Log.d(TAG, "No such document");
                    }


                    //for image1

                }
                else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });


        showData();


    }


    private void showData(){
        Query query = FirebaseFirestore.getInstance()
                .collection("Team");
        options = new FirestoreRecyclerOptions.Builder<Team>()
                .setQuery(query, new SnapshotParser<Team>() {
                    @NonNull
                    @Override
                    public Team parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        Team team= new Team();
                        team.setTeamId(snapshot.getId());
                        team.setTeamName(snapshot.getString("TeamName"));
                        team.setTeamImgUrl(snapshot.getString("TeamImgUrl"));
                        /*String imageurl = */
                        /*progressBar.setVisibility(View.GONE);*/
                        return team;
                    }
                })
                /*.setQuery(query, Team.class)*/
                .build();

        firebaseRecyclerAdapter = new FirestoreRecyclerAdapter<Team, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull final Team model) {
                holder.setDetails(getApplicationContext(),model.getTeamName(), model.getTeamImgUrl());
                holder.setOnClickListner(new MyViewHolder.ClickListner() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                        intent.putExtra("TeamViewKey", model.getTeamId());
                        startActivity(intent);

                       // Toast.makeText(MainActivity.this,""+model.getTeamId(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                            Toast.makeText(MainActivity.this,"long click ",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.btnview, parent, false);
                final MyViewHolder viewHolder = new MyViewHolder(itemView);


                return viewHolder;
            }
        };
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);


    }



    @Override
    protected void onStart(){
        super.onStart();
        if (firebaseRecyclerAdapter!=null)
            firebaseRecyclerAdapter.startListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        if (firebaseRecyclerAdapter!=null)
            firebaseRecyclerAdapter.startListening();




    }
    @Override
    protected void onResume(){
        super.onResume();
        if (firebaseRecyclerAdapter!=null)
            firebaseRecyclerAdapter.startListening();
    }

}
