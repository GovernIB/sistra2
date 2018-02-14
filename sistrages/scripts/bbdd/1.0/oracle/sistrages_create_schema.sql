/* V 1.0.0 */

create sequence STG_ACCPER_SEQ;

create sequence STG_ANETRA_SEQ;

create sequence STG_AREA_SEQ;

create sequence STG_AVENT_SEQ;

create sequence STG_CAMFUE_SEQ;

create sequence STG_CNFGLO_SEQ;

create sequence STG_DOMINI_SEQ;

create sequence STG_ENTIDA_SEQ;

create sequence STG_FICHER_SEQ;

create sequence STG_FILFUE_SEQ;

create sequence STG_FORELE_SEQ;

create sequence STG_FORFMT_SEQ;

create sequence STG_FORLI_SEQ;

create sequence STG_FORMUL_SEQ;

create sequence STG_FORPAG_SEQ;

create sequence STG_FORPLI_SEQ;

create sequence STG_FORPLT_SEQ;

create sequence STG_FORSEC_SEQ;

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

create sequence STG_VERTRA_SEQ;

/*==============================================================*/
/* Table: STG_ACCPER                                            */
/*==============================================================*/
create table STG_ACCPER 
(
   ACP_CODIGO           NUMBER(20)           not null,
   ACP_CODFOR           NUMBER(20)           not null,
   ACP_ACCION           VARCHAR2(20)         not null,
   ACP_DESCRI           NUMBER(20)           not null,
   ACP_VALIDA           NUMBER(1)            default 0 not null
);

comment on table STG_ACCPER is
'Acciones personalizadas del formulario';

comment on column STG_ACCPER.ACP_CODIGO is
'Codigo de la acci�n';

comment on column STG_ACCPER.ACP_CODFOR is
'Formulario al que pertenece la acci�n';

comment on column STG_ACCPER.ACP_ACCION is
'Acci�n a realizar. �nica en el formulario';

comment on column STG_ACCPER.ACP_DESCRI is
'T�tulo de la acci�n';

comment on column STG_ACCPER.ACP_VALIDA is
'La acci�n hace que se valide el formulario';

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
   ANE_CODIGO           NUMBER(20)           not null,
   ANE_CODPTR           NUMBER(20)           not null,
   ANE_IDEDOC           VARCHAR2(20)         not null,
   ANE_DESCRIP          NUMBER(20)           not null,
   ANE_ORDEN            NUMBER(2)            not null,
   ANE_OBLIGA           VARCHAR2(1 CHAR)     not null,
   ANE_SCROBL           NUMBER(20),
   ANE_AYUTXT           NUMBER(20),
   ANE_AYUFIC           NUMBER(20),
   ANE_AYUULR           VARCHAR2(250 CHAR),
   ANE_TIPPRE           VARCHAR2(1 CHAR)     not null,
   ANE_NUMINS           NUMBER(2)            not null,
   ANE_EXTPER           VARCHAR2(1000 CHAR)  not null,
   ANE_TAMMAX           NUMBER(4)            not null,
   ANE_TAMUNI           VARCHAR2(2)          default 'KB' not null,
   ANE_CNVPDF           NUMBER(1)            not null,
   ANE_FIRMAR           NUMBER(1)            default 0 not null,
   ANE_SCRFIR           NUMBER(20),
   ANE_FIRMAD           NUMBER(1)            default 0 not null,
   ANE_SCRVAL           NUMBER(20),
   ANE_COMPUL           NUMBER(1)            default 0 not null,
   ANE_FOTOCP           NUMBER(1)            default 0 not null
);

comment on table STG_ANETRA is
'Anexo tr�mite';

comment on column STG_ANETRA.ANE_CODIGO is
'C�digo anexo';

comment on column STG_ANETRA.ANE_CODPTR is
'C�digo paso tramitaci�n';

comment on column STG_ANETRA.ANE_IDEDOC is
'Identificador documento dentro de la versi�n';

comment on column STG_ANETRA.ANE_DESCRIP is
'Descripci�n anexo';

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
'Tipo presentaci�n: E (electr�nica) / P (Presencial)';

comment on column STG_ANETRA.ANE_NUMINS is
'N�mero instancia (por defecto 1, para multiinstancia > 1)';

comment on column STG_ANETRA.ANE_EXTPER is
'Lista extensiones permitidas separadas por coma';

comment on column STG_ANETRA.ANE_TAMMAX is
'Tama�o m�ximo (segun unidad tama�o)';

comment on column STG_ANETRA.ANE_TAMUNI is
'Unidad de tama�o del anexo (KB o MB)';

comment on column STG_ANETRA.ANE_CNVPDF is
'Indica si hay que convertir a PDF el anexo';

comment on column STG_ANETRA.ANE_FIRMAR is
'Indica si se debe firmar digitalmente ';

comment on column STG_ANETRA.ANE_SCRFIR is
'Permite indicar qui�n debe firmar el anexo (permite indicar varios firmantes). 
Si se habilita el pase a bandeja de firma se podr� especificar si el anexo debe ser anexado por uno de los firmantes.';

comment on column STG_ANETRA.ANE_FIRMAD is
'Indica si se debe anexar firmado';

comment on column STG_ANETRA.ANE_SCRVAL is
'Permite establecer una validaci�n sobre el documento anexado. En este script estar� disponible un plugin que permita acceder a datos de formularios PDF.';

comment on column STG_ANETRA.ANE_COMPUL is
'Compulsar (presencial)';

comment on column STG_ANETRA.ANE_FOTOCP is
'Fotocopia (presencial)';

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
   ARE_CODIGO           NUMBER(20)           not null,
   ARE_CODENT           NUMBER(20)           not null,
   ARE_IDENTI           VARCHAR2(20)         not null,
   ARE_DESCR            VARCHAR2(255 CHAR)   not null
);

comment on table STG_AREA is
'Areas funcionales';

comment on column STG_AREA.ARE_CODIGO is
'C�digo';

comment on column STG_AREA.ARE_CODENT is
'C�digo entidad';

comment on column STG_AREA.ARE_IDENTI is
'Identificador';

comment on column STG_AREA.ARE_DESCR is
'Descripci�n';

alter table STG_AREA
   add constraint STG_AREA_PK primary key (ARE_CODIGO);

/*==============================================================*/
/* Index: STG_AREA_IDENTI_UK                                    */
/*==============================================================*/
create unique index STG_AREA_IDENTI_UK on STG_AREA (
   ARE_IDENTI ASC
);

/*==============================================================*/
/* Table: STG_AVIENT                                            */
/*==============================================================*/
create table STG_AVIENT 
(
   AVI_CODIGO           NUMBER(20)           not null,
   AVI_CODENT           NUMBER(20)           not null,
   AVI_TRAMEN           NUMBER(20)           not null,
   AVI_TIPO             VARCHAR2(3 CHAR)     not null,
   AVI_BLOQ             NUMBER(1)            default 0 not null,
   AVI_FCINI            DATE,
   AVI_FCFIN            DATE,
   AVI_LSTTRA           VARCHAR2(1000 CHAR)
);

comment on table STG_AVIENT is
'Avisos entidad (para el asistente de tramitaci�n)';

comment on column STG_AVIENT.AVI_CODIGO is
'C�digo';

comment on column STG_AVIENT.AVI_CODENT is
'C�digo entidad';

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
  - ORG: Por organismo
  - LST: Lista tr�mites ';

comment on column STG_AVIENT.AVI_BLOQ is
'Si se bloquea acceso al tr�mite';

comment on column STG_AVIENT.AVI_FCINI is
'Fecha inicio';

comment on column STG_AVIENT.AVI_FCFIN is
'Fecha fin';

comment on column STG_AVIENT.AVI_LSTTRA is
'Lista serializada con c�digos de tr�mites ';

alter table STG_AVIENT
   add constraint STG_AVIENT_PK primary key (AVI_CODIGO);

/*==============================================================*/
/* Table: STG_CAMFUE                                            */
/*==============================================================*/
create table STG_CAMFUE 
(
   CFU_CODIGO           NUMBER(20)           not null,
   CFU_CODFUE           NUMBER(20)           not null,
   CFU_IDENT            VARCHAR2(20)         not null,
   CFU_ESPK             VARCHAR2(1)          default 'N' not null
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
   CFG_CODIGO           NUMBER(20)           not null,
   CFG_PROP             VARCHAR2(100 CHAR)   not null,
   CFG_VALOR            VARCHAR2(500 CHAR),
   CFG_DESCR            VARCHAR2(255 CHAR)   not null
);

