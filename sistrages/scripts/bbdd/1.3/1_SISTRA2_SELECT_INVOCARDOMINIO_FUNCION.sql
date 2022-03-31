 

create or replace function isIdentificadorCorrecto(identificador in VARCHAR2)    
return BOOLEAN    
is      
contieneGLOBAL number;
cuantosPUNTOS number;
begin        
      SELECT  INSTR(IDENTIFICADOR, 'GLOBAL.?') INTO contieneGLOBAL
        FROM DUAL;
      
      IF contieneGLOBAL > 0 
      THEN
          -- ES GLOBAL.
          RETURN TRUE;
      ELSE
          --Restamos para saber el total de puntos, 1 punto si es de tipo entidad y 2 puntos si es ambito área
          select length(IDENTIFICADOR) - length(replace(IDENTIFICADOR,'.',null)) INTO cuantosPUNTOS
          from dual;
          
          IF cuantosPUNTOS = 1 OR cuantosPUNTOS = 2
          THEN
              --CORRECTO PORQUE ES DE AMBITO AREA O ENTIDAD
              RETURN TRUE;
          ELSE 
              RETURN FALSE;   
          END IF;
          RETURN FALSE;
      END IF;
end;   




create or replace function obtenerIdentificadorCorrecto(identificador in VARCHAR2)    
return VARCHAR2    
is      
identificadorCompuesto varchar2(50);
identificadorEntidad varchar2(50);
identificadorArea varchar2(50);
codigoEntidad number;
CURSOR dominios (identificador varchar2) IS
    SELECT DOM_AMBITO, DOM_CODENT, DOM_CODARE
      FROM STG_DOMINI
      WHERE DOM_IDENTI LIKE identificador;

 
begin        
      FOR dominio IN dominios(identificador)
      LOOP
          IF dominio.DOM_AMBITO = 'G'
          THEN
              identificadorCompuesto := 'GLOBAL.' || IDENTIFICADOR;
          ELSIF dominio.DOM_AMBITO = 'E'
          THEN
               SELECT ENT_IDENTI
                 INTO identificadorEntidad
                 FROM STG_ENTIDA
                 WHERE ENT_CODIGO = dominio.DOM_CODENT;
              identificadorCompuesto := identificadorEntidad || '.' || IDENTIFICADOR;
          ELSE 
              SELECT ARE_IDENTI, ARE_CODENT
                 INTO identificadorAREA, codigoEntidad
                 FROM STG_AREA
                 WHERE ARE_CODIGO = dominio.DOM_CODARE;
             
             SELECT ENT_IDENTI
                 INTO identificadorEntidad
                 FROM STG_ENTIDA
                 WHERE ENT_CODIGO = codigoEntidad;
              identificadorCompuesto := identificadorEntidad || '.' || identificadorArea || '.' || IDENTIFICADOR;
          END IF;
      END LOOP;
      RETURN identificadorCompuesto;
end;   