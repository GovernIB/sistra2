update SISTRAGES.STG_CNFGLO set CFG_VALOR = '1' where cfg_prop = 'sistra2.version.patch';
insert into SISTRAGES.STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) values (STG_CNFGLO_SEQ.NEXTVAL, 'debug.activo','N','Al estar activo, muestra logs por consola de javascript y de java');
insert into SISTRAGES.STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) values (STG_CNFGLO_SEQ.NEXTVAL, 'codemirror.desactivar','N','Cuando vale S, se desactiva el codeMirror y se pone un textarea');

commit;