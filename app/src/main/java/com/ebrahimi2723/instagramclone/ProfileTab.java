package com.ebrahimi2723.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileTab extends Fragment {
    private EditText edtName,edtBio;
    private Button update,logout;
    private TextView textView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileTab.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileTab newInstance(String param1, String param2) {
        ProfileTab fragment = new ProfileTab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile_tab, container, false);
        edtName = view.findViewById(R.id.edtProfileName);
        edtBio = view.findViewById(R.id.edtProfileBio);
        update = view.findViewById(R.id.btnProfileUpdate);
        logout= view.findViewById(R.id.logout);

        final ParseUser appUser= ParseUser.getCurrentUser();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appUser.logOut();
                Intent intent = new Intent(getContext(),SignUp.class);
                startActivity(intent);

            }
        });
        if (ParseUser.getCurrentUser().get("Profile_name")== null){
            edtName.setText("");
        }else {
            edtName.setText(appUser.get("Profile_name").toString());
        }
        if (ParseUser.getCurrentUser().get("bio")== null){
            edtBio.setText("");
        }else {
            edtBio.setText(appUser.get("bio").toString());
        }
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                appUser.put("Profile_name",edtName.getText().toString());
                appUser.put("bio",edtBio.getText().toString());
                appUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            FancyToast.makeText(getContext(), "Your Profile is Updated "
                                    , FancyToast.LENGTH_LONG, FancyToast.INFO, false).show();
                        }else {
                            FancyToast.makeText(getContext(), e.getMessage()
                                    , FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                        }
                    }
                });

            }

            });
        return view;

    }
}