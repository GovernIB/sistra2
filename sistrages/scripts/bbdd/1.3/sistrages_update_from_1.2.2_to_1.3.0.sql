/** V 1.3   **************************************/

/*** CONF. AUTENTICACION EN ENTIDAD Y GLOBAL **/
ALTER TABLE STG_CONFAUT MODIFY CAU_CODARE NULL;
ALTER TABLE STG_CONFAUT ADD CAU_AMBITO VARCHAR2(1 CHAR);
ALTER TABLE STG_CONFAUT ADD CAU_CODENT NUMBER(18);

/** Para migrar todas las conf. autenticacion al ambito de tipo area ya que sólo deberá haber de este tipo **/
update STG_CONFAUT  set cau_ambito = 'A';
commit;


ALTER TABLE STG_CONFAUT MODIFY CAU_AMBITO NOT NULL;

alter table STG_CONFAUT   add constraint STG_CONFAUT_ENT_FK foreign key (CAU_CODENT)     references STG_ENTIDA (ENT_CODIGO);



comment on column STG_CONFAUT.CAU_AMBITO is
'Ámbito fuente datos (G : Global / E: Entidad / A: Área)';
comment on column STG_CONFAUT.CAU_CODENT is
'Indica la entidad si el ambito es de tipo entidad';

/*** SCRIPT PARA INFO DEBE SABER CON SCRIPTS.***/

ALTER TABLE STG_PASDBS ADD    PDB_SCRINS    NUMBER(18);

comment on column STG_PASDBS.PDB_SCRINS is 'Script para establecer instrucciones iniciales';

alter table STG_PASDBS add constraint STG_PASDBS_SCRIPT_FK foreign key (PDB_SCRINS) references STG_SCRIPT (SCR_CODIGO);

/** script para poner bien la relacion de dominios con entidades y areas **/
--- añadir campos nuevos y fks
ALTER TABLE STG_DOMINI ADD DOM_CODENT NUMBER(18);
ALTER TABLE STG_DOMINI ADD DOM_CODARE NUMBER(18);

comment on column STG_DOMINI.DOM_CODENT is 'Código entidad';
comment on column STG_DOMINI.DOM_CODARE is 'Código área';

alter table STG_DOMINI
   add constraint STG_DOMINI_AREA_FK foreign key (DOM_CODARE)
      references STG_AREA (ARE_CODIGO);

alter table STG_DOMINI
   add constraint STG_DOMINI_ENTIDA_FK foreign key (DOM_CODENT)
      references STG_ENTIDA (ENT_CODIGO);

--- script migracion
UPDATE STG_DOMINI DOM
   SET DOM_CODENT = (SELECT DEN_CODENT FROM STG_DOMENT WHERE DEN_CODDOM = DOM.DOM_CODIGO  );
UPDATE STG_DOMINI DOM
   SET DOM_CODARE = (SELECT DMA_CODARE FROM STG_AREDOM WHERE DMA_CODDOM = DOM.DOM_CODIGO  );
COMMIT;

--- cuando TODO FUNCIONE BIEN, SE BORRAN LAS TABLAS ANTIGUAS
--Drop table STG_DOMENT cascade constraints;
--Drop table STG_AREDOM cascade constraints;



/** script para identificadores compuestos **/
ALTER TABLE STG_ENTIDA ADD ENT_IDENTI VARCHAR2(20);
comment on column STG_ENTIDA.ENT_IDENTI is 'Identificador de entidad';

UPDATE STG_ENTIDA SET ENT_IDENTI = DECODE(ENT_DIR3, 'A04003003' ,'CAIB', 'ENT'||ENT_CODIGO);
COMMIT;

ALTER TABLE STG_ENTIDA MODIFY ENT_IDENTI NOT NULL;
DROP INDEX STG_TRAMIT_IDENTI_UK;
DROP INDEX STG_DOMINI_IDENTI_UK;
DROP INDEX STG_FUEDAT_IDENTI_UK;
DROP INDEX STG_GESFOR_IDENTI_UK;
create unique index STG_GESFOR_IDENTI_UK on STG_GESFOR (
   GFE_IDENTI ASC,
   GFE_CODARE ASC
);
alter table STG_GESFOR MODIFY GFE_CODARE	NUMBER(18,0) not null;

/** actualizacion comentario por la 466 */
comment on column STG_FORCIN.CIN_TIPO is
'Tipo campo indexado:
- SELECTOR
- DESPLEGABLE
- MULTIPLE
- UNICA
- DINAMICO';

/** Tabla captch #467 */
CREATE TABLE STG_FORCPT
(
  FCP_CODIGO	NUMBER(18,0) not null,
  FCP_TEXTO	NUMBER(18,0)
);
comment on table STG_FORCPT is 'Campo captch';
comment on column STG_FORCPT.FCP_CODIGO is 'Código';
comment on column STG_FORCPT.FCP_TEXTO is 'Texto';
alter table STG_FORCPT
   add constraint STG_FORCPT_PK primary key (FCP_CODIGO);


/** --- 09/03/2022 #495 **/
ALTER TABLE STG_AREA
ADD ARE_EMAIL VARCHAR2(255 CHAR) NULL;
COMMENT ON COLUMN STG_AREA.ARE_EMAIL IS 'Email';

ALTER TABLE STG_FORSOP
MODIFY FSO_TIPDST VARCHAR2(5 CHAR);

/**Props globales para v1.3.0**/
update stg_cnfglo set cfg_valor = '1.3' where cfg_prop = 'sistra2.version';
COMMIT;
update STG_CNFGLO set CFG_VALOR = '0' where cfg_prop = 'sistra2.version.patch';
COMMIT;

INSERT INTO STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) VALUES (STG_CNFGLO_SEQ.nextval, 'iframeFirmaWidth', '500', 'Ancho de la ventana de firma');
INSERT INTO STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) VALUES (STG_CNFGLO_SEQ.nextval, 'iframeFirmaHeight', '500', 'Ancho de la ventana de firma');
COMMIT; 

/** --- 28/03/2022 **/
ALTER TABLE STG_GESFOR MODIFY ( GFE_CODARE NOT NULL);
