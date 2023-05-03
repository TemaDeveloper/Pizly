package com_pizly.java_pizly.pizly.ui.friends;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.managers.SharedPrefManager;
import com_pizly.java_pizly.pizly.models.BannedUser;
import com_pizly.java_pizly.pizly.models.Person;
import com_pizly.java_pizly.pizly.rest.ApiClient;
import com_pizly.java_pizly.pizly.rest.ApiInterface;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FriendAdditionFragment extends BottomSheetDialogFragment {

    private Person person;
    private TextView nameFriendTextView, hobbyTextView, descriptionTextView;
    private CircleImageView imageFriend;
    private MaterialButton addFriendButton;
    private ApiInterface apiInterface;
    private int uid;


    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend_addition, container, false);

        uid = SharedPrefManager.getInstance(getContext()).getKeyID();
        nameFriendTextView = view.findViewById(R.id.text_view_name_friend);
        imageFriend = view.findViewById(R.id.image_view_friend);
        hobbyTextView = view.findViewById(R.id.text_view_hobby);
        addFriendButton = view.findViewById(R.id.button_add_friend);
        descriptionTextView = view.findViewById(R.id.text_view_description);

        apiInterface = ApiClient.getApiService();
        Call<Person> personCall = apiInterface.getUser(person.getId());
        Call<BannedUser> usersBannedCall = apiInterface.getBlockedUser(person.getId());
        personCall.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                hobbyTextView.setText(response.body().getHobby().trim());
                descriptionTextView.setText(response.body().getDescription().trim());
                Picasso.get().load(response.body().getImage()).into(imageFriend);
                nameFriendTextView.setText(response.body().getName().trim());

                usersBannedCall.enqueue(new Callback<BannedUser>() {
                    @Override
                    public void onResponse(Call<BannedUser> call, Response<BannedUser> response) {


                        if (response.body().getBlockedFromID() == person.getId() && response.body().getBlockToID() == uid) {
                            addFriendButton.setText("Followed");
                            addFriendButton.setEnabled(false);
                        }

                    }

                    @Override
                    public void onFailure(Call<BannedUser> call, Throwable t) {

                    }
                });

            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {

            }
        });


        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFriend();
            }
        });

        return view;
    }

    private void addFriend() {
        apiInterface = ApiClient.getApiService();
        Call<ResponseBody> friendAdditionCall = apiInterface.addFriend(uid, person.getId(), 1);
        friendAdditionCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    addFriendButton.setText("Followed");
                    addFriendButton.setEnabled(false);
                    Toast.makeText(getContext(), "Successfully added", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


}