package com_pizly.java_pizly.pizly.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.material.button.MaterialButton;

import com_pizly.java_pizly.pizly.MainActivity;
import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.animation.TextAnimator;
import com_pizly.java_pizly.pizly.animation.TypeTextWriterTextView;
import com_pizly.java_pizly.pizly.auth.login.LoginActivity;
import com_pizly.java_pizly.pizly.auth.signup.SignUpActivity;
import com_pizly.java_pizly.pizly.managers.SharedPrefManager;

public class SignupLoginActivity extends AppCompatActivity {

    private MaterialButton loginButton;
    private Button signUpButton;

    private RelativeLayout gradientLayout;

    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login);



        init();
        checkingNetworkConnection();

        sharedPrefManager = SharedPrefManager.getInstance(getApplicationContext());

        if(sharedPrefManager.getKeyID() != 0){
            startActivity(new Intent(SignupLoginActivity.this, MainActivity.class).putExtra("ID", sharedPrefManager.getKeyID()));
        }

        if(sharedPrefManager.getMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupLoginActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupLoginActivity.this, SignUpActivity.class));
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            }
        });
        animBackground();

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void checkingNetworkConnection(){
        if(!isNetworkAvailable()){
            final Dialog dialog = new Dialog(SignupLoginActivity.this, R.style.ThemeDialog);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dialog_internet_connection);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            MaterialButton btnExit = dialog.findViewById(R.id.btn_exit), btnSettings = dialog.findViewById(R.id.btn_settings);

            btnExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveTaskToBack(true);
                    finish();
                    System.exit(0);
                    dialog.cancel();
                }
            });

            btnSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Settings.ACTION_SETTINGS));
                }
            });

            dialog.show();
        }
    }

    private void init(){
        loginButton = findViewById(R.id.button_login);
        signUpButton = findViewById(R.id.sign_up_button);
        gradientLayout = findViewById(R.id.relative_layout_gradient);
    }

    private void animBackground(){
        AnimationDrawable animationDrawable = (AnimationDrawable) gradientLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
    }

}