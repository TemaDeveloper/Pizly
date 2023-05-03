package com_pizly.java_pizly.pizly.auth.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.models.Person;

public class SignUpActivity extends AppCompatActivity {

    public static Person person = new Person();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.blue));
        }
        setContentView(R.layout.activity_sign_up);




    }

}