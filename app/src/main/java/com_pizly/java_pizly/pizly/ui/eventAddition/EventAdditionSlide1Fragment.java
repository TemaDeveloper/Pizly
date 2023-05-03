package com_pizly.java_pizly.pizly.ui.eventAddition;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.animation.TextAnimator;
import com_pizly.java_pizly.pizly.animation.TypeTextWriterTextView;
import com_pizly.java_pizly.pizly.auth.SlideChanger;
import com_pizly.java_pizly.pizly.auth.signup.SignUpSecondSlideFragment;
import com_pizly.java_pizly.pizly.ui.home.AdditionFriendActivity;


public class EventAdditionSlide1Fragment extends Fragment implements SlideChanger, TextAnimator {

    private TypeTextWriterTextView step1Title;
    private FloatingActionButton buttonNextPartyAddition;

    private TextInputEditText titleEditText, addressEditText;
    private DatePicker datePicker;

    private boolean isPrevious = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_addition_slide1, container, false);

        init(view);

        animText();

        buttonNextPartyAddition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(titleEditText.getText().toString().trim().isEmpty() || addressEditText.getText().toString().trim().isEmpty()){
                    Snackbar.make(view, "All of the fields must be filled", Snackbar.LENGTH_SHORT)
                            .setTextColor(getResources().getColor(R.color.white))
                            .setBackgroundTint(getResources().getColor(R.color.red))
                            .show();
                }else{
                    goToSlide();
                }
            }
        });

        if(getInfo() == true){
            titleEditText.setText(AddtitionActivity.party.getTitle());
            addressEditText.setText(AddtitionActivity.party.getAddress());
        }


        return view;
    }

    private void init(View view){
        step1Title = view.findViewById(R.id.text_view_title_step_1_addition);
        buttonNextPartyAddition = view.findViewById(R.id.button_next_slide_1_party_addition);
        titleEditText = view.findViewById(R.id.edit_text_title);
        addressEditText = view.findViewById(R.id.edit_text_address);
        datePicker = view.findViewById(R.id.date_picker);
    }

    @Override
    public void animText() {
        step1Title.setText("");
        step1Title.setCharacterDelay(120);
        step1Title.displayTextWithAnimation("Main info");
    }

    @Override
    public void goToSlide() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.eventFragment, new EventAdditionSlide2Fragment());
        transaction.addToBackStack(null);

        AddtitionActivity.party.setTitle(titleEditText.getText().toString().trim());
        AddtitionActivity.party.setAddress(addressEditText.getText().toString().trim());
        AddtitionActivity.party.setOpen_date(datePicker.getMonth() + "." + datePicker.getDayOfMonth() + "." + datePicker.getYear());

        transaction.commit();
    }

    @Override
    public void goToPrevious() {

    }

    @Override
    public Boolean getInfo() {
        if(AddtitionActivity.party.getTitle() == null ||
                AddtitionActivity.party.getAddress() == null ||
                AddtitionActivity.party.getOpen_date() == null){

            isPrevious = false;

        }else{
            isPrevious = true;
        }
        return isPrevious;
    }
}