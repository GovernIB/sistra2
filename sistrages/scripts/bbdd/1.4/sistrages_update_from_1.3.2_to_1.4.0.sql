create table STG_SECREU
(
   SREU_CODIGO          NUMBER(18)           not null,
   SREU_CODENT          NUMBER(18)           not null,
   SREU_CODFOR           NUMBER(18),
   SREU_IDENTI          VARCHAR2(3 CHAR)     not null,
   SREU_DESCR           VARCHAR2(255 CHAR)   not null,
   SREU_BLOQ            NUMBER(1)            default 0,
   SREU_BLOQID          VARCHAR2(10 CHAR),
   SREU_BLOQUS          VARCHAR2(255 CHAR),
   SREU_RELEAS          NUMBER(8),
   SREU_HUELLA          VARCHAR2(20 CHAR),
   SREU_ACTIVA			NUMBER(1,0) DEFAULT 0
);

comment on table STG_SECREU is
'SECCIONES REUSABLES';

comment on column STG_SECREU.SREU_CODIGO is
'Codigo interno';

comment on column STG_SECREU.SREU_CODENT is
'Codigo entidad';

comment on column STG_SECREU.SREU_CODFOR is
'Codigo fornulario asociado';

comment on column STG_SECREU.SREU_IDENTI is
'Identificador funcional entidad';

comment on column STG_SECREU.SREU_DESCR is
'Descripcion';

comment on column STG_SECREU.SREU_BLOQ is
'Version bloqueada';

comment on column STG_SECREU.SREU_BLOQID is
'Identificacion del usuario que bloquea la version';

comment on column STG_SECREU.SREU_BLOQUS is
'Nombre y apellidos del usuario que bloquea la version';

comment on column STG_SECREU.SREU_RELEAS is
'Realease actual, se crea al desbloquear la version';

comment on column STG_SECREU.SREU_HUELLA is
'Numero random que identifica versiones entre diferentes entornos';

comment on column STG_SECREU.SREU_ACTIVA is
'Indica si está activa';

alter table STG_SECREU
   add constraint STG_SECREU_PK primary key (SREU_CODIGO);

alter table STG_SECREU
   add constraint FK_STG_SECR_REFERENCE_STG_FORM foreign key (SREU_CODFOR)
      references STG_FORMUL (FOR_CODIGO);

alter table STG_SECREU
   add constraint STG_SECREU_ENTIDA_FK foreign key (SREU_CODENT)
      references STG_ENTIDA (ENT_CODIGO);

create table STG_HISSRU
(
   HSR_CODIGO           NUMBER(18)           not null,
   HSR_CODSRU           NUMBER(18)           not null,
   HSR_FECHA            DATE                 not null,
   HSR_ACCION           VARCHAR2(1 CHAR)     not null,
   HSR_RELEAS           NUMBER(8)            not null,
   HSR_HUELLA           VARCHAR2(20 CHAR),
   HSR_CAMBIO           VARCHAR2(255 CHAR)   not null,
   HSR_USER             VARCHAR2(100 CHAR)   not null
);

comment on table STG_HISSRU is
'Historial seccion reusable';

comment on column STG_HISSRU.HSR_CODIGO is
'Codigo';

comment on column STG_HISSRU.HSR_CODSRU is
'Codigo seccion reusable';

comment on column STG_HISSRU.HSR_FECHA is
'Fecha';

comment on column STG_HISSRU.HSR_ACCION is
'Tipo accion: C (Creacion) / M (Modificacion) / I (Importacion)';

comment on column STG_HISSRU.HSR_RELEAS is
'Release version';

comment on column STG_HISSRU.HSR_HUELLA is
'Huella version';

comment on column STG_HISSRU.HSR_CAMBIO is
'Detalle cambio';

comment on column STG_HISSRU.HSR_USER is
'Usuario';

alter table STG_HISSRU
   add constraint STG_HISSRU_PK primary key (HSR_CODIGO);

alter table STG_HISSRU
   add constraint STG_HISSRU_SECREU_FK foreign key (HSR_CODSRU)
      references STG_SECREU (SREU_CODIGO);

create table STG_SCRSRU
(
   SSR_CODIGO           NUMBER(18)           not null,
   SSR_CODSRU           NUMBER(18)           not null,
   SSR_CODSCR           NUMBER(18)           not null,
   SSR_TIPSCR           VARCHAR2(3)          not null
);

