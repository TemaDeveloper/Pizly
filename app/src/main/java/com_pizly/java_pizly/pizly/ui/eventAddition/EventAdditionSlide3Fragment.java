package com_pizly.java_pizly.pizly.ui.eventAddition;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.animation.TextAnimator;
import com_pizly.java_pizly.pizly.animation.TypeTextWriterTextView;
import com_pizly.java_pizly.pizly.auth.SlideChanger;
import com_pizly.java_pizly.pizly.ui.home.AdditionFriendActivity;


public class EventAdditionSlide3Fragment extends Fragment implements SlideChanger, TextAnimator {

    private TypeTextWriterTextView title3Addition;
    private FloatingActionButton buttonNextPartyAddition;
    private Button previousButton;
    private TextInputEditText additionalInfoEditText;
    private boolean isPrevious = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_addition_slide3, container, false);

        init(view);
        animText();

        buttonNextPartyAddition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(additionalInfoEditText.getText().toString().trim().isEmpty()){
                    Snackbar.make(view, "All of the fields must be filled", Snackbar.LENGTH_SHORT)
                            .setTextColor(getResources().getColor(R.color.white))
                            .setBackgroundTint(getResources().getColor(R.color.red))
                            .show();
                }else{
                    goToSlide();
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPrevious();
            }
        });

        if(getInfo() == true){
            additionalInfoEditText.setText(AddtitionActivity.party.getAdditional_info());
        }

        return view;
    }

    private void init(View view){
        title3Addition = view.findViewById(R.id.text_view_title_step_3_addition);
        previousButton = view.findViewById(R.id.button_previous_3);
        buttonNextPartyAddition = view.findViewById(R.id.button_next_slide_3_party_addition);
        additionalInfoEditText = view.findViewById(R.id.edit_text_additional_info);
    }

    @Override
    public void animText() {
        title3Addition.setText("");
        title3Addition.setCharacterDelay(120);
        title3Addition.displayTextWithAnimation("Additional Info");
    }

    @Override
    public void goToSlide() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.eventFragment, new EventPriceAdditionSlide4Fragment());
        transaction.addToBackStack(null);

        AddtitionActivity.party.setAdditional_info(additionalInfoEditText.getText().toString().trim());

        transaction.commit();
    }

    @Override
    public void goToPrevious() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.eventFragment, new EventAdditionSlide2Fragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public Boolean getInfo() {
        if(AddtitionActivity.party.getAdditional_info() == null){
            isPrevious = false;
        }else{
            isPrevious = true;
        }
        return isPrevious;
    }
}