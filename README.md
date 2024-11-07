# Sistema de Gestión de Cuentas Bancarias

Este es un sistema de ejemplo en Java para gestionar clientes y cuentas bancarias. El programa permite registrar clientes, abrir cuentas bancarias (de tipo Ahorros y Corriente) y realizar operaciones como depósitos y retiros en las cuentas de los clientes.

## Descripción

El sistema permite:

- Registrar clientes con sus datos personales (nombre, apellido, DNI, correo electrónico).
- Abrir cuentas bancarias de tipo Ahorros o Corriente para cada cliente.
- Realizar depósitos y retiros en las cuentas bancarias de los clientes.
- Consultar el saldo de las cuentas bancarias.

## Código SQL (Independiente)

-- Crear la base de datos "banking_system" si no existe
CREATE DATABASE IF NOT EXISTS banking_system;

-- Seleccionar la base de datos "banking_system" para su uso
USE banking_system;

-- Crear la tabla "client" para almacenar la información de los clientes
CREATE TABLE client (
    dni CHAR(8) PRIMARY KEY,  -- DNI del cliente, único e identificador principal
    name VARCHAR(50) NOT NULL,  -- Nombre del cliente
    last_name VARCHAR(50) NOT NULL,  -- Apellido del cliente
    email VARCHAR(100),  -- Correo electrónico del cliente
    -- Restricción para verificar el formato del correo electrónico
    CONSTRAINT chk_email_format CHECK (email REGEXP '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$')
);

-- Crear la tabla "bank_account" para almacenar la información de las cuentas bancarias
CREATE TABLE bank_account (
    account_number CHAR(10) PRIMARY KEY,  -- Número de cuenta bancaria, único
    balance DOUBLE DEFAULT 0.0,  -- Saldo de la cuenta, valor por defecto es 0.0
    account_type ENUM('SAVINGS', 'CHECKING') NOT NULL,  -- Tipo de cuenta (Ahorros o Corriente)
    dni CHAR(8),  -- DNI del cliente propietario de la cuenta
    -- Relación con la tabla "client", asegurando que el dni en "bank_account" exista en "client"
    CONSTRAINT fk_client FOREIGN KEY (dni) REFERENCES client(dni)
);

-- Crear un trigger (desencadenador) para validar antes de insertar una cuenta bancaria
-- Verifica que el saldo no sea negativo en cuentas de Ahorros y no exceda el límite en cuentas Corrientes
CREATE TRIGGER before_insert_bank_account
BEFORE INSERT ON bank_account
FOR EACH ROW
BEGIN
    IF (NEW.account_type = 'SAVINGS' AND NEW.balance < 0) OR 
       (NEW.account_type = 'CHECKING' AND NEW.balance < -500.0) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Balance constraint violated';
    END IF;
END //

-- Crear un trigger para validar antes de actualizar una cuenta bancaria
-- Similar al trigger de inserción, asegura que el saldo no sea negativo ni exceda los límites
CREATE TRIGGER before_update_bank_account
BEFORE UPDATE ON bank_account
FOR EACH ROW
BEGIN
    IF (NEW.account_type = 'SAVINGS' AND NEW.balance < 0) OR 
       (NEW.account_type = 'CHECKING' AND NEW.balance < -500.0) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Balance constraint violated';
    END IF;
END //

-- Insertar algunos clientes de ejemplo en la tabla "client"
INSERT INTO client (name, last_name, dni, email) VALUES
('Juan', 'Pérez', '12345678', 'juan.perez@gmail.com'),
('Ana', 'Torres', '23456789', 'ana.torres@gmail.com'),
('Luis', 'García', '34567890', 'luis.garcia@gmail.com'),
('Carmen', 'Díaz', '45678901', 'carmen.diaz@gmail.com'),
('José', 'Mendoza', '56789012', 'jose.mendoza@gmail.com'),
('Marta', 'Ramos', '67890123', 'marta.ramos@gmail.com'),
('Pedro', 'Fernández', '78901234', 'pedro.fernandez@gmail.com');

-- Insertar algunas cuentas bancarias de ejemplo en la tabla "bank_account"
INSERT INTO bank_account (account_number, balance, account_type, dni) VALUES
('0001234567', 0.0, 'SAVINGS', '12345678'),  
('0002345678', 0.0, 'CHECKING', '12345678'), 
('0003456789', 0.0, 'SAVINGS', '23456789'),   
('0004567890', 0.0, 'SAVINGS', '34567890'), 
('0005678901', 0.0, 'CHECKING', '34567890'), 
('0006789012', 0.0, 'SAVINGS', '45678901'),  
('0008901234', 0.0, 'SAVINGS', '56789012'),   
('0012345678', 0.0, 'SAVINGS', '67890123'),  
('0013456789', 0.0, 'CHECKING', '67890123'),    
('0014567890', 0.0, 'SAVINGS', '78901234');

-- Seleccionar todos los registros de la tabla "client" para verificar la inserción
SELECT * FROM client;

-- Seleccionar todos los registros de la tabla "bank_account" para verificar la inserción
SELECT * FROM bank_account;

-- Actualizar los saldos de las cuentas de tipo "SAVINGS" con valores aleatorios
UPDATE bank_account
SET balance = ROUND(RAND() * 1000, 2) 
WHERE account_type = 'SAVINGS' 
AND dni IS NOT NULL;

-- Ver los cambios en los saldos de las cuentas "SAVINGS"
SELECT * FROM bank_account;

-- Actualizar los saldos de las cuentas de tipo "CHECKING" con valores aleatorios que pueden ser positivos o negativos
UPDATE bank_account
SET balance = ROUND(RAND() * 500 * IF(RAND() > 0.5, 1, -1), 2)  
WHERE account_type = 'CHECKING'
AND dni IS NOT NULL;

-- Ver los cambios en los saldos de las cuentas "CHECKING"
SELECT * FROM bank_account;

-- Consultar las cuentas bancarias con saldo mayor a 300, ordenado por saldo de mayor a menor
SELECT balance, account_type, dni FROM bank_account
WHERE balance > 300
ORDER BY balance DESC;

-- Consultar el saldo total por tipo de cuenta (SAVINGS y CHECKING)
SELECT account_type, SUM(balance) AS total_balance
FROM bank_account
GROUP BY account_type;

-- Eliminar la restricción de clave externa "fk_client" de la tabla "bank_account"
ALTER TABLE bank_account DROP FOREIGN KEY fk_client;

-- Volver a agregar la restricción de clave externa "fk_client" con la opción "ON DELETE CASCADE" para eliminar las cuentas asociadas si se elimina un cliente
ALTER TABLE bank_account
ADD CONSTRAINT fk_client FOREIGN KEY (dni) REFERENCES client(dni) ON DELETE CASCADE;

-- Eliminar un cliente de la tabla "client" y sus cuentas asociadas en la tabla "bank_account"
DELETE FROM client
WHERE dni = '12345678';

-- Ver los registros restantes en la tabla "bank_account"
SELECT * FROM bank_account;
