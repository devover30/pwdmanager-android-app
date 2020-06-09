package info.devram.passvault.ViewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.AuthFailureError;

import java.util.List;

import info.devram.passvault.Models.Accounts;
import info.devram.passvault.Repository.AccountsRepository;


public class AccountActivityViewModel extends AndroidViewModel {

    private MutableLiveData<List<Accounts>> mAccountsLiveData;
    private Application application;
    private MutableLiveData<Boolean> mIsFetching;
    private AccountsRepository accountsRepository;

    public AccountActivityViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public void init() {
        if (mAccountsLiveData != null) {
            return;
        }
        accountsRepository = new AccountsRepository(this
                .application.getApplicationContext());

    }

    public LiveData<List<Accounts>> getAccounts() {
        return mAccountsLiveData;
    }

    public LiveData<List<Accounts>> getFromApi() {
        mIsFetching.setValue(true);


        if (mAccountsLiveData == null) {
            mAccountsLiveData = accountsRepository.getApiAccounts();
            mIsFetching.setValue(false);
        }

        return mAccountsLiveData;
    }

    public LiveData<Boolean> getIsFetching() {
        return mIsFetching;
    }
}
