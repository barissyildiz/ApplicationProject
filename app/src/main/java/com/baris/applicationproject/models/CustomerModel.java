package com.baris.applicationproject.models;

import android.net.Uri;

import java.io.Serializable;

public class CustomerModel implements Serializable {

    String customerName;
    String customerBirthday;
    String gender;
    String customerphonenumber;
    String customerPicture;

    public String getCustomerBirthday() {
        return customerBirthday;
    }

    public void setCustomerBirthday(String customerBirthday) {
        this.customerBirthday = customerBirthday;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCustomerphonenumber() {
        return customerphonenumber;
    }

    public void setCustomerphonenumber(String customerphonenumber) {
        this.customerphonenumber = customerphonenumber;
    }

    public String getCustomerPicture() {
        return customerPicture;
    }

    public void setCustomerPicture(String customerPicture) {
        this.customerPicture = customerPicture;
    }
}
