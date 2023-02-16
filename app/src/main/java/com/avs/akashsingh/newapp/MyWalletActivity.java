package com.avs.akashsingh.newapp;

import static com.avs.akashsingh.newapp.MainActivity.InterID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.avs.akashsingh.newapp.Model.WithdrawRequest;

import com.avs.akashsingh.newapp.databinding.ActivityMyWalletBinding;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unity3d.ads.UnityAds;

import java.util.HashMap;
import java.util.Map;

public class MyWalletActivity extends AppCompatActivity {
    ActivityMyWalletBinding binding;
    FirebaseFirestore database;
    FirebaseAuth firebaseAuth;

    private long coins;
    private String name;
    private long coinsAmount;

    public static Dialog loadingDialog,loadingDialog1;
    String paymentType;

    // fb ads
    private AdView adView;
    InterstitialAd fInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyWalletBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        adView = new AdView(this, getResources().getString(R.string.fb_banner_ads1), AdSize.BANNER_HEIGHT_50);
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
                finish();

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


//

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                if (fInterstitialAd.isAdLoaded()){
                    fInterstitialAd.show();
                }else {
                    finish();

                }

            }
        });

        ///loading Dialog
        loadingDialog1 = new Dialog(MyWalletActivity.this);
        loadingDialog1.setContentView(R.layout.loading_progress_dialog);
        loadingDialog1.setCancelable(false);
        loadingDialog1.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog1.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog1.show();
        /////end loading dialog


        loadingDialog = new Dialog(MyWalletActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        // coins fetch
        database.collection("USERS")
                .document(firebaseAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()){
                            DocumentSnapshot snapshot = task.getResult();
                            coins = (long) snapshot.get("coins");  // error line
                            name = (String)snapshot.get("name");
                            binding.textView3.setText(String.valueOf("Coins = "+coins));
                            loadingDialog1.dismiss();
                        }else {
                            Toast.makeText(getApplicationContext(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

      //   coins fetch finish

        binding.g1.setChecked(true);



       binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.g1:
                        // do operations specific to this selection
                        binding.withdrawMob.setHint("Enter G pay Number");
                        paymentType = "G pay";
                        Toast.makeText(MyWalletActivity.this, ""+paymentType, Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.g2:
                        // do operations specific to this selection
                        binding.withdrawMob.setHint("Enter Phone pay Number");
                        paymentType = "Phone pay";
                        Toast.makeText(MyWalletActivity.this, ""+paymentType, Toast.LENGTH_SHORT).show();



                        break;
                    case R.id.g3:
                        // do operations specific to this selection
                        binding.withdrawMob.setHint("Enter Paytm Number");
                        paymentType = "Paytm";
                        Toast.makeText(MyWalletActivity.this, ""+paymentType, Toast.LENGTH_SHORT).show();



                        break;
                    case R.id.g4:
                        // do operations specific to this selection
                        binding.withdrawMob.setHint("Enter Paypal Gmail I'D");
                        paymentType = "Paypal";
                        Toast.makeText(MyWalletActivity.this, ""+paymentType, Toast.LENGTH_SHORT).show();



                        break;
                }
            }
        });

        binding.textView3.setText(String.valueOf(coins));
        binding.requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (!binding.withdrawMob.getText().toString().equals("")){

                        if (!binding.amount.getText().toString().equals("")){
                            loadingDialog.show();
                            binding.requestButton.setEnabled(true);
                            if(coins > 10000) {
                                String uid = FirebaseAuth.getInstance().getUid();
                                String mobil =   binding.withdrawMob.getText().toString();
                               // coinsAmount = Long.parseLong(binding.amount.getText().toString());
                                coinsAmount = Long.parseLong(binding.amount.getText().toString());

                                if (coins>=coinsAmount) {

                                    WithdrawRequest request = new WithdrawRequest(uid, mobil, name, coinsAmount,paymentType);
                                    database
                                            .collection("withdraws")
                                            .document(uid)
                                            .set(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            long finalCoins = coins - coinsAmount;
                                            binding.textView3.setText(String.valueOf("Coins = " + finalCoins));
                                            Map<String, Object> updateUserData = new HashMap<>();
                                            updateUserData.put("coins", finalCoins);

                                            database.collection("USERS").document(firebaseAuth.getUid())
                                                    .update(updateUserData)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                              //  UnityAds.show(MyWalletActivity.this,InterID);
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });

                                            loadingDialog.dismiss();
                                            Dialog dialog = new Dialog(MyWalletActivity.this);
                                            dialog.setContentView(R.layout.request_send_diloag);
                                            dialog.setCancelable(true);
                                            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                                            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                                            dialog.show();
                                            binding.withdrawMob.setText("");
                                            binding.amount.setText("");
                                            Toast.makeText(MyWalletActivity.this, "Request sent successfully.", Toast.LENGTH_SHORT).show();
                                            binding.requestButton.setEnabled(true);


                                        }
                                    });

                                }else {

                                    loadingDialog.dismiss();
                                    Dialog dialog = new Dialog(MyWalletActivity.this);
                                    dialog.setContentView(R.layout.infcument_coins_diolag);
                                    dialog.setCancelable(true);
                                    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                                    dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                                    dialog.show();
                                    binding.requestButton.setEnabled(true);
                                    UnityAds.show(MyWalletActivity.this,InterID);
                                }


                            } else {
                                loadingDialog.dismiss();
                                Dialog dialog = new Dialog(MyWalletActivity.this);
                                dialog.setContentView(R.layout.infcument_coins_diolag);
                                dialog.setCancelable(true);
                                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                                dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                                dialog.show();
                                binding.requestButton.setEnabled(true);
                            }
                        }else {
                            binding.amount.setError("please enter coins");
                        }
                    }else {
                        binding.withdrawMob.setError("Please enter coins");
                    }

            }
        });


    }

    @Override
    public void onBackPressed() {
        if (fInterstitialAd.isAdLoaded()){
            fInterstitialAd.show();
        }else {
            finish();

        }
    }
}