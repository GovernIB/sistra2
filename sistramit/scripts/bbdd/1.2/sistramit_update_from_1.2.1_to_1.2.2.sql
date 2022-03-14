/* Logos x UA */
alter table STT_FORMUL add   SFR_DIR3PRO          VARCHAR2(20 CHAR);
comment on column STT_FORMUL.SFR_DIR3PRO is 'Dir3 responsable procedimiento';

/* #496 check de debug falla en intentar desar el seient a BBDD  */
ALTER TABLE STT_LOGINT
ADD LOG_EVEDET_2 CLOB;

UPDATE STT_LOGINT 
SET LOG_EVEDET_2 = TO_CLOB (LOG_EVEDET);

ALTER TABLE STT_LOGINT
DROP COLUMN LOG_EVEDET;

ALTER TABLE STT_LOGINT
RENAME COLUMN LOG_EVEDET_2 TO LOG_EVEDET;

COMMENT ON COLUMN STT_LOGINT.LOG_EVEDET IS 'Detalle evento (depende del evento). Permite establecer info adicional mediante una lista de campos de información particulares del evento con formato: propiedad1=valor1#@#propiedad2=valor2';
