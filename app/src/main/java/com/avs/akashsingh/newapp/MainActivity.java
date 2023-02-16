package com.avs.akashsingh.newapp;

import static com.google.firebase.messaging.Constants.MessageNotificationKeys.TAG;

import androidx.annotation.NonNull;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.avs.akashsingh.newapp.databinding.ActivityMainBinding;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;


import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseFirestore database;
    private FirebaseAuth firebaseAuth;
    private ActionBarDrawerToggle toggle;

    long coins;
    String name;
    public static Dialog loadingDialog;
    String date,currentDate,todayDate;
    //spinweel
    int spinCounter = 0;
    int spinTotal = 15;

    // DalyBonus
    int spinCounter4 = 0;
    int spinTotal4= 1;

    // scratchCard
    int spinCounter5 = 0;
    int spinTotal5= 15;

    private FirebaseAnalytics analytics;
    public static  String GameID = "4726509";
    public static String BannerID="Banner_Android";
    public static String InterID="Interstitial_Android";
    public static Boolean TestMode = false; //(select any one. if you test then you select true)
    ProgressDialog progressDialog;


    // fb ads
    private AdView adView;
    InterstitialAd fInterstitialAd;
    int AdsValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        analytics = FirebaseAnalytics.getInstance(MainActivity.this);


        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        currentDate  = sh.getString("date", "");

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        todayDate = df.format(Calendar.getInstance().getTime());
        if(!currentDate.equals(todayDate)){
            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putString("date",todayDate);

            myEdit.putInt("spinCounter",spinCounter);
            myEdit.putInt("spinTotalLeft",spinTotal);

            myEdit.putInt("spinCounter4",spinCounter4);
            myEdit.putInt("spinTotalLeft4",spinTotal4);

            myEdit.putInt("spinCounter5",spinCounter5);
            myEdit.putInt("spinTotalLeft5",spinTotal5);

            myEdit.commit();
        }






        UnityAds.initialize(this,GameID,TestMode);
        BannerView view = new BannerView(MainActivity.this,BannerID,new UnityBannerSize(320,50));
        view.load();
       // binding.adView.addView(view);

        binding.toolbar.setTitle(getString(R.string.app_name));
        binding.toolbar.setTitleTextColor(getResources().getColor(R.color.white));

//

        drawerIcon();
        ///loading Dialog
        loadingDialog = new Dialog(MainActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        /////end loading dialog

        loadInterstital();

        // fb sdk
        AudienceNetworkAds.initialize(MainActivity.this);
        // fb sdk

        // Fb ads
        //// banner ads
        adView = new AdView(this, getResources().getString(R.string.fb_banner_ads), AdSize.BANNER_HEIGHT_50);
        binding.bannerContainer.addView(adView);
        adView.loadAd();



       AdListener adListener = new AdListener() {
           @Override
           public void onError(Ad ad, AdError adError) {
//               Toast.makeText(
//                               MainActivity.this,
//                               "Error: " + adError.getErrorMessage(),
//                               Toast.LENGTH_LONG)
//                       .show();
           }

           @Override
           public void onAdLoaded(Ad ad) {

           }

           @Override
           public void onAdClicked(Ad ad) {

           }

           @Override
           public void onLoggingImpression(Ad ad) {

           }
       };

        adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());


        //// banner ads

        fInterstitialAd = new InterstitialAd(this, getResources().getString(R.string.fb_inter_ads));
        fInterstitialAd.loadAd();
        // Create listeners for the Interstitial Ad
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
              if (AdsValue==0){
                  startActivity(new Intent(MainActivity.this,CategoryActivity.class));
              }else if (AdsValue==1){
                  Intent intent = new Intent(MainActivity.this,MyWalletActivity.class);
                  startActivity(intent);
              }else if (AdsValue==2){
                  startActivity(new Intent(MainActivity.this,ProfileActivity.class));
              }else if (AdsValue==3){
                  startActivity(new Intent(MainActivity.this,SpinWheelActivity.class));
              }else if (AdsValue==4){
                  startActivity(new Intent(MainActivity.this,DailyCheckActivity.class));
              }else if (AdsValue==5){
                  startActivity(new Intent(MainActivity.this,ScratchCardActivity.class));

              }


            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.d("adsaaa","Failed"+adError.getErrorMessage());
               // Toast.makeText(MainActivity.this, "ADS_fail"+adError.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.d("adsaaa","Load");
               // Toast.makeText(MainActivity.this, "ADS_Load", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };

        fInterstitialAd.loadAd(
                fInterstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());



        binding.allcategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  UnityAds.show(MainActivity.this,InterID);
                if (fInterstitialAd.isAdLoaded()){
                   fInterstitialAd.show();
                   AdsValue = 0;
                }else {
                    fInterstitialAd.loadAd();
                    startActivity(new Intent(MainActivity.this,CategoryActivity.class));

                }

            }
        });
        binding.myWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  UnityAds.show(MainActivity.this,InterID);

                if (fInterstitialAd.isAdLoaded()){
                    fInterstitialAd.show();
                    AdsValue = 1;
                }else {
                    fInterstitialAd.loadAd();
                    Intent intent = new Intent(MainActivity.this,MyWalletActivity.class);
                    startActivity(intent);

                }


