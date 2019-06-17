create sequence STG_ACCPER_SEQ;

create sequence STG_ANETRA_SEQ;

create sequence STG_AREA_SEQ;

create sequence STG_AVIENT_SEQ;

create sequence STG_CAMFUE_SEQ;

create sequence STG_CNFGLO_SEQ;

create sequence STG_DOMINI_SEQ;

create sequence STG_ENTIDA_SEQ;

create sequence STG_FICHER_SEQ;

create sequence STG_FILFUE_SEQ;

create sequence STG_FMTPLI_SEQ;

create sequence STG_FORELE_SEQ;

create sequence STG_FORFMT_SEQ;

create sequence STG_FORLI_SEQ;

create sequence STG_FORMUL_SEQ;

create sequence STG_FORPAG_SEQ;

create sequence STG_FORPLI_SEQ;

create sequence STG_FORPLT_SEQ;

create sequence STG_FORSOP_SEQ;

create sequence STG_FORTRA_SEQ;

create sequence STG_FUEDAT_SEQ;

create sequence STG_GESFOR_SEQ;

create sequence STG_HISVER_SEQ;

create sequence STG_LFVCIN_SEQ;

create sequence STG_LITSCR_SEQ;

create sequence STG_PAGTRA_SEQ;

create sequence STG_PASOTR_SEQ;

create sequence STG_PLUGIN_SEQ;

create sequence STG_PRDCIN_SEQ;

create sequence STG_ROLARE_SEQ;

create sequence STG_SCRIPT_SEQ;

create sequence STG_TIPPTR_SEQ;

create sequence STG_TRADUC_SEQ;

create sequence STG_TRAIDI_SEQ;

create sequence STG_TRAMIT_SEQ;

create sequence STG_VALCFU_SEQ;

create sequence STG_VALORA_SEQ;

create sequence STG_VERTRA_SEQ;

/*==============================================================*/
/* Table: STG_ACCPER                                            */
/*==============================================================*/
create table STG_ACCPER
(
   ACP_CODIGO           NUMBER(18)           not null,
   ACP_CODFOR           NUMBER(18)           not null,
   ACP_ACCION           VARCHAR2(20 CHAR)    not null,
   ACP_DESCRI           NUMBER(18)           not null,
   ACP_VALIDA           NUMBER(1)            default 0 not null
);

comment on table STG_ACCPER is
'Acciones personalizadas del formulario';

comment on column STG_ACCPER.ACP_CODIGO is
'Codigo de la acción';

comment on column STG_ACCPER.ACP_CODFOR is
'Formulario al que pertenece la acción';

comment on column STG_ACCPER.ACP_ACCION is
'Acción a realizar. Única en el formulario';

comment on column STG_ACCPER.ACP_DESCRI is
'Título de la acción';

comment on column STG_ACCPER.ACP_VALIDA is
'La acción hace que se valide el formulario';

alter table STG_ACCPER
   add constraint STG_ACCPER_PK primary key (ACP_CODIGO);

/*==============================================================*/
/* Index: STG_ACCPER_CODFOR_ACCION_UK                           */
/*==============================================================*/
create unique index STG_ACCPER_CODFOR_ACCION_UK on STG_ACCPER (
   ACP_CODFOR ASC,
   ACP_ACCION ASC
);

/*==============================================================*/
/* Table: STG_ANETRA                                            */
/*==============================================================*/
create table STG_ANETRA
(
   ANE_CODIGO           NUMBER(18)           not null,
   ANE_CODPTR           NUMBER(18)           not null,
   ANE_IDEDOC           VARCHAR2(20 CHAR)    not null,
   ANE_DESCRIP          NUMBER(18)           not null,
   ANE_ORDEN            NUMBER(2)            not null,
   ANE_OBLIGA           VARCHAR2(1 CHAR)     not null,
   ANE_SCROBL           NUMBER(18),
   ANE_AYUTXT           NUMBER(18),
   ANE_AYUFIC           NUMBER(18),
   ANE_AYUULR           VARCHAR2(250 CHAR),
   ANE_TIPPRE           VARCHAR2(1 CHAR)     not null,
   ANE_NUMINS           NUMBER(2)            not null,
   ANE_EXTPER           VARCHAR2(1000 CHAR)  not null,
   ANE_TAMMAX           NUMBER(4)            not null,
   ANE_TAMUNI           VARCHAR2(2 CHAR)     default 'KB' not null,
   ANE_CNVPDF           NUMBER(1)            default 0 not null,
   ANE_FIRMAR           NUMBER(1)            default 0 not null,
   ANE_SCRFIR           NUMBER(18),
   ANE_FIRMAD           NUMBER(1)            default 0 not null,
   ANE_SCRVAL           NUMBER(18)
);

comment on table STG_ANETRA is
'Anexo trámite';

comment on column STG_ANETRA.ANE_CODIGO is
'Código anexo';

comment on column STG_ANETRA.ANE_CODPTR is
'Código paso tramitación';

comment on column STG_ANETRA.ANE_IDEDOC is
'Identificador documento dentro de la versión';

comment on column STG_ANETRA.ANE_DESCRIP is
'Descripción anexo';

comment on column STG_ANETRA.ANE_ORDEN is
'Orden';

comment on column STG_ANETRA.ANE_OBLIGA is
'Obligatorio:
 - Si (S)
 - Opcional (N)
 - Dependiente (D)';

comment on column STG_ANETRA.ANE_SCROBL is
'En caso de ser dependiente establece obligatoriedad';

comment on column STG_ANETRA.ANE_AYUTXT is
'Texto de ayuda online para realizar el anexado';

comment on column STG_ANETRA.ANE_AYUFIC is
'Fichero binario con la plantilla del documento';

comment on column STG_ANETRA.ANE_AYUULR is
'Plantilla es una URL';

comment on column STG_ANETRA.ANE_TIPPRE is
'Tipo presentación: E (electrónica) / P (Presencial)';

comment on column STG_ANETRA.ANE_NUMINS is
'Número instancia (por defecto 1, para multiinstancia > 1)';

comment on column STG_ANETRA.ANE_EXTPER is
'Lista extensiones permitidas separadas por coma';

comment on column STG_ANETRA.ANE_TAMMAX is
'Tamaño máximo (segun unidad tamaño)';

comment on column STG_ANETRA.ANE_TAMUNI is
'Unidad de tamaño del anexo (KB o MB)';

comment on column STG_ANETRA.ANE_CNVPDF is
'Indica si hay que convertir a PDF el anexo';

comment on column STG_ANETRA.ANE_FIRMAR is
'Indica si se debe firmar digitalmente ';

comment on column STG_ANETRA.ANE_SCRFIR is
'Permite indicar quién debe firmar el anexo (permite indicar varios firmantes).
Si se habilita el pase a bandeja de firma se podrá especificar si el anexo debe ser anexado por uno de los firmantes.';

comment on column STG_ANETRA.ANE_FIRMAD is
'Indica si se debe anexar firmado';

comment on column STG_ANETRA.ANE_SCRVAL is
'Permite establecer una validación sobre el documento anexado. En este script estará disponible un plugin que permita acceder a datos de formularios PDF.';

alter table STG_ANETRA
   add constraint STG_ANETRA_PK primary key (ANE_CODIGO);

/*==============================================================*/
/* Index: STG_ANETRA_CODPTR_IDEDOC_UK                           */
/*==============================================================*/
create unique index STG_ANETRA_CODPTR_IDEDOC_UK on STG_ANETRA (
   ANE_CODPTR ASC,
   ANE_IDEDOC ASC
);

/*==============================================================*/
/* Table: STG_AREA                                              */
/*==============================================================*/
create table STG_AREA
(
   ARE_CODIGO           NUMBER(18)           not null,
   ARE_CODENT           NUMBER(18)           not null,
   ARE_IDENTI           VARCHAR2(20 CHAR)    not null,
   ARE_DESCR            VARCHAR2(255 CHAR)   not null
);

comment on table STG_AREA is
'Areas funcionales';

comment on column STG_AREA.ARE_CODIGO is
'Código';

comment on column STG_AREA.ARE_CODENT is
'Código entidad';

comment on column STG_AREA.ARE_IDENTI is
'Identificador';

comment on column STG_AREA.ARE_DESCR is
'Descripción';

alter table STG_AREA
   add constraint STG_AREA_PK primary key (ARE_CODIGO);

/*==============================================================*/
/* Index: STG_AREA_IDENTI_UK                                    */
/*==============================================================*/
create unique index STG_AREA_IDENTI_UK on STG_AREA (
   ARE_IDENTI ASC
);

/*==============================================================*/
/* Table: STG_AREDOM                                            */
/*==============================================================*/
create table STG_AREDOM
(
   DMA_CODARE           NUMBER(18)           not null,
   DMA_CODDOM           NUMBER(18)           not null
);

comment on table STG_AREDOM is
'Dominios area';

comment on column STG_AREDOM.DMA_CODARE is
'Código area';

comment on column STG_AREDOM.DMA_CODDOM is
'Código dominio';

alter table STG_AREDOM
   add constraint STG_AREDOM_PK primary key (DMA_CODARE, DMA_CODDOM);

/*==============================================================*/
/* Table: STG_AVIENT                                            */
/*==============================================================*/
create table STG_AVIENT
(
   AVI_CODIGO           NUMBER(18)           not null,
   AVI_CODENT           NUMBER(18)           not null,
   AVI_TRAMEN           NUMBER(18)           not null,
   AVI_TIPO             VARCHAR2(3 CHAR)     not null,
   AVI_BLOQ             NUMBER(1)            default 0 not null,
   AVI_FCINI            DATE,
   AVI_FCFIN            DATE,
   AVI_LSTTRA           VARCHAR2(1000 CHAR)
);

comment on table STG_AVIENT is
'Avisos entidad (para el asistente de tramitación)';

comment on column STG_AVIENT.AVI_CODIGO is
'Código';

comment on column STG_AVIENT.AVI_CODENT is
'Código entidad';

comment on column STG_AVIENT.AVI_TRAMEN is
'Mensaje aviso multiidioma';

comment on column STG_AVIENT.AVI_TIPO is
'Tipo:
  - TOD: Todos
  - AUT: Autenticados
  - NAU: No autenticados
  - PAG: Con pago
  - REG: Con registro
  - FIR: Con firma
  - LST: Lista trámites ';

comment on column STG_AVIENT.AVI_BLOQ is
'Si se bloquea acceso al trámite';

comment on column STG_AVIENT.AVI_FCINI is
'Fecha inicio';

comment on column STG_AVIENT.AVI_FCFIN is
'Fecha fin';

comment on column STG_AVIENT.AVI_LSTTRA is
'Lista serializada con códigos de trámites ';

alter table STG_AVIENT
   add constraint STG_AVIENT_PK primary key (AVI_CODIGO);

/*==============================================================*/
/* Table: STG_CAMFUE                                            */
/*==============================================================*/
create table STG_CAMFUE
(
   CFU_CODIGO           NUMBER(18)           not null,
   CFU_CODFUE           NUMBER(18)           not null,
   CFU_IDENT            VARCHAR2(20 CHAR)    not null,
   CFU_ESPK             VARCHAR2(1 CHAR)     default 'N' not null,
   CFU_ORDEN            NUMBER(2)            default 0 not null
);

comment on table STG_CAMFUE is
'Definicion campos fuente datos';

comment on column STG_CAMFUE.CFU_CODIGO is
'Codigo interno';

comment on column STG_CAMFUE.CFU_CODFUE is
'Codigo interno fuente';

comment on column STG_CAMFUE.CFU_IDENT is
'Id campo';

comment on column STG_CAMFUE.CFU_ESPK is
'Indica si el campo forma parte de la clave primaria';

comment on column STG_CAMFUE.CFU_ORDEN is
'Orden';

alter table STG_CAMFUE
   add constraint STG_CAMFUE_PK primary key (CFU_CODIGO);

/*==============================================================*/
/* Index: STG_CAMFUE_CODFUE_IDENT_UK                            */
/*==============================================================*/
create unique index STG_CAMFUE_CODFUE_IDENT_UK on STG_CAMFUE (
   CFU_CODFUE ASC,
   CFU_IDENT ASC
);

/*==============================================================*/
/* Table: STG_CNFGLO                                            */
/*==============================================================*/
create table STG_CNFGLO
(
   CFG_CODIGO           NUMBER(18)           not null,
   CFG_PROP             VARCHAR2(100 CHAR)   not null,
   CFG_VALOR            VARCHAR2(500 CHAR),
   CFG_DESCR            VARCHAR2(255 CHAR)   not null,
   CFG_NOMOD            NUMBER(1)            default 0 not null
);

comment on table STG_CNFGLO is
'Propiedades configuración global';

comment on column STG_CNFGLO.CFG_CODIGO is
'Código';

comment on column STG_CNFGLO.CFG_PROP is
'Propiedad';

comment on column STG_CNFGLO.CFG_VALOR is
'Valor';

comment on column STG_CNFGLO.CFG_DESCR is
'Descripción propiedad';

comment on column STG_CNFGLO.CFG_NOMOD is
'No modificable';

alter table STG_CNFGLO
   add constraint STG_CNFGLO_PK primary key (CFG_CODIGO);

