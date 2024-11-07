package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        List<Client> clients = ClientRepository.getAllClients();

        Map<String, String> savingsAccountNumbers = new HashMap<>();
        Map<String, String> checkingAccountNumbers = new HashMap<>();

        for (Client client : clients) {
            OperationsAvailable.registerClient(client.getName(), client.getLastName(), client.getDni(), client.getEmail());

            String savingsAccountNumber = OperationsAvailable.openAccount(client.getDni(), BankAccount.AccountType.SAVINGS);
            savingsAccountNumbers.put(client.getDni(), savingsAccountNumber);

            String checkingAccountNumber = OperationsAvailable.openAccount(client.getDni(), BankAccount.AccountType.CHECKING);
            checkingAccountNumbers.put(client.getDni(), checkingAccountNumber);
        }

        for (Client client : clients) {
            String dni = client.getDni();
            int lastDigit = Character.getNumericValue(dni.charAt(dni.length() - 1));

            String savingsAccountNumber = savingsAccountNumbers.get(dni);
            String checkingAccountNumber = checkingAccountNumbers.get(dni);

            if (lastDigit % 2 == 0) {
                OperationsAvailable.deposit(dni, savingsAccountNumber, 100);
                OperationsAvailable.deposit(dni, checkingAccountNumber, 500);
            }
            else {
                OperationsAvailable.deposit(dni, savingsAccountNumber, 200);
                OperationsAvailable.deposit(dni, checkingAccountNumber, 300);
            }


            if (lastDigit % 2 == 0) {
                OperationsAvailable.withdraw(dni, savingsAccountNumber, 80);
                OperationsAvailable.withdraw(dni, checkingAccountNumber, 300);
            }
            else {
                OperationsAvailable.withdraw(dni, savingsAccountNumber, 50);
                OperationsAvailable.withdraw(dni, checkingAccountNumber, 400);
            }


            double savingsBalance = OperationsAvailable.getBalance(dni, savingsAccountNumber);
            double checkingBalance = OperationsAvailable.getBalance(dni, checkingAccountNumber);
            System.out.println("Client DNI: " + dni + " - Savings Account Balance: " + savingsBalance);
            System.out.println("Client DNI: " + dni + " - Checking Account Balance: " + checkingBalance);
        }
    }
}
