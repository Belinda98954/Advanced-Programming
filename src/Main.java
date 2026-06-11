import Lecture1_adt.*; // Import all classes from Lecture1_adt package to be used in this client code
import Lecture4_interfaces_abstract_classes.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
/*
 * Client Code for accessing the Lecture1_adt.TransactionInterface.java module
 */
public class Main {

    public static void testTransaction1() {
        Calendar d1 = new GregorianCalendar(); // d1 is an Object [Objects are Reference types]
        Lecture1_adt.Transaction1 t1 = new Lecture1_adt.Transaction1(1000, d1); // amount and d1 are arguments

        System.out.println(t1.toString());
        System.out.println("Lecture1_adt.TransactionInterface Amount: \t " + t1.amount);
        System.out.println("Lecture1_adt.TransactionInterface Date: \t " + t1.date);

        // Please note that the Client Codes can access the data in the class directly through the dot operator
        // This kind of exposure is a threat to both the Representation Independence and Preservation of Invariants
    }


    /** @return a transaction of same amount as t, one month later
     * This is a PRODUCER of the class Lecture1_adt.Transaction2
     * This code will help demostrate the Design exposures still present in transaction2 class
     * */

    public static Transaction2 makeNextPayment(Transaction2 t) {
        Calendar d =  t.getDate();
        d.add(Calendar.MONTH, 1);
        return new Transaction2(t.getAmount(), d);
    }

    /*
    Testing Transaction2 class
     */
    public static void testTransaction2() {

        Calendar d1 = new GregorianCalendar();

        Lecture1_adt.Transaction2 t = new Lecture1_adt.Transaction2(1000, d1);

        Lecture1_adt.Transaction2 modified_t = makeNextPayment(t);

        System.out.println("\n\nState of the Object T1 After Client Code Tried to Change the Amount");
        System.out.println("Lecture1_adt.TransactionInterface Amount: \t "+modified_t.getAmount());
        System.out.println("Lecture1_adt.TransactionInterface Date: \t "+modified_t.getDate().getTime());

        System.out.println("\n\nHow does T2 Look Like?????");
        System.out.println("Lecture1_adt.TransactionInterface Amount: \t "+modified_t.getAmount());
        System.out.println("Lecture1_adt.TransactionInterface Date: \t "+modified_t.getDate().getTime());

        /* Please note that Although we have solved the problem of Transaction1
        * And client code can no longer use the dot (.) operator to directly access the data
        * There is still some exposure especially if we pass an object of a previous Transaction2 to create a new Transaction2 object
         */

    }


    /** @return a list of 12 monthly payments of identical amounts
     * This code will help demostrate the Design exposures still present in transaction3 class
     * */
    public static List<Transaction3> makeYearOfPayments (int amount) throws NullPointerException {

        List<Transaction3> listOfTransaction3s = new ArrayList<Transaction3>();
        Calendar date = new GregorianCalendar(2024, Calendar.JANUARY, 3);


        for (int i = 0; i < 12; i++) {
            listOfTransaction3s.add(new Transaction3(amount, date));
            date.add(Calendar.MONTH, 1);
        }
        return listOfTransaction3s;
    }

    /*
    Testing Transaction3 class
     */
    public static void testTransaction3() {

        List<Transaction3> allPaymentsIn2024 = makeYearOfPayments(1000);

        for (Transaction3 t3 : allPaymentsIn2024) {

            // Display all the 12 Transactions
            for (Transaction3 transact : allPaymentsIn2024) {
                System.out.println("\n\n  ::::::::::::::::::::::::::::::::::::::::::::\n");
                System.out.println("Lecture1_adt.TransactionInterface Amount: \t "+transact.getAmount());
                System.out.println("Lecture1_adt.TransactionInterface Date: \t "+transact.getDate().getTime());
            }
        }

        /* Please Check all the 12 transactions displayed and hwo their dates look like
         * Note that Although Transaction3 class resolves to an extent the exposure in Transaction2 class
         * There is still some exposure especially if we pass an object of a previous Transaction3 to create a
         * new Transaction3 object
         */
    }


    /** @return a list of 12 monthly payments of identical amounts
     * This code Show that by judicious copying and defensive programming we eliminate the exposure in Transaction3
     * As defined in the constructor of Transaction4 class
     * */

    public static List<Transaction4> makeYearOfPaymentsFinal (int amount) throws NullPointerException {

        List<Transaction4> listOfTransaction4s = new ArrayList<Transaction4>();
        Calendar date = new GregorianCalendar(2024, Calendar.JANUARY, 3);


        for (int i = 0; i < 12; i++) {
            listOfTransaction4s.add(new Transaction4(amount, date));
            date.add(Calendar.MONTH, 1);
        }
        return listOfTransaction4s;
    }

    /*
    Testing Transaction3 class
     */
    public static void testTransaction4() {

        /*
         * Call the function to make all the Twelve transaction in a year of our business
         */

        List<Transaction4> transactionsIn2024 = makeYearOfPaymentsFinal(1200);

        // Display all the 12 Transactions
        for (Transaction4 transact : transactionsIn2024) {
            System.out.println("\n\n  ::::::::::::::::::::::::::::::::::::::::::::\n");
            System.out.println("Lecture1_adt.TransactionInterface Amount: \t "+transact.getAmount());
            System.out.println("Lecture1_adt.TransactionInterface Date: \t "+transact.getDate().getTime());
        }

        // Please Take a look at all the 12 transaction now and compare with the outputs of the Transaction3 class
    }


    /*
     * ====================================================================
     * Assignment 1 - Question 4: Client Code to test Assignment classes
     * ====================================================================
     */

