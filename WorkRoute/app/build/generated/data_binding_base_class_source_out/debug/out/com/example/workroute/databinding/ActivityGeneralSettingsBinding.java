// Generated by view binder compiler. Do not edit!
package com.example.workroute.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.workroute.R;
import com.google.android.material.appbar.MaterialToolbar;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityGeneralSettingsBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView appNotifications;

  @NonNull
  public final CheckBox checkBoxAppNotifications;

  @NonNull
  public final CheckBox checkBoxEmailNotifications;

  @NonNull
  public final TextView emailNotifications;

  @NonNull
  public final MaterialToolbar toolbarSettings;

  @NonNull
  public final TextView tvAboutUs;

  @NonNull
  public final TextView tvHowToUseWorkRoute;

  @NonNull
  public final TextView tvNotificationPreferences;

  private ActivityGeneralSettingsBinding(@NonNull LinearLayout rootView,
      @NonNull TextView appNotifications, @NonNull CheckBox checkBoxAppNotifications,
      @NonNull CheckBox checkBoxEmailNotifications, @NonNull TextView emailNotifications,
      @NonNull MaterialToolbar toolbarSettings, @NonNull TextView tvAboutUs,
      @NonNull TextView tvHowToUseWorkRoute, @NonNull TextView tvNotificationPreferences) {
    this.rootView = rootView;
    this.appNotifications = appNotifications;
    this.checkBoxAppNotifications = checkBoxAppNotifications;
    this.checkBoxEmailNotifications = checkBoxEmailNotifications;
    this.emailNotifications = emailNotifications;
    this.toolbarSettings = toolbarSettings;
    this.tvAboutUs = tvAboutUs;
    this.tvHowToUseWorkRoute = tvHowToUseWorkRoute;
    this.tvNotificationPreferences = tvNotificationPreferences;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityGeneralSettingsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityGeneralSettingsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_general_settings, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityGeneralSettingsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.appNotifications;
      TextView appNotifications = ViewBindings.findChildViewById(rootView, id);
      if (appNotifications == null) {
        break missingId;
      }

      id = R.id.checkBoxAppNotifications;
      CheckBox checkBoxAppNotifications = ViewBindings.findChildViewById(rootView, id);
      if (checkBoxAppNotifications == null) {
        break missingId;
      }

      id = R.id.checkBoxEmailNotifications;
      CheckBox checkBoxEmailNotifications = ViewBindings.findChildViewById(rootView, id);
      if (checkBoxEmailNotifications == null) {
        break missingId;
      }

      id = R.id.emailNotifications;
      TextView emailNotifications = ViewBindings.findChildViewById(rootView, id);
      if (emailNotifications == null) {
        break missingId;
      }

      id = R.id.toolbarSettings;
      MaterialToolbar toolbarSettings = ViewBindings.findChildViewById(rootView, id);
      if (toolbarSettings == null) {
        break missingId;
      }

      id = R.id.tvAboutUs;
      TextView tvAboutUs = ViewBindings.findChildViewById(rootView, id);
      if (tvAboutUs == null) {
        break missingId;
      }

      id = R.id.tvHowToUseWorkRoute;
      TextView tvHowToUseWorkRoute = ViewBindings.findChildViewById(rootView, id);
      if (tvHowToUseWorkRoute == null) {
        break missingId;
      }

      id = R.id.tvNotificationPreferences;
      TextView tvNotificationPreferences = ViewBindings.findChildViewById(rootView, id);
      if (tvNotificationPreferences == null) {
        break missingId;
      }

      return new ActivityGeneralSettingsBinding((LinearLayout) rootView, appNotifications,
          checkBoxAppNotifications, checkBoxEmailNotifications, emailNotifications, toolbarSettings,
          tvAboutUs, tvHowToUseWorkRoute, tvNotificationPreferences);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
