package com.avs.akashsingh.newapp;

import static com.avs.akashsingh.newapp.MainActivity.InterID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;


import com.avs.akashsingh.newapp.databinding.ActivityResultBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unity3d.ads.UnityAds;

public class ResultActivity extends AppCompatActivity {

    ActivityResultBinding binding;
    int POINTS = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                UnityAds.show(ResultActivity.this,InterID);
//
//            }
//        }, 10000);


        int correctAnswers = getIntent().getIntExtra("correct", 0);
        int totalQuestions = getIntent().getIntExtra("total", 0);

        long points = correctAnswers * POINTS;

        binding.answerset.setText(String.format("%d/%d", correctAnswers, totalQuestions));
        binding.coins.setText(String.valueOf(points));


        FirebaseFirestore database = FirebaseFirestore.getInstance();

        database.collection("USERS")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(points));

        binding.restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  UnityAds.show(ResultActivity.this,InterID);
                startActivity(new Intent(ResultActivity.this, CategoryActivity.class));
                finishAffinity();
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnityAds.show(ResultActivity.this,InterID);
                startActivity(new Intent(ResultActivity.this, CategoryActivity.class));
            }
        });




    }
}