/*==============================================================*/
/* Index: STG_CNFGLO_PROP_UK                                    */
/*==============================================================*/
create unique index STG_CNFGLO_PROP_UK on STG_CNFGLO (
   CFG_PROP ASC
);

/*==============================================================*/
/* Table: STG_DOMENT                                            */
/*==============================================================*/
create table STG_DOMENT
(
   DEN_CODENT           NUMBER(18)           not null,
   DEN_CODDOM           NUMBER(18)           not null
);

comment on table STG_DOMENT is
'Dominios entidad';

comment on column STG_DOMENT.DEN_CODENT is
'Código entidad';

comment on column STG_DOMENT.DEN_CODDOM is
'Código dominio';

alter table STG_DOMENT
   add constraint STG_DOMENT_PK primary key (DEN_CODENT, DEN_CODDOM);

/*==============================================================*/
/* Table: STG_DOMINI                                            */
/*==============================================================*/
create table STG_DOMINI
(
   DOM_CODIGO           NUMBER(18)           not null,
   DOM_AMBITO           VARCHAR2(1 CHAR)     not null,
   DOM_IDENTI           VARCHAR2(20 CHAR)    not null,
   DOM_DESCR            VARCHAR2(255 CHAR)   not null,
   DOM_CACHE            NUMBER(1)            default 0 not null,
   DOM_TIPO             VARCHAR2(1 CHAR)     not null,
   DOM_BDJNDI           VARCHAR2(500 CHAR),
   DOM_BDSQL            VARCHAR2(2000 CHAR),
   DOM_FDIDFD           NUMBER(18),
   DOM_LFVALS           VARCHAR2(4000 CHAR),
   DOM_REURL            VARCHAR2(500 CHAR),
   DOM_PARAMS           VARCHAR2(4000 CHAR)
);

comment on table STG_DOMINI is
'Dominios';

comment on column STG_DOMINI.DOM_CODIGO is
'Código interno';

comment on column STG_DOMINI.DOM_AMBITO is
'Ambito dominio (G : Global / E: Entidad / A: Área)';

comment on column STG_DOMINI.DOM_IDENTI is
'Identificador dominio';

comment on column STG_DOMINI.DOM_DESCR is
'Descripción';

comment on column STG_DOMINI.DOM_CACHE is
'Indica si se realiza cacheo dominio';

comment on column STG_DOMINI.DOM_TIPO is
'Tipo dominio (B: Base datos / F: Fuente datos / L: Lista fija datos / R: Remota) ';

comment on column STG_DOMINI.DOM_BDJNDI is
'Para tipo Base datos indica JNDI Datasource';

comment on column STG_DOMINI.DOM_BDSQL is
'Para tipo Base datos indica SQL';

comment on column STG_DOMINI.DOM_FDIDFD is
'Para tipo Fuente de datos indica el ID de la Fuente de datos';

comment on column STG_DOMINI.DOM_LFVALS is
'Para tipo Lista fija de datos contiene lista serializada con codigo - valor';

comment on column STG_DOMINI.DOM_REURL is
'Para tipo Remoto indica URL de acceso al servicio remoto de consulta de dominio';

comment on column STG_DOMINI.DOM_PARAMS is
'Lista serializada de códigos parámetros dominio ';

alter table STG_DOMINI
   add constraint STG_DOMINI_PK primary key (DOM_CODIGO);

/*==============================================================*/
/* Index: STG_DOMINI_IDENTI_UK                                  */
/*==============================================================*/
create unique index STG_DOMINI_IDENTI_UK on STG_DOMINI (
   DOM_IDENTI ASC
);

/*==============================================================*/
/* Table: STG_DOMVER                                            */
/*==============================================================*/
create table STG_DOMVER
(
   DVT_CODVTR           NUMBER(18)           not null,
   DVT_CODDOM           NUMBER(18)           not null
);

comment on table STG_DOMVER is
'Dominios utilizados en una versión de  trámite';

comment on column STG_DOMVER.DVT_CODVTR is
'Código versión trámite';

comment on column STG_DOMVER.DVT_CODDOM is
'Código dominio';

alter table STG_DOMVER
   add constraint STG_DOMVER_PK primary key (DVT_CODVTR, DVT_CODDOM);

/*==============================================================*/
/* Table: STG_ENTIDA                                            */
/*==============================================================*/
create table STG_ENTIDA
(
   ENT_CODIGO           NUMBER(18)           not null,
   ENT_DIR3             VARCHAR2(10 CHAR)    not null,
   ENT_NOMBRE           NUMBER(18)           not null,
   ENT_ACTIVA           NUMBER(1)            default 0 not null,
   ENT_ROLADM           VARCHAR2(100 CHAR)   not null,
   ENT_ROLSUP           VARCHAR2(100 CHAR),
   ENT_LOGOTG           NUMBER(18),
   ENT_LOGOTT           NUMBER(18),
   ENT_CSSTT            NUMBER(18),
   ENT_PIETT            NUMBER(18),
   ENT_URLCAR           NUMBER(18),
   ENT_LOPDI            NUMBER(18),
   ENT_LOPD             NUMBER(18),
   ENT_MAPAW            NUMBER(18),
   ENT_AVISOL           NUMBER(18),
   ENT_RSS              NUMBER(18),
   ENT_PRGDIA           NUMBER(3),
   ENT_EMAIL            VARCHAR2(500 CHAR),
   ENT_CNTEMA           NUMBER(1)            default 0 not null,
   ENT_CNTTEL           NUMBER(1)            default 0 not null,
   ENT_CNTURL           NUMBER(1)            default 0 not null,
   ENT_CNTFOR           NUMBER(1)            default 0 not null,
   ENT_TELEFO           VARCHAR2(10 CHAR),
   ENT_URLSOP           VARCHAR2(500 CHAR),
   ENT_YOUTUB           VARCHAR2(255 CHAR),
   ENT_INSTAG           VARCHAR2(255 CHAR),
   ENT_TWITTR           VARCHAR2(255 CHAR),
   ENT_FACEBK           VARCHAR2(255 CHAR),
   ENT_SUBANE           NUMBER(1)            default 0 not null,
   ENT_SUBPAG           NUMBER(1)            default 0 not null,
   ENT_SUBREG           NUMBER(1)            default 0 not null,
   ENT_SUBINS           NUMBER(18),
   ENT_PRSDIA           NUMBER(3),
   ENT_PRSINS           NUMBER(18),
   ENT_REGCEN           NUMBER(1)            default 0 not null,
   ENT_REGOFI           VARCHAR2(20 CHAR),
   ENT_REGDOC 			NUMBER(1)            default 0 not null,
   ENT_VALTRA           NUMBER(1)            default 0 not null
);

comment on table STG_ENTIDA is
'Entidades';

comment on column STG_ENTIDA.ENT_CODIGO is
'Código interno';

comment on column STG_ENTIDA.ENT_DIR3 is
'Código DIR3';

comment on column STG_ENTIDA.ENT_NOMBRE is
'Nombre Entidad';

comment on column STG_ENTIDA.ENT_ACTIVA is
'Indica si la entidad está activa';

comment on column STG_ENTIDA.ENT_ROLADM is
'Role asociado al administrador de la entidad (STG)';

comment on column STG_ENTIDA.ENT_ROLSUP is
'Role asociado al supervidor de la entidad (STH)';

comment on column STG_ENTIDA.ENT_LOGOTG is
'Logo entidad Gestor Trámites';

comment on column STG_ENTIDA.ENT_LOGOTT is
'Logo entidad Asistente Tramitación';

comment on column STG_ENTIDA.ENT_CSSTT is
'CSS Asistente Tramitación';

comment on column STG_ENTIDA.ENT_PIETT is
'Pie de página de contacto para Asistente Tramitación (HTML)';

comment on column STG_ENTIDA.ENT_URLCAR is
'Url Carpeta Ciudadana';

comment on column STG_ENTIDA.ENT_LOPDI is
'Texto respecto LOPD (introducción)';

comment on column STG_ENTIDA.ENT_LOPD is
'Texto respecto LOPD';

comment on column STG_ENTIDA.ENT_MAPAW is
'Mapa web';

comment on column STG_ENTIDA.ENT_AVISOL is
'Aviso legal';

comment on column STG_ENTIDA.ENT_RSS is
'RSS';

comment on column STG_ENTIDA.ENT_PRGDIA is
'Dias preregistro';

comment on column STG_ENTIDA.ENT_EMAIL is
'Email contacto genérico';

comment on column STG_ENTIDA.ENT_CNTEMA is
'Habilitado contacto email';

comment on column STG_ENTIDA.ENT_CNTTEL is
'Habilitado contacto teléfono';

comment on column STG_ENTIDA.ENT_CNTURL is
'Habilitado contacto url';

comment on column STG_ENTIDA.ENT_CNTFOR is
'Habilitado contacto formulario incidencias';

comment on column STG_ENTIDA.ENT_TELEFO is
'Teléfono contacto';

comment on column STG_ENTIDA.ENT_URLSOP is
'Url soporte';

comment on column STG_ENTIDA.ENT_YOUTUB is
'Youtube';

comment on column STG_ENTIDA.ENT_INSTAG is
'Instagram';

comment on column STG_ENTIDA.ENT_TWITTR is
'Twitter';

comment on column STG_ENTIDA.ENT_FACEBK is
'Facebook';

comment on column STG_ENTIDA.ENT_SUBANE is
'Permite subsanación pago anexar';

comment on column STG_ENTIDA.ENT_SUBPAG is
'Permite subsanación pago pagar';

comment on column STG_ENTIDA.ENT_SUBREG is
'Permite subsanación pago registrar';

comment on column STG_ENTIDA.ENT_SUBINS is
'Instrucciones subsanación';

comment on column STG_ENTIDA.ENT_PRSDIA is
'Días a mantener los trámites presenciales';

comment on column STG_ENTIDA.ENT_PRSINS is
'Instrucciones presencial';

comment on column STG_ENTIDA.ENT_REGCEN is
'Indica si los datos de registro están centralizados';

comment on column STG_ENTIDA.ENT_REGOFI is
'Indica oficina de registro centralizada';

comment on column STG_ENTIDA.ENT_REGDOC is
'Indica si en Paso Guardar se ofrece descarga documentos';

comment on column STG_ENTIDA.ENT_VALTRA is
'Indica si en los trámites normalizados se realiza valoración del trámite al finalizar';

alter table STG_ENTIDA
   add constraint STG_ENTIDA_PK primary key (ENT_CODIGO);

/*==============================================================*/
/* Index: STG_ENTIDA_DIR3_UK                                    */
/*==============================================================*/
create unique index STG_ENTIDA_DIR3_UK on STG_ENTIDA (
   ENT_DIR3 ASC
);

/*==============================================================*/
/* Table: STG_FICEXT                                            */
/*==============================================================*/
create table STG_FICEXT
(
   FIE_REFDOC           VARCHAR2(1000 CHAR)  not null,
   FIE_REFFEC           TIMESTAMP            not null,
   FIE_CODFIC           NUMBER(18)           not null,
   FIE_BORRAR           NUMBER(1)            default 0 not null
);

comment on table STG_FICEXT is
'Ubicación de ficheros en sistema remoto. Se almacenará en filesystem que se accederá de forma compartida desde SISTRAMIT.

No se activa FK para que al borrar un fichero se marquen para borrar todos sus referencias externas




';

comment on column STG_FICEXT.FIE_REFDOC is
'Referencia externa (path fichero)

REFERENCIA DOC:  Path
/ENTIDAD-<codigo>
        /ESTILOS: docs asociados a estilos entidad (logo, css, ...)
        /TRAMITE-<codigo>/VERSION-<codigo>: docs asociados a esa versión de trámite, formularios, etc.

    Nombre de fichero: id generado automaticamente
';

comment on column STG_FICEXT.FIE_REFFEC is
'Fecha referencia (sólo será válida la última, el resto se borrarán)';

comment on column STG_FICEXT.FIE_CODFIC is
'Código fichero al que está enlazado';

comment on column STG_FICEXT.FIE_BORRAR is
'Indica si esta marcado para borrar (proceso de purgado)';

alter table STG_FICEXT
   add constraint STG_FICEXT_PK primary key (FIE_REFDOC);

/*==============================================================*/
/* Table: STG_FICHER                                            */
/*==============================================================*/
create table STG_FICHER
(
   FIC_CODIGO           NUMBER(18)           not null,
   FIC_NOMBRE           VARCHAR2(500 CHAR)   not null,
   FIC_PUBLIC           NUMBER(1)            default 0 not null
);

comment on table STG_FICHER is
'Ficheros';

comment on column STG_FICHER.FIC_CODIGO is
'Código fichero';

comment on column STG_FICHER.FIC_NOMBRE is
'Nombre fichero con extensión';

comment on column STG_FICHER.FIC_PUBLIC is
'Indica si es público (accesible desde internet)';

alter table STG_FICHER
   add constraint STG_FICHER_PK primary key (FIC_CODIGO);

/*==============================================================*/
/* Table: STG_FILFUE                                            */
/*==============================================================*/
create table STG_FILFUE
(
   FIF_CODIGO           NUMBER(18)           not null,
   FIF_CODFUE           NUMBER(18)           not null
);

comment on table STG_FILFUE is
'Filas de datos fuente datos';

comment on column STG_FILFUE.FIF_CODIGO is
'Codigo interno';

