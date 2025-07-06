--Se crean columnas nuevas para hacer copia de seguridad
ALTER TABLE SISTRAGES.STG_ANETRA ADD ANE_EXTPER_OLD VARCHAR2(1000 CHAR);
comment on column STG_ANETRA.ANE_EXTPER_OLD is
'Lista extensiones permitidas separadas por coma';
