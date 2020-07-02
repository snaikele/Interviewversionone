package com.example.interviewversionone;

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

import com.example.interviewversionone.holders.MyHolderListOfExam;
import com.example.interviewversionone.holders.MyViewHolder;
import com.example.interviewversionone.model.ExamList;
import com.example.interviewversionone.ui.ViewActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ListPreparationExam extends AppCompatActivity {
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    FirestoreRecyclerOptions<ExamList> options;
    LinearLayoutManager mLinearLayoutManager;
    FirestoreRecyclerAdapter<ExamList, MyHolderListOfExam> firebaseRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_preparation_exam);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(false);
        mLinearLayoutManager.setStackFromEnd(false);
        mRecyclerView=findViewById(R.id.rv_listOfExams);
        mFirebaseDatabase= FirebaseDatabase.getInstance();

        showData();
    }
    private void showData(){
        Query query = FirebaseFirestore.getInstance()
                .collection("ExamList");
        options = new FirestoreRecyclerOptions.Builder<ExamList>()
                .setQuery(query, new SnapshotParser<ExamList>() {
                    @NonNull
                    @Override
                    public ExamList parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        ExamList team= new ExamList();
                        team.setExamID(snapshot.getId());
                        team.setExamName(snapshot.getString("examName"));
                        team.setExamImgUrl(snapshot.getString("ExamImgUrl"));
                        /*String imageurl = */
                        /*progressBar.setVisibility(View.GONE);*/
                        return team;
                    }
                })
                /*.setQuery(query, Team.class)*/
                .build();

        firebaseRecyclerAdapter = new FirestoreRecyclerAdapter<ExamList, MyHolderListOfExam>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyHolderListOfExam holder, int position, @NonNull final ExamList model) {
                holder.setDetails(getApplicationContext(),model.getExamName(), model.getExamImgUrl());
                holder.setOnClickListner(new MyHolderListOfExam.ClickListner() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(ListPreparationExam.this, MCQList.class);
                        intent.putExtra("TabLayoutInfo", model.getExamID());
                        startActivity(intent);

                        // Toast.makeText(MainActivity.this,""+model.getTeamId(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(ListPreparationExam.this,"long click ",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @NonNull
            @Override
            public MyHolderListOfExam onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_list_view, parent, false);
                final MyHolderListOfExam viewHolder = new MyHolderListOfExam(itemView);


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