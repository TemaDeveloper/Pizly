package com_pizly.java_pizly.pizly.ui.friends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import com_pizly.java_pizly.pizly.MainActivity;
import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.animation.TextAnimator;
import com_pizly.java_pizly.pizly.animation.TypeTextWriterTextView;
import com_pizly.java_pizly.pizly.managers.SharedPrefManager;
import com_pizly.java_pizly.pizly.models.Person;
import com_pizly.java_pizly.pizly.rest.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendDataActivity extends AppCompatActivity implements TextAnimator {

    //----widgets----//
    private TypeTextWriterTextView name;
    private TextView hobby, description;
    private ImageView imageFriend, back;
    private MaterialButton blockButton, frezerButton;
    //----IDs----//
    private int blockToID = 0, friendID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_data);

        //----get intents----//

        friendID = getIntent().getIntExtra("ID", 0);
        blockToID = getIntent().getIntExtra("blockToID", 0);

        init();

        if(blockToID > 0){
            blockButton.setText("Unblock");
            frezerButton.setVisibility(View.GONE);
        }else{
            blockButton.setText("Block");
            frezerButton.setVisibility(View.VISIBLE);
        }

        blockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(blockToID > 0){
                    unblockUser(view);
                }else{
                    blockUser();
                }

            }
        });

        //----get data about the person from a list----//
        Call<Person> userCall = ApiClient.getApiService().getUser(friendID);
        userCall.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                //animate text
                animText();
                name.displayTextWithAnimation(response.body().getName());
                //set textViews
                hobby.setText(response.body().getHobby());
                description.setText(response.body().getDescription());
                //set the image using picasso
                Picasso.get().load(response.body().getImage()).into(imageFriend);
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {

            }
        });

        //----go back method----//
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    //----initialization of widgets----//

    private void init(){
        name = findViewById(R.id.text_view_name_friend);
        hobby = findViewById(R.id.text_view_hobby);
        description = findViewById(R.id.text_view_description);
        imageFriend = findViewById(R.id.image_view_ticket);
        back = findViewById(R.id.image_view_back);
        blockButton = findViewById(R.id.button_ban_friend);
        frezerButton = findViewById(R.id.button_freeze_friend);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //if I would keep only onBackPressed() => the bottomDialog will be viewed
        startActivity(new Intent(FriendDataActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
        finish();
    }

    @Override
    public void animText() {
        name.setText("");
        name.setCharacterDelay(120);

    }

    private void blockUser(){
        Call<ResponseBody> banCall = ApiClient.getApiService().blockUser(
                SharedPrefManager.getInstance(getApplicationContext()).getKeyID(),
                friendID);


        Call<ResponseBody> friendRemovingCall = ApiClient.getApiService().removeFriend(getIntent().getIntExtra("rowID", 0));

        friendRemovingCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(FriendDataActivity.this, "The friend was successfully removed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


        banCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    blockButton.setText("Unblock");
                    frezerButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });



    }

    private void unblockUser(View view){
        Call<ResponseBody> unBlockFriendCall = ApiClient.getApiService().addFriend(
                SharedPrefManager.getInstance(getApplicationContext()).getKeyID(),
                blockToID,
                0
        );

        unBlockFriendCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Snackbar.make(view, "The user was unblocked", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        Call<ResponseBody> blockRemovingCall = ApiClient.getApiService().removeBlockedFriend(getIntent().getIntExtra("rowBlockedID", 0));
        blockRemovingCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    blockButton.setText("Block");
                    frezerButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

}