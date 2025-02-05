create table STG_VARARE
(
   VAR_CODIGO           NUMBER(18)           not null,
   VAR_IDENT            VARCHAR2(50 CHAR)    not null,
   VAR_CODARE           NUMBER(18)           not null,
   VAR_URL              VARCHAR2(512 CHAR)    not null,
   VAR_DESCRI           VARCHAR2(256 CHAR)
);

comment on table STG_VARARE is
'Lista variables área';

comment on column STG_VARARE.VAR_CODIGO is
'Código interno';

comment on column STG_VARARE.VAR_IDENT is
'Identificador';

comment on column STG_VARARE.VAR_CODARE is
'Código área';

comment on column STG_VARARE.VAR_URL is
'URL';

comment on column STG_VARARE.VAR_DESCRI is
'Descripción variable área';

alter table STG_VARARE
   add constraint STG_VARARE_PK primary key (VAR_CODIGO);

create sequence STG_VARARE_SEQ;

alter table STG_VARARE
   add constraint STG_VARARE_AREA_FK foreign key (VAR_CODARE)
      references STG_AREA (ARE_CODIGO);


ALTER TABLE STG_PLUGIN
	ADD PLG_CLASSREAL VARCHAR2(500 CHAR) NULL;

ALTER TABLE STG_PLUGIN
	ADD PLG_CLASSMOCK VARCHAR2(500 CHAR) NULL;

COMMENT ON COLUMN STG_PLUGIN.PLG_CLASSREAL IS 'Clase real';

COMMENT ON COLUMN STG_PLUGIN.PLG_CLASSMOCK IS 'Clase mock';

UPDATE STG_PLUGIN SET STG_PLUGIN.PLG_CLASSREAL=STG_PLUGIN.PLG_CLASS;

ALTER TABLE STG_SECREU MODIFY  SREU_BLOQID VARCHAR2(255 CHAR);

update STG_CNFGLO set CFG_VALOR = '2' where cfg_prop = 'sistra2.version.patch';

UPDATE STG_PLUGIN
SET plg_classmock = 'es.caib.sistra2.commons.plugins.dominio.mock.DominioPluginMock'
WHERE plg_tipo='D';

UPDATE STG_PLUGIN
SET plg_classmock = 'es.caib.sistra2.commons.plugins.autenticacion.mock.ComponenteAutenticacionPluginMock'
WHERE plg_tipo='L';

UPDATE STG_PLUGIN
SET plg_classmock = 'es.caib.sistra2.commons.plugins.email.mock.EmailPluginMock'
WHERE plg_tipo='M';

UPDATE STG_PLUGIN
SET plg_classmock = 'es.caib.sistra2.commons.plugins.registro.mock.EnvioRemotoMockPlugin'
WHERE plg_tipo='B';

UPDATE STG_PLUGIN
SET plg_classmock = 'es.caib.sistra2.commons.plugins.catalogoprocedimientos.mock.CatalogoProcedimientosPluginMock'
WHERE plg_tipo='C';

UPDATE STG_PLUGIN
SET plg_classmock = 'es.caib.sistra2.commons.plugins.registro.mock.RegistroMockPlugin'
WHERE plg_tipo='E';

UPDATE STG_PLUGIN
SET plg_classmock = 'es.caib.sistra2.commons.plugins.firmacliente.mock.ComponenteFirmaPluginMock'
WHERE plg_tipo='F';

UPDATE STG_PLUGIN
SET plg_classmock = 'es.caib.sistra2.commons.plugins.formulario.mock.FormularioPluginMock'
WHERE plg_tipo='G';

UPDATE STG_PLUGIN
SET plg_classmock = 'es.caib.sistra2.commons.plugins.validacionfirma.mock.ValidacionFirmaPluginMock'
WHERE plg_tipo='S';

COMMIT;
