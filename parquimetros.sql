# Creo de la Base de Datos
CREATE DATABASE parquimetros;

# selecciono la base de datos sobre la cual voy a hacer modificaciones
USE parquimetros;

#Tablas de entidades

CREATE TABLE Conductores (
    dni INT UNSIGNED NOT NULL,
    nombre VARCHAR(25) NOT NULL,
    apellido VARCHAR(25) NOT NULL,
    direccion VARCHAR(30) NOT NULL,
    telefono VARCHAR(15),
    registro INT UNSIGNED NOT NULL,

    CONSTRAINT pk_dni
    PRIMARY KEY (dni)
) ENGINE=InnoDB;

CREATE TABLE Automoviles (
    patente CHAR(6) NOT NULL,
    marca VARCHAR(20) NOT NULL,
    modelo VARCHAR(20) NOT NULL,
    color VARCHAR(20) NOT NULL,
    dni INT UNSIGNED NOT NULL,

    CONSTRAINT pk_patente
    PRIMARY KEY (patente),

    CONSTRAINT fk_automovil_dni
    FOREIGN KEY (dni) REFERENCES Conductores (dni)
        ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE Tipos_tarjeta (
    tipo VARCHAR(20) NOT NULL,
    descuento DECIMAL(3,2) UNSIGNED NOT NULL, #rango->[0,1]

    CONSTRAINT check_descuento
    CHECK (descuento<=1),

    CONSTRAINT pk_tipo
    PRIMARY KEY (tipo)
) ENGINE=InnoDB;

CREATE TABLE Tarjetas (
    id_tarjeta INT UNSIGNED NOT NULL AUTO_INCREMENT,
    saldo DECIMAL(5,2) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    patente CHAR(6) NOT NULL,

    CONSTRAINT pk_tarjeta
    PRIMARY KEY (id_tarjeta),

    CONSTRAINT fk_tarjeta_tipo
    FOREIGN KEY (tipo) REFERENCES Tipos_tarjeta (tipo)
     ON DELETE RESTRICT ON UPDATE RESTRICT,

    CONSTRAINT fk_tarjeta_patente
    FOREIGN KEY (patente) REFERENCES Automoviles (patente)
     ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE Inspectores (
    legajo INT UNSIGNED NOT NULL,
    dni INT UNSIGNED NOT NULL,
    nombre VARCHAR(25) NOT NULL,
    apellido VARCHAR(25) NOT NULL,
    password CHAR(32) NOT NULL,

    CONSTRAINT pk_legajo
    PRIMARY KEY (legajo)
) ENGINE=InnoDB;

CREATE TABLE Ubicaciones (
    calle VARCHAR(25) NOT NULL,
    altura INT UNSIGNED NOT NULL,
    tarifa DECIMAL(5,2) UNSIGNED NOT NULL,

    CONSTRAINT pk_calle_altura
    PRIMARY KEY (calle, altura)
) ENGINE=InnoDB;

CREATE TABLE Parquimetros (
    id_parq INT UNSIGNED NOT NULL,
    numero INT UNSIGNED NOT NULL,
    calle VARCHAR(25) NOT NULL,
    altura INT UNSIGNED NOT NULL,

    CONSTRAINT pk_id_parquimetro
    PRIMARY KEY (id_parq),

    CONSTRAINT fk_parq_calle
    FOREIGN KEY (calle, altura) REFERENCES Ubicaciones (calle, altura)
     ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB;

#Tablas de relaciones

CREATE TABLE Estacionamientos (
    id_tarjeta INT UNSIGNED NOT NULL,
    id_parq INT UNSIGNED NOT NULL,
    fecha_ent DATE NOT NULL,
    hora_ent TIME NOT NULL,
    fecha_sal DATE,
    hora_sal TIME,

    CONSTRAINT pk_estacionamiento
    PRIMARY KEY (id_parq, fecha_ent, hora_ent),

    CONSTRAINT fk_estacionamiento_tarjeta
    FOREIGN KEY (id_tarjeta) REFERENCES Tarjetas (id_tarjeta)
     ON DELETE RESTRICT ON UPDATE RESTRICT,

    CONSTRAINT fk_estacionamiento_parquimetro
    FOREIGN KEY (id_parq) REFERENCES Parquimetros (id_parq)
     ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE Accede (
    legajo INT UNSIGNED NOT NULL, 
    id_parq INT UNSIGNED NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,

    CONSTRAINT pk_accede
    PRIMARY KEY (id_parq, fecha, hora),

    CONSTRAINT fk_accede_legajo
    FOREIGN KEY (legajo) REFERENCES Inspectores (legajo)
     ON DELETE RESTRICT ON UPDATE RESTRICT,

    CONSTRAINT fk_accede_parq
    FOREIGN KEY (id_parq) REFERENCES Parquimetros (id_parq)
     ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE Asociado_con (
    id_asociado_con INT UNSIGNED NOT NULL AUTO_INCREMENT,
    legajo INT UNSIGNED NOT NULL,
    calle VARCHAR(25) NOT NULL,
    altura INT UNSIGNED NOT NULL,
    dia ENUM("do", "lu", "ma", "mi", "ju", "vi", "sa") NOT NULL,
    turno ENUM("m", "t") NOT NULL,

    CONSTRAINT pk_asociado
    PRIMARY KEY (id_asociado_con),

    CONSTRAINT fk_asociado_legajo
    FOREIGN KEY (legajo) REFERENCES Inspectores (legajo)
        ON DELETE RESTRICT ON UPDATE RESTRICT,
    
    CONSTRAINT fk_asociado_calle_altura
    FOREIGN KEY (calle, altura) REFERENCES Ubicaciones (calle, altura)
        ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE Multa (
    numero INT UNSIGNED NOT NULL AUTO_INCREMENT,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    patente CHAR(6) NOT NULL,
    id_asociado_con INT UNSIGNED NOT NULL,

    CONSTRAINT pk_numero
    PRIMARY KEY (numero),

    CONSTRAINT fk_multa_patente
    FOREIGN KEY (patente) REFERENCES Automoviles (patente)
     ON DELETE RESTRICT ON UPDATE RESTRICT,

    CONSTRAINT fk_multa_id_asocaido
    FOREIGN KEY (id_asociado_con) REFERENCES Asociado_con (id_asociado_con)
     ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB;


#Creacion de la vista Estacionados

CREATE VIEW Estacionados AS
SELECT P.calle, P.altura, T.patente
FROM Estacionamientos E NATURAL JOIN Parquimetros P
    NATURAL JOIN Tarjetas T
WHERE E.fecha_sal IS NULL AND E.hora_sal IS NULL;

#Creacion de usuarios

CREATE USER "admin"@"localhost" IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON parquimetros.* TO "admin"@"localhost" WITH GRANT OPTION;

CREATE USER "venta"@"localhost" IDENTIFIED BY "venta";
GRANT INSERT ON parquimetros.Tarjetas TO "venta"@"localhost";
GRANT SELECT ON parquimetros.Tipos_tarjeta TO "venta"@"localhost";

CREATE USER "inspector"@"localhost" IDENTIFIED BY "inspector";
GRANT SELECT ON parquimetros.Estacionados TO "inspector"@"localhost";
GRANT SELECT ON parquimetros.Inspectores TO "inspector"@"localhost";
GRANT SELECT ON parquimetros.Asociado_con TO "inspector"@"localhost";
GRANT SELECT ON parquimetros.Parquimetros TO "inspector"@"localhost";
GRANT INSERT ON parquimetros.Multa TO "inspector"@"localhost";
GRANT INSERT ON parquimetros.Accede TO "inspector"@"localhost";