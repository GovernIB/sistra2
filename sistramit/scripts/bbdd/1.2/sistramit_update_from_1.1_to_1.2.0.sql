/** Añadimos código SIA **/
alter table STT_FORMUL add  SFR_SIAPRO           VARCHAR2(20 CHAR);
comment on column STT_FORMUL.SFR_SIAPRO is 'Código SIA procedimiento';

/** Almacenar interesado que registra. */
alter table stt_traper DROP column TRP_NIFFIN;
alter table stt_traper DROP column TRP_NOMFIN;
alter table STT_DOCPTR add  DTP_REGNIF VARCHAR2(10 CHAR);
alter table STT_DOCPTR add  DTP_REGNOM VARCHAR2(1000 CHAR);
comment on column STT_DOCPTR.DTP_REGNIF is 'En caso de ser un registro indica nif interesado';
comment on column STT_DOCPTR.DTP_REGNOM is 'En caso de ser un registro indica nombre y apellidos del interesado';

/**	Persistencia tramites registrados  */
create sequence STT_TRAFIN_SEQ;

create table STT_TRAFIN
(
   TRF_CODIGO           NUMBER(19)           not null,
   TRF_IDESTR           VARCHAR2(50 CHAR)    not null,
   TRF_FECFIN           DATE                 not null,
   TRF_IDIOMA           VARCHAR2(2 CHAR)     not null,
   TRF_IDETRA           VARCHAR2(20 CHAR)    not null,
   TRF_VERTRA           NUMBER(2)            not null,
   TRF_DESTRA           VARCHAR2(1000 CHAR)  not null,
   TRF_PROSIA           VARCHAR2(20 CHAR),
   TRF_NIVAUT           VARCHAR2(1 CHAR)     not null,
   TRF_METAUT           VARCHAR2(50 CHAR)    not null,
   TRF_NIFINI           VARCHAR2(10 CHAR),
   TRF_NOMINI           VARCHAR2(1000 CHAR),
   TRF_NIFFIN           VARCHAR2(10 CHAR),
   TRF_NOMFIN           VARCHAR2(1000 CHAR),
   TRF_NUMREG           VARCHAR2(500 CHAR)
);

comment on table STT_TRAFIN is
'Trámites registrados (persistente)';

comment on column STT_TRAFIN.TRF_CODIGO is 'Código interno';

comment on column STT_TRAFIN.TRF_IDESTR is 'Id sesion tramitacion';

comment on column STT_TRAFIN.TRF_FECFIN is 'Fecha finalización';

comment on column STT_TRAFIN.TRF_IDIOMA is 'Idioma tramitación';

comment on column STT_TRAFIN.TRF_IDETRA is 'Identificador trámite';

comment on column STT_TRAFIN.TRF_VERTRA is 'Versión  trámite';

comment on column STT_TRAFIN.TRF_DESTRA is 'Descripción trámite';

comment on column STT_TRAFIN.TRF_PROSIA is 'Código SIA procedimiento';

comment on column STT_TRAFIN.TRF_NIVAUT is 'Nivel autenticación:  Autenticado (S) / Anónimo (N)';

comment on column STT_TRAFIN.TRF_METAUT is 'Método autenticación inicio trámite:
ANONIMO
CLAVE_CERTIFICADO
CLAVE_PIN
CLAVE_PERMANENTE';

comment on column STT_TRAFIN.TRF_NIFINI is 'Nif iniciador trámite (en caso de autenticado)';

comment on column STT_TRAFIN.TRF_NOMINI is 'Nombre iniciador trámite (en caso de autenticado)';

comment on column STT_TRAFIN.TRF_NIFFIN is 'Nif que realiza la presentación del trámite';

comment on column STT_TRAFIN.TRF_NOMFIN is 'Nombre y apellidos del que realiza la presentación del trámite';

comment on column STT_TRAFIN.TRF_NUMREG is 'Número registro';


alter table STT_TRAFIN
   add constraint PK_STT_TRAFIN primary key (TRF_CODIGO);

create unique index TRF_SESION_UK on STT_TRAFIN (
   TRF_IDESTR ASC
);

/* Campo pasa a ser multiestado. */
comment on column STT_FORMUL.SFR_CANCEL is 'Indica si el formulario se ha finalizado (0), cancelado (1) o guardado a mitad (2)';


