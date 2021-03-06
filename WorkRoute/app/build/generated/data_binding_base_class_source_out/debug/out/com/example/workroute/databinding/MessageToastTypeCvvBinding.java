// Generated by view binder compiler. Do not edit!
package com.example.workroute.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.workroute.R;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class MessageToastTypeCvvBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextInputLayout layoutEdCvv;

  @NonNull
  public final TextView txtTitle;

  private MessageToastTypeCvvBinding(@NonNull RelativeLayout rootView,
      @NonNull TextInputLayout layoutEdCvv, @NonNull TextView txtTitle) {
    this.rootView = rootView;
    this.layoutEdCvv = layoutEdCvv;
    this.txtTitle = txtTitle;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static MessageToastTypeCvvBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static MessageToastTypeCvvBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.message_toast_type_cvv, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static MessageToastTypeCvvBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.layout_ed_cvv;
      TextInputLayout layoutEdCvv = ViewBindings.findChildViewById(rootView, id);
      if (layoutEdCvv == null) {
        break missingId;
      }

      id = R.id.txt_title;
      TextView txtTitle = ViewBindings.findChildViewById(rootView, id);
      if (txtTitle == null) {
        break missingId;
      }

      return new MessageToastTypeCvvBinding((RelativeLayout) rootView, layoutEdCvv, txtTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
