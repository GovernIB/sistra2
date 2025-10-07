UPDATE sistrages.stg_anetra anexo
SET anexo.ANE_FIRMAD = 0,
    anexo.ANE_FIRMAR = 0,
    anexo.ANE_VALFIRM = 0
WHERE EXISTS (
  SELECT 1
  FROM sistrages.stg_pasotr paso
  JOIN sistrages.stg_vertra version ON paso.PTR_CODVTR = version.VTR_CODIGO
  WHERE anexo.ANE_CODPTR = paso.PTR_CODIGO AND
    (version.VTR_NORMATIVA = 'E' AND version.VTR_AUTENO = 1)
);

commit;