// Generated by view binder compiler. Do not edit!
package com.avs.akashsingh.newapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.avs.akashsingh.newapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMyWalletBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final LinearLayout adView;

  @NonNull
  public final EditText amount;

  @NonNull
  public final ImageView backButton;

  @NonNull
  public final ImageView imageView3;

  @NonNull
  public final Button requestButton;

  @NonNull
  public final TextView textView14;

  @NonNull
  public final TextView textView3;

  @NonNull
  public final TextView textView7;

  @NonNull
  public final Toolbar toolbar2;

  @NonNull
  public final EditText withdrawMob;

  private ActivityMyWalletBinding(@NonNull ConstraintLayout rootView, @NonNull LinearLayout adView,
      @NonNull EditText amount, @NonNull ImageView backButton, @NonNull ImageView imageView3,
      @NonNull Button requestButton, @NonNull TextView textView14, @NonNull TextView textView3,
      @NonNull TextView textView7, @NonNull Toolbar toolbar2, @NonNull EditText withdrawMob) {
    this.rootView = rootView;
    this.adView = adView;
    this.amount = amount;
    this.backButton = backButton;
    this.imageView3 = imageView3;
    this.requestButton = requestButton;
    this.textView14 = textView14;
    this.textView3 = textView3;
    this.textView7 = textView7;
    this.toolbar2 = toolbar2;
    this.withdrawMob = withdrawMob;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMyWalletBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMyWalletBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_my_wallet, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMyWalletBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.adView;
      LinearLayout adView = ViewBindings.findChildViewById(rootView, id);
      if (adView == null) {
        break missingId;
      }

      id = R.id.amount;
      EditText amount = ViewBindings.findChildViewById(rootView, id);
      if (amount == null) {
        break missingId;
      }

      id = R.id.backButton;
      ImageView backButton = ViewBindings.findChildViewById(rootView, id);
      if (backButton == null) {
        break missingId;
      }

      id = R.id.imageView3;
      ImageView imageView3 = ViewBindings.findChildViewById(rootView, id);
      if (imageView3 == null) {
        break missingId;
      }

      id = R.id.requestButton;
      Button requestButton = ViewBindings.findChildViewById(rootView, id);
      if (requestButton == null) {
        break missingId;
      }

      id = R.id.textView14;
      TextView textView14 = ViewBindings.findChildViewById(rootView, id);
      if (textView14 == null) {
        break missingId;
      }

      id = R.id.textView3;
      TextView textView3 = ViewBindings.findChildViewById(rootView, id);
      if (textView3 == null) {
        break missingId;
      }

      id = R.id.textView7;
      TextView textView7 = ViewBindings.findChildViewById(rootView, id);
      if (textView7 == null) {
        break missingId;
      }

      id = R.id.toolbar2;
      Toolbar toolbar2 = ViewBindings.findChildViewById(rootView, id);
      if (toolbar2 == null) {
        break missingId;
      }

      id = R.id.withdrawMob;
      EditText withdrawMob = ViewBindings.findChildViewById(rootView, id);
      if (withdrawMob == null) {
        break missingId;
      }

      return new ActivityMyWalletBinding((ConstraintLayout) rootView, adView, amount, backButton,
          imageView3, requestButton, textView14, textView3, textView7, toolbar2, withdrawMob);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
