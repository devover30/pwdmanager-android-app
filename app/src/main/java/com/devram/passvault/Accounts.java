package com.devram.passvault;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;


import com.devram.passvault.controller.AccountListAsync;
import com.devram.passvault.controller.AccountsHandler;
import com.devram.passvault.controller.DatabaseHandler;
import com.devram.passvault.core.RecyclerAccountAdapter;
import com.devram.passvault.model.AccountsModel;


import java.util.ArrayList;
import java.util.List;

public class Accounts extends AppCompatActivity {

    private int dbrecords;
    private SharedPreferences sharedPreferences;
    private String token;
    private DatabaseHandler db;
    private RecyclerView recyclerView;
    private RecyclerAccountAdapter adapter;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
        db = new DatabaseHandler(getApplicationContext());
        sharedPreferences = getSharedPreferences(
                String.valueOf(R.string.shared_pref),MODE_PRIVATE
        );

        token = sharedPreferences.getString("token","No Token");

        new AccountsHandler()
                .getRecords(
                        token,
                        getApplicationContext(),
                        new AccountListAsync() {
                            @Override
                            public void getApiRecords(int apiRecords) {

                                dbrecords = db.getCount();

                                if (apiRecords < dbrecords || apiRecords > dbrecords) {
                                    getRecords();
                                }

                            }


                        }
                );

        recyclerView = findViewById(R.id.accounts_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Accounts.this));

        displayAccounts(db.getAllAccounts());

    }

    public void displayAccounts(List<AccountsModel> accountList) {

        adapter = new RecyclerAccountAdapter(Accounts.this,accountList);

        recyclerView.setAdapter(adapter);
    }

    public void getRecords() {
        new AccountsHandler()
                .getAccounts(token,getApplicationContext(),
                        new AccountListAsync() {
                    @Override
                    public void processFinished(ArrayList<AccountsModel> accountsList) {
                        displayAccounts(accountsList);
                        for (int i = 0; i < accountsList.size(); i++) {
                            db.addAccount(accountsList.get(i));
                        }

                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor = sharedPreferences.edit();
        editor.remove("token");
        editor.apply();
    }
}
