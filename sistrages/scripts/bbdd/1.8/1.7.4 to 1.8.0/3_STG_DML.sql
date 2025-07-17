update SISTRAGES.STG_CNFGLO set CFG_VALOR = '1.8' where cfg_prop = 'sistra2.version';
update SISTRAGES.STG_CNFGLO set CFG_VALOR = '0' where cfg_prop = 'sistra2.version.patch';

UPDATE STG_CNFGLO SET CFG_DESCR = 'Temps (minuts) després del qual seran esborrats els tiquets de redirecció al component de pagaments que han estat usats (redirecció de S2 a component pagaments i retorn a S2). No té implicació a la sessió de pagament.' WHERE CFG_PROP = 'sistramit.purga.pago.fin';
UPDATE STG_CNFGLO SET CFG_DESCR = 'Temps (minuts) després del qual seran esborrats els tiquets de redirecció al component de pagaments que no han estat usats (redirecció de S2 a component pagaments però no s''ha retornat a S2). No té implicació a la sessió de pagament.' WHERE CFG_PROP = 'sistramit.purga.pago.nofin';

insert into SISTRAGES.STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) values (STG_CNFGLO_SEQ.nextval, 'iframeDigitalizacionWidth', '500', 'Ancho de la ventana de digitalización');
insert into SISTRAGES.STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) values (STG_CNFGLO_SEQ.nextval, 'iframeDigitalizacionHeight', '500', 'Ancho de la ventana de digitalización');
insert into SISTRAGES.STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) values (STG_CNFGLO_SEQ.nextval, 'sistrages.mostrarConvertirPDF', 'false', 'Habilitar convertir PDF');

commit;