update SISTRAGES.STG_CNFGLO set CFG_VALOR = '7' where cfg_prop = 'sistra2.version.patch';

UPDATE STG_CNFGLO
SET CFG_DESCR = 'Extensions permeses als annexos de SISTRAMIT.'
WHERE CFG_PROP = 'sistramit.anexos.extensionesPermitidas';

UPDATE STG_CNFGLO SET CFG_DESCR = 'Quan val S, es desactiva el codeMirror i es posa un textarea' WHERE CFG_PROP = 'codemirror.desactivar';
UPDATE STG_CNFGLO SET CFG_DESCR = 'En estar actiu, mostra logs per consola de javascript i java' WHERE CFG_PROP = 'debug.activo';
UPDATE STG_CNFGLO SET CFG_DESCR = 'Nombre de caràcters a partir del qual es llança la cerca al servidor del component cercador dinàmic.' WHERE CFG_PROP = 'formularioNumCharsBusqueda';
UPDATE STG_CNFGLO SET CFG_DESCR = 'Ample de la finestra de digitalització' WHERE CFG_PROP = 'iframeDigitalizacionHeight';
UPDATE STG_CNFGLO SET CFG_DESCR = 'Ample de la finestra de digitalització' WHERE CFG_PROP = 'iframeDigitalizacionWidth';
UPDATE STG_CNFGLO SET CFG_DESCR = 'Ample de la finestra de signatura' WHERE CFG_PROP = 'iframeFirmaHeight';
UPDATE STG_CNFGLO SET CFG_DESCR = 'Ample de la finestra de signatura' WHERE CFG_PROP = 'iframeFirmaWidth';
UPDATE STG_CNFGLO SET CFG_DESCR = 'Màxim d''elements que pot contenir un domini de font de dades perquè superi la validació del tràmit' WHERE CFG_PROP = 'maximo.fd.exportacion';
UPDATE STG_CNFGLO SET CFG_DESCR = 'Control concurrència en registrar (per instància de S2)' WHERE CFG_PROP = 'registro.controlConcurrencia';
UPDATE STG_CNFGLO SET CFG_DESCR = 'Habilitar convertir PDF' WHERE CFG_PROP = 'sistrages.mostrarConvertirPDF';
UPDATE STG_CNFGLO SET CFG_DESCR = 'Timeout accés SISTRAGES (segons)' WHERE CFG_PROP = 'sistrages.timeout';
UPDATE STG_CNFGLO SET CFG_DESCR = 'Indica si s''habilita timeout accés SISTRAGES' WHERE CFG_PROP = 'sistrages.timeoutHabilitar';
UPDATE STG_CNFGLO SET CFG_DESCR = 'Annex tràmit: indica si quan s''annexa signat ha de ser LTV (segell temps)' WHERE CFG_PROP = 'sistramit.anexos.anexoFirmado.PADES_LTV';
UPDATE STG_CNFGLO SET CFG_DESCR = 'Extensions permeses als annexos de SISTRAMIT' WHERE CFG_PROP = 'sistramit.anexos.extensionesPermitidas';

commit;