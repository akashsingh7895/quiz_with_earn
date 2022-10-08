// Generated by view binder compiler. Do not edit!
package com.avs.akashsingh.newapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public final class RowLearbordBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView coins;

  @NonNull
  public final ImageView imageView8;

  @NonNull
  public final TextView name;

  @NonNull
  public final TextView rank;

  private RowLearbordBinding(@NonNull ConstraintLayout rootView, @NonNull TextView coins,
      @NonNull ImageView imageView8, @NonNull TextView name, @NonNull TextView rank) {
    this.rootView = rootView;
    this.coins = coins;
    this.imageView8 = imageView8;
    this.name = name;
    this.rank = rank;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static RowLearbordBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static RowLearbordBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.row_learbord, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static RowLearbordBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.coins;
      TextView coins = ViewBindings.findChildViewById(rootView, id);
      if (coins == null) {
        break missingId;
      }

      id = R.id.imageView8;
      ImageView imageView8 = ViewBindings.findChildViewById(rootView, id);
      if (imageView8 == null) {
        break missingId;
      }

      id = R.id.name;
      TextView name = ViewBindings.findChildViewById(rootView, id);
      if (name == null) {
        break missingId;
      }

      id = R.id.rank;
      TextView rank = ViewBindings.findChildViewById(rootView, id);
      if (rank == null) {
        break missingId;
      }

      return new RowLearbordBinding((ConstraintLayout) rootView, coins, imageView8, name, rank);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
