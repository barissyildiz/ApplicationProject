package com.baris.applicationproject.ui.confirm;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.baris.applicationproject.R;
import com.baris.applicationproject.models.AccountModel;
import com.baris.applicationproject.models.CustomerModel;
import com.baris.applicationproject.ui.form.CreateFormFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.baris.applicationproject.ui.form.CreateFormFragment.KEY_CUSTOMER_ACCOUNT_INFO;
import static com.baris.applicationproject.ui.form.CreateFormFragment.KEY_CUSTOMER_ACCOUNT_NUMBER;
import static com.baris.applicationproject.ui.form.CreateFormFragment.KEY_CUSTOMER_ACCOUNT_TYPES;
import static com.baris.applicationproject.ui.form.CreateFormFragment.KEY_CUSTOMER_INFO;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class ConfirmationFragment extends Fragment {

    CustomerModel customerModel;
    AccountModel accountModel;
    ArrayList<CustomerModel> customerModelsList;
    String costumerAccountTypes = "";

    @BindView(R.id.textViewConfirmationCustomerNameAndSurname)
    TextView textViewCustomerNameAndSurname;
    
    @BindView(R.id.textViewConfirmationCustomerBirthday)
    TextView textViewCustomerBirthday;
    
    @BindView(R.id.textViewConfirmationCustomerAccountInfo)
    TextView textViewCustomerAccountInfo;
    
    @BindView(R.id.textViewConfirmationCustomerGender)
    TextView textViewCustomerGender;
    
    @BindView(R.id.textViewConfirmationCustomerPhoneNumber)
    TextView textViewCustomerPhoneNumber;
    
    @BindView(R.id.textViewConfirmationCustomerAccountType)
    TextView textViewCustomerAccountType;

    @BindView(R.id.imageViewConfirmationCustomerPicture)
    ImageView imageViewCustomerPicture;
    
    
    @OnClick(R.id.buttonConfirmationCustomerFinish)
    public void customerconfirmationfinish() {

        loadData();
        saveData(customerModel);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentManager.popBackStack();
        Fragment fragment = fragmentManager.findFragmentByTag("listfragment");
        fragmentTransaction.replace(R.id.fragmentHome,fragment).commit();
    }
    
    @OnClick(R.id.buttonConfirmationCustomerGiveup)
    public void costumerConfirmationGiveup() {

        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("createFragment");
        if (fragment != null) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHome,fragment).commit();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_confirmation,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        takeArguments();
        setView();
    }

    private void setView() {

        textViewCustomerNameAndSurname.setText(customerModel.getCustomerName());
        textViewCustomerBirthday.setText(customerModel.getCustomerBirthday());
        textViewCustomerGender.setText(customerModel.getGender());
        textViewCustomerPhoneNumber.setText(customerModel.getCustomerphonenumber());
        imageViewCustomerPicture.setImageURI(Uri.parse(customerModel.getCustomerPicture().toString()));
        textViewCustomerAccountType.setText(costumerAccountTypes);
        String accountInfo = accountModel.getCustomerAccountNumber() + "\n" + accountModel.getBranchNumber() + " " + accountModel.getBranchName() + "\n" + accountModel.getBalanceOfTheAccount() + accountModel.getCurrency();
        textViewCustomerAccountInfo.setText(accountInfo);

    }

    public void takeArguments() {
        
        Bundle bundle = getArguments();
        customerModel = new CustomerModel();
        accountModel = new AccountModel();
        customerModel = (CustomerModel) (bundle != null ? bundle.getSerializable(KEY_CUSTOMER_INFO) : null);
        accountModel = (AccountModel) (bundle != null ? bundle.getSerializable(KEY_CUSTOMER_ACCOUNT_INFO) : null);
        ArrayList<String> accountTypesList = bundle.getStringArrayList(KEY_CUSTOMER_ACCOUNT_TYPES);
        for (String element : accountTypesList) {

            costumerAccountTypes = costumerAccountTypes + element;
            costumerAccountTypes = costumerAccountTypes + " / ";
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
}
