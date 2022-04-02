package com.android.calculateinterest19110168;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {




    private Context context;
    private EditText mDeposit;
    private EditText mInterestRate;
    private EditText mSendingTerm;


    private Button btnSeenResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculate_interest);
        context = this;
        ConnectView();


    }

    private void ConnectView(){
        mDeposit = findViewById(R.id.deposit);
        mInterestRate = findViewById(R.id.depositInterestRate);
        mSendingTerm = findViewById(R.id.depositSendingTerm);


        SeenResultView();
    }
    private void SeenResultView(){

            btnSeenResult = (Button) findViewById(R.id.btnSeenResult);
            btnSeenResult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean flag = checkInput();
                    if(flag) {
                        Intent intent = new Intent(context, SeenResult.class);
                        intent.putExtra("R.string.key_total_receive_interest", calculateInterest());
                        intent.putExtra("R.string.key_total_money_received", totalMoneyReceived());


                        startActivity(intent);
                    }
                }
            });


    }
    private boolean checkInput(){
        boolean flag = true;
        if(Utilities.checkEmptyInput(mDeposit)){
            Toast.makeText(getApplicationContext(),R.string.error_input_deposit,Toast.LENGTH_SHORT).show();
            flag = false;
        }else {
            if(Double.parseDouble(mDeposit.getText().toString()) < 1000){
                Toast.makeText(getApplicationContext(),R.string.error_input_deposit,Toast.LENGTH_SHORT).show();
                flag = false;
            }
        }
        if(Utilities.checkEmptyInput(mInterestRate)){
            Toast.makeText(getApplicationContext(),R.string.error_input_interest,Toast.LENGTH_SHORT).show();
            flag = false;
        }
        if(Utilities.checkEmptyInput(mSendingTerm)){
            Toast.makeText(getApplicationContext(),R.string.error_input_due,Toast.LENGTH_SHORT).show();
            flag = false;
        }
        return flag;
    }
    private String calculateInterest(){

        long deposit = Long.parseLong(mDeposit.getText().toString());

        float interest = Float.parseFloat(mInterestRate.getText().toString());
        float due = Float.parseFloat(mSendingTerm.getText().toString());
        long result = (long) (deposit * interest/100 * ((due*30)/360));

        return result+"";
    }
    private String totalMoneyReceived(){
        long result = (long) (Double.parseDouble(mDeposit.getText().toString()) + Double.parseDouble(calculateInterest()));
        return  result+"";
    }

}
