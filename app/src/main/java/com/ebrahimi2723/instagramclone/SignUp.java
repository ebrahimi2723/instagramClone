package com.ebrahimi2723.instagramclone;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseSession;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private EditText email,userName,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        email= findViewById(R.id.email);
        userName = findViewById(R.id.username_Login);
        password = findViewById(R.id.PasswordLogin);
        final Button btnSignUp = findViewById(R.id.btnSignUp2);
        Button btnLogin = findViewById(R.id.btnLogin2);
        btnSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(btnSignUp);
                }
                return false;
            }
        });
        if (ParseUser.getCurrentUser() != null) {
            // ParseUser.getCurrentUser().logOut();
            goToSocialMedia();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin2:
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
                break;
            case R.id.btnSignUp2:
                ParseUser appUser = new ParseUser();
                if (email.getText().toString().equals("") || userName.getText().toString().equals("") || password.getText().toString().equals("")) {
                    FancyToast.makeText(SignUp.this, "Email,Username,Password is Require"
                            , FancyToast.LENGTH_LONG, FancyToast.INFO, false).show();
                } else {


                    final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
                    progressDialog.setMessage("Singing Up...");
                    progressDialog.show();
                    appUser.setEmail(email.getText().toString());
                    appUser.setUsername(userName.getText().toString());
                    appUser.setPassword(password.getText().toString());
                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(SignUp.this, "Welcome " + userName.getText().toString()
                                        , FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                goToSocialMedia();

                            } else {
                                FancyToast.makeText(SignUp.this, e.getMessage()
                                        , FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                            }
                            progressDialog.dismiss();

                        }
                    });

                    break;

                }

        }

    }
    public void tapMainLayoutSignUp(View view){
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager)  getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void goToSocialMedia(){
        finish();
        Intent intent =  new Intent(SignUp.this, Social_media.class);
        startActivity(intent);


    }
}

