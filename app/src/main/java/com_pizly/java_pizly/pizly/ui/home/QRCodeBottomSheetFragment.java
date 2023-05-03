package com_pizly.java_pizly.pizly.ui.home;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import com_pizly.java_pizly.pizly.R;
import com_pizly.java_pizly.pizly.managers.SharedPrefManager;


public class QRCodeBottomSheetFragment extends BottomSheetDialogFragment {

    private static final String TAG = "QRActivity";
    private ImageView imageQR;
    //QR code generation
    private Bitmap bitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_q_r_code_bottom_sheet, container, false);
        imageQR = view.findViewById(R.id.imageQR);
        generateQRCode();
        return view;
    }

    private void generateQRCode() {
        QRGEncoder qrgEncoder = new QRGEncoder(SharedPrefManager.getInstance(getContext()).getKeyID() + "", QRGContents.Type.TEXT, 400);
        try {
            qrgEncoder.setColorBlack(Color.TRANSPARENT);
            qrgEncoder.setColorWhite(Color.BLACK);
            bitmap = qrgEncoder.getBitmap();
            imageQR.setImageBitmap(bitmap);
        } catch (Exception e) {
            Log.v(TAG, e.toString());
        }
    }
}