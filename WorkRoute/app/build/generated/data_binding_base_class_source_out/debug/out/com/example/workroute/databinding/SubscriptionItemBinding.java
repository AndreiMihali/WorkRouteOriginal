// Generated by view binder compiler. Do not edit!
package com.example.workroute.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.workroute.R;
import com.google.android.material.card.MaterialCardView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class SubscriptionItemBinding implements ViewBinding {
  @NonNull
  private final MaterialCardView rootView;

  @NonNull
  public final MaterialCardView cardGlobal;

  @NonNull
  public final TextView txtDescription;

  @NonNull
  public final TextView txtPrice;

  @NonNull
  public final TextView txtTitle;

  private SubscriptionItemBinding(@NonNull MaterialCardView rootView,
      @NonNull MaterialCardView cardGlobal, @NonNull TextView txtDescription,
      @NonNull TextView txtPrice, @NonNull TextView txtTitle) {
    this.rootView = rootView;
    this.cardGlobal = cardGlobal;
    this.txtDescription = txtDescription;
    this.txtPrice = txtPrice;
    this.txtTitle = txtTitle;
  }

  @Override
  @NonNull
  public MaterialCardView getRoot() {
    return rootView;
  }

  @NonNull
  public static SubscriptionItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static SubscriptionItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.subscription_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static SubscriptionItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      MaterialCardView cardGlobal = (MaterialCardView) rootView;

      id = R.id.txt_description;
      TextView txtDescription = ViewBindings.findChildViewById(rootView, id);
      if (txtDescription == null) {
        break missingId;
      }

      id = R.id.txt_price;
      TextView txtPrice = ViewBindings.findChildViewById(rootView, id);
      if (txtPrice == null) {
        break missingId;
      }

      id = R.id.txt_title;
      TextView txtTitle = ViewBindings.findChildViewById(rootView, id);
      if (txtTitle == null) {
        break missingId;
      }

      return new SubscriptionItemBinding((MaterialCardView) rootView, cardGlobal, txtDescription,
          txtPrice, txtTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
