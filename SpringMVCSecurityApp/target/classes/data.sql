INSERT INTO professors (name) VALUES ('Juan Perez'), ('Maria Rodriguez'), ('Carlos Gomez'), ('Ana Martinez'), ('Luis Hernandez');

INSERT INTO courses (name, credits, professor_id) VALUES ('Introduccion a la Programacion', 4, 1);
INSERT INTO courses (name, credits, professor_id) VALUES ('Estructuras de Datos', 4, 1);
INSERT INTO courses (name, credits, professor_id) VALUES ('Anatomia Humana', 5, 2);
INSERT INTO courses (name, credits, professor_id) VALUES ('Fisiologia', 5, 2);
INSERT INTO courses (name, credits, professor_id) VALUES ('Derecho Penal', 3, 3);
INSERT INTO courses (name, credits, professor_id) VALUES ('Derecho Civil', 3, 3);
INSERT INTO courses (name, credits, professor_id) VALUES ('Dibujo Tecnico', 2, 4);
INSERT INTO courses (name, credits, professor_id) VALUES ('Historia del Arte', 3, 5);

INSERT INTO students (name, code, program) VALUES ('Laura Garcia', '2021102001', 'Ingenieria de Sistemas');
INSERT INTO students (name, code, program) VALUES ('Pedro Pascal', '2021102006', 'Ingenieria de Sistemas');
INSERT INTO students (name, code, program) VALUES ('Andres Lopez', '2021102002', 'Medicina');
INSERT INTO students (name, code, program) VALUES ('Sofia Torres', '2021102003', 'Derecho');
INSERT INTO students (name, code, program) VALUES ('David Ramirez', '2021102004', 'Arquitectura');
INSERT INTO students (name, code, program) VALUES ('Valentina Diaz', '2021102005', 'Diseno Grafico');
INSERT INTO students (name, code, program) VALUES ('Camila Velez', '2022102007', 'Medicina');

INSERT INTO students_courses (student_id, course_id) VALUES (1, 1), (1, 2); -- Laura (Sistemas) en 2 cursos de Sistemas
INSERT INTO students_courses (student_id, course_id) VALUES (2, 1); -- Pedro (Sistemas) en 1 curso de Sistemas
INSERT INTO students_courses (student_id, course_id) VALUES (3, 3), (3, 4); -- Andres (Medicina) en 2 cursos de Medicina
INSERT INTO students_courses (student_id, course_id) VALUES (4, 5), (4, 8); -- Sofia (Derecho) en Derecho Penal e Historia del Arte
INSERT INTO students_courses (student_id, course_id) VALUES (5, 7); -- David (Arquitectura) en Dibujo Tecnico
INSERT INTO students_courses (student_id, course_id) VALUES (6, 8); -- Valentina (Diseño) en Historia del Arte
INSERT INTO students_courses (student_id, course_id) VALUES (7, 3); -- Camila (Medicina) en Anatomia

-- Roles
INSERT INTO roles (id, name) VALUES (1, 'ROLE_STUDENT'), (2, 'ROLE_PROFESSOR');

-- Permissions
INSERT INTO permissions (id, name) VALUES (1, 'VIEW_COURSES'), (2, 'VIEW_OWN_COURSES'), (3, 'EDIT_COURSES'), (4, 'VIEW_STUDENTS');

-- Users
INSERT INTO users (id, username, password) VALUES (1, 'estudiante1@gmail.com', 'estudiante'), (2, 'profesor1@gmail.com', 'profesor');

-- User-Roles
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1), (2, 2);

-- Role-Permissions
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 2);
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 1), (2, 3), (2, 4);
