package info.devram.passvault.Repository;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

public interface APIService<T> {

    public void getApiAccounts();

    public void getApiAccountsCount();

    public void updateApiAccount(T obj);

    public void addApiAccount(T obj);

    public void deleteApiAccount(T obj);

}
