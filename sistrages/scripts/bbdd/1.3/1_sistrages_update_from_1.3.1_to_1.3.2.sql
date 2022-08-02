alter table STG_ENTIDA add ENT_ACCTT NUMBER(18,0);

COMMENT ON COLUMN STG_ENTIDA.ENT_ACCTT
   IS 'Declaración accesibilidad (HTML)';

alter table STG_ENTIDA
add constraint STG_ENTIDA_TRADUC_FK13 foreign key (ENT_ACCTT) references STG_TRADUC(TRA_CODIGO);

update STG_CNFGLO set CFG_VALOR = '2' where cfg_prop = 'sistra2.version.patch';