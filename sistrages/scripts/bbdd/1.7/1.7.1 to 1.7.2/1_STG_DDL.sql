ALTER TABLE STG_ENTIDA ADD ENT_MODENT NUMBER(1) default 0 not null;
comment on column STG_ENTIDA.ENT_MODENT is 'Indica si esta habilitado el modo entrega';

ALTER TABLE STG_PASREG ADD PRG_HABENT NUMBER(1) default 0 not null;
ALTER TABLE STG_PASREG ADD PRG_HABINM NUMBER(1) default 0 not null;
comment on column STG_PASREG.PRG_HABENT is 'Indica si el modo entrega esta habilitado';
comment on column STG_PASREG.PRG_HABINM is 'Indica si es inmediato cuando el modo entrega esta habilitado';

ALTER TABLE STG_FORCTX ADD CTX_NUMRMI_NEW NUMBER(12,2);
ALTER TABLE STG_FORCTX ADD CTX_NUMRMX_NEW NUMBER(12,2);
