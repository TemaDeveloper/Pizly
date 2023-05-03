package com_pizly.java_pizly.pizly.ui.eventAddition;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import com_pizly.java_pizly.pizly.MainActivity;
import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.animation.TextAnimator;
import com_pizly.java_pizly.pizly.animation.TypeTextWriterTextView;
import com_pizly.java_pizly.pizly.auth.SlideChanger;
import com_pizly.java_pizly.pizly.managers.SharedPrefManager;
import com_pizly.java_pizly.pizly.rest.ApiClient;
import com_pizly.java_pizly.pizly.rest.ApiInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventPriceAdditionSlide4Fragment extends Fragment implements SlideChanger, TextAnimator {

    private TypeTextWriterTextView title4Addition;
    private MaterialButton finishButton;
    private Button previousButton;
    private TextInputEditText priceEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_price_addition_slide4, container, false);

        init(view);
        animText();

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiInterface apiInterface = ApiClient.getApiService();
                Call<ResponseBody> eventAdditionCall = apiInterface.addParty(
                        AddtitionActivity.party.getTitle(),
                        AddtitionActivity.party.getAddress(),
                        AddtitionActivity.party.getOpen_date(),
                        AddtitionActivity.party.getRules(),
                        AddtitionActivity.party.getOpen_time(),
                        AddtitionActivity.party.getFinish_time(),
                        AddtitionActivity.party.getAdditional_info(),
                        priceEditText.getText().toString().trim(),
                        SharedPrefManager.getInstance(getContext()).getKeyID(),
                        AddtitionActivity.party.getImageParty()
                );

                eventAdditionCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            goToSlide();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPrevious();
            }
        });
        return view;
    }

    private void init(View view) {
        title4Addition = view.findViewById(R.id.text_view_title_step_4_addition);
        previousButton = view.findViewById(R.id.button_previous_4);
        finishButton = view.findViewById(R.id.button_finish_creating_event);
        priceEditText = view.findViewById(R.id.edit_text_price);
    }

    @Override
    public void animText() {
        title4Addition.setText("");
        title4Addition.setCharacterDelay(120);
        title4Addition.displayTextWithAnimation("Pricing");
    }

    @Override
    public void goToSlide() {
        AddtitionActivity.party.setTitle("");
        AddtitionActivity.party.setAddress("");
        AddtitionActivity.party.setOpen_date("");
        AddtitionActivity.party.setRules("");
        AddtitionActivity.party.setOpen_time("");
        AddtitionActivity.party.setFinish_time("");
        AddtitionActivity.party.setAdditional_info("");
        startActivity(new Intent(getContext(), MainActivity.class));
        getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

    @Override
    public void goToPrevious() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.eventFragment, new EventAdditionSlide3Fragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public Boolean getInfo() {
        return null;
    }
}