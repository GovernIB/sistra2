ALTER TABLE STH_HISTAVIS MODIFY (HIST_EVENTO VARCHAR2(1024 CHAR));

ALTER TABLE STH_HISTAVIS ADD (
  HIST_NOMBRE          VARCHAR2(20 CHAR)        NULL,
  HIST_TIPO            VARCHAR2(1 CHAR)         NULL,
  HIST_ENTIDAD         VARCHAR2(10 CHAR)        NULL,
  HIST_AREAS           VARCHAR2(1024 CHAR)      NULL,
  HIST_TRAMITE         VARCHAR2(50 CHAR)        NULL,
  HIST_VERSION         NUMBER(2,0)              NULL,
  HIST_MAIL            VARCHAR2(1024 CHAR)      NULL,
  HIST_INTEREVA        NUMBER(5,0)              NULL,
  HIST_PERIEVA         VARCHAR2(11 CHAR)        NULL,
  HIST_MODO_EVALUACION NUMBER(2,0)              NULL,
  HIST_IDIOMA          VARCHAR2(2 CHAR)         NULL
);

COMMENT ON COLUMN STH_HISTAVIS.HIST_NOMBRE IS 'Nombre Aviso Lanzado';
COMMENT ON COLUMN STH_HISTAVIS.HIST_TIPO IS 'Tipo de aviso: E (Entidad), A (Área), T (Trámite)';
COMMENT ON COLUMN STH_HISTAVIS.HIST_ENTIDAD IS 'CodDir3 de la entidad asociada al aviso';
COMMENT ON COLUMN STH_HISTAVIS.HIST_AREAS IS 'Lista de áreas del aviso lanzado';
COMMENT ON COLUMN STH_HISTAVIS.HIST_TRAMITE IS 'Identificador del trámite del aviso lanzado';
COMMENT ON COLUMN STH_HISTAVIS.HIST_VERSION IS 'Numero de versión del trámite del aviso lanzado';
COMMENT ON COLUMN STH_HISTAVIS.HIST_MAIL IS 'Lista de correos electrónicos asociados al aviso';
COMMENT ON COLUMN STH_HISTAVIS.HIST_INTEREVA IS 'Intervalo de evaluación en minutos';
COMMENT ON COLUMN STH_HISTAVIS.HIST_PERIEVA IS 'Período de evaluación (formato HH:mm:ss)';
COMMENT ON COLUMN STH_HISTAVIS.HIST_MODO_EVALUACION IS 'Modo de evaluación del aviso (1: Acumulado diario, 2: Por intervalo.)';
COMMENT ON COLUMN STH_HISTAVIS.HIST_IDIOMA IS 'Idioma de los mensajes de aviso enviados (es, ca.)';
