package com_pizly.java_pizly.pizly.ui.eventAddition;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.animation.TextAnimator;
import com_pizly.java_pizly.pizly.animation.TypeTextWriterTextView;
import com_pizly.java_pizly.pizly.auth.SlideChanger;


public class EventAdditionSlide2Fragment extends Fragment implements SlideChanger, TextAnimator {

    private TextInputEditText openTime, finishTime, rulesEditText;
    private TypeTextWriterTextView title2Addition;
    private FloatingActionButton buttonNextPartyAddition;
    private Button previousButton;
    private String openTimeFormatter, finishTimeFormatter;
    private boolean isPrevious = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_addition_slide2, container, false);

        init(view);
        animText();

        buttonNextPartyAddition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(openTime.getText().toString().trim().isEmpty() || finishTime.getText().toString().isEmpty()){
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

        openTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        String amPm;
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        openTimeFormatter = String.format("%02d:%02d", hourOfDay, minutes) + " " + amPm;
                        openTime.setText(openTimeFormatter);
                    }
                }, 0, 0, false);


                timePickerDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        timePickerDialog.getButton(Dialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.title_text_color));
                        timePickerDialog.getButton(Dialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.title_text_color));
                    }
                });
                timePickerDialog.show();
            }
        });

        finishTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        String amPm;
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        finishTimeFormatter = String.format("%02d:%02d", hourOfDay, minutes) + " " + amPm;
                        finishTime.setText(finishTimeFormatter);
                    }
                }, 0, 0, false);


                timePickerDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        timePickerDialog.getButton(Dialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.title_text_color));
                        timePickerDialog.getButton(Dialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.title_text_color));
                    }
                });
                timePickerDialog.show();
            }
        });

        if(getInfo() == true){
            openTime.setText(AddtitionActivity.party.getOpen_time());
            finishTime.setText(AddtitionActivity.party.getFinish_time());
            rulesEditText.setText(AddtitionActivity.party.getRules());
        }


        return view;
    }

    private void init(View view){
        openTime = view.findViewById(R.id.edit_text_open_time);
        rulesEditText = view.findViewById(R.id.edit_text_rules);
        finishTime = view.findViewById(R.id.edit_text_finish_time);
        title2Addition = view.findViewById(R.id.text_view_title_step_2_addition);
        previousButton = view.findViewById(R.id.button_previous_2);
        buttonNextPartyAddition = view.findViewById(R.id.button_next_slide_2_party_addition);
    }

    @Override
    public void animText() {
        title2Addition.setText("");
        title2Addition.setCharacterDelay(120);
        title2Addition.displayTextWithAnimation("Rules & Time");
    }

    @Override
    public void goToSlide() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.eventFragment, new EventAdditionSlide3Fragment());
        transaction.addToBackStack(null);

        AddtitionActivity.party.setRules(rulesEditText.getText().toString().trim());
        AddtitionActivity.party.setOpen_time(openTimeFormatter);
        AddtitionActivity.party.setFinish_time(finishTimeFormatter);

        transaction.commit();
    }

    @Override
    public void goToPrevious() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.eventFragment, new EventAdditionSlide1Fragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public Boolean getInfo() {
        if(AddtitionActivity.party.getRules() == null ||
                AddtitionActivity.party.getOpen_time() == null ||
                AddtitionActivity.party.getFinish_time() == null){

            isPrevious = false;

        }else{
            isPrevious = true;
        }
        return isPrevious;
    }
}