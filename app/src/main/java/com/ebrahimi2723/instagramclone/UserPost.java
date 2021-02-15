package com.ebrahimi2723.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class UserPost extends AppCompatActivity {
LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_post);
        linearLayout = findViewById(R.id.linerLayout);
        Intent reviveUserIntent = getIntent();
        String reviveUser = reviveUserIntent.getStringExtra("username");
//        FancyToast.makeText(UserPost.this,reviveUser, Toast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
        setTitle(reviveUser+"'s Posts");
        ParseQuery parseQuery = new ParseQuery("Photo");
        parseQuery.whereEqualTo("userName",reviveUser);
        parseQuery.orderByDescending("createAt");

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (objects.size()>0 && e == null){
                    for (ParseObject user : objects){
                        final TextView PostDes=new TextView(UserPost.this);
                        PostDes.setText(user.get("imgCaption") + "");
                        ParseFile parseFile = (ParseFile) user.get("picture");
                        parseFile.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (data!= null &&  e == null){
                                    // image
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                                    ImageView postImageView = new ImageView(UserPost.this);
                                    LinearLayout.LayoutParams imageView_params =
                                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                                    imageView_params.setMargins(5,5,5,5);
                                    postImageView.setLayoutParams(imageView_params);
                                    postImageView.setScaleType(ImageView.ScaleType.CENTER);
                                    postImageView.setImageBitmap(bitmap);
                                    //Caption
                                    LinearLayout.LayoutParams caption=
                                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                                                    ,ViewGroup.LayoutParams.WRAP_CONTENT);
                                    PostDes.setLayoutParams(caption);
                                    PostDes.setGravity(Gravity.CENTER);
                                    PostDes.setBackgroundColor(Color.GREEN);
                                    PostDes.setTextColor(Color.WHITE);
                                    PostDes.setTextSize(30f);
                                    linearLayout.addView(postImageView);
                                    linearLayout.addView(PostDes);


                                }
                            }

                        });

                    }
                }else {
                    TextView textView=new TextView(UserPost.this);
                    LinearLayout.LayoutParams caption=
                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                                    ,ViewGroup.LayoutParams.WRAP_CONTENT);
                    textView.setText("NO POST");
                    textView.setLayoutParams(caption);
                    textView.setGravity(Gravity.CENTER);
                    textView.setBackgroundColor(Color.GRAY);
                    textView.setTextColor(Color.WHITE);
                    textView.setTextSize(30f);
                    linearLayout.addView(textView);
                }
                progressDialog.dismiss();
            }


        });


    }
}