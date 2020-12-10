package com.example.iplanapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPass;
    private Button btnLogin;
    private TextView mForgotPassword;
    private TextView mSignupHere;

    private ProgressDialog mDialog;
    //firebase

    private FirebaseAuth mAuth;
    private SQLite_DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db= new SQLite_DB(this);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        mDialog=new ProgressDialog(this);
        loginDetails();

    }

    private void loginDetails(){

        mEmail=(EditText)findViewById(R.id.Email_Login);
        mPass=(EditText)findViewById(R.id.Password_Login);
        btnLogin=(Button)findViewById(R.id.btn_login);
        mForgotPassword=(TextView)findViewById(R.id.forgot_password);
        mSignupHere=(TextView)findViewById(R.id.signup_reg);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=mEmail.getText().toString().trim();
                String pass=mPass.getText().toString().trim();



                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email Required");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    mPass.setError("Password Required");

                }
                    mDialog.setMessage("Processing..");
                     mDialog.show();
                mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            mDialog.dismiss();



                            //checking to see if user exists in db
                            boolean x=db.checkIfUserExists(email);
                            if(x=false){System.out.println("LINE 87"); db.writeUserDB(email);}
                            db.close();
                            Toast.makeText(getApplicationContext(),"Log in  Successful!",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(MainActivity.this, HomeActivity.class);
                            i.putExtra("key",email);
                            System.out.println("MAIN ACTIVITY LINE 94");

                            startActivity(i);

                            // startActivity(new Intent(getApplicationContext(),HomeActivity.class));

                        }else{
                            mDialog.dismiss();



                            Toast.makeText(getApplicationContext(),"Log in  Failed!",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
//Reg Activity



        mSignupHere.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
            }
        });
//Reset Activity
        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ResetActivity.class));
            }
        });


    }

}