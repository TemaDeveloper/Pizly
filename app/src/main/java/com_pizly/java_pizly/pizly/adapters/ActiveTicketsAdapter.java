package com_pizly.java_pizly.pizly.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.models.Ticket;
import com_pizly.java_pizly.pizly.ui.activeTckets.TicketDataActivity;
import com_pizly.java_pizly.pizly.ui.yourParties.UpdateYourPartyActivity;

public class ActiveTicketsAdapter extends RecyclerView.Adapter<ActiveTicketsAdapter.ViewHolder> {

    private List<Ticket> tickets;
    private Context context;
    private Activity activity;


    public ActiveTicketsAdapter(List<Ticket> tickets, Context ctx, Activity activity) {
        this.context = ctx;
        this.tickets = tickets;
        this.activity = activity;
    }

    //return layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ticket, parent, false);
        return new ViewHolder(view);
    }

    //bind the screen with the view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.image.setImageResource(tickets.get(position).getImage());
        holder.title.setText(tickets.get(position).getTitle());
        holder.address.setText(tickets.get(position).getQuickDescription());

        Boolean enabling;

        if(tickets.get(position).isActive()){
            holder.gradient.setVisibility(View.VISIBLE);
            holder.title.setTextColor(context.getResources().getColor(R.color.black));
            holder.address.setTextColor(context.getResources().getColor(R.color.black));
            enabling = true;
        }else{
            holder.gradient.setVisibility(View.GONE);
            holder.title.setTextColor(context.getResources().getColor(android.R.color.darker_gray));
            holder.address.setTextColor(context.getResources().getColor(android.R.color.darker_gray));
            holder.cardViewBackground.setCardBackgroundColor(context.getResources().getColor(R.color.dark_blue));
            enabling = false;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, TicketDataActivity.class);

                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(activity, (View) holder.image, "ticket_image");

                intent.putExtra("enabling", enabling);

                context.startActivity(intent, options.toBundle());
            }
        });

    }


    //return the size of the List
    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image, gradient;
        private TextView title, address;
        private MaterialCardView cardViewBackground;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_view_spot);
            title = itemView.findViewById(R.id.text_view_title);
            address = itemView.findViewById(R.id.text_view_address);
            cardViewBackground = itemView.findViewById(R.id.card_view_ticket);
            gradient = itemView.findViewById(R.id.image_view_gradient);

        }
    }
}
