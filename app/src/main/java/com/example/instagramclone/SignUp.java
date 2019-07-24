package com.example.instagramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    private EditText edtEmail, edtUsername, edtPassword;
    private Button btnSignUp, btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ParseInstallation.getCurrentInstallation().saveInBackground();

        setTitle("Sign Up!!!");

        edtEmail = findViewById(R.id.edtEmail);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);

        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(btnSignUp);
                }
                return false;
            }
        });

        btnLogIn = findViewById(R.id.btnLogIn);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null){
       // ParseUser.getCurrentUser().logOut();
            transitionToSocialMediaActivity();
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnSignUp:

                if (edtEmail.getText().toString().equals("")
                        || edtUsername.getText().toString().equals("")
                        || edtPassword.getText().toString().equals("")){

                    Toast.makeText(SignUp.this,"E-mail, Username, Password is required",
                            Toast.LENGTH_LONG).show();
                }else{
                    final ParseUser appUser = new ParseUser();
                    appUser.setEmail(edtEmail.getText().toString());
                    appUser.setUsername(edtUsername.getText().toString());
                    appUser.setPassword(edtPassword.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing up " + edtUsername.getText().toString());
                    progressDialog.show();

                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(SignUp.this,
                                        appUser.getUsername() + " is signed up!!!",
                                        Toast.LENGTH_LONG).show();
                                transitionToSocialMediaActivity();
                            }else {
                                Toast.makeText(SignUp.this,e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }



                break;
            case R.id.btnLogIn:

                Intent intent = new Intent(SignUp.this,LogInActivity.class);
                startActivity(intent);


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
        Intent intent = new Intent(SignUp.this,SocialMediaActivity.class);
        startActivity(intent);
    }

}