comment on table STG_CNFGLO is
'Propiedades configuraci�n global';

comment on column STG_CNFGLO.CFG_CODIGO is
'C�digo';

comment on column STG_CNFGLO.CFG_PROP is
'Propiedad';

comment on column STG_CNFGLO.CFG_VALOR is
'Valor';

comment on column STG_CNFGLO.CFG_DESCR is
'Descripci�n propiedad';

alter table STG_CNFGLO
   add constraint STG_CNFGLO_PK primary key (CFG_CODIGO);

/*==============================================================*/
/* Table: STG_DOMENT                                            */
/*==============================================================*/
create table STG_DOMENT 
(
   DEN_CODENT           NUMBER(20)           not null,
   DEN_CODDOM           NUMBER(20)           not null
);

comment on table STG_DOMENT is
'Dominios entidad';

comment on column STG_DOMENT.DEN_CODENT is
'C�digo entidad';

comment on column STG_DOMENT.DEN_CODDOM is
'C�digo dominio';

alter table STG_DOMENT
   add constraint STG_DOMENT_PK primary key (DEN_CODENT, DEN_CODDOM);

/*==============================================================*/
/* Table: STG_DOMINI                                            */
/*==============================================================*/
create table STG_DOMINI 
(
   DOM_CODIGO           NUMBER(20)           not null,
   DOM_AMBITO           VARCHAR2(1 CHAR)     not null,
   DOM_IDENTI           VARCHAR2(20)         not null,
   DOM_DESCR            VARCHAR2(255 CHAR)   not null,
   DOM_CACHE            NUMBER(1)            default 0 not null,
   DOM_TIPO             VARCHAR2(1 CHAR)     not null,
   DOM_BDJNDI           VARCHAR2(500 CHAR),
   DOM_BDSQL            VARCHAR2(2000 CHAR),
   DOM_FDIDFD           NUMBER(20),
   DOM_LFVALS           VARCHAR2(4000 CHAR),
   DOM_REURL            VARCHAR2(500 CHAR),
   DOM_PARAMS           VARCHAR2(4000 CHAR)
);

comment on table STG_DOMINI is
'Dominios';

comment on column STG_DOMINI.DOM_CODIGO is
'C�digo interno';

comment on column STG_DOMINI.DOM_AMBITO is
'Ambito dominio (G : Global / E: Entidad / A: �rea)';

comment on column STG_DOMINI.DOM_IDENTI is
'Identificador dominio';

comment on column STG_DOMINI.DOM_DESCR is
'Descripci�n';

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
'Lista serializada de c�digos par�metros dominio ';

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
   DVT_CODVTR           NUMBER(20)           not null,
   DVT_CODDOM           NUMBER(20)           not null
);

comment on table STG_DOMVER is
'Dominios utilizados en una versi�n de  tr�mite';

comment on column STG_DOMVER.DVT_CODVTR is
'C�digo versi�n tr�mite';

comment on column STG_DOMVER.DVT_CODDOM is
'C�digo dominio';

alter table STG_DOMVER
   add constraint STG_DOMVER_PK primary key (DVT_CODVTR, DVT_CODDOM);

/*==============================================================*/
/* Table: STG_ENTIDA                                            */
/*==============================================================*/
create table STG_ENTIDA 
(
   ENT_CODIGO           NUMBER(20)           not null,
   ENT_DIR3             VARCHAR2(10)         not null,
   ENT_NOMBRE           NUMBER(20)           not null,
   ENT_ACTIVA           NUMBER(1)            default 0 not null,
   ENT_ROLADM           VARCHAR2(100 CHAR)   not null,
   ENT_LOGOTG           NUMBER(20),
   ENT_LOGOTT           NUMBER(20),
   ENT_CSSTT            NUMBER(20),
   ENT_PIETT            NUMBER(20),
   ENT_EMAIL            VARCHAR2(500 CHAR),
   ENT_CNTEMA           NUMBER(1)            default 0 not null,
   ENT_CNTTEL           NUMBER(1)            default 0 not null,
   ENT_CNTURL           NUMBER(1)            default 0 not null,
   ENT_CNTFOR           NUMBER(1)            default 0 not null,
   ENT_TELEFO           VARCHAR2(10 CHAR),
   ENT_URLSOP           VARCHAR2(500 CHAR)
);

comment on table STG_ENTIDA is
'Entidades';

comment on column STG_ENTIDA.ENT_CODIGO is
'C�digo interno';

comment on column STG_ENTIDA.ENT_DIR3 is
'C�digo DIR3';

comment on column STG_ENTIDA.ENT_NOMBRE is
'Nombre Entidad';

comment on column STG_ENTIDA.ENT_ACTIVA is
'Indica si la entidad est� activa';

comment on column STG_ENTIDA.ENT_ROLADM is
'Role asociado al administrador de la entidad';

comment on column STG_ENTIDA.ENT_LOGOTG is
'Logo entidad Gestor Tr�mites';

comment on column STG_ENTIDA.ENT_LOGOTT is
'Logo entidad Asistente Tramitaci�n';

comment on column STG_ENTIDA.ENT_CSSTT is
'CSS Asistente Tramitaci�n';

comment on column STG_ENTIDA.ENT_PIETT is
'Pie de p�gina de contacto para Asistente Tramitaci�n (HTML)';

comment on column STG_ENTIDA.ENT_EMAIL is
'Email contacto gen�rico';

comment on column STG_ENTIDA.ENT_CNTEMA is
'Habilitado contacto email';

comment on column STG_ENTIDA.ENT_CNTTEL is
'Habilitado contacto tel�fono';

comment on column STG_ENTIDA.ENT_CNTURL is
'Habilitado contacto url';

comment on column STG_ENTIDA.ENT_CNTFOR is
'Habilitado contacto formulario incidencias';

comment on column STG_ENTIDA.ENT_TELEFO is
'Tel�fono contacto';

comment on column STG_ENTIDA.ENT_URLSOP is
'Url soporte';

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
   FIE_REFDOC           VARCHAR2(1000)       not null,
   FIE_REFFEC           TIMESTAMP            not null,
   FIE_CODFIC           NUMBER(20)           not null,
   FIE_BORRAR           NUMBER(1)            default 0 not null
);

comment on table STG_FICEXT is
'Ubicaci�n de ficheros en sistema remoto

No se activa FK para que al borrar un fichero se marquen para borrar todos sus referencias externas

POR DETERMINAR SI SE ALMACENA EN SISTEMA DE FICHEROS


';

comment on column STG_FICEXT.FIE_REFDOC is
'Referencia externa (path fichero)

REFERENCIA DOC:  Path 
/ENTIDAD-<codigo>
        /ESTILOS: docs asociados a estilos entidad (logo, css, ...)
        /TRAMITE-<codigo>/VERSION-<codigo>: docs asociados a esa versi�n de tr�mite, formularios, etc.

    Nombre de fichero: id generado automaticamente
';

comment on column STG_FICEXT.FIE_REFFEC is
'Fecha referencia (s�lo ser� v�lida la �ltima, el resto se borrar�n)';

comment on column STG_FICEXT.FIE_CODFIC is
'C�digo fichero al que est� enlazado';

comment on column STG_FICEXT.FIE_BORRAR is
'Indica si esta marcado para borrar (proceso de purgado)';

alter table STG_FICEXT
   add constraint STG_FICEXT_PK primary key (FIE_REFDOC);

/*==============================================================*/
/* Table: STG_FICHER                                            */
/*==============================================================*/
create table STG_FICHER 
(
   FIC_CODIGO           NUMBER(20)           not null,
   FIC_NOMBRE           VARCHAR2(500 CHAR)   not null
);

comment on table STG_FICHER is
'Ficheros';

comment on column STG_FICHER.FIC_CODIGO is
'C�digo fichero';

comment on column STG_FICHER.FIC_NOMBRE is
'Nombre fichero con extensi�n';

alter table STG_FICHER
   add constraint STG_FICHER_PK primary key (FIC_CODIGO);

