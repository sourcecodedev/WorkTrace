USE [db_WorkTrace]
GO
/****** Object:  StoredProcedure [dbo].[usp_ActualizarAsistencia]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_ActualizarAsistencia]
    @id_asistencia          INT,
    @hora_entrada           TIME,
    @hora_salida            TIME,
    @estado                 VARCHAR(20)
AS
BEGIN
    UPDATE asistencia
    SET hora_entrada = @hora_entrada,
        hora_salida = @hora_salida,
        estado = @estado
    WHERE id_asistencia = @id_asistencia;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ActualizarDistritoTrabajo]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_ActualizarDistritoTrabajo]
    @id_distrito_trabajo INT,
    @nombre              VARCHAR(100)
AS
BEGIN
    UPDATE distrito_trabajo
    SET nombre = @nombre
    WHERE id_distrito_trabajo = @id_distrito_trabajo;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ActualizarHorarioAsignacion]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_ActualizarHorarioAsignacion]
    @id_horario_asignacion INT,
    @fecha_asignacion      DATETIME,
    @observacion           VARCHAR(255),
    @activo                BIT
AS
BEGIN
    UPDATE horario_asignacion
    SET fecha_asignacion = @fecha_asignacion,
        observacion = @observacion,
        activo = @activo
    WHERE id_horario_asignacion = @id_horario_asignacion;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ActualizarHorarioDetalle]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_ActualizarHorarioDetalle]
    @id_horario_detalle    INT,
    @id_dia_semana         INT,
    @hora_entrada          TIME,
    @hora_salida           TIME
AS
BEGIN
    UPDATE horario_detalle
    SET id_dia_semana = @id_dia_semana,
        hora_entrada = @hora_entrada,
        hora_salida = @hora_salida
    WHERE id_horario_detalle = @id_horario_detalle;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ActualizarMarcacion]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_ActualizarMarcacion]
    @id_marcacion      INT,
    @hora_marcacion    TIME,
    @latitud           DECIMAL(10, 7),
    @longitud          DECIMAL(10, 7),
    @ubicacion_texto   VARCHAR(150)
AS
BEGIN
    UPDATE marcacion
    SET hora_marcacion = @hora_marcacion,
        latitud = @latitud,
        longitud = @longitud,
        ubicacion_texto = @ubicacion_texto
    WHERE id_marcacion = @id_marcacion;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ActualizarRastreoUbicacion]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_ActualizarRastreoUbicacion]
    @id_rastreo          INT,
    @latitud             DECIMAL(10, 7),
    @longitud            DECIMAL(10, 7)
AS
BEGIN
    UPDATE rastreo_ubicacion
    SET latitud = @latitud,
        longitud = @longitud
    WHERE id_rastreo = @id_rastreo;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ActualizarTipoContrato]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_ActualizarTipoContrato]
    @id_tipo_contrato INT,
    @nombre           VARCHAR(100)
AS
BEGIN
    UPDATE tipo_contrato
    SET nombre = @nombre
    WHERE id_tipo_contrato = @id_tipo_contrato;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ActualizarTipoUsuario]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_ActualizarTipoUsuario]
    @id_tipo_usuario INT,
    @nombre          VARCHAR(50)
AS
BEGIN
    UPDATE tipo_usuario
    SET nombre = @nombre
    WHERE id_tipo_usuario = @id_tipo_usuario;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ActualizarTrabajador]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
 CREATE PROCEDURE [dbo].[usp_ActualizarTrabajador]
    @id_trabajador        INT,
    @nombres_apellidos    VARCHAR(150),
    @puesto               VARCHAR(100),
    @jefe_inmediato       VARCHAR(150),
    @id_tipo_contrato     INT,
    @direccion            VARCHAR(200),
    @telefono             VARCHAR(20),
    @id_distrito_trabajo  INT
AS
BEGIN
    UPDATE trabajador
    SET nombres_apellidos = @nombres_apellidos,
        puesto = @puesto,
        jefe_inmediato = @jefe_inmediato,
        id_tipo_contrato = @id_tipo_contrato,
        direccion = @direccion,
        telefono = @telefono,
        id_distrito_trabajo = @id_distrito_trabajo
    WHERE id_trabajador = @id_trabajador;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ActualizarUsuario]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
 CREATE PROCEDURE [dbo].[usp_ActualizarUsuario]
    @id_usuario      INT,
    @username        VARCHAR(100),
    @password        VARCHAR(255),
    @id_tipo_usuario INT
AS
BEGIN
    UPDATE usuario
    SET username = @username,
        password = @password,
        id_tipo_usuario = @id_tipo_usuario
    WHERE id_usuario = @id_usuario;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_EliminarAsistencia]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_EliminarAsistencia]
    @id_asistencia INT
AS
BEGIN
    DELETE FROM asistencia
    WHERE id_asistencia = @id_asistencia;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_EliminarDistritoTrabajo]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_EliminarDistritoTrabajo]
    @id_distrito_trabajo INT
AS
BEGIN
    DELETE FROM distrito_trabajo
    WHERE id_distrito_trabajo = @id_distrito_trabajo;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_EliminarHorarioAsignacion]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_EliminarHorarioAsignacion]
    @id_horario_asignacion INT
AS
BEGIN
    DELETE FROM horario_asignacion
    WHERE id_horario_asignacion = @id_horario_asignacion;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_EliminarHorarioDetalle]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_EliminarHorarioDetalle]
    @id_horario_detalle INT
AS
BEGIN
    DELETE FROM horario_detalle
    WHERE id_horario_detalle = @id_horario_detalle;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_EliminarMarcacion]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_EliminarMarcacion]
    @id_marcacion INT
AS
BEGIN
    DELETE FROM marcacion
    WHERE id_marcacion = @id_marcacion;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_EliminarRastreoUbicacion]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_EliminarRastreoUbicacion]
    @id_rastreo INT
AS
BEGIN
    DELETE FROM rastreo_ubicacion
    WHERE id_rastreo = @id_rastreo;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_EliminarTipoContrato]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_EliminarTipoContrato]
    @id_tipo_contrato INT
AS
BEGIN
    DELETE FROM tipo_contrato
    WHERE id_tipo_contrato = @id_tipo_contrato;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_EliminarTipoUsuario]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_EliminarTipoUsuario]
    @id_tipo_usuario INT
AS
BEGIN
    DELETE FROM tipo_usuario
    WHERE id_tipo_usuario = @id_tipo_usuario;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_EliminarTrabajador]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_EliminarTrabajador]
    @id_trabajador INT
AS
BEGIN
    UPDATE trabajador
    SET estado = 0
    WHERE id_trabajador = @id_trabajador;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_EliminarUsuario]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_EliminarUsuario]
    @id_usuario INT
AS
BEGIN
    UPDATE usuario
    SET estado = 0
    WHERE id_usuario = @id_usuario;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_InsertarAsistencia]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_InsertarAsistencia]
    @id_trabajador          INT,
    @id_horario_asignacion  INT,
    @fecha                  DATE,
    @hora_entrada           TIME,
    @hora_salida            TIME = NULL,
    @estado                 VARCHAR(20) = 'PRESENTE'
AS
BEGIN
    INSERT INTO asistencia (
        id_trabajador,
        id_horario_asignacion,
        fecha,
        hora_entrada,
        hora_salida,
        estado
    )
    VALUES (
        @id_trabajador,
        @id_horario_asignacion,
        @fecha,
        @hora_entrada,
        @hora_salida,
        @estado
    );
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_InsertarDistritoTrabajo]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_InsertarDistritoTrabajo]
    @nombre VARCHAR(100)
AS
BEGIN
    INSERT INTO distrito_trabajo (nombre)
    VALUES (@nombre);
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_InsertarHorarioAsignacion]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_InsertarHorarioAsignacion]
    @id_trabajador      INT,
    @fecha_asignacion   DATETIME,
    @observacion        VARCHAR(255),
    @activo             BIT
AS
BEGIN
    INSERT INTO horario_asignacion (
        id_trabajador,
        fecha_asignacion,
        observacion,
        activo
    )
    VALUES (
        @id_trabajador,
        @fecha_asignacion,
        @observacion,
        @activo
    );
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_InsertarHorarioDetalle]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
 CREATE PROCEDURE [dbo].[usp_InsertarHorarioDetalle]
    @id_horario_asignacion INT,
    @id_dia_semana         INT,
    @hora_entrada          TIME,
    @hora_salida           TIME
AS
BEGIN
    INSERT INTO horario_detalle (
        id_horario_asignacion,
        id_dia_semana,
        hora_entrada,
        hora_salida
    )
    VALUES (
        @id_horario_asignacion,
        @id_dia_semana,
        @hora_entrada,
        @hora_salida
    );
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_InsertarMarcacion]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_InsertarMarcacion]
    @id_asistencia     INT,
    @tipo_marcacion    VARCHAR(10),
    @fecha_marcacion   DATE,
    @hora_marcacion    TIME,
    @latitud           DECIMAL(10, 7),
    @longitud          DECIMAL(10, 7),
    @ubicacion_texto   VARCHAR(150),
    @fuente            VARCHAR(20)
AS
BEGIN
    INSERT INTO marcacion (
        id_asistencia,
        tipo_marcacion,
        fecha_marcacion,
        hora_marcacion,
        latitud,
        longitud,
        ubicacion_texto,
        fuente
    )
    VALUES (
        @id_asistencia,
        @tipo_marcacion,
        @fecha_marcacion,
        @hora_marcacion,
        @latitud,
        @longitud,
        @ubicacion_texto,
        @fuente
    );
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_InsertarRastreoUbicacion]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_InsertarRastreoUbicacion]
    @id_asistencia       INT,
    @timestamp_registro  DATETIME,
    @latitud             DECIMAL(10, 7),
    @longitud            DECIMAL(10, 7)
AS
BEGIN
    INSERT INTO rastreo_ubicacion (
        id_asistencia,
        timestamp_registro,
        latitud,
        longitud
    )
    VALUES (
        @id_asistencia,
        @timestamp_registro,
        @latitud,
        @longitud
    );
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_InsertarTipoContrato]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_InsertarTipoContrato]
    @nombre VARCHAR(100)
AS
BEGIN
    INSERT INTO tipo_contrato (nombre)
    VALUES (@nombre);
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_InsertarTipoUsuario]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_InsertarTipoUsuario]
    @nombre VARCHAR(50)
AS
BEGIN
    INSERT INTO tipo_usuario (nombre)
    VALUES (@nombre);
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_InsertarTrabajador]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_InsertarTrabajador]
    @id_usuario           INT,
    @nombres_apellidos    VARCHAR(150),
    @puesto               VARCHAR(100),
    @jefe_inmediato       VARCHAR(150),
    @id_tipo_contrato     INT,
    @direccion            VARCHAR(200),
    @telefono             VARCHAR(20),
    @id_distrito_trabajo  INT,
    @id_usuario_creador   INT
AS
BEGIN
    INSERT INTO trabajador (
        id_usuario,
        nombres_apellidos,
        puesto,
        jefe_inmediato,
        id_tipo_contrato,
        direccion,
        telefono,
        id_distrito_trabajo,
        estado,
        fecha_registro,
        id_usuario_creador
    )
    VALUES (
        @id_usuario,
        @nombres_apellidos,
        @puesto,
        @jefe_inmediato,
        @id_tipo_contrato,
        @direccion,
        @telefono,
        @id_distrito_trabajo,
        1,
        GETDATE(),
        @id_usuario_creador
    );
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_InsertarUsuario]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_InsertarUsuario]
    @username        VARCHAR(100),
    @password        VARCHAR(255),
    @id_tipo_usuario INT
AS
BEGIN
    INSERT INTO usuario (username, password, id_tipo_usuario, estado, fecha_creacion)
    VALUES (@username, @password, @id_tipo_usuario, 1, GETDATE());
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ListarAsistenciaPorTrabajador]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[usp_ListarAsistenciaPorTrabajador]
    @id_trabajador INT
AS
BEGIN
    SELECT  [id_asistencia], [id_trabajador], [id_horario_asignacion], [fecha], [hora_entrada], [hora_salida], [estado]
    FROM asistencia
    WHERE id_trabajador = @id_trabajador
    ORDER BY fecha DESC;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ListarDistritoTrabajo]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_ListarDistritoTrabajo]
AS
BEGIN
    SELECT id_distrito_trabajo, nombre
    FROM distrito_trabajo
    ORDER BY nombre;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ListarHorarioAsignacion]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_ListarHorarioAsignacion]
AS
BEGIN
    SELECT [id_horario_asignacion], [id_trabajador], [fecha_asignacion], [observacion], [activo]
    FROM horario_asignacion
    ORDER BY fecha_asignacion DESC;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ListarHorarioDetallePorAsignacion]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_ListarHorarioDetallePorAsignacion]
    @id_horario_asignacion INT
AS
BEGIN
    SELECT [id_horario_detalle], [id_horario_asignacion], [id_dia_semana], [hora_entrada], [hora_salida]
    FROM horario_detalle
    WHERE id_horario_asignacion = @id_horario_asignacion
    ORDER BY id_dia_semana;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ListarMarcacionesPorAsistencia]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_ListarMarcacionesPorAsistencia]
    @id_asistencia INT
AS
BEGIN
    SELECT  [id_marcacion], [id_asistencia], [tipo_marcacion], [fecha_marcacion], [hora_marcacion], [latitud], [longitud], [ubicacion_texto], [fuente]
    FROM marcacion
    WHERE id_asistencia = @id_asistencia
    ORDER BY fecha_marcacion, hora_marcacion;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ListarRastreoPorAsistencia]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_ListarRastreoPorAsistencia]
    @id_asistencia INT
AS
BEGIN
    SELECT [id_rastreo], [id_asistencia], [timestamp_registro], [latitud], [longitud]
    FROM rastreo_ubicacion
    WHERE id_asistencia = @id_asistencia
    ORDER BY timestamp_registro;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ListarTipoContrato]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_ListarTipoContrato]
AS
BEGIN
    SELECT id_tipo_contrato, nombre
    FROM tipo_contrato
    ORDER BY nombre;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ListarTipoUsuario]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_ListarTipoUsuario]
AS
BEGIN
    SELECT id_tipo_usuario, nombre
    FROM tipo_usuario
    ORDER BY nombre;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ListarTrabajadores]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_ListarTrabajadores]
AS
BEGIN
    SELECT [id_trabajador], [id_usuario], [nombres_apellidos], [puesto], [jefe_inmediato], [id_tipo_contrato], [direccion], [telefono], [id_distrito_trabajo], [estado], [fecha_registro], [id_usuario_creador]
    FROM trabajador
    WHERE estado = 1
    ORDER BY nombres_apellidos;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ListarUsuarios]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_ListarUsuarios]
AS
BEGIN
    SELECT id_usuario, username, password, id_tipo_usuario, estado, fecha_creacion
    FROM usuario
    WHERE estado = 1
    ORDER BY username;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ObtenerAsistenciaPorId]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_ObtenerAsistenciaPorId]
    @id_asistencia INT
AS
BEGIN
    SELECT  [id_asistencia], [id_trabajador], [id_horario_asignacion], [fecha], [hora_entrada], [hora_salida], [estado]
    FROM asistencia
    WHERE id_asistencia = @id_asistencia;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ObtenerDistritoTrabajoPorId]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_ObtenerDistritoTrabajoPorId]
    @id_distrito_trabajo INT
AS
BEGIN
    SELECT id_distrito_trabajo, nombre
    FROM distrito_trabajo
    WHERE id_distrito_trabajo = @id_distrito_trabajo;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ObtenerHorarioAsignacionPorId]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[usp_ObtenerHorarioAsignacionPorId]
    @id_horario_asignacion INT
AS
BEGIN
    SELECT [id_horario_asignacion], [id_trabajador], [fecha_asignacion], [observacion], [activo]    FROM horario_asignacion
    WHERE id_horario_asignacion = @id_horario_asignacion;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ObtenerHorarioDetallePorId]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_ObtenerHorarioDetallePorId]
    @id_horario_detalle INT
AS
BEGIN
    SELECT  [id_horario_detalle], [id_horario_asignacion], [id_dia_semana], [hora_entrada], [hora_salida]
    FROM horario_detalle
    WHERE id_horario_detalle = @id_horario_detalle;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ObtenerMarcacionPorId]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_ObtenerMarcacionPorId]
    @id_marcacion INT
AS
BEGIN
    SELECT [id_marcacion], [id_asistencia], [tipo_marcacion], [fecha_marcacion], [hora_marcacion], [latitud], [longitud], [ubicacion_texto], [fuente]
    FROM marcacion
    WHERE id_marcacion = @id_marcacion;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ObtenerRastreoUbicacionPorId]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_ObtenerRastreoUbicacionPorId]
    @id_rastreo INT
AS
BEGIN
    SELECT [id_rastreo], [id_asistencia], [timestamp_registro], [latitud], [longitud]
    FROM rastreo_ubicacion
    WHERE id_rastreo = @id_rastreo;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ObtenerTipoContratoPorId]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_ObtenerTipoContratoPorId]
    @id_tipo_contrato INT
AS
BEGIN
    SELECT id_tipo_contrato, nombre
    FROM tipo_contrato
    WHERE id_tipo_contrato = @id_tipo_contrato;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ObtenerTipoUsuarioPorId]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
 CREATE PROCEDURE [dbo].[usp_ObtenerTipoUsuarioPorId]
    @id_tipo_usuario INT
AS
BEGIN
    SELECT id_tipo_usuario, nombre
    FROM tipo_usuario
    WHERE id_tipo_usuario = @id_tipo_usuario;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ObtenerTrabajadorPorId]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_ObtenerTrabajadorPorId]
    @id_trabajador INT
AS
BEGIN
    SELECT [id_trabajador], [id_usuario], [nombres_apellidos], [puesto], [jefe_inmediato], [id_tipo_contrato], [direccion], [telefono], [id_distrito_trabajo], [estado], [fecha_registro], [id_usuario_creador]
    FROM trabajador
    WHERE id_trabajador = @id_trabajador;
END;
GO
/****** Object:  StoredProcedure [dbo].[usp_ObtenerUsuarioPorId]    Script Date: 18/05/2025 01:35:25  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[usp_ObtenerUsuarioPorId]
    @id_usuario INT
AS
BEGIN
    SELECT id_usuario, username, password, id_tipo_usuario, estado, fecha_creacion
    FROM usuario
    WHERE id_usuario = @id_usuario;
END;
GO
