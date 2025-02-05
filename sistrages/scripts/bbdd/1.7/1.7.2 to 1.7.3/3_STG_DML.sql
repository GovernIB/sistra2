update STG_CNFGLO set CFG_VALOR = '3' where cfg_prop = 'sistra2.version.patch';

--Se hace copia de seguridad en las columnas nuevas
UPDATE STG_ANETRA SET ANE_FIRMAR_OLD = ANE_FIRMAR;
UPDATE STG_ANETRA SET ANE_FIRMAD_OLD = ANE_FIRMAD;

COMMIT;