/*==============================================================*/
/* Table: STG_FILFUE                                            */
/*==============================================================*/
create table STG_FILFUE 
(
   FIF_CODIGO           NUMBER(20)           not null,
   FIF_CODFUE           NUMBER(20)           not null
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
/* Table: STG_FORCAM                                            */
/*==============================================================*/
create table STG_FORCAM 
(
   FCA_CODIGO           NUMBER(20)           not null,
   FCA_OBLIGA           NUMBER(1)            default 0 not null,
   FCA_LECTUR           NUMBER(1)            default 0 not null,
   FCA_NOMODI           NUMBER(1)            default 0 not null,
   FCA_SCRAUT           NUMBER(20),
   FCA_SCRSLE           NUMBER(20),
   FCA_SCRVAL           NUMBER(20)
);

comment on table STG_FORCAM is
'Campo formulario';

comment on column STG_FORCAM.FCA_CODIGO is
'C�digo';

comment on column STG_FORCAM.FCA_OBLIGA is
'Obligatorio';

comment on column STG_FORCAM.FCA_LECTUR is
'S�lo lectura';

comment on column STG_FORCAM.FCA_NOMODI is
'No modificable';

comment on column STG_FORCAM.FCA_SCRAUT is
'Permite establecer el valor de un campo en funci�n de otros';

comment on column STG_FORCAM.FCA_SCRSLE is
'Permite indicar si un campo es de solo lectura
';

comment on column STG_FORCAM.FCA_SCRVAL is
'Permite establecer validaciones personalizadas sobre un campo permitiendo mostrar un mensaje particularizado de error. Este script se ejecutar� al guardar la p�gina.';

alter table STG_FORCAM
   add constraint STG_FORCAM_PK primary key (FCA_CODIGO);

/*==============================================================*/
/* Table: STG_FORCHK                                            */
/*==============================================================*/
create table STG_FORCHK 
(
   CCK_CODIGO           NUMBER(20)           not null,
   CCK_VALCHK           VARCHAR2(100)        not null,
   CCK_VALNCH           VARCHAR2(100)        not null
);

comment on table STG_FORCHK is
'Campo formulario casilla verificaci�n';

comment on column STG_FORCHK.CCK_CODIGO is
'C�digo elemento';

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
   CIN_CODIGO           NUMBER(20)           not null,
   CIN_TIPO             VARCHAR2(10 CHAR)    not null,
   CIN_TIPLST           VARCHAR2(1 CHAR)     not null,
   CIN_SCRVAP           NUMBER(20),
   CIN_DOMCOD           NUMBER(20),
   CIN_DOMCCD           VARCHAR2(100 CHAR),
   CIN_DOMCDS           VARCHAR2(100 CHAR),
   CIN_INDICE           NUMBER(1)            default 0 not null
);

comment on table STG_FORCIN is
'Campo formulario indexado';

comment on column STG_FORCIN.CIN_CODIGO is
'C�digo elemento';

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
'Campo del dominio que se utilizar� como codigo';

comment on column STG_FORCIN.CIN_DOMCDS is
'Campo del dominio que se utilizar� como descripci�n';

comment on column STG_FORCIN.CIN_INDICE is
'Indica si se tiene que mostrar un �ndice alfab�tico en los campos de selecci�n.
';

alter table STG_FORCIN
   add constraint STG_FORCIN_PK primary key (CIN_CODIGO);

/*==============================================================*/
/* Table: STG_FORCTX                                            */
/*==============================================================*/
create table STG_FORCTX 
(
   CTX_CODIGO           NUMBER(20)           not null,
   CTX_TIPO             VARCHAR2(10 CHAR)    not null,
   CTX_NORTAM           NUMBER(4),
   CTX_NORMUL           NUMBER(1)            default 0 not null,
   CTX_NORLIN           NUMBER(3),
   CTX_NOREXP           VARCHAR2(4000 CHAR),
   CTX_NUMENT           NUMBER(2),
   CTX_NUMDEC           NUMBER(1),
   CTX_NUMSEP           VARCHAR2(2 CHAR),
   CTX_NUMRMI           NUMBER(10),
   CTX_NUMRMX           NUMBER(10),
   CTX_NUMSIG           NUMBER(1)            default 0 not null,
   CTX_IDENIF           NUMBER(1)            default 0 not null,
   CTX_IDECIF           NUMBER(1)            default 0 not null,
   CTX_IDENIE           NUMBER(1)            default 0 not null,
   CTX_IDENSS           NUMBER(1)            default 0 not null,
   CTX_TELMOV           NUMBER(1)            default 0 not null,
   CTX_TELFIJ           NUMBER(1)            default 0 not null,
   CTX_PERRAN           NUMBER(1)            default 0 not null
);

comment on table STG_FORCTX is
'Campo formulario texto';

comment on column STG_FORCTX.CTX_CODIGO is
'C�digo elemento';

comment on column STG_FORCTX.CTX_TIPO is
'Tipo campo de texto;
- NORMAL
- NUMERO
- EMAIL
- ID
- CP
- TELEFONO
- FECHA
- EXPR';

comment on column STG_FORCTX.CTX_NORTAM is
'Tama�o (tipo normal)';

comment on column STG_FORCTX.CTX_NORMUL is
'Multilinea (tipo normal)';

comment on column STG_FORCTX.CTX_NORLIN is
'N�mero l�neas (tipo normal)';

comment on column STG_FORCTX.CTX_NOREXP is
'Expresi�n regular (tipo normal)';

comment on column STG_FORCTX.CTX_NUMENT is
'N�mero d�gitos enteros (tipo numero)';

comment on column STG_FORCTX.CTX_NUMDEC is
'N�mero d�gitos decimales (tipo numero)';

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
'Indica si se admiten n�meros con signo (tipo numero)';

comment on column STG_FORCTX.CTX_IDENIF is
'Permite nif (tipo identificaci�n)';

comment on column STG_FORCTX.CTX_IDECIF is
'Permite cif (tipo identificaci�n)';

comment on column STG_FORCTX.CTX_IDENIE is
'Permite nie (tipo identificaci�n)';

comment on column STG_FORCTX.CTX_IDENSS is
'Permite nss (tipo identificaci�n)';

comment on column STG_FORCTX.CTX_TELMOV is
'Permite m�vil (tipo telefono)';

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
   FEL_CODIGO           NUMBER(20)           not null,
   FEL_CODFLS           NUMBER(20)           not null,
   FEL_IDENTI           VARCHAR2(50 CHAR)    not null,
   FEL_TIPO             VARCHAR2(2 CHAR)     not null,
   FEL_ORDEN            NUMBER(3)            not null,
   FEL_NUMCOL           NUMBER(2)            not null,
   FEL_TEXTO            NUMBER(20),
   FEL_AYUDA            NUMBER(20)
);

comment on table STG_FORELE is
'Elemento formulario';

comment on column STG_FORELE.FEL_CODIGO is
'C�digo';

comment on column STG_FORELE.FEL_CODFLS is
'C�digo fila';

comment on column STG_FORELE.FEL_IDENTI is
'Identificador elemento';

comment on column STG_FORELE.FEL_TIPO is
'Tipo elemento: CT (Campo de texto) / SE (Selector) / CK (Checkbox) / ET (Etiqueta) / CP (Captcha) / IM (Imagen)';

comment on column STG_FORELE.FEL_ORDEN is
'Orden';

comment on column STG_FORELE.FEL_NUMCOL is
'N�mero de columnas ocupadas';

comment on column STG_FORELE.FEL_TEXTO is
'Texto';

comment on column STG_FORELE.FEL_AYUDA is
'Texto tooltip ayuda';

alter table STG_FORELE
   add constraint STG_FORELE_PK primary key (FEL_CODIGO);

/*==============================================================*/
/* Table: STG_FORFMT                                            */
/*==============================================================*/
create table STG_FORFMT 
(
   FMT_CODIGO           NUMBER(20)           not null,
   FMT_CLASS            VARCHAR2(500 CHAR)   not null,
   FMT_DESCRI           VARCHAR2(255 CHAR)   not null
);

comment on table STG_FORFMT is
'Formateadores formulario';

comment on column STG_FORFMT.FMT_CODIGO is
'C�digo';

comment on column STG_FORFMT.FMT_CLASS is
'Classname del formateador';

comment on column STG_FORFMT.FMT_DESCRI is
'Descripci�n del formateador';

alter table STG_FORFMT
   add constraint STG_FORFMT_PK primary key (FMT_CODIGO);

/*==============================================================*/
/* Table: STG_FORIMG                                            */
/*==============================================================*/
create table STG_FORIMG 
(
   FIM_CODIGO           NUMBER(20)           not null,
   FIM_CODFIC           NUMBER(20)           not null
);

comment on table STG_FORIMG is
'Imagen formulario';

comment on column STG_FORIMG.FIM_CODIGO is
'C�digo';

comment on column STG_FORIMG.FIM_CODFIC is
'Fichero imagen';