comment on column STG_FILFUE.FIF_CODFUE is
'Codigo interno fuente datos';

alter table STG_FILFUE
   add constraint STG_FILFUE_PK primary key (FIF_CODIGO);

/*==============================================================*/
/* Table: STG_FMTPLI                                            */
/*==============================================================*/
create table STG_FMTPLI
(
   PFI_CODIGO           NUMBER(18)           not null,
   PFI_CODFMT           NUMBER(18)           not null,
   PFI_CODIDI           VARCHAR2(2 CHAR)     not null,
   PFI_CODFIC           NUMBER(18)           not null
);

comment on table STG_FMTPLI is
'Plantilla idioma por defecto formateador';

comment on column STG_FMTPLI.PFI_CODIGO is
'Código';

comment on column STG_FMTPLI.PFI_CODFMT is
'Código formateador';

comment on column STG_FMTPLI.PFI_CODIDI is
'Identificador idioma';

comment on column STG_FMTPLI.PFI_CODFIC is
'Código fichero';

alter table STG_FMTPLI
   add constraint STG_FMTPLI_PK primary key (PFI_CODIGO);

/*==============================================================*/
/* Table: STG_FORCAM                                            */
/*==============================================================*/
create table STG_FORCAM
(
   FCA_CODIGO           NUMBER(18)           not null,
   FCA_OBLIGA           NUMBER(1)            default 0 not null,
   FCA_LECTUR           NUMBER(1)            default 0 not null,
   FCA_NOMODI           NUMBER(1)            default 0 not null,
   FCA_SCRAUT           NUMBER(18),
   FCA_SCRSLE           NUMBER(18),
   FCA_SCRVAL           NUMBER(18)
);

comment on table STG_FORCAM is
'Campo formulario';

comment on column STG_FORCAM.FCA_CODIGO is
'Código';

comment on column STG_FORCAM.FCA_OBLIGA is
'Obligatorio';

comment on column STG_FORCAM.FCA_LECTUR is
'Sólo lectura';

comment on column STG_FORCAM.FCA_NOMODI is
'No modificable (no permite modificar su valor inicial)';

comment on column STG_FORCAM.FCA_SCRAUT is
'Permite establecer el valor de un campo en función de otros';

comment on column STG_FORCAM.FCA_SCRSLE is
'Permite indicar si un campo es de solo lectura
';

comment on column STG_FORCAM.FCA_SCRVAL is
'Permite establecer validaciones personalizadas sobre un campo permitiendo mostrar un mensaje particularizado de error. Este script se ejecutará al guardar la página.';

alter table STG_FORCAM
   add constraint STG_FORCAM_PK primary key (FCA_CODIGO);

/*==============================================================*/
/* Table: STG_FORCHK                                            */
/*==============================================================*/
create table STG_FORCHK
(
   CCK_CODIGO           NUMBER(18)           not null,
   CCK_VALCHK           VARCHAR2(100 CHAR)   not null,
   CCK_VALNCH           VARCHAR2(100 CHAR)   not null
);

comment on table STG_FORCHK is
'Campo formulario casilla verificación';

comment on column STG_FORCHK.CCK_CODIGO is
'Código elemento';

comment on column STG_FORCHK.CCK_VALCHK is
'Valor checked';

comment on column STG_FORCHK.CCK_VALNCH is
'Valor no checked';

alter table STG_FORCHK
   add constraint STG_FORCHK_PK primary key (CCK_CODIGO);

/*==============================================================*/
/* Table: STG_FORCIN                                            */
/*==============================================================*/
create table STG_FORCIN
(
   CIN_CODIGO           NUMBER(18)           not null,
   CIN_TIPO             VARCHAR2(20 CHAR)    not null,
   CIN_TIPLST           VARCHAR2(1 CHAR)     not null,
   CIN_SCRVAP           NUMBER(18),
   CIN_DOMCOD           NUMBER(18),
   CIN_DOMCCD           VARCHAR2(100 CHAR),
   CIN_DOMCDS           VARCHAR2(100 CHAR),
   CIN_INDICE           NUMBER(1)            default 0 not null,
   CIN_ALTURA           NUMBER(2)            default 1 not null,
   CIN_ORIENTA          VARCHAR2(1 CHAR)     default 'V' not null
);

comment on table STG_FORCIN is
'Campo formulario indexado';

comment on column STG_FORCIN.CIN_CODIGO is
'Código elemento';

comment on column STG_FORCIN.CIN_TIPO is
'Tipo campo indexado:
 - SELECTOR
 - DESPLEGABLE
 - MULTIPLE
 - UNICA';

comment on column STG_FORCIN.CIN_TIPLST is
'Tipo de lista de valores: Fija (F), dominio (D) y script (S)';

comment on column STG_FORCIN.CIN_SCRVAP is
'Permite indicar los valores posibles de campos selector';

comment on column STG_FORCIN.CIN_DOMCOD is
'Codigo de dominio';

comment on column STG_FORCIN.CIN_DOMCCD is
'Campo del dominio que se utilizará como codigo';

comment on column STG_FORCIN.CIN_DOMCDS is
'Campo del dominio que se utilizará como descripción';

comment on column STG_FORCIN.CIN_INDICE is
'Indica si se tiene que mostrar un índice alfabético en los campos de selección.
';

comment on column STG_FORCIN.CIN_ALTURA is
'Indica altura del selector';

comment on column STG_FORCIN.CIN_ORIENTA is
'Indica orientación (selección única): Horizontal (H) / Vertical (V)';

alter table STG_FORCIN
   add constraint STG_FORCIN_PK primary key (CIN_CODIGO);

/*==============================================================*/
/* Table: STG_FORCTX                                            */
/*==============================================================*/
create table STG_FORCTX
(
   CTX_CODIGO           NUMBER(18)           not null,
   CTX_OCULTO           NUMBER(1)            default 0 not null,
   CTX_TIPO             VARCHAR2(10 CHAR)    not null,
   CTX_NORTAM           NUMBER(4),
   CTX_NORMUL           NUMBER(1)            default 0 not null,
   CTX_NORLIN           NUMBER(3),
   CTX_NORMAY           NUMBER(1)            default 0 not null,
   CTX_NOREXP           VARCHAR2(4000 CHAR),
   CTX_NUMENT           NUMBER(2),
   CTX_NUMDEC           NUMBER(1),
   CTX_NUMSEP           VARCHAR2(2 CHAR),
   CTX_NUMRMI           NUMBER(10),
   CTX_NUMRMX           NUMBER(10),
   CTX_NUMSIG           NUMBER(1)            default 0 not null,
   CTX_IDENIJ           NUMBER(1)            default 0 not null,
   CTX_IDEDNI           NUMBER(1)            default 0 not null,
   CTX_IDENIE           NUMBER(1)            default 0 not null,
   CTX_IDENIO           NUMBER(1)            default 0 not null,
   CTX_IDENSS           NUMBER(1)            default 0 not null,
   CTX_TELMOV           NUMBER(1)            default 0 not null,
   CTX_TELFIJ           NUMBER(1)            default 0 not null,
   CTX_PERRAN           NUMBER(1)            default 0 not null
);

comment on table STG_FORCTX is
'Campo formulario texto';

comment on column STG_FORCTX.CTX_CODIGO is
'Código elemento';

comment on column STG_FORCTX.CTX_OCULTO is
'Indica si es campo oculto';

comment on column STG_FORCTX.CTX_TIPO is
'Tipo campo de texto;
- NORMAL
- NUMERO
- EMAIL
- ID
- CP
- TELEFONO
- FECHA
- HORA
- EXPR
- IMPORTE';

comment on column STG_FORCTX.CTX_NORTAM is
'Tamaño (tipo normal)';

comment on column STG_FORCTX.CTX_NORMUL is
'Multilinea (tipo normal)';

comment on column STG_FORCTX.CTX_NORLIN is
'Número líneas (tipo normal)';

comment on column STG_FORCTX.CTX_NORMAY is
'Forzar mayúsculas (tipo normal)';

comment on column STG_FORCTX.CTX_NOREXP is
'Expresión regular (tipo normal)';

comment on column STG_FORCTX.CTX_NUMENT is
'Número dígitos enteros (tipo numero)';

comment on column STG_FORCTX.CTX_NUMDEC is
'Número dígitos decimales (tipo numero)';

comment on column STG_FORCTX.CTX_NUMSEP is
'Separador miles, decimales (tipo numero):
 - PC: Punto y coma
 - CP: Coma y punto
 - SF: Sin formato';

comment on column STG_FORCTX.CTX_NUMRMI is
'Permite introducir un rango minimo (tipo numero)';

comment on column STG_FORCTX.CTX_NUMRMX is
'Permite introducir un rango maximo (tipo numero)';

comment on column STG_FORCTX.CTX_NUMSIG is
'Indica si se admiten números con signo (tipo numero)';

comment on column STG_FORCTX.CTX_IDENIJ is
'Permite nif persona jurídica (tipo identificación)';

comment on column STG_FORCTX.CTX_IDEDNI is
'Permite dni (tipo identificación)';

comment on column STG_FORCTX.CTX_IDENIE is
'Permite nie (tipo identificación)';

comment on column STG_FORCTX.CTX_IDENIO is
'Permite nif persona física especiales (tipo identificación)';

comment on column STG_FORCTX.CTX_IDENSS is
'Permite nss (tipo identificación)';

comment on column STG_FORCTX.CTX_TELMOV is
'Permite móvil (tipo telefono)';

comment on column STG_FORCTX.CTX_TELFIJ is
'Permite fijo (tipo telefono)';

comment on column STG_FORCTX.CTX_PERRAN is
'Permite rango';

alter table STG_FORCTX
   add constraint STG_FORCTX_PK primary key (CTX_CODIGO);

/*==============================================================*/
/* Table: STG_FORELE                                            */
/*==============================================================*/
create table STG_FORELE
(
   FEL_CODIGO           NUMBER(18)           not null,
   FEL_CODFLS           NUMBER(18)           not null,
   FEL_IDENTI           VARCHAR2(50 CHAR)    not null,
   FEL_TIPO             VARCHAR2(2 CHAR)     not null,
   FEL_ORDEN            NUMBER(3)            not null,
   FEL_NUMCOL           NUMBER(2)            not null,
   FEL_TEXTO            NUMBER(18),
   FEL_AYUDA            NUMBER(18),
   FEL_TEXTNO           NUMBER(1)            default 0 not null,
   FEL_TEXTAL           VARCHAR2(1 CHAR)     default 'I' not null,
   FEL_LELMOS           NUMBER(1)            default 0 not null,
   FEL_LELCOL           NUMBER(1)            default 0
);

comment on table STG_FORELE is
'Elemento formulario';

comment on column STG_FORELE.FEL_CODIGO is
'Código';

comment on column STG_FORELE.FEL_CODFLS is
'Código fila';

comment on column STG_FORELE.FEL_IDENTI is
'Identificador elemento';

comment on column STG_FORELE.FEL_TIPO is
'Tipo elemento: CT (Campo de texto) / SE (Selector) / CK (Checkbox) / ET (Etiqueta) / CP (Captcha) / IM (Imagen) / Lista elementos (LE)';

comment on column STG_FORELE.FEL_ORDEN is
'Orden';

comment on column STG_FORELE.FEL_NUMCOL is
'Número de columnas ocupadas (max 6)';

comment on column STG_FORELE.FEL_TEXTO is
'Texto';

comment on column STG_FORELE.FEL_AYUDA is
'Texto tooltip ayuda';

comment on column STG_FORELE.FEL_TEXTNO is
'No mostrar texto componente';

comment on column STG_FORELE.FEL_TEXTAL is
'Alineación texto: Izquierda (I), Centrada (C), Derecha (D)';

comment on column STG_FORELE.FEL_LELMOS is
'Para campos de un componente lista de elementos, indica si sale en la lista';

comment on column STG_FORELE.FEL_LELCOL is
'Para campos de un componente lista de elementos, si sale en la lista indica el ancho de columna';

alter table STG_FORELE
   add constraint STG_FORELE_PK primary key (FEL_CODIGO);

/*==============================================================*/
/* Table: STG_FORETQ                                            */
/*==============================================================*/
create table STG_FORETQ
(
   ETI_CODIGO           NUMBER(18)           not null,
   ETI_TIPETI           VARCHAR2(1 CHAR)     default 'N' not null
);

comment on table STG_FORETQ is
'Etiqueta';

comment on column STG_FORETQ.ETI_CODIGO is
'Código';

comment on column STG_FORETQ.ETI_TIPETI is
'Tipo etiqueta: Normal (N) / Info (I) / Warning (W) / Error (E)';

alter table STG_FORETQ
   add constraint STG_FORETQ primary key (ETI_CODIGO);

/*==============================================================*/
/* Table: STG_FORFMT                                            */
/*==============================================================*/
create table STG_FORFMT
(
   FMT_CODIGO           NUMBER(18)           not null,
   FMT_IDENTI           VARCHAR2(20 CHAR)    not null,
   FMT_CODENT           NUMBER(18)           not null,
   FMT_CLASS            VARCHAR2(500 CHAR)   not null,
   FMT_DESCRI           VARCHAR2(255 CHAR)   not null,
   FMT_DEFECT           NUMBER(1)            default 0 not null,
   FMT_BLOCK            NUMBER(1)            default 0 not null
);

