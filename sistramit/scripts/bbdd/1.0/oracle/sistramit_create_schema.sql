
create sequence STT_DOCPTR_SEQ;

create sequence STT_FICPTR_SEQ;

create sequence STT_FIRDPT_SEQ;

create sequence STT_FORMUL_SEQ;

create sequence STT_INVALI_SEQ;

create sequence STT_LOGINT_SEQ;

create sequence STT_PAGEXT_SEQ;

create sequence STT_PASTRP_SEQ;

create sequence STT_SESION_SEQ;

create sequence STT_TIPEVE_SEQ;

create sequence STT_TRAPER_SEQ;

/*==============================================================*/
/* Table: STT_DOCPTR                                            */
/*==============================================================*/
create table STT_DOCPTR 
(
   DTP_CODIGO           NUMBER(19)           not null,
   DTP_CODPTR           NUMBER(19)           not null,
   DTP_DOCIDE           VARCHAR2(20 CHAR)    not null,
   DTP_DOCINS           TIMESTAMP            not null,
   DTP_DOCTIP           VARCHAR2(1 CHAR)     not null,
   DTP_ESTADO           VARCHAR2(1 CHAR)     not null,
   DTP_FICHERO          NUMBER(19),
   DTP_FICCLA           VARCHAR2(50 CHAR),
   DTP_FORPDF           NUMBER(19),
   DTP_FORPDC           VARCHAR2(50 CHAR),
   DTP_ANENFI           VARCHAR2(500 CHAR),
   DTP_ANEDES           VARCHAR2(100 CHAR),
   DTP_PAGJUS           NUMBER(19),
   DTP_PAGJUC           VARCHAR2(50 CHAR),
   DTP_PAGNIF           VARCHAR2(20 CHAR),
   DTP_PAGIDE           VARCHAR2(100 CHAR),
   DTP_PAGEST           VARCHAR2(1 CHAR),
   DTP_PAGERR           VARCHAR2(100 CHAR),
   DTP_PAGERM           VARCHAR2(4000 CHAR),
   DTP_REGNUM           VARCHAR2(500 CHAR),
   DTP_REGFEC           DATE,
   DTP_REGPRE           VARCHAR2(1 CHAR)
);

comment on table STT_DOCPTR is
'Documentos paso trámite persistente';

comment on column STT_DOCPTR.DTP_CODIGO is
'Código documento persistente';

comment on column STT_DOCPTR.DTP_CODPTR is
'Código paso';

comment on column STT_DOCPTR.DTP_DOCIDE is
'Identificador documento';

comment on column STT_DOCPTR.DTP_DOCINS is
'Timestamp para marcar la instancia de un documento. El acceso a las instancias sera por orden,';

comment on column STT_DOCPTR.DTP_DOCTIP is
'Tipo documento: Formulario (f), Pago (p), Anexo (a), Registro (r),  Vble flujo (v), Justificante registro (j)';

comment on column STT_DOCPTR.DTP_ESTADO is
'Estado documento:  CORRECTO (S) / INCORRECTO (N)  / VACIO (V)';

comment on column STT_DOCPTR.DTP_FICHERO is
'Fichero';

comment on column STT_DOCPTR.DTP_FICCLA is
'Clave fichero';

comment on column STT_DOCPTR.DTP_FORPDF is
'En caso de ser un formulario puede tener un pdf de visualización (codigo)';

comment on column STT_DOCPTR.DTP_FORPDC is
'En caso de ser un formulario puede tener un pdf de visualización (clave)';

comment on column STT_DOCPTR.DTP_ANENFI is
'En caso de un anexo nos guardamos el nombre del fichero anexado para no tener que acceder al fichero';

comment on column STT_DOCPTR.DTP_ANEDES is
'En caso de un anexo gemérico se permite establecer una descripción para el documento';

comment on column STT_DOCPTR.DTP_PAGJUS is
'En caso de ser un pago puede tener un justificante de pago (codigo)';

comment on column STT_DOCPTR.DTP_PAGJUC is
'En caso de ser un pago puede tener un justificante de pago (clave)';

comment on column STT_DOCPTR.DTP_PAGNIF is
'En caso de ser un pago indica nif sujeto pasivo';

comment on column STT_DOCPTR.DTP_PAGIDE is
'En caso de ser un pago indica identificador del pago (numero autoliquidación)';

comment on column STT_DOCPTR.DTP_PAGEST is
'En caso de ser un pago indica estado incorrecto';

comment on column STT_DOCPTR.DTP_PAGERR is
'En caso de ser un pago indica codigo error pasarela';

