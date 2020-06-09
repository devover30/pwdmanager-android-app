package info.devram.passvault;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import info.devram.passvault.Models.Accounts;
import info.devram.passvault.Repository.AccountsRepository;
import info.devram.passvault.ViewModel.AccountActivityViewModel;
import info.devram.passvault.Adapters.RecyclerAccountAdapter;

import java.util.ArrayList;
import java.util.List;

public class AccountsActivity extends AppCompatActivity {

    public static final String TAG = "AccountsActivity";

    private RecyclerView recyclerView;
    private RecyclerAccountAdapter adapter;
    private SharedPreferences.Editor editor;
    AccountActivityViewModel accountActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        accountActivityViewModel = new ViewModelProvider.AndroidViewModelFactory(
                getApplication()).create(AccountActivityViewModel.class);


        accountActivityViewModel.init();

        recyclerView = findViewById(R.id.accounts_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AccountsActivity.this));


        accountActivityViewModel.getIsFetching().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean) {
                    displayAccounts();
                }
            }
        });




    }

    public void displayAccounts() {

        accountActivityViewModel.getFromApi().observe(this, new Observer<List<Accounts>>() {
            @Override
            public void onChanged(List<Accounts> accounts) {
                adapter = new RecyclerAccountAdapter(AccountsActivity.this, accounts);
            }
        });



        recyclerView.setAdapter(adapter);
    }


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        editor = sharedPreferences.edit();
//        editor.remove("token");
//        editor.apply();
//    }
}
