package com_pizly.java_pizly.pizly.auth.forget_password;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;

import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.animation.TextAnimator;
import com_pizly.java_pizly.pizly.animation.TypeTextWriterTextView;

public class ForgotPasswordActivity extends AppCompatActivity implements TextAnimator {

    private TypeTextWriterTextView titleForgotPassword;
    private TextInputEditText emailEditText;
    private ImageView backImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        titleForgotPassword = findViewById(R.id.text_view_title_forgotPpassword);
        emailEditText = findViewById(R.id.edit_text_email);
        backImageView = findViewById(R.id.image_view_back);

        animText();

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
            }
        });

    }

    private void forgotPassword(){

    }

    @Override
    public void animText() {
        titleForgotPassword.setText("");
        titleForgotPassword.setCharacterDelay(120);
        titleForgotPassword.displayTextWithAnimation("Forgot Password?");
    }
}