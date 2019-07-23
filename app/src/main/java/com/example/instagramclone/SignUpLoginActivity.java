package com.example.instagramclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpLoginActivity extends AppCompatActivity {

    private Button btnSignUp, btnLogin;
    private EditText edtUsernameSignup, edtPasswordSignUp, edtUsernameLogin, edtPasswordLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_login_activity);

        edtUsernameSignup = findViewById(R.id.edtUsernameSignUp);
        edtPasswordSignUp = findViewById(R.id.edtPasswordSignUp);
        edtUsernameLogin = findViewById(R.id.edtUsernameLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLogin);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ParseUser appUser = new ParseUser();
                appUser.setUsername(edtUsernameSignup.getText().toString());
                appUser.setPassword(edtPasswordSignUp.getText().toString());

                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            Toast.makeText(SignUpLoginActivity.this,appUser.get("username") + " signed up successfully",Toast.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(SignUpLoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });

    btnLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ParseUser.logInInBackground(edtUsernameLogin.getText().toString(),
                    edtPasswordLogin.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (user != null && e == null) {
                        Toast.makeText(SignUpLoginActivity.this,user.get("username") + " logged in successfully",Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    });
    }
}
