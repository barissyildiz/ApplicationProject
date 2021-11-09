package com.baris.applicationproject.ui.form;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.baris.applicationproject.R;
import com.baris.applicationproject.customviews.PhoneCustomView;
import com.baris.applicationproject.models.CustomerModel;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.rimoto.intlphoneinput.IntlPhoneInput;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

public class CreateFormFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    PhoneCustomView phoneCustomView;
    ArrayList<CustomerModel> customerModelsList;
    String lastphonenumbertext = "11";
    String date;
    String gender = "";
    int SELECT_PICTURE = 200;
    Uri selectedImageUri;
    public static final int GALLERY_INTENT_CALLED = 1;
    public static final int GALLERY_KITKAT_INTENT_CALLED = 2;

    @BindView(R.id.datePickerBirthday)
    DatePicker datePicker;

    @BindView(R.id.buttonBirthay)
    Button birthday;

    @BindView(R.id.phoneNumber)
    TextInputEditText phonenumber;

    @BindView(R.id.textviewbirthday)
    TextView textViewbirthday;

    @BindView(R.id.formSave)
    Button buttonFormSave;

    @BindView(R.id.customerName)
    TextInputEditText customerName;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroupGender;

    @BindView(R.id.imageViewCustomerPicture)
    ImageView imageViewCustomerPicture;

    @OnClick(R.id.buttonBirthay)
    public void buttonClick() {
        datePicker.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.deneme)
    public void deneme() {

        Toast.makeText(getContext(),phoneCustomView.getPhoneText(),Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.buttonCustomerİmage)
    public void buttonClick2() {

            // create an instance of the
            // intent of the type image
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_OPEN_DOCUMENT);

            // pass the constant to compare it
            // with the returned requestCode

            startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);

    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data

                selectedImageUri = data.getData();

                /*
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    imageViewCustomerPicture.setImageURI(selectedImageUri);
                }

                 */
                final Uri uri = data.getData();

                Glide.with(this)
                        .load(new File(uri.getPath()))
                        .into(imageViewCustomerPicture);
            }
        }
    }

    @OnClick(R.id.formSave)
    public void checkForm2() {

        if(textViewbirthday.getVisibility() == View.VISIBLE && !customerName.getText().toString().equalsIgnoreCase("") &&
                !gender.isEmpty()) {

            Toast.makeText(getContext(),gender,Toast.LENGTH_LONG).show();
            CustomerModel customerModel = new CustomerModel();
            customerModel.setCustomerName(customerName.getText().toString());
            customerModel.setCustomerBirthday(date);
            customerModel.setGender(gender);
            customerModel.setCustomerphonenumber(phonenumber.getText().toString());
            customerModel.setCustomerPicture(selectedImageUri.toString());
            loadData();
            saveData(customerModel);
        }
        else {

            Toast.makeText(getContext(),getResources().getString(R.string.fillingoutform),Toast.LENGTH_LONG).show();
        }
    }

    public void saveData(CustomerModel customerModel) {

        if(customerModelsList == null) {

            customerModelsList = new ArrayList<>();
        }
        customerModelsList.add(customerModel);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(getResources().getString(R.string.sharedprefencesfile), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(customerModelsList);
        editor.putString(getResources().getString(R.string.sharedpreferenceskey),json);
        editor.apply();

    }

    public void loadData() {

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(getResources().getString(R.string.sharedprefencesfile),Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(getResources().getString(R.string.sharedpreferenceskey),null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<CustomerModel>>() {

        }.getType();

        if(customerModelsList == null) {

            customerModelsList = new ArrayList<>();
        }
        customerModelsList = gson.fromJson(json,type);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_form,container,false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this,view);

        phoneCustomView = new PhoneCustomView(view.getContext());
        radioGroupGender.setOnCheckedChangeListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

                    date = i2+ "."+i1+"."+i;
                    datePicker.setVisibility(View.GONE);
                    String birthdaytext = getResources().getString(R.string.birthday) + date;
                    textViewbirthday.setText(birthdaytext);
                    textViewbirthday.setVisibility(View.VISIBLE);

                }
            });
        }

        /*
        phonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

                String text = phonenumber.getText().toString();

                int  textLength = phonenumber.getText().length();
                if (text.endsWith("-") || text.endsWith(" ") || text.endsWith(" "))
                    return;
                if (textLength == 1) {
                    if (!text.contains("("))
                    {
                        phonenumber.setText(new StringBuilder(text).insert(text.length() - 1, "0"+" (").toString());
                        phonenumber.setSelection(phonenumber.getText().length());
                    }
                }
                else if (textLength == 7)
                {
                    if (!text.contains(")"))
                    {
                        phonenumber.setText(new StringBuilder(text).insert(text.length() - 1, ")").toString());
                        phonenumber.setSelection(phonenumber.getText().length());
                    }
                }
                else if (textLength == 8)
                {
                    phonenumber.setText(new StringBuilder(text).insert(text.length() - 1, " ").toString());
                    phonenumber.setSelection(phonenumber.getText().length());
                }
                else if (textLength == 12)
                {
                    if (!text.contains("-"))
                    {
                        phonenumber.setText(new StringBuilder(text).insert(text.length() - 1, "-").toString());
                        phonenumber.setSelection(phonenumber.getText().length());
                    }
                }
                else if (textLength == 15)
                {
                    if (text.contains("-"))
                    {
                        phonenumber.setText(new StringBuilder(text).insert(text.length() - 1, "-").toString());
                        phonenumber.setSelection(phonenumber.getText().length());
                    }
                }
                else if (textLength == 17) {

                    lastphonenumbertext = phonenumber.getText().toString();
                }
                else if (textLength == 18)
                {
                    try {
                        phonenumber.setText("");
                        phonenumber.setError("GİRELEBİLECEK MAXİUMUM RAKAM SAYISI AŞILDI.");
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        });


         */
    }



    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        if(radioGroup.getId() == R.id.radioGroup) {

            if(radioGroupGender.getCheckedRadioButtonId() == R.id.radioMale) {

                gender = getResources().getString(R.string.male);

            }else if(radioGroupGender.getCheckedRadioButtonId() == R.id.radioFemale) {

                gender = getResources().getString(R.string.female);
            }
        }
    }
}
