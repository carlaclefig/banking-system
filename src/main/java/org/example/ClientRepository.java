package org.example;

import java.util.Arrays;
import java.util.List;

public class ClientRepository {
    public static List<Client> getAllClients(){
        Client c1 = new Client("Juan", "Pérez", "12345678", "juan.perez@gmail.com");
        Client c2 = new Client("María", "González", "87654321", "maria.gonzalez@gmail.com");
        Client c3 = new Client("Carlos", "Ramírez", "11223344", "carlos.ramirez@gmail.com");
        Client c4 = new Client("Ana", "López", "55667788", "ana.lopez@gmail.com");
        Client c5 = new Client("Luis", "Martínez", "99887766", "luis.martinez@gmail.com");
        Client c6 = new Client("Sofía", "Hernández", "44556677", "sofia.hernandez@gmail.com");
        Client c7 = new Client("Diego", "Torres", "33445566", "diego.torres@gmail.com");
        Client c8 = new Client("Lucía", "Flores", "22334455", "lucia.flores@gmail.com");
        Client c9 = new Client("Andrés", "Castro", "66554433", "andres.castro@gmail.com");
        Client c10 = new Client("Gabriela", "Vega", "77889900", "gabriela.vega@gmail.com");

        return Arrays.asList(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10);
    }
}
