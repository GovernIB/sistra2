ALTER TABLE SISTRAHELP.STH_AVISCONFIG ADD AVI_IDIOMA VARCHAR2(2 CHAR) default 'ca' not null;
comment on column  STH_AVISCONFIG.AVI_IDIOMA is 'Idioma de los mensajes enviados';

ALTER TABLE SISTRAHELP.STH_AVISCONFIG ADD AVI_MODO_EVALUACION NUMBER(2, 0) default 1;
comment on column  STH_AVISCONFIG.AVI_MODO_EVALUACION is 'Modo evaluacion: 1 - Acumulado diario; 2 - Por intervalo';

