update STG_CNFGLO set CFG_VALOR = '4' where cfg_prop = 'sistra2.version.patch';

--Se hace copia de seguridad en las columnas nuevas
UPDATE STG_VERTRA SET VTR_AUTMET_OLD = VTR_AUTMET;

COMMIT;
