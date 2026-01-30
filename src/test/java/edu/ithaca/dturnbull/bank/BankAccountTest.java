package edu.ithaca.dturnbull.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{  
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
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
        assertFalse(BankAccount.isEmailValid("a@.com")); //below boundary (domain starts with dot)
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
    }

}