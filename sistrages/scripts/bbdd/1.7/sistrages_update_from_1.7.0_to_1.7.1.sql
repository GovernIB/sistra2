update STG_CNFGLO set CFG_VALOR = '1' where cfg_prop = 'sistra2.version.patch';

/** 20/11/2023 issue: #772*/

ALTER TABLE STG_SESION
	ADD (SESI_PROPS VARCHAR2(4000 CHAR) DEFAULT '[{"codigo":"paginacion","valor":"10","orden":null}]' NOT NULL);

COMMENT ON COLUMN STG_SESION.SESI_PROPS IS 'Lista serializada propiedades (codigo  - valor)';

COMMIT;

