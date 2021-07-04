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
        qrvalue = "https://api-iiatmd.tychovanveen.nl/public/api/get_user/1?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvYXBpLWlpYXRtZC50eWNob3ZhbnZlZW4ubmxcL3B1YmxpY1wvYXBpXC9sb2dpbiIsImlhdCI6MTYyNTM4Nzk5MiwibmJmIjoxNjI1Mzg3OTkyLCJqdGkiOiJYMkRrQ0pRVFFLVHNpNzg0Iiwic3ViIjoxLCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0._WYYrRMGioucJv_RZ-ey6HCm7U6d8FRWvSeoWq6QtEY";
        qrImage = (ImageView)v.findViewById(R.id.qrImage);
        QRGEncoder qrgEncoder = new QRGEncoder(qrvalue, null, QRGContents.Type.TEXT, 500);
        Bitmap qrBits = qrgEncoder.getBitmap();
        qrImage.setImageBitmap(qrBits);
        return v;
    }
}
