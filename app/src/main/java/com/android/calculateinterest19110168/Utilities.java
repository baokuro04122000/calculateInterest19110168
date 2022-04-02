package com.android.calculateinterest19110168;

import android.widget.EditText;

    public class Utilities {
        public static boolean checkEmptyInput(EditText value){
            if(value.getText().toString().trim().length() <= 0) {
                return true;
            }
            return false;
        }
}
