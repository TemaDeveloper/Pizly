package com_pizly.java_pizly.pizly.ui.yourParties;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.adapters.YourPartiesTicketsAdapter;
import com_pizly.java_pizly.pizly.managers.SharedPrefManager;
import com_pizly.java_pizly.pizly.models.Party;
import com_pizly.java_pizly.pizly.models.Ticket;
import com_pizly.java_pizly.pizly.rest.ApiClient;
import com_pizly.java_pizly.pizly.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class YourPartiesFragment extends Fragment {

    //----widgets----//
    private RecyclerView spotsRecyclerView;
    private TextView noPartiesTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_your_parties, container, false);

        init(view);

        spotsRecyclerView.setHasFixedSize(true);
        spotsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setUpList();

        return view;
    }

    //----widgets initialization----//
    private void init(View view){
        spotsRecyclerView = view.findViewById(R.id.recycler_view_your_parties);
        noPartiesTextView = view.findViewById(R.id.text_view_no_parties);
    }

    private void setUpList() {
        ApiInterface apiInterface = ApiClient.getApiService();
        Call<List<Party>> partiesCall = apiInterface.getParties(SharedPrefManager.getInstance(getContext()).getKeyID());
        partiesCall.enqueue(new Callback<List<Party>>() {
            @Override
            public void onResponse(Call<List<Party>> call, Response<List<Party>> response) {
                if(response.isSuccessful()){
                    //----get the list of parties----//
                    //----set the recycler view adapter----//
                    spotsRecyclerView.setAdapter(new YourPartiesTicketsAdapter(response.body(), getContext(), getActivity()));
                    if(response.body().size() > 0){
                        noPartiesTextView.setVisibility(View.GONE);
                        spotsRecyclerView.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Party>> call, Throwable t) {
            }
        });

    }


}