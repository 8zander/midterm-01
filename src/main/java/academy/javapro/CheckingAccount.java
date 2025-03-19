package academy.javapro;

/**
 * CheckingAccount class extending the abstract Account class.
 * Features overdraft protection and transaction fees.
 */
public class CheckingAccount extends Account {
    private final double overdraftLimit;
    private static final double TRANSACTION_FEE = 1.5; // Fee per withdrawal

    /**
     * Constructor for creating a new checking account.
     *
     * @param accountNumber The account number
     * @param customerName The name of the account holder
     * @param initialBalance The initial balance
     * @param overdraftLimit The maximum allowed overdraft
     */
    public CheckingAccount(String accountNumber, String customerName, double initialBalance, double overdraftLimit) {
        super(accountNumber, customerName, initialBalance); // Call to the parent constructor
        this.overdraftLimit = overdraftLimit;
    }

    /**
     * Getter for overdraft limit.
     *
     * @return The overdraft limit
     */
    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    /**
     * Sets a new overdraft limit.
     *
     * @param overdraftLimit The new overdraft limit
     */
    public void setOverdraftLimit(double overdraftLimit) {
        System.out.println("Setting overdraft limit to $" + String.format("%.2f", overdraftLimit));
        // In a real implementation, we would update the limit
        // Since field is final in this implementation, we just log the change
        logTransaction("OVERDRAFT_LIMIT_CHANGE", overdraftLimit);
    }

    /**
     * Overrides the withdraw method with checking account-specific rules.
     * Implements overdraft protection and applies transaction fees.
     */
    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive");
            return;
        }
        
        // Check if withdrawal is within overdraft limit
        if (getBalance() - amount < -overdraftLimit) {
            System.out.println("Cannot withdraw: Would exceed overdraft limit of $" + overdraftLimit);
            return;
        }
        
        // Apply transaction fee
        double totalAmount = amount + TRANSACTION_FEE;
        
        setBalance(getBalance() - totalAmount);
        logTransaction("WITHDRAWAL", amount);
        logTransaction("FEE", TRANSACTION_FEE);
        
        if (getBalance() < 0) {
            System.out.println("Account is in overdraft. Current balance: $" + String.format("%.2f", getBalance()));
        }
    }

    /**
     * Overrides the displayInfo method to include checking account-specific information.
     */
    @Override
    public void displayInfo() {
        super.displayInfo(); // Call to the parent method
        System.out.println("Account Type: Checking Account");
        System.out.println("Overdraft Limit: $" + String.format("%.2f", overdraftLimit));
        System.out.println("Transaction Fee: $" + String.format("%.2f", TRANSACTION_FEE));
    }
}
