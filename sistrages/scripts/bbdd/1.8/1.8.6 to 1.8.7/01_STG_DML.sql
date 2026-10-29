update SISTRAGES.STG_CNFGLO set CFG_VALOR = '7' where cfg_prop = 'sistra2.version.patch';

UPDATE STG_CNFGLO
SET CFG_DESCR = 'Extensions permeses als annexos de SISTRAMIT.'
WHERE CFG_PROP = 'sistramit.anexos.extensionesPermitidas';

commit;