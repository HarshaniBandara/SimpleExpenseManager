/*
 * Copyright 2015 Department of Computer Science and Engineering, University of Moratuwa.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *                  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package lk.ac.mrt.cse.dbs.simpleexpensemanager;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.ExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.PersistentExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

import static org.junit.Assert.assertTrue;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest {
    private static ExpenseManager expenseManager;

    @Before
    public void setUp() throws ExpenseManagerException {
        Context context = ApplicationProvider.getApplicationContext();
        expenseManager = new PersistentExpenseManager(context);
    }

    @Test
    public void testAddAccount() throws InvalidAccountException {



        expenseManager.addAccount("190088","Pan Asia","Harshani",10000);
        List<String> accountNumberList = expenseManager.getAccountNumbersList();
        assertTrue(accountNumberList.contains("190088"));
    }

    @Test
    public void testAddTransactionExpense() throws ParseException, InvalidAccountException {
        String accountNumber="190089";
        String expensesAmount="1000.00";


        double currentBalance = expenseManager.getAccountsDAO().getAccount(accountNumber).getBalance();
        expenseManager.updateAccountBalance(accountNumber,24, 4, 2022, ExpenseType.EXPENSE,
                expensesAmount) ;


        double newBalance=expenseManager.getAccountsDAO().getAccount(accountNumber).getBalance();
        assertTrue(newBalance==-(Double.parseDouble(expensesAmount))+currentBalance);



//

    }
    @Test
    public void testAddTransactionIncome() throws ParseException, InvalidAccountException {


        String accountNumber="190089";
        String incomeAmount="1000.00";

        double currentBalance = expenseManager.getAccountsDAO().getAccount(accountNumber).getBalance();
        expenseManager.updateAccountBalance(accountNumber,24, 4, 2022, ExpenseType.INCOME,
                incomeAmount) ;


        double newBalance=expenseManager.getAccountsDAO().getAccount(accountNumber).getBalance();
        assertTrue(newBalance==(Double.parseDouble(incomeAmount))+currentBalance);

    }
}