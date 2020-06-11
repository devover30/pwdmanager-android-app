package info.devram.passvault.Repository;

import java.util.List;

import info.devram.passvault.Models.Accounts;

public interface ApiResponseListener {

    public void getResult(List<Accounts> response);
}
