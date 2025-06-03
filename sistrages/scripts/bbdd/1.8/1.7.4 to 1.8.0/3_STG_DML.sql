update SISTRAGES.STG_CNFGLO set CFG_VALOR = '1.8' where cfg_prop = 'sistra2.version';
update SISTRAGES.STG_CNFGLO set CFG_VALOR = '0' where cfg_prop = 'sistra2.version.patch';

insert into SISTRAGES.STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) values (STG_CNFGLO_SEQ.nextval, 'iframeDigitalizacionWidth', '500', 'Ancho de la ventana de digitalización');
insert into SISTRAGES.STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) values (STG_CNFGLO_SEQ.nextval, 'iframeDigitalizacionHeight', '500', 'Ancho de la ventana de digitalización');
insert into SISTRAGES.STG_CNFGLO (CFG_CODIGO, CFG_PROP, CFG_VALOR, CFG_DESCR) values (STG_CNFGLO_SEQ.nextval, 'sistrages.mostrarConvertirPDF', 'false', 'Habilitar convertir PDF');

commit;