package com_pizly.java_pizly.pizly.ui.activeTckets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.adapters.ActiveTicketsAdapter;
import com_pizly.java_pizly.pizly.models.Ticket;


public class AllTicketsFragment extends Fragment {

    private RecyclerView spotsRecyclerView;
    private List<Ticket> tickets;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_all_tickets, container, false);

        spotsRecyclerView = root.findViewById(R.id.recycler_view_spots);
        tickets = new ArrayList<>();
        setUpList();

        spotsRecyclerView.setHasFixedSize(true);
        spotsRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        spotsRecyclerView.setAdapter(new ActiveTicketsAdapter(tickets, getContext(), getActivity()));

        return root;
    }

    private void setUpList() {
        tickets.add(new Ticket("Restaurant", "32, Restaurant Night Street", R.drawable.img_ticket, true));
        tickets.add(new Ticket("Night Club", "92, Main Street", R.drawable.img_ticket, true));
        tickets.add(new Ticket("Night With Sava", "12, Union Street", R.drawable.img_ticket, true));
        tickets.add(new Ticket("Something like club", "York mill", R.drawable.img_ticket, false));
        tickets.add(new Ticket("Yessssss", "877, Yes Street", R.drawable.img_ticket, false));
    }


}