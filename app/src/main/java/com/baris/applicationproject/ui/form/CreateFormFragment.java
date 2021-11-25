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
import android.widget.CompoundButton;
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
import com.baris.applicationproject.models.AccountModel;
import com.baris.applicationproject.models.CustomerModel;
import com.baris.applicationproject.ui.account.CustomerAccountFragment;
import com.baris.applicationproject.ui.confirm.ConfirmationFragment;
import com.baris.applicationproject.ui.contract.ContractFragment;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateFormFragment extends Fragment implements RadioGroup.OnCheckedChangeListener{

    PhoneCustomView phoneCustomView;
    ArrayList<CustomerModel> customerModelsList;
    AccountModel accountModel;
    ArrayList<String> accountTypes;
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
    public static final String KEY_CUSTOMER_ACCOUNT_NUMBER = "customerAccountNumber";
    String branchNumber = "branchNumber";
    String branchName = "branchName";
    String customerBalance = "customerBalance";
    String currency = "currency";
    public static final String KEY_CUSTOMER_INFO = "customerInfo";
    public static final String KEY_CUSTOMER_ACCOUNT_INFO = "customerAccountInfo";
    public static final String KEY_CUSTOMER_ACCOUNT_TYPES = "customerAccountTypes";
    boolean isSavedCustomer = false;
    boolean isContractActivated = false;
    //When we transfer datas to ConfirmationPage , picture of costumer needs to be guaranteed to select in galeria by customer
    boolean isCostumerPictureActive = false;

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

    @BindView(R.id.depositAccount)
    CheckBox checkBoxDepositAccount;

    @BindView(R.id.checkingAccount)
    CheckBox checkBoxCheckingAccount;

    @BindView(R.id.fundAccount)
    CheckBox checkBoxFundAccount;

    @BindView(R.id.dollarGoldAccount)
    CheckBox checkBoxDollarGoldAccount;

    @BindView(R.id.savingsAccount)
    CheckBox checkBoxSavingsAccount;

    @OnClick(R.id.fragmentTextViewContractPage)
    public void openContractPage() {

        Fragment fragment = new ContractFragment();
        FragmentManager fragmentManager = myContext.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentHome, fragment).commit();
    }

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

    @SuppressLint({"WrongConstant", "ObsoleteSdkInt"})
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data

                if (data != null) {
                    selectedImageUri = data.getData();
                }

                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    imageViewCustomerPicture.setImageURI(selectedImageUri);
                    isCostumerPictureActive = true;
                }

                /*

                final Uri uri = data.getData();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    final int takeFlags = data.getFlags()
                            & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                            | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    requireContext().getContentResolver().takePersistableUriPermission(uri, takeFlags);
                }

                if (uri != null) {
                    Glide.with(this)
                            .load(new File(uri.getPath()))
                            .into(imageViewCustomerPicture);
                }

                 */

            }
        }
    }

    @OnClick(R.id.formSave)
    public void checkForm2() {

        if (textViewbirthday.getVisibility() == View.VISIBLE && !customerName.getText().toString().equalsIgnoreCase("") &&
                !gender.isEmpty() && isCostumerPictureActive && textViewSelectedCustomerAccount.getVisibility() == View.VISIBLE &&
                checkBoxContractPageCheck.isChecked() && phonenumber.getText().toString().length() == 17) {

            addAccountTypes();

            CustomerModel customerModel = new CustomerModel();
            customerModel.setCustomerName(customerName.getText().toString());
            customerModel.setCustomerBirthday(date);
            customerModel.setGender(gender);
            customerModel.setCustomerphonenumber(phonenumber.getText().toString());
            customerModel.setCustomerPicture(selectedImageUri.toString());

            Fragment fragment = new ConfirmationFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY_CUSTOMER_INFO, customerModel);
            bundle.putSerializable(KEY_CUSTOMER_ACCOUNT_INFO, accountModel);
            bundle.putStringArrayList(KEY_CUSTOMER_ACCOUNT_TYPES,accountTypes);
            fragment.setArguments(bundle);
            myContext.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHome, fragment).commit();

            /*
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

             */
        } else {

            Toast.makeText(getContext(), getResources().getString(R.string.fillingoutform), Toast.LENGTH_LONG).show();
        }
    }

    public void addAccountTypes() {

        if(checkBoxDepositAccount.isChecked()) {
            accountTypes.add(checkBoxDepositAccount.getText().toString());
        }
        if(checkBoxCheckingAccount.isChecked()) {
            accountTypes.add(checkBoxCheckingAccount.getText().toString());
        }
        if(checkBoxFundAccount.isChecked()) {
            accountTypes.add(checkBoxFundAccount.getText().toString());
        }
        if(checkBoxDollarGoldAccount.isChecked()) {
            accountTypes.add(checkBoxDollarGoldAccount.getText().toString());
        }
        if(checkBoxSavingsAccount.isChecked()) {
            accountTypes.add(checkBoxSavingsAccount.getText().toString());
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
        accountTypes = new ArrayList<>();
        checkBoxContractPageCheck.setChecked(isContractActivated);

        isSavedCustomer = false;

        phoneCustomView = new PhoneCustomView(view.getContext());
        radioGroupGender.setOnCheckedChangeListener(this);
        //checkBoxDepositAccount.setOnCheckedChangeListener(this);
        //checkBoxCheckingAccount.setOnCheckedChangeListener(this);
        //checkBoxFundAccount.setOnCheckedChangeListener(this);
        //checkBoxDollarGoldAccount.setOnCheckedChangeListener(this);
        //checkBoxDollarGoldAccount.setOnCheckedChangeListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener((datePicker, i, i1, i2) -> {

                date = i2 + "." + i1 + "." + i;
                datePicker.setVisibility(View.GONE);
                String birthdaytext = getResources().getString(R.string.birthday) + date;
                textViewbirthday.setText(birthdaytext);
                textViewbirthday.setVisibility(View.VISIBLE);

            });
        }

    }

    private void checkContractPageCheck() {

        checkBoxContractPageCheck.setChecked(isContractActivated);

    }

    private void takeArguments() {

        Bundle bundle = getArguments();
        if (bundle != null) {

            if (bundle.getString(KEY_CUSTOMER_ACCOUNT_NUMBER) != null) {

                String customerAccountNumberr = bundle.getString(KEY_CUSTOMER_ACCOUNT_NUMBER);
                String branchNumberr = bundle.getString(branchNumber);
                String branchNamee = bundle.getString(branchName);
                String balancee = bundle.getString(customerBalance);
                String currencyy = bundle.getString(currency);
                accountModel = new AccountModel(Integer.parseInt(customerAccountNumberr), branchNamee, Integer.parseInt(branchNumberr), Integer.parseInt(balancee), currencyy);


                textViewSelectedCustomerAccount.setVisibility(View.VISIBLE);
                textViewSelectedCustomerAccount.setText(branchNamee + "/" + balancee + currencyy);
            }
            if (bundle.getString("isContractActivated") != null) {
                isContractActivated = bundle.getBoolean("isContractActivated");
            }
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