comment on column STT_DOCPTR.DTP_PAGERM is
'En caso de ser un pago indica mensaje error pasarela';

comment on column STT_DOCPTR.DTP_REGNUM is
'En caso de ser un registro indica numero registro';

comment on column STT_DOCPTR.DTP_REGFEC is
'En caso de ser un registro indica fecha registro';

comment on column STT_DOCPTR.DTP_REGPRE is
'En caso de ser un registro indica si es un preregistro';

alter table STT_DOCPTR
   add constraint STT_DOCPTR_PK primary key (DTP_CODIGO);

/*==============================================================*/
/* Index: STT_DOCPTR_UK                                         */
/*==============================================================*/
create unique index STT_DOCPTR_UK on STT_DOCPTR (
   DTP_CODPTR ASC,
   DTP_DOCIDE ASC,
   DTP_DOCINS ASC
);

/*==============================================================*/
/* Index: STT_DOCPTR_FICHERO_I                                  */
/*==============================================================*/
create index STT_DOCPTR_FICHERO_I on STT_DOCPTR (
   DTP_FICHERO ASC
);

/*==============================================================*/
/* Index: STT_DOCPTR_FORPDF_I                                   */
/*==============================================================*/
create index STT_DOCPTR_FORPDF_I on STT_DOCPTR (
   DTP_FORPDF ASC
);

/*==============================================================*/
/* Index: STT_DOCPTR_PAGJUS_I                                   */
/*==============================================================*/
create index STT_DOCPTR_PAGJUS_I on STT_DOCPTR (
   DTP_PAGJUS ASC
);

/*==============================================================*/
/* Table: STT_FICPTR                                            */
/*==============================================================*/
create table STT_FICPTR 
(
   FIC_CODIGO           NUMBER(19)           not null,
   FIC_CLAVE            VARCHAR2(50 CHAR)    not null,
   FIC_NOMFIC           VARCHAR2(500 CHAR)   not null,
   FIC_DATFIC           BLOB                 not null,
   FIC_BORRAR           NUMBER(1)            default 0 not null,
   FIC_CODTRP           NUMBER(19),
   FIC_FECHA            DATE,
   FIC_TAMANYO          NUMBER(11,0)
)
TABLESPACE SISTRAMIT
   LOB (FIC_DATFIC) STORE AS STT_FICPTR_DATFIC_LOB
	 (TABLESPACE SISTRAMIT_LOB
	 INDEX STT_FICPTR_DATFIC_LOB_I);

comment on table STT_FICPTR is
'Tabla de ficheros';

comment on column STT_FICPTR.FIC_CODIGO is
'Código interno';

comment on column STT_FICPTR.FIC_CLAVE is
'Clave acceso';

comment on column STT_FICPTR.FIC_NOMFIC is
'Nombre fichero con extensión';

comment on column STT_FICPTR.FIC_DATFIC is
'Contenido del fichero';

comment on column STT_FICPTR.FIC_BORRAR is
'Indica si el fichero debe ser borrado';

comment on column STT_FICPTR.FIC_CODTRP is
'Codigo tramite (enlace directo para optimizar purga)';

comment on column STT_FICPTR.FIC_FECHA is
'Fecha creacion fichero';

comment on column STT_FICPTR.FIC_TAMANYO is
'Tamaño del fichero';

alter table STT_FICPTR
   add constraint STT_FICPTR_PK primary key (FIC_CODIGO);

/*==============================================================*/
/* Index: STT_FICCPTR_BORRAR_I                                  */
/*==============================================================*/
create index STT_FICCPTR_BORRAR_I on STT_FICPTR (
   FIC_BORRAR ASC
);

/*==============================================================*/
/* Table: STT_FIRDPT                                            */
/*==============================================================*/
create table STT_FIRDPT 
(
   FDP_CODIGO           NUMBER(19)           not null,
   FDP_CODDPT           NUMBER(19)           not null,
   FDP_CODFIC           NUMBER(19)           not null,
   FDP_FTENIF           VARCHAR2(10 CHAR)    not null,
   FDP_FTENOM           VARCHAR2(500 CHAR)   not null,
   FDP_FECFIR           DATE                 not null,
   FDP_TIPFIR           VARCHAR2(4 CHAR)     not null,
   FDP_FIRMA            NUMBER(19)           not null,
   FDP_FIRMAC           VARCHAR2(50 CHAR)    not null
);

comment on table STT_FIRDPT is
'Firmas de un documento de un paso de tramitación';

comment on column STT_FIRDPT.FDP_CODIGO is
'Código firma';

comment on column STT_FIRDPT.FDP_CODDPT is
'Código documento persistente';