comment on table STG_FORFMT is
'Formateadores formulario';

comment on column STG_FORFMT.FMT_CODIGO is
'Código';

comment on column STG_FORFMT.FMT_IDENTI is
'Identificador';

comment on column STG_FORFMT.FMT_CODENT is
'Código entidad';

comment on column STG_FORFMT.FMT_CLASS is
'Classname del formateador';

comment on column STG_FORFMT.FMT_DESCRI is
'Descripción del formateador';

comment on column STG_FORFMT.FMT_DEFECT is
'Formateador por defecto';

comment on column STG_FORFMT.FMT_BLOCK is
'Indica si el trámite se bloquea para que no se pueda seleccionar a nivel de formulario';

alter table STG_FORFMT
   add constraint STG_FORFMT_PK primary key (FMT_CODIGO);

/*==============================================================*/
/* Index: STG_FORFMT_IDENTI_UK                                  */
/*==============================================================*/
create unique index STG_FORFMT_IDENTI_UK on STG_FORFMT (
   FMT_IDENTI ASC,
   FMT_CODENT ASC
);

/*==============================================================*/
/* Table: STG_FORIMG                                            */
/*==============================================================*/
create table STG_FORIMG
(
   FIM_CODIGO           NUMBER(18)           not null,
   FIM_CODFIC           NUMBER(18)           not null
);

comment on table STG_FORIMG is
'Imagen formulario';

comment on column STG_FORIMG.FIM_CODIGO is
'Código';

comment on column STG_FORIMG.FIM_CODFIC is
'Fichero imagen';

alter table STG_FORIMG
   add constraint STG_FORIMG_PK primary key (FIM_CODIGO);

/*==============================================================*/
/* Table: STG_FORLEL                                            */
/*==============================================================*/
create table STG_FORLEL
(
   LEL_CODIGO           NUMBER(18)           not null,
   LEL_CODPAF           NUMBER(18)           not null
);

comment on table STG_FORLEL is
'Componente lista de elementos';

comment on column STG_FORLEL.LEL_CODIGO is
'Código';

comment on column STG_FORLEL.LEL_CODPAF is
'Código página asociada';

alter table STG_FORLEL
   add constraint STG_FORLEL_PK primary key (LEL_CODIGO);

/*==============================================================*/
/* Table: STG_FORLI                                             */
/*==============================================================*/
create table STG_FORLI
(
   FLS_CODIGO           NUMBER(18)           not null,
   FLS_CODPAF           NUMBER(18)           not null,
   FLS_ORDEN            NUMBER(2)            not null
);

comment on table STG_FORLI is
'Linea sección formulario';

comment on column STG_FORLI.FLS_CODIGO is
'Código';

comment on column STG_FORLI.FLS_CODPAF is
'Código página';

comment on column STG_FORLI.FLS_ORDEN is
'Orden';

alter table STG_FORLI
   add constraint STG_FORLI_PK primary key (FLS_CODIGO);

/*==============================================================*/
/* Table: STG_FORMUL                                            */
/*==============================================================*/
create table STG_FORMUL
(
   FOR_CODIGO           NUMBER(18)           not null,
   FOR_ACCPER           NUMBER(1)            default 0 not null,
   FOR_SCRPLT           NUMBER(18),
   FOR_CABFOR           NUMBER(1)            default 0 not null,
   FOR_CABTXT           NUMBER(18)
);

comment on table STG_FORMUL is
'Formularios internos';

comment on column STG_FORMUL.FOR_CODIGO is
'Código';

comment on column STG_FORMUL.FOR_ACCPER is
'Permitir acciones personalizadas';

comment on column STG_FORMUL.FOR_SCRPLT is
'Script para permitir indicar que plantilla usar';

comment on column STG_FORMUL.FOR_CABFOR is
'Indica si se establece cabecera formulario (logo + título)';

comment on column STG_FORMUL.FOR_CABTXT is
'Texto cabecera';

alter table STG_FORMUL
   add constraint STG_FORMUL_PK primary key (FOR_CODIGO);

/*==============================================================*/
/* Table: STG_FORPAG                                            */
/*==============================================================*/
create table STG_FORPAG
(
   PAF_CODIGO           NUMBER(18)           not null,
   PAF_CODFOR           NUMBER(18)           not null,
   PAF_SCRVAL           NUMBER(18),
   PAF_ORDEN            NUMBER(2)            not null,
   PAF_PAGFIN           NUMBER(1)            default 0 not null,
   PAF_PAGLEL           NUMBER(1)            default 0 not null
);

comment on table STG_FORPAG is
'Página formulario';

comment on column STG_FORPAG.PAF_CODIGO is
'Código';

comment on column STG_FORPAG.PAF_CODFOR is
'Código formulario';

comment on column STG_FORPAG.PAF_SCRVAL is
'Script de validación (permite establecer siguiente página)';

comment on column STG_FORPAG.PAF_ORDEN is
'Orden';

comment on column STG_FORPAG.PAF_PAGFIN is
'Indica si es página final';

comment on column STG_FORPAG.PAF_PAGLEL is
'Indica si una página asociada a un componente lista de elementos';

alter table STG_FORPAG
   add constraint STG_FORPAG_PK primary key (PAF_CODIGO);

/*==============================================================*/
/* Table: STG_FORPLI                                            */
/*==============================================================*/
create table STG_FORPLI
(
   PLI_CODIGO           NUMBER(18)           not null,
   PLI_CODPLT           NUMBER(18)           not null,
   PLI_CODIDI           VARCHAR2(2 CHAR)     not null,
   PLI_CODFIC           NUMBER(18)           not null
);

comment on table STG_FORPLI is
'Plantilla idioma formulario';

comment on column STG_FORPLI.PLI_CODIGO is
'Código';

comment on column STG_FORPLI.PLI_CODPLT is
'Código plantilla';

comment on column STG_FORPLI.PLI_CODIDI is
'Identificador idioma';

comment on column STG_FORPLI.PLI_CODFIC is
'Código fichero';

alter table STG_FORPLI
   add constraint STG_FORPLI_PK primary key (PLI_CODIGO);

/*==============================================================*/
/* Table: STG_FORPLT                                            */
/*==============================================================*/
create table STG_FORPLT
(
   PLT_CODIGO           NUMBER(18)           not null,
   PLT_CODFOR           NUMBER(18)           not null,
   PLT_IDENTI           VARCHAR2(20 CHAR)    not null,
   PLT_CODFMT           NUMBER(18)           not null,
   PLT_DESCRI           VARCHAR2(255 CHAR)   not null,
   PLT_DEFECT           NUMBER(1)            default 0 not null
);

comment on table STG_FORPLT is
'Plantillas formulario';

comment on column STG_FORPLT.PLT_CODIGO is
'Código';

comment on column STG_FORPLT.PLT_CODFOR is
'Código formulario';

comment on column STG_FORPLT.PLT_IDENTI is
'Identificador plantilla';

comment on column STG_FORPLT.PLT_CODFMT is
'Código formateador';

comment on column STG_FORPLT.PLT_DESCRI is
'Descripción';

comment on column STG_FORPLT.PLT_DEFECT is
'Plantilla por defecto';

alter table STG_FORPLT
   add constraint STG_FORPLT_PK primary key (PLT_CODIGO);

/*==============================================================*/
/* Index: STG_FORPLT_IDENTI_UK                                  */
/*==============================================================*/
create unique index STG_FORPLT_IDENTI_UK on STG_FORPLT (
   PLT_CODFOR ASC,
   PLT_IDENTI ASC
);

/*==============================================================*/
/* Table: STG_FORSEC                                            */
/*==============================================================*/
create table STG_FORSEC
(
   FSE_CODIGO           NUMBER(18)           not null,
   FSE_LETRA            VARCHAR2(2 CHAR)
);

comment on table STG_FORSEC is
'Secciones formulario';

comment on column STG_FORSEC.FSE_CODIGO is
'Código';

comment on column STG_FORSEC.FSE_LETRA is
'Letra de la sección';

alter table STG_FORSEC
   add constraint STG_FORSEC_PK primary key (FSE_CODIGO);

/*==============================================================*/
/* Table: STG_FORSOP                                            */
/*==============================================================*/
create table STG_FORSOP
(
   FSO_CODIGO           NUMBER(18)           not null,
   FSO_CODENT           NUMBER(18)           not null,
   FSO_TIPINC           NUMBER(18)           not null,
   FSO_DSCTIP           NUMBER(18)           not null,
   FSO_TIPDST           VARCHAR2(1 CHAR)     not null,
   FSO_LSTEMA           VARCHAR2(4000 CHAR)
);

comment on table STG_FORSOP is
'Formulario soporte para incidencias';

comment on column STG_FORSOP.FSO_CODIGO is
'Código interno';

comment on column STG_FORSOP.FSO_CODENT is
'Código interno';

comment on column STG_FORSOP.FSO_TIPINC is
'Literal tipo incidencia';

comment on column STG_FORSOP.FSO_DSCTIP is
'Literal descripción tipo incidencia';

comment on column STG_FORSOP.FSO_TIPDST is
'Tipo destinatario (R: responsable incidencias / E: lista emails)';

comment on column STG_FORSOP.FSO_LSTEMA is
'Lista emails separados por ; para tipo destinario E';

alter table STG_FORSOP
   add constraint STG_FORSOP_PK primary key (FSO_CODIGO);

/*==============================================================*/
/* Table: STG_FORTRA                                            */
/*==============================================================*/
create table STG_FORTRA
(
   FTR_CODIGO           NUMBER(18)           not null,
   FTR_IDENTI           VARCHAR2(20 CHAR)    not null,
   FTR_DESCRIP          NUMBER(18)           not null,
   FTR_TIPO             VARCHAR2(1 CHAR)     not null,
   FTR_ORDEN            NUMBER(2)            not null,
   FTR_OBLIGA           VARCHAR2(1 CHAR)     not null,
   FTR_SCROBL           NUMBER(18),
   FTR_FIRDIG           NUMBER(1)            default 0,
   FTR_SCRFIR           NUMBER(18),
   FTR_PREREG           NUMBER(1)            default 0,
   FTR_SCRINI           NUMBER(18),
   FTR_SCRPAR           NUMBER(18),
   FTR_SCRRET           NUMBER(18),
   FTR_TIPFOR           VARCHAR2(1 CHAR)     not null,
   FTR_CODFOR           NUMBER(18),
   FTR_FEXGST           NUMBER(18),
   FTR_FEXIDE           VARCHAR2(20 CHAR)
);

comment on table STG_FORTRA is
'Formulario trámite';

comment on column STG_FORTRA.FTR_CODIGO is
'Código';

comment on column STG_FORTRA.FTR_IDENTI is
'Identificador del formulario';

comment on column STG_FORTRA.FTR_DESCRIP is
'Descripción formulario';

comment on column STG_FORTRA.FTR_TIPO is
'Tipo: formulario trámite (T) o formulario captura (C)';

comment on column STG_FORTRA.FTR_ORDEN is
'Orden';

comment on column STG_FORTRA.FTR_OBLIGA is
'Obligatorio:
 - Si (S)
 - Opcional (N)
 - Dependiente (D)';

comment on column STG_FORTRA.FTR_SCROBL is
'En caso de ser dependiente establece obligatoriedad';

comment on column STG_FORTRA.FTR_FIRDIG is
'Indica si se debe firmar digitalmente (para formulario tipo Tramite)';

comment on column STG_FORTRA.FTR_SCRFIR is
'Permite establecer quién debe firmar el formulario (para formulario tramite)';

comment on column STG_FORTRA.FTR_PREREG is
'Indica si se debe presentar en preregistro';

comment on column STG_FORTRA.FTR_SCRINI is
'Script para establecer datos iniciales formulario';

comment on column STG_FORTRA.FTR_SCRPAR is
'Permite establecer parametros cada vez que se acceda al formulario';

comment on column STG_FORTRA.FTR_SCRRET is
'Este script se ejecutará tras el retorno del gestor de formulario y permitirá:
- validar el formulario tras el retorno del gestor de formulario
- alimentar datos de los otros formularios y cambiar su estado. ';

comment on column STG_FORTRA.FTR_TIPFOR is
'Indica tipo formulario: interno (I)  / externo (E)';

comment on column STG_FORTRA.FTR_CODFOR is
'Código formulario gestor interno (si es interno)';

comment on column STG_FORTRA.FTR_FEXGST is
'Gestor formulario externo (si es externo)';

comment on column STG_FORTRA.FTR_FEXIDE is
'Identificador formulario externo (si es externo)';

alter table STG_FORTRA
   add constraint STG_FORTRA_PK primary key (FTR_CODIGO);

/*==============================================================*/
/* Table: STG_FUEDAT                                            */
/*==============================================================*/
create table STG_FUEDAT
(
   FUE_CODIGO           NUMBER(18)           not null,
   FUE_AMBITO           VARCHAR2(1 CHAR)     not null,
   FUE_IDENT            VARCHAR2(20 CHAR)    not null,
   FUE_DESCR            VARCHAR2(255 CHAR)   not null,
   FUE_CODENT           NUMBER(18),
   FUE_CODARE           NUMBER(18)
);

comment on table STG_FUEDAT is
'Fuente de datos';

comment on column STG_FUEDAT.FUE_CODIGO is
'Codigo interno';

