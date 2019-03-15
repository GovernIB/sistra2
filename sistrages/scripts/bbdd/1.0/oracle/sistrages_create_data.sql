insert into STG_IDIOMA (IDI_IDENTI) VALUES ('es');
insert into STG_IDIOMA (IDI_IDENTI) VALUES ('ca');
insert into STG_IDIOMA (IDI_IDENTI) VALUES ('en');
insert into STG_IDIOMA (IDI_IDENTI) VALUES ('de');

insert into STG_PROCES (PROC_IDENT,PROC_INSTAN,PROC_FECHA) values ('MAESTRO','NONE', sysdate);

insert into  STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) values (STG_CNFGLO_SEQ.NEXTVAL, 'definicionTramite.lenguajeDefecto','ca','Lenguaje por defecto si no existe el lenguaje indicado');
INSERT INTO STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) VALUES (STG_CNFGLO_SEQ.nextval, 'sistramit.url', 'http://localhost:8080/sistramitfront', 'Url Frontal sistramit');
INSERT INTO STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) VALUES (STG_CNFGLO_SEQ.nextval, 'sistramit.idiomas', 'ca,es', 'Idiomas sistramit. En caso de cambiar los idiomas, se debe reiniciar el servidor.');
INSERT INTO STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) VALUES (STG_CNFGLO_SEQ.nextval, 'sistra2.version', '0.0.1-SNAPSHOT', 'Versión Sistra2');
INSERT INTO STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) VALUES (STG_CNFGLO_SEQ.nextval, 'sistramit.rest.url', 'http://localhost:8080/sistramit/api/rest/interna', 'Url Rest sistramit');
INSERT INTO STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) VALUES (STG_CNFGLO_SEQ.nextval, 'sistramit.rest.user', 'api-stt', 'Usuario Rest sistramit');
INSERT INTO STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) VALUES (STG_CNFGLO_SEQ.nextval, 'sistramit.rest.pwd', '1234', 'Contraseña Rest sistramit');
INSERT INTO STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) VALUES (STG_CNFGLO_SEQ.nextval, 'plugins.prefix', 'es.caib.sistra2', 'Prefijo base para los plugins usados en Sistra2');


Insert into STG_PLUGIN (PLG_CODIGO,PLG_AMBITO,PLG_CODENT,PLG_TIPO,PLG_DESCR,PLG_CLASS,PLG_PROPS,PLG_PREPRO) values (STG_PLUGIN_SEQ.nextval,'G',null,'L','Plugin login mock ','es.caib.sistra2.commons.plugins.autenticacion.mock.ComponenteAutenticacionPluginMock','[]','pluginsib.login.mock');
Insert into STG_PLUGIN (PLG_CODIGO,PLG_AMBITO,PLG_CODENT,PLG_TIPO,PLG_DESCR,PLG_CLASS,PLG_PROPS,PLG_PREPRO) values (STG_PLUGIN_SEQ.nextval,'G',null,'M','Plugin email mock ','es.caib.sistra2.commons.plugins.email.mock.EmailPluginMock','[]','pluginsib.email.mock');

INSERT INTO STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) VALUES (STG_CNFGLO_SEQ.nextval, 'sistrahelp.perdidaClave.numMaxTramites', '100', 'Num. max de tramites a consultar en perdida de clave');

insert into  STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) values (STG_CNFGLO_SEQ.NEXTVAL, 'sistramit.purga.erroresInternos','7','Tiempos de purgado (si algun parametro se configura con 0 no se purga), siendo Tiempo (dias) que permanecerán los errores internos (no asociados a un trámite)');
insert into  STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) values (STG_CNFGLO_SEQ.NEXTVAL, 'sistramit.purga.formulario.fin','10','Tiempo (minutos) tras el cual seran borradas las sesiones de formularios, si la sesion de formulario esta finalizada se eliminara despues del tiempo indicado a partir de su finalizacion');
insert into  STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) values (STG_CNFGLO_SEQ.NEXTVAL, 'sistramit.purga.formulario.nofin','60','Tiempo (minutos) tras el cual seran borradas las sesiones de formularios, si la sesion de formulario no esta finalizada se eliminara despues del tiempo indicado a partir de su inicio');
insert into  STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) values (STG_CNFGLO_SEQ.NEXTVAL, 'sistramit.purga.finalizado','6','Tiempo (horas) tras su fecha de finalizacion tras el cual un tramite finalizado sera purgado.');
insert into  STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) values (STG_CNFGLO_SEQ.NEXTVAL, 'sistramit.purga.noPersistente','12','Tiempo (horas) tras su fecha de ultimo acceso tras el cual seran purgados los tramites no persistentes no finalizados. ');
insert into  STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) values (STG_CNFGLO_SEQ.NEXTVAL, 'sistramit.purga.caducados','12','Tiempo (horas) tras su fecha de caducidad tras el cual seran purgados los tramites persistentes caducados. ');
insert into  STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) values (STG_CNFGLO_SEQ.NEXTVAL, 'sistramit.purga.purgados','90','Tiempo (dias) tras su fecha de purgado tras el cual seran definitivamente borrados los purgados.');
insert into  STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) values (STG_CNFGLO_SEQ.NEXTVAL, 'sistramit.purga.invalidaciones','24','Tiempo (horas) tras que las invalidaciones de tramites y dominios se purgan, debe ser mayor que el TimeToLive (o si existe el TimeToIdle) de las caches de tramites y dominios.');
insert into  STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) values (STG_CNFGLO_SEQ.NEXTVAL, 'sistramit.purga.pendientePurgaPago','365','Dias despues de la ultima modificacion del tramite tras los cuales se borrara tramite pendiente de purgar por pago realizado.');
insert into  STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) values (STG_CNFGLO_SEQ.NEXTVAL, 'sistramit.purga.pago.fin','10','Tiempo (minutos) tras el cual seran borradas los pagos, si la sesion de pagos est� finalizada se eliminar� despu�s del tiempo indicado a partir de su finalizacion');
insert into  STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) values (STG_CNFGLO_SEQ.NEXTVAL, 'sistramit.purga.pago.nofin','60','Tiempo (minutos) tras el cual seran borradas los pagos, si la sesion de pagos no est� finalizada se eliminar� despu�s del tiempo indicado a partir de su inicio');
insert into  STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) values (STG_CNFGLO_SEQ.NEXTVAL, 'sistramit.anexoSoporte.extensiones','pdf','Extensiones permitidas para el anexo del formulario de soporte (lista separada por comas).');
insert into  STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) values (STG_CNFGLO_SEQ.NEXTVAL, 'sistramit.anexoSoporte.tamanyo','1MB','Tamaño máximo permitido para el anexo del formulario de soporte (MB).');
insert into  STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) values (STG_CNFGLO_SEQ.NEXTVAL, 'growl.propiedades','sticky=false#life=4000','Parametro para alterar las propieades de un growl a nivel global (sticky y tiempo)');

