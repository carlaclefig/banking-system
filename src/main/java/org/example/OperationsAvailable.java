package org.example;

import java.util.HashMap;
import java.util.Map;

public class OperationsAvailable {

    private static Map<String, Client> clients = new HashMap<>();

    // Metodo para registrar un cliente
    public static void registerClient(String name, String lastName, String dni, String email) {
        if (clients.containsKey(dni)){ // verifica si existe el dni
//            throw new IllegalArgumentException("Client with already registered DNI");
            System.out.println("Client with already registered DNI");
            return;
        }

        Client client = new Client(name, lastName, dni, email);
        clients.put(dni, client);
        System.out.println("Client successfully registered.");
    }

    // Metodo para abrir una cuenta bancaria
    public static String openAccount(String dni, BankAccount.AccountType accountType) {
        Client client = clients.get(dni);
        if(client == null){
//            throw new IllegalArgumentException("Client not found");
            System.out.println("Client not found");
            return null;
        }

        BankAccount newBankAccount = new BankAccount(accountType); // Genera automáticamente el número de cuenta
        client.addAccount(newBankAccount);

        String accountNumber = newBankAccount.getAccountNumber();
        System.out.println("Account " + accountType + " successfully opened for customer " + dni + ". Account Number: " + accountNumber);
        return accountNumber;
    }

    // Metodo para depositar dinero en una cuenta
    public static void deposit(String dni, String accountNumber, double amount) {
        BankAccount account = findBankAccount(dni, accountNumber);
        if (account == null) {
//            throw new IllegalArgumentException("Account not found");
            System.out.println("Account not found");
            return;
        }

        account.deposit(amount);
        System.out.println("Deposit of " + amount + " made successfully.");
    }

    // Metodo para retirar dinero de una cuenta

    public static void withdraw(String dni, String accountNumber, double amount) {
        BankAccount account = findBankAccount(dni, accountNumber);
        if (account == null) {
//            throw new IllegalArgumentException("Account not found");
            System.out.println("Account not found");
            return;
        }

        account.withdraw(amount);
        System.out.println("Withdrawal of " + amount + " successfully performed.");
    }

    // Metodo para consultar saldo de una cuenta
    public static double getBalance(String dni, String accountNumber) {
        BankAccount account = findBankAccount(dni, accountNumber);
        if (account == null) {
//            throw new IllegalArgumentException("Account not found");
            System.out.println("Account not found");
            return 0.0;
        }

        return account.getBalance();
    }

    // Metodo para encontrar una cuenta bancaria - implementación para no repetir código
    private static BankAccount findBankAccount(String dni, String accountNumber) {
        Client client = clients.get(dni);
        if(client == null){
            return null;
        }

        return client.getBankAccounts().stream()
                .filter(BankAccount -> BankAccount.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
    }
}
