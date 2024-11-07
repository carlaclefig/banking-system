package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Client {
    private final String name;
    private final String lastName;
    private final String dni;
    private String email;
    private List<BankAccount> BankAccounts; //lista de cuentas del cliente

    public Client(String name, String lastName, String dni, String email) {

        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name is required");
        if (lastName == null || lastName.trim().isEmpty()) throw new IllegalArgumentException("Last Name is required");
        if (dni == null || dni.trim().isEmpty()) throw new IllegalArgumentException("DNI is required");
        if (email == null || email.trim().isEmpty()) throw new IllegalArgumentException("Email is required");

        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        setEmail(email);
        this.BankAccounts = new ArrayList<>(); // se inicia la lista de cuentas el cliente
    }

    public void setEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);

        if (pattern.matcher(email).matches()) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid mail");
        }
    }

    public void addAccount(BankAccount newBankAccount) { // agrega las cuentas de los clientes
        this.BankAccounts.add(newBankAccount);
    }

    public List<BankAccount> getBankAccounts() { // obtener la lista de cuentas de los clientes
        return this.BankAccounts;
    }

    public String getName() {
        return this.name;
    }
    public String getLastName() {
        return this.lastName;
    }
    public String getDni() {
        return this.dni;
    }
    public String getEmail() {
        return this.email;
    }
}
