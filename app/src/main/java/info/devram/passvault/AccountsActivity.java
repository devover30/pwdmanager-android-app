package info.devram.passvault;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;


import info.devram.passvault.Adapters.RecyclerOnClick;
import info.devram.passvault.ViewModel.AccountActivityViewModel;
import info.devram.passvault.Adapters.RecyclerAccountAdapter;


public class AccountsActivity extends AppCompatActivity  implements RecyclerOnClick {

    public static final String TAG = "AccountsActivity";

    private RecyclerView recyclerView;
    private RecyclerAccountAdapter adapter;
    private SharedPreferences.Editor editor;
    private AccountActivityViewModel accountActivityViewModel;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        accountActivityViewModel = new ViewModelProvider.AndroidViewModelFactory(
                getApplication()).create(AccountActivityViewModel.class);


        recyclerView = findViewById(R.id.accounts_recyclerView);

        progressBar = findViewById(R.id.progress_bar);

        accountActivityViewModel.getIsFetching().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {
                    progressBar.setVisibility(View.VISIBLE);

                }else {
                    displayAccounts();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

        displayAccounts();

    }

    public void displayAccounts() {

        adapter = new RecyclerAccountAdapter(this,
                accountActivityViewModel.getAccounts().getValue(),this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AccountsActivity.this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(int position) {

        Intent intent = new Intent(this,AccountDetail.class);

        intent.putExtra("position",position);

        startActivity(intent);
    }
}
