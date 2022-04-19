set serveroutput on;
declare 
    cursor cursorScripts is
      SELECT scr_codigo codigo,
             dbms_lob.substr(SCR_SCRIPT, 1999) script , 
             dbms_lob.substr(SCR_SCRIPT, 2000,1999) script2
        FROM stg_script 
       WHERE dbms_lob.substr(SCR_SCRIPT, 1999) LIKE '%invocarDominio%'
          OR dbms_lob.substr(SCR_SCRIPT, 2000,1999) LIKE '%invocarDominio%'
        ;
    posicionInvocarDominio NUMBER(18,0);
    posicionComilla NUMBER(18,0);
    posicionComilla2 NUMBER(18,0);
    textoInvocar VARCHAR2(200);
    total NUMBER := 0;
    totalIncorrecto NUMBER := 0;
    codi  NUMBER(18,0);
    correctoDominio boolean;
    textoInvocarCorrecto VARCHAR2(400);
begin
    DBMS_OUTPUT.PUT_LINE('INICIO');
    FOR lineaScript in cursorScripts
    LOOP
        BEGIN
        total := total +1;
        codi := lineaScript.CODIGO;
       
            select INSTR(lineaScript.script, 'invocarDominio') into posicionInvocarDominio from dual;
            IF posicionInvocarDominio > 0 THEN
               select INSTR(lineaScript.script, '''', posicionInvocarDominio) into posicionComilla from dual;
            END IF;
            IF posicionInvocarDominio > 0 AND posicionComilla > 0 THEN
               select INSTR(lineaScript.script, '''', posicionComilla+1) into posicionComilla2 from dual;
            END IF;
            
            
            IF posicionInvocarDominio > 0 AND posicionComilla > 0 AND posicionComilla2 > 0 THEN
                select substr(lineaScript.script, posicionComilla+1, (posicionComilla2 - posicionComilla-1)) into textoInvocar from dual;
                correctoDominio := isIdentificadorCorrecto(textoInvocar);
                IF correctoDominio THEN
                    DBMS_OUTPUT.PUT_LINE('CORRECTO. codigo:' || lineaScript.CODIGO || ' invocarDominio:' || textoInvocar );
                ELSE 
                    
                    textoInvocarCorrecto := obtenerIdentificadorCorrecto(textoInvocar);
                    IF (LENGTH(textoInvocarCorrecto) >0 )
                    THEN 
                      totalIncorrecto := totalIncorrecto + 1;  
                    END IF;
                    DBMS_OUTPUT.PUT_LINE('**INCORRECTO. codigo:' || lineaScript.CODIGO || ' invocarDominio:' || textoInvocar || ' invocarDominioCorrecto:' || textoInvocarCorrecto);
                END IF; 
            END IF;
          EXCEPTION 
            WHEN OTHERS THEN
                DBMS_OUTPUT.PUT_LINE('Error en el codigo:' || lineaScript.CODIGO);
          END;
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('TOTAL:' || total);
    DBMS_OUTPUT.PUT_LINE('TOTAL_INCORRECTO:' || totalIncorrecto);    
    DBMS_OUTPUT.PUT_LINE('FIN');
EXCEPTION 
  WHEN OTHERS THEN
                DBMS_OUTPUT.PUT_LINE('Error PRINCIPAL en el codigo:' || codi);
                DBMS_OUTPUT.PUT_LINE('TOTAL:' || total);
                DBMS_OUTPUT.PUT_LINE('SQLCODE:' ||  SQLCODE || ' MENSAJE:' || SUBSTR(SQLERRM, 1, 100));
end;
