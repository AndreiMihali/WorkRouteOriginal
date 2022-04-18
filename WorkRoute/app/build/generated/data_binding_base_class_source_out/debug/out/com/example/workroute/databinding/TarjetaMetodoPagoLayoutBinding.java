// Generated by view binder compiler. Do not edit!
package com.example.workroute.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public final class TarjetaMetodoPagoLayoutBinding implements ViewBinding {
  @NonNull
  private final MaterialCardView rootView;

  @NonNull
  public final TextView cardName;

  @NonNull
  public final TextView cardNumber;

  @NonNull
  public final ImageView cardStatus;

  @NonNull
  public final MaterialCardView cardTarjeta;

  @NonNull
  public final ImageView cardType;

  private TarjetaMetodoPagoLayoutBinding(@NonNull MaterialCardView rootView,
      @NonNull TextView cardName, @NonNull TextView cardNumber, @NonNull ImageView cardStatus,
      @NonNull MaterialCardView cardTarjeta, @NonNull ImageView cardType) {
    this.rootView = rootView;
    this.cardName = cardName;
    this.cardNumber = cardNumber;
    this.cardStatus = cardStatus;
    this.cardTarjeta = cardTarjeta;
    this.cardType = cardType;
  }

  @Override
  @NonNull
  public MaterialCardView getRoot() {
    return rootView;
  }

  @NonNull
  public static TarjetaMetodoPagoLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static TarjetaMetodoPagoLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.tarjeta_metodo_pago_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static TarjetaMetodoPagoLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.card_name;
      TextView cardName = ViewBindings.findChildViewById(rootView, id);
      if (cardName == null) {
        break missingId;
      }

      id = R.id.card_number;
      TextView cardNumber = ViewBindings.findChildViewById(rootView, id);
      if (cardNumber == null) {
        break missingId;
      }

      id = R.id.card_status;
      ImageView cardStatus = ViewBindings.findChildViewById(rootView, id);
      if (cardStatus == null) {
        break missingId;
      }

      MaterialCardView cardTarjeta = (MaterialCardView) rootView;

      id = R.id.card_type;
      ImageView cardType = ViewBindings.findChildViewById(rootView, id);
      if (cardType == null) {
        break missingId;
      }

      return new TarjetaMetodoPagoLayoutBinding((MaterialCardView) rootView, cardName, cardNumber,
          cardStatus, cardTarjeta, cardType);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