comment on table STG_SCRSRU is
'Scripts seccion reusable';

comment on column STG_SCRSRU.SSR_CODIGO is
'Codigo interno';

comment on column STG_SCRSRU.SSR_CODSRU is
'Codigo seccion reusable';

comment on column STG_SCRSRU.SSR_CODSCR is
'Codigo script';

comment on column STG_SCRSRU.SSR_TIPSCR is
'Tipo script (CDI: Carga datos iniciales)';

alter table STG_SCRSRU
   add constraint STG_SCRSRU_PK primary key (SSR_CODIGO);

alter table STG_SCRSRU
   add constraint FK_STG_SCRS_REFERENCE_STG_SCRI foreign key (SSR_CODSCR)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_SCRSRU
   add constraint STG_SCRSRU_SECREU_FK foreign key (SSR_CODSRU)
      references STG_SECREU (SREU_CODIGO);

create table STG_FORSRU
(
   FSE_CODIGO           NUMBER(18)           not null,
   FSE_CODSRU           NUMBER(18)           not null,
   FSE_LETRA            VARCHAR2(2 CHAR)     not null
);

comment on table STG_FORSRU is
'Seccion reusable formulario';

comment on column STG_FORSRU.FSE_CODIGO is
'Codigo';

comment on column STG_FORSRU.FSE_CODSRU is
'Codigo seccion reusable';

comment on column STG_FORSRU.FSE_LETRA is
'Letra de la seccion';

alter table STG_FORSRU
   add constraint STG_FORSRU_PK primary key (FSE_CODIGO);

alter table STG_FORSRU
   add constraint STG_FORSRU_FORELE_FK foreign key (FSE_CODIGO)
      references STG_FORELE (FEL_CODIGO);

alter table STG_FORSRU
   add constraint STG_SECREU_FORSRU_FK foreign key (FSE_CODSRU)
      references STG_SECREU (SREU_CODIGO);

create sequence STG_SECREU_SEQ;
create sequence STG_HISSRU_SEQ;
create sequence STG_SCRSRU_SEQ;
create sequence STG_FORSRU_SEQ;

update STG_TRAIDI set TRI_LITERA = 'Finalizar' where TRI_CODTRA in (SELECT PTR_DESCRI from STG_PASOTR where PTR_TIPPAS='rt') and TRI_IDIOMA='es';
update STG_TRAIDI set TRI_LITERA = 'Finalitzar' where TRI_CODTRA in (SELECT PTR_DESCRI from STG_PASOTR where PTR_TIPPAS='rt') and TRI_IDIOMA='ca';

create sequence STG_TRADEX_SEQ;
create sequence STG_TREIDI_SEQ;

create table STG_TRADEX
(
   TEX_CODIGO           NUMBER(18)           not null
);

comment on table STG_TRADEX is
'Tabla de literales multidioma extendido';

comment on column STG_TRADEX.TEX_CODIGO is
'Código interno';

alter table STG_TRADEX
   add constraint STG_TRADEX_PK primary key (TEX_CODIGO);
   
   
create table STG_TREIDI
(
   TEI_CODIGO           NUMBER(18)           not null,
   TEI_CODTRA           NUMBER(18)           not null,
   TEI_LITERA           CLOB  				 not null,
   TEI_IDIOMA           VARCHAR2(2 CHAR)     not null
);

comment on table STG_TREIDI is
'Traducción literales extendido';

comment on column STG_TREIDI.TEI_CODIGO is
'Código interno';

comment on column STG_TREIDI.TEI_CODTRA is
'Código traducción';

comment on column STG_TREIDI.TEI_LITERA is
'Literal';

comment on column STG_TREIDI.TEI_IDIOMA is
'Idioma';

alter table STG_TREIDI
   add constraint STG_TREIDI_PK primary key (TEI_CODIGO);
   
   
   
 alter table STG_ENTIDA
			drop constraint STG_ENTIDA_TRADUC_FK13;
 alter table STG_ENTIDA
			add constraint STG_ENTIDA_TRADUC_FK13 
			foreign key (ENT_ACCTT) references STG_TRADEX(TEX_CODIGO);

update stg_cnfglo set cfg_valor = '1.4' where cfg_prop = 'sistra2.version';
update STG_CNFGLO set CFG_VALOR = '0' where cfg_prop = 'sistra2.version.patch';

commit;
