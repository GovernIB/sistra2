insert into STG_IDIOMA (IDI_IDENTI) VALUES ('es');
insert into STG_IDIOMA (IDI_IDENTI) VALUES ('ca');
insert into STG_IDIOMA (IDI_IDENTI) VALUES ('en');
insert into STG_IDIOMA (IDI_IDENTI) VALUES ('de');

insert into STG_PROCES (PROC_IDENT,PROC_INSTAN,PROC_FECHA) values ('MAESTRO','NONE', sysdate);

insert into  STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) values (STG_CNFGLO_SEQ.NEXTVAL, 'definicionTramite.lenguajeDefecto','ca','Lenguaje por defecto si no existe el lenguaje indicado');           
INSERT INTO STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) VALUES (STG_CNFGLO_SEQ.nextval, 'sistramit.url', 'http://localhost:8080/sistramitfront', 'Url sistramit');
INSERT INTO STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) VALUES (STG_CNFGLO_SEQ.nextval, 'sistramit.idiomas', 'ca,es', 'Idiomas sistramit');
INSERT INTO STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) VALUES (STG_CNFGLO_SEQ.nextval, 'sistramit.cacheFlujo.timeout', '3600', 'Timeout en microsegundos');   
