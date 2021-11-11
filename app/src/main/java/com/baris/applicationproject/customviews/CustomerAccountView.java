package com.baris.applicationproject.customviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.baris.applicationproject.R;
import com.baris.applicationproject.models.AccountModel;
import com.baris.applicationproject.ui.form.RecyclerViewAdapterCustomerDepositAccount;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomerAccountView extends LinearLayout {

    RecyclerViewAdapterCustomerDepositAccount recyclerViewAdapterCustomerDepositAccount;
    ArrayList<AccountModel> accountModels;
    boolean isDepositAccountOpen = false;

    @BindView(R.id.recyclerViewCostumerDepositAccount)
    RecyclerView recyclerViewCustomerDepositAccount;

    //@BindView(R.id.recyclerViewDepositAccountArrow)
    //ImageView recyclerViewDepositAccountArrow;

    @SuppressLint("UseCompatLoadingForDrawables")
    @OnClick(R.id.relativeLayoutVadeliHesaplar)
    public void openDepositAccounts() {


        //recyclerViewCustomerDepositAccount.setVisibility(VISIBLE);

        if (isDepositAccountOpen) {
            isDepositAccountOpen = false;
            recyclerViewCustomerDepositAccount.setVisibility(GONE);
            //recyclerViewDepositAccountArrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_closearrow));

        }else {
            isDepositAccountOpen = true;
            recyclerViewCustomerDepositAccount.setVisibility(View.VISIBLE);
            //recyclerViewDepositAccountArrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_openarrow));
        }

    }

    public CustomerAccountView(Context context) {
        super(context);
        init(context);

    }

    public CustomerAccountView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomerAccountView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CustomerAccountView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.custom_accountview,this);
        ButterKnife.bind(this);
        //setAccountModels();
        //recyclerViewAdapterCustomerDepositAccount = new RecyclerViewAdapterCustomerDepositAccount(accountModels);
        //recyclerViewCustomerDepositAccount.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        //recyclerViewCustomerDepositAccount.setAdapter(recyclerViewAdapterCustomerDepositAccount);
    }

    private void setAccountModels() {

        accountModels = new ArrayList<>();
        accountModels.add(new AccountModel(1324452,"GAZİ ÜNİVERSİTESİ ŞB./ANKARA",7426,500,"TL"));
        accountModels.add(new AccountModel(8210543,"İSTANBUL ÜNİVERSİTESİ ŞB./İSTANBUL",4578,650,"TL"));
        accountModels.add(new AccountModel(5638953,"SAHRAYICEDİT ŞB./İSTANBUL",5793,1200,"TL"));
        accountModels.add(new AccountModel(6943246,"GÖLBAŞI ŞB/ANKARA",5739,4568,"TL"));
        accountModels.add(new AccountModel(2468923,"ELMADAĞ ŞB./ANKARA",4721,7632,"TL"));
        accountModels.add(new AccountModel(2214467,"MEZİTLİ ŞB./MERSİN",3276,3278,"TL"));
        accountModels.add(new AccountModel(7896421,"FATSA ŞB./ORDU",5682,9658,"TL"));
        accountModels.add(new AccountModel(5683257,"PENDİK GÜZELYALI ŞB./İSTANBUL",4876,15654,"TL"));

    }
}
