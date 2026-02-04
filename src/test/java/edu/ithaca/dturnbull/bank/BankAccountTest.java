package edu.ithaca.dturnbull.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


class BankAccountTest {

    @Test
    void getBalanceTest() throws InsufficientFundsException {

        //Equivalence class: initial balance

        // Below boundary: zero balance
        BankAccount bankAccount1 = new BankAccount("a@b.com", 0);
        assertEquals(0, bankAccount1.getBalance(), 0.001);

        // At boundary: small balance
        BankAccount bankAccount2 = new BankAccount("a@b.com", 0.01);
        assertEquals(0.01, bankAccount2.getBalance(), 0.001);

        // Above boundary: normal balance
        BankAccount bankAccount3 = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount3.getBalance(), 0.001);

        // Middle of class: large balance
        BankAccount bankAccount4 = new BankAccount("a@b.com", 1000000);
        assertEquals(1000000, bankAccount4.getBalance(), 0.001);




        //Equivalence class: balance after valid withdrawals

        // Below boundary: withdraw small amount
        BankAccount bankAccount5 = new BankAccount("a@b.com", 200);
        bankAccount5.withdraw(0.01);
        assertEquals(199.99, bankAccount5.getBalance(), 0.001);

        // At boundary: withdraw full balance
        BankAccount bankAccount6 = new BankAccount("a@b.com", 200);
        bankAccount6.withdraw(200);
        assertEquals(0, bankAccount6.getBalance(), 0.001);

        // Above boundary: withdraw just under full balance
        BankAccount bankAccount7 = new BankAccount("a@b.com", 200);
        bankAccount7.withdraw(199.99);
        assertEquals(0.01, bankAccount7.getBalance(), 0.001);

        // Middle of class: multiple withdrawals
        BankAccount bankAccount8 = new BankAccount("a@b.com", 200);
        bankAccount8.withdraw(50);
        bankAccount8.withdraw(50);
        assertEquals(100, bankAccount8.getBalance(), 0.001);



 
        //Equivalence class: balance unchanged after invalid withdrawals

