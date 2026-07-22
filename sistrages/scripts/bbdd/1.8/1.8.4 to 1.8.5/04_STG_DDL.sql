ALTER table SISTRAGES.STG_PASPAG ADD PPG_SCRDIN  NUMBER(18);
comment on column STG_PASPAG.PPG_SCRDIN is 'Script pagos dinámicos';
alter table SISTRAGES.STG_PASPAG  add constraint STG_PASPAG_SCRIPT_FK foreign key (PPG_SCRDIN) references STG_SCRIPT (SCR_CODIGO);
