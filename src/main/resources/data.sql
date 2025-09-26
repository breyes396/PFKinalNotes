-- Limpieza (orden para respetar FK). Si la BD está vacía estas sentencias simplemente no afectarán.
DELETE FROM Notas;
DELETE FROM estudiante_curso;
DELETE FROM Docentes;
DELETE FROM Estudiantes;
DELETE FROM Cursos;
DELETE FROM Usuarios;

-- Usuarios (roles válidos: STUDENT, TEACHER, ADMIN)
INSERT INTO Usuarios(correo, contrasena, rol) VALUES
 ('wilsonDiaz@gmail.com','12345678','STUDENT'),
 ('saulSatbaja@gmail.com','12345678','STUDENT'),
 ('wilsonYaxon@gmail.com','12345678','STUDENT'),
 ('matiasTuvubal@gmail.com','12345678','STUDENT'),
 ('jeffersonDiaz@gmail.com','12345678','TEACHER'),
 ('wilsonMorales@gmail.com','12345678','TEACHER'),
 ('angelTucubal@gmail.com','12345678','TEACHER'),
 ('javierDiaz@gmail.com','12345678','TEACHER'),
 ('ricardoLucero@gmail.com','12345678','TEACHER'),
 ('matiasITcotoya@gmail.com','12345678','ADMIN'),
 ('wilsonSosa@gmail.com','12345678','ADMIN'),
 ('BillyDiaz@gmail.com','12345678','ADMIN'),
 ('saulSipack@gmail.com','12345678','ADMIN'),
 ('BillyYaxon@gmail.com','12345678','ADMIN');

-- Cursos básicos por nivel (12 cursos)
INSERT INTO Cursos(nombre, grado) VALUES
 ('Matemática','Principiante'),
 ('Matemática','Intermedio'),
 ('Matemática','Avanzado'),
 ('Lenguaje','Principiante'),
 ('Lenguaje','Intermedio'),
 ('Lenguaje','Avanzado'),
 ('Programación','Principiante'),
 ('Programación','Intermedio'),
 ('Programación','Avanzado'),
 ('Física','Principiante'),
 ('Física','Intermedio'),
 ('Física','Avanzado');

-- Estudiantes (mapeo a usuarios STUDENT)
INSERT INTO Estudiantes(nombre, apellido, codigo_usuario)
SELECT 'Wilson','Diaz', u.codigo FROM Usuarios u WHERE u.correo='wilsonDiaz@gmail.com';
INSERT INTO Estudiantes(nombre, apellido, codigo_usuario)
SELECT 'Saul','Satbaja', u.codigo FROM Usuarios u WHERE u.correo='saulSatbaja@gmail.com';
INSERT INTO Estudiantes(nombre, apellido, codigo_usuario)
SELECT 'Wilson','Yaxon', u.codigo FROM Usuarios u WHERE u.correo='wilsonYaxon@gmail.com';
INSERT INTO Estudiantes(nombre, apellido, codigo_usuario)
SELECT 'Matias','Tuvubal', u.codigo FROM Usuarios u WHERE u.correo='matiasTuvubal@gmail.com';

-- Docentes (asignamos 5 cursos específicos a 5 docentes)
-- Cada docente solo puede tener un curso y cada curso un docente (constraints únicos)
INSERT INTO Docentes(nombre, codigo_curso, codigo_usuario)
SELECT 'Jefferson Diaz', c.codigo, u.codigo FROM Cursos c, Usuarios u
 WHERE c.nombre='Matemática' AND c.grado='Principiante' AND u.correo='jeffersonDiaz@gmail.com';
INSERT INTO Docentes(nombre, codigo_curso, codigo_usuario)
SELECT 'Wilson Morales', c.codigo, u.codigo FROM Cursos c, Usuarios u
 WHERE c.nombre='Programación' AND c.grado='Principiante' AND u.correo='wilsonMorales@gmail.com';
INSERT INTO Docentes(nombre, codigo_curso, codigo_usuario)
SELECT 'Angel Tucubal', c.codigo, u.codigo FROM Cursos c, Usuarios u
 WHERE c.nombre='Lenguaje' AND c.grado='Principiante' AND u.correo='angelTucubal@gmail.com';
INSERT INTO Docentes(nombre, codigo_curso, codigo_usuario)
SELECT 'Javier Diaz', c.codigo, u.codigo FROM Cursos c, Usuarios u
 WHERE c.nombre='Física' AND c.grado='Principiante' AND u.correo='javierDiaz@gmail.com';
INSERT INTO Docentes(nombre, codigo_curso, codigo_usuario)
SELECT 'Ricardo Lucero', c.codigo, u.codigo FROM Cursos c, Usuarios u
 WHERE c.nombre='Matemática' AND c.grado='Intermedio' AND u.correo='ricardoLucero@gmail.com';

-- Inscripciones (estudiante_curso)
-- Wilson Diaz en Matemática Principiante y Programación Principiante
INSERT INTO estudiante_curso(codigo_estudiante, codigo_curso)
SELECT e.codigo, c.codigo FROM Estudiantes e, Cursos c
 WHERE e.nombre='Wilson' AND e.apellido='Diaz' AND c.nombre='Matemática' AND c.grado='Principiante';
