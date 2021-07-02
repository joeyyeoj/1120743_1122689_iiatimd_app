package com.example.qrcontacts;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class GeneratorFragment extends Fragment {
    private Button scanBtn;
    private ImageView qrImage;
    private String qrvalue;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View v = inflater.inflate(R.layout.generator_fragment, container, false);
        qrvalue = "https://polar-anchorage-54627.herokuapp.com/api/getContact/1";
        qrImage = (ImageView)v.findViewById(R.id.qrImage);
        QRGEncoder qrgEncoder = new QRGEncoder(qrvalue, null, QRGContents.Type.TEXT, 500);
        Bitmap qrBits = qrgEncoder.getBitmap();
        qrImage.setImageBitmap(qrBits);
        return v;
    }
}
