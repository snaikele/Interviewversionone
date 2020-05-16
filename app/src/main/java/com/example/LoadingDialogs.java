package com.example;

import android.app.Activity;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import com.example.interviewversionone.R;


class LoadingDialogs {
        Activity activity;
        AlertDialog dialog;

        LoadingDialogs(Activity myActivity){
        activity =  myActivity;

        }
        void startLoadingDialog(){
        AlertDialog.Builder builder= new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_dialog, null));
        builder.setCancelable(true);
        dialog =builder.create();
        dialog.show();

        }
        void dismissDialog(){
        dialog.dismiss();
        }
        }