comment on column STT_FIRDPT.FDP_CODFIC is
'Código fichero al que pertenece la firma';

comment on column STT_FIRDPT.FDP_FTENIF is
'Nif firmante';

comment on column STT_FIRDPT.FDP_FTENOM is
'Nombre firmante';

comment on column STT_FIRDPT.FDP_FECFIR is
'Fecha firma';

comment on column STT_FIRDPT.FDP_TIPFIR is
'Tipo de firma
- TF01 - CSV. 	
- TF02 - XAdES internally detached signature.	
- TF03 - XAdES enveloped signature. 	
- TF04 - CAdES detached/explicit signature.	
- TF05 - CAdES attached/implicit signature.	
- TF06 - PAdES.
';

comment on column STT_FIRDPT.FDP_FIRMA is
'Fichero de firma (codigo)';

comment on column STT_FIRDPT.FDP_FIRMAC is
'Fichero de firma (clave)';

alter table STT_FIRDPT
   add constraint STT_FIRDPT_PK primary key (FDP_CODIGO);

/*==============================================================*/
/* Index: STT_FIRDPT_UK                                         */
/*==============================================================*/
create unique index STT_FIRDPT_UK on STT_FIRDPT (
   FDP_CODDPT ASC,
   FDP_FTENIF ASC,
   FDP_CODFIC ASC
);

/*==============================================================*/
/* Index: STT_FIRDPT_FIRMA_I                                    */
/*==============================================================*/
create index STT_FIRDPT_FIRMA_I on STT_FIRDPT (
   FDP_FIRMA ASC
);

/*==============================================================*/
/* Index: STT_FIRDPT_CODFIC_I                                   */
/*==============================================================*/
create index STT_FIRDPT_CODFIC_I on STT_FIRDPT (
   FDP_CODFIC ASC
);

/*==============================================================*/
/* Table: STT_FORMUL                                            */
/*==============================================================*/
create table STT_FORMUL 
(
   SFR_CODIGO           NUMBER(19)           not null,
   SFR_TICKET           VARCHAR2(200 CHAR)   not null,
   SFR_FECINI           DATE                 not null,
   SFR_IDESTR           VARCHAR2(50 CHAR)    not null,
   SFR_IDTRAM           VARCHAR2(20 CHAR),
   SFR_VERSIO           NUMBER(2),
   SFR_RELESE           NUMBER(8),
   SFR_IDPASO           VARCHAR2(20 CHAR)    not null,
   SFR_IDFORM           VARCHAR2(20 CHAR)    not null,
   SFR_DATFOR           BLOB,
   SFR_INFAUT           CLOB,
   SFR_PARFOR           CLOB,
   SFR_INFPRO           CLOB,
   SFR_FECFIN           DATE,
   SFR_CANCEL           NUMBER(1)            default 0,
   SFR_XML              BLOB,
   SFR_PDF              BLOB,
   SFR_TCKUSA           NUMBER(1)            default 0
)
TABLESPACE SISTRAMIT
   LOB (SFR_DATFOR) STORE AS STT_FORMUL_DATFOR_LOB
	 (TABLESPACE SISTRAMIT_LOB
	 INDEX STT_FORMUL_DATFOR_LOB_I)
   LOB (SFR_PARFOR) STORE AS STT_FORMUL_PARFOR_LOB
	 (TABLESPACE SISTRAMIT_LOB
	 INDEX STT_FORMUL_PARFOR_LOB_I)
   LOB (SFR_INFPRO) STORE AS STT_FORMUL_INFPRO_LOB
	 (TABLESPACE SISTRAMIT_LOB
	 INDEX STT_FORMUL_INFPRO_LOB_I)
   LOB (SFR_INFAUT) STORE AS STT_FORMUL_INFAUT_LOB
	 (TABLESPACE SISTRAMIT_LOB
	 INDEX STT_FORMUL_INFAUT_LOB_I)
   LOB (SFR_XML) STORE AS STT_FORMUL_XML_LOB
	 (TABLESPACE SISTRAMIT_LOB
	 INDEX STT_FORMUL_XML_LOB_I)
   LOB (SFR_PDF) STORE AS STT_FORMUL_PDF_LOB
	 (TABLESPACE SISTRAMIT_LOB
	 INDEX STT_FORMUL_PDF_LOB_I);

comment on table STT_FORMUL is
'Formularios retornados desde los gestores de formulario (interno / externo)';

comment on column STT_FORMUL.SFR_CODIGO is
'Codigo interno';

comment on column STT_FORMUL.SFR_TICKET is
'Ticket de acceso';

comment on column STT_FORMUL.SFR_FECINI is
'Fecha de apertura del formulario';

