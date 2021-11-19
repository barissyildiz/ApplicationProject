package com.baris.applicationproject.ui.contract;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.baris.applicationproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContractFragment extends Fragment {

    String contractText;
    SpannableString spannableString;
    ClickableSpan clickableSpan;

    @BindView(R.id.textViewBringContractPage)
    TextView textViewBringContractPage;

    @BindView(R.id.switchBringContractPageKVKK)
    SwitchCompat switchBringContractPageKVKK;

    @BindView(R.id.switchBringContractBiometricData)
    SwitchCompat switchBringContractBiometricData;

    @OnClick(R.id.buttonContractPageConfirmationActive)
    public void activatedConfirmationPage() {

        if(switchBringContractPageKVKK.isChecked() && switchBringContractBiometricData.isChecked()) {

            Context mContext = getContext();
            Fragment fragment = ((FragmentActivity)mContext).getSupportFragmentManager().findFragmentByTag("createFragment");
            Bundle bundle = new Bundle();
            bundle.putBoolean("isContractActivated",true);
            fragment.setArguments(bundle);
            ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHome,fragment).commit();

        }else {

            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setMessage(getResources().getString(R.string.contractPageSwitchCheckText));
            dialog.setPositiveButton(getResources().getString(R.string.contractPageSwitchCheckAccept), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialog.show();
        }
    }

    @OnClick(R.id.buttonContractPageConfirmationPassive)
    public void rejectedConfirmationPage() {

        Context mContext = getContext();
        Fragment fragment = ((FragmentActivity)mContext).getSupportFragmentManager().findFragmentByTag("createFragment");
        Bundle bundle = new Bundle();
        bundle.putBoolean("isContractActivated",false);
        fragment.setArguments(bundle);
        ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHome,fragment).commit();

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_contract,container,false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this,view);

        contractText = getResources().getString(R.string.bringContractPageText);
        spannableString = new SpannableString(contractText);
        spannableString.setSpan(new ForegroundColorSpan(Color.BLUE),0,20,0);
        spannableString.setSpan(new UnderlineSpan(),0,20,0);

        textViewBringContractPage.setText(spannableString);

        clickSpannableString();

    }

    private void clickSpannableString() {

           clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {

                Toast.makeText(getContext(),"TIKLANDI",Toast.LENGTH_SHORT).show();

            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };
        spannableString.setSpan(clickableSpan,0,20,0);

    }
}
