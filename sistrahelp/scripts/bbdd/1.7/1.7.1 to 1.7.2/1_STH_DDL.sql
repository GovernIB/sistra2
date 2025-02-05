create table STH_PROCES
(
   PROC_IDENT           VARCHAR2(20 CHAR)    not null,
   PROC_INSTAN          VARCHAR2(50 CHAR),
   PROC_FECHA           DATE
);

comment on table STH_PROCES is
'Control ejecución procesos background.
Para que una sola instancia se autoconfigure como maestro.';

comment on column STH_PROCES.PROC_IDENT is
'Identificador fijo';

comment on column STH_PROCES.PROC_INSTAN is
'Id instancia';

comment on column STH_PROCES.PROC_FECHA is
'Fecha ultima verificación';

alter table STH_PROCES
   add constraint STH_PROCES_PK primary key (PROC_IDENT);

ALTER TABLE STH_AVISCONFIG ADD AVI_FECHA DATE;
comment on column  STH_AVISCONFIG.AVI_FECHA is 'Fecha de la última verificación';

ALTER TABLE STH_AVISCONFIG ADD AVI_ACTIVO NUMBER(1,0) default 1 not null;
comment on column  STH_AVISCONFIG.AVI_ACTIVO is 'Indica si la alerta está activa';

ALTER TABLE STH_AVISCONFIG ADD AVI_HORA_RESUMEN VARCHAR2(5 CHAR);
comment on column  STH_AVISCONFIG.AVI_HORA_RESUMEN is 'Hora resumen';
