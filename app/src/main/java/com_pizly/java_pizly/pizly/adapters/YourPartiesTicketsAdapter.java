package com_pizly.java_pizly.pizly.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.models.Party;
import com_pizly.java_pizly.pizly.models.Ticket;
import com_pizly.java_pizly.pizly.ui.yourParties.UpdateYourPartyActivity;

public class YourPartiesTicketsAdapter extends RecyclerView.Adapter<YourPartiesTicketsAdapter.ViewHolder> {

    private List<Party> parties;
    private Context context;
    private Activity activity;


    public YourPartiesTicketsAdapter(List<Party> parties, Context ctx, Activity activity) {
        this.context = ctx;
        this.parties = parties;
        this.activity = activity;
    }

    //return layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_your_party_ticket, parent, false);
        return new ViewHolder(view);
    }

    //bind the screen with the view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(Uri.parse(parties.get(position).getImageParty())).into(holder.image);
        holder.title.setText(parties.get(position).getTitle());
        holder.price.setText(parties.get(position).getPricing() + " EU");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateYourPartyActivity.class);

                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(activity, (View) holder.image, "party_image");


                intent.putExtra("title", parties.get(position).getTitle())
                        .putExtra("rules", parties.get(position).getRules())
                        .putExtra("additional_info", parties.get(position).getAdditional_info())
                        .putExtra("imagePath", parties.get(position).getImageParty())
                        .putExtra("ID", parties.get(position).getId());

                context.startActivity(intent, options.toBundle());
            }
        });
    }


    //return the size of the List
    @Override
    public int getItemCount() {
        return parties.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView title,  price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_view_spot);
            title = itemView.findViewById(R.id.text_view_title);
            price = itemView.findViewById(R.id.text_view_your_party_cost);
        }
    }
}