comment on column STG_FUEDAT.FUE_AMBITO is
'Ambito fuente datos (G : Global / E: Entidad / A: Área)';

comment on column STG_FUEDAT.FUE_IDENT is
'Identificador fuente de datos';

comment on column STG_FUEDAT.FUE_DESCR is
'Descripción fuente de datos';

comment on column STG_FUEDAT.FUE_CODENT is
'Código entidad';

comment on column STG_FUEDAT.FUE_CODARE is
'Código área';

alter table STG_FUEDAT
   add constraint STG_FUEDAT_PK primary key (FUE_CODIGO);

/*==============================================================*/
/* Index: STG_FUEDAT_IDENTI_UK                                  */
/*==============================================================*/
create unique index STG_FUEDAT_IDENTI_UK on STG_FUEDAT (
   FUE_IDENT ASC
);

/*==============================================================*/
/* Table: STG_GESFOR                                            */
/*==============================================================*/
create table STG_GESFOR
(
   GFE_CODIGO           NUMBER(18)           not null,
   GFE_CODENT           NUMBER(18)           not null,
   GFE_IDENTI           VARCHAR2(20 CHAR)    not null,
   GFE_DESCR            VARCHAR2(255 CHAR)   not null,
   GFE_URL              VARCHAR2(100 CHAR)   not null
);

comment on table STG_GESFOR is
'Gestor formularios externos';

comment on column STG_GESFOR.GFE_CODIGO is
'Código';

comment on column STG_GESFOR.GFE_CODENT is
'Código entidad';

comment on column STG_GESFOR.GFE_IDENTI is
'Identificador';

comment on column STG_GESFOR.GFE_DESCR is
'Descripción';

comment on column STG_GESFOR.GFE_URL is
'Url acceso gestor formularios';

alter table STG_GESFOR
   add constraint STG_GESFOR_PK primary key (GFE_CODIGO);

/*==============================================================*/
/* Index: STG_GESFOR_IDENTI_UK                                  */
/*==============================================================*/
create unique index STG_GESFOR_IDENTI_UK on STG_GESFOR (
   GFE_IDENTI ASC
);

/*==============================================================*/
/* Table: STG_HISVER                                            */
/*==============================================================*/
create table STG_HISVER
(
   HVE_CODIGO           NUMBER(18)           not null,
   HVE_CODVTR           NUMBER(18)           not null,
   HVE_FECHA            DATE                 not null,
   HVE_ACCION           VARCHAR2(1 CHAR)     not null,
   HVE_RELEAS           NUMBER(8)            not null,
   HVE_CAMBIO           VARCHAR2(255 CHAR)   not null,
   HVE_USER             VARCHAR2(100 CHAR)   not null
);

comment on table STG_HISVER is
'Historial versión';

comment on column STG_HISVER.HVE_CODIGO is
'Código';

comment on column STG_HISVER.HVE_CODVTR is
'Código versión trámite';

comment on column STG_HISVER.HVE_FECHA is
'Fecha';

comment on column STG_HISVER.HVE_ACCION is
'Tipo acción: C (Creación) / M (Modificación) / I (Importación)';

comment on column STG_HISVER.HVE_RELEAS is
'Release versión';

comment on column STG_HISVER.HVE_CAMBIO is
'Detalle cambio';

comment on column STG_HISVER.HVE_USER is
'Usuario';

alter table STG_HISVER
   add constraint STG_HISVER_PK primary key (HVE_CODIGO);

/*==============================================================*/
/* Table: STG_IDIOMA                                            */
/*==============================================================*/
create table STG_IDIOMA
(
   IDI_IDENTI           VARCHAR2(2 CHAR)     not null
);

comment on table STG_IDIOMA is
'Idiomas soportados';

comment on column STG_IDIOMA.IDI_IDENTI is
'Identificador idioma';

alter table STG_IDIOMA
   add constraint STG_IDIOMA_PK primary key (IDI_IDENTI);

/*==============================================================*/
/* Table: STG_LFVCIN                                            */
/*==============================================================*/
create table STG_LFVCIN
(
   LFV_CODIGO           NUMBER(18)           not null,
   LFV_CODCIN           NUMBER(18)           not null,
   LFV_VALOR            VARCHAR2(100 CHAR)   not null,
   LFV_DESCRIP          NUMBER(18)           not null,
   LFV_DEFECT           NUMBER(1)            default 0 not null,
   LFV_ORDEN            NUMBER(2)            not null
);

comment on table STG_LFVCIN is
'Para campo indexado de lista valores fija especifica la lista de valores';

comment on column STG_LFVCIN.LFV_CODIGO is
'Código valor';

comment on column STG_LFVCIN.LFV_CODCIN is
'Código elemento';

comment on column STG_LFVCIN.LFV_VALOR is
'Valor';

comment on column STG_LFVCIN.LFV_DESCRIP is
'Descripción';

comment on column STG_LFVCIN.LFV_DEFECT is
'Valor por defecto';

comment on column STG_LFVCIN.LFV_ORDEN is
'Orden';

alter table STG_LFVCIN
   add constraint STG_LFVCIN_PK primary key (LFV_CODIGO);

/*==============================================================*/
/* Index: STG_LFVCIN_CODCIN_VALOR_UK                            */
/*==============================================================*/
create unique index STG_LFVCIN_CODCIN_VALOR_UK on STG_LFVCIN (
   LFV_CODCIN ASC,
   LFV_VALOR ASC
);

/*==============================================================*/
/* Table: STG_LITSCR                                            */
/*==============================================================*/
create table STG_LITSCR
(
   LSC_CODIGO           NUMBER(18)           not null,
   LSC_CODSCR           NUMBER(18)           not null,
   LSC_IDENTI           VARCHAR2(20 CHAR)    not null,
   LSC_CODTRA           NUMBER(18)           not null
);

comment on table STG_LITSCR is
'Identifica los literares de error de cada Script en GTT';

comment on column STG_LITSCR.LSC_CODIGO is
'Codigo del literal';

comment on column STG_LITSCR.LSC_CODSCR is
'Codigo del Script';

comment on column STG_LITSCR.LSC_IDENTI is
'Identificación del literal';

comment on column STG_LITSCR.LSC_CODTRA is
'Código traducción';

alter table STG_LITSCR
   add constraint STG_LITSCR_PK primary key (LSC_CODIGO);

/*==============================================================*/
/* Index: STG_LITSCR_CODSCR_IDENTI_UK                           */
/*==============================================================*/
create unique index STG_LITSCR_CODSCR_IDENTI_UK on STG_LITSCR (
   LSC_CODSCR ASC,
   LSC_IDENTI ASC
);

/*==============================================================*/
/* Table: STG_PAGTRA                                            */
/*==============================================================*/
create table STG_PAGTRA
(
   PAG_CODIGO           NUMBER(18)           not null,
   PAG_CODPTR           NUMBER(18)           not null,
   PAG_IDENTI           VARCHAR2(20 CHAR)    not null,
   PAG_DESCRIP          NUMBER(18)           not null,
   PAG_ORDEN            NUMBER(2)            not null,
   PAG_OBLIGA           VARCHAR2(1 CHAR)     not null,
   PAG_SCROBL           NUMBER(18),
   PAG_SCRDPG           NUMBER(18),
   PAG_TIPO             VARCHAR2(1 CHAR)     not null,
   PAG_SIMULA           NUMBER(1)            default 0 not null
);

comment on table STG_PAGTRA is
'Pago trámite';

comment on column STG_PAGTRA.PAG_CODIGO is
'Código';

comment on column STG_PAGTRA.PAG_CODPTR is
'Código paso tramitación';

comment on column STG_PAGTRA.PAG_IDENTI is
'Codigo del documento de pago';

comment on column STG_PAGTRA.PAG_DESCRIP is
'Descripción anexo';

comment on column STG_PAGTRA.PAG_ORDEN is
'Orden';

comment on column STG_PAGTRA.PAG_OBLIGA is
'Obligatorio:
 - Si (S)
 - Opcional (N)
 - Dependiente (D)';

comment on column STG_PAGTRA.PAG_SCROBL is
'En caso de ser dependiente establece obligatoriedad';

comment on column STG_PAGTRA.PAG_SCRDPG is
'Permite establecer dinámicamente los datos del pago';

comment on column STG_PAGTRA.PAG_TIPO is
'Tipo: T (Telemático) / P (Presencial) / A (Ambos). Dependerá del tipo de plugin.';

comment on column STG_PAGTRA.PAG_SIMULA is
'Indica que el pago es simulado.';

alter table STG_PAGTRA
   add constraint STG_PAGTRA_PK primary key (PAG_CODIGO);

/*==============================================================*/
/* Index: STG_PAGTRA_CODPTR_IDENTI_UK                           */
/*==============================================================*/
create unique index STG_PAGTRA_CODPTR_IDENTI_UK on STG_PAGTRA (
   PAG_CODPTR ASC,
   PAG_IDENTI ASC
);

/*==============================================================*/
/* Table: STG_PASANE                                            */
/*==============================================================*/
create table STG_PASANE
(
   PAN_CODIGO           NUMBER(18)           not null,
   PAN_SCRDIN           NUMBER(18),
   PAN_SUBSAN           NUMBER(1)            default 0 not null
);

comment on table STG_PASANE is
'Paso anexar';

comment on column STG_PASANE.PAN_CODIGO is
'Código';

comment on column STG_PASANE.PAN_SCRDIN is
'Script para anexos dinámicos';

comment on column STG_PASANE.PAN_SUBSAN is
'Indica si se habilita subsanación';

alter table STG_PASANE
   add constraint STG_PASANE_PK primary key (PAN_CODIGO);

/*==============================================================*/
/* Table: STG_PASCAP                                            */
/*==============================================================*/
create table STG_PASCAP
(
   PCA_CODIGO           NUMBER(18)           not null,
   PCA_CODFOR           NUMBER(18)
);

comment on table STG_PASCAP is
'Paso captura';

comment on column STG_PASCAP.PCA_CODIGO is
'Código';

comment on column STG_PASCAP.PCA_CODFOR is
'Código';

alter table STG_PASCAP
   add constraint STG_PASCAP_PK primary key (PCA_CODIGO);

/*==============================================================*/
/* Table: STG_PASDBS                                            */
/*==============================================================*/
create table STG_PASDBS
(
   PDB_CODIGO           NUMBER(18)           not null,
   PDB_INSINI           NUMBER(18)
);

comment on table STG_PASDBS is
'Paso debe saber';

comment on column STG_PASDBS.PDB_CODIGO is
'Código';

comment on column STG_PASDBS.PDB_INSINI is
'Instrucciones inicio (HTML)';

alter table STG_PASDBS
   add constraint STG_PASDBS_PK primary key (PDB_CODIGO);

/*==============================================================*/
/* Table: STG_PASINF                                            */
/*==============================================================*/
create table STG_PASINF
(
   PIN_CODIGO           NUMBER(18)           not null,
   PIN_SCRDAT           NUMBER(18),
   PIN_FICPLA           NUMBER(18)
);

comment on table STG_PASINF is
'Paso información';

comment on column STG_PASINF.PIN_CODIGO is
'Código';

comment on column STG_PASINF.PIN_SCRDAT is
'Script datos';

comment on column STG_PASINF.PIN_FICPLA is
'Fichero plantilla';

alter table STG_PASINF
   add constraint STG_PASINF_PK primary key (PIN_CODIGO);

/*==============================================================*/
/* Table: STG_PASOTR                                            */
/*==============================================================*/
create table STG_PASOTR
(
   PTR_CODIGO           NUMBER(18)           not null,
   PTR_CODVTR           NUMBER(18)           not null,
   PTR_TIPPAS           VARCHAR2(2 CHAR)     not null,
   PTR_IDEPTR           VARCHAR2(20 CHAR)    not null,
   PTR_DESCRI           NUMBER(18),
   PTR_ORDEN            NUMBER(2)            not null,
   PTR_FINAL            NUMBER(1)            default 0 not null,
   PTR_SCRNVG           NUMBER(18),
   PTR_SCRVAR           NUMBER(18)
);

comment on table STG_PASOTR is
'Paso tramitación';

comment on column STG_PASOTR.PTR_CODIGO is
'Código paso tramitación';

comment on column STG_PASOTR.PTR_CODVTR is
'Código versión trámite';

comment on column STG_PASOTR.PTR_TIPPAS is
'Tipo del paso de tramitación';

comment on column STG_PASOTR.PTR_IDEPTR is
'Identificador paso tramitación. Para flujo normalizado será establecido automáticamente, para flujo personalizado será establecido por desarrollador.';

comment on column STG_PASOTR.PTR_DESCRI is
'Descripción del paso de tramitación. En flujo normalizado será establecido automáticamente.';

comment on column STG_PASOTR.PTR_ORDEN is
'Orden paso';

comment on column STG_PASOTR.PTR_FINAL is
'Indica que paso es final';

comment on column STG_PASOTR.PTR_SCRNVG is
'Para flujo personalizado permite establecer script navegación';

comment on column STG_PASOTR.PTR_SCRVAR is
'Para flujo personalizado permite almacenamiento de variables a usar entre pasos';

alter table STG_PASOTR
   add constraint STG_PASOTR_PK primary key (PTR_CODIGO);