INSERT INTO estudiante_curso(codigo_estudiante, codigo_curso)
SELECT e.codigo, c.codigo FROM Estudiantes e, Cursos c
 WHERE e.nombre='Wilson' AND e.apellido='Diaz' AND c.nombre='Programación' AND c.grado='Principiante';
-- Saul Satbaja en Lenguaje Principiante y Matemática Intermedio
INSERT INTO estudiante_curso(codigo_estudiante, codigo_curso)
SELECT e.codigo, c.codigo FROM Estudiantes e, Cursos c
 WHERE e.nombre='Saul' AND e.apellido='Satbaja' AND c.nombre='Lenguaje' AND c.grado='Principiante';
INSERT INTO estudiante_curso(codigo_estudiante, codigo_curso)
SELECT e.codigo, c.codigo FROM Estudiantes e, Cursos c
 WHERE e.nombre='Saul' AND e.apellido='Satbaja' AND c.nombre='Matemática' AND c.grado='Intermedio';
-- Wilson Yaxon en Física Principiante
INSERT INTO estudiante_curso(codigo_estudiante, codigo_curso)
SELECT e.codigo, c.codigo FROM Estudiantes e, Cursos c
 WHERE e.nombre='Wilson' AND e.apellido='Yaxon' AND c.nombre='Física' AND c.grado='Principiante';
-- Matias Tuvubal en Programación Principiante y Matemática Principiante
INSERT INTO estudiante_curso(codigo_estudiante, codigo_curso)
SELECT e.codigo, c.codigo FROM Estudiantes e, Cursos c
 WHERE e.nombre='Matias' AND e.apellido='Tuvubal' AND c.nombre='Programación' AND c.grado='Principiante';
INSERT INTO estudiante_curso(codigo_estudiante, codigo_curso)
SELECT e.codigo, c.codigo FROM Estudiantes e, Cursos c
 WHERE e.nombre='Matias' AND e.apellido='Tuvubal' AND c.nombre='Matemática' AND c.grado='Principiante';

-- Notas (ejemplos)
-- Nota para Wilson Diaz en Matemática Principiante (docente Jefferson Diaz)
INSERT INTO Notas(fecha, calificacion, codigo_curso, codigo_estudiante, codigo_docente)
SELECT CURDATE(), 85, c.codigo, e.codigo, d.codigo
 FROM Cursos c, Estudiantes e, Docentes d
 WHERE c.nombre='Matemática' AND c.grado='Principiante'
   AND e.nombre='Wilson' AND e.apellido='Diaz'
   AND d.codigo_curso=c.codigo;
-- Nota para Wilson Diaz en Programación Principiante (docente Wilson Morales)
INSERT INTO Notas(fecha, calificacion, codigo_curso, codigo_estudiante, codigo_docente)
SELECT CURDATE(), 90, c.codigo, e.codigo, d.codigo
 FROM Cursos c, Estudiantes e, Docentes d
 WHERE c.nombre='Programación' AND c.grado='Principiante'
   AND e.nombre='Wilson' AND e.apellido='Diaz'
   AND d.codigo_curso=c.codigo;
-- Nota para Saul Satbaja en Lenguaje Principiante (docente Angel Tucubal)
INSERT INTO Notas(fecha, calificacion, codigo_curso, codigo_estudiante, codigo_docente)
SELECT CURDATE(), 78, c.codigo, e.codigo, d.codigo
 FROM Cursos c, Estudiantes e, Docentes d
 WHERE c.nombre='Lenguaje' AND c.grado='Principiante'
   AND e.nombre='Saul' AND e.apellido='Satbaja'
   AND d.codigo_curso=c.codigo;
-- Nota para Wilson Yaxon en Física Principiante (docente Javier Diaz)
INSERT INTO Notas(fecha, calificacion, codigo_curso, codigo_estudiante, codigo_docente)
SELECT CURDATE(), 88, c.codigo, e.codigo, d.codigo
 FROM Cursos c, Estudiantes e, Docentes d
 WHERE c.nombre='Física' AND c.grado='Principiante'
   AND e.nombre='Wilson' AND e.apellido='Yaxon'
   AND d.codigo_curso=c.codigo;
-- Nota para Matias Tuvubal en Programación Principiante (docente Wilson Morales)
INSERT INTO Notas(fecha, calificacion, codigo_curso, codigo_estudiante, codigo_docente)
SELECT CURDATE(), 92, c.codigo, e.codigo, d.codigo
 FROM Cursos c, Estudiantes e, Docentes d
 WHERE c.nombre='Programación' AND c.grado='Principiante'
   AND e.nombre='Matias' AND e.apellido='Tuvubal'
   AND d.codigo_curso=c.codigo;
-- Nota para Matias Tuvubal en Matemática Principiante (docente Jefferson Diaz)
INSERT INTO Notas(fecha, calificacion, codigo_curso, codigo_estudiante, codigo_docente)
SELECT CURDATE(), 81, c.codigo, e.codigo, d.codigo
 FROM Cursos c, Estudiantes e, Docentes d
 WHERE c.nombre='Matemática' AND c.grado='Principiante'
   AND e.nombre='Matias' AND e.apellido='Tuvubal'
   AND d.codigo_curso=c.codigo;
