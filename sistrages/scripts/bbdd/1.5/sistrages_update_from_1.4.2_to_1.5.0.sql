alter table STG_FORCTX add  CTX_PREVPEG NUMBER(1) default 0 not null;
comment on column STG_FORCTX.CTX_PREVPEG is
'Prevenir pegar';

update STG_CNFGLO set CFG_VALOR = '1.5' where cfg_prop = 'sistra2.version';
update STG_CNFGLO set CFG_VALOR = '0' where cfg_prop = 'sistra2.version.patch';

COMMIT;
