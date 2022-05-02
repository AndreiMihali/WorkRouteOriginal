// Generated by view binder compiler. Do not edit!
package com.example.workroute.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.workroute.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityProfileBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final LinearLayout activeSubscriptions;

  @NonNull
  public final MaterialCardView cardViewImage;

  @NonNull
  public final LinearLayout deleteAccount;

  @NonNull
  public final LinearLayout logout;

  @NonNull
  public final LinearLayout payMethod;

  @NonNull
  public final LinearLayout personalInformation;

  @NonNull
  public final ImageView profileImage;

  @NonNull
  public final LinearLayout settings;

  @NonNull
  public final MaterialToolbar toolbar;

  private ActivityProfileBinding(@NonNull RelativeLayout rootView,
      @NonNull LinearLayout activeSubscriptions, @NonNull MaterialCardView cardViewImage,
      @NonNull LinearLayout deleteAccount, @NonNull LinearLayout logout,
      @NonNull LinearLayout payMethod, @NonNull LinearLayout personalInformation,
      @NonNull ImageView profileImage, @NonNull LinearLayout settings,
      @NonNull MaterialToolbar toolbar) {
    this.rootView = rootView;
    this.activeSubscriptions = activeSubscriptions;
    this.cardViewImage = cardViewImage;
    this.deleteAccount = deleteAccount;
    this.logout = logout;
    this.payMethod = payMethod;
    this.personalInformation = personalInformation;
    this.profileImage = profileImage;
    this.settings = settings;
    this.toolbar = toolbar;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_profile, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityProfileBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.activeSubscriptions;
      LinearLayout activeSubscriptions = ViewBindings.findChildViewById(rootView, id);
      if (activeSubscriptions == null) {
        break missingId;
      }

      id = R.id.cardViewImage;
      MaterialCardView cardViewImage = ViewBindings.findChildViewById(rootView, id);
      if (cardViewImage == null) {
        break missingId;
      }

      id = R.id.deleteAccount;
      LinearLayout deleteAccount = ViewBindings.findChildViewById(rootView, id);
      if (deleteAccount == null) {
        break missingId;
      }

      id = R.id.logout;
      LinearLayout logout = ViewBindings.findChildViewById(rootView, id);
      if (logout == null) {
        break missingId;
      }

      id = R.id.payMethod;
      LinearLayout payMethod = ViewBindings.findChildViewById(rootView, id);
      if (payMethod == null) {
        break missingId;
      }

      id = R.id.personal_information;
      LinearLayout personalInformation = ViewBindings.findChildViewById(rootView, id);
      if (personalInformation == null) {
        break missingId;
      }

      id = R.id.profileImage;
      ImageView profileImage = ViewBindings.findChildViewById(rootView, id);
      if (profileImage == null) {
        break missingId;
      }

      id = R.id.settings;
      LinearLayout settings = ViewBindings.findChildViewById(rootView, id);
      if (settings == null) {
        break missingId;
      }

      id = R.id.toolbar;
      MaterialToolbar toolbar = ViewBindings.findChildViewById(rootView, id);
      if (toolbar == null) {
        break missingId;
      }

      return new ActivityProfileBinding((RelativeLayout) rootView, activeSubscriptions,
          cardViewImage, deleteAccount, logout, payMethod, personalInformation, profileImage,
          settings, toolbar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
