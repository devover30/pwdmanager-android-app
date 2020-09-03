package info.devram.tizori.Repository;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import info.devram.tizori.Config.Util;
import info.devram.tizori.Interfaces.ApiResponseListener;
import info.devram.tizori.Models.Accounts;
import info.devram.tizori.R;
import info.devram.tizori.core.HttpHandler;

public class ApiAccountRepository {

    private String URL = Util.KEY_URL_ACCOUNTS;
    private HttpHandler httpHandler;
    private String token;
    private Context ctx;

    public ApiAccountRepository(Context context) {
        this.ctx = context;
        this.httpHandler = HttpHandler.getInstance(context);
    }


    public void getApiAccounts(final ApiResponseListener listener) {



    }

    public void updateApiAccount(Accounts obj) {

    }


    public void addApiAccount(Accounts obj) {

    }


    public void deleteApiAccount(Accounts obj) {

    }

    private void getToken() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(
                String.valueOf(R.string.shared_pref), Context.MODE_PRIVATE
        );

        token = sharedPreferences.getString("token","No Token");

    }

    private Accounts parseApiResponse(JSONObject response) throws JSONException {
        Accounts accountModel = new Accounts();

        accountModel.setType(response.getString("type"));
        accountModel.setAccountName(response.getString("account_name"));
        accountModel.setLoginId(response.getString("login_id"));
        accountModel.setLoginPwd(response.getString("login_pwd"));
        accountModel.setCreatedDate(response.getString("created_date"));

        return accountModel;
    }
}
