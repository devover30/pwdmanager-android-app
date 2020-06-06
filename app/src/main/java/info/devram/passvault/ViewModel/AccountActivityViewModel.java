package info.devram.passvault.ViewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import info.devram.passvault.Models.Accounts;
import info.devram.passvault.Repository.AccountsRepository;
import info.devram.passvault.controller.DatabaseHandler;

public class AccountActivityViewModel extends AndroidViewModel {

    private MutableLiveData<List<Accounts>> mAccountsLiveData;
    private Application application;

    public AccountActivityViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public void init() {
        if (mAccountsLiveData != null) {
            return;
        }
        AccountsRepository accountsRepository = new AccountsRepository(this
                .application.getApplicationContext());
        mAccountsLiveData = accountsRepository.getAll();
    }

    public LiveData<List<Accounts>> getAccounts() {
        return mAccountsLiveData;
    }
}
