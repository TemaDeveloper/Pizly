package com_pizly.java_pizly.pizly.ui.eventAddition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;

import com_pizly.java_pizly.pizly.MainActivity;
import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.models.Party;


public class AddtitionActivity extends AppCompatActivity {

    public static Party party = new Party();

    private TextView addImageTextView;
    private ImageView back, backgroundImageView, gradientBackgroundImageView;
    //bitmap
    private Bitmap bitmap;
    //request code
    private static final int PICK_IMAGE_REQUEST = 777;
    //uri path for image
    private Uri path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);

        init();

        addImageTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddtitionActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AddtitionActivity.this, MainActivity.class));
    }

    private void init() {
        back = findViewById(R.id.image_view_back);
        backgroundImageView = findViewById(R.id.image_view_map);
        addImageTextView = findViewById(R.id.text_view_add_image);
        gradientBackgroundImageView = findViewById(R.id.image_view_gradient_background);
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
                backgroundImageView.setImageBitmap(bitmap);
                party.setImageParty(convertImageToString(bitmap));
                gradientBackgroundImageView.setVisibility(View.GONE);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

}

