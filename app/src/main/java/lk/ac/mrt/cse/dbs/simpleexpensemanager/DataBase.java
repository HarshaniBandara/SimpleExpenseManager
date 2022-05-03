package lk.ac.mrt.cse.dbs.simpleexpensemanager;
//implemented for access data base
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

public class DataBase extends SQLiteOpenHelper {
    public static final String ACCOUNT_TABLE = "Account_Table";
    public static final String COL_TRANSACTION_ID = "ID";
    public static final String COL_ACCOUNT_ID = "Account_ID";
    public static final String COL_BANK_NAME = "Bank_Name";
    public static final String COL_ACCOUNT_HOLDER_NAME = "Account_Holder_Name";
    public static final String COL_BALANCE = "Balance";
    public static final String TRANSACTION_TABLE = "Transaction_Table";
    public static final String COL_TRASACTION_DATE = "Date";
    public static final String COL_TRASACTION_ACCOUNT_ID = "Account_ID";
    public static final String TRANSACTION_AMOUNT = "Amount";
    public static final String COL_EXPENSE_TYPE = "Expense_Type";

    public DataBase(@Nullable Context context) {
        super(context, "miniProject.db",null, 1);
    }

    //when there is no database. when first run the code this method will be called and create the database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String queryOfCreatingTable1= "CREATE TABLE " + ACCOUNT_TABLE + "(" + COL_ACCOUNT_ID + " TEXT PRIMARY KEY," + COL_BANK_NAME + " TEXT," + COL_ACCOUNT_HOLDER_NAME + " TEXT," + COL_BALANCE + " REAL )";
        sqLiteDatabase.execSQL(queryOfCreatingTable1);
        String queryOfCreatingTable2= "CREATE TABLE " + TRANSACTION_TABLE + "(" + COL_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_TRASACTION_DATE + " DATE, " + COL_TRASACTION_ACCOUNT_ID + " REFERENCES ACCOUNT_TABLE(Account_ID) , " + TRANSACTION_AMOUNT + " REAL, " + COL_EXPENSE_TYPE + " TEXT )";
        sqLiteDatabase.execSQL(queryOfCreatingTable2);
    }
    //After creating ,used for the upgrade and modify database
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean addOne(Account account) {
        SQLiteDatabase db=this.getReadableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL_ACCOUNT_ID,account.getAccountNo());
        cv.put(COL_BALANCE,account.getBalance());
        cv.put(COL_BANK_NAME,account.getBankName());
        cv.put(COL_ACCOUNT_HOLDER_NAME,account.getAccountHolderName());

        long insert=db.insert(ACCOUNT_TABLE, null,cv);
        if(insert==-1){
            return true;
        }
        else {
            return false;
        }
    }
}