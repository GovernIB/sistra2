INSERT INTO STG_CNFGLO (
    CFG_CODIGO, 
    CFG_PROP, 
    CFG_VALOR, 
    CFG_DESCR, 
    CFG_NOMOD
) VALUES (
    STG_CNFGLO_SEQ.NEXTVAL, 
    'maximo.fd.exportacion', 
    '200', 
    'Máximo de elementos que puede contener un dominio de fuente de datos, para que supere la validación del trámite', 
    0
);

commit;