comment on column STT_FORMUL.SFR_IDESTR is
'Identificador sesión tramitación';

comment on column STT_FORMUL.SFR_IDTRAM is
'Identificador tramite';

comment on column STT_FORMUL.SFR_VERSIO is
'Version tramite';

comment on column STT_FORMUL.SFR_RELESE is
'Release tramite';

comment on column STT_FORMUL.SFR_IDPASO is
'Identificador paso';

comment on column STT_FORMUL.SFR_IDFORM is
'Identificador formulario';

comment on column STT_FORMUL.SFR_DATFOR is
'Datos actuales';

comment on column STT_FORMUL.SFR_INFAUT is
'Informacion de autenticacion (serializado)';

comment on column STT_FORMUL.SFR_PARFOR is
'Parametros formulario (serializado)';

comment on column STT_FORMUL.SFR_INFPRO is
'Información procedimiento (serializado)';

comment on column STT_FORMUL.SFR_FECFIN is
'Fecha de finalización formulario';

comment on column STT_FORMUL.SFR_CANCEL is
'Indica si el formulario se ha cancelado';

comment on column STT_FORMUL.SFR_XML is
'Xml de datos';

comment on column STT_FORMUL.SFR_PDF is
'Pdf de visualización';

comment on column STT_FORMUL.SFR_TCKUSA is
'Indica si el ticket se ha usado para retornar';

alter table STT_FORMUL
   add constraint STT_FORMUL_PK primary key (SFR_CODIGO);

/*==============================================================*/
/* Index: STT_FORMUL_UK                                         */
/*==============================================================*/
create unique index STT_FORMUL_UK on STT_FORMUL (
   SFR_TICKET ASC
);

/*==============================================================*/
/* Index: STT_FORMUL_FECINI_I                                   */
/*==============================================================*/
create index STT_FORMUL_FECINI_I on STT_FORMUL (
   SFR_FECINI ASC
);

/*==============================================================*/
/* Index: STT_FORMUL_FECFIN_I                                   */
/*==============================================================*/
create index STT_FORMUL_FECFIN_I on STT_FORMUL (
   SFR_FECFIN ASC
);

/*==============================================================*/
/* Table: STT_INVALI                                            */
/*==============================================================*/
create table STT_INVALI 
(
   INV_CODIGO           NUMBER(19)           not null,
   INV_TIPO             VARCHAR2(1 CHAR)     not null,
   INV_FECHA            DATE                 not null,
   INV_IDENTI           VARCHAR2(400 CHAR)
);

comment on table STT_INVALI is
'Invalidaciones de definicion de tramites y datos de dominios';

comment on column STT_INVALI.INV_CODIGO is
'Código secuencial interno';

comment on column STT_INVALI.INV_TIPO is
'Tipo invalidacion: definicion tramite (T) / datos dominio (D) / entidad (E) / configuracion (C)';

comment on column STT_INVALI.INV_FECHA is
'Fecha invalidación';

comment on column STT_INVALI.INV_IDENTI is
'Identificador elemento dependiendo del elemento: para tramite: idtramite-version / para dominio: iddominio / para entidad: identidad';

alter table STT_INVALI
   add constraint STT_INVALI_PK primary key (INV_CODIGO);

/*==============================================================*/
/* Table: STT_LOGINT                                            */
/*==============================================================*/
create table STT_LOGINT 
(
   LOG_CODIGO           NUMBER(19)           not null,
   LOG_EVETIP           VARCHAR2(20 CHAR)    not null,
   LOG_EVEFEC           TIMESTAMP            not null,
   LOG_EVEDES           VARCHAR2(1000 CHAR)  not null,
   LOG_EVERES           VARCHAR2(50 CHAR),
   LOG_EVEDET           VARCHAR2(4000 CHAR),
   LOG_CODSES           NUMBER(19),
   LOG_ERRCOD           VARCHAR2(500 CHAR),
   LOG_ERRDET           CLOB
)
TABLESPACE SISTRAMIT
   LOB (LOG_ERRDET) STORE AS STT_LOGINT_ERRDET_LOB
	 (TABLESPACE SISTRAMIT_LOB
	 INDEX STT_LOGINT_ERRDET_LOB_I);

comment on table STT_LOGINT is
'Log interno de auditoria y errores';

comment on column STT_LOGINT.LOG_CODIGO is
'Código evento';

comment on column STT_LOGINT.LOG_EVETIP is
'Tipo evento';

comment on column STT_LOGINT.LOG_EVEFEC is
'Fecha';

comment on column STT_LOGINT.LOG_EVEDES is
'Descripción evento';

