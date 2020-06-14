package info.devram.tizori;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

import info.devram.tizori.Models.Accounts;
import info.devram.tizori.ViewModel.AccountActivityViewModel;

public class AddAccount extends AppCompatActivity {

    //private static final String TAG = "AddAccount";

    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private AccountActivityViewModel accountActivityViewModel;
    private EditText nameEditText;
    private EditText loginIdEditText;
    private EditText loginPwdEditText;
    private Boolean isAccountSaved;
    private ViewGroup addAccountGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        isAccountSaved = false;

        setTitle("Add Account");

        accountActivityViewModel = new ViewModelProvider.AndroidViewModelFactory(
                getApplication()).create(AccountActivityViewModel.class);

        spinner = findViewById(R.id.spinner);
        Button accountSaveButton = findViewById(R.id.save_account_btn);
        nameEditText = findViewById(R.id.name_edit_txt);
        loginIdEditText = findViewById(R.id.login_id_edit_txt);
        loginPwdEditText = findViewById(R.id.login_pwd_edit_txt);

        addAccountGroup = (ViewGroup) findViewById(R.id.add_account_group);

        adapter = ArrayAdapter.createFromResource(this,
                R.array.expense_type,android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        accountSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Accounts account = new Accounts();

                Boolean isValidated = validateData();

                if (isValidated) {

                    String accountType = parseData(parseData(String.valueOf(adapter.getItem(
                    spinner.getSelectedItemPosition()))));

                    account.setType(accountType);

                    account.setAccountName(parseData(nameEditText.getText().toString()));
                    account.setLoginId(parseData(loginIdEditText.getText().toString()));
                    account.setLoginPwd(parseData(loginPwdEditText.getText().toString()));

                    SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("E, dd MMM yyyy");

                    Date nowDate = new Date();

                    String created_date = dateTimeFormatter.format(nowDate);
                    account.setCreatedDate(created_date);

                    accountActivityViewModel.addAccount(account);

                    isAccountSaved = true;
                    setIntent();
                    finish();
                }


            }
        });
    }

    private Boolean validateData() {
        for (int i = 0; i < addAccountGroup.getChildCount() ; i++) {
            View view = addAccountGroup.getChildAt(i);

            if (view instanceof TextInputLayout) {
                if (((TextInputLayout) view).getEditText().getText().toString().equals("")) {
                    ((TextInputLayout) view).getEditText().requestFocus();
                    ((TextInputLayout) view).getEditText().setError("this can't be null");
                    return false;
                }
            }
        }
        return true;
    }

    private String parseData(String data){

        data = data.trim();

        data = data.toLowerCase();

        return data;
    }

    private void setIntent() {
        Intent resultIntent = getIntent();
        resultIntent.putExtra("save",isAccountSaved);
        setResult(1,resultIntent);

    }

    @Override
    public void onBackPressed() {
        setIntent();
        super.onBackPressed();
    }
}