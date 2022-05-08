// Generated by view binder compiler. Do not edit!
package com.example.workroute.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.andremion.counterfab.CounterFab;
import com.example.workroute.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.divider.MaterialDivider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityDriverMapBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final ImageView botonDrag;

  @NonNull
  public final MaterialButton btnRideStatus;

  @NonNull
  public final CounterFab buttonMenu;

  @NonNull
  public final ImageButton buttonMessage;

  @NonNull
  public final CounterFab buttonMessages;

  @NonNull
  public final CounterFab buttonNotifications;

  @NonNull
  public final CounterFab buttonProfile;

  @NonNull
  public final FloatingActionButton buttonUbi;

  @NonNull
  public final MaterialDivider divider;

  @NonNull
  public final RelativeLayout informationUser;

  @NonNull
  public final ImageButton locationIcon;

  @NonNull
  public final ImageButton locationIcon1;

  @NonNull
  public final ImageView profilePhotoSheet;

  @NonNull
  public final CoordinatorLayout rela;

  @NonNull
  public final FrameLayout secondSheet;

  @NonNull
  public final FrameLayout sheet;

  @NonNull
  public final RelativeLayout toolbarBottomSheet;

  @NonNull
  public final TextView txtDestination;

  @NonNull
  public final TextView txtDistance;

  @NonNull
  public final TextView txtName;

  @NonNull
  public final TextView txtStartLocation;

  @NonNull
  public final TextView txtStatusMessage;

  @NonNull
  public final TextView txtTotalPayTravel;

  @NonNull
  public final TextView txtTravelInformation;

  private ActivityDriverMapBinding(@NonNull CoordinatorLayout rootView,
      @NonNull ImageView botonDrag, @NonNull MaterialButton btnRideStatus,
      @NonNull CounterFab buttonMenu, @NonNull ImageButton buttonMessage,
      @NonNull CounterFab buttonMessages, @NonNull CounterFab buttonNotifications,
      @NonNull CounterFab buttonProfile, @NonNull FloatingActionButton buttonUbi,
      @NonNull MaterialDivider divider, @NonNull RelativeLayout informationUser,
      @NonNull ImageButton locationIcon, @NonNull ImageButton locationIcon1,
      @NonNull ImageView profilePhotoSheet, @NonNull CoordinatorLayout rela,
      @NonNull FrameLayout secondSheet, @NonNull FrameLayout sheet,
      @NonNull RelativeLayout toolbarBottomSheet, @NonNull TextView txtDestination,
      @NonNull TextView txtDistance, @NonNull TextView txtName, @NonNull TextView txtStartLocation,
      @NonNull TextView txtStatusMessage, @NonNull TextView txtTotalPayTravel,
      @NonNull TextView txtTravelInformation) {
    this.rootView = rootView;
    this.botonDrag = botonDrag;
    this.btnRideStatus = btnRideStatus;
    this.buttonMenu = buttonMenu;
    this.buttonMessage = buttonMessage;
    this.buttonMessages = buttonMessages;
    this.buttonNotifications = buttonNotifications;
    this.buttonProfile = buttonProfile;
    this.buttonUbi = buttonUbi;
    this.divider = divider;
    this.informationUser = informationUser;
    this.locationIcon = locationIcon;
    this.locationIcon1 = locationIcon1;
    this.profilePhotoSheet = profilePhotoSheet;
    this.rela = rela;
    this.secondSheet = secondSheet;
    this.sheet = sheet;
    this.toolbarBottomSheet = toolbarBottomSheet;
    this.txtDestination = txtDestination;
    this.txtDistance = txtDistance;
    this.txtName = txtName;
    this.txtStartLocation = txtStartLocation;
    this.txtStatusMessage = txtStatusMessage;
    this.txtTotalPayTravel = txtTotalPayTravel;
    this.txtTravelInformation = txtTravelInformation;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityDriverMapBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityDriverMapBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_driver_map, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityDriverMapBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.boton_drag;
      ImageView botonDrag = ViewBindings.findChildViewById(rootView, id);
      if (botonDrag == null) {
        break missingId;
      }

      id = R.id.btn_rideStatus;
      MaterialButton btnRideStatus = ViewBindings.findChildViewById(rootView, id);
      if (btnRideStatus == null) {
        break missingId;
      }

      id = R.id.buttonMenu;
      CounterFab buttonMenu = ViewBindings.findChildViewById(rootView, id);
      if (buttonMenu == null) {
        break missingId;
      }

      id = R.id.button_message;
      ImageButton buttonMessage = ViewBindings.findChildViewById(rootView, id);
      if (buttonMessage == null) {
        break missingId;
      }

      id = R.id.buttonMessages;
      CounterFab buttonMessages = ViewBindings.findChildViewById(rootView, id);
      if (buttonMessages == null) {
        break missingId;
      }

      id = R.id.button_notifications;
      CounterFab buttonNotifications = ViewBindings.findChildViewById(rootView, id);
      if (buttonNotifications == null) {
        break missingId;
      }

      id = R.id.buttonProfile;
      CounterFab buttonProfile = ViewBindings.findChildViewById(rootView, id);
      if (buttonProfile == null) {
        break missingId;
      }

      id = R.id.buttonUbi;
      FloatingActionButton buttonUbi = ViewBindings.findChildViewById(rootView, id);
      if (buttonUbi == null) {
        break missingId;
      }

      id = R.id.divider;
      MaterialDivider divider = ViewBindings.findChildViewById(rootView, id);
      if (divider == null) {
        break missingId;
      }

      id = R.id.information_user;
      RelativeLayout informationUser = ViewBindings.findChildViewById(rootView, id);
      if (informationUser == null) {
        break missingId;
      }

      id = R.id.location_icon;
      ImageButton locationIcon = ViewBindings.findChildViewById(rootView, id);
      if (locationIcon == null) {
        break missingId;
      }

      id = R.id.location_icon;
      ImageButton locationIcon1 = ViewBindings.findChildViewById(rootView, id);
      if (locationIcon1 == null) {
        break missingId;
      }

      id = R.id.profile_photo_sheet;
      ImageView profilePhotoSheet = ViewBindings.findChildViewById(rootView, id);
      if (profilePhotoSheet == null) {
        break missingId;
      }

      CoordinatorLayout rela = (CoordinatorLayout) rootView;

      id = R.id.second_sheet;
      FrameLayout secondSheet = ViewBindings.findChildViewById(rootView, id);
      if (secondSheet == null) {
        break missingId;
      }

      id = R.id.sheet;
      FrameLayout sheet = ViewBindings.findChildViewById(rootView, id);
      if (sheet == null) {
        break missingId;
      }

      id = R.id.toolbar_bottom_sheet;
      RelativeLayout toolbarBottomSheet = ViewBindings.findChildViewById(rootView, id);
      if (toolbarBottomSheet == null) {
        break missingId;
      }

      id = R.id.txt_destination;
      TextView txtDestination = ViewBindings.findChildViewById(rootView, id);
      if (txtDestination == null) {
        break missingId;
      }

      id = R.id.txt_distance;
      TextView txtDistance = ViewBindings.findChildViewById(rootView, id);
      if (txtDistance == null) {
        break missingId;
      }

      id = R.id.txt_name;
      TextView txtName = ViewBindings.findChildViewById(rootView, id);
      if (txtName == null) {
        break missingId;
      }

      id = R.id.txt_startLocation;
      TextView txtStartLocation = ViewBindings.findChildViewById(rootView, id);
      if (txtStartLocation == null) {
        break missingId;
      }

      id = R.id.txt_status_message;
      TextView txtStatusMessage = ViewBindings.findChildViewById(rootView, id);
      if (txtStatusMessage == null) {
        break missingId;
      }

      id = R.id.txt_total_pay_travel;
      TextView txtTotalPayTravel = ViewBindings.findChildViewById(rootView, id);
      if (txtTotalPayTravel == null) {
        break missingId;
      }

      id = R.id.txt_travel_information;
      TextView txtTravelInformation = ViewBindings.findChildViewById(rootView, id);
      if (txtTravelInformation == null) {
        break missingId;
      }

      return new ActivityDriverMapBinding((CoordinatorLayout) rootView, botonDrag, btnRideStatus,
          buttonMenu, buttonMessage, buttonMessages, buttonNotifications, buttonProfile, buttonUbi,
          divider, informationUser, locationIcon, locationIcon1, profilePhotoSheet, rela,
          secondSheet, sheet, toolbarBottomSheet, txtDestination, txtDistance, txtName,
          txtStartLocation, txtStatusMessage, txtTotalPayTravel, txtTravelInformation);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}