comment on column STT_LOGINT.LOG_EVERES is
'Resultado evento (depende del evento)';

comment on column STT_LOGINT.LOG_EVEDET is
'Detalle evento (depende del evento). Permite establecer info adicional mediante una lista de campos de información particulares del evento con formato: propiedad1=valor1#@#propiedad2=valor2';

comment on column STT_LOGINT.LOG_CODSES is
'Codigo sesión tramitación';

comment on column STT_LOGINT.LOG_ERRCOD is
'Para evento de tipo error indica el código de error (excepción de negocio)';

comment on column STT_LOGINT.LOG_ERRDET is
'Para evento de tipo error permite establecer la traza del error';

alter table STT_LOGINT
   add constraint STT_LOGINT_PK primary key (LOG_CODIGO);

/*==============================================================*/
/* Index: STT_LOGINT_ERRCOD_I                                   */
/*==============================================================*/
create index STT_LOGINT_ERRCOD_I on STT_LOGINT (
   LOG_ERRCOD ASC
);

/*==============================================================*/
/* Index: STT_LOGINT_EVEFEC_CODIGO_I                            */
/*==============================================================*/
create index STT_LOGINT_EVEFEC_CODIGO_I on STT_LOGINT (
   LOG_EVEFEC ASC,
   LOG_CODIGO ASC
);

/*==============================================================*/
/* Index: STT_LOGINT_EVFC_EVTI_I                                */
/*==============================================================*/
create index STT_LOGINT_EVFC_EVTI_I on STT_LOGINT (
   LOG_EVEFEC ASC,
   LOG_EVETIP ASC
);

/*==============================================================*/
/* Table: STT_PAGEXT                                            */
/*==============================================================*/
create table STT_PAGEXT 
(
   PAE_CODIGO           NUMBER(19)           not null,
   PAE_TICKET           VARCHAR2(200 CHAR)   not null,
   PAE_FECINI           DATE                 not null,
   PAE_IDESTR           VARCHAR2(50 CHAR)    not null,
   PAE_IDPASO           VARCHAR2(20 CHAR)    not null,
   PAE_IDPAGO           VARCHAR2(20 CHAR)    not null,
   PAE_NIVAUT           VARCHAR2(1 CHAR)     not null,
   PAE_INFAUT           BLOB,
   PAE_FECFIN           DATE,
   PAE_TCKUSA           NUMBER(1)            default 0
)
TABLESPACE SISTRAMIT
   LOB (PAE_INFAUT) STORE AS STT_PAGEXT_INFAUT_LOB
	 (TABLESPACE SISTRAMIT_LOB
	 INDEX STT_PAGEXT_INFAUT_LOB_I);

comment on table STT_PAGEXT is
'Pago externo';

comment on column STT_PAGEXT.PAE_CODIGO is
'Codigo interno';

comment on column STT_PAGEXT.PAE_TICKET is
'Ticket de acceso';

comment on column STT_PAGEXT.PAE_FECINI is
'Fecha de apertura del formulario';

comment on column STT_PAGEXT.PAE_IDESTR is
'Identificador sesión tramitación';

comment on column STT_PAGEXT.PAE_IDPASO is
'Identificador paso';

comment on column STT_PAGEXT.PAE_IDPAGO is
'Identificador pago';

comment on column STT_PAGEXT.PAE_NIVAUT is
'Nivel autenticación para el retorno';

comment on column STT_PAGEXT.PAE_INFAUT is
'Informacion de autenticacion serializada para el retorno';

comment on column STT_PAGEXT.PAE_FECFIN is
'Indica fecha fin sesion pago';

comment on column STT_PAGEXT.PAE_TCKUSA is
'Indica si el ticket se ha usado para retornar';

alter table STT_PAGEXT
   add constraint STT_PAGEXT_PK primary key (PAE_CODIGO);

/*==============================================================*/
/* Index: STT_PAGEXT_UK                                         */
/*==============================================================*/
create unique index STT_PAGEXT_UK on STT_PAGEXT (
   PAE_TICKET ASC
);

/*==============================================================*/
/* Index: STT_PAGEXT_FECINI_I                                   */
/*==============================================================*/
create index STT_PAGEXT_FECINI_I on STT_PAGEXT (
   PAE_FECINI ASC
);

/*==============================================================*/
/* Index: STT_PAGEXT_FECFIN_I                                   */
/*==============================================================*/
create index STT_PAGEXT_FECFIN_I on STT_PAGEXT (
   PAE_FECFIN ASC
);

