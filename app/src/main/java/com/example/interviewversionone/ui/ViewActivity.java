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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.DetailActivity;
import com.example.interviewversionone.holders.MyViewHolderTopics;
import com.example.interviewversionone.R;
import com.example.interviewversionone.model.TeamMembers;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ViewActivity extends AppCompatActivity {
    private static final String TAG = "TeamMemberInfo";
    ImageView imageView;
    ProgressBar progressBar;
    TextView title, desc;
    DatabaseReference databaseReference;
    LinearLayoutManager mLinearLayoutManager;
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    FirestoreRecyclerAdapter<TeamMembers, MyViewHolderTopics> firebaseRecyclerAdapter;

    FirestoreRecyclerOptions<TeamMembers> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Intent intent = getIntent();
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(false);
        mLinearLayoutManager.setStackFromEnd(false);
        mRecyclerView=findViewById(R.id.rec_topicslist);

        progressBar = findViewById(R.id.spinner_viewActivity);
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        mFirebaseDatabase= FirebaseDatabase.getInstance();
        String message = intent.getStringExtra("TeamViewKey");
       /* showData();*/



        Query query = FirebaseFirestore.getInstance()
                .collection("TeamMembers")
                .whereEqualTo("TeamId", message);
        options = new FirestoreRecyclerOptions.Builder<TeamMembers>()
                .setQuery(query, new SnapshotParser<TeamMembers>() {
                    @NonNull
                    @Override
                    public TeamMembers parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        TeamMembers team= new TeamMembers();
                        team.setTeamId(snapshot.getId());
                        team.setName(snapshot.getString("Name"));
                        team.setMobile(snapshot.getString("Mobile"));
                        team.setMembersId(snapshot.getId());
                        progressBar.setVisibility(View.GONE);
                        return team;
                    }
                })
                /*.setQuery(query, Team.class)*/
                .build();

        firebaseRecyclerAdapter = new FirestoreRecyclerAdapter<TeamMembers, MyViewHolderTopics>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolderTopics holder, int position, @NonNull final TeamMembers model) {
                holder.setDetails(getApplicationContext(), model.getMobile(),model.getName());
                holder.setOnClickListner(new MyViewHolderTopics.ClickListner() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(ViewActivity.this, DetailActivity.class);
                        intent.putExtra("TeamDetailKey", model.getMembersId());
                        startActivity(intent);

                        // Toast.makeText(MainActivity.this,""+model.getTeamId(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(ViewActivity.this,"long click ",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @NonNull
            @Override
            public MyViewHolderTopics onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.topicslist, parent, false);
                final MyViewHolderTopics viewHolder = new MyViewHolderTopics(itemView);


                return viewHolder;
            }
        };
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);





        //Toast.makeText(ViewActivity.this,""+message,Toast.LENGTH_SHORT).show();

       /* FirebaseFirestore db  = FirebaseFirestore.getInstance();
        db.collection("TeamMembers")
                .whereEqualTo("TeamId", message)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Toast.makeText(getApplicationContext(),""+document.getData(),Toast.LENGTH_SHORT).show();
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });*/
    }

   /* private void showData(){

    }*/



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