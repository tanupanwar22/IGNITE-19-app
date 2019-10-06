package com.example.ignite19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.ignite19.Admin.AdminHomeAcitivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.Objects;

import am.appwise.components.ni.NoInternetDialog;
import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {
    private EditText userNameEditText;
    private EditText userPasswordEditText;
    private FirebaseAuth mAuth;
    private Button signin;
    private ImageView loader;
    private ScrollView scrollLayout;
    private LinearLayout linearLayout;
    private final String TAG = "ALPHA";
    NoInternetDialog noInternetDialog;
    String fcmFLAG;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

      if(!isConnected(LoginActivity.this)){
          //buildDialog(LoginActivity.this).show();


      }else {
          FirebaseUser currentUser = mAuth.getCurrentUser();
          if (currentUser != null) {
              updateUI(currentUser);
          }
      }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         getSupportActionBar().hide();
            loader=findViewById(R.id.gif1);
        loader.setVisibility(View.INVISIBLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();




        if(!isConnected(LoginActivity.this)){
           // buildDialog(LoginActivity.this).show();
            noInternetDialog= new NoInternetDialog.Builder(LoginActivity.this).build();

        }
        else {


            userNameEditText=findViewById(R.id.username_editText);
            userPasswordEditText=findViewById(R.id.password_editText);
            signin=findViewById(R.id.sign_in_button);


            userPasswordEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        signin.performClick();
                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        return true;
                    }
                    return false;
                }
            });

            loader = findViewById(R.id.gif1);
          // Glide.with(getApplicationContext()).load(R.drawable.loader).transform(new CircleCrop()).into(loader);

           loader.setVisibility(View.INVISIBLE);


            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   loader.setVisibility(View.VISIBLE);
                    onClickSignIN(view);
                }
            });
        }
    }

    public void onClickSignIN(View view) {
        loader.setVisibility(View.VISIBLE);
        String userName = userNameEditText.getText().toString();
        String userPassword = userPasswordEditText.getText().toString();
        if(isStringEmpty(userName, userPassword)){
            //details are given.. lets login now
            userName = userName + "@ignite.com";
            fireabaseSignIN(userName, userPassword);



        }
        else{
            //no proper data
            //Toast.makeText(getApplicationContext(),"Please enter details in both the fields",Toast.LENGTH_LONG).show();
            Toasty.info(getApplicationContext(),"Please enter details in both the fields",Toast.LENGTH_LONG).show();
         loader.setVisibility(View.INVISIBLE);
        }
    }

    private void fireabaseSignIN(String userName, String userPassword) {
        mAuth.signInWithEmailAndPassword(userName, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                             loader.setVisibility(View.INVISIBLE);
                                updateUI(user);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                           // Toast.makeText(getApplicationContext(), "Authentication failed.",
                             //       Toast.LENGTH_LONG).show();
                            Toasty.error(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_LONG).show();
                         loader.setVisibility(View.INVISIBLE);
                        }
                    }
                });

    }

    private void updateUI(FirebaseUser user) {
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);
            }
        }



        int pos = Objects.requireNonNull(user.getEmail()).indexOf("@");
        String displayName = user.getEmail().substring(0 , pos);
        Intent intent = getIntent();
        if(getIntent() != null){
            fcmFLAG = intent.getStringExtra("mFlag");
        }

        if(displayName.equalsIgnoreCase("ADMIN")){
            Intent adminIntent = new Intent(LoginActivity.this, AdminHomeAcitivity.class);
            adminIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            adminIntent.putExtra("UUID",user.getUid());
            adminIntent.putExtra("userName",user.getDisplayName());
            adminIntent.putExtra("mFlag",fcmFLAG);

            startActivity(adminIntent);
        }
        else{
           Intent userIntent = new Intent(LoginActivity.this,splash.class);
            //Intent userIntent = new Intent(LoginActivity.this,MainActivity.class);
            userIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            userIntent.putExtra("UUID",user.getUid());
            userIntent.putExtra("userName",user.getDisplayName());
            userIntent.putExtra("mFlag",fcmFLAG);
            startActivity(userIntent);
        }
    }

    boolean isStringEmpty(String userName,String userPassword){
        return !TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPassword);
    }

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) {
                return true;
            } else {
                return false;
            }
        } else
            return false;
    }


    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        return builder;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(noInternetDialog!=null)
        noInternetDialog.onDestroy();
    }
}