/*==============================================================*/
/* Table: STT_PASTRP                                            */
/*==============================================================*/
create table STT_PASTRP 
(
   PTR_CODIGO           NUMBER(19)           not null,
   PTR_CODTRP           NUMBER(19)           not null,
   PTR_IDEPTR           VARCHAR2(20 CHAR)    not null,
   PTR_TIPO             VARCHAR2(2 CHAR)     not null,
   PTR_ESTADO           VARCHAR2(1 CHAR)     not null,
   PTR_ORDEN            NUMBER(2)            not null
);

comment on table STT_PASTRP is
'Paso trámite persistente.';

comment on column STT_PASTRP.PTR_CODIGO is
'Código paso';

comment on column STT_PASTRP.PTR_CODTRP is
'Código trámite persistente';

comment on column STT_PASTRP.PTR_IDEPTR is
'Identificador paso (para flujo normalizado será fijo, para flujo personalizado será particular)';

comment on column STT_PASTRP.PTR_TIPO is
'Tipo paso:
- Paso inicial de Debe saber: "ds"
- Paso de rellenar formularios: "rf"
- Paso de anexar documentos: "ad"
- Paso de captura de datos a través de un formulario: "cd"
- Paso informativo que muestra datos a partir de una plantilla de visualización: "in"
- Paso de pago de tasas: "pt"
- Paso de registro del trámite: "rt"
- Paso para guardar el justificante: "gj"
';

comment on column STT_PASTRP.PTR_ESTADO is
'Estado paso:
    NO_INICIALIZADO ("n") :  No inicializado. Todavía no se han inicializado los datos del paso.
    PENDIENTE ("p"): Paso pendiente.
    COMPLETADO ("c"): Paso completado.
    REVISAR ("r"): Paso pendiente revisar porque se ha modificado un paso anterior. Se deben revisar los datos del paso.';

comment on column STT_PASTRP.PTR_ORDEN is
'Número orden';

alter table STT_PASTRP
   add constraint STT_PASTRP_PK primary key (PTR_CODIGO);

/*==============================================================*/
/* Index: STT_PASTRP_UK                                         */
/*==============================================================*/
create unique index STT_PASTRP_UK on STT_PASTRP (
   PTR_CODTRP ASC,
   PTR_IDEPTR ASC
);

/*==============================================================*/
/* Table: STT_PROCES                                            */
/*==============================================================*/
create table STT_PROCES 
(
   PROC_IDENT           VARCHAR2(20 CHAR)    not null,
   PROC_INSTAN          VARCHAR2(50 CHAR),
   PROC_FECHA           DATE
);

comment on table STT_PROCES is
'Control ejecución procesos background.
Para que una sola instancia se autoconfigure como maestro.';

comment on column STT_PROCES.PROC_IDENT is
'Identificador fijo';

comment on column STT_PROCES.PROC_INSTAN is
'Id instancia';

comment on column STT_PROCES.PROC_FECHA is
'Fecha ultima verificación';

alter table STT_PROCES
   add constraint STT_PROCES_PK primary key (PROC_IDENT);

/*==============================================================*/
/* Table: STT_SESION                                            */
/*==============================================================*/
create table STT_SESION 
(
   SES_CODIGO           NUMBER(19)           not null,
   SES_IDESTR           VARCHAR2(50 CHAR)    not null,
   SES_FECHA            DATE                 not null
);

comment on table STT_SESION is
'Sesion de tramitacion';

comment on column STT_SESION.SES_CODIGO is
'Codigo secuencial';

comment on column STT_SESION.SES_IDESTR is
'Id sesion tramitacion';

comment on column STT_SESION.SES_FECHA is
'Fecha creacion';

alter table STT_SESION
   add constraint STT_SESION_PK primary key (SES_CODIGO);

/*==============================================================*/
/* Index: STT_SESION_UK                                         */
/*==============================================================*/
create unique index STT_SESION_UK on STT_SESION (
   SES_IDESTR ASC
);