/*==============================================================*/
/* Index: STG_PASOTR_IDEPTR_CODVTR_UK                           */
/*==============================================================*/
create unique index STG_PASOTR_IDEPTR_CODVTR_UK on STG_PASOTR (
   PTR_IDEPTR ASC,
   PTR_CODVTR ASC
);

/*==============================================================*/
/* Table: STG_PASPAG                                            */
/*==============================================================*/
create table STG_PASPAG
(
   PPG_CODIGO           NUMBER(18)           not null,
   PPG_SUBSAN           NUMBER(1)            default 0 not null
);

comment on table STG_PASPAG is
'Paso pagos';

comment on column STG_PASPAG.PPG_CODIGO is
'Código';

comment on column STG_PASPAG.PPG_SUBSAN is
'Indica si se habilita subsanación';

alter table STG_PASPAG
   add constraint STG_PASPAG_PK primary key (PPG_CODIGO);

/*==============================================================*/
/* Table: STG_PASREG                                            */
/*==============================================================*/
create table STG_PASREG
(
   PRG_CODIGO           NUMBER(18)           not null,
   PRG_REGOFI           VARCHAR2(20 CHAR),
   PRG_REGLIB           VARCHAR2(20 CHAR),
   PRG_REGASU           VARCHAR2(20 CHAR),
   PRG_SCRREG           NUMBER(18),
   PRG_INSFIT           NUMBER(18),
   PRG_INSPRE           NUMBER(18),
   PRG_INSSUB           NUMBER(18),
   PRG_SCRPRE           NUMBER(18),
   PRG_REPADM           NUMBER(1)            default 0 not null,
   PRG_REPVAL           NUMBER(1)            default 0 not null,
   PRG_SCRREP           NUMBER(18),
   PRG_SCRVAL           NUMBER(18),
   PRG_SUBSAN           NUMBER(1)            default 0 not null
);

comment on table STG_PASREG is
'Paso registrar';

comment on column STG_PASREG.PRG_CODIGO is
'Código';

comment on column STG_PASREG.PRG_REGOFI is
'Código oficina registro';

comment on column STG_PASREG.PRG_REGLIB is
'Código libro registro';

comment on column STG_PASREG.PRG_REGASU is
'Código tipo asunto';

comment on column STG_PASREG.PRG_SCRREG is
'Script destino registro';

comment on column STG_PASREG.PRG_INSFIT is
'Instrucciones fin tramitación';

comment on column STG_PASREG.PRG_INSPRE is
'Instrucciones entrega presencial';

comment on column STG_PASREG.PRG_INSSUB is
'Instrucciones subsanación';

comment on column STG_PASREG.PRG_SCRPRE is
'Script presentador';

comment on column STG_PASREG.PRG_REPADM is
'Indica si admite representación';

comment on column STG_PASREG.PRG_REPVAL is
'Indica si valida representación';

comment on column STG_PASREG.PRG_SCRREP is
'Script representante';

comment on column STG_PASREG.PRG_SCRVAL is
'Script para validar permitir registrar';

comment on column STG_PASREG.PRG_SUBSAN is
'Indica si se habilita subsanación';

alter table STG_PASREG
   add constraint STG_PASREG_PK primary key (PRG_CODIGO);

/*==============================================================*/
/* Table: STG_PASREL                                            */
/*==============================================================*/
create table STG_PASREL
(
   PRL_CODIGO           NUMBER(18)           not null
);

comment on table STG_PASREL is
'Paso rellenar';

comment on column STG_PASREL.PRL_CODIGO is
'Código';

alter table STG_PASREL
   add constraint STG_PASREL_PK primary key (PRL_CODIGO);

/*==============================================================*/
/* Table: STG_PLUGIN                                            */
/*==============================================================*/
create table STG_PLUGIN
(
   PLG_CODIGO           NUMBER(18)           not null,
   PLG_AMBITO           VARCHAR2(1 CHAR)     not null,
   PLG_CODENT           NUMBER(18),
   PLG_TIPO             VARCHAR2(3 CHAR)     not null,
   PLG_DESCR            VARCHAR2(255 CHAR)   not null,
   PLG_CLASS            VARCHAR2(500 CHAR)   not null,
   PLG_PREPRO           VARCHAR2(100 CHAR),
   PLG_PROPS            VARCHAR2(4000 CHAR)
);

comment on table STG_PLUGIN is
'Plugins de conexión con otros sistemas';

comment on column STG_PLUGIN.PLG_CODIGO is
'Codigo';

comment on column STG_PLUGIN.PLG_AMBITO is
'Ámbito: G (Global) , E (Entidad)';

comment on column STG_PLUGIN.PLG_CODENT is
'Código entidad (Ambito entidad)';

comment on column STG_PLUGIN.PLG_TIPO is
'Tipo plugin:
  - Global:  LOG: Login, REP: Representación, DOM: Dominios remotos, FIR: Firma
  - Entidad: PRO: Catalogo procedimientos, REG: Registro, PAG: Pagos';

comment on column STG_PLUGIN.PLG_DESCR is
'Descripción plugin';

comment on column STG_PLUGIN.PLG_CLASS is
'Clase implementadora';

comment on column STG_PLUGIN.PLG_PREPRO is
'Prefijo propiedades';

comment on column STG_PLUGIN.PLG_PROPS is
'Lista serializada propiedades (codigo  - valor)';

alter table STG_PLUGIN
   add constraint STG_PLUGIN_PK primary key (PLG_CODIGO);

/*==============================================================*/
/* Table: STG_PRDCIN                                            */
/*==============================================================*/
create table STG_PRDCIN
(
   CIP_CODIGO           NUMBER(18)           not null,
   CIP_CODCIN           NUMBER(18)           not null,
   CIP_TIPO             VARCHAR2(1 CHAR)     not null,
   CIP_VALOR            VARCHAR2(1000 CHAR)  not null,
   CIP_PARAM            VARCHAR2(1000 CHAR)
);

comment on table STG_PRDCIN is
'Para campo indexado de tipo dominio especifica parámetros dominio ';

comment on column STG_PRDCIN.CIP_CODIGO is
'Código interno';

comment on column STG_PRDCIN.CIP_CODCIN is
'Código campo indexado';

comment on column STG_PRDCIN.CIP_TIPO is
'Tipo parámetro: constante, campo, parámetro apertura, etc.';

comment on column STG_PRDCIN.CIP_VALOR is
'Valor parámetro';

comment on column STG_PRDCIN.CIP_PARAM is
'Parámetro dominio';

alter table STG_PRDCIN
   add constraint STG_PRDCIN_PK primary key (CIP_CODIGO);

/*==============================================================*/
/* Table: STG_PRLFTR                                            */
/*==============================================================*/
create table STG_PRLFTR
(
   FPR_CODPRL           NUMBER(18)           not null,
   FPR_CODFOR           NUMBER(18)           not null
);

comment on table STG_PRLFTR is
'Formularios paso rellenar';

comment on column STG_PRLFTR.FPR_CODPRL is
'Código paso rellenar';

comment on column STG_PRLFTR.FPR_CODFOR is
'Código paso formulario';

alter table STG_PRLFTR
   add constraint STG_PRLFTR_PK primary key (FPR_CODPRL, FPR_CODFOR);

/*==============================================================*/
/* Table: STG_PROCES                                            */
/*==============================================================*/
create table STG_PROCES
(
   PROC_IDENT           VARCHAR2(20 CHAR)    not null,
   PROC_INSTAN          VARCHAR2(50 CHAR),
   PROC_FECHA           DATE
);

comment on table STG_PROCES is
'Control ejecución procesos background.
Para que una sola instancia se autoconfigure como maestro.';

comment on column STG_PROCES.PROC_IDENT is
'Identificador fijo';

comment on column STG_PROCES.PROC_INSTAN is
'Id instancia';

comment on column STG_PROCES.PROC_FECHA is
'Fecha ultima verificación';

alter table STG_PROCES
   add constraint STG_PROCES_PK primary key (PROC_IDENT);

/*==============================================================*/
/* Table: STG_ROLARE                                            */
/*==============================================================*/
create table STG_ROLARE
(
   RLA_CODIGO           NUMBER(18)           not null,
   RLA_CODARE           NUMBER(18)           not null,
   RLA_TIPO             VARCHAR2(1 CHAR)     not null,
   RLA_VALOR            VARCHAR2(100 CHAR)   not null,
   RLA_DESCR            VARCHAR2(255 CHAR)   not null,
   RLA_PERALT           NUMBER(1)            default 0 not null,
   RLA_PERMOD           NUMBER(1)            default 0 not null,
   RLA_PERCON           NUMBER(1)            default 0 not null,
   RLA_PERHLP           NUMBER(1)            default 0 not null
);

comment on table STG_ROLARE is
'Roles área';

comment on column STG_ROLARE.RLA_CODIGO is
'Código';

comment on column STG_ROLARE.RLA_CODARE is
'Código área';

comment on column STG_ROLARE.RLA_TIPO is
'Tipo: R (Role) / U (Usuario)';

comment on column STG_ROLARE.RLA_VALOR is
'Para Role el código de Role, para Usuario el NIF del usuario';

comment on column STG_ROLARE.RLA_DESCR is
'Descripción';

comment on column STG_ROLARE.RLA_PERALT is
'Permiso administrador área';

comment on column STG_ROLARE.RLA_PERMOD is
'Permiso desarrollador área';

comment on column STG_ROLARE.RLA_PERCON is
'Permiso consulta';

comment on column STG_ROLARE.RLA_PERHLP is
'Permiso acceso helpdesk';

alter table STG_ROLARE
   add constraint STG_ROLARE_PK primary key (RLA_CODIGO);

/*==============================================================*/
/* Table: STG_SCRIPT                                            */
/*==============================================================*/
create table STG_SCRIPT
(
   SCR_CODIGO           NUMBER(18)           not null,
   SCR_SCRIPT           CLOB
)
TABLESPACE SISTRAGES
   LOB (SCR_SCRIPT) STORE AS STG_SCRIPT_SCRIPT_LOB
	 (TABLESPACE SISTRAGES_LOB
	 INDEX STG_SCRIPT_SCRIPT_LOB_I);

comment on table STG_SCRIPT is
'Scripts';

comment on column STG_SCRIPT.SCR_CODIGO is
'Código';

comment on column STG_SCRIPT.SCR_SCRIPT is
'Script';

alter table STG_SCRIPT
   add constraint STG_SCRIPT_PK primary key (SCR_CODIGO);

/*==============================================================*/
/* Table: STG_SESION                                            */
/*==============================================================*/
create table STG_SESION
(
   SESI_USUA            VARCHAR2(100 CHAR)   not null,
   SESI_FECHA           DATE                 not null,
   SESI_PERFIL          VARCHAR2(50),
   SESI_IDIOMA          VARCHAR2(2 CHAR),
   SESI_ENTIDA          NUMBER(18)
);

comment on table STG_SESION is
'Sesiones usuario';

comment on column STG_SESION.SESI_USUA is
'Identificador fijo';

comment on column STG_SESION.SESI_FECHA is
'Fecha ultima sesión';

comment on column STG_SESION.SESI_PERFIL is
'Perfil usado';

comment on column STG_SESION.SESI_IDIOMA is
'Idioma';

comment on column STG_SESION.SESI_ENTIDA is
'Entidad (opcional según perfil)';

alter table STG_SESION
   add constraint STG_SESION_PK primary key (SESI_USUA);

/*==============================================================*/
/* Table: STG_TRADUC                                            */
/*==============================================================*/
create table STG_TRADUC
(
   TRA_CODIGO           NUMBER(18)           not null
);

comment on table STG_TRADUC is
'Tabla de literales multidioma';

comment on column STG_TRADUC.TRA_CODIGO is
'Código interno';

alter table STG_TRADUC
   add constraint STG_TRADUC_PK primary key (TRA_CODIGO);

/*==============================================================*/
/* Table: STG_TRAIDI                                            */
/*==============================================================*/
create table STG_TRAIDI
(
   TRI_CODIGO           NUMBER(18)           not null,
   TRI_CODTRA           NUMBER(18)           not null,
   TRI_LITERA           VARCHAR2(4000 CHAR)  not null,
   TRI_IDIOMA           VARCHAR2(2 CHAR)     not null
);

comment on table STG_TRAIDI is
'Traducción literales';

comment on column STG_TRAIDI.TRI_CODIGO is
'Código interno';

comment on column STG_TRAIDI.TRI_CODTRA is
'Código traducción';

comment on column STG_TRAIDI.TRI_LITERA is
'Literal';

comment on column STG_TRAIDI.TRI_IDIOMA is
'Idioma';

alter table STG_TRAIDI
   add constraint STG_TRAIDI_PK primary key (TRI_CODIGO);

/*==============================================================*/
/* Table: STG_TRAMIT                                            */
/*==============================================================*/
create table STG_TRAMIT
(
   TRM_CODIGO           NUMBER(18)           not null,
   TRM_CODARE           NUMBER(18)           not null,
   TRM_IDENTI           VARCHAR2(20 CHAR)    not null,
   TRM_DESCR            VARCHAR2(1000 CHAR)  not null
);

comment on table STG_TRAMIT is
'Tramites';

comment on column STG_TRAMIT.TRM_CODIGO is
'Código';

comment on column STG_TRAMIT.TRM_CODARE is
'Código área';

