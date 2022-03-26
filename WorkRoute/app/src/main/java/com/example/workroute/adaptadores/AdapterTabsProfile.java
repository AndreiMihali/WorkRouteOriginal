package com.example.workroute.adaptadores;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.workroute.fragmentos.CuentaFragment;
import com.example.workroute.fragmentos.DetalleFrgament;

public class AdapterTabsProfile extends FragmentStateAdapter {

    public AdapterTabsProfile(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new DetalleFrgament();
            case 1:
                return new CuentaFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public CharSequence getTabTitle(int position){
        switch (position){
            case 0:
                return "DETAILS";
            case 1:
                return "ACCOUNT";
            default:
                return "";
        }
    }
}
