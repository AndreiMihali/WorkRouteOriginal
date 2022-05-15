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

public final class ActivityCustomerMapBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final ImageView botonDrag;

  @NonNull
  public final MaterialButton buttonCancel;

  @NonNull
  public final CounterFab buttonMenu;

  @NonNull
  public final ImageButton buttonMessageCost;

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
  public final RelativeLayout informationUserCost;

  @NonNull
  public final ImageButton locationIcon;

  @NonNull
  public final ImageButton locationIcon1;

  @NonNull
  public final ImageView profilePhotoSheetCost;

  @NonNull
  public final CoordinatorLayout rela;

  @NonNull
  public final FrameLayout secondSheet;

  @NonNull
  public final FrameLayout sheet;

  @NonNull
  public final RelativeLayout toolbarBottomSheet;

  @NonNull
  public final TextView txtDepartureHome;

  @NonNull
  public final TextView txtDepartureWork;

  @NonNull
  public final TextView txtDestinationCost;

  @NonNull
  public final TextView txtDistanceCost;

  @NonNull
  public final TextView txtNameCost;

  @NonNull
  public final TextView txtStartLocationCost;

  @NonNull
  public final TextView txtTotalPayTravelCost;

  @NonNull
  public final TextView txtTravelInformationCost;

  private ActivityCustomerMapBinding(@NonNull CoordinatorLayout rootView,
      @NonNull ImageView botonDrag, @NonNull MaterialButton buttonCancel,
      @NonNull CounterFab buttonMenu, @NonNull ImageButton buttonMessageCost,
      @NonNull CounterFab buttonMessages, @NonNull CounterFab buttonNotifications,
      @NonNull CounterFab buttonProfile, @NonNull FloatingActionButton buttonUbi,
      @NonNull MaterialDivider divider, @NonNull RelativeLayout informationUserCost,
      @NonNull ImageButton locationIcon, @NonNull ImageButton locationIcon1,
      @NonNull ImageView profilePhotoSheetCost, @NonNull CoordinatorLayout rela,
      @NonNull FrameLayout secondSheet, @NonNull FrameLayout sheet,
      @NonNull RelativeLayout toolbarBottomSheet, @NonNull TextView txtDepartureHome,
      @NonNull TextView txtDepartureWork, @NonNull TextView txtDestinationCost,
      @NonNull TextView txtDistanceCost, @NonNull TextView txtNameCost,
      @NonNull TextView txtStartLocationCost, @NonNull TextView txtTotalPayTravelCost,
      @NonNull TextView txtTravelInformationCost) {
    this.rootView = rootView;
    this.botonDrag = botonDrag;
    this.buttonCancel = buttonCancel;
    this.buttonMenu = buttonMenu;
    this.buttonMessageCost = buttonMessageCost;
    this.buttonMessages = buttonMessages;
    this.buttonNotifications = buttonNotifications;
    this.buttonProfile = buttonProfile;
    this.buttonUbi = buttonUbi;
    this.divider = divider;
    this.informationUserCost = informationUserCost;
    this.locationIcon = locationIcon;
    this.locationIcon1 = locationIcon1;
    this.profilePhotoSheetCost = profilePhotoSheetCost;
    this.rela = rela;
    this.secondSheet = secondSheet;
    this.sheet = sheet;
    this.toolbarBottomSheet = toolbarBottomSheet;
    this.txtDepartureHome = txtDepartureHome;
    this.txtDepartureWork = txtDepartureWork;
    this.txtDestinationCost = txtDestinationCost;
    this.txtDistanceCost = txtDistanceCost;
    this.txtNameCost = txtNameCost;
    this.txtStartLocationCost = txtStartLocationCost;
    this.txtTotalPayTravelCost = txtTotalPayTravelCost;
    this.txtTravelInformationCost = txtTravelInformationCost;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityCustomerMapBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityCustomerMapBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_customer_map, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityCustomerMapBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.boton_drag;
      ImageView botonDrag = ViewBindings.findChildViewById(rootView, id);
      if (botonDrag == null) {
        break missingId;
      }

      id = R.id.button_cancel;
      MaterialButton buttonCancel = ViewBindings.findChildViewById(rootView, id);
      if (buttonCancel == null) {
        break missingId;
      }

      id = R.id.buttonMenu;
      CounterFab buttonMenu = ViewBindings.findChildViewById(rootView, id);
      if (buttonMenu == null) {
        break missingId;
      }

      id = R.id.button_message_cost;
      ImageButton buttonMessageCost = ViewBindings.findChildViewById(rootView, id);
      if (buttonMessageCost == null) {
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

      id = R.id.information_user_cost;
      RelativeLayout informationUserCost = ViewBindings.findChildViewById(rootView, id);
      if (informationUserCost == null) {
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

      id = R.id.profile_photo_sheet_cost;
      ImageView profilePhotoSheetCost = ViewBindings.findChildViewById(rootView, id);
      if (profilePhotoSheetCost == null) {
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

      id = R.id.txt_departure_home;
      TextView txtDepartureHome = ViewBindings.findChildViewById(rootView, id);
      if (txtDepartureHome == null) {
        break missingId;
      }

      id = R.id.txt_departure_work;
      TextView txtDepartureWork = ViewBindings.findChildViewById(rootView, id);
      if (txtDepartureWork == null) {
        break missingId;
      }

      id = R.id.txt_destination_cost;
      TextView txtDestinationCost = ViewBindings.findChildViewById(rootView, id);
      if (txtDestinationCost == null) {
        break missingId;
      }

      id = R.id.txt_distance_cost;
      TextView txtDistanceCost = ViewBindings.findChildViewById(rootView, id);
      if (txtDistanceCost == null) {
        break missingId;
      }

      id = R.id.txt_name_cost;
      TextView txtNameCost = ViewBindings.findChildViewById(rootView, id);
      if (txtNameCost == null) {
        break missingId;
      }

      id = R.id.txt_startLocation_cost;
      TextView txtStartLocationCost = ViewBindings.findChildViewById(rootView, id);
      if (txtStartLocationCost == null) {
        break missingId;
      }

      id = R.id.txt_total_pay_travel_cost;
      TextView txtTotalPayTravelCost = ViewBindings.findChildViewById(rootView, id);
      if (txtTotalPayTravelCost == null) {
        break missingId;
      }

      id = R.id.txt_travel_information_cost;
      TextView txtTravelInformationCost = ViewBindings.findChildViewById(rootView, id);
      if (txtTravelInformationCost == null) {
        break missingId;
      }

      return new ActivityCustomerMapBinding((CoordinatorLayout) rootView, botonDrag, buttonCancel,
          buttonMenu, buttonMessageCost, buttonMessages, buttonNotifications, buttonProfile,
          buttonUbi, divider, informationUserCost, locationIcon, locationIcon1,
          profilePhotoSheetCost, rela, secondSheet, sheet, toolbarBottomSheet, txtDepartureHome,
          txtDepartureWork, txtDestinationCost, txtDistanceCost, txtNameCost, txtStartLocationCost,
          txtTotalPayTravelCost, txtTravelInformationCost);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
