--Se hace copia de seguridad en las columnas nuevas
UPDATE SISTRAGES.STG_ANETRA SET ANE_EXTPER_OLD = ANE_EXTPER;
commit;
