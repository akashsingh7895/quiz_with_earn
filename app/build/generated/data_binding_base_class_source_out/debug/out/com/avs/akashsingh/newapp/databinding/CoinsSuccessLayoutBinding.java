// Generated by view binder compiler. Do not edit!
package com.avs.akashsingh.newapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.avs.akashsingh.newapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class CoinsSuccessLayoutBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView imageView12;

  @NonNull
  public final Button retry;

  @NonNull
  public final TextView textView11;

  @NonNull
  public final TextView textView12;

  private CoinsSuccessLayoutBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageView imageView12, @NonNull Button retry, @NonNull TextView textView11,
      @NonNull TextView textView12) {
    this.rootView = rootView;
    this.imageView12 = imageView12;
    this.retry = retry;
    this.textView11 = textView11;
    this.textView12 = textView12;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static CoinsSuccessLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CoinsSuccessLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.coins_success_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CoinsSuccessLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imageView12;
      ImageView imageView12 = ViewBindings.findChildViewById(rootView, id);
      if (imageView12 == null) {
        break missingId;
      }

      id = R.id.retry;
      Button retry = ViewBindings.findChildViewById(rootView, id);
      if (retry == null) {
        break missingId;
      }

      id = R.id.textView11;
      TextView textView11 = ViewBindings.findChildViewById(rootView, id);
      if (textView11 == null) {
        break missingId;
      }

      id = R.id.textView12;
      TextView textView12 = ViewBindings.findChildViewById(rootView, id);
      if (textView12 == null) {
        break missingId;
      }

      return new CoinsSuccessLayoutBinding((ConstraintLayout) rootView, imageView12, retry,
          textView11, textView12);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
