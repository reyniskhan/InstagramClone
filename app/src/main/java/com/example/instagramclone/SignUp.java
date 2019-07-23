package com.example.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    private Button btnKickBoxer,btnGetAllData, btnTransition;
    private EditText name,punchSpeed,punchPower,kickSpeed,kickPower;
    private TextView txtGetData;
    private String allKickBoxers;

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
        txtGetData = findViewById(R.id.txtGetData);
        btnGetAllData = findViewById(R.id.btnGetAllData);
        btnTransition = findViewById(R.id.btnTransition);
        btnKickBoxer.setOnClickListener(SignUp.this);

        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("kickBoxer");
                parseQuery.getInBackground("8x6Y5IcpTb", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {

                        if (object != null && e == null){
                            txtGetData.setText(object.get("Name") + "" + "Punch Power"
                            + object.get("PunchPower"));
                        }

                    }
                });
            }
        });

        btnGetAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allKickBoxers = "";
                ParseQuery<ParseObject> queryall = ParseQuery.getQuery("kickBoxer");

                queryall.whereGreaterThan("PunchPower",100);
                queryall.setLimit(1);

                queryall.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (objects.size() > 0 && e == null ){
                            for (ParseObject parseObject : objects){
                                allKickBoxers = allKickBoxers + parseObject.get("Name") + "\n";
                            }
                            Toast.makeText(SignUp.this,allKickBoxers,Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(SignUp.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        btnTransition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SignUp.this,
                        SignUpLoginActivity.class);
                startActivity(intent);

            }
        });
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


