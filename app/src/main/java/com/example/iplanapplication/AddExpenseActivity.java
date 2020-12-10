package com.example.iplanapplication;

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

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddExpenseActivity extends AppCompatActivity {

   
        private EditText expense_name;
        private EditText expense_amount;
        private Button save_btn;
        private Spinner expense_menu;
        private DatePicker expense_date;
        private String expense_recurr;
        private CheckBox recur;
        private String User;
        private SQLite_DB db;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_addexpense);
            db= new SQLite_DB(this);
            recur=findViewById(R.id.income_recurrence);

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                User = extras.getString("key");
            }
            expense_name=findViewById(R.id.expense_name);
            expense_amount=findViewById(R.id.expense_amount);
            save_btn=(Button)findViewById(R.id.expense_save);
            expense_date=(DatePicker)findViewById(R.id.expense_date);
            Spinner mySpinner = (Spinner) findViewById(R.id.expense_menu);

            save_btn.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if( TextUtils.isEmpty(expense_name.getText())){
                        expense_name.setError( "Name is required!" );
                    } else if(TextUtils.isEmpty(expense_amount.getText())){
                        expense_amount.setError("Amount is Required");
                    } else if(TextUtils.isEmpty(mySpinner.getSelectedItem().toString())){
                        ((TextView)mySpinner.getSelectedView()).setError("Error message");
                    }


                    else{
                        Date date1= (Date) new Date
                                (expense_date.getYear(), expense_date.getMonth(), expense_date.getDayOfMonth());
                        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
                        String dateString = sdf.format(date1);

                        String expenseName=expense_name.getText().toString().trim();
                        String expenseAmount=expense_amount.getText().toString();
                        int expense_amount=Integer.parseInt(expenseAmount);;
                        String expense_menu_ans = mySpinner.getSelectedItem().toString();

                        System.out.println(expenseName+"  "+expenseAmount+"  "+expense_menu_ans+"  "+dateString);

/*
                        boolean x=db.checkIfExpenseExists(User, expenseName,expense_amount,dateString,expense_menu_ans, expense_recurr);
                        if(!x){
                            System.out.println("expense activity line 81 reached");*/

                        if(recur.isChecked()){
                            expense_recurr="true";
                        } else{
                            expense_recurr="false";
                        }


                        db.writeExpenseToDB(User, expenseName,expenseAmount,dateString,expense_menu_ans, String.valueOf(expense_recurr));





                        }

                }
            });



        }
    }
