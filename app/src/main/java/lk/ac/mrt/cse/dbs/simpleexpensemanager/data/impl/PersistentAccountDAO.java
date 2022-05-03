package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.DataBase;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class PersistentAccountDAO extends DataBase implements AccountDAO {
    public PersistentAccountDAO(Context context) {
        super(context);
    }

    @Override
    public List<String> getAccountNumbersList() {
        List<String> AccountNumberList=new ArrayList<>();
        String queryString="SELECT Account_ID FROM Account_Table;";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            do {
                String accountNumber=cursor.getString(0);
                AccountNumberList.add(accountNumber);
            }while (cursor.moveToNext());
        }
        else {

        }
        cursor.close();
        //db.close();
        return AccountNumberList;
    }

    @Override
    public List<Account> getAccountsList() {
        List<Account> AccountList=new ArrayList<>();
        String queryString="SELECT * FROM Account_Table;";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            do {
                String accountNumber=cursor.getString(0);
                String bankName=cursor.getString(1);
                String bankHolderName=cursor.getString(2);
                Double accountBalance=cursor.getDouble(3);

                Account account=new Account(accountNumber,bankName,bankHolderName,accountBalance);
                AccountList.add(account);

            }while (cursor.moveToNext());
        }
        else {

        }
        cursor.close();
        //db.close();
        return AccountList;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        String queryString="SELECT * FROM Account_Table WHERE Account_ID=" + "=\"" + accountNo +"\";";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(queryString,null);
        Account account;
        if(cursor.moveToFirst()){
            String accountNumber=cursor.getString(0);
            String bankName=cursor.getString(1);
            String bankHolderName=cursor.getString(2);
            Double accountBalance=cursor.getDouble(3);
            account=new Account(accountNumber,bankName,bankHolderName,accountBalance);
        }else{
            account = null;
        }

        db.close();
        return account;

    }

    @Override
    public void addAccount(Account account) {
        SQLiteDatabase db=this.getReadableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("Account_ID",account.getAccountNo());
        cv.put("Balance",account.getBalance());
        cv.put("Bank_Name",account.getBankName());
        cv.put("Account_Holder_Name",account.getAccountHolderName());

        long insert=db.insert(ACCOUNT_TABLE, null,cv);

        db.close();
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase db=this.getWritableDatabase();
        String queryString="DELETE FROM Account_Table WHERE Account_ID=" + "=\"" + accountNo +"\";";
        db.rawQuery(queryString,null);
        db.close();
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        SQLiteDatabase db=this.getWritableDatabase();
        Account account = this.getAccount((accountNo));
        double balance = account.getBalance();
        String queryString;
        if(expenseType==expenseType.INCOME) {

            balance = balance + amount;

        }
        else{
            balance = balance - amount;

        }
        queryString = "UPDATE Account_Table SET Balance=" + balance +  " WHERE Account_ID" + "=\"" + accountNo +"\";";

        db.execSQL(queryString);


        db.close();
    }
}
