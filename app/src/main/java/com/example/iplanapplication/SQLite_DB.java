package com.example.iplanapplication;
/*
Users                                                  Expense
__________________________________________        _______________________________________________________________
|UserId|Username|TotalIncome|Total Expense|       |Username|Exp_name|Exp_amount|Exp_date|Exp_type|Exp_recurrence|
|------|--------|-----------|-------------|       |--------|--------|----------|--------|--------|--------------|
|______|________|___________|_____________|       |________|________|__________|________|________|______________|

Income
______________________________________________________
|Username|Inc_name|Inc_amount|Inc_date|Inc_recurrence|
|--------|--------|----------|--------|--------------|
|________|________|__________|________|______________|

 */
import java.lang.*;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLite_DB extends SQLiteOpenHelper {

    // All Static variables

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "iPlanApp";
    //  table names
    private static final String USER_TABLE= "USER";
    private static final String INC_TABLE="INCOMES";
    private static final String EXP_TABLE="EXPENSES";
    // USER Table Columns names
    private static final String USER_ID = "UserId";
    private static final String USERNAME = "Username";
    private static final String TOTAL_INCOME="Total_Income";
    private static final String TOTAL_EXPENSE="Total_Expense";
    // INCOME Table Column names
    private static final String INC_ID = "IncId";
    private static final String INC_NAME="Income_Name";
    private static final String INC_AMOUNT="Income_Amount";
    private static final String INC_DATE="Income_Date";
    private static final String INC_RECURRENCE="Income_Recurrence";
    //EXPENSE Table Column names
    private static final String EXP_ID = "ExpId";
    private static final String EXP_TYPE="Expense_Type";
    private static final String EXP_NAME="Expense_Name";
    private static final String EXP_AMOUNT="Expense_Amount";
    private static final String EXP_DATE="Expense_Date";
    private static final String EXP_RECURRENCE="Expense_Recurrence";


    //contructor
    public SQLite_DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        //Creating the tables with SQL Query

        String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE + "("
                + USER_ID + " INTEGER PRIMARY KEY,"
                + USERNAME + " TEXT, "
                +TOTAL_EXPENSE+" TEXT, "
                +TOTAL_INCOME+" TEXT"
                +  ")";
        db.execSQL(CREATE_USER_TABLE);
        String CREATE_INCOME_TABLE = " CREATE TABLE " + INC_TABLE + "("
                + INC_ID +" INTEGER PRIMARY KEY,"
                + USERNAME + " TEXT,"
                +INC_NAME+" VARCHAR(255), "
                +INC_DATE+" VARCHAR(255),"
                +INC_AMOUNT+" VARCHAR(255),"
                +INC_RECURRENCE+" VARCHAR(255) "
                +  ")";
        db.execSQL(CREATE_INCOME_TABLE);
        String CREATE_EXPENSE_TABLE = " CREATE TABLE " + EXP_TABLE + "("
                +EXP_ID + " INTEGER PRIMARY KEY,"
                +USERNAME + " TEXT,"
                +EXP_TYPE+" VARCHAR(255),"
                +EXP_NAME+" VARCHAR(255), "
                +EXP_DATE+" VARCHAR(255),"
                +EXP_AMOUNT+" VARCHAR(255),"
                +EXP_RECURRENCE+" VARCHAR(255) "
                +  ")";
        db.execSQL(CREATE_EXPENSE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + INC_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + EXP_TABLE);
        // Create tables again
        onCreate(db);
    }


    /* ***************************************************
     * All CRUD(Create, Read, Update, Delete) Operations
     *************************************************** */

    //writing to Users table
    //This method is invoked when a new user logs into the system for the first time
    public void writeUserDB(String Username){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USERNAME,Username);
        values.put(TOTAL_EXPENSE,0);
        values.put(TOTAL_INCOME,0);
        //inserting row into db table
        long rowInserted=db.insert(USER_TABLE, null, values);
        //Checking if row was inserted
        if(rowInserted != -1)
            System.out.println("NEW ROW ADDED IN USERS. SQLITEDB LINE 122 "+rowInserted);
        else
            System.out.println("NEW ROW NOT ADDED IN USERS. SQLITEDB LINE 124 "+rowInserted);

        db.close();
        //getting all users in the db and printing in console to verify
        getAllUsers();
    }

    //Writing infromation to the Income table
    //This method is invoked whenever the save button on the income page is pressed
    public void writeIncomeToDB(String username,String name, String amount, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USERNAME, username);
        values.put(INC_NAME, name);
        values.put(INC_AMOUNT, amount);
        values.put(INC_DATE,date);
       // values.put(INC_RECURRENCE,recurrence);

        // Inserting Row
        long rowInserted=db.insert(INC_TABLE, null, values);

        //Checking if row was inserted into db table
        if(rowInserted != -1)
            System.out.println("NEW ROW ADD. SQLITEDB LINE 178. ID: "+rowInserted);
        else
            System.out.println("NEW ROW NOT ADD. SQLITEDB LINE 180. ID: "+rowInserted);

        db.close(); // Closing database connection
    }

    //Saving information to the Expense table
    //This method runs whenever the save button on the expense page is pressed
    public void writeExpenseToDB(String username,String name,String amount, String date, String category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EXP_TYPE, category);
        values.put(USERNAME, username);
        values.put(EXP_NAME, name);
        values.put(EXP_AMOUNT, amount);
        values.put(EXP_DATE,date);
      //  values.put(EXP_RECURRENCE,recurrence);

        // Inserting Row
        long rowInserted=db.insert(EXP_TABLE, null, values);
        //Checking if row was inserted
        if(rowInserted != -1)
            System.out.println("NEW ROW ADD. SQLITEDB LINE 178. ID: "+rowInserted);
        else
            System.out.println("NEW ROW NOT ADD. SQLITEDB LINE 180. ID: "+rowInserted);

        db.close(); // Closing database connection
    }
    //checking is user exists in the user table
    public boolean checkIfUserExists(String email){
        SQLiteDatabase db = this.getWritableDatabase();
      //  String Query = "SELECT * FROM " + USER_TABLE+" WHERE "+USERNAME+"=?",email = null;
        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE+" WHERE "+USERNAME+"=?",new String[]{email});
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    // Getting All Users
    public List getAllUsers() {
        List users_table_content = new ArrayList();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + USER_TABLE+" ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    int userID = cursor.getInt(0);
                    String users = cursor.getString(1);
                    String expense = cursor.getString(2);
                    String income = cursor.getString(3);
                    System.out.println("CONTENT OF TABLE USER IS ");
                    System.out.println(users + "ID IS " + userID);
                    System.out.println(expense + " total expense is");
                    System.out.println(income + " total income is ");
                    users_table_content.add(userID);
                    users_table_content.add(users);
                    users_table_content.add(expense);
                    users_table_content.add(income);
                } while (cursor.moveToNext());
            }
            // return users list
            return users_table_content;

    }

    // Getting All Expenses
    public List getAllExpenses() {
        System.out.println("you are in getAllExpenses in SQLITEDB LIN 220");
        List ExpenseTableContents = new ArrayList();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + EXP_TABLE+" ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int userID=cursor.getInt(0);String I=cursor.getString(1);String J=cursor.getString(2);
                String K=cursor.getString(3);String L=cursor.getString(4);String M=cursor.getString(5);
                String N=cursor.getString(6);
                ExpenseTableContents.add(userID);ExpenseTableContents.add(I);ExpenseTableContents.add(J);ExpenseTableContents.add(K);
                ExpenseTableContents.add(L);ExpenseTableContents.add(M);ExpenseTableContents.add(N);
                //logging contents to console
                System.out.println("CONTENT OF TABLE EXPENSE IS ");
                System.out.println("EXP_ID "+userID); System.out.println("USERNAME "+I);System.out.println("EXP_TYPE "+J );
                System.out.println("EXP_NAME "+K);System.out.println("EXP_DATE "+L); System.out.println("EXP_AMOUNT "+M);
                System.out.println("EXP_RECCURRENCE "+N);

            } while (cursor.moveToNext());
        }
        return ExpenseTableContents;
    }
    // Getting All Income table values
    public List getAllIncome() {
        System.out.println("you are in getAllIncome in SQLITEDB LIN 245");
        List IncomeTableContents = new ArrayList();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + INC_TABLE+" ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int userID=cursor.getInt(0);String I=cursor.getString(1);String J=cursor.getString(2);
                String K=cursor.getString(3);String L=cursor.getString(4);String M=cursor.getString(5);
                IncomeTableContents.add(userID);IncomeTableContents.add(I);IncomeTableContents.add(J);
                IncomeTableContents.add(K);IncomeTableContents.add(L);IncomeTableContents.add(M);
                //logging contents to console
                System.out.println("CONTENT OF TABLE EXPENSE IS ");
                System.out.println("inc_ID "+userID); System.out.println("USERNAME "+I);System.out.println("INC_NAME "+J );
                System.out.println("INC_DATE "+K);System.out.println("INC_AMOUNT "+L); System.out.println("INC_RECCURRENCE "+M);


            } while (cursor.moveToNext());
        }
        return IncomeTableContents;
    }



    /* ******these methods gets the total of income and expense the user inputs, respectivley***** */
    public int GetTotalIncome(String username){
        //this list has the amount of each income from the current user
        List Income_amounts=getIncomeByUser(username);
        int total=0;
        total=Integer.parseInt(Income_amounts.get(4).toString());
        for (int i=4; i<Income_amounts.size(); i+=6){
            total += Integer.parseInt(Income_amounts.get(i).toString());
        }
        return total;
    }
    public int GetTotalExpense(String username){
        List Expense_amounts=getExpenseByUser(username);
        int total=0;
        total=Integer.parseInt(Expense_amounts.get(5).toString());
        for (int i=5; i<Expense_amounts.size(); i+=7){
            total += Integer.parseInt(Expense_amounts.get(i).toString());
        }
        return total;
    }
    public int difference(int one, int bottom){
        return 100*(one/bottom);
    }







    //after total income is put into the user table, this method will retrieve it
    public int getTotalUserIncome(String username){
        int totalIncome=0;
        System.out.println("You are in getTotalUserIncome By User in SQLITEDB LINE 300");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+TOTAL_INCOME+ " FROM " + USER_TABLE+" WHERE "+USERNAME+"=?",new String[]{username});
        totalIncome=cursor.getInt(1);
        return totalIncome;
    }
    //after the total expense is put into the user table, this method wil; retrieve it
    public int getTotalUserExpense(String username){
        int totalExpense=0;
        System.out.println("You are in getotalUserExpense By User in SQLITEDB LINE 309");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+TOTAL_EXPENSE+ " FROM " + USER_TABLE+" WHERE "+USERNAME+"=?",new String[]{username});
        totalExpense=cursor.getInt(0);
        return totalExpense;
    }
    public List getExpenseByUser(String Username){
        System.out.println("You are in getExpense By User in SQLITEDB LINE 316");
        SQLiteDatabase db = this.getWritableDatabase();
        List UserExpenses = new ArrayList();
        System.out.println("You are in getExpense By User in SQLITEDB LINE 319");
        Cursor cursor = db.rawQuery("SELECT * FROM " + EXP_TABLE+" WHERE "+USERNAME+"=?",new String[]{Username});



        if(cursor.moveToFirst()){
            do{
                int user_ID=cursor.getInt(0); String user_name=cursor.getString(1);
                String expense_type=cursor.getString(2); String expense_name=cursor.getString(3);
                String expense_date=cursor.getString(4);int exp_amount=cursor.getInt(5);
                String expense_recurrence=cursor.getString(6);
                System.out.println(user_ID+"  "+user_name+"  "+expense_type+"  "+expense_name+"   "+expense_date+"  "+exp_amount+"  "+expense_recurrence);
                UserExpenses.add(user_ID);UserExpenses.add(user_name);UserExpenses.add(expense_type);UserExpenses.add(expense_name);UserExpenses.add(expense_date);
                UserExpenses.add(exp_amount);UserExpenses.add(expense_recurrence);
            }
            while(cursor.moveToNext());
        }

        System.out.println(Arrays.toString(UserExpenses.toArray()));
        return UserExpenses;
    }

    public List getIncomeByUser(String Username){
        System.out.println("You are in getIncome By User in SQLITEDB LINE 228");
        SQLiteDatabase db = this.getWritableDatabase();
        List UserIncome = new ArrayList();
        System.out.println("You are in getIncome By User in SQLITEDB LINE 341");
        Cursor cursor = db.rawQuery("SELECT * FROM " + INC_TABLE+" WHERE "+USERNAME+"=?",new String[]{Username});
        if(cursor.moveToFirst()){
            do{
                int user_ID=cursor.getInt(0); String user_name=cursor.getString(1);
                String income_type=cursor.getString(2); String income_name=cursor.getString(3);
                String income_date=cursor.getString(4);int income_amount=cursor.getInt(5);
               // String income_recurrence=cursor.getString(6);
                System.out.println(user_ID+"  "+user_name+"  "+income_type+"  "+income_name+"   "+income_date+"  "+income_amount+"  ");
                UserIncome.add(user_ID);UserIncome.add(user_name);UserIncome.add(income_type);UserIncome.add(income_name);UserIncome.add(income_date);
                UserIncome.add(income_amount);
            }
            while(cursor.moveToNext());
        }
        System.out.println(Arrays.toString(UserIncome.toArray()));
        return UserIncome;
    }



}
