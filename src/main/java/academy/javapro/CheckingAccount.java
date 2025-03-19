package academy.javapro;

/**
 * CheckingAccount class extending the abstract Account class.
 * Features overdraft protection and transaction fees.
 */
public class CheckingAccount extends Account {
    // Had to get rid of the final keyword to be able to change the overdraft limit
    private double overdraftLimit;
    private static final double TRANSACTION_FEE = 1.5; // Fee per withdrawal

    /**
     * Constructor for creating a new checking account.
     *
     * @param accountNumber  The account number
     * @param customerName   The name of the account holder
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
        this.overdraftLimit = overdraftLimit;
        System.out.printf("Overdraft limit updated to $%.2f%n", overdraftLimit);
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

        double totalAmount = amount + TRANSACTION_FEE;
        if (getBalance() - totalAmount < -overdraftLimit) {
            System.out.println("Withdrawal failed: Exceeds overdraft limit");
            return;
        }

        setBalance(getBalance() - totalAmount);
        System.out.printf("Withdrew $%.2f from checking account%n", amount);
        System.out.printf("Transaction fee: $%.2f%n", TRANSACTION_FEE);
        if (getBalance() < 0) {
            System.out.printf("Account is in overdraft. Current balance: $%.2f%n", getBalance());
        }
        logTransaction("WITHDRAWAL", amount);
        logTransaction("FEE", TRANSACTION_FEE);
    }

    /**
     * Overrides the displayInfo method to include checking account-specific
     * information.
     */
    @Override
    public void displayInfo() {
        super.displayInfo(); // Call to the parent method
        System.out.println("Account Type: Checking Account");
        System.out.println("Overdraft Limit: $" + String.format("%.2f", overdraftLimit));
        System.out.println("Transaction Fee: $" + String.format("%.2f", TRANSACTION_FEE));
    }
}
