update STG_CNFGLO set CFG_VALOR = '1.7' where cfg_prop = 'sistra2.version';
update STG_CNFGLO set CFG_VALOR = '0' where cfg_prop = 'sistra2.version.patch';

update STG_CNFGLO set CFG_DESCR = 'Hores, després de la data de caducitat, a partir de les quals els tràmits persistents caducats seran marcats per purgar.' where cfg_prop = 'sistramit.purga.caducados';
update STG_CNFGLO set CFG_DESCR = 'Dies, després dels quals els errors interns (no associats a un tràmit) seran purgats. Si algun paràmetre es configura amb 0 no es purga.' where cfg_prop = 'sistramit.purga.erroresInternos';
update STG_CNFGLO set CFG_DESCR = 'Hores, després de la data de finalització, a partir de les quals els tràmits finalitzats seran marcats per purgar.' where cfg_prop = 'sistramit.purga.finalizado';
update STG_CNFGLO set CFG_DESCR = 'Minuts, després de la data de finalització, a partir dels quals les sessions de formularis finalitzades seran purgades.' where cfg_prop = 'sistramit.purga.formulario.fin';
update STG_CNFGLO set CFG_DESCR = 'Minuts, després de la data d''inici, a partir dels quals les sessions de formularis no finalitzades seran purgades.' where cfg_prop = 'sistramit.purga.formulario.nofin';
update STG_CNFGLO set CFG_DESCR = 'Hores després de les quals les invalidacions de tràmits i dominis seran purgades. El valor ha de ser més gran que el TimeToLive (si no existeix el TimeToIdle) de les caches de tràmits i dominis.' where cfg_prop = 'sistramit.purga.invalidaciones';
update STG_CNFGLO set CFG_DESCR = 'Hores, després de la data d''últim accés, a partir de les quals els tràmits no persistents no finalitzats seran marcats per purgar.' where cfg_prop = 'sistramit.purga.noPersistente';
update STG_CNFGLO set CFG_DESCR = 'Minuts, després de la data de finalització, a partir dels quals les sessions de pagaments finalitzades seran purgades.' where cfg_prop = 'sistramit.purga.pago.fin';
update STG_CNFGLO set CFG_DESCR = 'Minuts, després de la data d''inici, a partir dels quals les sessions de pagaments no finalitzades seran purgades.' where cfg_prop = 'sistramit.purga.pago.nofin';
update STG_CNFGLO set CFG_DESCR = 'Dies, després de la data d''ultima modificació, a partir dels quals els tràmits amb pagament realitzat seran marcats per purgar.' where cfg_prop = 'sistramit.purga.pendientePurgaPago';
update STG_CNFGLO set CFG_DESCR = 'Dies, després de la data d''últim accés, a partir dels quals els tràmits persistents que no tenen data de caducitat seran marcats per purgar.' where cfg_prop = 'sistramit.purga.persistenteSinCaducidad';
update STG_CNFGLO set CFG_DESCR = 'Dies, després de la data de marcatge per purgar, a partir dels quals els tràmits marcats per purgar seran esborrats definitivament.' where cfg_prop = 'sistramit.purga.purgados';

delete STG_CNFGLO where cfg_prop = 'sistramit.purga.erroresInternos.limite';
delete STG_CNFGLO where cfg_prop = 'sistramit.purga.ficherosHuerfanos.limite';
delete STG_CNFGLO where cfg_prop = 'sistramit.purga.formulario.limite';
delete STG_CNFGLO where cfg_prop = 'sistramit.purga.pago.limite';
delete STG_CNFGLO where cfg_prop = 'sistramit.purga.tramites.limite';
delete STG_CNFGLO where cfg_prop = 'sistramit.purga.tramitesPurgados.limite';

COMMIT;
