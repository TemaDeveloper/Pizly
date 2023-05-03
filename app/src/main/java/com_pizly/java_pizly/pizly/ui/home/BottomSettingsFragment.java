package com_pizly.java_pizly.pizly.ui.home;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatDelegate;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import com_pizly.java_pizly.pizly.BuildConfig;
import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.auth.SignupLoginActivity;
import com_pizly.java_pizly.pizly.auth.login.LoginActivity;
import com_pizly.java_pizly.pizly.managers.SharedPrefManager;

public class BottomSettingsFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    //widgets
    private RelativeLayout feedbackLayout, shareLayout;
    private LinearLayout signOutLayout;
    private LottieAnimationView lottieSwitcher;
    //check the switcher
    private Boolean mode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        init(view);

        shareLayout.setOnClickListener(this);
        lottieSwitcher.setOnClickListener(this);
        feedbackLayout.setOnClickListener(this);
        signOutLayout.setOnClickListener(this);

        //check mode
        if (mode == false) {
            lottieSwitcher.setMinAndMaxProgress(0f, .5f);
        } else {
            lottieSwitcher.setMinAndMaxProgress(.5f, 1.0f);
        }

        return view;
    }

    private void init(View view) {

        mode = SharedPrefManager.getInstance(getContext()).getMode();
        feedbackLayout = view.findViewById(R.id.lin_feed_back);
        shareLayout = view.findViewById(R.id.lin_share_app);
        lottieSwitcher = view.findViewById(R.id.switcher);
        signOutLayout = view.findViewById(R.id.lin_sign_out);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.switcher:
                if (mode == true) {
                    lottieSwitcher.setMinAndMaxProgress(0f, .5f);//.5f, 1.0f
                    lottieSwitcher.playAnimation();
                    SharedPrefManager.getInstance(getContext()).setMode(false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    mode = false;
                } else {
                    lottieSwitcher.setMinAndMaxProgress(.5f, 1.0f);
                    lottieSwitcher.playAnimation();
                    SharedPrefManager.getInstance(getContext()).setMode(true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    mode = true;
                }
                break;
            case R.id.lin_share_app:
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "WE app");
                    String shareMessage = "\n " + "Let me recommend" + " \n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "APP"));
                } catch (Exception e) {
                    //e.toString();
                }
                break;
            case R.id.lin_feed_back:
                Intent feedbackEmail = new Intent(Intent.ACTION_SEND);
                feedbackEmail.setType("text/email");
                feedbackEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"freshart666@gmail.com"});
                feedbackEmail.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                startActivity(Intent.createChooser(feedbackEmail, "Send feedback"));
                break;
            case R.id.lin_sign_out:

                final Dialog dialog = new Dialog(getActivity(), R.style.ThemeDialog);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.alert_dialog_sign_out);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                MaterialButton btnNo = dialog.findViewById(R.id.btn_no), btnYes = dialog.findViewById(R.id.btn_yes);
                btnNo.setOnClickListener(v -> dialog.dismiss());
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPrefManager.getInstance(getContext()).setKeyID(0);
                        startActivity(new Intent(getContext(), SignupLoginActivity.class));
                        getActivity().finish();
                        dialog.cancel();
                    }
                });
                dialog.show();

                break;
        }
    }
}
