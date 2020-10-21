USE parquimetros;

INSERT INTO Conductores(dni, nombre, apellido, direccion, registro) VALUES (12569, "Jose", "Perez", "Belgrano 356", 1001);
INSERT INTO Conductores(dni, nombre, apellido, direccion, registro) VALUES (15468, "Sofia", "Gomez", "Soler 421", 1002);
INSERT INTO Conductores(dni, nombre, apellido, direccion, registro) VALUES (29513, "Juan", "Perez", "Alem 1423", 1003);
INSERT INTO Conductores(dni, nombre, apellido, direccion, registro) VALUES (35978, "Tomas", "Lopez", "Aguado 731", 1004);
INSERT INTO Conductores(dni, nombre, apellido, direccion, registro) VALUES (23284, "Alberto", "Perez", "Soler 212", 1005);

INSERT INTO Automoviles VALUES ("ABC001", "Fiat", "Palio", "Blanco", 12569);
INSERT INTO Automoviles VALUES ("FFF089", "Chevrolet", "Cruze", "Verde", 12569);
INSERT INTO Automoviles VALUES ("RTD852", "Honda", "HR-V", "Azul", 15468);
INSERT INTO Automoviles VALUES ("BWR315", "Nissan", "Frontier", "Negro", 29513);
INSERT INTO Automoviles VALUES ("LHD178", "Fiat", "Cronos", "Blanco", 35978);
INSERT INTO Automoviles VALUES ("POS597", "Toyota", "Hilux", "Rojo", 35978);

INSERT INTO Tipos_tarjeta VALUES ("Normal", 0.00);
INSERT INTO Tipos_tarjeta VALUES ("Deluxe", 0.10);
INSERT INTO Tipos_tarjeta VALUES ("Premium", 0.25);

INSERT INTO Tarjetas VALUES (1, 15.35, "Normal", "ABC001");
INSERT INTO Tarjetas VALUES (2, 60.22, "Deluxe", "ABC001");
INSERT INTO Tarjetas VALUES (3, 32.56, "Deluxe", "FFF089");
INSERT INTO Tarjetas VALUES (4, 1.26, "Normal", "RTD852");
INSERT INTO Tarjetas VALUES (5, 23.15, "Premium", "RTD852");
INSERT INTO Tarjetas VALUES (6, 96.12, "Deluxe", "BWR315");
INSERT INTO Tarjetas VALUES (7, 192.17, "Premium", "LHD178");
INSERT INTO Tarjetas VALUES (8, 27.66, "Normal", "POS597");

INSERT INTO Inspectores VALUES (101, 35975, "Fernando", "Rodriguez", md5("pass1234"));
INSERT INTO Inspectores VALUES (102, 35975, "Maria", "Rodriguez", md5("mr12"));
INSERT INTO Inspectores VALUES (103, 35975, "Liliana", "Gonzalez", md5("26021994"));
INSERT INTO Inspectores VALUES (104, 35975, "Jose", "Gonzalez", md5("jg4321"));
INSERT INTO Inspectores VALUES (105, 35975, "Roberto", "Lopez", md5("9876543210"));
INSERT INTO Inspectores VALUES (106, 35975, "Fabiana", "Perez", md5("4321ssap"));

INSERT INTO Ubicaciones VALUES ("Alvarado", 100, 7.50);
INSERT INTO Ubicaciones VALUES ("Alvarado", 200, 7.50);
INSERT INTO Ubicaciones VALUES ("Alvarado", 300, 7.50);
INSERT INTO Ubicaciones VALUES ("Alvarado", 400, 7.50);
INSERT INTO Ubicaciones VALUES ("Belgrano", 300, 12.00);
INSERT INTO Ubicaciones VALUES ("Belgrano", 400, 12.00);
INSERT INTO Ubicaciones VALUES ("Belgrano", 500, 10.00);
INSERT INTO Ubicaciones VALUES ("Belgrano", 600, 10.00);
INSERT INTO Ubicaciones VALUES ("Belgrano", 700, 8.50);

