--Se crean columnas nuevas para hacer copia de seguridad
ALTER TABLE STG_VERTRA ADD VTR_AUTMET_OLD VARCHAR(50);

comment on column STG_VERTRA.VTR_AUTMET_OLD is
'Indica métodos autenticación separados por punto y coma: Certificado (CER), Clave Móvil (MOV), Clave Permanente (PER)';