/*==============================================================*/
/* Table: STT_TRAPER                                            */
/*==============================================================*/
create table STT_TRAPER 
(
   TRP_CODIGO           NUMBER(19)           not null,
   TRP_CODSTR           NUMBER(19)           not null,
   TRP_IDETRA           VARCHAR2(20 CHAR)    not null,
   TRP_VERTRA           NUMBER(2)            not null,
   TRP_DESTRA           VARCHAR2(1000 CHAR)  not null,
   TRP_IDETCP           VARCHAR2(20 CHAR)    not null,
   TRP_IDEPCP           VARCHAR2(20 CHAR)    not null,
   TRP_PROSIA           VARCHAR2(20 CHAR)    not null,
   TRP_ESTADO           VARCHAR2(1 CHAR)     not null,
   TRP_NIVAUT           VARCHAR2(1 CHAR)     not null,
   TRP_METAUT           VARCHAR2(50 CHAR),
   TRP_NIFINI           VARCHAR2(10 CHAR),
   TRP_NOMINI           VARCHAR2(1000 CHAR),
   TRP_APE1INI          VARCHAR2(255 CHAR),
   TRP_APE2INI          VARCHAR2(255 CHAR),
   TRP_TSFLUJO          TIMESTAMP,
   TRP_IDIOMA           VARCHAR2(2 CHAR)     not null,
   TRP_URLINI           VARCHAR2(4000 CHAR),
   TRP_PARINI           VARCHAR2(4000 CHAR),
   TRP_PERSIS           NUMBER(1)            default 0 not null,
   TRP_PLZDIN           NUMBER(1)            default 0 not null,
   TRP_FECINI           DATE                 not null,
   TRP_FECACC           DATE,
   TRP_FECCAD           DATE,
   TRP_FECFIN           DATE,
   TRP_BORRAD           NUMBER(1)            default 0,
   TRP_NIFFIN           VARCHAR2(10 CHAR),
   TRP_NOMFIN           VARCHAR2(1000 CHAR),
   TRP_PURGA            NUMBER(1)            default 0 not null,
   TRP_FCPURG           DATE,
   TRP_PURCHK           NUMBER(1)            default 0 not null,
   TRP_PURPAG           NUMBER(1)            default 0 not null
);

comment on table STT_TRAPER is
'Trámites en persistencia';

comment on column STT_TRAPER.TRP_CODIGO is
'Código trámite persistente';

comment on column STT_TRAPER.TRP_CODSTR is
'Código sesión trámitación';

comment on column STT_TRAPER.TRP_IDETRA is
'Identificador trámite';

comment on column STT_TRAPER.TRP_VERTRA is
'Versión  trámite';

comment on column STT_TRAPER.TRP_DESTRA is
'Descripción trámite';

comment on column STT_TRAPER.TRP_IDETCP is
'Codigo trámite asociado del Catalogo de Procedimientos';

comment on column STT_TRAPER.TRP_IDEPCP is
'Codigo procedimiento asociado del Catalogo de Procedimientos';

comment on column STT_TRAPER.TRP_PROSIA is
'Código SIA procedimiento';

comment on column STT_TRAPER.TRP_ESTADO is
'Estado trámite: 
    RELLENANDO("r"): Trámite en fase de rellenado
    PENDIENTE_ENVIAR_BANDEJAFIRMA("e"): Indica que el trámite debe enviarse a la bandeja de firma
    ENVIADO_BANDEJAFIRMA("b"): Indica que el trámite no puede modificarse ya que se ha enviado a bandeja de firma
    FINALIZADO("f"): Indica que el trámite se ha finalizado

Es calculado en función de los pasos.';

comment on column STT_TRAPER.TRP_NIVAUT is
'Nivel autenticación:  Autenticado (S) / Anónimo (N)';

comment on column STT_TRAPER.TRP_METAUT is
'Método autenticación inicio trámite:
ANONIMO
CLAVE_CERTIFICADO
CLAVE_PIN
CLAVE_PERMANENTE';

comment on column STT_TRAPER.TRP_NIFINI is
'Nif iniciador trámite (en caso de autenticado)';

comment on column STT_TRAPER.TRP_NOMINI is
'Nombre iniciador trámite (en caso de autenticado)';

comment on column STT_TRAPER.TRP_APE1INI is
'Apellido 1 iniciador trámite (en caso de autenticado)';

comment on column STT_TRAPER.TRP_APE2INI is
'Apellido 2  iniciador trámite (en caso de autenticado)';

comment on column STT_TRAPER.TRP_TSFLUJO is
'Timestamp del flujo, Para controlar que sólo haya una sesión activa sobre el flujo.';

comment on column STT_TRAPER.TRP_IDIOMA is
'Idioma tramitación';

comment on column STT_TRAPER.TRP_URLINI is
'Url de inicio del tramite';

comment on column STT_TRAPER.TRP_PARINI is
'Parámetros iniciales trámite';

comment on column STT_TRAPER.TRP_PERSIS is
'Indica si el tramite es persistente';

comment on column STT_TRAPER.TRP_PLZDIN is
'Indica si el plazo se ha establecido de forma dinámica. En este caso se obviarán los plazos de GUC.';

comment on column STT_TRAPER.TRP_FECINI is
'Fecha inicio';

comment on column STT_TRAPER.TRP_FECACC is
'Fecha último acceso';

comment on column STT_TRAPER.TRP_FECCAD is
'Fecha caducidad trámite (minimo persistencia y fin plazo)';