alter table STG_FORIMG
   add constraint STG_FORIMG_PK primary key (FIM_CODIGO);

/*==============================================================*/
/* Table: STG_FORLI                                             */
/*==============================================================*/
create table STG_FORLI 
(
   FLS_CODIGO           NUMBER(20)           not null,
   FLS_CODFSE           NUMBER(20)           not null,
   FLS_ORDEN            NUMBER(2)            not null
);

comment on table STG_FORLI is
'Linea secci�n formulario';

comment on column STG_FORLI.FLS_CODIGO is
'C�digo';

comment on column STG_FORLI.FLS_CODFSE is
'C�digo secci�n';

comment on column STG_FORLI.FLS_ORDEN is
'Orden';

alter table STG_FORLI
   add constraint STG_FORLI_PK primary key (FLS_CODIGO);

/*==============================================================*/
/* Table: STG_FORMUL                                            */
/*==============================================================*/
create table STG_FORMUL 
(
   FOR_CODIGO           NUMBER(20)           not null,
   GTTFORGST_ACCPER     NUMBER(1)            default 0 not null,
   FOR_SCRPLT           NUMBER(20)
);

comment on table STG_FORMUL is
'Formularios internos';

comment on column STG_FORMUL.FOR_CODIGO is
'C�digo';

comment on column STG_FORMUL.GTTFORGST_ACCPER is
'Permitir acciones personalizadas';

comment on column STG_FORMUL.FOR_SCRPLT is
'Script para permitir indicar que plantilla usar';

alter table STG_FORMUL
   add constraint STG_FORMUL_PK primary key (FOR_CODIGO);

/*==============================================================*/
/* Table: STG_FORPAG                                            */
/*==============================================================*/
create table STG_FORPAG 
(
   PAF_CODIGO           NUMBER(20)           not null,
   PAF_CODFOR           NUMBER(20)           not null,
   PAF_SCRVAL           NUMBER(20),
   PAF_ORDEN            NUMBER(2)            not null,
   PAF_CABFOR           NUMBER(1)            default 0 not null,
   PAF_CABLOG           NUMBER(20),
   PAF_CABTXT           NUMBER(20)
);

comment on table STG_FORPAG is
'P�gina formulario';

comment on column STG_FORPAG.PAF_CODIGO is
'C�digo';

comment on column STG_FORPAG.PAF_CODFOR is
'C�digo formulario';

comment on column STG_FORPAG.PAF_SCRVAL is
'Script de validaci�n (permite establecer siguiente p�gina)';

comment on column STG_FORPAG.PAF_ORDEN is
'Orden';

comment on column STG_FORPAG.PAF_CABFOR is
'Indica si se establece cabecera formulario (logo + t�tulo)';

comment on column STG_FORPAG.PAF_CABLOG is
'Logo cabecera';

comment on column STG_FORPAG.PAF_CABTXT is
'Texto cabecera';

alter table STG_FORPAG
   add constraint STG_FORPAG_PK primary key (PAF_CODIGO);

/*==============================================================*/
/* Table: STG_FORPLI                                            */
/*==============================================================*/
create table STG_FORPLI 
(
   PLI_CODIGO           NUMBER(20)           not null,
   PLI_CODPLT           NUMBER(20)           not null,
   PLI_CODIDI           VARCHAR2(2 CHAR)     not null,
   PLI_CODFIC           NUMBER(20)           not null
);

comment on table STG_FORPLI is
'Plantilla idioma formulario';

comment on column STG_FORPLI.PLI_CODIGO is
'C�digo';

comment on column STG_FORPLI.PLI_CODPLT is
'C�digo plantilla';

comment on column STG_FORPLI.PLI_CODIDI is
'Identificador idioma';

comment on column STG_FORPLI.PLI_CODFIC is
'C�digo fichero';

alter table STG_FORPLI
   add constraint STG_FORPLI_PK primary key (PLI_CODIGO);

/*==============================================================*/
/* Table: STG_FORPLT                                            */
/*==============================================================*/
create table STG_FORPLT 
(
   PLT_CODIGO           NUMBER(20)           not null,
   PLT_CODFOR           NUMBER(20)           not null,
   PLT_CODFMT           NUMBER(20)           not null,
   PLT_DESCRI           VARCHAR2(255 CHAR)   not null,
   PLT_DEFECT           NUMBER(1)            default 0 not null
);

comment on table STG_FORPLT is
'Plantillas formulario';

comment on column STG_FORPLT.PLT_CODIGO is
'C�digo';

comment on column STG_FORPLT.PLT_CODFOR is
'C�digo formulario';

comment on column STG_FORPLT.PLT_CODFMT is
'C�digo formateador';

comment on column STG_FORPLT.PLT_DESCRI is
'Descripci�n';

comment on column STG_FORPLT.PLT_DEFECT is
'Plantilla por defecto';

alter table STG_FORPLT
   add constraint STG_FORPLT_PK primary key (PLT_CODIGO);

/*==============================================================*/
/* Table: STG_FORSEC                                            */
/*==============================================================*/
create table STG_FORSEC 
(
   FSE_CODIGO           NUMBER(20)           not null,
   FSE_CODPAF           NUMBER(20)           not null,
   FSE_ORDEN            NUMBER(2)            not null,
   FSE_TITULO           NUMBER(20)           not null
);

comment on table STG_FORSEC is
'Secciones formulario';

comment on column STG_FORSEC.FSE_CODIGO is
'C�digo';

comment on column STG_FORSEC.FSE_CODPAF is
'C�digo p�gina';

comment on column STG_FORSEC.FSE_ORDEN is
'Orden';

comment on column STG_FORSEC.FSE_TITULO is
'Titulo secci�n';

alter table STG_FORSEC
   add constraint STG_FORSEC_PK primary key (FSE_CODIGO);

/*==============================================================*/
/* Table: STG_FORSOP                                            */
/*==============================================================*/
create table STG_FORSOP 
(
   FSO_CODIGO           NUMBER(20)           not null,
   FSO_CODENT           NUMBER(20)           not null,
   FSO_TIPINC           NUMBER(20)           not null,
   FSO_DSCTIP           NUMBER(20)           not null,
   FSO_TIPDST           VARCHAR2(1 CHAR)     not null,
   FSO_LSTEMA           VARCHAR2(4000 CHAR)
);

comment on table STG_FORSOP is
'Formulario soporte para incidencias';

comment on column STG_FORSOP.FSO_CODIGO is
'C�digo interno';

comment on column STG_FORSOP.FSO_CODENT is
'C�digo interno';

comment on column STG_FORSOP.FSO_TIPINC is
'Literal tipo incidencia';

comment on column STG_FORSOP.FSO_DSCTIP is
'Literal descripci�n tipo incidencia';

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
   FTR_CODIGO           NUMBER(20)           not null,
   FTR_IDENTI           VARCHAR2(20)         not null,
   FTR_DESCRIP          NUMBER(20)           not null,
   FTR_TIPO             VARCHAR2(1)          not null,
   FTR_ORDEN            NUMBER(2)            not null,
   FTR_OBLIGA           VARCHAR2(1 CHAR)     not null,
   FTR_SCROBL           NUMBER(20),
   FTR_FIRDIG           NUMBER(1)            default 0,
   FTR_SCRFIR           NUMBER(20),
   FTR_PREREG           NUMBER(1)            default 0,
   FTR_SCRINI           NUMBER(20),
   FTR_SCRPAR           NUMBER(20),
   FTR_SCRRET           NUMBER(20),
   FTR_TIPFOR           VARCHAR2(1 CHAR)     not null,
   FTR_CODFOR           NUMBER(20),
   FTR_FEXGST           NUMBER(20),
   FTR_FEXIDE           VARCHAR2(20)
);

comment on table STG_FORTRA is
'Formulario tr�mite';

comment on column STG_FORTRA.FTR_CODIGO is
'C�digo';

comment on column STG_FORTRA.FTR_IDENTI is
'Identificador del formulario';

comment on column STG_FORTRA.FTR_DESCRIP is
'Descripci�n formulario';

comment on column STG_FORTRA.FTR_TIPO is
'Tipo: formulario tr�mite (T) o formulario captura (C)';

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
'Permite establecer qui�n debe firmar el formulario (para formulario tramite)';

comment on column STG_FORTRA.FTR_PREREG is
'Indica si se debe presentar en preregistro';

comment on column STG_FORTRA.FTR_SCRINI is
'Script para establecer datos iniciales formulario';

