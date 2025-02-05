--Se crean columnas nuevas para hacer copia de seguridad
ALTER TABLE STG_ANETRA ADD ANE_FIRMAR_OLD NUMBER(1,0);
ALTER TABLE STG_ANETRA ADD ANE_FIRMAD_OLD NUMBER(1,0);

comment on column STG_ANETRA.ANE_FIRMAR_OLD is
'Indica si se debe firmar digitalmente';

comment on column STG_ANETRA.ANE_FIRMAD_OLD is
'Indica si se debe anexar firmado';
