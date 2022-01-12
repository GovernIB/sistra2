--DML
update stg_cnfglo set cfg_valor = '1.2' where cfg_prop = 'sistra2.version';
COMMIT;

/** MULTIPÁGINA **/
--DDL
ALTER TABLE STG_ENTIDA ADD ENT_URLSEU           NUMBER(18);
comment on column STG_ENTIDA.ENT_URLSEU is 'Url Sede Electrónica';
alter table STG_ENTIDA
   add constraint STG_ENTIDA_TRADUC_FK11 foreign key (ENT_URLSEU)
      references STG_TRADUC (TRA_CODIGO);
ALTER TABLE STG_FORMUL ADD    FOR_GUARDA           NUMBER(1)            default 0;
comment on column STG_FORMUL.FOR_GUARDA is 'Indica si permite guardar sin finalizar';
ALTER TABLE STG_FORPAG ADD  PAF_SCRNAV           NUMBER(18);
ALTER TABLE STG_FORPAG ADD  PAF_ALIAS            VARCHAR2(20 CHAR);
comment on column STG_FORPAG.PAF_SCRVAL is 'Script de validación';
comment on column STG_FORPAG.PAF_SCRNAV is 'Script de navegación entre páginas';
comment on column STG_FORPAG.PAF_ALIAS is 'Alias página';
alter table STG_FORPAG add constraint STG_FORPAG_SCRIPT_FK2 foreign key (PAF_SCRNAV) references STG_SCRIPT (SCR_CODIGO);

--DML
/*UPDATE STG_FORPAG SET PAF_ALIAS = 'P' || TO_CHAR(PAF_ORDEN);
COMMIT;*/

--DDL
ALTER TABLE STG_FORPAG MODIFY PAF_ALIAS NOT NULL;
ALTER TABLE STG_FORPAG RENAME COLUMN PAF_ALIAS TO PAF_IDENTI;


/** CONFIGURCION AUTENTICACIONES **/

create sequence STG_CONFAUT_SEQ;

create table STG_CONFAUT
(
   CAU_CODIGO           NUMBER(18)           not null,
   CAU_CODARE           NUMBER(18)           not null,
   CAU_IDENTI           VARCHAR2(20 CHAR)    not null,
   CAU_DESCR            VARCHAR2(255 CHAR)   not null,
   CAU_USER             VARCHAR2(50 CHAR)    not null,
   CAU_PASS             VARCHAR2(50 CHAR)    not null
);

comment on table STG_CONFAUT is 'Configuración autenticación área';

comment on column STG_CONFAUT.CAU_CODIGO is 'CODIGO INTERNO';

comment on column STG_CONFAUT.CAU_IDENTI is 'IDENTIFICADOR';

comment on column STG_CONFAUT.CAU_DESCR is 'DESCRIPCIÓN';

comment on column STG_CONFAUT.CAU_USER is 'USUARIO';

comment on column STG_CONFAUT.CAU_PASS is 'CONTRASEÑA';

comment on column STG_CONFAUT.CAU_CODARE is 'AREA';

alter table STG_CONFAUT
   add constraint PK_STG_CONFAUT primary key (CAU_CODIGO);

alter table STG_CONFAUT
   add constraint STG_CONFAUT_AREA_FK foreign key (CAU_CODARE)
      references STG_AREA (ARE_CODIGO);


/** GESTOR FORMULARIOS EXTERNO  **/

ALTER table STG_GESFOR  ADD   GFE_CODARE           NUMBER(18);
ALTER table STG_GESFOR  ADD   GFE_CODCAU           NUMBER(18);

alter table STG_GESFOR
   add constraint STG_GESFOR_AREA_FK foreign key (GFE_CODARE)
      references STG_AREA (ARE_CODIGO);

comment on column STG_GESFOR.GFE_CODARE is 'Código área';
comment on column STG_GESFOR.GFE_CODCAU is 'Código configuración autenticación';

alter table STG_GESFOR
   add constraint STG_GESFOR_CONFAUT_FK foreign key (GFE_CODCAU)
      references STG_CONFAUT (CAU_CODIGO);


/** DOMINIOS REMOTOS**/

ALTER table STG_DOMINI ADD DOM_CODCAU           NUMBER(18);
ALTER table STG_DOMINI ADD DOM_TIMEOUT          NUMBER(4);

comment on column STG_DOMINI.DOM_CODCAU is 'Código configuración autenticación';

comment on column STG_DOMINI.DOM_TIMEOUT is 'Timeout para dominios remotos (segundos)';

alter table STG_DOMINI
   add constraint STG_DOMINI_CONFAUT_FK foreign key (DOM_CODCAU)
      references STG_CONFAUT (CAU_CODIGO);



--DML

/** UPDATE PARA CONVERTIR LOS DOMINIOS DE TIPO REMOTO, QUE NO SEAN DE AREA, A TIPO LISTA FIJA (HABRA QUE ELIMINARLOS Y CREARLOS EN EL AREA QUE CORRESPONDA) **/
 UPDATE  STG_DOMINI
   SET DOM_TIPO = 'L'
  WHERE DOM_AMBITO <> 'A'
    AND DOM_TIPO = 'R';
COMMIT;

/** REASIGNAR LOS GESTORES DE FORMULARIOS A UNA AREA */
ALTER table STG_GESFOR DROP COLUMN GFE_CODENT;

create sequence STG_PLANENT_SEQ;

create table STG_PLAENT
(
   PLE_CODIGO           NUMBER(18)           not null,
   PLE_CODENT           NUMBER(18),
   PLE_TIPO             VARCHAR2(2 CHAR)     not null,
   PLE_IDIOMA           VARCHAR2(2 CHAR),
   PLE_CODFIC           NUMBER(18)
);

comment on table STG_PLAENT is
'PLANTILLAS ENTIDAD';

comment on column STG_PLAENT.PLE_CODIGO is
'CODIGO';

comment on column STG_PLAENT.PLE_CODENT is
'CODIGO ENTIDAD';

comment on column STG_PLAENT.PLE_TIPO is
'TIPO PLANTILLA:
 - FR: FIN REGISTRO';

comment on column STG_PLAENT.PLE_IDIOMA is
'IDIOMA';

comment on column STG_PLAENT.PLE_CODFIC is
'CODIGO FICHERO';

alter table STG_PLAENT
   add constraint STG_PLAENT_PK primary key (PLE_CODIGO);

create unique index STG_PLAENT_PLA_UK on STG_PLAENT (
   PLE_CODENT ASC,
   PLE_TIPO ASC,
   PLE_IDIOMA ASC
);

alter table STG_PLAENT
   add constraint STG_PLAENT_ENTIDA_FK foreign key (PLE_CODENT)
      references STG_ENTIDA (ENT_CODIGO);

alter table STG_PLAENT
   add constraint STG_PLAENT_FICHER_FK foreign key (PLE_CODFIC)
      references STG_FICHER (FIC_CODIGO);
	  

Insert into STG_CNFGLO (CFG_CODIGO,CFG_PROP,CFG_VALOR,CFG_DESCR,CFG_NOMOD) values (STG_CNFGLO_SEQ.nextval,'sistra2.version.patch','0','version menor de sistra2 (sistra2.version.x)','1');
commit;
