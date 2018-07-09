insert into STG_IDIOMA (IDI_IDENTI) VALUES ('es');
insert into STG_IDIOMA (IDI_IDENTI) VALUES ('ca');
insert into STG_IDIOMA (IDI_IDENTI) VALUES ('en');
insert into STG_IDIOMA (IDI_IDENTI) VALUES ('de');

insert into STG_PROCES (PROC_IDENT,PROC_INSTAN,PROC_FECHA) values ('MAESTRO','NONE', sysdate);

insert into  STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) values (STG_CNFGLO_SEQ.NEXTVAL, 'definicionTramite.lenguajeDefecto','ca','Lenguaje por defecto si no existe el lenguaje indicado');              



SET SERVEROUTPUT ON;
DECLARE
 
  /* ID TIPOS . **/
  vIdDebeSaber NUMBER;
  vRellenar    NUMBER;
  vAnexar      NUMBER;
  vTasa        NUMBER;
  vRegistrar   NUMBER;
 
  /* ID TRADUCCIONES. **/
  vLiteral1   NUMBER;
  vLiteral2   NUMBER;
  vLiteral3   NUMBER;
  vLiteral4   NUMBER;
  vLiteral5   NUMBER;
  
  /* ID TRADUCCION CA/ES. **/
  vTraduccion1ca  NUMBER;
  vTraduccion1es  NUMBER;
  vTraduccion2ca  NUMBER;
  vTraduccion2es  NUMBER;
  vTraduccion3ca  NUMBER;
  vTraduccion3es  NUMBER;
  vTraduccion4ca  NUMBER;
  vTraduccion4es  NUMBER;
  vTraduccion5ca  NUMBER;
  vTraduccion5es  NUMBER;
  
    
