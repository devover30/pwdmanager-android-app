package info.devram.tizori.Interfaces;

import java.util.List;

import info.devram.tizori.Models.Accounts;

public interface ApiResponseListener {

    public void getResult(List<Accounts> response);
}
