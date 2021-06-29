package com.example.qrcontacts;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class ScannerFragment extends Fragment {
    CodeScanner codeScanner;
    CodeScannerView scannerView;
    TextView resultData;
    Button cameraButton;

   private static final int CAMERA_PERMISSION_CODE = 100;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View v = inflater.inflate(R.layout.scanner_fragment, container, false);
        scannerView = v.findViewById(R.id.scanner_view);
        codeScanner = new CodeScanner(v.getContext(), scannerView);
        codeScanner.setCamera(CodeScanner.CAMERA_BACK);
        codeScanner.setAutoFocusMode(AutoFocusMode.SAFE);
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {

                getActivity().runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        resultData.setText(result.getText());
                    }
                });
            }
        });





        return v;
    }


}
