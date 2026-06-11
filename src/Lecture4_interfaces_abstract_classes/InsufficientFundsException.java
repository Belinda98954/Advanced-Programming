package Lecture4_interfaces_abstract_classes;

/**
 * Custom Exception class to handle insufficient funds during withdrawal transactions.
 * Extends Exception to make it a checked exception, enforcing proper error handling.
 */
public class InsufficientFundsException extends Exception {

    private final double amount;
    private final double balance;

    /**
     * Constructor for InsufficientFundsException
     * @param amount the withdrawal amount that was attempted
     * @param balance the current balance in the account
     */
    public InsufficientFundsException(double amount, double balance) {
        super("Insufficient Funds: Attempted to withdraw " + amount + " but only " + balance + " available.");
        this.amount = amount;
        this.balance = balance;
    }

    /**
     * getAmount()
     * @return the amount that was attempted to be withdrawn
     */
    public double getAmount() {
        return amount;
    }

    /**
     * getBalance()
     * @return the balance at the time of the failed transaction
     */
    public double getBalance() {
        return balance;
    }
}