comment on column STG_FORTRA.FTR_SCRPAR is
'Permite establecer parametros cada vez que se acceda al formulario';

comment on column STG_FORTRA.FTR_SCRRET is
'Este script se ejecutar� tras el retorno del gestor de formulario y permitir�:  
- validar el formulario tras el retorno del gestor de formulario  
- alimentar datos de los otros formularios y cambiar su estado. ';

comment on column STG_FORTRA.FTR_TIPFOR is
'Indica tipo formulario: interno (I)  / externo (E)';

comment on column STG_FORTRA.FTR_CODFOR is
'C�digo formulario gestor interno (si es interno)';

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
   FUE_CODIGO           NUMBER(20)           not null,
   FUE_AMBITO           VARCHAR2(1 CHAR)     not null,
   FUE_IDENT            VARCHAR2(20)         not null,
   FUE_DESCR            VARCHAR2(255 CHAR)   not null
);

comment on table STG_FUEDAT is
'Fuente de datos';

comment on column STG_FUEDAT.FUE_CODIGO is
'Codigo interno';

comment on column STG_FUEDAT.FUE_AMBITO is
'Ambito fuente datos (G : Global / E: Entidad / A: �rea)';

comment on column STG_FUEDAT.FUE_IDENT is
'Identificador fuente de datos';

comment on column STG_FUEDAT.FUE_DESCR is
'Descripci�n fuente de datos';

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
   GFE_CODIGO           NUMBER(20)           not null,
   GFE_CODENT           NUMBER(20)           not null,
   GFE_IDENTI           VARCHAR2(20)         not null,
   GFE_DESCR            VARCHAR2(255 CHAR)   not null,
   GFE_URL              VARCHAR2(100 CHAR)   not null
);

comment on table STG_GESFOR is
'Gestor formularios externos';

comment on column STG_GESFOR.GFE_CODIGO is
'C�digo';

comment on column STG_GESFOR.GFE_CODENT is
'C�digo entidad';

comment on column STG_GESFOR.GFE_IDENTI is
'Identificador';

comment on column STG_GESFOR.GFE_DESCR is
'Descripci�n';

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
   HVE_CODIGO           NUMBER(20)           not null,
   HVE_CODVTR           NUMBER(20)           not null,
   HVE_FECHA            DATE                 not null,
   HVE_ACCION           VARCHAR2(1 CHAR)     not null,
   HVE_RELEAS           NUMBER(8)            not null,
   HVE_CAMBIO           VARCHAR2(255 CHAR)   not null,
   HVE_USER             VARCHAR2(100 CHAR)   not null
);

comment on table STG_HISVER is
'Historial versi�n';

comment on column STG_HISVER.HVE_CODIGO is
'C�digo';

comment on column STG_HISVER.HVE_CODVTR is
'C�digo versi�n tr�mite';

comment on column STG_HISVER.HVE_FECHA is
'Fecha';

comment on column STG_HISVER.HVE_ACCION is
'Tipo acci�n: C (Creaci�n) / M (Modificaci�n) / I (Importaci�n)';

comment on column STG_HISVER.HVE_RELEAS is
'Release versi�n';

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
   LFV_CODIGO           NUMBER(20)           not null,
   LFV_CODCIN           NUMBER(20)           not null,
   LFV_VALOR            VARCHAR2(100 CHAR)   not null,
   LFV_DESCRIP          NUMBER(20)           not null,
   LFV_DEFECT           NUMBER(1)            default 0 not null,
   LFV_ORDEN            NUMBER(2)            not null
);

comment on table STG_LFVCIN is
'Para campo indexado de lista valores fija especifica la lista de valores';

comment on column STG_LFVCIN.LFV_CODIGO is
'C�digo valor';

comment on column STG_LFVCIN.LFV_CODCIN is
'C�digo elemento';

comment on column STG_LFVCIN.LFV_VALOR is
'Valor';

comment on column STG_LFVCIN.LFV_DESCRIP is
'Descripci�n';

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
   LSC_CODIGO           NUMBER(20)           not null,
   LSC_CODSCR           NUMBER(20)           not null,
   LSC_IDENTI           VARCHAR2(20)         not null,
   LSC_CODTRA           NUMBER(20)           not null
);

comment on table STG_LITSCR is
'Identifica los literares de error de cada Script en GTT';

comment on column STG_LITSCR.LSC_CODIGO is
'Codigo del literal';

comment on column STG_LITSCR.LSC_CODSCR is
'Codigo del Script';

comment on column STG_LITSCR.LSC_IDENTI is
'Identificaci�n del literal';

comment on column STG_LITSCR.LSC_CODTRA is
'C�digo traducci�n';

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
   PAG_CODIGO           NUMBER(20)           not null,
   PAG_CODPTR           NUMBER(20)           not null,
   PAG_IDENTI           VARCHAR2(20)         not null,
   PAG_DESCRIP          NUMBER(20)           not null,
   PAG_ORDEN            NUMBER(2)            not null,
   PAG_OBLIGA           VARCHAR2(1 CHAR)     not null,
   PAG_SCROBL           NUMBER(20),
   PAG_PLUGIN           VARCHAR2(20)         not null,
   PAG_SCRDPG           NUMBER(20),
   PAG_TIPO             VARCHAR2(1 CHAR)     not null,
   PAG_SIMULA           NUMBER(1)            default 0 not null
);

comment on table STG_PAGTRA is
'Pago tr�mite';

comment on column STG_PAGTRA.PAG_CODIGO is
'C�digo';

comment on column STG_PAGTRA.PAG_CODPTR is
'C�digo paso tramitaci�n';

comment on column STG_PAGTRA.PAG_IDENTI is
'Codigo del documento de pago';

comment on column STG_PAGTRA.PAG_DESCRIP is
'Descripci�n anexo';

comment on column STG_PAGTRA.PAG_ORDEN is
'Orden';

comment on column STG_PAGTRA.PAG_OBLIGA is
'Obligatorio:
 - Si (S)
 - Opcional (N)
 - Dependiente (D)';

comment on column STG_PAGTRA.PAG_SCROBL is
'En caso de ser dependiente establece obligatoriedad';

comment on column STG_PAGTRA.PAG_PLUGIN is
'Tipo plugin a emplear';

comment on column STG_PAGTRA.PAG_SCRDPG is
'Permite establecer din�micamente los datos del pago';

comment on column STG_PAGTRA.PAG_TIPO is
'Tipo: T (Telem�tico) / P (Presencial). Depender� del tipo de plugin.';

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
   PAN_CODIGO           NUMBER(20)           not null,
   PAN_SCRDIN           NUMBER(20)
);

comment on table STG_PASANE is
'Paso anexar';

comment on column STG_PASANE.PAN_CODIGO is
'C�digo';

comment on column STG_PASANE.PAN_SCRDIN is
'Script para anexos din�micos';

alter table STG_PASANE
   add constraint STG_PASANE_PK primary key (PAN_CODIGO);

/*==============================================================*/
/* Table: STG_PASCAP                                            */
/*==============================================================*/
create table STG_PASCAP 
(
   PCA_CODIGO           NUMBER(20)           not null,
   PCA_CODFOR           NUMBER(20)
);

comment on table STG_PASCAP is
'Paso captura';

comment on column STG_PASCAP.PCA_CODIGO is
'C�digo';

comment on column STG_PASCAP.PCA_CODFOR is
'C�digo';

alter table STG_PASCAP
   add constraint STG_PASCAP_PK primary key (PCA_CODIGO);

/*==============================================================*/
/* Table: STG_PASDBS                                            */
/*==============================================================*/
create table STG_PASDBS 
(
   PDB_CODIGO           NUMBER(20)           not null,
   PDB_INSINI           NUMBER(20)
);

comment on table STG_PASDBS is
'Paso debe saber';

comment on column STG_PASDBS.PDB_CODIGO is
'C�digo';

comment on column STG_PASDBS.PDB_INSINI is
'Instrucciones inicio (HTML)';

alter table STG_PASDBS
   add constraint STG_PASDBS_PK primary key (PDB_CODIGO);

/*==============================================================*/
/* Table: STG_PASINF                                            */
/*==============================================================*/
create table STG_PASINF 
(
   PIN_CODIGO           NUMBER(20)           not null,
   PIN_SCRDAT           NUMBER(20),
   PIN_FICPLA           NUMBER(20)
);

comment on table STG_PASINF is
'Paso informaci�n';

