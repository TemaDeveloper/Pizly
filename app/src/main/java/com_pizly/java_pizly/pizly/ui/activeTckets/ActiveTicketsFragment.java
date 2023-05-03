package com_pizly.java_pizly.pizly.ui.activeTckets;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import com_pizly.java_pizly.pizly.R;

import com_pizly.java_pizly.pizly.adapters.ViewPagerAdapter;
import com_pizly.java_pizly.pizly.ui.yourParties.YourPartiesFragment;


public class ActiveTicketsFragment extends Fragment {

    private ViewPager2 historyViewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter ticketsAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_active, container, false);

        historyViewPager = root.findViewById(R.id.view_pager_tickets);
        tabLayout = root.findViewById(R.id.tab_layout_tickets);

        tabLayout.addTab(tabLayout.newTab().setText("Tickets"));
        tabLayout.addTab(tabLayout.newTab().setText("Your Parties"));

        ticketsAdapter = new ViewPagerAdapter(getFragmentManager(), getLifecycle());
        ticketsAdapter.addFragment(new AllTicketsFragment());
        ticketsAdapter.addFragment(new YourPartiesFragment());
        historyViewPager.setAdapter(ticketsAdapter);

        historyViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                historyViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return root;
    }


}