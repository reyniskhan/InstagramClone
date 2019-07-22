package com.example.instagramclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    private Button btnKickBoxer;
    private EditText name,punchSpeed,punchPower,kickSpeed,kickPower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseInstallation.getCurrentInstallation().saveInBackground();

        btnKickBoxer = findViewById(R.id.btnKickBoxer);
        name = findViewById(R.id.name);
        punchPower = findViewById(R.id.punchPower);
        punchSpeed = findViewById(R.id.punchSpeed);
        kickPower = findViewById(R.id.kickPower);
        kickSpeed = findViewById(R.id.kickSpeed);
        btnKickBoxer.setOnClickListener(SignUp.this);
    }

    @Override
    public void onClick(View view) {
        try {

            final ParseObject kickBoxer = new ParseObject("kickBoxer");
            kickBoxer.put("Name", name.getText().toString());
            kickBoxer.put("PunchSpeed", Integer.parseInt(punchSpeed.getText().toString()));
            kickBoxer.put("PunchPower", Integer.parseInt(punchPower.getText().toString()));
            kickBoxer.put("KickSpeed", Integer.parseInt(kickSpeed.getText().toString()));
            kickBoxer.put("KickPower", Integer.parseInt(kickPower.getText().toString()));
            kickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(SignUp.this, kickBoxer.get("Name") + " was saved successfully", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (Exception e){
            Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}