//                Intent intent = new Intent(MainActivity.this,MyWalletActivity.class);
//                startActivity(intent);

            }
        });

        binding.myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  UnityAds.show(MainActivity.this,InterID);

                if (fInterstitialAd.isAdLoaded()){
                    fInterstitialAd.show();
                    AdsValue = 2;
                }else {
                    fInterstitialAd.loadAd();
                    startActivity(new Intent(MainActivity.this,ProfileActivity.class));

                }

            }
        });
        binding.spinWheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fInterstitialAd.isAdLoaded()){
                    fInterstitialAd.show();
                    AdsValue = 3;
                }else {
                    fInterstitialAd.loadAd();
                    startActivity(new Intent(MainActivity.this,SpinWheelActivity.class));


                }



//                startActivity(new Intent(MainActivity.this,SpinWheelActivity.class));


            }
        });
        binding.dailyCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fInterstitialAd.isAdLoaded()){
                    fInterstitialAd.show();
                    AdsValue = 4;
                }else {
                    fInterstitialAd.loadAd();
                    startActivity(new Intent(MainActivity.this,DailyCheckActivity.class));


                }



            }
        });

        binding.scratchCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (fInterstitialAd.isAdLoaded()){
                    fInterstitialAd.show();
                    AdsValue = 5;
                }else {
                    fInterstitialAd.loadAd();
                    startActivity(new Intent(MainActivity.this,ScratchCardActivity.class));


                }



            }
        });




        firebaseNotification();

    }

        private void loadInterstital() {
        if (UnityAds.isInitialized()){
            UnityAds.load((InterID));
        }else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    UnityAds.load(InterID);

                }
            }, 5000);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        database.collection("USERS").document(firebaseAuth.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                      if (task.isSuccessful()){
                          DocumentSnapshot snapshot = task.getResult();
                          name = (String)snapshot.get("name");
                          coins = (long) snapshot.get("coins");
                          binding.coinsTotal.setText(String.valueOf("Coins " + coins));
                          binding.helloReaders1.setText(name);
                          loadingDialog.dismiss();
                      }else {
                          Toast.makeText(getApplicationContext(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                      }
            }
        });

    }
    void firebaseNotification(){
        FirebaseMessaging.getInstance().subscribeToTopic("QPaisa")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg ="Done";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }

                       // Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("कृपया इस ऐप को Play store पर 5 Star ratings दें Thank you ")
                .setCancelable(true)
                .setPositiveButton("exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        MainActivity.super.onBackPressed();

                    }
                })
                .setNegativeButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName())));
                        }
                        catch (ActivityNotFoundException e){
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName())));
                        }
                    }
                })
                .show();
    }
    public void drawerIcon(){
        toggle = new ActionBarDrawerToggle(this,binding.drawerLayout,binding.toolbar,R.string.drawerOpen,R.string.drawerClose);

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        binding. drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navigationView.setItemIconTintList(null);

        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            MenuItem menuItem;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                menuItem = item;


                binding.drawerLayout.closeDrawer(GravityCompat.START);
                binding.drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);

                        switch (menuItem.getItemId()) {

                            case R.id.myProfile:
                                       //  UnityAds.show(MainActivity.this,InterID);
                                         startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                                         break;
                                     case R.id.myWallet:
                                        // UnityAds.show(MainActivity.this,InterID);
                                         startActivity(new Intent(MainActivity.this,MyWalletActivity.class));

                                         break;
                                     case R.id.shareThis:

                                         try {
                                             Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                             shareIntent.setType("text/plain");
                                             shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                                             String shareMessage= "\nLet me recommend you this application You can get Rs 50/- as SIGN UP Bonous\n\n";
                                             shareMessage = shareMessage + "https://play.google.com/store/apps/details?id="+getPackageName();
                                             shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                                             startActivity(Intent.createChooser(shareIntent, "choose one"));
                                         } catch(Exception e) {
                                             //e.toString();
                                         }

                                         break;
                                     case R.id.rateThisApp:

                                         try{
                                             startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName())));
                                         }
                                         catch (ActivityNotFoundException e){
                                             startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName())));
                                         }

                                         break;
                                     case R.id.contactUs:
                                         UnityAds.show(MainActivity.this,InterID);
                                         Intent i = new Intent(Intent.ACTION_SEND);
                                         i.setType("message/rfc822");
                                         i.putExtra(Intent.EXTRA_EMAIL  , new String[]{getString(R.string.supported_email)});
                                         i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                                         i.putExtra(Intent.EXTRA_TEXT   , "body of email");
                                         try {
                                             startActivity(Intent.createChooser(i, "Send mail..."));
                                         } catch (android.content.ActivityNotFoundException ex) {
                                             Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                                         }

                                         break;
                                     case R.id.privacyPoliy:

                                         Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.privacy_policy)));
                                         startActivity(browserIntent);

                                         break;
                                     case R.id.termsCondi:
                                         Intent searchIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.terms_condition)));
                                         startActivity(searchIntent);

                                         break;
                                     case R.id.logout:
                                         FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                         firebaseAuth.signOut();
                                         startActivity(new Intent(MainActivity.this,LogInActivity.class));

                        }
                        binding.drawerLayout.removeDrawerListener(this);


                    }
                });

                return true;
            }
        });
    }

    }
