package org.example;

import java.util.Random;

public class BankAccount {
    private final String accountNumber;
    private double balance;
    private final AccountType accountType;

    public enum AccountType {
        SAVINGS, CHECKING
    }

    public BankAccount(AccountType accountType) {
        this.accountNumber = generateAccountNumber(); // Generación automática
        this.balance = 0.0;
        this.accountType = accountType;
    }

    //Generar número aleatorio para la cuenta
    private String generateAccountNumber() {
        Random random = new Random();
        int number = 1000000000 + random.nextInt(900000000); // Genera un número de 10 dígitos
        return String.valueOf(number);
    }

    // Metodo para deposito
    public void deposit(double amount) {
        if (amount < 0) {
//            throw new IllegalArgumentException("The amount must be positive.");
            System.out.println("The amount must be positive.");
            return;
        }

        this.balance += amount;
    }

    // Metodo para retiro
    public void withdraw(double amount) {
        if (amount <= 0) {
//            throw new IllegalArgumentException("The amount must be positive.");
            System.out.println("The amount must be positive.");
            return;
        }

        double newBalance = balance - amount;

        // Verificar si la operación está prohibida para la cuenta de ahorros
        if (this.accountType == AccountType.SAVINGS && newBalance < 0) {
//            throw new IllegalArgumentException("No negative balance allowed in savings accounts.");
            System.out.println("No negative balance allowed in savings accounts.");
            return;
        }

        // Verificar si la operación está prohibida para la cuenta corriente con límite de sobregiro
        if (this.accountType == AccountType.CHECKING && newBalance < -500) {
//            throw new IllegalArgumentException("Overdraft limit reached for checking accounts.");
            System.out.println("Overdraft limit reached for checking accounts.");
            return;
        }

        this.balance = newBalance;
    }

    // Getter para consultar el saldo
    public double getBalance() {
        return this.balance;
    }

    // Getter para obtener el número de cuenta
    public String getAccountNumber() {
        return this.accountNumber;
    }
}