INSERT INTO Parquimetros VALUES (1, 142, "Alvarado", 100);
INSERT INTO Parquimetros VALUES (2, 193, "Alvarado", 100);
INSERT INTO Parquimetros VALUES (3, 213, "Alvarado", 200);
INSERT INTO Parquimetros VALUES (4, 356, "Alvarado", 300);
INSERT INTO Parquimetros VALUES (5, 426, "Alvarado", 400);
INSERT INTO Parquimetros VALUES (6, 325, "Belgrano", 300);
INSERT INTO Parquimetros VALUES (7, 410, "Belgrano", 400);
INSERT INTO Parquimetros VALUES (8, 459, "Belgrano", 400);
INSERT INTO Parquimetros VALUES (9, 523, "Belgrano", 500);
INSERT INTO Parquimetros VALUES (10, 602, "Belgrano", 600);
INSERT INTO Parquimetros VALUES (11, 742, "Belgrano", 700);


INSERT INTO Estacionamientos VALUES (1, 4, "2020-10-5", "15:23:48", NULL, NULL);
INSERT INTO Estacionamientos VALUES (2, 8, "2020-10-5", "17:12:35", NULL, NULL);
INSERT INTO Estacionamientos VALUES (3, 1, "2020-10-5", "11:55:22", "2020-10-5", "13:03:16");
INSERT INTO Estacionamientos VALUES (4, 3, "2020-10-6", "9:42:39", "2020-10-6", "10:15:34");
INSERT INTO Estacionamientos VALUES (5, 6, "2020-10-7", "16:36:41", NULL, NULL);


INSERT INTO Accede VALUES (101, 1, "2020-10-5", "10:31:02");
INSERT INTO Accede VALUES (102, 2, "2020-10-5", "12:31:29");
INSERT INTO Accede VALUES (103, 3, "2020-10-6", "9:31:13");
INSERT INTO Accede VALUES (104, 4, "2020-10-7", "15:01:35");

INSERT INTO Asociado_con VALUES (1, 101, "Alvarado", 100, "lu", "m");
INSERT INTO Asociado_con VALUES (2, 101, "Alvarado", 100, "ma", "m");
INSERT INTO Asociado_con VALUES (3, 101, "Alvarado", 100, "mi", "m");
INSERT INTO Asociado_con VALUES (4, 101, "Alvarado", 100, "ju", "m");
INSERT INTO Asociado_con VALUES (5, 101, "Alvarado", 100, "vi", "m");
INSERT INTO Asociado_con VALUES (6, 101, "Alvarado", 200, "lu", "m");
INSERT INTO Asociado_con VALUES (7, 101, "Alvarado", 200, "ma", "m");
INSERT INTO Asociado_con VALUES (8, 101, "Alvarado", 200, "mi", "m");
INSERT INTO Asociado_con VALUES (9, 101, "Alvarado", 200, "ju", "m");
INSERT INTO Asociado_con VALUES (10, 101, "Alvarado", 200, "vi", "m");

INSERT INTO Asociado_con VALUES (11, 102, "Alvarado", 100, "lu", "t");
INSERT INTO Asociado_con VALUES (12, 102, "Alvarado", 100, "ma", "t");
INSERT INTO Asociado_con VALUES (13, 102, "Alvarado", 100, "mi", "t");
INSERT INTO Asociado_con VALUES (14, 102, "Alvarado", 100, "ju", "t");
INSERT INTO Asociado_con VALUES (15, 102, "Alvarado", 100, "vi", "t");
INSERT INTO Asociado_con VALUES (16, 102, "Alvarado", 200, "lu", "t");
INSERT INTO Asociado_con VALUES (17, 102, "Alvarado", 200, "ma", "t");
INSERT INTO Asociado_con VALUES (18, 102, "Alvarado", 200, "mi", "t");
INSERT INTO Asociado_con VALUES (19, 102, "Alvarado", 200, "ju", "t");
INSERT INTO Asociado_con VALUES (20, 102, "Alvarado", 200, "vi", "t");