comment on column STG_TRAMIT.TRM_IDENTI is
'Identificador trámite';

comment on column STG_TRAMIT.TRM_DESCR is
'Descripción';

alter table STG_TRAMIT
   add constraint STG_TRAMIT_PK primary key (TRM_CODIGO);

/*==============================================================*/
/* Index: STG_TRAMIT_IDENTI_UK                                  */
/*==============================================================*/
create unique index STG_TRAMIT_IDENTI_UK on STG_TRAMIT (
   TRM_IDENTI ASC
);

/*==============================================================*/
/* Table: STG_VALCFU                                            */
/*==============================================================*/
create table STG_VALCFU
(
   VCF_CODIGO           NUMBER(18)           not null,
   VCF_CODFIF           NUMBER(18)           not null,
   VCF_CODCFU           NUMBER(18)           not null,
   VCF_VALOR            VARCHAR2(4000 CHAR)
);

comment on table STG_VALCFU is
'Valores fuente datos';

comment on column STG_VALCFU.VCF_CODIGO is
'Codigo interno';

comment on column STG_VALCFU.VCF_CODFIF is
'Codigo interno fila fuente datos';

comment on column STG_VALCFU.VCF_CODCFU is
'Codigo interno campo fuente datos';

comment on column STG_VALCFU.VCF_VALOR is
'Valor';

alter table STG_VALCFU
   add constraint STG_VACFU_PK primary key (VCF_CODIGO);

/*==============================================================*/
/* Table: STG_VALORA                                            */
/*==============================================================*/
create table STG_VALORA
(
   VAT_CODIGO           NUMBER(18)           not null,
   VAT_CODENT           NUMBER(18)           not null,
   VAT_IDENTI           VARCHAR2(20 CHAR)    not null,
   VAT_DESCRI           NUMBER(18)           not null
);

comment on table STG_VALORA is
'Lista valoraciones trámite';

comment on column STG_VALORA.VAT_CODIGO is
'Código interno';

comment on column STG_VALORA.VAT_CODENT is
'Código entidad';

comment on column STG_VALORA.VAT_IDENTI is
'Código incidencia';

comment on column STG_VALORA.VAT_DESCRI is
'Descripción incidencia';

alter table STG_VALORA
   add constraint STG_VALORA_PK primary key (VAT_CODIGO);

/*==============================================================*/
/* Index: STG_VALORA_IDENTI_UK                                  */
/*==============================================================*/
create unique index STG_VALORA_IDENTI_UK on STG_VALORA (
   VAT_CODENT ASC,
   VAT_IDENTI ASC
);

/*==============================================================*/
/* Table: STG_VERTRA                                            */
/*==============================================================*/
create table STG_VERTRA
(
   VTR_CODIGO           NUMBER(18)           not null,
   VTR_CODTRM           NUMBER(18)           not null,
   VTR_NUMVER           NUMBER(2)            not null,
   VTR_TIPFLU           VARCHAR2(1 CHAR)     not null,
   VTR_AUTENT           NUMBER(1)            default 0 not null,
   VTR_AUTQAA           NUMBER(1),
   VTR_AUTENO           NUMBER(1)            default 0 not null,
   VTR_IDISOP           VARCHAR2(50 CHAR)    not null,
   VTR_PERSIS           NUMBER(1)            default 0 not null,
   VTR_PERINF           NUMBER(1)            default 0 not null,
   VTR_PERDIA           NUMBER(2),
   VTR_SCRPER           NUMBER(18),
   VTR_SCRINTRA         NUMBER(18),
   VTR_BLOQ             NUMBER(1)            default 0,
   VTR_BLOQID           VARCHAR2(10 CHAR),
   VTR_BLOQUS           VARCHAR2(255 CHAR),
   VTR_RELEAS           NUMBER(8),
   VTR_ACTIVO           NUMBER(1)            default 0 not null,
   VTR_DEBUG            NUMBER(1)            default 0 not null,
   VTR_LIMTIP           VARCHAR2(1 char)     default 'N' not null,
   VTR_LIMNUM           NUMBER(5),
   VTR_LIMINT           NUMBER(2),
   VTR_DESPLZ           NUMBER(1)            default 0 not null,
   VTR_DESINI           DATE,
   VTR_DESFIN           DATE,
   VTR_DESMEN           NUMBER(18)
);

comment on table STG_VERTRA is
'Versión trámite';

comment on column STG_VERTRA.VTR_CODIGO is
'Código';

comment on column STG_VERTRA.VTR_CODTRM is
'Código trámite';

comment on column STG_VERTRA.VTR_NUMVER is
'Número versión';

comment on column STG_VERTRA.VTR_TIPFLU is
'Tipo de flujo; Normal (N) / Personalizado (P)';

comment on column STG_VERTRA.VTR_AUTENT is
'Indica si es autenticado';

comment on column STG_VERTRA.VTR_AUTQAA is
'Para autenticado indica nivel QAA';

comment on column STG_VERTRA.VTR_AUTENO is
'Indica si no es autenticado';

comment on column STG_VERTRA.VTR_IDISOP is
'Idiomas soportados (lista de idiomas separados por punto y coma)';

comment on column STG_VERTRA.VTR_PERSIS is
'Indica si admite persistencia';

comment on column STG_VERTRA.VTR_PERINF is
'En caso de admitir persistencia indica si es infinita';

comment on column STG_VERTRA.VTR_PERDIA is
'En caso de admitir persistencia y no ser infinita se indican los días de persistencia';

comment on column STG_VERTRA.VTR_SCRPER is
'Script de personalizacion. Se ejecuta al cargar trámite (inicio o retomar desde persistencia)';

comment on column STG_VERTRA.VTR_SCRINTRA is
'Script inicialización trámite. Se ejecuta al iniciar un trámite.';

comment on column STG_VERTRA.VTR_BLOQ is
'Versión bloqueada';

comment on column STG_VERTRA.VTR_BLOQID is
'Identificación del usuario que bloquea la versión';

comment on column STG_VERTRA.VTR_BLOQUS is
'Nombre y apellidos del usuario que bloquea la versión';

comment on column STG_VERTRA.VTR_RELEAS is
'Realease actual, se crea al desbloquear la versión';

comment on column STG_VERTRA.VTR_ACTIVO is
'Indica si la versión está activa';

comment on column STG_VERTRA.VTR_DEBUG is
'Indica si esta habilitado el debug del tramite';

comment on column STG_VERTRA.VTR_LIMTIP is
'Liimite tramitacion: N, I (sin limite, iniciados x intervalo, ...)';

comment on column STG_VERTRA.VTR_LIMNUM is
'Limite tramitacion: número x intervalo';

comment on column STG_VERTRA.VTR_LIMINT is
'Limite tramitacion: minutos intervalo';

comment on column STG_VERTRA.VTR_DESPLZ is
'Indica si se activa un plazo de desactivación temporal';

comment on column STG_VERTRA.VTR_DESINI is
'Plazo inicio desactivación';

comment on column STG_VERTRA.VTR_DESFIN is
'Plazo fin desactivación';

comment on column STG_VERTRA.VTR_DESMEN is
'Mensaje desactivación';

alter table STG_VERTRA
   add constraint STG_VERTRA_PK primary key (VTR_CODIGO);

/*==============================================================*/
/* Index: STG_VERTRA_CODTRM_NUMVER_UK                           */
/*==============================================================*/
create unique index STG_VERTRA_CODTRM_NUMVER_UK on STG_VERTRA (
   VTR_CODTRM ASC,
   VTR_NUMVER ASC
);

alter table STG_ACCPER
   add constraint STG_ACCPER_FORMUL_FK foreign key (ACP_CODFOR)
      references STG_FORMUL (FOR_CODIGO);

alter table STG_ACCPER
   add constraint STG_ACCPER_TRADUC_FK foreign key (ACP_DESCRI)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_ANETRA
   add constraint STG_ANETRA_FICHER_FK foreign key (ANE_AYUFIC)
      references STG_FICHER (FIC_CODIGO);

alter table STG_ANETRA
   add constraint STG_ANETRA_PASANE_FK foreign key (ANE_CODPTR)
      references STG_PASANE (PAN_CODIGO);

alter table STG_ANETRA
   add constraint STG_ANETRA_SCRIPT_FK foreign key (ANE_SCROBL)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_ANETRA
   add constraint STG_ANETRA_SCRIPT_FK2 foreign key (ANE_SCRFIR)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_ANETRA
   add constraint STG_ANETRA_SCRIPT_FK3 foreign key (ANE_SCRVAL)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_ANETRA
   add constraint STG_ANETRA_TRADUC_FK foreign key (ANE_DESCRIP)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_ANETRA
   add constraint STG_ANETRA_TRADUC_FK2 foreign key (ANE_AYUTXT)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_AREA
   add constraint STG_AREA_ENTIDA_FK foreign key (ARE_CODENT)
      references STG_ENTIDA (ENT_CODIGO);

alter table STG_AREDOM
   add constraint STG_AREDOM_AREA_FK foreign key (DMA_CODARE)
      references STG_AREA (ARE_CODIGO);

alter table STG_AREDOM
   add constraint STG_AREDOM_DOMINI_FK foreign key (DMA_CODDOM)
      references STG_DOMINI (DOM_CODIGO);

alter table STG_AVIENT
   add constraint STG_AVIENT_ENTIDA_FK foreign key (AVI_CODENT)
      references STG_ENTIDA (ENT_CODIGO);

alter table STG_AVIENT
   add constraint STG_AVIENT_TRADUC_FK foreign key (AVI_TRAMEN)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_CAMFUE
   add constraint STG_CAMFUE_FUEDAT_FK foreign key (CFU_CODFUE)
      references STG_FUEDAT (FUE_CODIGO);

alter table STG_DOMENT
   add constraint STG_DOMENT_DOMINI_FK foreign key (DEN_CODDOM)
      references STG_DOMINI (DOM_CODIGO);

alter table STG_DOMENT
   add constraint STG_DOMENT_ENTIDA_FK foreign key (DEN_CODENT)
      references STG_ENTIDA (ENT_CODIGO);

alter table STG_DOMINI
   add constraint STG_DOMINI_FUEDAT_FK foreign key (DOM_FDIDFD)
      references STG_FUEDAT (FUE_CODIGO);

alter table STG_DOMVER
   add constraint STG_DOMVER_DOMINI_FK foreign key (DVT_CODDOM)
      references STG_DOMINI (DOM_CODIGO);

alter table STG_DOMVER
   add constraint STG_DOMVER_VERTRA_FK foreign key (DVT_CODVTR)
      references STG_VERTRA (VTR_CODIGO);

alter table STG_ENTIDA
   add constraint STG_ENTIDA_FICHER_FK foreign key (ENT_LOGOTG)
      references STG_FICHER (FIC_CODIGO);

alter table STG_ENTIDA
   add constraint STG_ENTIDA_FICHER_FK2 foreign key (ENT_LOGOTT)
      references STG_FICHER (FIC_CODIGO);

alter table STG_ENTIDA
   add constraint STG_ENTIDA_FICHER_FK3 foreign key (ENT_CSSTT)
      references STG_FICHER (FIC_CODIGO);

alter table STG_ENTIDA
   add constraint STG_ENTIDA_TRADUC_FK foreign key (ENT_NOMBRE)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_ENTIDA
   add constraint STG_ENTIDA_TRADUC_FK10 foreign key (ENT_PRSINS)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_ENTIDA
   add constraint STG_ENTIDA_TRADUC_FK2 foreign key (ENT_PIETT)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_ENTIDA
   add constraint STG_ENTIDA_TRADUC_FK3 foreign key (ENT_URLCAR)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_ENTIDA
   add constraint STG_ENTIDA_TRADUC_FK4 foreign key (ENT_LOPD)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_ENTIDA
   add constraint STG_ENTIDA_TRADUC_FK5 foreign key (ENT_MAPAW)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_ENTIDA
   add constraint STG_ENTIDA_TRADUC_FK6 foreign key (ENT_AVISOL)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_ENTIDA
   add constraint STG_ENTIDA_TRADUC_FK7 foreign key (ENT_RSS)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_ENTIDA
   add constraint STG_ENTIDA_TRADUC_FK8 foreign key (ENT_LOPDI)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_ENTIDA
   add constraint STG_ENTIDA_TRADUC_FK9 foreign key (ENT_SUBINS)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_FILFUE
   add constraint STG_FILFUE_FUEDAT_FK foreign key (FIF_CODFUE)
      references STG_FUEDAT (FUE_CODIGO);

alter table STG_FMTPLI
   add constraint STG_FMTPLI_FICHER_FK foreign key (PFI_CODFIC)
      references STG_FICHER (FIC_CODIGO);

alter table STG_FMTPLI
   add constraint STG_FMTPLI_FORFMT_FK foreign key (PFI_CODFMT)
      references STG_FORFMT (FMT_CODIGO);

alter table STG_FMTPLI
   add constraint STG_FMTPLI_IDIOMA_FK foreign key (PFI_CODIDI)
      references STG_IDIOMA (IDI_IDENTI);

alter table STG_FORCAM
   add constraint STG_FORCAM_FORELE_FK foreign key (FCA_CODIGO)
      references STG_FORELE (FEL_CODIGO);

