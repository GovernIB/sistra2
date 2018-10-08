insert into STG_IDIOMA (IDI_IDENTI) VALUES ('es');
insert into STG_IDIOMA (IDI_IDENTI) VALUES ('ca');
insert into STG_IDIOMA (IDI_IDENTI) VALUES ('en');
insert into STG_IDIOMA (IDI_IDENTI) VALUES ('de');

insert into STG_PROCES (PROC_IDENT,PROC_INSTAN,PROC_FECHA) values ('MAESTRO','NONE', sysdate);

insert into  STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) values (STG_CNFGLO_SEQ.NEXTVAL, 'definicionTramite.lenguajeDefecto','ca','Lenguaje por defecto si no existe el lenguaje indicado');           
INSERT INTO STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) VALUES (STG_CNFGLO_SEQ.nextval, 'sistramit.url', 'http://localhost:8080/sistramitfront', 'Url Frontal sistramit');
INSERT INTO STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) VALUES (STG_CNFGLO_SEQ.nextval, 'sistramit.idiomas', 'ca,es', 'Idiomas sistramit');
INSERT INTO STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) VALUES (STG_CNFGLO_SEQ.nextval, 'sistramit.cacheFlujo.timeout', '3600', 'Timeout en microsegundos');   
INSERT INTO STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) VALUES (STG_CNFGLO_SEQ.nextval, 'sistrages.version', '1.0.0', 'Version de sistrages');
INSERT INTO STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) VALUES (STG_CNFGLO_SEQ.nextval, 'sistramit.rest.url', 'http://localhost:8080/sistramit/api/rest/interna', 'Url Rest sistramit');
INSERT INTO STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) VALUES (STG_CNFGLO_SEQ.nextval, 'sistramit.rest.user', 'api-stt', 'Usuario Rest sistramit');
INSERT INTO STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) VALUES (STG_CNFGLO_SEQ.nextval, 'sistramit.rest.pwd', '1234', 'Contraseña Rest sistramit');


Insert into STG_PLUGIN (PLG_CODIGO,PLG_AMBITO,PLG_CODENT,PLG_TIPO,PLG_DESCR,PLG_CLASS,PLG_PROPS,PLG_PREPRO) values (STG_PLUGIN_SEQ.nextval,'E','1','C','Plugin catálogo mock ','es.caib.sistra2.commons.plugins.catalogoprocedimientos.mock.CatalogoProcedimientosPluginMock','[]','xxx');
Insert into STG_PLUGIN (PLG_CODIGO,PLG_AMBITO,PLG_CODENT,PLG_TIPO,PLG_DESCR,PLG_CLASS,PLG_PROPS,PLG_PREPRO) values (STG_PLUGIN_SEQ.nextval,'G',null,'L','Plugin login mock ','es.caib.sistra2.commons.plugins.autenticacion.mock.ComponenteAutenticacionPluginMock','[]','xxx');
Insert into STG_PLUGIN (PLG_CODIGO,PLG_AMBITO,PLG_CODENT,PLG_TIPO,PLG_DESCR,PLG_CLASS,PLG_PROPS,PLG_PREPRO) values (STG_PLUGIN_SEQ.nextval,'G',null,'M','Plugin email mock ','es.caib.sistra2.commons.plugins.email.mock.EmailPluginMock','[]','xxx');
