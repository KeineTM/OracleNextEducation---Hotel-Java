CREATE DATABASE IF NOT EXISTS `Hotel`
    DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci

-- Administración de usuarios: pendiente de acuerdo con las políticas del negocio

CREATE TABLE IF NOT EXISTS `TipoHabitacion` ( -- Tabla Independiente
    Id TINYINT PRIMARY KEY NOT NULL, -- Relación 1:N Habitaciones
    Descripcion VARCHAR(150) NOT NULL, -- Sencilla, Doble, Mixta
    Costo FLOAT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE IF NOT EXISTS `Habitaciones` ( -- Tabla Dependiente
    Id INT PRIMARY KEY NOT NULL,
    Piso TINYINT NOT NULL,
    Tipo TINYINT NOT NULL,
    Estado BIT(1) NOT NULL, -- true = 1 || false = 0
    FOREIGN KEY (Tipo) REFERENCES TipoHabitacion(Id) -- Relación N:1 TipoHabitacion
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;;

CREATE TABLE IF NOT EXISTS `Huespedes` (
    Id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    Nombre VARCHAR(70) NOT NULL,
    Apellido VARCHAR(150) NOT NULL,
    FechaNacimiento DATE NOT NULL,
    Telefono VARCHAR(10) NOT NULL,
    Email VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE IF NOT EXISTS `FormasPago` (
    Id INT NOT NULL PRIMARY KEY,
    Descripcion VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE IF NOT EXISTS `Reservas` (
    Id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    FechaEntrada DATETIME NOT NULL, -- Formato AAAA-MM-DD 00:00:00
    FechaSalida DATETIME NOT NULL,
    ImporteTotal FLOAT NOT NULL,
    FormaPago INT NOT NULL,
    IdHuesped INT NOT NULL,
    FOREIGN KEY (IdHuesped) REFERENCES Huespedes(Id), -- Relación 1:N Huéspedes
    FOREIGN KEY (FormaPago) REFERENCES FormasPago(Id) -- Relación N:1 FormasPago
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Tabla con llave primaria de 2 campos para almacenar los detalles de las reservas, pues una sóla reserva puede incluír varias habitaciones
CREATE TABLE IF NOT EXISTS `DetalleReservas` (
    IdReserva INT NOT NULL,
    IdHabitacion INT NOT NULL,
    PRIMARY KEY (IdReserva, IdHabitacion),              
    -- Relaciones Identificatorias:
    FOREIGN KEY (IdReserva) REFERENCES Reservas(Id), -- Relación N:1 Reservas
    FOREIGN KEY (IdHabitacion) REFERENCES Habitaciones(Id) -- Relación N:1 Habitaciones
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Tabla independiente de usuarios del sistema
CREATE TABLE IF NOT EXISTS `Usuarios` (
    Id VARCHAR(150) NOT NULL PRIMARY KEY,
    Pass VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


-- Valores iniciales
INSERT INTO Usuarios VALUES ("admin", "admin");

INSERT INTO TipoHabitacion VALUES 
    (1, 'Habitación básica con 1 cama individual', 299),
    (2, 'Habitación básica doble con 2 camas individuales', 359),
    (3, 'Habitación matrimonial con 1 cama matrimonial', 399),
    (4, 'Habitación mixta con 1 cama individual y 1 cama matrimonial', 459),
    (5, 'Habitación matrimonial doble con 2 camas matrimoniales', 599)
;

INSERT INTO Habitaciones VALUES
    (101, 1, 1, 0),
    (102, 1, 1, 0),
    (103, 1, 2, 0),
    (104, 1, 2, 0),
    (201, 2, 3, 0),
    (202, 2, 3, 0),
    (203, 2, 4, 0),
    (204, 2, 4, 0),
    (301, 3, 5, 0),
    (303, 3, 5, 0)
;

INSERT INTO FormasPago VALUES
    (1, "Efectivo"),
    (2, "Tarjeta de débito"),
    (3, "Tarjeta de crédito"),
    (4, "Tarjeta de regalo")
;

-- Formato de registro de reservas y huéspedes desde el programa en Java:
INSERT INTO Huespedes (Nombre, Apellido, FechaNacimiento, Telefono, Email) VALUES
    ("Edson", "Trejo", "2006-11-11","0123456789","ed.trejo@alura.com");
    
INSERT INTO Reservas (FechaEntrada, FechaSalida, ImporteTotal, FormaPago, IdHuesped) VALUES
    ("","","",,);

SELECT Reservas.Id, Reservas.FechaEntrada, Reservas.FechaSalida, Reservas.ImporteTotal, FormasPago.Descripcion, Reservas.IdHuesped
FROM Reservas
INNER JOIN FormasPago ON FormasPago.Id = Reservas.FormaPago;
