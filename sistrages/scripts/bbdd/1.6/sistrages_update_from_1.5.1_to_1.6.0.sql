ALTER TABLE STG_FORLEL DROP CONSTRAINT STG_FORLEL_FORELE_FK;
ALTER TABLE STG_FORLEL ADD LEL_CODFOR NUMBER(18);
comment on column STG_FORLEL.LEL_CODFOR is 'Código formulario asociado';
alter table STG_FORLEL add constraint STG_FORLEL_FORMUL_FK foreign key (LEL_CODFOR) references STG_FORMUL (FOR_CODIGO);

ALTER TABLE STG_FORCAM ADD FCA_LELMOS NUMBER(1) default 0 not null;
ALTER TABLE STG_FORCAM ADD FCA_LELCOL NUMBER(3) default 0;
comment on column STG_FORCAM.FCA_LELMOS is 'Para campos de un componente lista de elementos, indica si sale en la lista';
comment on column STG_FORCAM.FCA_LELCOL is 'Para campos de un componente lista de elementos, si sale en la lista indica el ancho de columna';

ALTER table STG_FORLEL ADD LEL_MAXELE  NUMBER(3) default 10;
comment on column STG_FORLEL.LEL_MAXELE is 'Número máximo de elementos';

ALTER TABLE STG_FORELE DROP COLUMN FEL_LELMOS;
ALTER TABLE STG_FORELE DROP COLUMN FEL_LELCOL;
ALTER TABLE STG_FORLEL DROP COLUMN LEL_CODPAF;

update STG_CNFGLO set CFG_VALOR = '1.6' where cfg_prop = 'sistra2.version';
update STG_CNFGLO set CFG_VALOR = '0' where cfg_prop = 'sistra2.version.patch';

COMMIT;
