package info.devram.tizori;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import info.devram.tizori.Interfaces.RecyclerOnClick;
import info.devram.tizori.Models.Accounts;
import info.devram.tizori.UI.ConfirmDialog;
import info.devram.tizori.ViewModel.AccountActivityViewModel;
import info.devram.tizori.Adapters.RecyclerAccountAdapter;


public class AccountsActivity extends AppCompatActivity
        implements RecyclerOnClick, ConfirmDialog.ConfirmDialogListener {

    public static final String TAG = "AccountsActivity";

    public static final int REQUEST_CODE = 1;
    private int accountPosition;
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

                startActivityForResult(addActivity, REQUEST_CODE);


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
                accounts, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(AccountsActivity.this));

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(View view, final int position, final String accountType) {
        Log.d(TAG, "onItemClicked: " + position);
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view, Gravity.CENTER);

        popupMenu.getMenuInflater().inflate(R.menu.options_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.editMenuItem:
                        Intent intent = new Intent(AccountsActivity.this,
                                AccountDetail.class);

                        intent.putExtra("position", position);
                        intent.putExtra("type", accountType);

                        startActivityForResult(intent, REQUEST_CODE);
                        break;
                    case R.id.deleteMenuItem:
                        showDialog();
                        accountPosition = position;
                        break;
                    default:
                }
                return true;
            }
        });

        popupMenu.show();


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
                boolean isAccountEdited = data.getBooleanExtra("edit", false);

                if (isAccountEdited) {
                    updateRecyclerView();
                }
            }
        }

    }

    private void updateRecyclerView() {
        List<Accounts> newAccountsList = accountActivityViewModel
                .getAccounts().getValue();

        assert newAccountsList != null;
        displayAccounts(newAccountsList);
    }

    private void showDialog() {
        //private static final String TAG = "AccountDetail";
        DialogFragment confirmDialog = new ConfirmDialog();

        confirmDialog.show(getSupportFragmentManager(), null);
    }

    @Override
    public void onCancelClick(DialogFragment dialogFragment) {
        dialogFragment.dismiss();
    }

    @Override
    public void onOkClick(final DialogFragment dialogFragment) {
        accountActivityViewModel.deleteAccount(accountPosition);
        updateRecyclerView();
        dialogFragment.dismiss();
    }


}
