package com.example.instagramclone;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {

    private EditText edtProfileName, edtProfileBio, edtProfileProfession, edtProfileHobbies,
    edtProfileFavoriteSport;
    private Button btnProfileUpdateInfo;

    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        edtProfileName = view.findViewById(R.id.edtProfileName);
        edtProfileBio = view.findViewById(R.id.edtProfileBio);
        edtProfileProfession = view.findViewById(R.id.edtProfileProfession);
        edtProfileHobbies = view.findViewById(R.id.edtProfileHobbies);
        edtProfileFavoriteSport = view.findViewById(R.id.edtProfileFavoriteSport);
        btnProfileUpdateInfo = view.findViewById(R.id.btnProfileUpdateInfo);

        final ParseUser parseUser = ParseUser.getCurrentUser();

        if (parseUser.get("profileName") == null){
            edtProfileName.setText(" ");

        }else {
            edtProfileName.setText(parseUser.get("profileName").toString());

        }

        edtProfileBio.setText(parseUser.get("profileBio")+"");
        edtProfileProfession.setText(parseUser.get("profileProfession")+"");
        edtProfileHobbies.setText(parseUser.get("profileHobbies")+"");
        edtProfileFavoriteSport.setText(parseUser.get("profileFavoriteSport")+"");



        btnProfileUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                parseUser.put("profileName", edtProfileName.getText().toString());
                parseUser.put("profileBio", edtProfileBio.getText().toString());
                parseUser.put("profileProfession", edtProfileProfession.getText().toString());
                parseUser.put("profileHobbies", edtProfileHobbies.getText().toString());
                parseUser.put("profileFavoriteSport", edtProfileFavoriteSport.getText().toString());

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getContext(),"Info Updated!!!",Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                        }
                    }
                });

            }
        });

        return view;
    }

}
