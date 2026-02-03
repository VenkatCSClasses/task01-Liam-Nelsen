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

        return false;
    }
}