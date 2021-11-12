package com.baris.applicationproject.customviews;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.baris.applicationproject.R;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhoneCustomView extends LinearLayout {

    boolean isphoneNumberCompleted = false;

    @BindView(R.id.phoneNumber)
    TextInputEditText phoneNumber;

    //TextInputEditText phoneNumber;
    String lastphonenumbertext = "11";

    public String getLastphonenumbertext() {
        return lastphonenumbertext;
    }

    public void setLastphonenumbertext(String lastphonenumbertext) {
        this.lastphonenumbertext = lastphonenumbertext;
    }

    public String getPhoneText() {

        String phoneNumberr = phoneNumber.getText().toString();
        return phoneNumberr;
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

        SharedPreferences sharedPreferences = context.getSharedPreferences(getResources().getString(R.string.sharedprefencesfile),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        View view = LayoutInflater.from(context).inflate(R.layout.custom_phone_inputview,this);
        ButterKnife.bind(this);

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
                lastphonenumbertext = editable.toString();

                int  textLength = phoneNumber.getText().length();
                if (text.endsWith("-") || text.endsWith(" ") || text.endsWith(" "))
                    return;
                if (textLength == 1) {
                    if (!text.contains("("))
                    {
                        phoneNumber.setText(new StringBuilder(text).insert(text.length() - 1, "0"+" (").toString());
                        phoneNumber.setSelection(phoneNumber.getText().length());
                        editor.putString("phoneNumber",getResources().getString(R.string.invalidPhoneNumber));
                        editor.apply();
                    }
                }
                else if (textLength == 7)
                {
                    if (!text.contains(")"))
                    {
                        phoneNumber.setText(new StringBuilder(text).insert(text.length() - 1, ")").toString());
                        phoneNumber.setSelection(phoneNumber.getText().length());
                        editor.putString("phoneNumber",getResources().getString(R.string.invalidPhoneNumber));
                        editor.apply();

                    }
                }
                else if (textLength == 8)
                {
                    phoneNumber.setText(new StringBuilder(text).insert(text.length() - 1, " ").toString());
                    phoneNumber.setSelection(phoneNumber.getText().length());
                    editor.putString("phoneNumber",getResources().getString(R.string.invalidPhoneNumber));
                    editor.apply();

                }
                else if (textLength == 12)
                {
                    if (!text.contains("-"))
                    {
                        phoneNumber.setText(new StringBuilder(text).insert(text.length() - 1, "-").toString());
                        phoneNumber.setSelection(phoneNumber.getText().length());
                        editor.putString("phoneNumber",getResources().getString(R.string.invalidPhoneNumber));
                        editor.apply();

                    }
                }
                else if (textLength == 15)
                {
                    if (text.contains("-"))
                    {
                        phoneNumber.setText(new StringBuilder(text).insert(text.length() - 1, "-").toString());
                        phoneNumber.setSelection(phoneNumber.getText().length());
                        editor.putString("phoneNumber",getResources().getString(R.string.invalidPhoneNumber));
                        editor.apply();

                    }
                }
                else if (textLength == 17) {

                    editor.putString("phoneNumber",phoneNumber.getText().toString());
                    editor.apply();

                }
                else if (textLength == 18)
                {
                    try {
                        phoneNumber.setText("");
                        phoneNumber.setError("GİRELEBİLECEK MAXİUMUM RAKAM SAYISI AŞILDI.");
                        editor.putString("phoneNumber",getResources().getString(R.string.invalidPhoneNumber));
                        editor.apply();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
