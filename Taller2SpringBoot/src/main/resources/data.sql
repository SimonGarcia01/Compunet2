-- Privilegios
INSERT INTO privileges (name, description) VALUES ('Crear usuarios', 'Permite crear usuarios');
INSERT INTO privileges (name, description) VALUES ('Ver usuarios', 'Permite ver usuarios');
INSERT INTO privileges (name, description) VALUES ('Actualizar usuarios', 'Permite actualizar usuarios');
INSERT INTO privileges (name, description) VALUES ('Borrar usuarios', 'Permite borrar usuarios');
INSERT INTO privileges (name, description) VALUES ('Enviar mensajes', 'Permite enviar mensajes');
INSERT INTO privileges (name, description) VALUES ('Recibir notificaciones', 'Permite recibir notificaciones');
INSERT INTO privileges (name, description) VALUES ('Iniciar sesion', 'Permite iniciar sesion');
INSERT INTO privileges (name, description) VALUES ('Crear rutina de ejercicio', 'Permite crear rutina de ejercicio');
INSERT INTO privileges (name, description) VALUES ('Editar rutina de ejercicio', 'Permite editar rutina de ejercicio');
INSERT INTO privileges (name, description) VALUES ('Registrar progreso', 'Permite registrar progreso');
INSERT INTO privileges (name, description) VALUES ('Visualizar rutinas', 'Permite visualizar rutinas');
INSERT INTO privileges (name, description) VALUES ('Visualizar progreso', 'Permite visualizar progreso');
INSERT INTO privileges (name, description) VALUES ('Generar recomendaciones', 'Permite generar recomendaciones segun el avance del usuario');
INSERT INTO privileges (name, description) VALUES ('Subir rutinas', 'Permite subir rutinas prediseñadas');
INSERT INTO privileges (name, description) VALUES ('Ver eventos', 'Permite ver eventos que suceden en la universidad Icesi');
INSERT INTO privileges (name, description) VALUES ('Ver espacios disponibles', 'Permite ver los espacios disponibles en la universidad Icesi');
INSERT INTO privileges (name, description) VALUES ('Crear entrenadores', 'Permite crear entrenadores');
INSERT INTO privileges (name, description) VALUES ('Ver entrenadores', 'Permite ver entrenadores');
INSERT INTO privileges (name, description) VALUES ('Actualizar entrenadores', 'Permite actualizar entrenadores');
INSERT INTO privileges (name, description) VALUES ('Borrar entrenadores', 'Permite borrar entrenadores');
INSERT INTO privileges (name, description) VALUES ('Asignar entrenadores a usuarios', 'Permite borrar entrenadores');
INSERT INTO privileges (name, description) VALUES ('Crear ejercicios', 'Permite crear ejercicios');
INSERT INTO privileges (name, description) VALUES ('Ver ejercicios', 'Permite ver ejercicios');
INSERT INTO privileges (name, description) VALUES ('Actualizar ejercicios', 'Permite actualizar ejercicios');
INSERT INTO privileges (name, description) VALUES ('Borrar ejercicios', 'Permite borrar ejercicios');
INSERT INTO privileges (name, description) VALUES ('Crear eventos', 'Permite crear eventos');
INSERT INTO privileges (name, description) VALUES ('Ver eventos', 'Permite ver eventos');
INSERT INTO privileges (name, description) VALUES ('Actualizar eventos', 'Permite actualizar eventos');
INSERT INTO privileges (name, description) VALUES ('Borrar eventos', 'Permite borrar eventos');
INSERT INTO privileges (name, description) VALUES ('Consultar historial de actividades', 'Permite consultar historial de actividades');
INSERT INTO privileges (name, description) VALUES ('Consultar historial de rutinas', 'Permite consultar historial de rutinas');
INSERT INTO privileges (name, description) VALUES ('Consultar historial de metricas de rendimiento', 'Permite consultar historial de metricas de rendimiento');

