set serveroutput on;
declare 
    cursor cursorScripts is
      SELECT scr_codigo codigo,
             SCR_SCRIPT
             AS script
        FROM stg_script 
       WHERE dbms_lob.substr(SCR_SCRIPT, 1999) LIKE '%invocarDominio%'
          OR dbms_lob.substr(SCR_SCRIPT, 1999,2000) LIKE '%invocarDominio%'
          OR dbms_lob.substr(SCR_SCRIPT, 1999,4000) LIKE '%invocarDominio%'
          OR dbms_lob.substr(SCR_SCRIPT, 1999,6000) LIKE '%invocarDominio%'
          OR dbms_lob.substr(SCR_SCRIPT, 1999,8000) LIKE '%invocarDominio%'
          OR dbms_lob.substr(SCR_SCRIPT, 1999,10000) LIKE '%invocarDominio%'
          OR dbms_lob.substr(SCR_SCRIPT, 1999,12000) LIKE '%invocarDominio%'
          OR dbms_lob.substr(SCR_SCRIPT, 1999,14000) LIKE '%invocarDominio%'
       ORDER BY scr_codigo
        ;
    posicionInvocarDominio NUMBER(18,0);
    posicionComilla NUMBER(18,0);
    posicionComilla2 NUMBER(18,0);
    textoInvocar VARCHAR2(200);
    total NUMBER := 0;
    totalIncorrecto NUMBER := 0;
	totalCorrectos NUMBER := 0;
	totalNoCorregidos NUMBER := 0;
    codi  NUMBER(18,0);
    correctoDominio boolean;
    textoInvocarCorrecto VARCHAR2(400);
    tamanyoScript NUMBER(18,0);
    tamanyoScriptNuevo NUMBER(18,0);
    solucionadoIf VARCHAR2(400); 
    solucionado BOOLEAN;
    solucionadoError VARCHAR2(400);
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
                
                
                IF correctoDominio then
                    DBMS_OUTPUT.PUT_LINE('CORRECTO. codigo:' || lineaScript.CODIGO || ' invocarDominio:' || textoInvocar );
					totalCorrectos := totalCorrectos +1;
                ELSE 
                    
                    textoInvocarCorrecto := obtenerIdentificadorCorrecto(textoInvocar);
                    solucionado := false;
                    solucionadoIf := '';
                    solucionadoError := '';
                    
                    
                    IF (LENGTH(textoInvocarCorrecto) >0 )
                    THEN 
                                /** vamos a proceder a intentar actualizarlo **/
                                totalIncorrecto := totalIncorrecto + 1;  
                                UPDATE STG_SCRIPT
                                     SET  SCR_SCRIPT = REPLACE(SCR_SCRIPT, '''' || textoInvocar || '''' , '''' || textoInvocarCorrecto || '''')
                                  WHERE SCR_CODIGO = lineaScript.CODIGO;
                                
                                SELECT dbms_lob.getlength(SCR_SCRIPT) 
                                  INTO tamanyoScriptNuevo
                                  FROM STG_SCRIPT
                                 WHERE SCR_CODIGO = lineaScript.CODIGO;
                     
                               solucionado := true;
                               IF tamanyoScriptNuevo != tamanyoScript + ( LENGTH(textoInvocarCorrecto) - LENGTH(textoInvocar) )
                               THEN 
                                    solucionadoError := 'ERROR REVISAR PQ NO CUADRAN TAMANYOS (PUEDE PQ HUBIERAN DOS INVOCAR Y HAY QUE REVISAR). ANTES: ' || tamanyoScript || ' DESPUES:' || tamanyoScriptNuevo;
                               END IF;
                     
                    END IF;
                    IF solucionado 
                    THEN
                        DBMS_OUTPUT.PUT_LINE('**CORREGIDO. codigo:' || lineaScript.CODIGO || ' invocarDominio:' || textoInvocar || ' invocarDominioCorrecto:' || textoInvocarCorrecto || ' .' || solucionadoIf || ' ' || solucionadoError);
                    ELSE 
                        DBMS_OUTPUT.PUT_LINE('**INCORRECTO. codigo:' || lineaScript.CODIGO || ' invocarDominio:' || textoInvocar || ' invocarDominioCorrecto:' || textoInvocarCorrecto);
                    END IF;
                END IF; 
            END IF;
          EXCEPTION 
            WHEN OTHERS THEN
                DBMS_OUTPUT.PUT_LINE('Error en el codigo:' || lineaScript.CODIGO || ' SQLCODE:' ||  SQLCODE || ' MENSAJE:' || SUBSTR(SQLERRM, 1, 100));
          END;
    END LOOP;
	totalNoCorregidos := total - (totalIncorrecto + totalCorrectos);
    DBMS_OUTPUT.PUT_LINE('TOTAL:' || total);
    DBMS_OUTPUT.PUT_LINE('TOTAL_SOLUCIONADOS:' || totalIncorrecto);    
	DBMS_OUTPUT.PUT_LINE('TOTAL_CORRECTOS:' || totalCorrectos);    
	DBMS_OUTPUT.PUT_LINE('TOTAL_INCORRECTOS:' || totalNoCorregidos); 
    DBMS_OUTPUT.PUT_LINE('FIN');
EXCEPTION 
  WHEN OTHERS THEN
                DBMS_OUTPUT.PUT_LINE('Error PRINCIPAL en el codigo:' || codi);
                DBMS_OUTPUT.PUT_LINE('TOTAL:' || total);
                DBMS_OUTPUT.PUT_LINE('SQLCODE:' ||  SQLCODE || ' MENSAJE:' || SUBSTR(SQLERRM, 1, 100));
end;
