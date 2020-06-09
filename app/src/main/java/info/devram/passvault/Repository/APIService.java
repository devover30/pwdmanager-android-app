package info.devram.passvault.Repository;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

public interface APIService<T> {

    public MutableLiveData<List<T>> getAccounts();

    public int getAccountsCount();

    public Boolean addAccount(T obj);

    public Boolean updateAccount(T obj);

    public Boolean deleteAccount(T obj);
}
