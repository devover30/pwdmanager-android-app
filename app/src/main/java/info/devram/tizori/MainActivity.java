package info.devram.tizori;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import info.devram.tizori.controller.LoginAsyncResponse;
import info.devram.tizori.controller.LoginHandler;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText userEmail;
    private EditText userPassword;
    private Button loginButton;
    SharedPreferences sharedPreferences;



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

        sharedPreferences = getSharedPreferences(
                String.valueOf(R.string.shared_pref),MODE_PRIVATE
        );

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

        String email = userEmail.getText().toString().trim();
        String pass = userPassword.getText().toString().trim();


        final SharedPreferences.Editor editor = sharedPreferences.edit();

        new LoginHandler().checkLogin(email, pass,
                getApplicationContext(), new LoginAsyncResponse() {
            @Override
            public void getToken(String token) {
                editor.putString("token",token);
                editor.apply();

                Intent intent = new Intent(MainActivity.this,
                        AccountsActivity.class);

                startActivity(intent);
            }

            @Override
            public void responseError(Boolean isNetworkError) {
                if (isNetworkError) {

                    showToast("Network Issue! Try Again Later");
                }else {
                    showToast("Email/Password Incorrect!!");
                }

            }


        });
    }

    private void showToast(String message) {
        Toast toast = Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP,0,0);
        toast.show();
    }
}
