ALTER TABLE SISTRAGES.STG_VERTRA
    ADD VTR_NORMATIVA VARCHAR2(1 CHAR) DEFAULT 'G' NOT NULL;

COMMENT ON COLUMN STG_VERTRA.VTR_NORMATIVA IS 'Normativa: General (G) / Específica (E)';

alter table SISTRAGES.STG_ENTIDA add ENT_PERMEXTSUST number(1,0) default 0 not null;
alter table SISTRAGES.STG_ENTIDA add ENT_SUSTCERT number(1,0) default 0 not null;
comment on column STG_ENTIDA.ENT_PERMEXTSUST is 'Permitir cualquier extensión en niveles de seguridad sustanciales';
comment on column STG_ENTIDA.ENT_SUSTCERT is 'Permitir nivel de seguridad "Sustancial (firma con certificado)"';

alter table  SISTRAGES.STG_VERTRA add VTR_NIVSEG number(10,0) default 1;
comment on column STG_VERTRA.VTR_NIVSEG is 'Nivel de seguridad: 1 (Bajo), 2 (Sustancial), 3 (Sustancial con certificado), 4 (Alto)';