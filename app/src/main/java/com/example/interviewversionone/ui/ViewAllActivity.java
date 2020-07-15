package com.example.interviewversionone.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.interviewversionone.R;
import com.example.interviewversionone.holders.MyViewHolder;
import com.example.interviewversionone.model.Team;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ViewAllActivity extends AppCompatActivity  {
    private static final String TAG = "slider";

    LinearLayoutManager mLinearLayoutManagerr;
    RecyclerView sRecyclerView;
    FirebaseDatabase mFirebaseDatabasee;
    FirestoreRecyclerAdapter<Team, MyViewHolder> firebaseRecyclerAdapterr;
    FirestoreRecyclerOptions<Team> optionss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        mLinearLayoutManagerr = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mLinearLayoutManagerr.setReverseLayout(false);
        mLinearLayoutManagerr.setStackFromEnd(false);
        sRecyclerView=findViewById(R.id.rec_view_all_btnview);
        mFirebaseDatabasee= FirebaseDatabase.getInstance();

        Query query = FirebaseFirestore.getInstance()
                .collection("Team");
        optionss = new FirestoreRecyclerOptions.Builder<Team>()
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



        firebaseRecyclerAdapterr = new FirestoreRecyclerAdapter<Team, MyViewHolder>(optionss) {
            @Override
            protected void onBindViewHolder(@NonNull final MyViewHolder holder, int position, @NonNull final Team model) {
                holder.setDetails(getApplicationContext(),model.getTeamName(), model.getTeamImgUrl());
                holder.setOnClickListner(new MyViewHolder.ClickListner() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(ViewAllActivity.this, ViewActivity.class);
                        intent.putExtra("TeamViewKey", model.getTeamId());

                        startActivity(intent);

                        // Toast.makeText(MainActivity.this,""+model.getTeamId(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(ViewAllActivity.this,"long click ",Toast.LENGTH_SHORT).show();
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
        this.sRecyclerView.setLayoutManager(mLinearLayoutManagerr);
        firebaseRecyclerAdapterr.startListening();
        this.sRecyclerView.setAdapter(firebaseRecyclerAdapterr);

    }



    @Override
    protected void onStart(){
        super.onStart();
        if (firebaseRecyclerAdapterr!=null)
            firebaseRecyclerAdapterr.startListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        if (firebaseRecyclerAdapterr!=null)
            firebaseRecyclerAdapterr.startListening();

    }
    @Override
    protected void onResume(){
        super.onResume();
        if (firebaseRecyclerAdapterr!=null)
            firebaseRecyclerAdapterr.startListening();
    }
}