comment on column STG_PASINF.PIN_CODIGO is
'C�digo';

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
   PTR_CODIGO           NUMBER(20)           not null,
   PTR_CODVTR           NUMBER(20)           not null,
   PTR_TIPPTR           NUMBER(20)           not null,
   PTR_IDEPTR           VARCHAR2(20 CHAR)    not null,
   PTR_DESCRI           NUMBER(20),
   PTR_ORDEN            NUMBER(2)            not null,
   PTR_FINAL            NUMBER(1)            default 0 not null,
   PTR_SCRNVG           NUMBER(20),
   PTR_SCRVAR           NUMBER(20)
);

comment on table STG_PASOTR is
'Paso tramitaci�n';

comment on column STG_PASOTR.PTR_CODIGO is
'C�digo paso tramitaci�n';

comment on column STG_PASOTR.PTR_CODVTR is
'C�digo versi�n tr�mite';

comment on column STG_PASOTR.PTR_TIPPTR is
'Tipo del paso de tramitaci�n';

comment on column STG_PASOTR.PTR_IDEPTR is
'Identificador paso tramitaci�n. Para flujo normalizado ser� establecido autom�ticamente, para flujo personalizado ser� establecido por desarrollador.';

comment on column STG_PASOTR.PTR_DESCRI is
'Descripci�n del paso de tramitaci�n. En flujo normalizado ser� establecido autom�ticamente.';

comment on column STG_PASOTR.PTR_ORDEN is
'Orden paso';

comment on column STG_PASOTR.PTR_FINAL is
'Indica que paso es final';

comment on column STG_PASOTR.PTR_SCRNVG is
'Para flujo personalizado permite establecer script navegaci�n';

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
   PPG_CODIGO           NUMBER(20)           not null
);

comment on table STG_PASPAG is
'Paso pagos';

comment on column STG_PASPAG.PPG_CODIGO is
'C�digo';

alter table STG_PASPAG
   add constraint STG_PASPAG_PK primary key (PPG_CODIGO);

/*==============================================================*/
/* Table: STG_PASREG                                            */
/*==============================================================*/
create table STG_PASREG 
(
   PRG_CODIGO           NUMBER(20)           not null,
   PRG_REGOFI           VARCHAR2(20),
   PRG_REGLIB           VARCHAR2(20),
   PRG_REGASU           VARCHAR2(20),
   PRG_REGORG           VARCHAR2(20),
   PRG_SCRREG           NUMBER(20),
   PRG_INSPRE           NUMBER(20),
   PRG_INSFIT           NUMBER(20),
   PRG_SCRPRE           NUMBER(20),
   PRG_FIRMAR           NUMBER(1)            default 0 not null,
   PRG_REPADM           NUMBER(1)            default 0 not null,
   PRG_REPVAL           NUMBER(1)            default 0 not null,
   PRG_SCRREP           NUMBER(20),
   PRG_SCRVAL           NUMBER(20)
);

comment on table STG_PASREG is
'Paso registrar';

comment on column STG_PASREG.PRG_CODIGO is
'C�digo';

comment on column STG_PASREG.PRG_REGOFI is
'C�digo oficina registro';

comment on column STG_PASREG.PRG_REGLIB is
'C�digo libro registro';

comment on column STG_PASREG.PRG_REGASU is
'C�digo tipo asunto';

comment on column STG_PASREG.PRG_REGORG is
'C�digo �rgano destino';

comment on column STG_PASREG.PRG_SCRREG is
'Script destino registro';

comment on column STG_PASREG.PRG_INSPRE is
'Instrucciones presentaci�n';

comment on column STG_PASREG.PRG_INSFIT is
'Instrucciones fin tramitaci�n';

comment on column STG_PASREG.PRG_SCRPRE is
'Script presentador';

comment on column STG_PASREG.PRG_FIRMAR is
'Indica si se ha de firmar';

comment on column STG_PASREG.PRG_REPADM is
'Indica si admite representaci�n';

comment on column STG_PASREG.PRG_REPVAL is
'Indica si valida representaci�n';

comment on column STG_PASREG.PRG_SCRREP is
'Script representante';

comment on column STG_PASREG.PRG_SCRVAL is
'Script para validar permitir registrar';

alter table STG_PASREG
   add constraint STG_PASREG_PK primary key (PRG_CODIGO);

/*==============================================================*/
/* Table: STG_PASREL                                            */
/*==============================================================*/
create table STG_PASREL 
(
   PRL_CODIGO           NUMBER(20)           not null
);

comment on table STG_PASREL is
'Paso rellenar';

comment on column STG_PASREL.PRL_CODIGO is
'C�digo';

alter table STG_PASREL
   add constraint STG_PASREL_PK primary key (PRL_CODIGO);

/*==============================================================*/
/* Table: STG_PLGENT                                            */
/*==============================================================*/
create table STG_PLGENT 
(
   PLE_CODPLG           NUMBER(20)           not null,
   PLE_CODENT           NUMBER(20)           not null
);

comment on table STG_PLGENT is
'Plugins Entidad';

comment on column STG_PLGENT.PLE_CODPLG is
'Codigo plugin';

comment on column STG_PLGENT.PLE_CODENT is
'C�digo entidad';

alter table STG_PLGENT
   add constraint STG_PLGENT_PK primary key (PLE_CODPLG, PLE_CODENT);

/*==============================================================*/
/* Table: STG_PLUGIN                                            */
/*==============================================================*/
create table STG_PLUGIN 
(
   PLG_CODIGO           NUMBER(20)           not null,
   PLG_AMBITO           VARCHAR2(1 CHAR)     not null,
   PLG_TIPO             VARCHAR2(3 CHAR)     not null,
   PLG_DESCR            VARCHAR2(255 CHAR)   not null,
   PLG_CLASS            VARCHAR2(500 CHAR)   not null,
   PLG_PROPS            VARCHAR2(4000 CHAR),
   PLG_IDINST           VARCHAR2(20)
);

comment on table STG_PLUGIN is
'Plugins de conexi�n con otros sistemas';

comment on column STG_PLUGIN.PLG_CODIGO is
'Codigo';

comment on column STG_PLUGIN.PLG_AMBITO is
'�mbito: G (Global) , E (Entidad)';

comment on column STG_PLUGIN.PLG_TIPO is
'Tipo plugin:  
  - Global:  LOG: Login, REP: Representaci�n, DOM: Dominios remotos, FIR: Firma
  - Entidad: PRO: Catalogo procedimientos, REG: Registro, PAG: Pagos';

comment on column STG_PLUGIN.PLG_DESCR is
'Descripci�n plugin';

comment on column STG_PLUGIN.PLG_CLASS is
'Clase implementadora';

comment on column STG_PLUGIN.PLG_PROPS is
'Lista serializada propiedades (codigo  - valor)';

comment on column STG_PLUGIN.PLG_IDINST is
'Id Instancia (para plugins multiinstancia como pagos)';

alter table STG_PLUGIN
   add constraint STG_PLUGIN_PK primary key (PLG_CODIGO);

/*==============================================================*/
/* Table: STG_PRDCIN                                            */
/*==============================================================*/
create table STG_PRDCIN 
(
   CIP_CODIGO           NUMBER(20)           not null,
   CIP_CODCIN           NUMBER(20)           not null,
   CIP_TIPO             VARCHAR2(1 CHAR)     not null,
   CIP_VALOR            VARCHAR2(1000 CHAR)  not null,
   CIP_PARAM            VARCHAR2(1000 CHAR)
);

comment on table STG_PRDCIN is
'Para campo indexado de tipo dominio especifica par�metros dominio ';

comment on column STG_PRDCIN.CIP_CODIGO is
'C�digo interno';

comment on column STG_PRDCIN.CIP_CODCIN is
'C�digo campo indexado';

comment on column STG_PRDCIN.CIP_TIPO is
'Tipo par�metro: constante, campo, par�metro apertura, etc.';

comment on column STG_PRDCIN.CIP_VALOR is
'Valor par�metro';

comment on column STG_PRDCIN.CIP_PARAM is
'Par�metro dominio';

alter table STG_PRDCIN
   add constraint STG_PRDCIN_PK primary key (CIP_CODIGO);

/*==============================================================*/
/* Table: STG_PRLFTR                                            */
/*==============================================================*/
create table STG_PRLFTR 
(
   FPR_CODPRL           NUMBER(20)           not null,
   FPR_CODFOR           NUMBER(20)           not null
);

comment on table STG_PRLFTR is
'Formularios paso rellenar';

comment on column STG_PRLFTR.FPR_CODPRL is
'C�digo paso rellenar';

