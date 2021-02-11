package com.ebrahimi2723.instagramclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;

public class Social_media extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private  TabAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_media);
        setTitle("hi");


      viewPager = findViewById(R.id.viewPager);
      tabAdapter = new TabAdapter(getSupportFragmentManager());
      viewPager.setAdapter(tabAdapter);
      tabLayout = findViewById(R.id.tabLayout);
      tabLayout.setupWithViewPager(viewPager,false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =getMenuInflater();
        menuInflater.inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.postImageItem){
            if (android.os.Build.VERSION.SDK_INT >=23 &&
                    ActivityCompat.checkSelfPermission(Social_media.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)!=
                            PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                        ,3000);
            }else {
                getChoseImage();
            }
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 3000){
            if (grantResults.length >= 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                getChoseImage();
            }
        }
    }

    private void getChoseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,4000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4000 && requestCode == RESULT_OK && data != null ){
        try {
            Uri uri= data.getData();
            Bitmap receivedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            receivedImageBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            ParseFile parseFile= new ParseFile("img.png",bytes);
            ParseObject parseObject = new ParseObject("Photo");
            parseObject.put("picture",parseFile);
            parseObject.put("userName",ParseUser.getCurrentUser().getUsername());
            final ProgressDialog progressDialog = new ProgressDialog(Social_media.this);
            progressDialog.setMessage("loading...");
            progressDialog.show();
            parseObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null){
                        FancyToast.makeText(Social_media.this,"Done!",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true ).show();

                    }else {
                        FancyToast.makeText(Social_media.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true ).show();

                    }
                    progressDialog.dismiss();
                }
            });

        }catch (Exception e){
            e.printStackTrace();

        }
        }
    }
}