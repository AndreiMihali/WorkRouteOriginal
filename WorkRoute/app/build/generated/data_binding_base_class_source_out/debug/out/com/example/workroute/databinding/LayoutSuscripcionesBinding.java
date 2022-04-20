// Generated by view binder compiler. Do not edit!
package com.example.workroute.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.workroute.R;
import com.google.android.material.button.MaterialButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class LayoutSuscripcionesBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageButton buttonCard;

  @NonNull
  public final MaterialButton buttonSuscribe;

  @NonNull
  public final LinearLayout prices;

  @NonNull
  public final LinearLayout titles;

  @NonNull
  public final Toolbar toolbar;

  private LayoutSuscripcionesBinding(@NonNull RelativeLayout rootView,
      @NonNull ImageButton buttonCard, @NonNull MaterialButton buttonSuscribe,
      @NonNull LinearLayout prices, @NonNull LinearLayout titles, @NonNull Toolbar toolbar) {
    this.rootView = rootView;
    this.buttonCard = buttonCard;
    this.buttonSuscribe = buttonSuscribe;
    this.prices = prices;
    this.titles = titles;
    this.toolbar = toolbar;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static LayoutSuscripcionesBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static LayoutSuscripcionesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.layout_suscripciones, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static LayoutSuscripcionesBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.button_card;
      ImageButton buttonCard = ViewBindings.findChildViewById(rootView, id);
      if (buttonCard == null) {
        break missingId;
      }

      id = R.id.button_suscribe;
      MaterialButton buttonSuscribe = ViewBindings.findChildViewById(rootView, id);
      if (buttonSuscribe == null) {
        break missingId;
      }

      id = R.id.prices;
      LinearLayout prices = ViewBindings.findChildViewById(rootView, id);
      if (prices == null) {
        break missingId;
      }

      id = R.id.titles;
      LinearLayout titles = ViewBindings.findChildViewById(rootView, id);
      if (titles == null) {
        break missingId;
      }

      id = R.id.toolbar;
      Toolbar toolbar = ViewBindings.findChildViewById(rootView, id);
      if (toolbar == null) {
        break missingId;
      }

      return new LayoutSuscripcionesBinding((RelativeLayout) rootView, buttonCard, buttonSuscribe,
          prices, titles, toolbar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}