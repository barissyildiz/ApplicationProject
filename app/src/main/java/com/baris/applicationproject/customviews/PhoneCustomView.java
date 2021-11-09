package com.baris.applicationproject.customviews;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.baris.applicationproject.R;
import com.google.android.material.textfield.TextInputEditText;

public class PhoneCustomView extends LinearLayout {

    TextInputEditText phoneNumber;
    String lastphonenumbertext = "11";

    public String getPhoneText() {

        return phoneNumber.getText().toString();
    }


    public PhoneCustomView(Context context) {
        super(context);
        init(context);
    }

    public PhoneCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PhoneCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public PhoneCustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.custom_phone_inputview,this);
        phoneNumber = view.findViewById(R.id.phoneNumber);

        //phoneNumber.setText("0325235232");

        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String text = phoneNumber.getText().toString();

                int  textLength = phoneNumber.getText().length();
                if (text.endsWith("-") || text.endsWith(" ") || text.endsWith(" "))
                    return;
                if (textLength == 1) {
                    if (!text.contains("("))
                    {
                        phoneNumber.setText(new StringBuilder(text).insert(text.length() - 1, "0"+" (").toString());
                        phoneNumber.setSelection(phoneNumber.getText().length());
                    }
                }
                else if (textLength == 7)
                {
                    if (!text.contains(")"))
                    {
                        phoneNumber.setText(new StringBuilder(text).insert(text.length() - 1, ")").toString());
                        phoneNumber.setSelection(phoneNumber.getText().length());
                    }
                }
                else if (textLength == 8)
                {
                    phoneNumber.setText(new StringBuilder(text).insert(text.length() - 1, " ").toString());
                    phoneNumber.setSelection(phoneNumber.getText().length());
                }
                else if (textLength == 12)
                {
                    if (!text.contains("-"))
                    {
                        phoneNumber.setText(new StringBuilder(text).insert(text.length() - 1, "-").toString());
                        phoneNumber.setSelection(phoneNumber.getText().length());
                    }
                }
                else if (textLength == 15)
                {
                    if (text.contains("-"))
                    {
                        phoneNumber.setText(new StringBuilder(text).insert(text.length() - 1, "-").toString());
                        phoneNumber.setSelection(phoneNumber.getText().length());
                    }
                }
                else if (textLength == 17) {

                    lastphonenumbertext = phoneNumber.getText().toString();
                }
                else if (textLength == 18)
                {
                    try {
                        phoneNumber.setText("");
                        phoneNumber.setError("GİRELEBİLECEK MAXİUMUM RAKAM SAYISI AŞILDI.");
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
