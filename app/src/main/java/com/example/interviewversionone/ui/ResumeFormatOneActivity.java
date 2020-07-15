package com.example.interviewversionone.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;


import com.example.interviewversionone.MainActivity;
import com.example.interviewversionone.R;

public class ResumeFormatOneActivity extends Activity {

    ImageView edit,download;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_format_one);
        edit=findViewById(R.id.toolbaar_edit);
        download=findViewById(R.id.toolbaar_download);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ResumeFormatOneActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

    }
}