package com_pizly.java_pizly.pizly.ui.yourParties;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com_pizly.java_pizly.pizly.MainActivity;
import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.managers.SharedPrefManager;
import com_pizly.java_pizly.pizly.rest.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateYourPartyActivity extends AppCompatActivity implements View.OnClickListener {

    //----widgets----//
    private EditText titleEditText, rulesEditText, additionalInfoEditText;
    private MaterialButton updatePartyButton;
    private ImageView imageParty, backImageView;
    private TextView imageChooserTextView;
    //----bitmap----//
    private Bitmap bitmap;
    //----request code----//
    private static final int PICK_IMAGE_REQUEST = 777;
    //----uri path for image----//
    private Uri path;
    //----ID for party updating----//
    private int idParty;
    private String imagePathPrevious, title, rules, additionalInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_your_party);

        init();
        setData();

        titleEditText.setText(title);
        rulesEditText.setText(rules);
        additionalInfoEditText.setText(additionalInfo);
        Picasso.get().load(imagePathPrevious).into(imageParty);

        imageChooserTextView.setOnClickListener(this);
        backImageView.setOnClickListener(this);
        updatePartyButton.setOnClickListener(this);

        animateTextAppearance();
    }

    private void animateTextAppearance(){
        AlphaAnimation animation1 = new AlphaAnimation(0f, 1.0f);
        animation1.setDuration(500);
        animation1.setStartOffset(500);
        animation1.setFillAfter(true);
        titleEditText.startAnimation(animation1);
        rulesEditText.startAnimation(animation1);
        additionalInfoEditText.startAnimation(animation1);
        imageChooserTextView.startAnimation(animation1);
        updatePartyButton.startAnimation(animation1);
    }

    //----get data----//
    private void setData(){
        idParty = getIntent().getIntExtra("ID", 0);
        title = getIntent().getStringExtra("title");
        rules = getIntent().getStringExtra("rules");
        additionalInfo = getIntent().getStringExtra("additional_info");
        imagePathPrevious = getIntent().getStringExtra("imagePath");
    }

    //----initialization----//
    private void init() {
        titleEditText = findViewById(R.id.edit_text_party_title);
        rulesEditText = findViewById(R.id.edit_text_rules);
        additionalInfoEditText = findViewById(R.id.edit_text_additional_info);
        updatePartyButton = findViewById(R.id.button_update_party);
        imageParty = findViewById(R.id.image_view_spot);
        imageChooserTextView = findViewById(R.id.text_view_change_image);
        backImageView = findViewById(R.id.image_view_back);
    }

    //----converting from Bitmap to String----//
    public String convertImageToString(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    //----selecting image from your gallery----//
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
                imageParty.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_update_party:
                BitmapDrawable drawable = (BitmapDrawable) imageParty.getDrawable();
                Bitmap profileBitmap = drawable.getBitmap();
                final String image = convertImageToString(profileBitmap);

                Call<ResponseBody> updatePartyCall = ApiClient.getApiService().updateYourParty(
                        idParty,
                        SharedPrefManager.getInstance(getApplicationContext()).getKeyID(),
                        titleEditText.getText().toString().trim(),
                        rulesEditText.getText().toString().trim(),
                        additionalInfoEditText.getText().toString().trim(),
                        image
                );

                updatePartyCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                break;
            case R.id.text_view_change_image:
                selectImage();
                break;
            case R.id.image_view_back:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                break;
        }
    }
}