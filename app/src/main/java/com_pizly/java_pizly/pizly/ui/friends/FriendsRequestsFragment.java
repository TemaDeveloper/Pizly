package com_pizly.java_pizly.pizly.ui.friends;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.adapters.RequestAdapter;
import com_pizly.java_pizly.pizly.adapters.FriendsAdapter;
import com_pizly.java_pizly.pizly.managers.SharedPrefManager;
import com_pizly.java_pizly.pizly.models.BannedUser;
import com_pizly.java_pizly.pizly.models.Friend;
import com_pizly.java_pizly.pizly.rest.ApiClient;
import com_pizly.java_pizly.pizly.rest.ApiInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


  public class FriendsRequestsFragment extends Fragment {

    //----widgets----//
    private RecyclerView friendsRecyclerView, friendsRequestsRecyclerView;
    private TextView noFriendsTextView;
    //----IDs----//
    private int uid, blockedID;
    //----recycler view adapter----//
    private FriendsAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        uid = SharedPrefManager.getInstance(getContext()).getKeyID();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends_requests, container, false);

        init(view);

        friendsRequestsRecyclerView.setHasFixedSize(true);
        friendsRequestsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        ApiInterface apiInterface = ApiClient.getApiService();
        Call<List<Friend>> requestsCall = apiInterface.getRequests(uid);
        requestsCall.enqueue(new Callback<List<Friend>>() {
            @Override
            public void onResponse(Call<List<Friend>> call, Response<List<Friend>> response) {
                friendsRequestsRecyclerView.setAdapter(new RequestAdapter(response.body(), getContext()));
            }

            @Override
            public void onFailure(Call<List<Friend>> call, Throwable t) {

            }
        });

        friendsRecyclerView.setHasFixedSize(true);
        friendsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        blockedID = 0;

        Call<List<BannedUser>> bannedUserCall = apiInterface.getBlockedFriends(SharedPrefManager.getInstance(getContext()).getKeyID());
        bannedUserCall.enqueue(new Callback<List<BannedUser>>() {
            @Override
            public void onResponse(Call<List<BannedUser>> call, Response<List<BannedUser>> response) {
                for(BannedUser bannedUser : response.body()){
                    blockedID = bannedUser.getBlockToID();
                }
            }

            @Override
            public void onFailure(Call<List<BannedUser>> call, Throwable t) {

            }
        });

        getFriends();

        return view;
    }

    private void init(View view){
        friendsRecyclerView = view.findViewById(R.id.recycler_view_friends);
        friendsRequestsRecyclerView = view.findViewById(R.id.recycler_view_friends_requests);
        noFriendsTextView = view.findViewById(R.id.text_view_no_friends);
    }

    private void getFriends(){
        Call<List<Friend>> friendsCall = ApiClient.getApiService().getFriends(uid, uid);
        friendsCall.enqueue(new Callback<List<Friend>>() {
            @Override
            public void onResponse(Call<List<Friend>> call, Response<List<Friend>> response) {
                List<Friend> friends = response.body();
                adapter = new FriendsAdapter(response.body(), getContext(), getActivity());
                friendsRecyclerView.setAdapter(adapter);
                for(int i = 0; i < response.body().size(); i++){
                    if((response.body().get(i).getUid() == blockedID && response.body().get(i).getFriendID() == uid) ||
                            (response.body().get(i).getUid() == uid && response.body().get(i).getFriendID() == blockedID)){

                        friends.remove(i);


                    }
                }
                if(response.body().size() > 0){
                    friendsRecyclerView.setVisibility(View.VISIBLE);
                    noFriendsTextView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Friend>> call, Throwable t) {

            }
        });
    }

}