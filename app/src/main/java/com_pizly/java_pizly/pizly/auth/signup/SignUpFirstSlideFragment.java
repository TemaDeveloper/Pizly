package com_pizly.java_pizly.pizly.auth.signup;

import static com_pizly.java_pizly.pizly.auth.signup.SignUpActivity.person;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.fredporciuncula.phonemoji.PhonemojiTextInputEditText;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.animation.TextAnimator;
import com_pizly.java_pizly.pizly.animation.TypeTextWriterTextView;
import com_pizly.java_pizly.pizly.auth.SlideChanger;
import com_pizly.java_pizly.pizly.auth.login.LoginActivity;
import com_pizly.java_pizly.pizly.models.Person;


public class SignUpFirstSlideFragment extends Fragment implements SlideChanger, TextAnimator {

    //----widgets----//
    private MaterialButton nextSlide1Button;
    private TypeTextWriterTextView titleAnimation;
    private Button loginButton;
    private PhonemojiTextInputEditText phoneNoEditText;
    private TextInputEditText emailEditText, passwordEditText;
    private boolean isPrevious = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_first_slide, container, false);

        init(view);
        animText();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
            }
        });

        nextSlide1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phoneNoEditText.getText().toString().trim().isEmpty() ||
                        emailEditText.getText().toString().trim().isEmpty() ||
                        passwordEditText.getText().toString().trim().isEmpty()){

                    Snackbar.make(view, "All of the fields must be filled", Snackbar.LENGTH_SHORT)
                            .setTextColor(getResources().getColor(R.color.white))
                            .setBackgroundTint(getResources().getColor(R.color.red))
                            .show();

                }else{
                    goToSlide();
                }
            }
        });

        //fill some fields with information gotten from object
        if(getInfo() == true){
            phoneNoEditText.setText(person.getPhoneNo());
            emailEditText.setText(person.getEmail());
            passwordEditText.setText(person.getPassword());
        }

        return view;
    }

    public Boolean getInfo(){
        if(person.getEmail() == null ||
                person.getPassword() == null ||
                person.getPhoneNo() == null)
        {
            isPrevious = false;
        }
        else
        {
            isPrevious = true;
        }

        return isPrevious;
    }

    //----initialization of widgets----//
    private void init(View view) {
        nextSlide1Button = view.findViewById(R.id.button_next_slide_1);
        titleAnimation = view.findViewById(R.id.signup_title_text);
        loginButton = view.findViewById(R.id.button_login);
        phoneNoEditText = view.findViewById(R.id.edit_text_phone);
        emailEditText = view.findViewById(R.id.edit_text_email);
        passwordEditText = view.findViewById(R.id.edit_text_password);
    }

    @Override
    public void goToSlide() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fooFragment, new SignUpSecondSlideFragment());
        transaction.addToBackStack(null);

        person.setEmail(emailEditText.getText().toString().trim());
        person.setPhoneNo(phoneNoEditText.getText().toString().trim());
        person.setPassword(passwordEditText.getText().toString().trim());

        transaction.commit();
    }

    @Override
    public void goToPrevious() {

    }

    //----animate the text----//
    @Override
    public void animText() {
        titleAnimation.setText("");
        titleAnimation.setCharacterDelay(120);
        titleAnimation.displayTextWithAnimation("Create Account");
    }
}