alter table STT_FORMUL add    SFR_ENTIDA           VARCHAR2(10 CHAR);
comment on column STT_FORMUL.SFR_ENTIDA is 'Código entidad';

alter table STT_FORMUL add    SFR_TCKGFE           VARCHAR2(100 CHAR);
comment on column STT_FORMUL.SFR_TCKGFE is 'Ticket OTP del GFE';

alter table STT_FORMUL add SFR_IDGFE            VARCHAR2(20 CHAR);
comment on column STT_FORMUL.SFR_IDGFE is 'Id gestor formulario externo';