comment on column STG_PRLFTR.FPR_CODFOR is
'C�digo paso formulario';

alter table STG_PRLFTR
   add constraint STG_PRLFTR_PK primary key (FPR_CODPRL, FPR_CODFOR);

/*==============================================================*/
/* Table: STG_ROLARE                                            */
/*==============================================================*/
create table STG_ROLARE 
(
   RLA_CODIGO           NUMBER(20)           not null,
   RLA_CODARE           NUMBER(20)           not null,
   RLA_TIPO             VARCHAR2(1 CHAR)     not null,
   RLA_VALOR            VARCHAR2(100 CHAR)   not null,
   RLA_DESCR            VARCHAR2(255 CHAR)   not null,
   RLA_PERALT           NUMBER(1)            default 0 not null,
   RLA_PERMOD           NUMBER(1)            default 0 not null,
   RLA_PERCON           NUMBER(1)            default 0 not null,
   RLA_PERHLP           NUMBER(1)            default 0 not null,
   RLA_PERDOM           NUMBER(1)            default 0 not null
);

comment on table STG_ROLARE is
'Roles �rea';

comment on column STG_ROLARE.RLA_CODIGO is
'C�digo';

comment on column STG_ROLARE.RLA_CODARE is
'C�digo �rea';

comment on column STG_ROLARE.RLA_TIPO is
'Tipo: R (Role) / U (Usuario)';

comment on column STG_ROLARE.RLA_VALOR is
'Para Role el c�digo de Role, para Usuario el NIF del usuario';

comment on column STG_ROLARE.RLA_DESCR is
'Descripci�n';

comment on column STG_ROLARE.RLA_PERALT is
'Permiso alta-baja tr�mites';

comment on column STG_ROLARE.RLA_PERMOD is
'Permiso modificaci�n tr�mites';

comment on column STG_ROLARE.RLA_PERCON is
'Permiso consulta tr�mites';

comment on column STG_ROLARE.RLA_PERHLP is
'Permiso acceso helpdesk';

comment on column STG_ROLARE.RLA_PERDOM is
'Permiso gesti�n dominios �rea';

alter table STG_ROLARE
   add constraint STG_ROLARE_PK primary key (RLA_CODIGO);

/*==============================================================*/
/* Table: STG_SCRIPT                                            */
/*==============================================================*/
create table STG_SCRIPT 
(
   SCR_CODIGO           NUMBER(20)           not null,
   SCR_SCRIPT           CLOB
 ) 
 TABLESPACE SISTRAGES_DADES
   LOB (SCR_SCRIPT) STORE AS STG_SCRIPT_SCRIPT_LOB
	 (TABLESPACE SISTRAGES_LOB
	 INDEX STG_SCRIPT_SCRIPT_LOB_I); 


comment on table STG_SCRIPT is
'Scripts';

comment on column STG_SCRIPT.SCR_CODIGO is
'C�digo';

comment on column STG_SCRIPT.SCR_SCRIPT is
'Script';

alter table STG_SCRIPT
   add constraint STG_SCRIPT_PK primary key (SCR_CODIGO);

/*==============================================================*/
/* Table: STG_TIPPTR                                            */
/*==============================================================*/
create table STG_TIPPTR 
(
   TIP_CODIGO           NUMBER(20)           not null,
   TIP_PASO             VARCHAR2(20)         not null,
   TIP_DESCOR           NUMBER(20)           not null,
   TIP_ORDEN            NUMBER(2)
);

comment on table STG_TIPPTR is
'Tipos de paso de tramitaci�n:

Debe saber
Informaci�n en paso de debe saber

Rellenar formularios    
Paso que permite rellenar de 1 a n formularios. Muestra pantalla con la lista de formularios a completar.

Capturar datos
Paso que permite capturar datos a trav�s de un formulario.

Anexar documentos
Paso que permite anexar 1 a n documentos binarios

Pagar tasas
Paso que permite realizar el pago de tasas

Informaci�n
Paso que permite mostrar una pantalla informativa calculando din�micamente los datos a mostrar.

Registrar tr�mite
Paso que permite realizar el registro del tr�mite una vez se completen los pasos requeridos.
Despu�s de este paso no podr� haber m�s pasos.';

comment on column STG_TIPPTR.TIP_CODIGO is
'C�digo tipo certificado';

comment on column STG_TIPPTR.TIP_PASO is
'Identificador tipo de paso';

comment on column STG_TIPPTR.TIP_DESCOR is
'Descripci�n corta';

comment on column STG_TIPPTR.TIP_ORDEN is
'Orden, si forman parte del flujo normal';

alter table STG_TIPPTR
   add constraint STG_TIPPTR_PK primary key (TIP_CODIGO);

/*==============================================================*/
/* Index: STG_TIPPTR_PASO_UK                                    */
/*==============================================================*/
create unique index STG_TIPPTR_PASO_UK on STG_TIPPTR (
   TIP_PASO ASC
);

/*==============================================================*/
/* Table: STG_TRADUC                                            */
/*==============================================================*/
create table STG_TRADUC 
(
   TRA_CODIGO           NUMBER(20)           not null
);

comment on table STG_TRADUC is
'Tabla de literales multidioma';

comment on column STG_TRADUC.TRA_CODIGO is
'C�digo interno';

alter table STG_TRADUC
   add constraint STG_TRADUC_PK primary key (TRA_CODIGO);

/*==============================================================*/
/* Table: STG_TRAIDI                                            */
/*==============================================================*/
create table STG_TRAIDI 
(
   TRI_CODIGO           NUMBER(20)           not null,
   TRI_CODTRA           NUMBER(20)           not null,
   TRI_LITERA           VARCHAR2(1000 CHAR)  not null,
   TRI_IDIOMA           VARCHAR2(2 CHAR)     not null
);

comment on table STG_TRAIDI is
'Traducci�n literales';

comment on column STG_TRAIDI.TRI_CODIGO is
'C�digo interno';

comment on column STG_TRAIDI.TRI_CODTRA is
'C�digo traducci�n';

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
   TRM_CODIGO           NUMBER(20)           not null,
   TRM_CODARE           NUMBER(20)           not null,
   TRM_IDENTI           VARCHAR2(20)         not null,
   TRM_DESCR            VARCHAR2(1000 CHAR)  not null
);

comment on table STG_TRAMIT is
'Tramites';

comment on column STG_TRAMIT.TRM_CODIGO is
'C�digo';

comment on column STG_TRAMIT.TRM_CODARE is
'C�digo �rea';

comment on column STG_TRAMIT.TRM_IDENTI is
'Identificador tr�mite';

comment on column STG_TRAMIT.TRM_DESCR is
'Descripci�n';

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
   VCF_CODIGO           NUMBER(20)           not null,
   VCF_CODFIF           NUMBER(20)           not null,
   VCF_CODCFU           NUMBER(20)           not null,
   VCF_VALOR            VARCHAR2(4000)
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
/* Table: STG_VERTRA                                            */
/*==============================================================*/
create table STG_VERTRA 
(
   VTR_CODIGO           NUMBER(20)           not null,
   VTR_CODTRM           NUMBER(20)           not null,
   VTR_NUMVER           NUMBER(2)            not null,
   VTR_TIPFLU           VARCHAR2(1)          not null,
   VTR_DESCRIP          NUMBER(20)           not null,
   VTR_AUTENT           NUMBER(1)            default 0 not null,
   VTR_AUTQAA           NUMBER(1),
   VTR_IDISOP           VARCHAR2(50)         not null,
   VTR_PERSIS           NUMBER(1)            default 0 not null,
   VTR_PERINF           NUMBER(1)            default 0 not null,
   VTR_PERDIA           NUMBER(2),
   VTR_SCRPER           NUMBER(20),
   VTR_SCRINTRA         NUMBER(20),
   VTR_BLOQ             NUMBER(1),
   VTR_BLOQID           VARCHAR2(10 CHAR),
   VTR_BLOQUS           VARCHAR2(255 CHAR),
   VTR_RELEAS           NUMBER(8),
   VTR_ACTIVO           NUMBER(1)            not null,
   VTR_DEBUG            NUMBER(1)            default 0 not null,
   VTR_LIMTIP           VARCHAR2(1 char)     default 'N' not null,
   VTR_LIMNUM           NUMBER(5),
   VTR_LIMINT           NUMBER(2),
   VTR_DESPLZ           NUMBER(1)            default 0 not null,
   VTR_DESINI           DATE,
   VTR_DESFIN           DATE,
   VTR_DESMEN           NUMBER(20)
);