    /**
     * Test the DepositTransaction class
     * Creates a DepositTransaction, applies it to a BankAccount, and prints details.
     */
    public static void testDepositTransaction() {
        System.out.println("=============================================");
        System.out.println("   Testing DepositTransaction");
        System.out.println("=============================================");

        // Create a BankAccount with initial balance of 1000
        BankAccount account = new BankAccount(1000);
        System.out.println("Initial Balance: \t" + account.getBalance());

        // Create a DepositTransaction of 500
        Calendar date = new GregorianCalendar();
        DepositTrasaction deposit = new DepositTrasaction(500, date);

        // Print the transaction details
        deposit.printTransactionDetails();

        // Apply the deposit to the bank account
        try {
            deposit.apply(account);
        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Balance After Deposit: \t" + account.getBalance());

        System.out.println("\n--- Testing Polymorphism: Type casting DepositTransaction to BaseTransaction ---");

        // Type casting: Assign the subtype object to a base type reference (early vs late binding)
        BaseTransaction baseRef = deposit; // Upcasting - subtype to base type
        System.out.println("\nCalling apply() through BaseTransaction reference (late binding):");
        try {
            baseRef.apply(new BankAccount(2000)); // Late binding: calls DepositTrasaction.apply()
        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Test the WithdrawalTransaction class
     * Tests normal withdrawal, insufficient funds exception, partial withdrawal, and reversal.
     */
    public static void testWithdrawalTransaction() {
        System.out.println("\n\n=============================================");
        System.out.println("   Testing WithdrawalTransaction");
        System.out.println("=============================================");

        // Create a BankAccount with initial balance of 1000
        BankAccount account = new BankAccount(1000);
        System.out.println("Initial Balance: \t" + account.getBalance());

        // --- Test 1: Normal Withdrawal ---
        System.out.println("\n--- Test 1: Normal Withdrawal of 300 ---");
        Calendar date1 = new GregorianCalendar();
        WithdrawalTransaction withdrawal1 = new WithdrawalTransaction(300, date1);
        withdrawal1.printTransactionDetails();
        try {
            withdrawal1.apply(account);
        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Balance After Withdrawal: \t" + account.getBalance());

        // --- Test 2: Reversal of the Withdrawal ---
        System.out.println("\n--- Test 2: Reversing the Withdrawal of 300 ---");
        boolean reversed = withdrawal1.reverse();
        System.out.println("Reversal Successful: \t" + reversed);
        System.out.println("Balance After Reversal: \t" + account.getBalance());

        // --- Test 3: Withdrawal with Insufficient Funds (throws exception) ---
        System.out.println("\n--- Test 3: Withdrawal of 5000 with Insufficient Funds (throws) ---");
        Calendar date2 = new GregorianCalendar();
        WithdrawalTransaction withdrawal2 = new WithdrawalTransaction(5000, date2);
        try {
            withdrawal2.apply(account);
        } catch (InsufficientFundsException e) {
            System.out.println("Exception Caught: " + e.getMessage());
        }
        System.out.println("Balance After Failed Withdrawal: \t" + account.getBalance());

        // --- Test 4: Partial Withdrawal using overloaded apply() with try-catch-finally ---
        System.out.println("\n--- Test 4: Partial Withdrawal of 5000 using overloaded apply() ---");
        Calendar date3 = new GregorianCalendar();
        WithdrawalTransaction withdrawal3 = new WithdrawalTransaction(5000, date3);
        withdrawal3.apply(account, true);
        System.out.println("Amount Not Withdrawn: \t" + withdrawal3.getAmountNotWithdrawn());

        // --- Test 5: Polymorphism - Type casting to BaseTransaction ---
        System.out.println("\n--- Test 5: Polymorphism - Type Casting WithdrawalTransaction to BaseTransaction ---");
        BankAccount account2 = new BankAccount(2000);
        Calendar date4 = new GregorianCalendar();
        WithdrawalTransaction withdrawal4 = new WithdrawalTransaction(500, date4);

        // Upcasting: subtype object to base type reference
        BaseTransaction baseRef = withdrawal4;
        System.out.println("Calling apply() through BaseTransaction reference (late binding):");
        try {
            baseRef.apply(account2); // Late binding: calls WithdrawalTransaction.apply()
        } catch (InsufficientFundsException e) {
            System.out.println("Exception Caught: " + e.getMessage());
        }
        System.out.println("Balance After Polymorphic Withdrawal: \t" + account2.getBalance());
    }

    /**
     * Test the BaseTransaction class directly
     * Shows how the BaseTransaction.apply() differs from subclass implementations.
     */
    public static void testBaseTransaction() {
        System.out.println("\n\n=============================================");
        System.out.println("   Testing BaseTransaction (directly)");
        System.out.println("=============================================");

        BankAccount account = new BankAccount(1000);
        Calendar date = new GregorianCalendar();
        BaseTransaction baseTransaction = new BaseTransaction(100, date);

        System.out.println("Initial Balance: \t" + account.getBalance());
        baseTransaction.printTransactionDetails();

        try {
            baseTransaction.apply(account);
        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Balance After Base Apply: \t" + account.getBalance());
        // Note: BaseTransaction.apply() does NOT change the balance - it only prints details
    }


    public static void main(String[] args) {
        // This is the client code
        // Uncomment the following lines to test the class which you would like to test

        // testTransaction1()
        // testTransaction2()
        // testTransaction3()
        // testTransaction4()

        /*
         * Assignment 1 - Question 4: Test the Assignment classes
         */
        testBaseTransaction();
        testDepositTransaction();
        testWithdrawalTransaction();
    }
}