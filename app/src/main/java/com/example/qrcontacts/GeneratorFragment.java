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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View v = inflater.inflate(R.layout.generator_fragment, container, false);

        Button scanBtn;
        ImageView qrImage;
        String qrvalue;
        qrvalue = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
        qrImage = (ImageView)v.findViewById(R.id.qrImage);
        QRGEncoder qrgEncoder = new QRGEncoder(qrvalue, null, QRGContents.Type.TEXT, 500);
        Bitmap qrBits = qrgEncoder.getBitmap();
        qrImage.setImageBitmap(qrBits);

        return v;
    }
}