        // Below boundary: negative withdrawal
        BankAccount bankAccount9 = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> bankAccount9.withdraw(-1));
        assertEquals(200, bankAccount9.getBalance(), 0.001);

        // At boundary: zero withdrawal
        BankAccount bankAccount10 = new BankAccount("a@b.com", 200);
        bankAccount10.withdraw(0);
        assertEquals(200, bankAccount10.getBalance(), 0.001);

        // Above boundary: insufficient funds
        BankAccount bankAccount11 = new BankAccount("a@b.com", 200);
        assertThrows(InsufficientFundsException.class, () -> bankAccount11.withdraw(201));
        assertEquals(200, bankAccount11.getBalance(), 0.001);




        //Equivalence class: withdrawals with small float's

        // Below boundary: small withdrawal
        BankAccount bankAccount12 = new BankAccount("a@b.com", 200);
        bankAccount12.withdraw(0.0001);
        assertEquals(199.9999, bankAccount12.getBalance(), 0.001);

        // Above boundary: normal partial withdrawal
        BankAccount bankAccount14 = new BankAccount("a@b.com", 200);
        bankAccount14.withdraw(0.5);
        assertEquals(199.5, bankAccount14.getBalance(), 0.001);




        //Equivalence class: extreme balances

        // Below boundary: withdraw very small balance
        BankAccount bankAccount15 = new BankAccount("a@b.com", Double.MIN_VALUE);
        assertEquals(Double.MIN_VALUE, bankAccount15.getBalance(), 0.001);

        // At boundary: withdraw very large balance
        BankAccount bankAccount16 = new BankAccount("a@b.com", Double.MAX_VALUE);
        assertEquals(Double.MAX_VALUE, bankAccount16.getBalance(), 0.001);

        // Middle of class: large, normal balance
        BankAccount bankAccount17 = new BankAccount("a@b.com", 1000000000);
        assertEquals(1000000000, bankAccount17.getBalance(), 0.001);
    }


    @Test
    void withdrawTest() throws InsufficientFundsException {

        BankAccount bankAccount1 = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount1.getBalance(), 0.001);

        //Equivalence class: Valid withdrawals (0 <= amount <= balance)

        // At boundary: withdraw full balance
        BankAccount bankAccount2 = new BankAccount("a@b.com", 200);
        bankAccount2.withdraw(200);
        assertEquals(0, bankAccount2.getBalance(), 0.001);

        // Above boundary: just under full balance
        BankAccount bankAccount3 = new BankAccount("a@b.com", 200);
        bankAccount3.withdraw(199.99);
        assertEquals(0.01, bankAccount3.getBalance(), 0.001);

        // Middle of valid class
        BankAccount bankAccount4 = new BankAccount("a@b.com", 200);
        bankAccount4.withdraw(100);
        assertEquals(100, bankAccount4.getBalance(), 0.001);




        //Equivalence class: Invalid: amount must be >= 0

        // Below boundary: negative amount
        BankAccount bankAccount5 = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> bankAccount5.withdraw(-1));

        // At boundary: zero 
        BankAccount bankAccount6 = new BankAccount("a@b.com", 200);
        bankAccount6.withdraw(0);
        assertEquals(200, bankAccount6.getBalance(), 0.001);

        // Above boundary: smallest valid positive amount
        BankAccount bankAccount7 = new BankAccount("a@b.com", 200);
        bankAccount7.withdraw(0.01);
        assertEquals(199.99, bankAccount7.getBalance(), 0.001);




        //Equivalence class: Invalid: insufficient funds (amount > balance)

        // Below boundary: just over balance
        BankAccount bankAccount8 = new BankAccount("a@b.com", 200);
        assertThrows(InsufficientFundsException.class, () -> bankAccount8.withdraw(200.01));

        // At boundary: equal to balance (valid)
        BankAccount bankAccount9 = new BankAccount("a@b.com", 200);
        bankAccount9.withdraw(200);
        assertEquals(0, bankAccount9.getBalance(), 0.001);

        // Above boundary: middle of invalid class
        BankAccount bankAccount10 = new BankAccount("a@b.com", 200);
        assertThrows(InsufficientFundsException.class, () -> bankAccount10.withdraw(500));


 

        //Equivalence class: Multiple withdrawals (state-based equivalence class)

        // Middle of boundary: multiple valid withdrawals
        BankAccount bankAccount14 = new BankAccount("a@b.com", 200);
        bankAccount14.withdraw(50);
        bankAccount14.withdraw(50);
        assertEquals(100, bankAccount14.getBalance(), 0.001);

        // At boundary: withdraw remaining balance after previous withdrawals
        BankAccount bankAccount15 = new BankAccount("a@b.com", 200);
        bankAccount15.withdraw(150);
        bankAccount15.withdraw(50);
        assertEquals(0, bankAccount15.getBalance(), 0.001);

        // Below boundary: attempt to withdraw after balance is zero
        BankAccount bankAccount16 = new BankAccount("a@b.com", 200);
        bankAccount16.withdraw(200);
        assertThrows(InsufficientFundsException.class, () -> bankAccount16.withdraw(1));




        //Equivalence class: Very small float withdrawals

        // Below boundary: too small to matter (treated as valid)
        BankAccount bankAccount17 = new BankAccount("a@b.com", 200);
        bankAccount17.withdraw(0.0001);
        assertEquals(199.9999, bankAccount17.getBalance(), 0.001);

        // At boundary: smallest representable positive double
        BankAccount bankAccount18 = new BankAccount("a@b.com", 200);
        bankAccount18.withdraw(Double.MIN_VALUE);
        assertEquals(200 - Double.MIN_VALUE, bankAccount18.getBalance(), 0.001);

        // Above boundary: small but normal amount
        BankAccount bankAccount19 = new BankAccount("a@b.com", 200);
        bankAccount19.withdraw(0.5);
        assertEquals(199.5, bankAccount19.getBalance(), 0.001);




        //Equivalence class: Very large withdrawals (within balance)

        // Middle: large valid withdrawal
        BankAccount bankAccount20 = new BankAccount("a@b.com", 1_000_000);
        bankAccount20.withdraw(500_000);
        assertEquals(500_000, bankAccount20.getBalance(), 0.001);

        // At boundary: withdraw entire large balance
        BankAccount bankAccount21 = new BankAccount("a@b.com", 1_000_000);
        bankAccount21.withdraw(1_000_000);
        assertEquals(0, bankAccount21.getBalance(), 0.001);

        // Above boundary: slightly more than balance
        BankAccount bankAccount22 = new BankAccount("a@b.com", 1_000_000);
        assertThrows(InsufficientFundsException.class, () -> bankAccount22.withdraw(1_000_001));
        
        BankAccount negTest = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> negTest.withdraw(-5));

        BankAccount decimalTest = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> decimalTest.withdraw(1.234));
    }

    @Test
    void isEmailValidTest(){

        //all valid emails
        assertTrue(BankAccount.isEmailValid("a@b.com"));
        assertTrue(BankAccount.isEmailValid("glintwave92@orbimail.net"));
        assertTrue(BankAccount.isEmailValid("saffron.loop@tessera.io"));
        assertTrue(BankAccount.isEmailValid("emberlane@postcircuit.app"));

        //Equivalence class: tld
        assertFalse(BankAccount.isEmailValid("a@b"));       // equivalence class: bad domain - no tld
        assertFalse(BankAccount.isEmailValid("a@b."));      // equivalence class: bad domain - zero char tld / border case

        //Equivalence class: domain
        assertFalse(BankAccount.isEmailValid("a@.com"));    // equivalence class: bad structure - missing domain / border case

        //Equivalence class: local
        assertTrue(BankAccount.isEmailValid("a@b.com"));    // equivalence class: bad structure - missing local part / border case

        //Equivalence class: character issue
        assertFalse(BankAccount.isEmailValid(""));          // empty string
        assertFalse(BankAccount.isEmailValid("a.b.com"));   // equivalence class: bad structure - no @
        assertFalse(BankAccount.isEmailValid("a@b..com"));  // equivalence class: bad domain - double dot

        //Equivalence class: Structure
        assertFalse(BankAccount.isEmailValid("a"));         // equivalence class: bad structure - single char / border case
        assertFalse(BankAccount.isEmailValid("@"));         // equivalence class: bad structure - zero char local and domain / border case

        // Equivalence class: local part length
        assertTrue(BankAccount.isEmailValid("ab@b.com")); // above boundary
        assertTrue(BankAccount.isEmailValid("username@b.com")); // middle of boudary


        // Equivalence class: domain must contain dot
        assertFalse(BankAccount.isEmailValid("@ab.c")); // below boundary
        assertTrue(BankAccount.isEmailValid("a@bc.de")); // above boundary
        assertTrue(BankAccount.isEmailValid("a@example.com")); // middle of boundary


        // Equivalence class: dot placement in local part
        assertFalse(BankAccount.isEmailValid(".a@b.com")); //below boundary (leading dot)
        assertTrue(BankAccount.isEmailValid("a.b@b.com")); //at boundary
        assertTrue(BankAccount.isEmailValid("ab.cd@b.com")); //above boundary
        assertTrue(BankAccount.isEmailValid("first.last@example.com")); //middle of boundary


        // Equivalence class: dot placement in domain
        assertFalse(BankAccount.isEmailValid("a@.com")); //below boundary (domain starts with dot)777   
        assertTrue(BankAccount.isEmailValid("a@b.cd")); //above boundary
        assertTrue(BankAccount.isEmailValid("a@example.com")); //middle of boundary


        // Equivalence class: hyphen placement in domain
        assertFalse(BankAccount.isEmailValid("a@-example.com")); //below boundary (domain starts with hyphen)
        assertTrue(BankAccount.isEmailValid("a@ex-ample.com")); //at boundary
        assertTrue(BankAccount.isEmailValid("a@exa-mple.com")); //above boundary
        assertTrue(BankAccount.isEmailValid("a@example.com")); //middle of boundary


        // Equivalence class: number of @ symbols
        assertFalse(BankAccount.isEmailValid("a@@b.com")); //above boundary
        assertTrue(BankAccount.isEmailValid("first.last@example.com")); //middle of boundary


        // Equivalence class: illegal characters – below boundary (space in local part)
        assertFalse(BankAccount.isEmailValid("a b@c.com")); //below boundary
        assertTrue(BankAccount.isEmailValid("a_b@c.com")); //at boundary
        assertTrue(BankAccount.isEmailValid("a-b@c.com")); //above boundary
        assertTrue(BankAccount.isEmailValid("user.name@example.com")); //middle of boundary
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));

        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", -1));

        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", 1.234));
    }


    @Test
    void isAmountValidBoundaryTest() {

        //Equivalence class: valid/invalid amounts (Boundary at 0)
        assertFalse(BankAccount.isAmountValid(-0.01));   // below boundary
        assertTrue(BankAccount.isAmountValid(0.00));     // at boundary
        assertTrue(BankAccount.isAmountValid(0.01));     // above boundary


        //Equivalence class: decimal accuracy
        assertTrue(BankAccount.isAmountValid(1.10));     // at boundary
        assertFalse(BankAccount.isAmountValid(1.001));   // below boundary
        assertTrue(BankAccount.isAmountValid(1.1));     // above boundary


        //Equivalence class: testing valid amounts
        assertTrue(BankAccount.isAmountValid(5));
        assertTrue(BankAccount.isAmountValid(5.1));
        assertTrue(BankAccount.isAmountValid(5.12));


        //Equivalence class: 0 amount
        assertTrue(BankAccount.isAmountValid(0));        // at boundary
        assertFalse(BankAccount.isAmountValid(-0.0001)); // below boundary
        assertTrue(BankAccount.isAmountValid(0.01));     // above boundary


        //Equivalence class: testing large amounts
        assertTrue(BankAccount.isAmountValid(999999.99)); //at boundary
        assertFalse(BankAccount.isAmountValid(999999.999)); // above boundary (too many decimals)
    }

}