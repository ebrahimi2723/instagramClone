package com.ebrahimi2723.instagramclone;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private EditText userName, password;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        userName = findViewById(R.id.username_Login);
        password = findViewById(R.id.PasswordLogin);
        final Button btnLogin = findViewById(R.id.btnLogin2);
        Button btnSignUp = findViewById(R.id.btnSignUp2);

        btnSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(btnLogin);
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
                final ProgressDialog progressDialog = new ProgressDialog(Login.this);
                progressDialog.setMessage("Login... ");
                progressDialog.show();
                ParseUser.logInInBackground(userName.getText().toString(), password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e == null && user != null){
                            FancyToast.makeText(Login.this,"Welcome back "+userName.getText().toString()
                                    ,FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                            goToSocialMedia();

                        }else {
                            FancyToast.makeText(Login.this,e.getMessage()
                                    ,FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                        }
                        progressDialog.dismiss();

                    }
                });
                break;
            case  R.id.btnSignUp2:
                finish();
                break;

        }
    }
    public void mainLayoutLogin(View view){
       try {
           InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
           inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
       }catch (Exception e){
           e.printStackTrace();
       }
    }
    private void goToSocialMedia(){
        Intent intent =  new Intent(Login.this, Social_media.class);
        startActivity(intent);

    }

}
