update SISTRAGES.STG_CNFGLO set CFG_VALOR = '4' where cfg_prop = 'sistra2.version.patch';
insert into SISTRAGES.STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) values (STG_CNFGLO_SEQ.NEXTVAL, 'registro.controlConcurrencia','20','Control concurrencia al registrar (por instancia de S2)');
commit;