INSERT INTO Asociado_con VALUES (21, 103, "Alvarado", 300, "lu", "t");
INSERT INTO Asociado_con VALUES (22, 103, "Alvarado", 300, "ma", "t");
INSERT INTO Asociado_con VALUES (23, 103, "Alvarado", 300, "mi", "t");
INSERT INTO Asociado_con VALUES (24, 103, "Alvarado", 300, "ju", "t");
INSERT INTO Asociado_con VALUES (25, 103, "Alvarado", 300, "vi", "t");
INSERT INTO Asociado_con VALUES (26, 103, "Alvarado", 400, "lu", "t");
INSERT INTO Asociado_con VALUES (27, 103, "Alvarado", 400, "ma", "t");
INSERT INTO Asociado_con VALUES (28, 103, "Alvarado", 400, "mi", "t");
INSERT INTO Asociado_con VALUES (29, 103, "Alvarado", 400, "ju", "t");
INSERT INTO Asociado_con VALUES (30, 103, "Alvarado", 400, "vi", "t");

INSERT INTO Asociado_con VALUES (31, 103, "Alvarado", 300, "lu", "m");
INSERT INTO Asociado_con VALUES (32, 103, "Alvarado", 300, "ma", "m");
INSERT INTO Asociado_con VALUES (33, 103, "Alvarado", 300, "mi", "m");
INSERT INTO Asociado_con VALUES (34, 103, "Alvarado", 300, "ju", "m");
INSERT INTO Asociado_con VALUES (35, 103, "Alvarado", 300, "vi", "m");
INSERT INTO Asociado_con VALUES (36, 103, "Alvarado", 400, "lu", "m");
INSERT INTO Asociado_con VALUES (37, 103, "Alvarado", 400, "ma", "m");
INSERT INTO Asociado_con VALUES (38, 103, "Alvarado", 400, "mi", "m");
INSERT INTO Asociado_con VALUES (39, 103, "Alvarado", 400, "ju", "m");
INSERT INTO Asociado_con VALUES (40, 103, "Alvarado", 400, "vi", "m");

INSERT INTO Asociado_con VALUES (41, 104, "Belgrano", 300, "lu", "m");
INSERT INTO Asociado_con VALUES (42, 104, "Belgrano", 300, "ma", "m");
INSERT INTO Asociado_con VALUES (43, 104, "Belgrano", 300, "mi", "m");
INSERT INTO Asociado_con VALUES (44, 104, "Belgrano", 300, "ju", "m");
INSERT INTO Asociado_con VALUES (45, 104, "Belgrano", 300, "vi", "m");
INSERT INTO Asociado_con VALUES (46, 104, "Belgrano", 400, "lu", "m");
INSERT INTO Asociado_con VALUES (47, 104, "Belgrano", 400, "ma", "m");
INSERT INTO Asociado_con VALUES (48, 104, "Belgrano", 400, "mi", "m");
INSERT INTO Asociado_con VALUES (49, 104, "Belgrano", 400, "ju", "m");
INSERT INTO Asociado_con VALUES (50, 104, "Belgrano", 400, "vi", "m");

INSERT INTO Asociado_con VALUES (51, 105, "Belgrano", 300, "lu", "t");
INSERT INTO Asociado_con VALUES (52, 105, "Belgrano", 300, "ma", "t");
INSERT INTO Asociado_con VALUES (53, 105, "Belgrano", 300, "mi", "t");
INSERT INTO Asociado_con VALUES (54, 105, "Belgrano", 300, "ju", "t");
INSERT INTO Asociado_con VALUES (55, 105, "Belgrano", 300, "vi", "t");
INSERT INTO Asociado_con VALUES (56, 105, "Belgrano", 400, "lu", "t");
INSERT INTO Asociado_con VALUES (57, 105, "Belgrano", 400, "ma", "t");
INSERT INTO Asociado_con VALUES (58, 105, "Belgrano", 400, "mi", "t");
INSERT INTO Asociado_con VALUES (59, 105, "Belgrano", 400, "ju", "t");
INSERT INTO Asociado_con VALUES (60, 105, "Belgrano", 400, "vi", "t");

INSERT INTO Multa VALUES (1, "2020-10-5", "19:54:30", "POS597", 51);
INSERT INTO Multa VALUES (2, "2020-10-7", "12:11:23", "LHD178", 43);