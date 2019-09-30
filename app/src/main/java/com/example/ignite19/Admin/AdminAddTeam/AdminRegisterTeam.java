


package com.example.ignite19.Admin.AdminAddTeam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ignite19.LoginActivity;
import com.example.ignite19.Participation;
import com.example.ignite19.R;
import com.example.ignite19.UserDetail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class AdminRegisterTeam extends AppCompatActivity implements View.OnClickListener{

    EditText edUserName,edPassword,edCollegeName,edParticipant1,edParticipant2,edParticipant3,edParticipant4,edPartcipant5;
    String userName,password,collegeName,participant1,participant2,participant3,participant4,participant5;
    Button registerButton;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_register_team);
        getSupportActionBar().setTitle("Team Registration");
        edUserName = (EditText)findViewById(R.id.edx1_username);
        edPassword = (EditText)findViewById(R.id.edx2_pw);
        edCollegeName = (EditText)findViewById(R.id.ed3_college_name);
        edParticipant1 = (EditText)findViewById(R.id.edx_p1);
        edParticipant2 = (EditText)findViewById(R.id.edx_p2);
        edParticipant3 = (EditText)findViewById(R.id.edx_p3);
        edParticipant4 = (EditText)findViewById(R.id.edx_p4);
        edPartcipant5= (EditText)findViewById(R.id.edx_p5);
        registerButton = (Button)findViewById(R.id.button_register_team);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_register_team:
                if(checkEditTextIsEmptyOrNot()){
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(userName+"@ignite.com",password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                UserDetail userDetail=  new UserDetail(collegeName,participant1,participant2,participant3,participant4,participant5);
                                String uuid = mAuth.getCurrentUser().getUid();
                                FirebaseDatabase.getInstance().getReference("Users").child(uuid).setValue(userDetail);
                                ArrayList<Participation> mList = StaticListOfEventParticipation.getEventListWithParticipation();
                                for(int i = 0 ; i < mList.size();++i){
                                    FirebaseDatabase.getInstance().getReference("Users").child(uuid).child("participation").child(mList.get(i).getEvent_name()).setValue(mList.get(i));
                                }
                                Toasty.success(getApplicationContext(),"User creation Successful, ",Toast.LENGTH_LONG).show();
                                mAuth.signOut();
                                Intent intent = new Intent(AdminRegisterTeam.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toasty.error(getApplicationContext(),"User creation failed, Try again",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else{
                    Toasty.info(getApplicationContext(),"Please fill all the fields correctly",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }



    private boolean checkEditTextIsEmptyOrNot() {
        userName = edUserName.getText().toString();
        password = edPassword.getText().toString();
        collegeName = edCollegeName.getText().toString();
        participant1 = edParticipant1.getText().toString();
        participant2 = edParticipant2.getText().toString();
        participant3 = edParticipant3.getText().toString();
        participant4 = edParticipant4.getText().toString();
        participant5 = edPartcipant5.getText().toString();
        if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(password) || TextUtils.isEmpty(collegeName) || TextUtils.isEmpty(participant1) || TextUtils.isEmpty(participant2) || TextUtils.isEmpty(participant3) || TextUtils.isEmpty(participant4) || TextUtils.isEmpty(participant5)){
            return false;
        }
        else{
            return true;
        }
    }
}


/****************************************************
 *

package com.example.ignite19.Admin.AdminAddTeam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ignite19.LoginActivity;
import com.example.ignite19.Participation;
import com.example.ignite19.R;
import com.example.ignite19.UserDetail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminRegisterTeam extends AppCompatActivity implements View.OnClickListener{

    EditText edUserName,edPassword,edCollegeName,edParticipant1,edParticipant2,edParticipant3,edParticipant4,edPartcipant5;
    String userName,password,collegeName,participant1,participant2,participant3,participant4,participant5;
    Button registerButton;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register_team);
        edUserName = (EditText)findViewById(R.id.edx1_username);
        edPassword = (EditText)findViewById(R.id.edx2_pw);
        edCollegeName = (EditText)findViewById(R.id.ed3_college_name);
        edParticipant1 = (EditText)findViewById(R.id.edx_p1);
        edParticipant2 = (EditText)findViewById(R.id.edx_p2);
        edParticipant3 = (EditText)findViewById(R.id.edx_p3);
        edParticipant4 = (EditText)findViewById(R.id.edx_p4);
        edPartcipant5= (EditText)findViewById(R.id.edx_p5);
        registerButton = (Button)findViewById(R.id.button_register_team);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_register_team:
                if(checkEditTextIsEmptyOrNot()){
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(userName+"@ignite.com",password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           UserDetail userDetail=  new UserDetail(collegeName,participant1,participant2,participant3,participant4,participant5);
                           String uuid = mAuth.getCurrentUser().getUid();
                         FirebaseDatabase.getInstance().getReference("Users").child(uuid).setValue(userDetail);
                           ArrayList<Participation> mList = StaticListOfEventParticipation.getEventListWithParticipation();
                           for(int i = 0 ; i < mList.size();++i){
                               FirebaseDatabase.getInstance().getReference("Users").child(uuid).child("participation").child(mList.get(i).getEvent_name()).setValue(mList.get(i));
                           }
                           Toast.makeText(getApplicationContext(),"User creation Successful, Try again",Toast.LENGTH_LONG).show();
                           mAuth.signOut();
                           Intent intent = new Intent(AdminRegisterTeam.this, LoginActivity.class);
                           startActivity(intent);
                       }
                       else{
                           Toast.makeText(getApplicationContext(),"User creation failed, Try again",Toast.LENGTH_LONG).show();
                       }
                        }
                    });
                }
                break;
        }
    }



    private boolean checkEditTextIsEmptyOrNot() {
        userName = edUserName.getText().toString();
        password = edPassword.getText().toString();
        collegeName = edCollegeName.getText().toString();
        participant1 = edParticipant1.getText().toString();
        participant2 = edParticipant2.getText().toString();
        participant3 = edParticipant3.getText().toString();
        participant4 = edParticipant4.getText().toString();
        participant5 = edPartcipant5.getText().toString();
        if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(password) || TextUtils.isEmpty(collegeName) || TextUtils.isEmpty(participant1) || TextUtils.isEmpty(participant2) || TextUtils.isEmpty(participant3) || TextUtils.isEmpty(participant4) || TextUtils.isEmpty(participant5)){
            return false;
        }
        else{
            return true;
        }
    }
}
/*****************************************************************************************


 */