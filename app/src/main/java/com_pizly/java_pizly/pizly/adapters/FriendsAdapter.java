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
import com_pizly.java_pizly.pizly.managers.SharedPrefManager;
import com_pizly.java_pizly.pizly.models.Friend;
import com_pizly.java_pizly.pizly.models.ItemSearcher;
import com_pizly.java_pizly.pizly.models.Person;
import com_pizly.java_pizly.pizly.rest.ApiClient;
import com_pizly.java_pizly.pizly.rest.ApiInterface;
import com_pizly.java_pizly.pizly.ui.friends.FriendDataActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.PeopleViewHolder> {

    private List<Friend> friends;
    private Context context;
    private Activity activity;

    public FriendsAdapter(List<Friend> friends, Context context, Activity activity) {
        this.friends = friends;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public FriendsAdapter.PeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person, parent, false);
        return new PeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsAdapter.PeopleViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ApiInterface apiInterface = ApiClient.getApiService();
        if(SharedPrefManager.getInstance(context).getKeyID() == friends.get(position).getUid()){
            Call<Person> personCall = apiInterface.getUser(friends.get(position).getFriendID());
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

                    intent.putExtra("ID", friends.get(position).getFriendID())
                            .putExtra("rowID", friends.get(position).getId());

                    context.startActivity(intent, options.toBundle());
                }
            });
        }else{
            Call<Person> personCall = apiInterface.getUser(friends.get(position).getUid());
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

                    intent.putExtra("ID", friends.get(position).getUid())
                            .putExtra("rowID", friends.get(position).getId());

                    context.startActivity(intent, options.toBundle());
                }
            });
        }
    }



    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class PeopleViewHolder extends RecyclerView.ViewHolder {

        private TextView nameText;
        private ImageView personImage;

        public PeopleViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.text_view_name);
            personImage = itemView.findViewById(R.id.image_view_person);

        }
    }
}
