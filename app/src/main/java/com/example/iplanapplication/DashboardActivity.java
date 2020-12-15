package com.example.iplanapplication;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.lzyzsd.circleprogress.CircleProgress;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DashboardActivity extends AppCompatActivity {


    private TextView txtProgress;
    private ProgressBar progressBar;
    private int pStatus = 0;
    private Handler handler = new Handler();
    private SQLite_DB db;
    private String Current_user;
    private int total_expenses;
    private int total_income;
    private int income_expense_difference;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    //int pStatus = 0;
    //private Handler handler = new Handler();
    TextView tv;
    ListView listView ;
    ListView listView2 ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        listView = (ListView) findViewById(R.id.income_list);
        listView2 = (ListView) findViewById(R.id.expense_list);

        db= new SQLite_DB(this);

        //this gets the current users email
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Current_user = extras.getString("key");
        }

        total_expenses=db.GetTotalExpense(Current_user);
        total_income=db.GetTotalIncome(Current_user);
        income_expense_difference=total_income-total_expenses;


        double total_spe= ((double)total_expenses / total_income) * 100;
        int total_spent=(int)(total_spe);

        TextView textViewToChange = (TextView) findViewById(R.id.total_expense);
        textViewToChange.setText("$"+String.valueOf(total_expenses));
        TextView textViewToChange2 = (TextView) findViewById(R.id.total_income);
        textViewToChange2.setText("$"+String.valueOf(total_income));


        Timer timer;

        CircleProgress circleProgress = (CircleProgress) findViewById(R.id.circle_progress);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        circleProgress.setProgress(total_spent);

                    }
                });
            }
        }, 1000, 50);

        //retrieving list with all the incomes including the name, date, type and etc
        //SQLiteDB contains method getIncomeByUser
        List Income_List=db.getIncomeByUser(Current_user);
        //Size variable required to iterate throughh the array and retrievee only the income name
        int Income_size=Income_List.size()/6;
        //this array will hold the string value of the name of the income
        String[] Income_names=new String[Income_size];

        //this loop iterates through the List and populates the income name array

        //the loops starts with the first income at index 2
        //it increments by 6 becasue every 6th element is the income name
        int j=0;
        for (int i=2; i<Income_List.size(); i+=6){
            Income_names[j]=String.valueOf(Income_List.get(i));
            j++;

        }
        System.out.println("the string array contains "+Income_names[0]+"  "+Income_names[1]);
        //This assaigns the string to the adapter so that the listview can retreive values
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, Income_names);

        // this assigns the adapter and thus displays the items in the layout view
        listView.setAdapter(adapter);


        //This loop adds dates into an array
        String[] Calendar_Income=new String[Income_size];
        int m=0;
        for (int i=4; i<Income_List.size(); i+=7){
            Calendar_Income[m]=String.valueOf(Income_List.get(i));
            m++;

        }
        //the values from the calendar array can be used to add calendar events to the calaendarview




        List Expense_List=db.getExpenseByUser(Current_user);
        int Exp_size=Expense_List.size()/7;
        String[] EXP_names=new String[Exp_size];



        int k=0;
        for (int i=3; i<Expense_List.size(); i+=7){
            EXP_names[k]=String.valueOf(Expense_List.get(i));
            k++;

        }
        System.out.println("the string array contains "+EXP_names[0]+"  "+EXP_names[1]);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_2, android.R.id.text2, EXP_names);


        // Assign adapter to ListView
        listView2.setAdapter(adapter2);

        String[] Calendar_Expense=new String[Exp_size];
        int n=0;
        for (int i=4; i<Expense_List.size(); i+=7){
            Calendar_Expense[n]=String.valueOf(Expense_List.get(i));
            n++;

        }








    }

}
