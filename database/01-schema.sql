-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS gestion_equipos;

-- Seleccionar la base de datos
USE gestion_equipos;

-- Crear la tabla de equipos tecnológicos
CREATE TABLE IF NOT EXISTS equipo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    marca VARCHAR(50) NOT NULL,
    numero_serie VARCHAR(100) NOT NULL UNIQUE,
    fecha_registro DATE NOT NULL,
    estado VARCHAR(30) NOT NULL
);