BEGIN
    DBMS_OUTPUT.PUT_LINE('INICIA');

    SELECT STG_TRADUC_SEQ.NEXTVAL INTO vLiteral1 FROM DUAL;
    SELECT STG_TRADUC_SEQ.NEXTVAL INTO vLiteral2 FROM DUAL;
    SELECT STG_TRADUC_SEQ.NEXTVAL INTO vLiteral3 FROM DUAL;
    SELECT STG_TRADUC_SEQ.NEXTVAL INTO vLiteral4 FROM DUAL;
    SELECT STG_TRADUC_SEQ.NEXTVAL INTO vLiteral5 FROM DUAL;
    
    SELECT STG_TRAIDI_SEQ.NEXTVAL INTO vTraduccion1ca FROM DUAL;
    SELECT STG_TRAIDI_SEQ.NEXTVAL INTO vTraduccion1es FROM DUAL;
    SELECT STG_TRAIDI_SEQ.NEXTVAL INTO vTraduccion2ca FROM DUAL;
    SELECT STG_TRAIDI_SEQ.NEXTVAL INTO vTraduccion2es FROM DUAL;
    SELECT STG_TRAIDI_SEQ.NEXTVAL INTO vTraduccion3ca FROM DUAL;
    SELECT STG_TRAIDI_SEQ.NEXTVAL INTO vTraduccion3es FROM DUAL;
    SELECT STG_TRAIDI_SEQ.NEXTVAL INTO vTraduccion4ca FROM DUAL;
    SELECT STG_TRAIDI_SEQ.NEXTVAL INTO vTraduccion4es FROM DUAL;
    SELECT STG_TRAIDI_SEQ.NEXTVAL INTO vTraduccion5ca FROM DUAL;
    SELECT STG_TRAIDI_SEQ.NEXTVAL INTO vTraduccion5es FROM DUAL;
    
    SELECT STG_TIPPTR_SEQ.NEXTVAL INTO vIdDebeSaber FROM DUAL;
    SELECT STG_TIPPTR_SEQ.NEXTVAL INTO vRellenar FROM DUAL;
    SELECT STG_TIPPTR_SEQ.NEXTVAL INTO vAnexar FROM DUAL;
    SELECT STG_TIPPTR_SEQ.NEXTVAL INTO vTasa FROM DUAL;
    SELECT STG_TIPPTR_SEQ.NEXTVAL INTO vRegistrar FROM DUAL;
    
    --Traducciones
    INSERT INTO STG_TRADUC (TRA_CODIGO) VALUES (vLiteral1);
    INSERT INTO STG_TRADUC (TRA_CODIGO) VALUES (vLiteral2);
    INSERT INTO STG_TRADUC (TRA_CODIGO) VALUES (vLiteral3);
    INSERT INTO STG_TRADUC (TRA_CODIGO) VALUES (vLiteral4);
    INSERT INTO STG_TRADUC (TRA_CODIGO) VALUES (vLiteral5);
    
    --Traduccion
    INSERT INTO STG_TRAIDI (TRI_CODIGO, TRI_CODTRA, TRI_LITERA, TRI_IDIOMA) VALUES (vTraduccion1ca, vLiteral1, 'Cal Saber', 'ca');
    INSERT INTO STG_TRAIDI (TRI_CODIGO, TRI_CODTRA, TRI_LITERA, TRI_IDIOMA) VALUES (vTraduccion1es, vLiteral1, 'Debe Saber', 'es');
    INSERT INTO STG_TRAIDI (TRI_CODIGO, TRI_CODTRA, TRI_LITERA, TRI_IDIOMA) VALUES (vTraduccion2ca, vLiteral2, 'Emplenar', 'ca');
    INSERT INTO STG_TRAIDI (TRI_CODIGO, TRI_CODTRA, TRI_LITERA, TRI_IDIOMA) VALUES (vTraduccion2es, vLiteral2, 'Rellenar', 'es');
    INSERT INTO STG_TRAIDI (TRI_CODIGO, TRI_CODTRA, TRI_LITERA, TRI_IDIOMA) VALUES (vTraduccion3ca, vLiteral3, 'Annexar', 'ca');
    INSERT INTO STG_TRAIDI (TRI_CODIGO, TRI_CODTRA, TRI_LITERA, TRI_IDIOMA) VALUES (vTraduccion3es, vLiteral3, 'Anexar', 'es');
    INSERT INTO STG_TRAIDI (TRI_CODIGO, TRI_CODTRA, TRI_LITERA, TRI_IDIOMA) VALUES (vTraduccion4ca, vLiteral4, 'Pagar taxa', 'ca');
    INSERT INTO STG_TRAIDI (TRI_CODIGO, TRI_CODTRA, TRI_LITERA, TRI_IDIOMA) VALUES (vTraduccion4es, vLiteral4, 'Pagar tasa', 'es');
    INSERT INTO STG_TRAIDI (TRI_CODIGO, TRI_CODTRA, TRI_LITERA, TRI_IDIOMA) VALUES (vTraduccion5ca, vLiteral5, 'Registrar', 'ca');
    INSERT INTO STG_TRAIDI (TRI_CODIGO, TRI_CODTRA, TRI_LITERA, TRI_IDIOMA) VALUES (vTraduccion5es, vLiteral5, 'Registrar', 'es');
    
    --Tipo
    INSERT INTO STG_TIPPTR (TIP_CODIGO, TIP_PASO, TIP_DESCOR, TIP_ORDEN) VALUES (vIdDebeSaber,  'DebeSaber', vLiteral1, 1);
    INSERT INTO STG_TIPPTR (TIP_CODIGO, TIP_PASO, TIP_DESCOR, TIP_ORDEN) VALUES (vRellenar,     'Rellenar',  vLiteral2, 2);
    INSERT INTO STG_TIPPTR (TIP_CODIGO, TIP_PASO, TIP_DESCOR, TIP_ORDEN) VALUES (vAnexar,       'Anexar',    vLiteral3, 3);
    INSERT INTO STG_TIPPTR (TIP_CODIGO, TIP_PASO, TIP_DESCOR, TIP_ORDEN) VALUES (vTasa,         'Tasa',      vLiteral4, 4);
    INSERT INTO STG_TIPPTR (TIP_CODIGO, TIP_PASO, TIP_DESCOR, TIP_ORDEN) VALUES (vRegistrar,    'Registrar', vLiteral5, 5);

    DBMS_OUTPUT.PUT_LINE('FINALIZA');
    COMMIT;
END;
