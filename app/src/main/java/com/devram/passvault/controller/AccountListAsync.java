package com.devram.passvault.controller;

import com.devram.passvault.model.AccountsModel;

import java.util.ArrayList;

public abstract class AccountListAsync {
    public void processFinished(ArrayList<AccountsModel> accountsList){}

    public void getApiRecords(int apiRecords){}
}
