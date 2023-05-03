package com_pizly.java_pizly.pizly.ui.friends;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;

import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.adapters.ViewPagerAdapter;


public class BottomSheetFriendsFragment extends BottomSheetDialogFragment {

    private ViewPager2 friendsSpecialsViewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter friendsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view = inflater.inflate(R.layout.fragment_friends, container, false);


        friendsSpecialsViewPager = view.findViewById(R.id.view_pager_friends_specials);
        tabLayout = view.findViewById(R.id.tab_layout_friends_specials);

        tabLayout.addTab(tabLayout.newTab().setText("Friends"));
        tabLayout.addTab(tabLayout.newTab().setText("\"Specials\""));

        friendsAdapter = new ViewPagerAdapter(getFragmentManager(), getLifecycle());
        friendsAdapter.addFragment(new FriendsRequestsFragment());
        friendsAdapter.addFragment(new SpecialsFragment());
        friendsSpecialsViewPager.setAdapter(friendsAdapter);

        friendsSpecialsViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                friendsSpecialsViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }
}