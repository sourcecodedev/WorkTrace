USE [master]
GO

CREATE DATABASE [db_WorkTrace]
GO
USE [db_WorkTrace]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[asistencia](
	[id_asistencia] [int] IDENTITY(1,1) NOT NULL,
	[id_trabajador] [int] NOT NULL,
	[id_horario_asignacion] [int] NOT NULL,
	[fecha] [date] NOT NULL,
	[hora_entrada] [time](7) NOT NULL,
	[hora_salida] [time](7) NULL,
	[estado] [varchar](20) NOT NULL,
PRIMARY KEY CLUSTERED
(
	[id_asistencia] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[bitacora_trabajador]    Script Date: 17/05/2025 12:27:22  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[bitacora_trabajador](
	[id_bitacora] [int] IDENTITY(1,1) NOT NULL,
	[id_trabajador] [int] NOT NULL,
	[campo_modificado] [varchar](100) NOT NULL,
	[valor_anterior] [varchar](255) NULL,
	[valor_nuevo] [varchar](255) NULL,
	[fecha_cambio] [datetime] NULL,
	[id_usuario_admin] [int] NOT NULL,
PRIMARY KEY CLUSTERED
(
	[id_bitacora] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[dia_semana]    Script Date: 17/05/2025 12:27:22  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[dia_semana](
	[id_dia_semana] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](20) NOT NULL,
PRIMARY KEY CLUSTERED
(
	[id_dia_semana] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[distrito_trabajo]    Script Date: 17/05/2025 12:27:22  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[distrito_trabajo](
	[id_distrito_trabajo] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](100) NOT NULL,
PRIMARY KEY CLUSTERED
(
	[id_distrito_trabajo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[horario_asignacion]    Script Date: 17/05/2025 12:27:22  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[horario_asignacion](
	[id_horario_asignacion] [int] IDENTITY(1,1) NOT NULL,
	[id_trabajador] [int] NOT NULL,
	[fecha_asignacion] [datetime] NULL,
	[observacion] [varchar](255) NULL,
	[activo] [bit] NULL,
PRIMARY KEY CLUSTERED
(
	[id_horario_asignacion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[horario_detalle]    Script Date: 17/05/2025 12:27:22  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[horario_detalle](
	[id_horario_detalle] [int] IDENTITY(1,1) NOT NULL,
	[id_horario_asignacion] [int] NOT NULL,
	[id_dia_semana] [int] NOT NULL,
	[hora_entrada] [time](7) NOT NULL,
	[hora_salida] [time](7) NOT NULL,
PRIMARY KEY CLUSTERED
(
	[id_horario_detalle] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[log_acceso_trabajador]    Script Date: 17/05/2025 12:27:22  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[log_acceso_trabajador](
	[id_log_acceso] [int] IDENTITY(1,1) NOT NULL,
	[id_usuario] [int] NOT NULL,
	[fecha_acceso] [datetime] NULL,
	[dispositivo] [varchar](100) NULL,
	[ip] [varchar](50) NULL,
	[latitud] [decimal](10, 7) NULL,
	[longitud] [decimal](10, 7) NULL,
PRIMARY KEY CLUSTERED
(
	[id_log_acceso] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[marcacion]    Script Date: 17/05/2025 12:27:22  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[marcacion](
	[id_marcacion] [int] IDENTITY(1,1) NOT NULL,
	[id_asistencia] [int] NOT NULL,
	[tipo_marcacion] [varchar](10) NOT NULL,
	[fecha_marcacion] [date] NOT NULL,
	[hora_marcacion] [time](7) NOT NULL,
	[latitud] [decimal](10, 7) NULL,
	[longitud] [decimal](10, 7) NULL,
	[ubicacion_texto] [varchar](150) NULL,
	[fuente] [varchar](20) NULL,
PRIMARY KEY CLUSTERED
(
	[id_marcacion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[rastreo_ubicacion]    Script Date: 17/05/2025 12:27:22  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[rastreo_ubicacion](
	[id_rastreo] [int] IDENTITY(1,1) NOT NULL,
	[id_asistencia] [int] NOT NULL,
	[timestamp_registro] [datetime] NOT NULL,
	[latitud] [decimal](10, 7) NOT NULL,
	[longitud] [decimal](10, 7) NOT NULL,
PRIMARY KEY CLUSTERED
(
	[id_rastreo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[reporte_generado]    Script Date: 17/05/2025 12:27:22  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[reporte_generado](
	[id_reporte] [int] IDENTITY(1,1) NOT NULL,
	[id_usuario_admin] [int] NOT NULL,
	[id_tipo_reporte] [int] NOT NULL,
	[id_trabajador] [int] NULL,
	[fecha_inicio] [date] NOT NULL,
	[fecha_fin] [date] NOT NULL,
	[fecha_generacion] [datetime] NULL,
PRIMARY KEY CLUSTERED
(
	[id_reporte] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tipo_contrato]    Script Date: 17/05/2025 12:27:22  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tipo_contrato](
	[id_tipo_contrato] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](100) NOT NULL,
PRIMARY KEY CLUSTERED
(
	[id_tipo_contrato] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tipo_reporte]    Script Date: 17/05/2025 12:27:22  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tipo_reporte](
	[id_tipo_reporte] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](100) NOT NULL,
	[descripcion] [varchar](255) NULL,
PRIMARY KEY CLUSTERED
(
	[id_tipo_reporte] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tipo_usuario]    Script Date: 17/05/2025 12:27:22  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tipo_usuario](
	[id_tipo_usuario] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED
(
	[id_tipo_usuario] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[trabajador]    Script Date: 17/05/2025 12:27:22  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[trabajador](
	[id_trabajador] [int] IDENTITY(1,1) NOT NULL,
	[id_usuario] [int] NOT NULL,
	[nombres_apellidos] [varchar](150) NOT NULL,
	[puesto] [varchar](100) NOT NULL,
	[jefe_inmediato] [varchar](150) NOT NULL,
	[id_tipo_contrato] [int] NOT NULL,
	[direccion] [varchar](200) NOT NULL,
	[telefono] [varchar](20) NULL,
	[id_distrito_trabajo] [int] NOT NULL,
	[estado] [tinyint] NULL,
	[fecha_registro] [datetime] NULL,
	[id_usuario_creador] [int] NULL,
PRIMARY KEY CLUSTERED
(
	[id_trabajador] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[usuario]    Script Date: 17/05/2025 12:27:22  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[usuario](
	[id_usuario] [int] IDENTITY(1,1) NOT NULL,
	[username] [varchar](100) NOT NULL,
	[password] [varchar](255) NOT NULL,
	[id_tipo_usuario] [int] NOT NULL,
	[estado] [tinyint] NULL,
	[fecha_creacion] [datetime] NULL,
PRIMARY KEY CLUSTERED
(
	[id_usuario] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[asistencia] ON
GO
INSERT [dbo].[asistencia] ([id_asistencia], [id_trabajador], [id_horario_asignacion], [fecha], [hora_entrada], [hora_salida], [estado]) VALUES (1, 1, 1, CAST(N'2025-05-13' AS Date), CAST(N'08:01:00' AS Time), CAST(N'17:00:00' AS Time), N'PRESENTE')
GO
SET IDENTITY_INSERT [dbo].[asistencia] OFF
GO
SET IDENTITY_INSERT [dbo].[dia_semana] ON
GO
INSERT [dbo].[dia_semana] ([id_dia_semana], [nombre]) VALUES (7, N'Domingo')
GO
INSERT [dbo].[dia_semana] ([id_dia_semana], [nombre]) VALUES (4, N'Jueves')
GO
INSERT [dbo].[dia_semana] ([id_dia_semana], [nombre]) VALUES (1, N'Lunes')
GO
INSERT [dbo].[dia_semana] ([id_dia_semana], [nombre]) VALUES (2, N'Martes')
GO
INSERT [dbo].[dia_semana] ([id_dia_semana], [nombre]) VALUES (3, N'Miércoles')
GO
INSERT [dbo].[dia_semana] ([id_dia_semana], [nombre]) VALUES (6, N'Sábado')
GO
INSERT [dbo].[dia_semana] ([id_dia_semana], [nombre]) VALUES (5, N'Viernes')
GO
SET IDENTITY_INSERT [dbo].[dia_semana] OFF
GO
SET IDENTITY_INSERT [dbo].[distrito_trabajo] ON
GO
INSERT [dbo].[distrito_trabajo] ([id_distrito_trabajo], [nombre]) VALUES (5, N'Callao')
GO
INSERT [dbo].[distrito_trabajo] ([id_distrito_trabajo], [nombre]) VALUES (1, N'Lima')
GO
INSERT [dbo].[distrito_trabajo] ([id_distrito_trabajo], [nombre]) VALUES (3, N'Miraflores')
GO
INSERT [dbo].[distrito_trabajo] ([id_distrito_trabajo], [nombre]) VALUES (2, N'San Isidro')
GO
INSERT [dbo].[distrito_trabajo] ([id_distrito_trabajo], [nombre]) VALUES (4, N'Surco')
GO
SET IDENTITY_INSERT [dbo].[distrito_trabajo] OFF
GO
SET IDENTITY_INSERT [dbo].[horario_asignacion] ON
GO
INSERT [dbo].[horario_asignacion] ([id_horario_asignacion], [id_trabajador], [fecha_asignacion], [observacion], [activo]) VALUES (1, 1, CAST(N'2025-05-17T00:00:00.000' AS DateTime), N'Horario regular de oficina', 1)
GO
SET IDENTITY_INSERT [dbo].[horario_asignacion] OFF
GO
SET IDENTITY_INSERT [dbo].[horario_detalle] ON
GO
INSERT [dbo].[horario_detalle] ([id_horario_detalle], [id_horario_asignacion], [id_dia_semana], [hora_entrada], [hora_salida]) VALUES (1, 1, 1, CAST(N'08:00:00' AS Time), CAST(N'17:00:00' AS Time))
GO
INSERT [dbo].[horario_detalle] ([id_horario_detalle], [id_horario_asignacion], [id_dia_semana], [hora_entrada], [hora_salida]) VALUES (2, 1, 2, CAST(N'08:00:00' AS Time), CAST(N'17:00:00' AS Time))
GO
INSERT [dbo].[horario_detalle] ([id_horario_detalle], [id_horario_asignacion], [id_dia_semana], [hora_entrada], [hora_salida]) VALUES (3, 1, 3, CAST(N'08:00:00' AS Time), CAST(N'17:00:00' AS Time))
GO
INSERT [dbo].[horario_detalle] ([id_horario_detalle], [id_horario_asignacion], [id_dia_semana], [hora_entrada], [hora_salida]) VALUES (4, 1, 4, CAST(N'08:00:00' AS Time), CAST(N'17:00:00' AS Time))
GO
INSERT [dbo].[horario_detalle] ([id_horario_detalle], [id_horario_asignacion], [id_dia_semana], [hora_entrada], [hora_salida]) VALUES (5, 1, 5, CAST(N'08:00:00' AS Time), CAST(N'17:00:00' AS Time))
GO
SET IDENTITY_INSERT [dbo].[horario_detalle] OFF
GO
SET IDENTITY_INSERT [dbo].[marcacion] ON
GO
INSERT [dbo].[marcacion] ([id_marcacion], [id_asistencia], [tipo_marcacion], [fecha_marcacion], [hora_marcacion], [latitud], [longitud], [ubicacion_texto], [fuente]) VALUES (1, 1, N'ENTRADA', CAST(N'2025-05-13' AS Date), CAST(N'08:01:00' AS Time), CAST(-12.0464000 AS Decimal(10, 7)), CAST(-77.0428000 AS Decimal(10, 7)), N'Oficina Central', N'APP')
GO
INSERT [dbo].[marcacion] ([id_marcacion], [id_asistencia], [tipo_marcacion], [fecha_marcacion], [hora_marcacion], [latitud], [longitud], [ubicacion_texto], [fuente]) VALUES (2, 1, N'SALIDA', CAST(N'2025-05-13' AS Date), CAST(N'17:00:00' AS Time), CAST(-12.0464000 AS Decimal(10, 7)), CAST(-77.0428000 AS Decimal(10, 7)), N'Oficina Central', N'APP')
GO
SET IDENTITY_INSERT [dbo].[marcacion] OFF
GO
SET IDENTITY_INSERT [dbo].[rastreo_ubicacion] ON
GO
INSERT [dbo].[rastreo_ubicacion] ([id_rastreo], [id_asistencia], [timestamp_registro], [latitud], [longitud]) VALUES (1, 1, CAST(N'2025-05-13T08:30:00.000' AS DateTime), CAST(-12.0464000 AS Decimal(10, 7)), CAST(-77.0428000 AS Decimal(10, 7)))
GO
INSERT [dbo].[rastreo_ubicacion] ([id_rastreo], [id_asistencia], [timestamp_registro], [latitud], [longitud]) VALUES (2, 1, CAST(N'2025-05-13T09:00:00.000' AS DateTime), CAST(-12.0464000 AS Decimal(10, 7)), CAST(-77.0428000 AS Decimal(10, 7)))
GO
INSERT [dbo].[rastreo_ubicacion] ([id_rastreo], [id_asistencia], [timestamp_registro], [latitud], [longitud]) VALUES (3, 1, CAST(N'2025-05-13T09:30:00.000' AS DateTime), CAST(-12.0464000 AS Decimal(10, 7)), CAST(-77.0428000 AS Decimal(10, 7)))
GO
INSERT [dbo].[rastreo_ubicacion] ([id_rastreo], [id_asistencia], [timestamp_registro], [latitud], [longitud]) VALUES (4, 1, CAST(N'2025-05-13T10:00:00.000' AS DateTime), CAST(-12.0464000 AS Decimal(10, 7)), CAST(-77.0428000 AS Decimal(10, 7)))
GO
INSERT [dbo].[rastreo_ubicacion] ([id_rastreo], [id_asistencia], [timestamp_registro], [latitud], [longitud]) VALUES (5, 1, CAST(N'2025-05-13T10:30:00.000' AS DateTime), CAST(-12.0464000 AS Decimal(10, 7)), CAST(-77.0428000 AS Decimal(10, 7)))
GO
SET IDENTITY_INSERT [dbo].[rastreo_ubicacion] OFF
GO
SET IDENTITY_INSERT [dbo].[reporte_generado] ON
GO
INSERT [dbo].[reporte_generado] ([id_reporte], [id_usuario_admin], [id_tipo_reporte], [id_trabajador], [fecha_inicio], [fecha_fin], [fecha_generacion]) VALUES (1, 1, 1, 1, CAST(N'2025-05-13' AS Date), CAST(N'2025-05-13' AS Date), CAST(N'2025-05-17T10:00:00.000' AS DateTime))
GO
SET IDENTITY_INSERT [dbo].[reporte_generado] OFF
GO
SET IDENTITY_INSERT [dbo].[tipo_contrato] ON
GO
INSERT [dbo].[tipo_contrato] ([id_tipo_contrato], [nombre]) VALUES (1, N'CAS')
GO
INSERT [dbo].[tipo_contrato] ([id_tipo_contrato], [nombre]) VALUES (4, N'Honorarios')
GO
INSERT [dbo].[tipo_contrato] ([id_tipo_contrato], [nombre]) VALUES (2, N'Locación de Servicios')
GO
INSERT [dbo].[tipo_contrato] ([id_tipo_contrato], [nombre]) VALUES (3, N'Planilla')
GO
INSERT [dbo].[tipo_contrato] ([id_tipo_contrato], [nombre]) VALUES (5, N'Prácticas')
GO
SET IDENTITY_INSERT [dbo].[tipo_contrato] OFF
GO
SET IDENTITY_INSERT [dbo].[tipo_reporte] ON
GO
INSERT [dbo].[tipo_reporte] ([id_tipo_reporte], [nombre], [descripcion]) VALUES (1, N'Resumen General', N'Cantidad de asistencias e inasistencias')
GO
INSERT [dbo].[tipo_reporte] ([id_tipo_reporte], [nombre], [descripcion]) VALUES (2, N'Porcentaje de Asistencia', N'Cálculo de porcentaje de cumplimiento')
GO
INSERT [dbo].[tipo_reporte] ([id_tipo_reporte], [nombre], [descripcion]) VALUES (3, N'Ranking de Puntualidad', N'Comparativa de horarios de ingreso')
GO
INSERT [dbo].[tipo_reporte] ([id_tipo_reporte], [nombre], [descripcion]) VALUES (4, N'Reporte Diario', N'Detalle diario de asistencia por trabajador')
GO
SET IDENTITY_INSERT [dbo].[tipo_reporte] OFF
GO
SET IDENTITY_INSERT [dbo].[tipo_usuario] ON
GO
INSERT [dbo].[tipo_usuario] ([id_tipo_usuario], [nombre]) VALUES (1, N'ADMINISTRADOR')
GO
INSERT [dbo].[tipo_usuario] ([id_tipo_usuario], [nombre]) VALUES (2, N'TRABAJADOR')
GO
SET IDENTITY_INSERT [dbo].[tipo_usuario] OFF
GO
SET IDENTITY_INSERT [dbo].[trabajador] ON
GO
INSERT [dbo].[trabajador] ([id_trabajador], [id_usuario], [nombres_apellidos], [puesto], [jefe_inmediato], [id_tipo_contrato], [direccion], [telefono], [id_distrito_trabajo], [estado], [fecha_registro], [id_usuario_creador]) VALUES (1, 2, N'Pedro Sánchez', N'Soporte Técnico', N'Lucía Gutiérrez', 1, N'Av. Primavera 123', N'999888777', 1, 1, CAST(N'2025-05-17T00:00:00.000' AS DateTime), 1)
GO
SET IDENTITY_INSERT [dbo].[trabajador] OFF
GO
SET IDENTITY_INSERT [dbo].[usuario] ON
GO
INSERT [dbo].[usuario] ([id_usuario], [username], [password], [id_tipo_usuario], [estado], [fecha_creacion]) VALUES (1, N'admin01', N'admin123', 1, 1, CAST(N'2025-05-17T00:00:00.000' AS DateTime))
GO
INSERT [dbo].[usuario] ([id_usuario], [username], [password], [id_tipo_usuario], [estado], [fecha_creacion]) VALUES (2, N'trabajador01', N'trab123', 2, 1, CAST(N'2025-05-17T00:00:00.000' AS DateTime))
GO
SET IDENTITY_INSERT [dbo].[usuario] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__dia_sema__72AFBCC6283F05F5]    Script Date: 17/05/2025 12:27:22  ******/
ALTER TABLE [dbo].[dia_semana] ADD UNIQUE NONCLUSTERED
(
	[nombre] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__distrito__72AFBCC6A5B47DE8]    Script Date: 17/05/2025 12:27:22  ******/
ALTER TABLE [dbo].[distrito_trabajo] ADD UNIQUE NONCLUSTERED
(
	[nombre] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__tipo_con__72AFBCC6AE31766F]    Script Date: 17/05/2025 12:27:22  ******/
ALTER TABLE [dbo].[tipo_contrato] ADD UNIQUE NONCLUSTERED
(
	[nombre] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__tipo_rep__72AFBCC6E98CD586]    Script Date: 17/05/2025 12:27:22  ******/
ALTER TABLE [dbo].[tipo_reporte] ADD UNIQUE NONCLUSTERED
(
	[nombre] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__tipo_usu__72AFBCC680627C56]    Script Date: 17/05/2025 12:27:22  ******/
ALTER TABLE [dbo].[tipo_usuario] ADD UNIQUE NONCLUSTERED
(
	[nombre] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__usuario__F3DBC572D7A9DBB1]    Script Date: 17/05/2025 12:27:22  ******/
ALTER TABLE [dbo].[usuario] ADD UNIQUE NONCLUSTERED
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[asistencia] ADD  DEFAULT ('PRESENTE') FOR [estado]
GO
ALTER TABLE [dbo].[bitacora_trabajador] ADD  DEFAULT (getdate()) FOR [fecha_cambio]
GO
ALTER TABLE [dbo].[horario_asignacion] ADD  DEFAULT (getdate()) FOR [fecha_asignacion]
GO
ALTER TABLE [dbo].[horario_asignacion] ADD  DEFAULT ((1)) FOR [activo]
GO
ALTER TABLE [dbo].[log_acceso_trabajador] ADD  DEFAULT (getdate()) FOR [fecha_acceso]
GO
ALTER TABLE [dbo].[marcacion] ADD  DEFAULT ('APP') FOR [fuente]
GO
ALTER TABLE [dbo].[reporte_generado] ADD  DEFAULT (getdate()) FOR [fecha_generacion]
GO
ALTER TABLE [dbo].[trabajador] ADD  DEFAULT ((1)) FOR [estado]
GO
ALTER TABLE [dbo].[trabajador] ADD  DEFAULT (getdate()) FOR [fecha_registro]
GO
ALTER TABLE [dbo].[usuario] ADD  DEFAULT ((1)) FOR [estado]
GO
ALTER TABLE [dbo].[usuario] ADD  DEFAULT (getdate()) FOR [fecha_creacion]
GO
ALTER TABLE [dbo].[asistencia]  WITH CHECK ADD FOREIGN KEY([id_horario_asignacion])
REFERENCES [dbo].[horario_asignacion] ([id_horario_asignacion])
GO
ALTER TABLE [dbo].[asistencia]  WITH CHECK ADD FOREIGN KEY([id_trabajador])
REFERENCES [dbo].[trabajador] ([id_trabajador])
GO
ALTER TABLE [dbo].[bitacora_trabajador]  WITH CHECK ADD FOREIGN KEY([id_trabajador])
REFERENCES [dbo].[trabajador] ([id_trabajador])
GO
ALTER TABLE [dbo].[bitacora_trabajador]  WITH CHECK ADD FOREIGN KEY([id_usuario_admin])
REFERENCES [dbo].[usuario] ([id_usuario])
GO
ALTER TABLE [dbo].[horario_asignacion]  WITH CHECK ADD FOREIGN KEY([id_trabajador])
REFERENCES [dbo].[trabajador] ([id_trabajador])
GO
ALTER TABLE [dbo].[horario_detalle]  WITH CHECK ADD FOREIGN KEY([id_dia_semana])
REFERENCES [dbo].[dia_semana] ([id_dia_semana])
GO
ALTER TABLE [dbo].[horario_detalle]  WITH CHECK ADD FOREIGN KEY([id_horario_asignacion])
REFERENCES [dbo].[horario_asignacion] ([id_horario_asignacion])
GO
ALTER TABLE [dbo].[log_acceso_trabajador]  WITH CHECK ADD FOREIGN KEY([id_usuario])
REFERENCES [dbo].[usuario] ([id_usuario])
GO
ALTER TABLE [dbo].[marcacion]  WITH CHECK ADD FOREIGN KEY([id_asistencia])
REFERENCES [dbo].[asistencia] ([id_asistencia])
GO
ALTER TABLE [dbo].[rastreo_ubicacion]  WITH CHECK ADD FOREIGN KEY([id_asistencia])
REFERENCES [dbo].[asistencia] ([id_asistencia])
GO
ALTER TABLE [dbo].[reporte_generado]  WITH CHECK ADD FOREIGN KEY([id_tipo_reporte])
REFERENCES [dbo].[tipo_reporte] ([id_tipo_reporte])
GO
ALTER TABLE [dbo].[reporte_generado]  WITH CHECK ADD FOREIGN KEY([id_trabajador])
REFERENCES [dbo].[trabajador] ([id_trabajador])
GO
ALTER TABLE [dbo].[reporte_generado]  WITH CHECK ADD FOREIGN KEY([id_usuario_admin])
REFERENCES [dbo].[usuario] ([id_usuario])
GO
ALTER TABLE [dbo].[trabajador]  WITH CHECK ADD FOREIGN KEY([id_distrito_trabajo])
REFERENCES [dbo].[distrito_trabajo] ([id_distrito_trabajo])
GO
ALTER TABLE [dbo].[trabajador]  WITH CHECK ADD FOREIGN KEY([id_tipo_contrato])
REFERENCES [dbo].[tipo_contrato] ([id_tipo_contrato])
GO
ALTER TABLE [dbo].[trabajador]  WITH CHECK ADD FOREIGN KEY([id_usuario])
REFERENCES [dbo].[usuario] ([id_usuario])
GO
ALTER TABLE [dbo].[trabajador]  WITH CHECK ADD FOREIGN KEY([id_usuario_creador])
REFERENCES [dbo].[usuario] ([id_usuario])
GO
ALTER TABLE [dbo].[usuario]  WITH CHECK ADD FOREIGN KEY([id_tipo_usuario])
REFERENCES [dbo].[tipo_usuario] ([id_tipo_usuario])
GO
ALTER TABLE [dbo].[asistencia]  WITH CHECK ADD  CONSTRAINT [chk_estado_asistencia] CHECK  (([estado]='TARDANZA' OR [estado]='FALTA' OR [estado]='PRESENTE'))
GO
ALTER TABLE [dbo].[asistencia] CHECK CONSTRAINT [chk_estado_asistencia]
GO
