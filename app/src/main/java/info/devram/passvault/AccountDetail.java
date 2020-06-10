package info.devram.passvault;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import info.devram.passvault.Models.Accounts;
import info.devram.passvault.ViewModel.AccountActivityViewModel;

public class AccountDetail extends AppCompatActivity {

    private static final String TAG = "AccountDetail";

    private AccountActivityViewModel accountActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);

        accountActivityViewModel = new ViewModelProvider.AndroidViewModelFactory(
                getApplication()).create(AccountActivityViewModel.class);

        List<Accounts> accountsList = accountActivityViewModel.getAccounts().getValue();

        Log.i(TAG, "onCreate: " + accountsList);

        Log.i(TAG, "onCreate: " + getIntent().getIntExtra("position",0));
    }
}