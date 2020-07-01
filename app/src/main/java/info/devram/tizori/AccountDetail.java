package info.devram.tizori;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import info.devram.tizori.Models.Accounts;
import info.devram.tizori.ViewModel.AccountActivityViewModel;

public class AccountDetail extends AppCompatActivity implements
        View.OnClickListener {

    private AccountActivityViewModel accountActivityViewModel;
    private EditText accountNameDetail;
    private EditText accountLoginIdDetail;
    private EditText accountLoginPwdDetail;
    private Button editButton;
    private Accounts account;
    int accountPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);

        String accType = getIntent().getStringExtra("type");

        accType = accType.substring(0, 1).toUpperCase() + accType.substring(1);

        setTitle(accType);

        accountActivityViewModel = new ViewModelProvider.AndroidViewModelFactory(
                getApplication()).create(AccountActivityViewModel.class);

        accountPosition = getIntent().getIntExtra("position", 0);

        account = accountActivityViewModel.getAccounts().getValue().get(accountPosition);

        accountNameDetail = findViewById(R.id.acc_name_det_txt_view);
        accountLoginIdDetail = findViewById(R.id.login_id_det_txt_view);
        accountLoginPwdDetail = findViewById(R.id.login_pwd_det_txt_view);
        editButton = findViewById(R.id.edit_btn);


        editButton.setOnClickListener(this);


        accountNameDetail.setText(account.getAccountName());

        accountLoginIdDetail.setText(account.getLoginId());
        accountLoginPwdDetail.setText(account.getLoginPwd());


//        String date = accountsList.get(position).getCreatedDate();
//        String newDate = parseDate(date);
//        accountDateDetail.setText(newDate);
    }

//    private String parseDate(String created_date) {
//        if (created_date.contains("00")) {
//            int index = created_date.indexOf("00");
//            created_date = created_date.substring(0,index).trim();
//        }
//
//        return created_date;
//    }

    @Override
    public void onClick(View v) {
        account.setAccountName(accountNameDetail.getText().toString());
        account.setLoginId(accountLoginIdDetail.getText().toString());
        account.setLoginPwd(accountLoginPwdDetail.getText().toString());

        accountActivityViewModel.editAccount(accountPosition,account);

        Intent resultIntent = getIntent();
        resultIntent.putExtra("edit",true);
        setResult(1,resultIntent);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },2000);
    }
}