package com_pizly.java_pizly.pizly.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.models.Friend;
import com_pizly.java_pizly.pizly.models.Person;
import com_pizly.java_pizly.pizly.rest.ApiClient;
import com_pizly.java_pizly.pizly.rest.ApiInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.FriendRequestViewHolder> {

    private List<Friend> friends;
    private Context context;

    public RequestAdapter(List<Friend> friends, Context context) {
        this.friends = friends;
        this.context = context;
    }

    @NonNull
    @Override
    public RequestAdapter.FriendRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request_friend, parent, false);
        return new FriendRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestAdapter.FriendRequestViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ApiInterface apiInterface = ApiClient.getApiService();

        Call<Person> personCall = apiInterface.getUser(friends.get(position).getUid());
        personCall.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                holder.friendName.setText(response.body().getName());
                Picasso.get().load(response.body().getImage()).into(holder.friendImage);
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {

            }
        });

        holder.followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<ResponseBody> friendRequestUpdateCall = apiInterface.updateFriendRequest(friends.get(position).getId());
                friendRequestUpdateCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        holder.followButton.setText("Followed");
                        holder.followButton.setEnabled(false);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class FriendRequestViewHolder extends RecyclerView.ViewHolder {
        private TextView friendName;
        private ImageView friendImage;
        private MaterialButton followButton;

        public FriendRequestViewHolder(@NonNull View itemView) {
            super(itemView);

            friendName = itemView.findViewById(R.id.text_view_name);
            friendImage = itemView.findViewById(R.id.image_view_person);
            followButton = itemView.findViewById(R.id.button_follow_back);

        }
    }
}
