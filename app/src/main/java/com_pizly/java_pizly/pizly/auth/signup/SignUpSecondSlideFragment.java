package com_pizly.java_pizly.pizly.auth.signup;

import static com_pizly.java_pizly.pizly.auth.signup.SignUpActivity.person;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.auth.SlideChanger;
import com_pizly.java_pizly.pizly.models.Person;


public class SignUpSecondSlideFragment extends Fragment implements SlideChanger {

    private MaterialButton nextSlide2Button, previous2Button;
    private FragmentTransaction transaction;
    private TextInputEditText nickname;
    private boolean isPrevious = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_second_slide, container, false);
        transaction = getFragmentManager().beginTransaction();
        init(view);

        nextSlide2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nickname.getText().toString().trim().isEmpty()){
                    Snackbar.make(view, "Nickname has to be filled", Snackbar.LENGTH_SHORT)
                            .setTextColor(getResources().getColor(R.color.white))
                            .setBackgroundTint(getResources().getColor(R.color.red))
                            .show();
                }else{
                    goToSlide();
                }
            }
        });

        previous2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPrevious();
            }
        });

        if(getInfo() == true){
            nickname.setText(person.getName());
        }

        return view;
    }

    public Boolean getInfo(){
        if(person.getName() == null)
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
        nextSlide2Button = view.findViewById(R.id.button_next_slide_2);
        previous2Button = view.findViewById(R.id.button_previous_2);
        nickname = view.findViewById(R.id.edit_text_nickname);
    }

    @Override
    public void goToSlide() {
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fooFragment, new SignUpThirdSlideFragment());
        transaction.addToBackStack(null);

        person.setName(nickname.getText().toString().trim());
        person.setAge(12);

        transaction.commit();
    }

    @Override
    public void goToPrevious() {
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.fooFragment, new SignUpFirstSlideFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

}