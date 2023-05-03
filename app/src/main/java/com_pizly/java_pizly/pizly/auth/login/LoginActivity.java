package com_pizly.java_pizly.pizly.auth.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com_pizly.java_pizly.pizly.MainActivity;
import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.animation.TextAnimator;
import com_pizly.java_pizly.pizly.animation.TypeTextWriterTextView;
import com_pizly.java_pizly.pizly.auth.SignupLoginActivity;
import com_pizly.java_pizly.pizly.auth.forget_password.ForgotPasswordActivity;
import com_pizly.java_pizly.pizly.managers.SharedPrefManager;
import com_pizly.java_pizly.pizly.models.Person;
import com_pizly.java_pizly.pizly.rest.ApiClient;
import com_pizly.java_pizly.pizly.rest.ApiInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements TextAnimator, View.OnClickListener {

    private TypeTextWriterTextView titleText;
    private ImageView back;
    private MaterialButton loginButton;
    private TextInputEditText emailEditText, passwordEditText;
    private TextView forgetPasswordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.dark_green));
        }
        setContentView(R.layout.activity_login);

        init();
        animText();

        forgetPasswordTextView.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void init(){
        back = findViewById(R.id.image_view_back);
        loginButton = findViewById(R.id.button_login);
        emailEditText = findViewById(R.id.edit_text_email);
        passwordEditText = findViewById(R.id.edit_text_password);
        titleText = findViewById(R.id.text_view_title_login);
        forgetPasswordTextView = findViewById(R.id.text_view_forgot_password);
    }

    private void login(View view){
        String email = emailEditText.getText().toString().trim();
        String url = "https://pizly.000webhostapp.com/pizly_db/login.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Snackbar.make(view, response.trim(), Snackbar.LENGTH_SHORT)
                        .setTextColor(getResources().getColor(R.color.black))
                        .setBackgroundTint(getResources().getColor(R.color.white))
                        .show();

                if(response.trim().equals("Login is successful")){

                    ApiInterface apiInterface = ApiClient.getApiService();
                    Call<Person> userIDCall = apiInterface.getUserID(email);
                    userIDCall.enqueue(new Callback<Person>() {
                        @Override
                        public void onResponse(Call<Person> call, Response<Person> response) {
                            SharedPrefManager.getInstance(LoginActivity.this).setKeyID(response.body().getId());
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }

                        @Override
                        public void onFailure(Call<Person> call, Throwable t) {

                        }

                    });

                }else{
                    Snackbar.make(view, "Check email or password", Snackbar.LENGTH_SHORT)
                            .setTextColor(getResources().getColor(R.color.white))
                            .setBackgroundTint(getResources().getColor(R.color.red))
                            .show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(view, "Weak internet connection", Snackbar.LENGTH_SHORT)
                        .setTextColor(getResources().getColor(R.color.white))
                        .setBackgroundTint(getResources().getColor(R.color.red))
                        .show();
            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", passwordEditText.getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(request);
    }

    @Override
    public void animText() {
        titleText.setText("");
        titleText.setCharacterDelay(120);
        titleText.displayTextWithAnimation("Login");
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_login:
                if(emailEditText.getText().toString().trim().isEmpty() && passwordEditText.getText().toString().trim().isEmpty()){
                    Snackbar.make(view, "Every Field has to be filled", Snackbar.LENGTH_SHORT)
                            .setTextColor(getResources().getColor(R.color.white))
                            .setBackgroundTint(getResources().getColor(R.color.red))
                            .show();
                }else{
                    login(view);
                }
                break;
            case R.id.text_view_forgot_password:
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
            case R.id.image_view_back:
                startActivity(new Intent(getApplicationContext(), SignupLoginActivity.class));
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                break;

        }
    }

}