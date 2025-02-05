/** 27/11/2023 issue: #787*/

create table STH_SESION
(
   SESI_USUA VARCHAR2(100 CHAR) not null,
   SESI_FECHA DATE not null,
   SESI_PROPS VARCHAR2(4000 CHAR)
);

comment on table STH_SESION is
'Sesiones usuario';

comment on column STH_SESION.SESI_USUA is
'Identificador fijo';

comment on column STH_SESION.SESI_FECHA is
'Fecha ultima sesión';

comment on column STH_SESION.SESI_PROPS is
'Lista serializada propiedades (codigo  - valor)';

alter table STH_SESION
   add constraint STH_SESION_PK primary key (SESI_USUA);

GRANT SELECT, DELETE, INSERT, UPDATE ON STH_SESION TO WWW_SISTRAHELP;


/** 02/01/2024 issue: #738*/
ALTER TABLE STH_AVISCONFIG MODIFY AVI_EVENTOS VARCHAR2(1024 CHAR);
ALTER TABLE STH_HISTAVIS MODIFY HIST_EVENTO VARCHAR2(1024 CHAR);


COMMIT;
