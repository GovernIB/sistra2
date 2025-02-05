UPDATE STG_FORCTX SET CTX_NUMRMI_NEW = CTX_NUMRMI;
UPDATE STG_FORCTX SET CTX_NUMRMX_NEW = CTX_NUMRMX;

update STG_CNFGLO set CFG_VALOR = '2' where cfg_prop = 'sistra2.version.patch';

INSERT INTO STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) VALUES (STG_CNFGLO_SEQ.nextval, 'sistrages.timeout', '60', 'Timeout acceso SISTRAGES (segundos)');
INSERT INTO STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) VALUES (STG_CNFGLO_SEQ.nextval, 'sistrages.timeoutHabilitar', 'true', 'Indica si se habilita timeout acceso SISTRAGES');

COMMIT;

