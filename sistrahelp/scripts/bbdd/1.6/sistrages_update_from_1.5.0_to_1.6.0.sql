ALTER TABLE STH_AVISCONFIG ADD AVI_TIPO VARCHAR2(1 CHAR) default 'E';
comment on column  STH_AVISCONFIG.AVI_TIPO is 'Tipo de alerta: E (Entidad), A (Área), T (Trámite)';

ALTER TABLE STH_AVISCONFIG ADD AVI_TRAMITE VARCHAR2(50 CHAR);
comment on column  STH_AVISCONFIG.AVI_TRAMITE is 'Identificador del trámite a evaluar para alertas tipo T';

ALTER TABLE STH_AVISCONFIG ADD AVI_ENTIDAD VARCHAR2(10 CHAR);
comment on column  STH_AVISCONFIG.AVI_ENTIDAD is 'CodDir3 entidad relacionada';

ALTER TABLE STH_AVISCONFIG ADD AVI_VERSION NUMBER(2,0);
comment on column  STH_AVISCONFIG.AVI_VERSION is 'Numero versión del trámite a evaluar para alertas tipo V';

COMMIT;
