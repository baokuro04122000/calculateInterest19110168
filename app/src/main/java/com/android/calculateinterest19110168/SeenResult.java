package com.android.calculateinterest19110168;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.concurrent.TimeUnit;

public class SeenResult extends AppCompatActivity  {
    private Context context;
    private EditText mTotalReceivingInterest;
    private EditText mTotalMoneyReceived;
    private Button btnTakeAPicture;

    private static final int CAMERA_REQUEST = 1888;


    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seen_result);
        context = this;
        mTotalReceivingInterest = findViewById(R.id.totalReceivingInterest);
        mTotalMoneyReceived = findViewById(R.id.totalMoneyReceive);
        Intent intent = getIntent();

        mTotalReceivingInterest.setText(convertText(intent.getStringExtra("R.string.key_total_receive_interest")));
        mTotalMoneyReceived.setText(convertText(intent.getStringExtra("R.string.key_total_money_received")));




        mTotalReceivingInterest.setEnabled(false); mTotalReceivingInterest.setInputType(InputType.TYPE_NULL);
        mTotalMoneyReceived.setEnabled(false); mTotalMoneyReceived.setInputType(InputType.TYPE_NULL);
        TakeAPictureView();
    }

    private void TakeAPictureView(){

        btnTakeAPicture = (Button) findViewById(R.id.btnTakeAPicture);
        btnTakeAPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermission();
            }
        });


    }
    private  void askCameraPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},MY_CAMERA_PERMISSION_CODE);
        }else{
            openCamera();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults.length < 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, R.string.camera_request_permission_granted, Toast.LENGTH_LONG).show();
                openCamera();
            }
            else
            {
                Toast.makeText(this, R.string.camera_request_permission_denied, Toast.LENGTH_LONG).show();
            }
        }
    }
    private void openCamera(){
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {
            Toast.makeText(this, R.string.camera_take_a_picture_success, Toast.LENGTH_LONG).show();
            try {
                TimeUnit.SECONDS.sleep(2);
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    public String convertText(String text){
        StringBuilder stringBuilder = new StringBuilder(text);
        for(int i=stringBuilder.length() -3 ; i>0;i -= 3){
            stringBuilder.insert(i, ",");
        }
        return stringBuilder.toString()+" d";
    }
}
