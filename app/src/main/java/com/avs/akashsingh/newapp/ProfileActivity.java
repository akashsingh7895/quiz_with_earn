package com.avs.akashsingh.newapp;

import static com.avs.akashsingh.newapp.MainActivity.BannerID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.avs.akashsingh.newapp.databinding.ActivityProfileBinding;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    FirebaseFirestore database;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

//        BannerView view = new BannerView(ProfileActivity.this,BannerID,new UnityBannerSize(320,50));
//        view.load();
//        binding.adView.addView(view);



        database.collection("USERS").document(firebaseAuth.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()){
                    DocumentSnapshot snapshot = task.getResult();

                    String name = (String) snapshot.get("name");
                    String mob = (String) snapshot.get("mobile");
                    String emailId = (String) snapshot.get("email");

                    binding.name.setText(name);
                    binding.email.setText(emailId);
                    binding.mobline.setText(mob);

                }
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.upbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,UpdateProfileAcvity.class);
                startActivity(intent);
            }
        });
    }

}