-- Roles
INSERT INTO roles (name, description) VALUES ('Usuario', 'Este es un rol usuario');
INSERT INTO roles (name, description) VALUES ('Entrenador', 'Este es un rol entrenador');
INSERT INTO roles (name, description) VALUES ('Administrador', 'Este es un rol administrador');


-- Roles_Privileges

-- Usuarios
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (1, 7);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (1, 8);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (1, 9);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (1, 10);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (1, 15);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (1, 16);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (1, 6);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (1, 30);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (1, 31);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (1, 32);

-- Entrenadores
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (2, 11);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (2, 12);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (2, 13);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (2, 14);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (2, 15);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (2, 16);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (2, 5);

-- Administradores
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (3, 1);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (3, 2);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (3, 3);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (3, 4);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (3, 17);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (3, 18);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (3, 19);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (3, 20);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (3, 21);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (3, 22);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (3, 23);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (3, 24);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (3, 25);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (3, 26);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (3, 27);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (3, 28);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (3, 29);

-- Users
INSERT INTO users (name, encrypted_password, photo_url, active, creation_date) VALUES ('Carolina Moreno', '1lsjfldjLEUIUER#234', 'url1', true, '2025-09-15');
INSERT INTO users (name, encrypted_password, photo_url, active, creation_date) VALUES ('Hector Mejia', 'kjfhsd234', 'url2', true, '2025-09-15');
INSERT INTO users (name, encrypted_password, photo_url, active, creation_date) VALUES ('Eliana Torrealba', '9ksjfhkjhsdf@asf', 'url3', true, '2025-09-15');
INSERT INTO users (name, encrypted_password, photo_url, active, creation_date) VALUES ('Daniela Castaño', 'dsfdsf0978@dsfer', 'url4', true, '2025-09-15');
INSERT INTO users (name, encrypted_password, photo_url, active, creation_date) VALUES ('Simon Garcia', 'sdfkjdh30498sdfjh', 'url5', true, '2025-09-15');
INSERT INTO users (name, encrypted_password, photo_url, active, creation_date) VALUES ('Juan Jose Angarita', '787SDKHkhdfhKJldfd', 'url6', true, '2025-09-15');
INSERT INTO users (name, encrypted_password, photo_url, active, creation_date) VALUES ('James Rodriguez', 'ewfdsf7sfkkhOkjhdf@', 'url7', false, '2025-09-15');

-- UserRoles
INSERT INTO user_roles (user_id, role_id, assigned_date) VALUES (1, 1, '2025-09-15');
INSERT INTO user_roles (user_id, role_id, assigned_date) VALUES (2, 2, '2025-09-02');
INSERT INTO user_roles (user_id, role_id, assigned_date) VALUES (3, 3, '2025-08-22');
INSERT INTO user_roles (user_id, role_id, assigned_date) VALUES (4, 1, '2022-10-04');
INSERT INTO user_roles (user_id, role_id, assigned_date) VALUES (5, 1, '2009-01-07');
INSERT INTO user_roles (user_id, role_id, assigned_date) VALUES (6, 2, '2015-07-28');
INSERT INTO user_roles (user_id, role_id, assigned_date) VALUES (7, 3, '2018-05-26');

-- TrainersTrainee
INSERT INTO trainer_trainee (trainer_id, trainee_id, start_date, end_date) VALUES (1, 2, '2025-08-24', '2025-09-24');
INSERT INTO trainer_trainee (trainer_id, trainee_id, start_date, end_date) VALUES (3, 4, '2025-04-17', '2025-06-17');

-- Messages
INSERT INTO messages (sent_date, read_date, text, thread, trainer_id, trainee_id) VALUES ('2025-09-14', '2025-09-15', 'thread1', 'This is a message 1', 1, 2);
INSERT INTO messages (sent_date, read_date, thread, text, trainer_id, trainee_id) VALUES ('2025-04-14', '2025-07-15', '2', 'This is a message 2', 3, 4);
INSERT INTO messages (sent_date, read_date, thread, text, trainer_id, trainee_id) VALUES ('2024-10-14', '2025-01-09', '3', 'This is a message 3', 3, 4);

