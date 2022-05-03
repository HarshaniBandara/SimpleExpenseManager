package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.DataBase;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class PersistentTransactionDAO extends DataBase implements TransactionDAO {
    public PersistentTransactionDAO(Context context) {

        super(context);
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        Transaction transaction = new Transaction(date, accountNo, expenseType, amount);
        SQLiteDatabase db=this.getReadableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("Date", String.valueOf(date));
        cv.put("Account_ID ",accountNo);
        cv.put("Amount",amount);
        cv.put("Expense_Type", String.valueOf(expenseType));

        long insert=db.insert(TRANSACTION_TABLE, null,cv);

        db.close();
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        List<Transaction> AllTransactionList=new ArrayList<>();
        String queryString="SELECT * FROM Transaction_Table;";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            do {
                String TransactionID=cursor.getString(0);
                String Date=cursor.getString(1);
                String Account_ID =cursor.getString(2);
                Double Amount=cursor.getDouble(4);
                String Type=cursor.getString(5);
                ExpenseType type=ExpenseType.valueOf(Type);

                SimpleDateFormat sdf3 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

                Date date = null;
                try{
                    date = sdf3.parse(Date);

                }catch (Exception e){ e.printStackTrace(); }

                Transaction transaction=new Transaction(date,Account_ID,type,Amount);
                AllTransactionList.add(transaction);

            }while (cursor.moveToNext());
        }
        else {

        }
        cursor.close();
        db.close();
        return AllTransactionList;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        List<Transaction> AllTransactionList=new ArrayList<>();
        String queryString="SELECT * FROM Transaction_Table LIMIT " + limit + ";";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            do {
                String TransactionID=cursor.getString(0);
                String Date=cursor.getString(1);
                String Account_ID =cursor.getString(2);
                Double Amount=cursor.getDouble(3);
                String Type=cursor.getString(4);
                ExpenseType type= Type.equals("EXPENSE")? ExpenseType.EXPENSE:ExpenseType.INCOME;

                SimpleDateFormat sdf3 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

                Date date = null;
                try{
                    date = sdf3.parse(Date);

                }catch (Exception e){ e.printStackTrace(); }

                Transaction transaction=new Transaction(date,Account_ID,type,Amount);
                AllTransactionList.add(transaction);

            }while (cursor.moveToNext());
        }
        else {

        }
        cursor.close();
        db.close();
        return AllTransactionList;
    }
}
