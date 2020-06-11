package info.devram.passvault;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import java.util.List;

import info.devram.passvault.Models.Accounts;
import info.devram.passvault.ViewModel.AccountActivityViewModel;

public class AccountDetail extends AppCompatActivity {

    private static final String TAG = "AccountDetail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);

        AccountActivityViewModel accountActivityViewModel = new ViewModelProvider.AndroidViewModelFactory(
                getApplication()).create(AccountActivityViewModel.class);

        List<Accounts> accountsList = accountActivityViewModel.getAccounts().getValue();

        int position = getIntent().getIntExtra("position",0);


        TextView accountTypeDetail = findViewById(R.id.acc_type_det_txt_view);
        TextView accountNameDetail = findViewById(R.id.acc_name_det_txt_view);
        TextView accountLoginIdDetail = findViewById(R.id.login_id_det_txt_view);
        TextView accountLoginPwdDetail = findViewById(R.id.login_pwd_det_txt_view);
        TextView accountDateDetail = findViewById(R.id.date_det_txt_view);

        accountTypeDetail.setText(accountsList.get(position).getType());
        accountNameDetail.setText(accountsList.get(position).getAccountName());
        accountLoginIdDetail.setText(accountsList.get(position).getLoginId());
        accountLoginPwdDetail.setText(accountsList.get(position).getLoginPwd());
        String date = accountsList.get(position).getCreatedDate();
        String newDate = parseDate(date);
        accountDateDetail.setText(newDate);
    }

    private String parseDate(String created_date) {
        int index = created_date.indexOf("00");
        created_date = created_date.substring(0,index).trim();

        return created_date;
    }
}