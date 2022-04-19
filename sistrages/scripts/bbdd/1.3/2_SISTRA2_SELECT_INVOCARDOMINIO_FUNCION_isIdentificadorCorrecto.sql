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



