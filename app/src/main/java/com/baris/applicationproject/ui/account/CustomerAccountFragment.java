package com.baris.applicationproject.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baris.applicationproject.R;
import com.baris.applicationproject.models.AccountModel;
import com.baris.applicationproject.ui.form.RecyclerViewAdapterCustomerDepositAccount;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerAccountFragment extends Fragment {

    ArrayList<AccountModel> accountModels;
    RecyclerViewAdapterCustomerDepositAccount recyclerViewAdapterCustomerDepositAccount;

    @BindView(R.id.recyclerViewCostumerDepositAccount)
    RecyclerView recyclerViewCustomerAccount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_customer_account,container,false);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        setModels();
        setAdapter();
        recyclerViewAdapterCustomerDepositAccount = new RecyclerViewAdapterCustomerDepositAccount(accountModels);
        recyclerViewCustomerAccount.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewCustomerAccount.getContext(),LinearLayoutManager.VERTICAL);
        recyclerViewCustomerAccount.setAdapter(recyclerViewAdapterCustomerDepositAccount);
        recyclerViewCustomerAccount.addItemDecoration(dividerItemDecoration);

    }

    private void setModels() {

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

    private void setAdapter() {


    }


}
