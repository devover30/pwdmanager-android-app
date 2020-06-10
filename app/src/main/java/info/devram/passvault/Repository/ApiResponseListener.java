package info.devram.passvault.Repository;

import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;

import java.util.List;

import info.devram.passvault.Models.Accounts;

public interface ApiResponseListener {

    public void getResult(List<Accounts> response);
}