-- WorkoutPrograms
INSERT INTO workoutprograms (name, description, photo_url, create_by_trainer, creator_id, creation_date, completed) VALUES ('Workout program 1', 'This is the workout program 1', 'photoUrl1', true, 1, '2020-10-22', false);
INSERT INTO workoutprograms (name, description, photo_url, create_by_trainer, creator_id, creation_date, completed) VALUES ('Workout program 2', 'This is the workout program 2', 'photoUrl2', false, 6, '2024-11-26', true);

-- UsersWorkoutPrograms
INSERT INTO users_workoutprograms (user_id, workout_id) VALUES (2, 1);
INSERT INTO users_workoutprograms (user_id, workout_id) VALUES (4, 2);

-- GeneralProgress
INSERT INTO general_progress (type, percentage, user_id, workout_id) VALUES ('Cardio hiit', 120.123, 2, 1);
INSERT INTO general_progress (type, percentage, user_id, workout_id) VALUES ('Cardio hiit', 34, 4, 2);

-- Recommendations
INSERT INTO recommendations (content, comment_date, progress_id, trainer_id, trainee_id) VALUES ('This is a recommendation 1', '2023-07-15', 1, 1, 2);
INSERT INTO recommendations (content, comment_date, progress_id, trainer_id, trainee_id) VALUES ('This is a recommendation 2', '2022-12-05', 1, 1, 2);
INSERT INTO recommendations (content, comment_date, progress_id, trainer_id, trainee_id) VALUES ('This is a recommendation 3', '2019-02-03', 2, 3, 4);
INSERT INTO recommendations (content, comment_date, progress_id, trainer_id, trainee_id) VALUES ('This is a recommendation 4', '2005-03-08', 2, 3, 4);

-- Exercises
INSERT INTO exercises (name, type, description, difficulty, video_url, progress_unit, estimated_unitary_calories_burnt) VALUES ('Burpee', 'Cardio hiit', 'Medium', 'videoUrl1', 'videoUrl1', 'Calories', 64.5);

-- WorkoutExercises
INSERT INTO workout_exercises (workout_program_id, exercise_id, exercise_progress, series) VALUES (1, 1, 23.78, 10);

-- EventTypes
INSERT INTO event_types (name, description) VALUES ('Religioso', 'Esta es la descripcion de un evento tipo religioso');
INSERT INTO event_types (name, description) VALUES ('Social', 'Esta es la descripcion de un evento tipo social');
INSERT INTO event_types (name, description) VALUES ('Educativo', 'Esta es la descripcion de un evento tipo educativo');

-- AvailableSpaces
INSERT INTO available_spaces (name, location, location_max_attendees) VALUES ('Coliseo 1', 'Por los parqueaderos del edificio L', 200);

-- Events
INSERT INTO events (name, date_time_start, date_time_end, description, creation_date, availablespace_id, event_type_id, creator_id, max_attendees, status, estimated_burnt_calories) VALUES ('Aniversario de Andy', '2025-09-18', '2025-09-19', 'Este es el aniversario 11 de Andy', '2025-09-17', 1, 1, 1, 200, 'Terminado', 175);

-- EventsAttendaces
INSERT INTO events_attendance (event_id, user_id, date_inscription) VALUES (1, 1, '2025-09-17');

-- HistoricalRecords
INSERT INTO historical_records (user_id, event_id, workout_program_id, details, estimated_burnt_calories) VALUES (1, 1, 1, 'Este es un ejemplo de un detalle', 175);

-- Notifications
INSERT INTO notifications (title, text, creation_date_time, event_id) VALUES ('Notificacion1', 'Texto de la notificacion 1', '2025-09-15', 1);

-- ReceivedNotifications
INSERT INTO received_notifications (notification_id, user_id) VALUES (1, 1);
