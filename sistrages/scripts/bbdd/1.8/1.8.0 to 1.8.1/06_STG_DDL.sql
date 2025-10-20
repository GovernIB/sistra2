--Se crean columnas nuevas para hacer copia de seguridad
ALTER TABLE SISTRAGES.STG_FORTRA ADD FTR_FIRDIG_OLD NUMBER(1,0);
comment on column STG_FORTRA.FTR_FIRDIG_OLD is
'Indica si se debe firmar digitalmente (para formulario tipo Tramite)';
ALTER TABLE SISTRAGES.STG_ANETRA ADD ANE_FIRMAD_OLD NUMBER(1,0);
comment on column STG_ANETRA.ANE_FIRMAD_OLD is
'Indica si se debe anexar firmado';
ALTER TABLE SISTRAGES.STG_ANETRA ADD ANE_FIRMAR_OLD NUMBER(1,0);
comment on column STG_ANETRA.ANE_FIRMAR_OLD is
'Indica si se debe firmar digitalmente';
ALTER TABLE SISTRAGES.STG_ANETRA ADD ANE_VALFIRM_OLD NUMBER(1,0);
comment on column STG_ANETRA.ANE_VALFIRM_OLD is
'Indica si se debe validar firmantes';
