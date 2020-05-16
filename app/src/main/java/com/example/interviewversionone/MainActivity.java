package com.example.interviewversionone;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.interviewversionone.holders.MyViewHolder;
import com.example.interviewversionone.model.Team;
import com.example.interviewversionone.ui.ViewActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    LinearLayoutManager mLinearLayoutManager;
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    FirestoreRecyclerAdapter<Team, MyViewHolder> firebaseRecyclerAdapter;

    FirestoreRecyclerOptions<Team> options;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(false);
        mLinearLayoutManager.setStackFromEnd(false);
        mRecyclerView=findViewById(R.id.rec_btnview);
        mFirebaseDatabase= FirebaseDatabase.getInstance();
        /*mDatabaseReference = mFirebaseDatabase.getReference("Button");*/
        progressBar = findViewById(R.id.spinner_mainActivity);
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
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
                        progressBar.setVisibility(View.GONE);
                        return team;
                    }
                })
                /*.setQuery(query, Team.class)*/
                .build();

        firebaseRecyclerAdapter = new FirestoreRecyclerAdapter<Team, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull final Team model) {
                holder.setDetails(getApplicationContext(), model.getTeamName());
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
