package com.example.interviewversionone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.interviewversionone.holders.MyViewHolder;
import com.example.interviewversionone.model.Team;
import com.example.interviewversionone.ui.ListPreparationExam;
import com.example.interviewversionone.ui.ResumeFormatOneActivity;
import com.example.interviewversionone.ui.ViewActivity;
import com.example.interviewversionone.ui.ViewAllActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.view.GravityCompat.START;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "slider";

    LinearLayoutManager mLinearLayoutManager;
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    FirestoreRecyclerAdapter<Team, MyViewHolder> firebaseRecyclerAdapter;
    FirestoreRecyclerOptions<Team> options;
    NavigationView navigationView;




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

        Toolbar toolbar= findViewById(R.id.main_toolbaar);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_notification_white);

        final DrawerLayout drawer= findViewById(R.id.drawer);

        navigationView =(NavigationView) findViewById(R.id.nevigation);

        TextView viewall= findViewById(R.id.view_all);
        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ViewAllActivity.class);
                startActivity(intent);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(START);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.newsfeed:
                        Intent i = new Intent(MainActivity.this, ResumeFormatOneActivity.class);
                        startActivity(i);

                        break;
                    case R.id.facebook:

                        break;
                    case R.id.Instagram:

                        break;

                    case R.id.Download:

                        break;
                    case R.id.Logout:

                }

                return false;
            }
        });

        Button button= findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, ListPreparationExam.class);
                startActivity(intent);
            }
        });

        showSlider();
        showData();







    }

    private void showSlider() {
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
    }


    protected void showData(){

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
        this.mRecyclerView.setLayoutManager(mLinearLayoutManager);
        firebaseRecyclerAdapter.startListening();
        this.mRecyclerView.setAdapter(firebaseRecyclerAdapter);

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
