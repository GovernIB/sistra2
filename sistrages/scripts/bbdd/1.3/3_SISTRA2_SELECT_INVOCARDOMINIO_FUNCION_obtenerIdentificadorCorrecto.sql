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