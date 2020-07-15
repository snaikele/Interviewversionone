package com.example.interviewversionone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.interviewversionone.holders.MyViewHolderQuant;
import com.example.interviewversionone.model.MCQ;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class TabLayoutThree_Logicall extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private static final String TAG = "Logical";


    LinearLayoutManager mLinearLayoutManager;
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    FirestoreRecyclerAdapter<MCQ, MyViewHolderQuant> firebaseRecyclerAdapter;

    FirestoreRecyclerOptions<MCQ> options;

    public TabLayoutThree_Logicall() {
        // Required empty public constructor
    }


    public static TabLayoutTwo_English newInstance(String param1, String param2) {
        TabLayoutTwo_English fragment = new TabLayoutTwo_English();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_tab_layout_one__quant, container, false);
        rootView.setTag(TAG);
        return inflater.inflate(R.layout.activity_tab_layout_three__logicall, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Intent intent = getActivity().getIntent();
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(false);
        mLinearLayoutManager.setStackFromEnd(false);
        mRecyclerView=view.findViewById(R.id.rv_tab_layout_three_logical);





        mFirebaseDatabase= FirebaseDatabase.getInstance();
        String message = intent.getStringExtra("TabLayoutInfo");



        //query that fire to fetch data from firebase
        Query query = FirebaseFirestore.getInstance()
                .collection("Logical")
                .whereEqualTo("ExamId", message);
        options = new FirestoreRecyclerOptions.Builder<MCQ>()
                .setQuery(query, new SnapshotParser<MCQ>() {
                    @NonNull
                    @Override
                    public MCQ parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        MCQ team= new MCQ();
                        team.setMcqId(snapshot.getId());
                        team.setQuestion(snapshot.getString("Question"));
                        team.setAnswer(snapshot.getString("Answer"));
                        team.setOptionOne(snapshot.getString("optionOne"));
                        team.setOptionTwo(snapshot.getString("optionTwo"));
                        team.setOptionThree(snapshot.getString("optionThree"));
                        team.setOptionFour(snapshot.getString("optionFour"));
                        return team;
                    }
                })
                /*.setQuery(query, Team.class)*/
                .build();

        firebaseRecyclerAdapter = new FirestoreRecyclerAdapter<MCQ, MyViewHolderQuant>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolderQuant holder, int position, @NonNull final MCQ model) {
                holder.setDetails(getActivity(),model.getQuestion(), model.getAnswer(),model.getOptionOne(),model.getOptionTwo(),model.getOptionThree(),model.getOptionFour(),model.getMcqId());
                holder.setOnClickListner(new MyViewHolderQuant.ClickListner() {
                    @Override
                    public void onItemClick(View view, int position) {
                       /* Intent intent = new Intent(TabLayoutOne_Quant.this, DetailActivity.class);
                        intent.putExtra("TeamDetailKey", model.getMembersId());
                        startActivity(intent);*/

                        // Toast.makeText(MainActivity.this,""+model.getTeamId(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        /*Toast.makeText(TabLayoutOne_Quant.this,"long click ",Toast.LENGTH_SHORT).show();*/
                    }
                });
            }

            @NonNull
            @Override
            public MyViewHolderQuant onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mcqview, parent, false);
                final MyViewHolderQuant viewHolder = new MyViewHolderQuant(itemView);


                return viewHolder;
            }
        };
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);


    }

    @Override
    public void onStart(){
        super.onStart();
        if (firebaseRecyclerAdapter!=null)
            firebaseRecyclerAdapter.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        if (firebaseRecyclerAdapter!=null)
            firebaseRecyclerAdapter.startListening();

    }

    @Override
    public void onResume(){
        super.onResume();
        if (firebaseRecyclerAdapter!=null)
            firebaseRecyclerAdapter.startListening();
    }
}