comment on table STG_VERTRA is
'Versi�n tr�mite';

comment on column STG_VERTRA.VTR_CODIGO is
'C�digo';

comment on column STG_VERTRA.VTR_CODTRM is
'C�digo tr�mite';

comment on column STG_VERTRA.VTR_NUMVER is
'N�mero versi�n';

comment on column STG_VERTRA.VTR_TIPFLU is
'Tipo de flujo; Normal (N) / Personalizado (P)';

comment on column STG_VERTRA.VTR_DESCRIP is
'C�digo traducci�n';

comment on column STG_VERTRA.VTR_AUTENT is
'Indica si es autenticado o no';

comment on column STG_VERTRA.VTR_AUTQAA is
'Para autenticado indica nivel QAA';

comment on column STG_VERTRA.VTR_IDISOP is
'Idiomas soportados (lista de idiomas separados por coma)';

comment on column STG_VERTRA.VTR_PERSIS is
'Indica si admite persistencia';

comment on column STG_VERTRA.VTR_PERINF is
'En caso de admitir persistencia indica si es infinita';

comment on column STG_VERTRA.VTR_PERDIA is
'En caso de admitir persistencia y no ser infinita se indican los d�as de persistencia';

comment on column STG_VERTRA.VTR_SCRPER is
'Script de personalizacion. Se ejecuta al cargar tr�mite (inicio o retomar desde persistencia)';

comment on column STG_VERTRA.VTR_SCRINTRA is
'Script inicializaci�n tr�mite. Se ejecuta al iniciar un tr�mite.';

comment on column STG_VERTRA.VTR_BLOQ is
'Versi�n bloqueada';

comment on column STG_VERTRA.VTR_BLOQID is
'Identificaci�n del usuario que bloquea la versi�n';

comment on column STG_VERTRA.VTR_BLOQUS is
'Nombre y apellidos del usuario que bloquea la versi�n';

comment on column STG_VERTRA.VTR_RELEAS is
'Realease actual, se crea al desbloquear la versi�n';

comment on column STG_VERTRA.VTR_ACTIVO is
'Indica si la versi�n est� activa';

comment on column STG_VERTRA.VTR_DEBUG is
'Indica si esta habilitado el debug del tramite';

comment on column STG_VERTRA.VTR_LIMTIP is
'Liimite tramitacion: N, I (sin limite, iniciados x intervalo, ...)';

comment on column STG_VERTRA.VTR_LIMNUM is
'Limite tramitacion: n�mero x intervalo';

comment on column STG_VERTRA.VTR_LIMINT is
'Limite tramitacion: minutos intervalo';

comment on column STG_VERTRA.VTR_DESPLZ is
'Indica si se activa un plazo de desactivaci�n temporal';

comment on column STG_VERTRA.VTR_DESINI is
'Plazo inicio desactivaci�n';

comment on column STG_VERTRA.VTR_DESFIN is
'Plazo fin desactivaci�n';

comment on column STG_VERTRA.VTR_DESMEN is
'Mensaje desactivaci�n';

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
   add constraint STG_ENTIDA_TRADUC_FK2 foreign key (ENT_PIETT)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_FILFUE
   add constraint STG_FILFUE_FUEDAT_FK foreign key (FIF_CODFUE)
      references STG_FUEDAT (FUE_CODIGO);

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

alter table STG_FORIMG
   add constraint STG_FORIMG_FICHER_FK foreign key (FIM_CODFIC)
      references STG_FICHER (FIC_CODIGO);

alter table STG_FORIMG
   add constraint STG_FORIMG_FORELE_FK foreign key (FIM_CODIGO)
      references STG_FORELE (FEL_CODIGO);

alter table STG_FORLI
   add constraint STG_FORLI_FORSEC_FK foreign key (FLS_CODFSE)
      references STG_FORSEC (FSE_CODIGO);

alter table STG_FORMUL
   add constraint STG_FORMUL_SCRIPT_FK foreign key (FOR_SCRPLT)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_FORPAG
   add constraint STG_FORPAG_FICHER_FK foreign key (PAF_CABLOG)
      references STG_FICHER (FIC_CODIGO);

alter table STG_FORPAG
   add constraint STG_FORPAG_FORMUL_FK foreign key (PAF_CODFOR)
      references STG_FORMUL (FOR_CODIGO);

alter table STG_FORPAG
   add constraint STG_FORPAG_SCRIPT_FK foreign key (PAF_SCRVAL)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_FORPAG
   add constraint STG_FORPAG_TRADUC_FK foreign key (PAF_CABTXT)
      references STG_TRADUC (TRA_CODIGO);

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
   add constraint STG_FORSEC_FORPAG_FK foreign key (FSE_CODPAF)
      references STG_FORPAG (PAF_CODIGO);

alter table STG_FORSEC
   add constraint STG_FORSEC_TRADUC_FK foreign key (FSE_TITULO)
      references STG_TRADUC (TRA_CODIGO);

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
   add constraint STG_PASOTR_TIPPTR_FK foreign key (PTR_TIPPTR)
      references STG_TIPPTR (TIP_CODIGO);

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

alter table STG_PASREL
   add constraint STG_PASREL_PRLFTR_FK foreign key (PRL_CODIGO)
      references STG_PASOTR (PTR_CODIGO);

alter table STG_PLGENT
   add constraint STG_PLGENT_ENTIDA_FK foreign key (PLE_CODENT)
      references STG_ENTIDA (ENT_CODIGO);

alter table STG_PLGENT
   add constraint STG_PLGENT_PLUGIN_FK foreign key (PLE_CODPLG)
      references STG_PLUGIN (PLG_CODIGO);

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

alter table STG_TIPPTR
   add constraint STG_TIPPTR_TRADUC_FK foreign key (TIP_DESCOR)
      references STG_TRADUC (TRA_CODIGO);

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

alter table STG_VERTRA
   add constraint STG_VERTRA_SCRIPT_FK foreign key (VTR_SCRPER)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_VERTRA
   add constraint STG_VERTRA_SCRIPT_FK2 foreign key (VTR_SCRINTRA)
      references STG_SCRIPT (SCR_CODIGO);

alter table STG_VERTRA
   add constraint STG_VERTRA_TRADUC_FK foreign key (VTR_DESCRIP)
      references STG_TRADUC (TRA_CODIGO);

alter table STG_VERTRA
   add constraint STG_VERTRA_TRAMIT_FK foreign key (VTR_CODTRM)
      references STG_TRAMIT (TRM_CODIGO);


/*==============================================================*/
/* Table: STG_AREFUE                                            */
/*==============================================================*/
create table STG_AREFUE 
(
   FUA_CODAREA          NUMBER(20)           not null,
   FUA_CODFUE           NUMBER(20)           not null
);

comment on table STG_AREFUE is
'Fuente de datos de �rea';

comment on column STG_AREFUE.FUA_CODAREA is
'C�digo �rea';

comment on column STG_AREFUE.FUA_CODFUE is
'Codigo fuente de datos';

alter table STG_AREFUE
   add constraint STG_AREFUE_PK primary key (FUA_CODAREA, FUA_CODFUE);

alter table STG_AREFUE
   add constraint STG_AREFUE_FUEDAT_FK foreign key (FUA_CODFUE)
      references STG_FUEDAT (FUE_CODIGO);

alter table STG_AREFUE
   add constraint STG_FUEDOM_AREA_FK foreign key (FUA_CODAREA)
      references STG_AREA (ARE_CODIGO);


/*==============================================================*/
/* Table: STG_AREDOM                                            */
/*==============================================================*/
create table STG_AREDOM 
(
   DMA_CODARE           NUMBER(20)           not null,
   DMA_CODDOM           NUMBER(20)           not null
);

comment on table STG_AREDOM is
'Dominios area';

comment on column STG_AREDOM.DMA_CODARE is
'C�digo area';

comment on column STG_AREDOM.DMA_CODDOM is
'C�digo dominio';

alter table STG_AREDOM
   add constraint STG_AREDOM_PK primary key (DMA_CODARE, DMA_CODDOM);

alter table STG_AREDOM
   add constraint STG_AREDOM_AREA_FK foreign key (DMA_CODARE)
      references STG_AREA (ARE_CODIGO);

alter table STG_AREDOM
   add constraint STG_AREDOM_DOMINI_FK foreign key (DMA_CODDOM)
      references STG_DOMINI (DOM_CODIGO);
      
      
