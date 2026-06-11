package Lecture4_interfaces_abstract_classes;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

/**
 * DepositTransaction extends BaseTransaction.
 * Deposits are irreversible - once applied, they cannot be undone.
 * Overrides the apply() method to add the deposit amount to the bank account balance.
 */
public class DepositTrasaction extends BaseTransaction {
    public DepositTrasaction(int amount, @NotNull Calendar date){
        super(amount, date);
    }
    private boolean checkDepositAmount(int amt){
        if (amt < 0){
           return false;
        }
        else{
            return  true;
        }
    }

    // Method to print a transaction receipt or details
    public void printTransactionDetails(){
        System.out.println("Deposit Transaction: " + this.toString());
        System.out.println("Amount Deposited: \t" + getAmount());
        System.out.println("Transaction Date: \t" + getDate().getTime());
        System.out.println("Transaction ID: \t" + getTransactionID());
    }

    /**
     * apply(BankAccount ba)
     * Overrides BaseTransaction.apply() to add the deposit amount to the account balance.
     * This implementation differs from BaseTransaction (which only prints details) and
     * WithdrawalTransaction (which subtracts from balance).
     * @param ba the BankAccount object to apply the deposit on
     */
    @Override
    public void apply(BankAccount ba) throws InsufficientFundsException {
        double curr_balance = ba.getBalance();
        double new_balance = curr_balance + getAmount();
        ba.setBalance(new_balance);
        System.out.println("Deposit Applied Successfully. New Balance: " + ba.getBalance());
    }
}
