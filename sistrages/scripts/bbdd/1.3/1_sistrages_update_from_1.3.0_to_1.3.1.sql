alter table STG_VERTRA add VTR_TIPTRA    VARCHAR2(1 CHAR)     default 'T' not null;
comment on column STG_VERTRA.VTR_TIPTRA is 'Tipo de trámite: Trámite (T) / Servicio (S)';

alter table STG_PASREG  add   PRG_DESTIN           VARCHAR2(1 CHAR)     default 'R' not null;
comment on column STG_PASREG.PRG_DESTIN is 'Destino: Registro (R) / Envío (E)';

alter table STG_PASREG add  PRG_CODEVR           NUMBER(18);
comment on column STG_PASREG.PRG_CODEVR is 'Para tipo Envío: Código envío remoto';

comment on column STG_PASREG.PRG_REGOFI is 'Para tipo Registro: Código oficina registro';

comment on column STG_PASREG.PRG_REGLIB is 'Para tipo Registro: Código libro registro';

comment on column STG_PASREG.PRG_SCRREG is 'Para tipo Registro: Script destino registro';

create table STG_ENVREM
(
   EVR_CODIGO           NUMBER(18)           not null,
   EVR_AMBITO           VARCHAR2(1 CHAR)     not null,
   ER_CODENT            NUMBER(18),
   EVR_CODARE           NUMBER(18),
   EVR_IDENTI           VARCHAR2(20 CHAR)    not null,
   EVR_DESCR            VARCHAR2(255 CHAR)   not null,
   EVR_URL              VARCHAR2(500 CHAR)   not null,
   EVR_CODCAU           NUMBER(18),
   EVR_TIMEOUT          NUMBER(4)
);

comment on table STG_ENVREM is
'Envío remoto';

comment on column STG_ENVREM.EVR_CODIGO is
'Código interno';

comment on column STG_ENVREM.EVR_AMBITO is
'Ambito dominio (E: Entidad / A: Área)';

comment on column STG_ENVREM.ER_CODENT is
'Código entidad';

comment on column STG_ENVREM.EVR_CODARE is
'Código área';

comment on column STG_ENVREM.EVR_IDENTI is
'Identificador dominio';

comment on column STG_ENVREM.EVR_DESCR is
'Descripción';

comment on column STG_ENVREM.EVR_URL is
'Url acceso';

comment on column STG_ENVREM.EVR_CODCAU is
'Código configuración autenticación';

comment on column STG_ENVREM.EVR_TIMEOUT is
'Timeout (segundos)';

alter table STG_ENVREM
   add constraint STG_ENVREM_PK primary key (EVR_CODIGO);

alter table STG_ENVREM
   add constraint STG_ENVREM_AREA_FK foreign key (EVR_CODARE)
      references STG_AREA (ARE_CODIGO);

alter table STG_ENVREM
   add constraint STG_ENVREM_ENTIDA_FK foreign key (ER_CODENT)
      references STG_ENTIDA (ENT_CODIGO);

alter table STG_PASREG
   add constraint STG_PASREG_ENVREM_FK foreign key (PRG_CODEVR)
      references STG_ENVREM (EVR_CODIGO);

create sequence STG_ENVREM_SEQ;


/** 20/05/2022 #535 **/
ALTER TABLE STG_ENTIDA
ADD ENT_TITULOSTT NUMBER(18,0) NULL
CONSTRAINT STG_ENTIDA_TRADUC_FK12 REFERENCES STG_TRADUC(TRA_CODIGO);

COMMENT ON COLUMN STG_ENTIDA.ENT_TITULOSTT IS 'Título página Sistramit';

ALTER TABLE STG_ENTIDA
ADD ENT_ICONOSTT NUMBER(18,0) NULL
CONSTRAINT STG_ENTIDA_FICHER_FK4 REFERENCES STG_FICHER(FIC_CODIGO);

COMMENT ON COLUMN STG_ENTIDA.ENT_ICONOSTT  IS 'Icono página Sistramit';

INSERT INTO STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) VALUES (STG_CNFGLO_SEQ.nextval, 'sistramit.anexos.extensionesPermitidas', 'pptx,jpg,jpeg,txt,xml,xsig,xlsx,odg,odt,ods,pdf,odp,png,svg,tiff,docx,rtf', 'Extensiones permitidas en los anexos de SISTRAMIT');

update STG_CNFGLO set CFG_VALOR = '1' where cfg_prop = 'sistra2.version.patch';
