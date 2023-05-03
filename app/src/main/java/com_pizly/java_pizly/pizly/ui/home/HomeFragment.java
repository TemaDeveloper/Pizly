package com_pizly.java_pizly.pizly.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.managers.SharedPrefManager;
import com_pizly.java_pizly.pizly.models.Friend;
import com_pizly.java_pizly.pizly.models.Person;
import com_pizly.java_pizly.pizly.rest.ApiClient;
import com_pizly.java_pizly.pizly.rest.ApiInterface;
import com_pizly.java_pizly.pizly.ui.friends.BottomSheetFriendsFragment;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener {

    //----widgets----//
    private Button addFriendButton, editButton;
    private TextView emailTextView, phoneTextView, hobbyTextView, descriptionTextView, friendsTextView, friendsCounterTextView;
    private ShimmerFrameLayout shimmerFrameLayout;
    private CircleImageView profileImageView;
    //----fields of personal information----//
    private String imageActualPath, name, email, phoneNo, hobby, description;
    private ApiInterface apiInterface;
    private int id, friendsCounter = 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_home, container, false);

        init(root);
        shimmerFrameLayout.startShimmer();

        //----get your personal user id----//
        id = SharedPrefManager.getInstance(getContext()).getKeyID();

        apiInterface = ApiClient.getApiService();
        Call<Person> callUserGetter = apiInterface.getUser(id);
        callUserGetter.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if (response.isSuccessful()) {
                    //----get fields from database----//
                    shimmerFrameLayout.setVisibility(View.GONE);

                    profileImageView.setVisibility(View.VISIBLE);

                    imageActualPath = response.body().getImage();
                    name = response.body().getName();
                    email = response.body().getEmail();
                    phoneNo = response.body().getPhoneNo();
                    hobby = response.body().getHobby();
                    description = response.body().getDescription();

                    //set text with these fields
                    emailTextView.setText(email);
                    phoneTextView.setText(phoneNo);
                    descriptionTextView.setText(description);
                    hobbyTextView.setText(hobby);
                    //Glide.with(getContext()).load(imageActualPath).into(profileImageView);
                    Picasso.get().load(Uri.parse(imageActualPath)).into(profileImageView);
                }
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {

            }
        });

        getFriendsCount();


        editButton.setOnClickListener(this);
        addFriendButton.setOnClickListener(this);
        friendsTextView.setOnClickListener(this);

        return root;
    }

    //----initialization of widgets----//
    private void init(View root) {
        addFriendButton = root.findViewById(R.id.button_add_friend);
        editButton = root.findViewById(R.id.button_edit);
        emailTextView = root.findViewById(R.id.text_view_email);
        phoneTextView = root.findViewById(R.id.text_view_phone);
        hobbyTextView = root.findViewById(R.id.text_view_hobby);
        descriptionTextView = root.findViewById(R.id.text_view_about);
        profileImageView = root.findViewById(R.id.image_view_profile);
        friendsTextView = root.findViewById(R.id.text_view_friends);
        friendsCounterTextView = root.findViewById(R.id.friends_counter_text_view);
        shimmerFrameLayout = root.findViewById(R.id.shimmer);

    }

    private void getFriendsCount() {

        apiInterface = ApiClient.getApiService();
        Call<List<Friend>> friendsCall = apiInterface.getFriends(id, id);
        friendsCall.enqueue(new Callback<List<Friend>>() {
            @Override
            public void onResponse(Call<List<Friend>> call, Response<List<Friend>> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().size(); i++) {
                        //get the list size of all friends
                        friendsCounter = response.body().size();
                    }
                    friendsCounterTextView.setText(String.valueOf(friendsCounter));
                }
            }

            @Override
            public void onFailure(Call<List<Friend>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_add_friend:
                startActivity(new Intent(getContext(), AdditionFriendActivity.class));
                break;
            case R.id.button_edit:

                Intent intent = new Intent(getContext(), UpdateActivity.class);

                //image smooth transaction
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(getActivity(), (View) profileImageView, "user_image");

                //put some data to intent
                intent.putExtra("name", name)
                        .putExtra("email", email)
                        .putExtra("hobby", hobby)
                        .putExtra("phone", phoneNo)
                        .putExtra("description", description)
                        .putExtra("imagePath", imageActualPath);

                startActivity(intent, options.toBundle());

                break;
            case R.id.text_view_friends:
                //connect bottom view dialog to fragment
                BottomSheetFriendsFragment friendsFragment = new BottomSheetFriendsFragment();
                friendsFragment.show(getFragmentManager(), friendsFragment.getTag());
                break;
        }
    }

}