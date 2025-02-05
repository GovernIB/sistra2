--Se igualan los checks "Firmar" y "Anexar firmado" en los anexos con una instancia
UPDATE STG_ANETRA SET ANE_FIRMAR = 1 WHERE ANE_NUMINS = 1 AND ANE_FIRMAD = 1;
UPDATE STG_ANETRA SET ANE_FIRMAD = 1 WHERE ANE_NUMINS = 1 AND ANE_FIRMAR = 1;
COMMIT;
