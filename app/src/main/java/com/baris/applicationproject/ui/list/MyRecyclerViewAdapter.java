package com.baris.applicationproject.ui.list;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.baris.applicationproject.R;
import com.baris.applicationproject.models.CustomerModel;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    List<CustomerModel> customerList;

    public MyRecyclerViewAdapter(List<CustomerModel> customerList) {

        this.customerList = customerList;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new MyRecyclerViewAdapter.MyViewHolder(inflater.inflate(R.layout.recyclerview_item_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.textViewcustomername.setText(customerList.get(position).getCustomerName());
        holder.textViewcustomergender.setText(customerList.get(position).getGender());
        holder.imageViewcustomer.setImageURI(Uri.parse(customerList.get(position).getCustomerPicture()));

        if(customerList.get(position).getGender().equalsIgnoreCase("ERKEK")) {

            holder.linearLayout.setBackgroundColor(holder.linearLayout.getResources().getColor(R.color.blue));
        }else {

            holder.linearLayout.setBackgroundColor(holder.linearLayout.getResources().getColor(R.color.green));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(),holder.textViewcustomername.getText().toString() +" \n "+ customerList.get(position).getCustomerBirthday(),Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewcustomername,textViewcustomergender;
        ImageView imageViewcustomer;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewcustomername = itemView.findViewById(R.id.textViewcustomername);
            textViewcustomergender = itemView.findViewById(R.id.textViewcustomergender);
            imageViewcustomer = itemView.findViewById(R.id.imageViewcustomerimage);
            linearLayout = itemView.findViewById(R.id.linearLayoutRecycleritem);
        }
    }
}
