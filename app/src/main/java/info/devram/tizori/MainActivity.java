package info.devram.tizori;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import info.devram.tizori.Services.LoginService;
import info.devram.tizori.Interfaces.HttpAsyncListener;
import info.devram.tizori.Models.UserEntity;


public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, HttpAsyncListener {

    private static final String TAG = "MainActivity";
    
    private EditText userEmail;
    private EditText userPassword;
    private Button loginButton;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userEmail = findViewById(R.id.user_email);
        userPassword = findViewById(R.id.user_pwd);
        loginButton = findViewById(R.id.login_btn);

        loginButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: starts");
        UserEntity userEntity = new UserEntity();
        userEntity.setUser_email(userEmail.getText().toString().trim());
        userEntity.setUser_pwd(userPassword.getText().toString().trim());
        Log.d(TAG, "onClick: " + userEntity);
        executorService = Executors.newCachedThreadPool();
        LoginService loginService = new LoginService(userEntity,this);
        executorService.execute(loginService);
        Log.d(TAG, "onClick: ends");
    }

    private void showToast(String message) {
        Toast toast = Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP,0,0);
        toast.show();
    }

    @Override
    public void httpResponse(JSONObject jsonObject, int statusCode) {
        Log.d(TAG, "loginResponse: starts");
        if (statusCode == 200) {
            SharedPreferences sharedPreferences = getSharedPreferences("token",MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPreferences.edit();

            try {
                editor.putString("token",jsonObject.getString("msg"));
                editor.apply();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            executorService.shutdownNow();
            Intent intent = new Intent(MainActivity.this,AccountsActivity.class);
            startActivity(intent);
        }
        Log.d(TAG, "loginResponse: " + jsonObject);
        Log.d(TAG, "loginResponse: " + statusCode);
        Log.d(TAG, "loginResponse: ends");
    }
}
