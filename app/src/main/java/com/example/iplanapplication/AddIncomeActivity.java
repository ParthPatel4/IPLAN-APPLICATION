package com.example.iplanapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddIncomeActivity extends AppCompatActivity {
        private EditText income_name;
        private EditText income_amount;
        private Button save_btn;
        private Spinner income_menu;
        private DatePicker income_date;
        private SQLite_DB db;
        private String User;
        private String inc_recurr;
        private CheckBox recur;

//////////////////////////firebase////////////////////////
        private ProgressDialog mDialog;
        private FirebaseAuth mAuth;
///////////////////////////////////////////////////////////


        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_addincome);
            db= new SQLite_DB(this);

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                User = extras.getString("key");
            }




            mAuth = FirebaseAuth.getInstance();
            mDialog = new ProgressDialog(this);


            income_name=findViewById(R.id.income_name);
            income_amount=findViewById(R.id.income_amount);
            save_btn=(Button)findViewById(R.id.income_save);
            income_date=(DatePicker)findViewById(R.id.income_date);


           // recur=findViewById(R.id.expense_recurrence);
            //Spinner mySpinner = (Spinner) findViewById(R.id.income_menu);




            save_btn.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if( TextUtils.isEmpty(income_name.getText())){
                        income_name.setError( "Name is required!" );
                    } else if(TextUtils.isEmpty(income_amount.getText())){
                        income_amount.setError("Amount is Required");
                    }

                    else{

                        Date date1= (Date) new Date
                                (income_date.getYear(), income_date.getMonth(), income_date.getDayOfMonth());
                        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
                        String dateString = sdf.format(date1);

                        String incomeName=income_name.getText().toString().trim();
                        String incomeAmount=income_amount.getText().toString().trim();
                        System.out.println(incomeName+"  "+incomeAmount+"  "+"  "+dateString);


                        /*
                        if(recur.isChecked()){
                            inc_recurr="true";
                        } else{
                            inc_recurr="false";
                        }
*/

                        db.writeIncomeToDB(User, incomeName, incomeAmount,dateString);


                    }
                }
            });



        }
}
