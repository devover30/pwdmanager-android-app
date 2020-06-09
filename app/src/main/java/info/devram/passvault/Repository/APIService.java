package info.devram.passvault.Repository;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

public interface APIService<T> {

    public MutableLiveData<List<T>> getApiAccounts();

    public int getApiAccountsCount();

    public Boolean updateApiAccount(T obj);

    public Boolean addApiAccount(T obj);

    public Boolean deleteApiAccount(T obj);

}
