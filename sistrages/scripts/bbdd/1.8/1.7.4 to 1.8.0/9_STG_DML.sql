UPDATE sistrages.stg_anetra anexo
SET anexo.ane_extper = 'pdf'
WHERE (anexo.ane_firmar = 1 AND anexo.ane_firmad = 1) 
  AND (
    (REGEXP_LIKE(anexo.ane_extper, '(^|;)([^;]*[^pP][^dD][^fF][^;]*|\\*)(;|$)') 
     AND NOT REGEXP_LIKE(anexo.ane_extper, '^(pdf;?)+$')) 
    AND (NOT REGEXP_LIKE(anexo.ane_extper, '^;*pdf;*$')) 
    OR (REGEXP_LIKE(anexo.ane_extper, '^[^;]+$') AND LOWER(anexo.ane_extper) != 'pdf') 
    OR anexo.ane_extper = '*'
  );