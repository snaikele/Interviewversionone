package com.example.interviewversionone.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


/*import com.example.LoadingDialogs;*/
import com.example.interviewversionone.Utils.TextViewEx;
import com.example.interviewversionone.holders.MyViewHolderDetail;
import com.example.interviewversionone.R;
import com.example.interviewversionone.model.TeamMembers;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "TeamMemberDetail";
    LinearLayoutManager mLinearLayoutManager;
    RecyclerView mRecyclerView;
    TextViewEx textView;
    TextViewEx  name;
    ImageView imageView;
    ProgressBar progressBar;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    TeamMembers teamMembers;
    Button next;
    FirestoreRecyclerAdapter<TeamMembers, MyViewHolderDetail> firebaseRecyclerAdapter;

    FirestoreRecyclerOptions<TeamMembers> options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        textView=findViewById(R.id.tv_detailText);
        //name=findViewById(R.id.tv_DetailName);
        imageView=findViewById(R.id.img_detailView);
        next = findViewById(R.id.next);
        Intent intent = getIntent();


        final LoadingDialogs dialog =new LoadingDialogs(DetailActivity.this);
        dialog.startLoadingDialog();
        /*progressBar = findViewById(R.id.spinner_detailActivity);
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
*/


        Toolbar toolbar= findViewById(R.id.toolbar_Detail);



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //toolbar back button click listner

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();   }
        });


        /*toolbar.setTitle(title);*/
       /* toolbar.setTitle("hello");*/


        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.CollapsingToolbar);
        //collapsingToolbarLayout.setTitle(" ");

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.security);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable Palette palette) {
                if(palette!=null)
                {
                    collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(R.attr.colorPrimary));
                }
            }
        });
        /*toolbar.setTitle(teamMembers.getName());*/




        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(false);
        mLinearLayoutManager.setStackFromEnd(false);
        mFirebaseDatabase= FirebaseDatabase.getInstance();

        final String message = intent.getStringExtra("TeamDetailKey");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        final FirebaseFirestore db  = FirebaseFirestore.getInstance();
        db.collection("MembersTasks")
                .whereEqualTo("MembersId", message)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Toast.makeText(getApplicationContext(),""+document.getData(),Toast.LENGTH_SHORT).show();
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                textView.setText(document.getString("Task").replace("__b","\n\n"),true);


                                String image = document.getString("TaskImage");
                                Picasso.get().load(image).into(imageView);
                                final DocumentReference docRef = db.collection("TeamMembers").document(document.getString("MembersId"));
                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                                //name.setText(document.getString("Name"),true);

                                                String Name = document.getString("Name");
                                                collapsingToolbarLayout.setTitle(Name);
                                                //name.setText(Name);

                                                dialog.dismissDialog();
                                               /* progressBar.setVisibility(View.GONE);*/

                                            } else {
                                                Log.d(TAG, "No such document");
                                            }
                                        } else {
                                            Log.d(TAG, "get failed with ", task.getException());
                                        }
                                    }
                                });

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

}
