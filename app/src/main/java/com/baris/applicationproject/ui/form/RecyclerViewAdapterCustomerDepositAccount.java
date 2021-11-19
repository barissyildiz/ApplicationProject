package com.baris.applicationproject.ui.form;

import static com.baris.applicationproject.ui.form.CreateFormFragment.KEY_CUSTOMER_ACCOUNT_NUMBER;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.baris.applicationproject.R;
import com.baris.applicationproject.models.AccountModel;

import java.util.ArrayList;

public class RecyclerViewAdapterCustomerDepositAccount extends RecyclerView.Adapter<RecyclerViewAdapterCustomerDepositAccount.MyViewHolder> {

    ArrayList<AccountModel> accountModels;
    String branchNumber = "branchNumber";
    String branchName = "branchName";
    String customerBalance = "customerBalance";
    String currency = "currency";

    public RecyclerViewAdapterCustomerDepositAccount(ArrayList<AccountModel> accountModels) {

        this.accountModels = accountModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recylerview_item_deposit_account, parent, false);
        return new RecyclerViewAdapterCustomerDepositAccount.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.textViewCustomerAccountNumber.setText(String.valueOf(accountModels.get(position).getCustomerAccountNumber()));
        holder.textViewBranchNumber.setText(String.valueOf(accountModels.get(position).getBranchNumber()));
        holder.textViewBranchName.setText(accountModels.get(position).getBranchName());
        holder.textViewCustomerBalance.setText(String.valueOf(accountModels.get(position).getBalanceOfTheAccount()));
        holder.textViewCurrency.setText(accountModels.get(position).getCurrency());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context mContext = holder.textViewBranchName.getContext();
                Fragment fragment = ((FragmentActivity) mContext).getSupportFragmentManager().findFragmentByTag("createFragment");
                Bundle bundle = new Bundle();
                bundle.putString(KEY_CUSTOMER_ACCOUNT_NUMBER, holder.textViewCustomerAccountNumber.getText().toString());
                bundle.putString(branchNumber, holder.textViewBranchNumber.getText().toString());
                bundle.putString(branchName, holder.textViewBranchName.getText().toString());
                bundle.putString(customerBalance, holder.textViewCustomerBalance.getText().toString());
                bundle.putString(currency, holder.textViewCurrency.getText().toString());
                fragment.setArguments(bundle);

                ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentHome, fragment)
                        .commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return accountModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView imageViewStar;
        TextView textViewCustomerAccountNumber, textViewBranchNumber, textViewBranchName, textViewCustomerBalance, textViewCurrency;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewStar = itemView.findViewById(R.id.recyclerViewImageViewStar);
            textViewCustomerAccountNumber = itemView.findViewById(R.id.recyclerViewItemCustomerAccountNumber);
            textViewBranchNumber = itemView.findViewById(R.id.recyclerViewItemBranchNumber);
            textViewBranchName = itemView.findViewById(R.id.recyclerViewBranchPlace);
            textViewCustomerBalance = itemView.findViewById(R.id.recyclerViewItemCustomerBalance);
            textViewCurrency = itemView.findViewById(R.id.currency);
        }
    }
}
