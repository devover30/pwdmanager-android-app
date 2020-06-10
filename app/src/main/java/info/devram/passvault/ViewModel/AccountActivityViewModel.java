package info.devram.passvault.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import info.devram.passvault.Models.Accounts;
import info.devram.passvault.Repository.ApiAccountRepository;
import info.devram.passvault.Repository.DBAccountsRepository;
import info.devram.passvault.Repository.ApiResponseListener;


public class AccountActivityViewModel extends AndroidViewModel {

    private MutableLiveData<List<Accounts>> mAccountsLiveData;
    private Application application;
    private MutableLiveData<Boolean> mIsFetching;
    private DBAccountsRepository dbAccountsRepository;
    private ApiAccountRepository apiAccountRepository;
    List<Accounts> accountsList = new ArrayList<>();

    public AccountActivityViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        dbAccountsRepository = new DBAccountsRepository(this
                .application.getApplicationContext());
        apiAccountRepository = new ApiAccountRepository(this
                .application.getApplicationContext());
        mIsFetching = new MutableLiveData<>();

    }

    public LiveData<List<Accounts>> getAccounts() {
        if (mAccountsLiveData == null) {
            mAccountsLiveData = new MutableLiveData<>();
            loadAccounts();

        }
        return mAccountsLiveData;
    }

    private void loadAccounts() {

        final int dbRecordCount = dbAccountsRepository.getCount();

        mIsFetching.setValue(true);

        apiAccountRepository.getApiAccountsCount(new ApiAccountRepository.ApiResultListener() {
            @Override
            public void loadRecordCount(int recordCount) {
                if (dbRecordCount < recordCount) {
                    loadAccountFromApi();

                }else {
                    mIsFetching.postValue(false);
                }
            }
        });

        accountsList = dbAccountsRepository.getAll();

        mAccountsLiveData.setValue(accountsList);

    }

    private void loadAccountFromApi() {
        apiAccountRepository.getApiAccounts(new ApiResponseListener() {
            @Override
            public void getResult(List<Accounts> response) {
                mAccountsLiveData.setValue(response);
                mIsFetching.postValue(false);
                for (int i = 0; i < response.size(); i++) {
                    dbAccountsRepository.addData(response.get(i));
                }

            }
        });
    }

    public LiveData<Boolean> getIsFetching() {
        return mIsFetching;
    }
}
