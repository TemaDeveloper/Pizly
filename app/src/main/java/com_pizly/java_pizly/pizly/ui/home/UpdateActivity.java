package com_pizly.java_pizly.pizly.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fredporciuncula.phonemoji.PhonemojiTextInputEditText;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com_pizly.java_pizly.pizly.MainActivity;
import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.animation.TextAnimator;
import com_pizly.java_pizly.pizly.animation.TypeTextWriterTextView;
import com_pizly.java_pizly.pizly.auth.login.LoginActivity;
import com_pizly.java_pizly.pizly.managers.SharedPrefManager;
import com_pizly.java_pizly.pizly.models.Person;
import com_pizly.java_pizly.pizly.rest.ApiClient;
import com_pizly.java_pizly.pizly.rest.ApiInterface;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity implements TextAnimator, View.OnClickListener {

    private TypeTextWriterTextView titleEditProfile;
    private ImageView back, changingPasswordImageView;
    private EditText nameEditText, emailEditText, hobbyEditText, descriptionEditText;
    private PhonemojiTextInputEditText phoneEditText;
    private MaterialButton updateButton;
    private CircleImageView profileImageView;
    //bitmap
    private Bitmap bitmap;
    //request code
    private static final int PICK_IMAGE_REQUEST = 777;
    //uri path for image
    private Uri path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        init();
        getPreviousUserData();

        profileImageView.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        back.setOnClickListener(this);
        changingPasswordImageView.setOnClickListener(this);

        animText();
        animateTextAppearance();
    }

    private void init() {
        titleEditProfile = findViewById(R.id.text_view_title_edit_profile);
        back = findViewById(R.id.image_view_back);
        nameEditText = findViewById(R.id.edit_text_name);
        emailEditText = findViewById(R.id.edit_text_email);
        phoneEditText = findViewById(R.id.edit_text_phone_no);
        hobbyEditText = findViewById(R.id.edit_text_hobby);
        descriptionEditText = findViewById(R.id.edit_text_description);
        updateButton = findViewById(R.id.button_update);
        profileImageView = findViewById(R.id.image_view_profile);
        changingPasswordImageView = findViewById(R.id.image_view_changing_password);
    }

    private void animateTextAppearance(){
        AlphaAnimation animation1 = new AlphaAnimation(0f, 1.0f);
        animation1.setDuration(500);
        animation1.setStartOffset(500);
        animation1.setFillAfter(true);
        nameEditText.startAnimation(animation1);
        emailEditText.startAnimation(animation1);
        phoneEditText.startAnimation(animation1);
        hobbyEditText.startAnimation(animation1);
        descriptionEditText.startAnimation(animation1);
        updateButton.startAnimation(animation1);
    }

    @Override
    public void animText() {
        titleEditProfile.setText("");
        titleEditProfile.setCharacterDelay(120);
        titleEditProfile.displayTextWithAnimation("Edit Profile");
    }

    public String convertImageToString(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST && data != null) {
            //get path from media store
            path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                profileImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    private void update() {
        ApiInterface apiInterface = ApiClient.getApiService();

        BitmapDrawable drawable = (BitmapDrawable) profileImageView.getDrawable();
        Bitmap profileBitmap = drawable.getBitmap();
        final String image = convertImageToString(profileBitmap);

        Call<ResponseBody> userUpdateCall = apiInterface.updateUserData(
                SharedPrefManager.getInstance(UpdateActivity.this).getKeyID(),
                nameEditText.getText().toString().trim(),
                hobbyEditText.getText().toString().trim(),
                descriptionEditText.getText().toString().trim(),
                phoneEditText.getText().toString().trim(),
                emailEditText.getText().toString().trim(),
                image
        );
        userUpdateCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    startActivity(new Intent(UpdateActivity.this, MainActivity.class));
                    overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }

    private void getPreviousUserData() {
        //get intent data from HomeFragment
        nameEditText.setText(getIntent().getStringExtra("name"));
        emailEditText.setText(getIntent().getStringExtra("email"));
        hobbyEditText.setText(getIntent().getStringExtra("hobby"));
        descriptionEditText.setText(getIntent().getStringExtra("description"));
        phoneEditText.setText(getIntent().getStringExtra("phone"));
        //Glide.with(UpdateActivity.this).load(getIntent().getStringExtra("imagePath")).into(profileImageView);
        Picasso.get().load(Uri.parse(getIntent().getStringExtra("imagePath"))).into(profileImageView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_update:
                update();
                break;
            case R.id.image_view_back:
                startActivity(new Intent(UpdateActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                break;
            case R.id.image_view_profile:
                selectImage();
                break;
            case R.id.image_view_changing_password:
                BottomSheetChangePasswordFragment bottomSheetChangePasswordFragment = new BottomSheetChangePasswordFragment(emailEditText.getText().toString().trim());
                bottomSheetChangePasswordFragment.show(getSupportFragmentManager(), bottomSheetChangePasswordFragment.getTag());
                break;
        }
    }
}