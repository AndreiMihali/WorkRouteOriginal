// Generated by view binder compiler. Do not edit!
package com.example.workroute.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.workroute.R;
import com.google.android.material.appbar.MaterialToolbar;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityNotificationsBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ProgressBar progressCircularNotifications;

  @NonNull
  public final RecyclerView recyclerNotifications;

  @NonNull
  public final MaterialToolbar toolbar;

  @NonNull
  public final TextView txtNullNotifications;

  private ActivityNotificationsBinding(@NonNull RelativeLayout rootView,
      @NonNull ProgressBar progressCircularNotifications,
      @NonNull RecyclerView recyclerNotifications, @NonNull MaterialToolbar toolbar,
      @NonNull TextView txtNullNotifications) {
    this.rootView = rootView;
    this.progressCircularNotifications = progressCircularNotifications;
    this.recyclerNotifications = recyclerNotifications;
    this.toolbar = toolbar;
    this.txtNullNotifications = txtNullNotifications;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityNotificationsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityNotificationsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_notifications, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityNotificationsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.progress_circular_notifications;
      ProgressBar progressCircularNotifications = ViewBindings.findChildViewById(rootView, id);
      if (progressCircularNotifications == null) {
        break missingId;
      }

      id = R.id.recycler_notifications;
      RecyclerView recyclerNotifications = ViewBindings.findChildViewById(rootView, id);
      if (recyclerNotifications == null) {
        break missingId;
      }

      id = R.id.toolbar;
      MaterialToolbar toolbar = ViewBindings.findChildViewById(rootView, id);
      if (toolbar == null) {
        break missingId;
      }

      id = R.id.txt_null_notifications;
      TextView txtNullNotifications = ViewBindings.findChildViewById(rootView, id);
      if (txtNullNotifications == null) {
        break missingId;
      }

      return new ActivityNotificationsBinding((RelativeLayout) rootView,
          progressCircularNotifications, recyclerNotifications, toolbar, txtNullNotifications);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
