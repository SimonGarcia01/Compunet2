-- Users
INSERT INTO users (username, password) VALUES ( 'usuario1@correo.com', '$2a$12$LE5wWF2zJKLfE98E4KgJPO.buVfS0xHlSg2F2ciQMnk5kdgEBx506'),
                                              ( 'usuario2@correo.com', '$2a$12$LE5wWF2zJKLfE98E4KgJPO.buVfS0xHlSg2F2ciQMnk5kdgEBx506');
-- Password de los usuario: 123456

-- Domicilios para usuario1
INSERT INTO domicilios (nombre_domiciliario, estado, user_id) VALUES ('Carlos Rodríguez', 'EN_CAMINO', 1);
INSERT INTO domicilios (nombre_domiciliario, estado, user_id) VALUES ('María González', 'EN_REPARTO', 1);
INSERT INTO domicilios (nombre_domiciliario, estado, user_id) VALUES ('Pedro Martínez', 'ENTREGADO', 1);

-- Domicilios para usuario2
INSERT INTO domicilios (nombre_domiciliario, estado, user_id) VALUES ('Ana López', 'EN_CAMINO', 2);
INSERT INTO domicilios (nombre_domiciliario, estado, user_id) VALUES ('Luis Fernández', 'ENTREGADO', 2);
INSERT INTO domicilios (nombre_domiciliario, estado, user_id) VALUES ('Sandra Ramírez', 'EN_REPARTO', 2);
