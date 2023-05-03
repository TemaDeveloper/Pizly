package com_pizly.java_pizly.pizly.ui.friends;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.adapters.BlockedUsersAdapter;
import com_pizly.java_pizly.pizly.adapters.FriendsAdapter;
import com_pizly.java_pizly.pizly.managers.SharedPrefManager;
import com_pizly.java_pizly.pizly.models.BannedUser;
import com_pizly.java_pizly.pizly.models.Friend;
import com_pizly.java_pizly.pizly.rest.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SpecialsFragment extends Fragment {

    //----widgets----//
    private RecyclerView blockedUsersRecyclerView;
    private TextView noBannedUsersTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //----Inflate the layout for this fragment----//
        View view = inflater.inflate(R.layout.fragment_specials, container, false);

        blockedUsersRecyclerView = view.findViewById(R.id.recycler_view_blocked_friends);
        noBannedUsersTextView = view.findViewById(R.id.text_view_no_blocks);

        blockedUsersRecyclerView.setHasFixedSize(true);
        blockedUsersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setBannedList();

        return view;
    }

    private void setBannedList(){
        Call<List<BannedUser>> friendsCall = ApiClient.getApiService().getBlockedFriends(SharedPrefManager.getInstance(getContext()).getKeyID());
        friendsCall.enqueue(new Callback<List<BannedUser>>() {
            @Override
            public void onResponse(Call<List<BannedUser>> call, Response<List<BannedUser>> response) {
                //----set list from database----//
                blockedUsersRecyclerView.setAdapter(new BlockedUsersAdapter(response.body(), getContext(), getActivity()));
                //----turn text visible----//
                if(response.body().size() > 0){
                    blockedUsersRecyclerView.setVisibility(View.VISIBLE);
                    noBannedUsersTextView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<List<BannedUser>> call, Throwable t) {

            }
        });
    }

}