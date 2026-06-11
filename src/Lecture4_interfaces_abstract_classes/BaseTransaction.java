package Lecture4_interfaces_abstract_classes;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

/**
 * Concrete class that implements the TransactionInterface.
 * This class provides a base implementation for all transaction types.
 * Subclasses (DepositTransaction, WithdrawalTransaction) override the apply() method
 * to provide specific transaction behavior (polymorphism via late binding).
 */
public class BaseTransaction implements TransactionInterface {
    private final int amount;
    private final Calendar date;
    private final String transactionID;

    /**
     * Lecture1_adt.TransactionInterface Constructor
     * @param amount in an integer
     * @param date: Not null, and must be a Calendar object
     * @return void
     * Instialises the field, attributes of a transaction
     * Creates a object of this
     */
    public BaseTransaction(int amount, @NotNull Calendar date)  {
        this.amount = amount;
        this.date = (Calendar) date.clone();
        int uniq = (int) Math.random()*10000;
        transactionID = date.toString()+uniq;
    }

    /**
     * getAmount()
     * @return double - the transaction amount
     */
    public double getAmount() {
        return amount; // Because we are dealing with Value types we need not worry about what we return
    }

    /**
     * getDate()
     * @return Calendar Object
     */
    public Calendar getDate() {
//        return date;    // Because we are dealing with Reference types we need to judiciously copy what our getters return
        return (Calendar) date.clone(); // Defensive copying or Judicious Copying
    }

    // Method to get a unique identifier for the transaction
    public String getTransactionID(){
        return  transactionID;
    }

    /**
     * printTransactionDetails()
     * Prints out the details of this transaction including the amount, date, and transaction ID.
     */
    public void printTransactionDetails(){
        System.out.println("Transaction Details:");
        System.out.println("Transaction ID: \t" + transactionID);
        System.out.println("Transaction Amount: \t" + amount);
        System.out.println("Transaction Date: \t" + date.getTime());
    }

    /**
     * apply(BankAccount ba)
     * Base implementation of apply - simply prints the transaction details.
     * This differs from DepositTransaction (which adds to balance) and
     * WithdrawalTransaction (which subtracts from balance).
     * Subclasses override this method to provide specific behavior (late binding).
     * @param ba the BankAccount object to apply the transaction on
     */
    public void apply(BankAccount ba) throws InsufficientFundsException {
        System.out.println("Base Transaction Applied:");
        printTransactionDetails();
        System.out.println("Current Balance After Base Apply: \t" + ba.getBalance());
    }
}
