ALTER TABLE STG_ENTIDA MODIFY  ENT_PRGDIA NOT NULL;

INSERT INTO STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) VALUES (STG_CNFGLO_SEQ.nextval, 'sistrages.version', '1.0.0', 'Version de sistrages');
INSERT INTO STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) VALUES (STG_CNFGLO_SEQ.nextval, 'sistramit.cacheFlujo.timeout', '3600', 'Timeout en microsegundos');
INSERT INTO  STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) VALUES (STG_CNFGLO_SEQ.nextval, 'sistramit.ticket.timeout','30','Timeout autenticación tickets (segundos): tiempo permitido desde la generación del ticket hasta que se consume.');

DELETE FROM STG_CNFGLO WHERE CFG_PROP = 'sistramit.cacheFlujo.timeout';

ALTER TABLE STG_TRAIDI MODIFY TRI_LITERA VARCHAR2(4000);

UPDATE STG_CNFGLO SET CFG_DESCR = 'Idiomas sistramit. En caso de cambiar los idiomas, se debe reiniciar el servidor.' where CFG_PROP LIKE 'sistramit.idiomas';

ALTER TABLE STG_FORTRA DROP COLUMN FTR_PREREG;

UPDATE STG_CNFGLO SET CFG_NOMOD = 1 WHERE CFG_PROP = 'sistra2.version';

ALTER TABLE STG_VERTRA ADD VTR_AUTMET           VARCHAR2(50 CHAR)    default 'CER;PIN;PER';
comment on column STG_VERTRA.VTR_AUTMET is
'Indica métodos autenticación separados por punto y coma:
-             Certificado (CER)
-             Clave Pin (PIN)
-             Clave Permanente (PER)
';

ALTER TABLE STG_ENTIDA drop column ENT_LOPDI;

