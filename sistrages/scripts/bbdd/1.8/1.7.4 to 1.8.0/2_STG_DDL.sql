ALTER TABLE SISTRAGES.STG_ENTIDA ADD ENT_AYCTFORM NUMBER(1) default 1 not null;
comment on column STG_ENTIDA.ENT_AYCTFORM is 'Indica si esta habilitado mostrar la ayuda contextual en los formularios';

alter table SISTRAGES.STG_ENTIDA add ENT_MODFUNCHAB NUMBER(1) default 0 not null;
comment on column STG_ENTIDA.ENT_MODFUNCHAB is 'Indica si está habilitado el modo funcionario habilitado';
