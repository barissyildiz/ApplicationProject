package com.baris.applicationproject.models;

public class AccountModel {

    int customerAccountNumber;
    String branchName;
    int branchNumber;
    int balanceOfTheAccount;
    String currency;

    public AccountModel(int customerAccountNumber,String branchName,int branchNumber,int balanceOfTheAccount,String currency) {
        this.customerAccountNumber = customerAccountNumber;
        this.branchName = branchName;
        this.branchNumber = branchNumber;
        this.balanceOfTheAccount = balanceOfTheAccount;
        this.currency = currency;
    }

    public int getCustomerAccountNumber() {
        return customerAccountNumber;
    }

    public void setCustomerAccountNumber(int customerAccountNumber) {
        this.customerAccountNumber = customerAccountNumber;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public int getBranchNumber() {
        return branchNumber;
    }

    public void setBranchNumber(int branchNumber) {
        this.branchNumber = branchNumber;
    }

    public int getBalanceOfTheAccount() {
        return balanceOfTheAccount;
    }

    public void setBalanceOfTheAccount(int balanceOfTheAccount) {
        this.balanceOfTheAccount = balanceOfTheAccount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


}
