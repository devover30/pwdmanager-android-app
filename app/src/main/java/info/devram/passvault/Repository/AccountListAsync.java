package info.devram.passvault.Repository;

import info.devram.passvault.Models.Accounts;

import java.util.ArrayList;

public abstract class AccountListAsync {
    public void processFinished(ArrayList<Accounts> accountsList){}

    public void getApiRecords(int apiRecords){}
}
