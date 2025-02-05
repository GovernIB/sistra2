ALTER TABLE STG_FORCTX ADD CTX_TEIVAP NUMBER(1,0) DEFAULT 0;
comment on column STG_FORCTX.CTX_TEIVAP is 'Indica si para campo teléfono internacional se realiza validación precisa';
ALTER TABLE STG_ANETRA ADD ANE_VALFIRM NUMBER(1,0) default 0 NOT NULL;
COMMENT ON COLUMN STG_ANETRA.ANE_VALFIRM IS 'Indica si se debe validar firmantes';
