package com.example.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnLogInSignUp, btnLogInLogIn;
    private EditText edtLogInUsername, edtLogInPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        setTitle("Log In!!!");

        btnLogInLogIn = findViewById(R.id.btnLogInLogIn);
        btnLogInSignUp = findViewById(R.id.btnLogInSignUp);
        edtLogInUsername = findViewById(R.id.enterLogInUsername);
        edtLogInPassword = findViewById(R.id.edtLogInPassword);

        edtLogInPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(btnLogInLogIn);
                }
                return false;
            }
        });

        btnLogInSignUp.setOnClickListener(this);
        btnLogInLogIn.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
            ParseUser.getCurrentUser().logOut();
            }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnLogInLogIn:

                if (edtLogInUsername.getText().toString().equals("")
                        || edtLogInPassword.getText().toString().equals("")) {

                    Toast.makeText(LogInActivity.this, "Username, Password is required",
                            Toast.LENGTH_LONG).show();
                } else {

                    ParseUser.logInInBackground(edtLogInUsername.getText().toString(),
                            edtLogInPassword.getText().toString(), new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {
                                    if (user != null && e == null) {
                                        Toast.makeText(LogInActivity.this, user.getUsername() + " logged in successfully", Toast.LENGTH_LONG).show();
                                        transitionToSocialMediaActivity();
                                    } else {
                                        Toast.makeText(LogInActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                }

                    break;
                    case R.id.btnLogInSignUp:
                        ParseUser.getCurrentUser().logOut();
                        finish();
                        break;
        }

    }

    public void rootLayoutTapped(View view){

        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (Exception e ){
            e.printStackTrace();
        }

    }

    private void transitionToSocialMediaActivity(){
        Intent intent = new Intent(LogInActivity.this,SocialMediaActivity.class);
        startActivity(intent);
    }
}
