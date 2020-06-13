package info.devram.tizori;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import info.devram.tizori.Adapters.RecyclerOnClick;
import info.devram.tizori.Models.Accounts;
import info.devram.tizori.ViewModel.AccountActivityViewModel;
import info.devram.tizori.Adapters.RecyclerAccountAdapter;


public class AccountsActivity extends AppCompatActivity implements RecyclerOnClick {

    public static final String TAG = "AccountsActivity";

    public static final int REQUEST_CODE = 1;

    private RecyclerView recyclerView;
    private RecyclerAccountAdapter adapter;
    private AccountActivityViewModel accountActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        accountActivityViewModel = new ViewModelProvider.AndroidViewModelFactory(
                getApplication()).create(AccountActivityViewModel.class);


        recyclerView = findViewById(R.id.accounts_recyclerView);

        final ProgressBar progressBar = findViewById(R.id.progress_bar);
        final FloatingActionButton accountAddFab = findViewById(R.id.fab_add_btn);


        accountAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent addActivity = new Intent(AccountsActivity.this,
                        AddAccount.class);

                startActivityForResult(addActivity,REQUEST_CODE);


            }
        });

        accountActivityViewModel.getAccounts().observe(this, new Observer<List<Accounts>>() {
            @Override
            public void onChanged(List<Accounts> accounts) {

                if (accounts.size() > 0) {
                    displayAccounts(accounts);

                }
            }
        });



        accountActivityViewModel.getIsUpdating().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);

                }else {

                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);

                }
            }
        });
    }


    public void displayAccounts(List<Accounts> accounts) {
        adapter = new RecyclerAccountAdapter(this,
                accounts,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(AccountsActivity.this));

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(int position) {

        Intent intent = new Intent(this,AccountDetail.class);

        intent.putExtra("position",position);

        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == 1) {
                assert data != null;
                boolean isNewAccount = data.getBooleanExtra("save", false);
                if (isNewAccount) {
                    List<Accounts> newAccountsList = accountActivityViewModel
                            .getAccounts().getValue();

                    assert newAccountsList != null;
                    if (newAccountsList.size() == 1) {
                        displayAccounts(newAccountsList);
                    }else {
                        adapter.addData(newAccountsList);
                    }

                }
            }
        }

    }


}
