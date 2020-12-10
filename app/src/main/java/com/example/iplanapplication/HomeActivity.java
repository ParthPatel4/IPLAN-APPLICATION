package com.example.iplanapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {
    private Button dash_btn;
    private Button income_btn;
    private Button expense_btn;

   // private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //These are the buttons  from activity home
        dash_btn=(Button)findViewById(R.id.dash_btn);
        income_btn=(Button)findViewById(R.id.income_btn);
        expense_btn=(Button)findViewById(R.id.expense_btn);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String curr_email = extras.getString("key");
            System.out.println(curr_email);
            int in =1234;

        }




        dash_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //This retrieves the email of the current user from the login page
                String curr_email = extras.getString("key");

                //These 2 lines send the email from the current page (Home) to the Dashboard
                Intent i = new Intent(HomeActivity.this, DashboardActivity.class);
                i.putExtra("key",curr_email);
                //The user is redirected to the Dashboard
                startActivity(i);
            }
        });

        income_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String curr_email = extras.getString("key");
                Intent i = new Intent(HomeActivity.this, AddIncomeActivity.class);
                i.putExtra("key",curr_email);
                startActivity(i);
            }
        });

        expense_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String curr_email = extras.getString("key");
                Intent i = new Intent(HomeActivity.this, AddExpenseActivity.class);
                i.putExtra("key",curr_email);
                startActivity(i);

                //startActivity(new Intent(getApplicationContext(),AddExpenseActivity.class));
            }
        });
    }
}