alter table STG_FORCAM
   add constraint STG_FORCAM_SCRIPT_FK3 foreign key (FCA_SCRVAL)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_FORCAM
   add constraint STG_FORCAM_SCRIPT_FK foreign key (FCA_SCRAUT)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_FORCAM
   add constraint STG_FORCAM_SCRIPT_FK2 foreign key (FCA_SCRSLE)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_FORCHK
   add constraint STG_FORCHK_FORCAM_FK foreign key (CCK_CODIGO)
      references STG_FORCAM (FCA_CODIGO);

alter table STG_FORCIN
   add constraint STG_FORCIN_DOMINI_FK foreign key (CIN_DOMCOD)
      references STG_DOMINI (DOM_CODIGO);

alter table STG_FORCIN
   add constraint STG_FORCIN_FORCAM_FK foreign key (CIN_CODIGO)
      references STG_FORCAM (FCA_CODIGO);

alter table STG_FORCIN
   add constraint STG_FORCIN_SCRIPT_FK foreign key (CIN_SCRVAP)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_FORCTX
   add constraint STG_FORCTX_FORCAM_FK foreign key (CTX_CODIGO)
      references STG_FORCAM (FCA_CODIGO);

alter table STG_FORELE
   add constraint STG_FORELE_FORLI_FK foreign key (FEL_CODFLS)
      references STG_FORLI (FLS_CODIGO);

alter table STG_FORELE
   add constraint STG_FORELE_TRADUC_FK foreign key (FEL_TEXTO)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_FORELE
   add constraint STG_FORELE_TRADUC_FK2 foreign key (FEL_AYUDA)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_FORETQ
   add constraint STG_FORETQ_FORELE_FK foreign key (ETI_CODIGO)
      references STG_FORELE (FEL_CODIGO);

alter table STG_FORFMT
   add constraint STG_FORFMT_ENTIDA_FK foreign key (FMT_CODENT)
      references STG_ENTIDA (ENT_CODIGO);

alter table STG_FORIMG
   add constraint STG_FORIMG_FICHER_FK foreign key (FIM_CODFIC)
      references STG_FICHER (FIC_CODIGO);

alter table STG_FORIMG
   add constraint STG_FORIMG_FORELE_FK foreign key (FIM_CODIGO)
      references STG_FORELE (FEL_CODIGO);

alter table STG_FORLEL
   add constraint STG_FORLEL_FORELE_FK foreign key (LEL_CODIGO)
      references STG_FORELE (FEL_CODIGO);

alter table STG_FORLEL
   add constraint FK_STG_FORL_STG_FORLE_STG_FORP foreign key (LEL_CODPAF)
      references STG_FORPAG (PAF_CODIGO);

alter table STG_FORLI
   add constraint STG_FORLI_FORPAG_FK foreign key (FLS_CODPAF)
      references STG_FORPAG (PAF_CODIGO);

alter table STG_FORMUL
   add constraint STG_FORMUL_SCRIPT_FK foreign key (FOR_SCRPLT)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_FORMUL
   add constraint STG_FORMUL_TRADUC_FK foreign key (FOR_CABTXT)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_FORPAG
   add constraint STG_FORPAG_FORMUL_FK foreign key (PAF_CODFOR)
      references STG_FORMUL (FOR_CODIGO);

alter table STG_FORPAG
   add constraint STG_FORPAG_SCRIPT_FK foreign key (PAF_SCRVAL)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_FORPLI
   add constraint STG_FORPLI_FICHER_FK foreign key (PLI_CODFIC)
      references STG_FICHER (FIC_CODIGO);

alter table STG_FORPLI
   add constraint STG_FORPLI_FORPLT_FK foreign key (PLI_CODPLT)
      references STG_FORPLT (PLT_CODIGO);

alter table STG_FORPLI
   add constraint STG_FORPLI_IDIOMA_FK foreign key (PLI_CODIDI)
      references STG_IDIOMA (IDI_IDENTI);

alter table STG_FORPLT
   add constraint STG_FORPLT_FORFMT_FK foreign key (PLT_CODFMT)
      references STG_FORFMT (FMT_CODIGO);

alter table STG_FORPLT
   add constraint STG_FORPLT_FORMUL_FK foreign key (PLT_CODFOR)
      references STG_FORMUL (FOR_CODIGO);

alter table STG_FORSEC
   add constraint STG_FORSEC_FORELE_FK foreign key (FSE_CODIGO)
      references STG_FORELE (FEL_CODIGO);

alter table STG_FORSOP
   add constraint STG_FORSOP_ENTIDA_FK foreign key (FSO_CODENT)
      references STG_ENTIDA (ENT_CODIGO);

alter table STG_FORSOP
   add constraint STG_FORSOP_TRADUC_FK foreign key (FSO_TIPINC)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_FORSOP
   add constraint STG_FORSOP_TRADUC_FK2 foreign key (FSO_DSCTIP)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_FORTRA
   add constraint STG_FORTRA_FORMUL_FK foreign key (FTR_CODFOR)
      references STG_FORMUL (FOR_CODIGO);

alter table STG_FORTRA
   add constraint STG_FORTRA_GESFOR_FK foreign key (FTR_FEXGST)
      references STG_GESFOR (GFE_CODIGO);

alter table STG_FORTRA
   add constraint STG_FORTRA_SCRIPT_FK foreign key (FTR_SCROBL)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_FORTRA
   add constraint STG_FORTRA_SCRIPT_FK2 foreign key (FTR_SCRFIR)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_FORTRA
   add constraint STG_FORTRA_SCRIPT_FK3 foreign key (FTR_SCRINI)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_FORTRA
   add constraint STG_FORTRA_SCRIPT_FK4 foreign key (FTR_SCRPAR)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_FORTRA
   add constraint STG_FORTRA_SCRIPT_FK5 foreign key (FTR_SCRRET)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_FORTRA
   add constraint STG_FORTRA_TRADUC_FK foreign key (FTR_DESCRIP)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_FUEDAT
   add constraint STG_FUEDAT_AREA_FK foreign key (FUE_CODARE)
      references STG_AREA (ARE_CODIGO);

alter table STG_FUEDAT
   add constraint STG_FUEDAT_ENTIDA_FK foreign key (FUE_CODENT)
      references STG_ENTIDA (ENT_CODIGO);

alter table STG_GESFOR
   add constraint STG_GESFOR_ENTIDA_FK foreign key (GFE_CODENT)
      references STG_ENTIDA (ENT_CODIGO);

alter table STG_HISVER
   add constraint STG_HISVER_VERTRA_FK foreign key (HVE_CODVTR)
      references STG_VERTRA (VTR_CODIGO);

alter table STG_LFVCIN
   add constraint STG_FORCIN_LFVCIN_FK foreign key (LFV_CODCIN)
      references STG_FORCIN (CIN_CODIGO);

alter table STG_LFVCIN
   add constraint STG_LFVCIN_TRADUC_FK foreign key (LFV_DESCRIP)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_LITSCR
   add constraint STG_LITSCR_SCRIPT_FK foreign key (LSC_CODSCR)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_LITSCR
   add constraint STG_LITSCR_TRADUC_FK foreign key (LSC_CODTRA)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_PAGTRA
   add constraint STG_PAGTRA_PASPAG_FK foreign key (PAG_CODPTR)
      references STG_PASPAG (PPG_CODIGO);

alter table STG_PAGTRA
   add constraint STG_PAGTRA_SCRIPT_FK foreign key (PAG_SCROBL)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_PAGTRA
   add constraint STG_PAGTRA_SCRIPT_FK2 foreign key (PAG_SCRDPG)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_PAGTRA
   add constraint STG_PAGTRA_TRADUC_FK foreign key (PAG_DESCRIP)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_PASANE
   add constraint STG_PASANE_PASOTR_FK foreign key (PAN_CODIGO)
      references STG_PASOTR (PTR_CODIGO);

alter table STG_PASANE
   add constraint STG_PASANE_SCRIPT_FK foreign key (PAN_SCRDIN)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_PASCAP
   add constraint STG_PASCAP_FORTRA_FK foreign key (PCA_CODFOR)
      references STG_FORTRA (FTR_CODIGO);

alter table STG_PASCAP
   add constraint STG_PASCAP_PASOTR_FK foreign key (PCA_CODIGO)
      references STG_PASOTR (PTR_CODIGO);

alter table STG_PASDBS
   add constraint STG_PASDBS_PASOTR_FK foreign key (PDB_CODIGO)
      references STG_PASOTR (PTR_CODIGO);

alter table STG_PASDBS
   add constraint STG_PASDBS_TRADUC_FK foreign key (PDB_INSINI)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_PASINF
   add constraint STG_PASINF_FICHER_FK foreign key (PIN_FICPLA)
      references STG_FICHER (FIC_CODIGO);

alter table STG_PASINF
   add constraint STG_PASINF_PASOTR_FK foreign key (PIN_CODIGO)
      references STG_PASOTR (PTR_CODIGO);

alter table STG_PASINF
   add constraint STG_PASINF_SCRIPT_FK foreign key (PIN_SCRDAT)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_PASOTR
   add constraint STG_DOMVER_DOMINI_FK2 foreign key (PTR_SCRVAR)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_PASOTR
   add constraint STG_PASOTR_SCRIPT_FK foreign key (PTR_SCRNVG)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_PASOTR
   add constraint STG_PASOTR_TRADUC_FK foreign key (PTR_DESCRI)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_PASOTR
   add constraint STG_VERTRA_PASOTR_FK foreign key (PTR_CODVTR)
      references STG_VERTRA (VTR_CODIGO);

alter table STG_PASPAG
   add constraint STG_PASOTR_PASPAG_FK foreign key (PPG_CODIGO)
      references STG_PASOTR (PTR_CODIGO);

alter table STG_PASREG
   add constraint STG_PASOTR_PASREG_FK foreign key (PRG_CODIGO)
      references STG_PASOTR (PTR_CODIGO);

alter table STG_PASREG
   add constraint STG_PASREG_SCRIPT_FK foreign key (PRG_SCRREG)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_PASREG
   add constraint STG_PASREG_SCRIPT_FK2 foreign key (PRG_SCRPRE)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_PASREG
   add constraint STG_PASREG_SCRIPT_FK3 foreign key (PRG_SCRREP)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_PASREG
   add constraint STG_PASREG_SCRIPT_FK4 foreign key (PRG_SCRVAL)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_PASREG
   add constraint STG_PASREG_TRADUC_FK foreign key (PRG_INSPRE)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_PASREG
   add constraint STG_PASREG_TRADUC_FK2 foreign key (PRG_INSFIT)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_PASREG
   add constraint STG_PASREG_TRADUC_FK3 foreign key (PRG_INSSUB)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_PASREL
   add constraint STG_PASREL_PRLFTR_FK foreign key (PRL_CODIGO)
      references STG_PASOTR (PTR_CODIGO);

alter table STG_PLUGIN
   add constraint STG_PLUGIN_ENTIDA_FK foreign key (PLG_CODENT)
      references STG_ENTIDA (ENT_CODIGO);

alter table STG_PRDCIN
   add constraint STG_FORCIN_PRDCIN_FK foreign key (CIP_CODCIN)
      references STG_FORCIN (CIN_CODIGO);

alter table STG_PRLFTR
   add constraint STG_PFLFTR_FORTRA_FK foreign key (FPR_CODFOR)
      references STG_FORTRA (FTR_CODIGO);

alter table STG_PRLFTR
   add constraint STG_PRLFTR_PASREL_FK foreign key (FPR_CODPRL)
      references STG_PASREL (PRL_CODIGO);

alter table STG_ROLARE
   add constraint STG_ROLARE_AREA_FK foreign key (RLA_CODARE)
      references STG_AREA (ARE_CODIGO);

alter table STG_TRAIDI
   add constraint STG_TRAIDI_IDIOMA_FK foreign key (TRI_IDIOMA)
      references STG_IDIOMA (IDI_IDENTI);

alter table STG_TRAIDI
   add constraint STG_TRAIDI_TRADUC_FK foreign key (TRI_CODTRA)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_TRAMIT
   add constraint STG_TRAMIT_AREA_FK foreign key (TRM_CODARE)
      references STG_AREA (ARE_CODIGO);

alter table STG_VALCFU
   add constraint STG_VALCFU_CAMFUE_FK foreign key (VCF_CODCFU)
      references STG_CAMFUE (CFU_CODIGO);

alter table STG_VALCFU
   add constraint STG_VALCFU_FILFUE_FK foreign key (VCF_CODFIF)
      references STG_FILFUE (FIF_CODIGO);

alter table STG_VALORA
   add constraint STG_VALORA_ENTIDA_FK foreign key (VAT_CODENT)
      references STG_ENTIDA (ENT_CODIGO);

alter table STG_VALORA
   add constraint STG_VALORA_TRADUC_FK foreign key (VAT_DESCRI)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_VERTRA
   add constraint STG_VERTRA_SCRIPT_FK foreign key (VTR_SCRPER)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_VERTRA
   add constraint STG_VERTRA_SCRIPT_FK2 foreign key (VTR_SCRINTRA)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_VERTRA
   add constraint STG_VERTRA_TRADUC_FK2 foreign key (VTR_DESMEN)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_VERTRA
   add constraint STG_VERTRA_TRAMIT_FK foreign key (VTR_CODTRM)
      references STG_TRAMIT (TRM_CODIGO);