comment on column STT_TRAPER.TRP_FECFIN is
'Fecha finalización trámite (envío o cancelación)';

comment on column STT_TRAPER.TRP_BORRAD is
'Indica si el trámite se ha cancelado antes de enviar.';

comment on column STT_TRAPER.TRP_NIFFIN is
'Nif que realiza la presentación del trámite';

comment on column STT_TRAPER.TRP_NOMFIN is
'Nombre y apellidos del que realiza la presentación del trámite';

comment on column STT_TRAPER.TRP_PURGA is
'Indica si el tramite se ha prugado';

comment on column STT_TRAPER.TRP_FCPURG is
'Fecha purgado';

comment on column STT_TRAPER.TRP_PURCHK is
'Marcado para purgar';

comment on column STT_TRAPER.TRP_PURPAG is
'Indica si no se ha podido purgar por tener pagos realizados';

alter table STT_TRAPER
   add constraint STT_TRAPER_PK primary key (TRP_CODIGO);

/*==============================================================*/
/* Index: STT_TRAPER_UK                                         */
/*==============================================================*/
create unique index STT_TRAPER_UK on STT_TRAPER (
   TRP_CODSTR ASC
);

/*==============================================================*/
/* Index: STT_TRAPER_PURCHK_I                                   */
/*==============================================================*/
create index STT_TRAPER_PURCHK_I on STT_TRAPER (
   TRP_PURCHK ASC
);

/*==============================================================*/
/* Index: STT_TRAPER_PERSIS_NIFINI_I                            */
/*==============================================================*/
create index STT_TRAPER_PERSIS_NIFINI_I on STT_TRAPER (
   TRP_PERSIS ASC,
   TRP_NIFINI ASC
);

/*==============================================================*/
/* Index: STT_TRAPER_IDTR_PERS_PRO_I                            */
/*==============================================================*/
create index STT_TRAPER_IDTR_PERS_PRO_I on STT_TRAPER (
   TRP_IDETRA ASC,
   TRP_PERSIS ASC,
   TRP_IDETCP ASC
);

/*==============================================================*/
/* Index: STT_TRAPER_EST_PURG_PURCHK_I                          */
/*==============================================================*/
create index STT_TRAPER_EST_PURG_PURCHK_I on STT_TRAPER (
   TRP_ESTADO ASC,
   TRP_PURGA ASC,
   TRP_PURCHK ASC
);

/*==============================================================*/
/* Index: STT_TRAPER_IDTR_VERTR_FCIN_I                          */
/*==============================================================*/
create index STT_TRAPER_IDTR_VERTR_FCIN_I on STT_TRAPER (
   TRP_IDETRA ASC,
   TRP_VERTRA ASC,
   TRP_FECINI ASC
);

alter table STT_DOCPTR
   add constraint STT_DOCPTR_FICPTR_FK foreign key (DTP_FICHERO)
      references STT_FICPTR (FIC_CODIGO);

alter table STT_DOCPTR
   add constraint STT_DOCPTR_FICPTR_FK2 foreign key (DTP_FORPDF)
      references STT_FICPTR (FIC_CODIGO);

alter table STT_DOCPTR
   add constraint STT_DOCPTR_FICPTR_FK3 foreign key (DTP_PAGJUS)
      references STT_FICPTR (FIC_CODIGO);

alter table STT_DOCPTR
   add constraint STT_PASTRP_DOCPTR_FK foreign key (DTP_CODPTR)
      references STT_PASTRP (PTR_CODIGO);

alter table STT_FIRDPT
   add constraint STT_FIRDPT_DOCPTR_FK foreign key (FDP_CODDPT)
      references STT_DOCPTR (DTP_CODIGO);

alter table STT_FIRDPT
   add constraint STT_FIRDPT_FICPTR_FK foreign key (FDP_FIRMA)
      references STT_FICPTR (FIC_CODIGO);

alter table STT_FIRDPT
   add constraint STT_FIRDPT_FICPTR_FK2 foreign key (FDP_CODFIC)
      references STT_FICPTR (FIC_CODIGO);

alter table STT_LOGINT
   add constraint STT_LOGINT_SESION_FK foreign key (LOG_CODSES)
      references STT_SESION (SES_CODIGO);

alter table STT_PASTRP
   add constraint STT_PASTRP_TRAPER_FK foreign key (PTR_CODTRP)
      references STT_TRAPER (TRP_CODIGO);

alter table STT_TRAPER
   add constraint STT_TRAPER_SESION_FK foreign key (TRP_CODSTR)
      references STT_SESION (SES_CODIGO);

