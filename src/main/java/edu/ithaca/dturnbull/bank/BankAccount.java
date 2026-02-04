package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * @throws InsufficientFundsException if amount is greater than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (amount < 0){
            throw new IllegalArgumentException("Amount must be non-negative");
        }
        if (amount > balance){
            throw new InsufficientFundsException("Not enough money");
        }

        balance -= amount;
    }



    public static boolean isEmailValid(String email){
        if (email.length() < 4){
            return false;
        }

        String emailRegex = "^([\\w+'!#%&*=/-]+(?:\\.[\\w+'!#%&*=/-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,})$";
        
        return email.matches(emailRegex);
    }


    /**
     * Returns true if the amount is non-negative and has at most two decimal places.
     * Returns false otherwise.
     */
    public static boolean isAmountValid(double amount) {

        if (amount < 0 || Double.isNaN(amount) || Double.isInfinite(amount)) {
            return false;
        }

        double cents = Math.round(amount * 100);

        if (Math.abs(cents - amount * 100) > 0.0000001) {
            return false;
        }

        return true;
    }

    /**
     * Deposits the given amount into this bank account.
     * @param amount: amount of money to deposit
     * @throws IllegalArgumentException if the amount is invalid
     */
    public void deposit(double amount) {

    if (amount < 0 || Double.isNaN(amount) || Double.isInfinite(amount)) {
        throw new IllegalArgumentException("Invalid deposit amount");
    }

    double cents = amount * 100;
    if (cents != Math.floor(cents)) {
        throw new IllegalArgumentException("Invalid deposit amount");
    }

    balance += amount;
}

    /**
     * Transfers the given amount from this bank account to the specified destination account.
     * @param amount: amount of money to transfer
     * @param destination: the account to receive the funds
     * @throws IllegalArgumentException: if the amount is invalid or destination is null
     * @throws InsufficientFundsException: if this account does not have enough balance
     */
    public void transfer(double amount, BankAccount destination) throws InsufficientFundsException {

    if (destination == null) {
        throw new IllegalArgumentException("Destination account cannot be null");
    }

    if (amount < 0 || Double.isNaN(amount) || Double.isInfinite(amount)) {
        throw new IllegalArgumentException("Invalid transfer amount");
    }

    double cents = amount * 100;
    if (cents != Math.floor(cents)) {
        throw new IllegalArgumentException("Invalid transfer amount");
    }

    if (amount > balance) {
        throw new InsufficientFundsException("Not enough money");
    }

    this.balance -= amount;
    destination.balance += amount;
}
}