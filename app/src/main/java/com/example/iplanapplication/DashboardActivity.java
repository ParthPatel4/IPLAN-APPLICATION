package com.example.iplanapplication;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
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

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        db= new SQLite_DB(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Current_user = extras.getString("key");
        }

        //db.getExpenseByUser(Current_user);
        //db.getIncomeByUser(Current_user);

        int total_spent = 35;
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



       /* ProgressBar progressBar = findViewById(R.id.progressBar);

        progressBar.setProgress(45);//initially progress is 0
        progressBar.setMax(100);//sets the maximum value 100*/
       /* progressBar.show();//displays the progress bar
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, 500); // see this max value coming back here, we animate towards that value
        animation.setDuration(5000); // in milliseconds
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();*/
    }

}
