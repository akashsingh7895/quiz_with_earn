package com.avs.akashsingh.newapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.avs.akashsingh.newapp.Model.CategoryAdapter;
import com.avs.akashsingh.newapp.Model.CategoryModel;

import com.avs.akashsingh.newapp.databinding.ActivityCategoryBinding;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity  {

    ActivityCategoryBinding binding;
    ArrayList<CategoryModel> categoryModels;
    FirebaseFirestore database;
    public static Dialog loadingDialog;

    // fb ads
    private AdView adView;
    InterstitialAd fInterstitialAd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        categoryModels = new ArrayList<>();
        database = FirebaseFirestore.getInstance();



        adView = new AdView(this, getResources().getString(R.string.fb_banner_ads), AdSize.BANNER_HEIGHT_50);
        binding.bannerContainer.addView(adView);
        adView.loadAd();


        fInterstitialAd = new InterstitialAd(this, getResources().getString(R.string.fb_inter_ads));
        fInterstitialAd.loadAd();
        // Create listeners for the Interstitial Ad

        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                Intent intent = new Intent(CategoryActivity.this,MainActivity.class);
                startActivity(intent);

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.d("error",adError.getErrorMessage());


            }

            @Override
            public void onAdLoaded(Ad ad) {


            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

//

            }
        };

        fInterstitialAd.loadAd(
                fInterstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());





        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fInterstitialAd.isAdLoaded()){
                    fInterstitialAd.show();
                }else {
                    startActivity(new Intent(CategoryActivity.this,MainActivity.class));
                    finish();
                }

            }
        });




        ///loading Dialog
        loadingDialog = new Dialog(CategoryActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        /////end loading dialog





        binding.CategoryRv.setLayoutManager(new GridLayoutManager(this,2));
        CategoryAdapter adapter = new CategoryAdapter(CategoryActivity.this,categoryModels);
        binding.CategoryRv.setAdapter(adapter);


        database.collection("categories")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        categoryModels.clear();
                        for (DocumentSnapshot snapshot : value.getDocuments()) {
                            CategoryModel model = snapshot.toObject(CategoryModel.class);
                            model.setCategoryId(snapshot.getId());
                            categoryModels.add(model);
                            loadingDialog.dismiss();
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
//

    }






    @Override
    public void onBackPressed() {
        if (fInterstitialAd.isAdLoaded()){
            fInterstitialAd.show();
        }else {
            startActivity(new Intent(CategoryActivity.this,MainActivity.class));
            finish();
        }
    }



}