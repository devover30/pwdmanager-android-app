package info.devram.tizori;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.List;

import info.devram.tizori.Models.Accounts;
import info.devram.tizori.UI.ConfirmDialog;
import info.devram.tizori.ViewModel.AccountActivityViewModel;

public class AccountDetail extends AppCompatActivity implements
        View.OnClickListener, ConfirmDialog.ConfirmDialogListener {

    //private static final String TAG = "AccountDetail";
    private DialogFragment confirmDialog;
    private List<Accounts> accountsList;
    private AccountActivityViewModel accountActivityViewModel;
    int accountPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);

        String accType = getIntent().getStringExtra("type");

        accType = accType.substring(0,1).toUpperCase() + accType.substring(1);

        setTitle(accType);

        accountActivityViewModel = new ViewModelProvider.AndroidViewModelFactory(
                getApplication()).create(AccountActivityViewModel.class);

         accountsList = accountActivityViewModel.getAccounts().getValue();

        accountPosition = getIntent().getIntExtra("position",0);


        EditText accountNameDetail = findViewById(R.id.acc_name_det_txt_view);
        EditText accountLoginIdDetail = findViewById(R.id.login_id_det_txt_view);
        EditText accountLoginPwdDetail = findViewById(R.id.login_pwd_det_txt_view);
        Button editButton = findViewById(R.id.edit_btn);
        Button deleteButton = findViewById(R.id.delete_btn);

        editButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);


        accountNameDetail.setText(accountsList.get(accountPosition).getAccountName());

        accountLoginIdDetail.setText(accountsList.get(accountPosition).getLoginId());
        accountLoginPwdDetail.setText(accountsList.get(accountPosition).getLoginPwd());



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
        switch (v.getId()) {
            case R.id.edit_btn:
                break;
            case R.id.delete_btn:
                showDialog();
        }
    }

    private void showDialog() {
        confirmDialog = new ConfirmDialog();

        confirmDialog.show(getSupportFragmentManager(),null);
    }

    @Override
    public void onCancelClick(DialogFragment dialogFragment) {
        dialogFragment.dismiss();
    }

    @Override
    public void onOkClick(DialogFragment dialogFragment) {
        dialogFragment.dismiss();
        accountActivityViewModel.deleteAccount(accountPosition);
        Intent resultIntent = getIntent();
        resultIntent.putExtra("delete",true);
        setResult(1,resultIntent);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },2000);
    }
}