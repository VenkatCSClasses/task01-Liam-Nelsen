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
        assertTrue(BankAccount.isEmailValid("a@b.com"));   // valid email address
        assertTrue(BankAccount.isEmailValid("glintwave92@orbimail.net"));
        assertTrue(BankAccount.isEmailValid("saffron.loop@tessera.io"));
        assertTrue(BankAccount.isEmailValid("emberlane@postcircuit.app"));
        assertFalse(BankAccount.isEmailValid(""));          // empty string
        assertFalse(BankAccount.isEmailValid("a@b"));       // equivalence class: bad domain - no tld
        assertFalse(BankAccount.isEmailValid("a.b.com"));   // equivalence class: bad structure - no @
        assertFalse(BankAccount.isEmailValid("@b.com"));    // equivalence class: bad structure - missing local part / border case
        assertFalse(BankAccount.isEmailValid("a@.com"));    // equivalence class: bad structure - missing domain / border case
        assertFalse(BankAccount.isEmailValid("a@b."));      // equivalence class: bad domain - zero char tld / border case
        assertFalse(BankAccount.isEmailValid("a"));         // equivalence class: bad structure - single char / border case
        assertFalse(BankAccount.isEmailValid("@"));         // equivalence class: bad structure - zero char local and domain / border case
        assertFalse(BankAccount.isEmailValid("a@b..com"));  // equivalence class: bad domain - double dot
        assertFalse(BankAccount.isEmailValid("a..b@c.com"));// equivalence class: bad local part - double dot

        // missing equivalence classes: checks for special/invalid characters, numbers, extra @ symbols
        // missing border cases: either part ending/starting with dot, max-lengths
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