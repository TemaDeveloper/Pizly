package com_pizly.java_pizly.pizly.auth.signup;

import static com_pizly.java_pizly.pizly.auth.signup.SignUpActivity.person;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import com_pizly.java_pizly.pizly.MainActivity;
import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.auth.SlideChanger;
import com_pizly.java_pizly.pizly.auth.login.LoginActivity;
import com_pizly.java_pizly.pizly.managers.SharedPrefManager;
import com_pizly.java_pizly.pizly.models.Person;
import com_pizly.java_pizly.pizly.rest.ApiClient;
import com_pizly.java_pizly.pizly.rest.ApiInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpThirdSlideFragment extends Fragment implements SlideChanger {

    private MaterialButton previous3Button, finishButton;
    private TextInputEditText hobbyEditText, descriptionEditText;
    private boolean isPrevious = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_third_slide, container, false);

        init(view);


        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hobbyEditText.getText().toString().isEmpty() || descriptionEditText.getText().toString().isEmpty()){
                    Snackbar.make(view, "All of the fields must be filled", Snackbar.LENGTH_SHORT)
                            .setTextColor(getResources().getColor(R.color.white))
                            .setBackgroundTint(getResources().getColor(R.color.red))
                            .show();
                }else{
                    goToSlide();
                }
            }
        });

        previous3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPrevious();
            }
        });

        if(getInfo() == true){
            hobbyEditText.setText(person.getHobby());
            descriptionEditText.setText(person.getDescription());
        }

        return view;
    }

    public Boolean getInfo(){
        if(person.getHobby() == null || person.getDescription() == null)
        {
            isPrevious = false;
        }
        else
        {
            isPrevious = true;
        }

        return isPrevious;
    }


    private void init(View view){
        previous3Button = view.findViewById(R.id.button_previous_3);
        finishButton = view.findViewById(R.id.button_finish_slide_3);
        hobbyEditText = view.findViewById(R.id.edit_text_hobby);
        descriptionEditText = view.findViewById(R.id.edit_text_description);
    }

    @Override
    public void goToSlide() {

        String name, email, password, phone, image, hobby, description;
        int age;

        person.setHobby(hobbyEditText.getText().toString());
        person.setDescription(descriptionEditText.getText().toString());

        email = person.getEmail();
        hobby = person.getHobby();
        description = person.getDescription();
        name = person.getName();
        password = person.getPassword();
        phone = person.getPhoneNo();
        age = person.getAge();
        image = "https://pizly.000webhostapp.com/pizly_db/pizly_user_images/account.png";

        ApiInterface apiInterface = ApiClient.getApiService();

        Call<ResponseBody> callSignUp = apiInterface.signup(
                name,
                age,
                hobby,
                description,
                phone,
                email,
                password,
                image
        );

        callSignUp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext(), "SuccessFully signup " + response.toString().trim(), Toast.LENGTH_LONG).show();
                    Log.d("SIGN_UP", response.body().toString());
                    Call<Person> userIDCall = apiInterface.getUserID(email);
                    userIDCall.enqueue(new Callback<Person>() {
                        @Override
                        public void onResponse(Call<Person> call, Response<Person> response) {
                            SharedPrefManager.getInstance(getContext()).setKeyID(response.body().getId());
                            startActivity(new Intent(getContext(), MainActivity.class));
                            getActivity().finish();
                        }

                        @Override
                        public void onFailure(Call<Person> call, Throwable t) {

                        }

                    });

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("FAILED_SIGN_UP", t.getMessage());
            }
        });


    }

    @Override
    public void goToPrevious() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.fooFragment, new SignUpSecondSlideFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}