package Lecture4_interfaces_abstract_classes;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

/**
 * WithdrawalTransaction extends BaseTransaction.
 * Withdrawals can be reversed - the reverse() method restores the bank account balance.
 * Overrides the apply() method to subtract the withdrawal amount from the bank account balance.
 * Implements exception handling for insufficient funds.
 */
public class WithdrawalTransaction extends BaseTransaction {

    private double amountNotWithdrawn; // Keeps a record of the amount not withdrawn
    private boolean applied;           // Tracks whether this transaction has been applied
    private BankAccount targetAccount; // Reference to the account the transaction was applied on

    public WithdrawalTransaction(int amount, @NotNull Calendar date) {
        super(amount, date);
        this.amountNotWithdrawn = 0;
        this.applied = false;
        this.targetAccount = null;
    }

    private boolean checkWithdrawalAmount(int amt) {
        if (amt < 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * reverse()
     * Reverses the withdrawal transaction by restoring the balance to the bank account.
     * Only succeeds if the transaction was previously applied.
     * @return true if reversal was successful, false otherwise
     */
    public boolean reverse() {
        if (!applied || targetAccount == null) {
            System.out.println("Withdrawal Transaction has not been applied yet. Cannot reverse.");
            return false;
        }

        // Restore the withdrawn amount (which is getAmount() - amountNotWithdrawn)
        double withdrawnAmount = getAmount() - amountNotWithdrawn;
        double currentBalance = targetAccount.getBalance();
        targetAccount.setBalance(currentBalance + withdrawnAmount);
        System.out.println("Withdrawal Reversed Successfully. New Balance: " + targetAccount.getBalance());
        applied = false;
        return true;
    }

    // Method to print a transaction receipt or details
    public void printTransactionDetails() {
        System.out.println("Withdrawal Transaction: " + this.toString());
        System.out.println("Amount Withdrawn: \t" + getAmount());
        System.out.println("Transaction Date: \t" + getDate().getTime());
        System.out.println("Transaction ID: \t" + getTransactionID());
        if (amountNotWithdrawn > 0) {
            System.out.println("Amount Not Withdrawn: \t" + amountNotWithdrawn);
        }
    }

    /**
     * apply(BankAccount ba)
     * Overrides BaseTransaction.apply() to subtract the withdrawal amount from the account balance.
     * Uses the throws keyword to handle InsufficientFundsException when the balance
     * is less than the withdrawal amount.
     * @param ba the BankAccount object to apply the withdrawal on
     * @throws InsufficientFundsException if the balance is less than the withdrawal amount
     */
    @Override
    public void apply(BankAccount ba) throws InsufficientFundsException {
        double curr_balance = ba.getBalance();
        if (curr_balance < getAmount()) {
            throw new InsufficientFundsException(getAmount(), curr_balance);
        }
        double new_balance = curr_balance - getAmount();
        ba.setBalance(new_balance);
        this.applied = true;
        this.targetAccount = ba;
        System.out.println("Withdrawal Applied Successfully. New Balance: " + ba.getBalance());
    }

    /**
     * Overloaded apply() method (Q3)
     * Not only checks if the balance covers the withdrawal amount but also checks
     * if the balance is greater than 0. In the case when 0 < balance < withdrawal amount,
     * it withdraws all the balance and keeps a record of the amount not withdrawn.
     * Implements exception handling using try{...} catch{...} finally{...} block.
     * @param ba the BankAccount object to apply the withdrawal on
     * @param checkAvailableBalance flag to indicate that this overloaded version should be used
     */
    public void apply(BankAccount ba, boolean checkAvailableBalance) {
        double curr_balance = ba.getBalance();

        try {
            // Check if the balance covers the withdrawal amount
            if (curr_balance < getAmount()) {
                throw new InsufficientFundsException(getAmount(), curr_balance);
            }

            // Sufficient funds - withdraw the full amount
            double new_balance = curr_balance - getAmount();
            ba.setBalance(new_balance);
            this.amountNotWithdrawn = 0;
            System.out.println("Withdrawal Applied Successfully. New Balance: " + ba.getBalance());

        } catch (InsufficientFundsException e) {
            // If the balance is greater than 0 but less than the withdrawal amount
            // Withdraw all the available balance and keep a record of the amount not withdrawn
            if (curr_balance > 0) {
                this.amountNotWithdrawn = getAmount() - curr_balance;
                ba.setBalance(0);
                System.out.println("Partial Withdrawal: Only " + curr_balance + " was available.");
                System.out.println("Amount Not Withdrawn: " + amountNotWithdrawn);
            } else {
                this.amountNotWithdrawn = getAmount();
                System.out.println("Error: " + e.getMessage());
            }
        } finally {
            this.applied = true;
            this.targetAccount = ba;
            System.out.println("Transaction Complete. Final Balance: " + ba.getBalance());
        }
    }

    /**
     * getAmountNotWithdrawn()
     * @return the amount that could not be withdrawn due to insufficient funds
     */
    public double getAmountNotWithdrawn() {
        return amountNotWithdrawn;
    }
}
