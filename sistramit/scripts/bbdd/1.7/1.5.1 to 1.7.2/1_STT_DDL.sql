create table STT_TRAETG
(
    ETG_CODIGO           NUMBER(19)           not null,
    ETG_IDESTR           VARCHAR2(50 CHAR)    not null,
    ETG_ENTIDAD          VARCHAR2(20 CHAR)    not null,
    ETG_ASIENT           CLOB                 not null,
    ETG_FCFITR           TIMESTAMP            not null,
    ETG_ESTADO           VARCHAR2(1 CHAR)     default 'x' not null,
    ETG_IDENV            VARCHAR2(50 CHAR),
    ETG_INMEDI           NUMBER(1)            default 0 not null,
    ETG_FCETG            TIMESTAMP,
    ETG_ERRMSG           VARCHAR2(1000 CHAR),
    ETG_FCBLOQ           TIMESTAMP
);

comment on table STT_TRAETG is
'TRAMITES ENTREGADOS (CES2)';

comment on column STT_TRAETG.ETG_CODIGO is
'Código';

comment on column STT_TRAETG.ETG_IDESTR is
'ID Sesión Tramitación';

comment on column STT_TRAETG.ETG_ENTIDAD is
'ID Entidad';

comment on column STT_TRAETG.ETG_ASIENT is
'Asiento (JSON)';

comment on column STT_TRAETG.ETG_FCFITR is
'Fecha fin trámite';

comment on column STT_TRAETG.ETG_ESTADO is
'Estado
 - n: pendiente entregar
 - e: error entrega (se reintenta)
 - s: entregado';

comment on column STT_TRAETG.ETG_IDENV is
'ID Sesión Envío Remoto';

comment on column STT_TRAETG.ETG_INMEDI is
'Si es inmediata';

comment on column STT_TRAETG.ETG_FCETG is
'Fecha entrega';

comment on column STT_TRAETG.ETG_ERRMSG is
'En caso de error, indica mensaje error';

comment on column STT_TRAETG.ETG_FCBLOQ is
'Fecha bloqueado para procesar';

alter table STT_TRAETG add constraint STT_TRAETG_PK primary key (ETG_CODIGO);

CREATE SEQUENCE STT_TRAETG_SEQ;

create unique index STT_TRAETG_UK on STT_TRAETG (  ETG_IDESTR ASC );


ALTER table STT_TRAFIN ADD TRF_NUMENV VARCHAR2(50 CHAR);
comment on column STT_TRAFIN.TRF_NUMENV is 'Número envío (si modo CES2)';

ALTER table STT_TRAFIN ADD TRF_PROCP VARCHAR2(20 CHAR);
comment on column STT_TRAFIN.TRF_PROCP is 'Código en catálogo procedimientos';

ALTER table STT_TRAFIN ADD TRF_FECREG TIMESTAMP;
comment on column STT_TRAFIN.TRF_FECREG is 'Fecha registro (en caso registro)';
