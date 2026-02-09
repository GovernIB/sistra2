ALTER TABLE SISTRAGES.STG_FORTRA ADD FTR_FIRDIGAIT NUMBER(1) default 0;
comment on column STG_FORTRA.FTR_FIRDIGAIT is 'Indica el estado anterior de si se debe firmar digitalmente para orden 1 (para formulario tipo Tramite)';
