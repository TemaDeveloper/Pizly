package com_pizly.java_pizly.pizly.adapters;

import android.annotation.SuppressLint;
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

import com.squareup.picasso.Picasso;

import java.util.List;

import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.models.BannedUser;
import com_pizly.java_pizly.pizly.models.Person;
import com_pizly.java_pizly.pizly.rest.ApiClient;
import com_pizly.java_pizly.pizly.ui.friends.FriendDataActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlockedUsersAdapter extends RecyclerView.Adapter<BlockedUsersAdapter.BlockedUserViewHolder> {

    private List<BannedUser> users;
    private Context context;
    private Activity activity;

    public BlockedUsersAdapter(List<BannedUser> users, Context context, Activity activity) {
        this.users = users;
        this.activity = activity;
        this.context = context;
    }

    @NonNull
    @Override
    public BlockedUsersAdapter.BlockedUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person, parent, false);
        return new BlockedUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlockedUsersAdapter.BlockedUserViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Call<Person> personCall = ApiClient.getApiService().getUser(users.get(position).getBlockToID());
        personCall.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                holder.nameText.setText(response.body().getName());
                Picasso.get().load(response.body().getImage()).into(holder.personImage);
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, FriendDataActivity.class);

                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(activity, (View) holder.personImage, "friend_image");

                intent.putExtra("blockToID", users.get(position).getBlockToID())
                        .putExtra("ID", users.get(position).getBlockToID())
                        .putExtra("rowBlockedID", users.get(position).getId());

                context.startActivity(intent, options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class BlockedUserViewHolder extends RecyclerView.ViewHolder {

        private TextView nameText;
        private ImageView personImage;
        public BlockedUserViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.text_view_name);
            personImage = itemView.findViewById(R.id.image_view_person);

        }
    }
}
