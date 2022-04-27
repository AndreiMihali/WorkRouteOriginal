// Generated by view binder compiler. Do not edit!
package com.example.workroute.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.workroute.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityPersonalInformationBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final MaterialCardView cardName;

  @NonNull
  public final MaterialCardView cardPassword;

  @NonNull
  public final MaterialCardView cardViewImage;

  @NonNull
  public final CoordinatorLayout cordLayout;

  @NonNull
  public final TextInputEditText edPass;

  @NonNull
  public final TextInputEditText edUsername;

  @NonNull
  public final ImageView imageLock;

  @NonNull
  public final TextInputLayout layoutName;

  @NonNull
  public final TextInputLayout layoutPass;

  @NonNull
  public final LinearLayout layoutSubscriptionLevel;

  @NonNull
  public final ImageView profileImage;

  @NonNull
  public final LinearLayout relLayout;

  @NonNull
  public final RelativeLayout relLayoutGeneral;

  @NonNull
  public final RelativeLayout relativeChangingColorOnClick;

  @NonNull
  public final TextView textUnlockSave;

  @NonNull
  public final TextView textView3;

  @NonNull
  public final TextView textViewActualLVL;

  @NonNull
  public final MaterialToolbar toolbarInformation;

  @NonNull
  public final TextView tvUserMail;

  @NonNull
  public final TextView tvVariableActualSubscription;

  private ActivityPersonalInformationBinding(@NonNull RelativeLayout rootView,
      @NonNull MaterialCardView cardName, @NonNull MaterialCardView cardPassword,
      @NonNull MaterialCardView cardViewImage, @NonNull CoordinatorLayout cordLayout,
      @NonNull TextInputEditText edPass, @NonNull TextInputEditText edUsername,
      @NonNull ImageView imageLock, @NonNull TextInputLayout layoutName,
      @NonNull TextInputLayout layoutPass, @NonNull LinearLayout layoutSubscriptionLevel,
      @NonNull ImageView profileImage, @NonNull LinearLayout relLayout,
      @NonNull RelativeLayout relLayoutGeneral,
      @NonNull RelativeLayout relativeChangingColorOnClick, @NonNull TextView textUnlockSave,
      @NonNull TextView textView3, @NonNull TextView textViewActualLVL,
      @NonNull MaterialToolbar toolbarInformation, @NonNull TextView tvUserMail,
      @NonNull TextView tvVariableActualSubscription) {
    this.rootView = rootView;
    this.cardName = cardName;
    this.cardPassword = cardPassword;
    this.cardViewImage = cardViewImage;
    this.cordLayout = cordLayout;
    this.edPass = edPass;
    this.edUsername = edUsername;
    this.imageLock = imageLock;
    this.layoutName = layoutName;
    this.layoutPass = layoutPass;
    this.layoutSubscriptionLevel = layoutSubscriptionLevel;
    this.profileImage = profileImage;
    this.relLayout = relLayout;
    this.relLayoutGeneral = relLayoutGeneral;
    this.relativeChangingColorOnClick = relativeChangingColorOnClick;
    this.textUnlockSave = textUnlockSave;
    this.textView3 = textView3;
    this.textViewActualLVL = textViewActualLVL;
    this.toolbarInformation = toolbarInformation;
    this.tvUserMail = tvUserMail;
    this.tvVariableActualSubscription = tvVariableActualSubscription;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityPersonalInformationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityPersonalInformationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_personal_information, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityPersonalInformationBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.card_name;
      MaterialCardView cardName = ViewBindings.findChildViewById(rootView, id);
      if (cardName == null) {
        break missingId;
      }

      id = R.id.card_password;
      MaterialCardView cardPassword = ViewBindings.findChildViewById(rootView, id);
      if (cardPassword == null) {
        break missingId;
      }

      id = R.id.cardViewImage;
      MaterialCardView cardViewImage = ViewBindings.findChildViewById(rootView, id);
      if (cardViewImage == null) {
        break missingId;
      }

      id = R.id.cord_layout;
      CoordinatorLayout cordLayout = ViewBindings.findChildViewById(rootView, id);
      if (cordLayout == null) {
        break missingId;
      }

      id = R.id.ed_pass;
      TextInputEditText edPass = ViewBindings.findChildViewById(rootView, id);
      if (edPass == null) {
        break missingId;
      }

      id = R.id.ed_username;
      TextInputEditText edUsername = ViewBindings.findChildViewById(rootView, id);
      if (edUsername == null) {
        break missingId;
      }

      id = R.id.imageLock;
      ImageView imageLock = ViewBindings.findChildViewById(rootView, id);
      if (imageLock == null) {
        break missingId;
      }

      id = R.id.layout_name;
      TextInputLayout layoutName = ViewBindings.findChildViewById(rootView, id);
      if (layoutName == null) {
        break missingId;
      }

      id = R.id.layout_pass;
      TextInputLayout layoutPass = ViewBindings.findChildViewById(rootView, id);
      if (layoutPass == null) {
        break missingId;
      }

      id = R.id.layout_subscriptionLevel;
      LinearLayout layoutSubscriptionLevel = ViewBindings.findChildViewById(rootView, id);
      if (layoutSubscriptionLevel == null) {
        break missingId;
      }

      id = R.id.profileImage;
      ImageView profileImage = ViewBindings.findChildViewById(rootView, id);
      if (profileImage == null) {
        break missingId;
      }

      id = R.id.rel_layout;
      LinearLayout relLayout = ViewBindings.findChildViewById(rootView, id);
      if (relLayout == null) {
        break missingId;
      }

      RelativeLayout relLayoutGeneral = (RelativeLayout) rootView;

      id = R.id.relativeChangingColorOnClick;
      RelativeLayout relativeChangingColorOnClick = ViewBindings.findChildViewById(rootView, id);
      if (relativeChangingColorOnClick == null) {
        break missingId;
      }

      id = R.id.textUnlock_Save;
      TextView textUnlockSave = ViewBindings.findChildViewById(rootView, id);
      if (textUnlockSave == null) {
        break missingId;
      }

      id = R.id.textView3;
      TextView textView3 = ViewBindings.findChildViewById(rootView, id);
      if (textView3 == null) {
        break missingId;
      }

      id = R.id.textViewActualLVL;
      TextView textViewActualLVL = ViewBindings.findChildViewById(rootView, id);
      if (textViewActualLVL == null) {
        break missingId;
      }

      id = R.id.toolbarInformation;
      MaterialToolbar toolbarInformation = ViewBindings.findChildViewById(rootView, id);
      if (toolbarInformation == null) {
        break missingId;
      }

      id = R.id.tv_userMail;
      TextView tvUserMail = ViewBindings.findChildViewById(rootView, id);
      if (tvUserMail == null) {
        break missingId;
      }

      id = R.id.tv_variableActualSubscription;
      TextView tvVariableActualSubscription = ViewBindings.findChildViewById(rootView, id);
      if (tvVariableActualSubscription == null) {
        break missingId;
      }

      return new ActivityPersonalInformationBinding((RelativeLayout) rootView, cardName,
          cardPassword, cardViewImage, cordLayout, edPass, edUsername, imageLock, layoutName,
          layoutPass, layoutSubscriptionLevel, profileImage, relLayout, relLayoutGeneral,
          relativeChangingColorOnClick, textUnlockSave, textView3, textViewActualLVL,
          toolbarInformation, tvUserMail, tvVariableActualSubscription);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
