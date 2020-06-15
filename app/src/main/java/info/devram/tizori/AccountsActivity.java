package info.devram.tizori;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

import info.devram.tizori.Adapters.RecyclerOnClick;
import info.devram.tizori.Models.Accounts;
import info.devram.tizori.ViewModel.AccountActivityViewModel;
import info.devram.tizori.Adapters.RecyclerAccountAdapter;


public class AccountsActivity extends AppCompatActivity implements RecyclerOnClick {

    //public static final String TAG = "AccountsActivity";

    public static final int REQUEST_CODE = 1;

    private RecyclerView recyclerView;
    private RecyclerAccountAdapter adapter;
    private AccountActivityViewModel accountActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        setTitle("DashBoard");

        accountActivityViewModel = new ViewModelProvider.AndroidViewModelFactory(
                getApplication()).create(AccountActivityViewModel.class);


        recyclerView = findViewById(R.id.accounts_recyclerView);


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




    }


    public void displayAccounts(List<Accounts> accounts) {
        adapter = new RecyclerAccountAdapter(this,
                accounts,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(AccountsActivity.this));

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(int position,String accountType) {

        Intent intent = new Intent(this,AccountDetail.class);

        intent.putExtra("position",position);
        intent.putExtra("type",accountType);

        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == 1) {
                assert data != null;
                boolean isNewAccount = data.getBooleanExtra("save", false);
                if (isNewAccount) {
                    updateRecyclerView();
                }
                boolean isAccountDeleted = data.getBooleanExtra("delete",false);

                if (isAccountDeleted) {
                    updateRecyclerView();
                }
            }
        }

    }

    private void updateRecyclerView() {
        List<Accounts> newAccountsList = accountActivityViewModel
                .getAccounts().getValue();

        assert newAccountsList != null;

        if (newAccountsList.size() == 1) {
            displayAccounts(newAccountsList);
        }else if(newAccountsList.size() > 0) {
            adapter.addData(newAccountsList);
        }else {
            adapter.clearData();
        }

    }
}
