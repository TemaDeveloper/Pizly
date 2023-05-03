package com_pizly.java_pizly.pizly.ui.activeTckets;


import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import com_pizly.java_pizly.pizly.R;

public class TicketDataActivity extends AppCompatActivity {

    private static final String TAG = "QRActivity";
    //QR code generation
    private ImageView imageQR, gradientBackground, back;
    private MaterialCardView ticketQRCodeCardView;
    private TextView QRCodeTextView;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_data);

        imageQR = findViewById(R.id.imageQR);
        QRCodeTextView = findViewById(R.id.text_view_qr_code);
        ticketQRCodeCardView = findViewById(R.id.card_ticket_qr_code);
        gradientBackground = findViewById(R.id.image_view_gradient);
        back = findViewById(R.id.image_view_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if(getIntent().getBooleanExtra("enabling", false) == false){
            ticketQRCodeCardView.setVisibility(View.GONE);
            QRCodeTextView.setVisibility(View.GONE);
            gradientBackground.setImageResource(R.drawable.gradient_background_signup_3);
        }else{
            ticketQRCodeCardView.setVisibility(View.VISIBLE);
            QRCodeTextView.setVisibility(View.VISIBLE);
            gradientBackground.setImageResource(R.drawable.gradient_background_signup_2);
        }

        generateQRCode();
    }

    private void generateQRCode() {
        QRGEncoder qrgEncoder = new QRGEncoder("Artemii", QRGContents.Type.TEXT, 400);

        try {
            qrgEncoder.setColorBlack(Color.TRANSPARENT);
            qrgEncoder.setColorWhite(Color.BLACK);
            bitmap = qrgEncoder.getBitmap();
            imageQR.setImageBitmap(bitmap);
        } catch (Exception e) {
            Log.v(TAG, e.toString());
        }
    }

}