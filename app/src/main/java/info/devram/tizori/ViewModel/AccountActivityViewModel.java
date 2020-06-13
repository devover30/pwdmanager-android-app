package info.devram.tizori.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import info.devram.tizori.Models.Accounts;
import info.devram.tizori.Repository.ApiAccountRepository;
import info.devram.tizori.Repository.DBAccountsRepository;
import info.devram.tizori.Repository.ApiResponseListener;


public class AccountActivityViewModel extends AndroidViewModel {

    private MutableLiveData<List<Accounts>> mAccountsLiveData;
    private Application application;
    private MutableLiveData<Boolean> mIsUpdating;
    private DBAccountsRepository dbAccountsRepository;
    private ApiAccountRepository apiAccountRepository;
    private List<Accounts> accountsList;

    public AccountActivityViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        dbAccountsRepository = new DBAccountsRepository(this
                .application.getApplicationContext());
        apiAccountRepository = new ApiAccountRepository(this
                .application.getApplicationContext());
        mIsUpdating = new MutableLiveData<>();
        mAccountsLiveData = new MutableLiveData<>();
        accountsList = new ArrayList<>();
    }


    public LiveData<List<Accounts>> getAccounts() {

        mIsUpdating.setValue(true);

        final int dbRecordCount = dbAccountsRepository.getCount();

        if (dbRecordCount == 0) {
            loadAccountFromApi();
        }else {
            accountsList = dbAccountsRepository.getAll();

            mAccountsLiveData.setValue(accountsList);
            mIsUpdating.postValue(false);
        }

        return mAccountsLiveData;

    }

    private void loadAccountFromApi() {

        apiAccountRepository.getApiAccounts(new ApiResponseListener() {
            @Override
            public void getResult(List<Accounts> response) {

                mAccountsLiveData.setValue(response);

                for (int i = 0; i < response.size(); i++) {
                    dbAccountsRepository.addData(response.get(i));
                }
                mIsUpdating.postValue(false);
            }
        });
    }

    public LiveData<Boolean> getIsUpdating() {
        return mIsUpdating;
    }

    public void addAccount(Accounts account) {
        mIsUpdating.setValue(true);

        try {

            if (mAccountsLiveData == null && accountsList == null) {
                accountsList = mAccountsLiveData.getValue();
                assert accountsList != null;
                accountsList.add(account);
                mAccountsLiveData.postValue(accountsList);
            }else {
                assert accountsList != null;
                accountsList.add(account);
                mAccountsLiveData.setValue(accountsList);
            }

        }catch (Exception e) {
            e.printStackTrace();

        }

        Boolean dbOperationResult = dbAccountsRepository.addData(account);

        if (dbOperationResult) {
            mIsUpdating.setValue(false);
        }

    }
}
