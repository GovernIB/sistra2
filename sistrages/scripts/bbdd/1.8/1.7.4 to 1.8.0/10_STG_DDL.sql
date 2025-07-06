alter table SISTRAGES.STG_ROLARE add RLA_TIPOUSU varchar(3) default 'GT';
comment on column STG_ROLARE.RLA_TIPOUSU is 'Tipo de usuario: GT (Gestor), CAU (Personal CAU)';
