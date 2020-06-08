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
import info.devram.passvault.ViewModel.AccountActivityViewModel;
import info.devram.passvault.Adapters.RecyclerAccountAdapter;

import java.util.List;

public class AccountsActivity extends AppCompatActivity {

    public static final String TAG = "AccountsActivity";

    private int dbrecords;
    private SharedPreferences sharedPreferences;
    private String token;
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

        sharedPreferences = getSharedPreferences(
                String.valueOf(R.string.shared_pref),MODE_PRIVATE
        );

        token = sharedPreferences.getString("token","No Token");

//        new AccountsHandler()
//                .getRecords(
//                        token,
//                        getApplicationContext(),
//                        new AccountListAsync() {
//                            @Override
//                            public void getApiRecords(int apiRecords) {
//
//                                dbrecords = db.getCount();
//
//                                if (apiRecords < dbrecords || apiRecords > dbrecords) {
//                                    getRecords();
//                                }
//
//                            }
//
//
//                        }
//                );

        recyclerView = findViewById(R.id.accounts_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AccountsActivity.this));

        accountActivityViewModel.getAccounts().observe(this,
                new Observer<List<Accounts>>() {
            @Override
            public void onChanged(List<Accounts> accounts) {
                Log.d(TAG, "onChanged: " + accounts.size());
                displayAccounts(accounts);
            }
        });


    }

    public void displayAccounts(List<info.devram.passvault.Models.Accounts> accountList) {

        adapter = new RecyclerAccountAdapter(AccountsActivity.this,accountList);

        recyclerView.setAdapter(adapter);
    }

//    public void getRecords() {
//        new AccountsHandler()
//                .getAccounts(token,getApplicationContext(),
//                        new AccountListAsync() {
//                    @Override
//                    public void processFinished(ArrayList<info.devram.passvault.Models.Accounts> accountsList) {
//                        displayAccounts(accountsList);
//                        for (int i = 0; i < accountsList.size(); i++) {
//                            db.addAccount(accountsList.get(i));
//                        }
//
//                    }
//                });
//
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor = sharedPreferences.edit();
        editor.remove("token");
        editor.apply();
    }
}
