package com_pizly.java_pizly.pizly.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import com_pizly.java_pizly.pizly.MainActivity;
import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.models.Person;
import com_pizly.java_pizly.pizly.rest.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BottomSheetChangePasswordFragment extends BottomSheetDialogFragment {

    private TextInputEditText newPasswordEditText, oldPasswordEditText, confirmationPasswordEditText;
    private MaterialButton changePasswordButton;
    private String email;

    public BottomSheetChangePasswordFragment(String email){
        this.email = email;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_change_password, container, false);

        init(view);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(newPasswordEditText.getText().toString().trim().equals(confirmationPasswordEditText.getText().toString().trim())){
                    changePassword(view);
                    startActivity(new Intent(getContext(), MainActivity.class));
                    getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                }else{
                    Snackbar.make(view, "Your new password and confirmation one not matches!", Snackbar.LENGTH_SHORT)
                            .setTextColor(getResources().getColor(R.color.white))
                            .setBackgroundTint(getResources().getColor(R.color.red))
                            .show();
                }

            }
        });


        return view;

    }

    private void init(View view){
        newPasswordEditText = view.findViewById(R.id.edit_text_new_password);
        oldPasswordEditText = view.findViewById(R.id.edit_text_password);
        confirmationPasswordEditText = view.findViewById(R.id.edit_text_new_password_confirmation);
        changePasswordButton = view.findViewById(R.id.button_change_password);
    }

    private void changePassword(View view){
        Call<ResponseBody> changingPasswordCall = ApiClient.getApiService().changeOldPassword(
                email,
                oldPasswordEditText.getText().toString().trim(),
                newPasswordEditText.getText().toString().trim(),
                confirmationPasswordEditText.getText().toString().trim());

        changingPasswordCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Snackbar.make(view, "Password was successfully changed", Snackbar.LENGTH_SHORT)
                            .setTextColor(getResources().getColor(R.color.black))
                            .setBackgroundTint(getResources().getColor(R.color.white))
                            .show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}