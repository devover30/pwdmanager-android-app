package info.devram.tizori;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        userEmail = findViewById(R.id.user_email);
        userPassword = findViewById(R.id.user_pwd);
        loginButton = findViewById(R.id.login_btn);

        loginButton.setOnClickListener(this);

        sharedPreferences = getSharedPreferences(
                String.valueOf(R.string.shared_pref),MODE_PRIVATE
        );

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

                Intent intent = new Intent(MainActivity.this, AccountsActivity.class);

                startActivity(intent);
            }
        });
    }
}
