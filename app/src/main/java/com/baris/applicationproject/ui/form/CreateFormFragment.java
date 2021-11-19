package com.baris.applicationproject.ui.form;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.baris.applicationproject.R;
import com.baris.applicationproject.customviews.PhoneCustomView;
import com.baris.applicationproject.models.CustomerModel;
import com.baris.applicationproject.ui.account.CustomerAccountFragment;
import com.baris.applicationproject.ui.contract.ContractFragment;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateFormFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    PhoneCustomView phoneCustomView;
    ArrayList<CustomerModel> customerModelsList;
    String lastphonenumbertext = "11";
    String date;
    String gender = "";
    View view;
    boolean isLoaded = false;
    int SELECT_PICTURE = 200;
    Uri selectedImageUri;
    public static final int GALLERY_INTENT_CALLED = 1;
    public static final int GALLERY_KITKAT_INTENT_CALLED = 2;
    private FragmentActivity myContext;
    BroadcastReceiver broadcastReceiver;
    String key = "customer";
    static final String KEY_CUSTOMER_ACCOUNT_NUMBER = "customerAccountNumber";
    String branchNumber = "branchNumber";
    String branchName = "branchName";
    String customerBalance = "customerBalance";
    String currency = "currency";
    boolean isSavedCustomer = false;
    boolean isContractActivated = false;

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

    @BindView(R.id.fragmentTextViewCustomerAccount)
    TextView textViewCustomerAccount;

    @BindView(R.id.fragmentTextViewSelectedCustomerAccount)
    TextView textViewSelectedCustomerAccount;

    @BindView(R.id.checkboxContractPageCheck)
    CheckBox checkBoxContractPageCheck;

    @OnClick(R.id.fragmentTextViewContractPage)
    public void openContractPage() {

        Fragment fragment = new ContractFragment();
        FragmentManager fragmentManager = myContext.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentHome, fragment).commit();
    }

    //@BindView(R.id.fragmentCustomerAccountView)
    //CustomerAccountView customerAccountView;

    @OnClick(R.id.fragmentTextViewCustomerAccount)
    public void openCustomerAccount() {

        Fragment fragment = new CustomerAccountFragment();
        FragmentManager fragmentManager = myContext.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentHome, fragment).commit();
    }

    @OnClick(R.id.buttonBirthay)
    public void buttonClick() {
        datePicker.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.deneme)
    public void deneme() {

        SharedPreferences sharedPreferences = myContext.getSharedPreferences(getResources().getString(R.string.sharedprefencesfile), Context.MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString("phoneNumber", getResources().getString(R.string.invalidPhoneNumber));
        if (phoneNumber.length() == 17) {
            Toast.makeText(getContext(), phoneNumber, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.invalidPhoneNumber), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.buttonCustomerİmage)
    public void buttonClick2() {

        /*

            // create an instance of the
            // intent of the type image
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_OPEN_DOCUMENT);

            // pass the constant to compare it
            // with the returned requestCode

            startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);

         */

        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        }
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);


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

                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    imageViewCustomerPicture.setImageURI(selectedImageUri);
                }
                final Uri uri = data.getData();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    final int takeFlags = data.getFlags()
                            & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                            | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    getContext().getContentResolver().takePersistableUriPermission(uri, takeFlags);
                }

                Glide.with(this)
                        .load(new File(uri.getPath()))
                        .into(imageViewCustomerPicture);

            }
        }
    }

    @OnClick(R.id.formSave)
    public void checkForm2() {

        if (textViewbirthday.getVisibility() == View.VISIBLE && !customerName.getText().toString().equalsIgnoreCase("") &&
                !gender.isEmpty()) {

            Toast.makeText(getContext(), "KAYIT YAPILDI", Toast.LENGTH_LONG).show();
            myContext.getSupportFragmentManager().popBackStackImmediate("formFragment", 0);
            myContext.getSupportFragmentManager().popBackStack();
            CustomerModel customerModel = new CustomerModel();
            customerModel.setCustomerName(customerName.getText().toString());
            customerModel.setCustomerBirthday(date);
            customerModel.setGender(gender);
            customerModel.setCustomerphonenumber(phonenumber.getText().toString());
            customerModel.setCustomerPicture(selectedImageUri.toString());
            loadData();
            saveData(customerModel);
        } else {

            Toast.makeText(getContext(), getResources().getString(R.string.fillingoutform), Toast.LENGTH_LONG).show();
        }
    }

    public void saveData(CustomerModel customerModel) {

        if (customerModelsList == null) {

            customerModelsList = new ArrayList<>();
        }
        customerModelsList.add(customerModel);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(getResources().getString(R.string.sharedprefencesfile), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(customerModelsList);
        editor.putString(getResources().getString(R.string.sharedpreferenceskey), json);
        editor.apply();

    }

    public void loadData() {

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(getResources().getString(R.string.sharedprefencesfile), Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(getResources().getString(R.string.sharedpreferenceskey), null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<CustomerModel>>() {

        }.getType();

        if (customerModelsList == null) {

            customerModelsList = new ArrayList<>();
        }
        customerModelsList = gson.fromJson(json, type);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (!isLoaded) {

            view = inflater.inflate(R.layout.fragment_create_form, container, false);
            Toast.makeText(view.getContext(), "ACTİVİTY HAS BEEN CREATED", Toast.LENGTH_SHORT).show();
            isLoaded = true;
        }


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        takeArguments();
        checkContractPageCheck();
        checkBoxContractPageCheck.setChecked(isContractActivated);

        isSavedCustomer = false;

        phoneCustomView = new PhoneCustomView(view.getContext());
        radioGroupGender.setOnCheckedChangeListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

                    date = i2 + "." + i1 + "." + i;
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

    private void checkContractPageCheck() {

        checkBoxContractPageCheck.setChecked(isContractActivated);

    }

    private void takeArguments() {

        Bundle bundle = getArguments();
        if (bundle != null) {
            String customerAccountNumberr = bundle.getString(KEY_CUSTOMER_ACCOUNT_NUMBER);
            String branchNumberr = bundle.getString(branchNumber);
            String branchNamee = bundle.getString(branchName);
            String balancee = bundle.getString(customerBalance);
            String currencyy = bundle.getString(currency);

            textViewSelectedCustomerAccount.setVisibility(View.VISIBLE);
            textViewSelectedCustomerAccount.setText(branchNamee + "/" + balancee + currencyy);

            isContractActivated = bundle.getBoolean("isContractActivated");

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                String message = bundle.getString("MESSAGE");

                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }

        };

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(broadcastReceiver, new IntentFilter("MESSAGE_SENT_ACTION"));
    }

    @Override
    public void onPause() {
        super.onPause();

        if (broadcastReceiver != null) {
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        if (radioGroup.getId() == R.id.radioGroup) {

            if (radioGroupGender.getCheckedRadioButtonId() == R.id.radioMale) {

                gender = getResources().getString(R.string.male);

            } else if (radioGroupGender.getCheckedRadioButtonId() == R.id.radioFemale) {

                gender = getResources().getString(R.string.female);
            }
        }
    }
}
