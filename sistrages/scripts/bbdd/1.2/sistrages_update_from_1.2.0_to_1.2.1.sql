/** --- V1.2.1 --- **/

update STG_CNFGLO set CFG_VALOR = '1' where cfg_prop = 'sistra2.version.patch';
update STG_CNFGLO set CFG_DESCR = 'Indica el tamaño máximo que puede tener la suma de todos los anexos de un trámite. La definición debe tener el siguiente formato: tamaño + (KB/MB). Ejemplo 20MB.' where cfg_prop = 'sistramit.anexos.tamanyoMaximo';
insert into STG_CNFGLO (CFG_CODIGO,CFG_PROP,CFG_VALOR,CFG_DESCR,CFG_NOMOD) values (STG_CNFGLO_SEQ.NEXTVAL,'sistramit.anexos.tamanyoMaximoIndividual','20MB','Indica el tamaño máximo que puede tener cada documento anexado individualmente. La definición debe tener el siguiente formato: tamaño + (KB/MB). Ejemplo 10MB.','0');
commit;
UPDATE STG_CNFGLO SET CFG_DESCR = 'Versión Sistra2' WHERE CFG_PROP= 'sistrages.version';

ALTER TABLE STG_VERTRA ADD VTR_DESCR VARCHAR2(255 CHAR);
comment on column STG_VERTRA.VTR_DESCR is 'Descripcion breve de la version';

/*** COMENTARIOS PROPS GLOBALES EN CATALÁN ***/
update STG_CNFGLO set CFG_DESCR = 'Propietat per externalitzar l''ajuda del mòdul SISTRAGES a un directori extern (s''indica la ruta del directori on s''ubicarà l''ajuda). Si aquesta propietat no té valor s''usarà l''ajuda inclosa a l''aplicació.' where cfg_prop = 'ayuda.sistrages.path';
update STG_CNFGLO set CFG_DESCR = 'Propietat per externalitzar l''ajuda del mòdul SISTRAHELP a un directori extern (s''indica la ruta del directori on s''ubicarà l''ajuda). Si aquesta propietat no té valor s''usarà l''ajuda inclosa a l''aplicació.' where cfg_prop = 'ayuda.sistrahelp.path';
update STG_CNFGLO set CFG_DESCR = 'Comprovar si les dades creuades amb el catàleg de procediments és correcte.' where cfg_prop = 'catalogoProcedimientos.verificarTramite';
update STG_CNFGLO set CFG_DESCR = 'Llengua per defecte si no existeix la llengua indicada' where cfg_prop = 'definicionTramite.lenguajeDefecto';
update STG_CNFGLO set CFG_DESCR = 'Paràmetre per alterar les propietats d''un growl a nivell global (sticky i temps)' where cfg_prop = 'growl.propiedades';
update STG_CNFGLO set CFG_DESCR = 'Prefix base per als plugins usats a Sistra2' where cfg_prop = 'plugins.prefix';
update STG_CNFGLO set CFG_DESCR = 'Núm. màx de tràmits a consultar en pèrdua de clau' where cfg_prop = 'sistrahelp.perdidaClave.numMaxTramites';
update STG_CNFGLO set CFG_DESCR = 'Extensions permeses per a l''annex del formulari de suport (llista separada per comes).' where cfg_prop = 'sistramit.anexoSoporte.extensiones';
update STG_CNFGLO set CFG_DESCR = 'Mida màxima permesa per a l''annex del formulari de suport (MB).' where cfg_prop = 'sistramit.anexoSoporte.tamanyo';
update STG_CNFGLO set CFG_DESCR = 'Indica la mida màxima que pot tenir la suma de tots els annexos d''un tràmit. La definició ha de tenir el format següent: mida + (KB/MB). Exemple 20MB.' where cfg_prop = 'sistramit.anexos.tamanyoMaximo';
update STG_CNFGLO set CFG_DESCR = 'Indica la mida màxima que pot tenir cada document annexat individualment. La definició ha de tenir el format següent: mida + (KB/MB). Exemple 10MB.' where cfg_prop = 'sistramit.anexos.tamanyoMaximoIndividual';
update STG_CNFGLO set CFG_DESCR = 'Idiomes sistramit. En cas de canviar els idiomes, cal reiniciar el servidor.' where cfg_prop = 'sistramit.idiomas';
update STG_CNFGLO set CFG_DESCR = 'Temps (hores) després de la data de caducitat després del qual seran purgats els tràmits persistents caducats.' where cfg_prop = 'sistramit.purga.caducados';
update STG_CNFGLO set CFG_DESCR = 'Temps de purgat (si algun paràmetre es configura amb 0 no es purga), essent Temps (dies) que romandran els errors interns (no associats a un tràmit)' where cfg_prop = 'sistramit.purga.erroresInternos';
update STG_CNFGLO set CFG_DESCR = 'Temps (hores) després de la seva data de finalització després del qual un tràmit finalitzat serà purgat.' where cfg_prop = 'sistramit.purga.finalizado';
update STG_CNFGLO set CFG_DESCR = 'Temps (minuts) després del qual seran esborrades les sessions de formularis, si la sessió de formulari aquesta finalitzada s''elimina després del temps indicat a partir de la seva finalització' where cfg_prop = 'sistramit.purga.formulario.fin';
update STG_CNFGLO set CFG_DESCR = 'Temps (minuts) després del qual seran esborrades les sessions de formularis, si la sessió de formulari no està finalitzada s''elimina després del temps indicat a partir del seu inici' where cfg_prop = 'sistramit.purga.formulario.nofin';
update STG_CNFGLO set CFG_DESCR = 'Temps (hores) després que les invalidacions de tràmits i dominis es purguin, ha de ser més gran que el TimeToLive (o si existeix el TimeToIdle) de les caches de tràmits i dominis.' where cfg_prop = 'sistramit.purga.invalidaciones';
update STG_CNFGLO set CFG_DESCR = 'Temps (hores) després de la data d''últim accés després del qual seran purgats els tràmits no persistents no finalitzats.' where cfg_prop = 'sistramit.purga.noPersistente';
update STG_CNFGLO set CFG_DESCR = 'Temps (minuts) després del qual seran esborrades els pagaments, si la sessió de pagaments està finalitzada s''eliminarà després del temps indicat a partir de la finalització' where cfg_prop = 'sistramit.purga.pago.fin';
update STG_CNFGLO set CFG_DESCR = 'Temps (minuts) després del qual seran esborrades els pagaments, si la sessió de pagaments no està finalitzada s''eliminarà després del temps indicat a partir del seu inici' where cfg_prop = 'sistramit.purga.pago.nofin';
update STG_CNFGLO set CFG_DESCR = 'Dies després de la darrera modificació del tràmit després dels quals s''esborrarà tràmit pendent de purgar per pagament realitzat.' where cfg_prop = 'sistramit.purga.pendientePurgaPago';
update STG_CNFGLO set CFG_DESCR = 'Temps (dies) després de la data d''últim accés després del qual seran purgats els tràmits persistents que no tenen data de caducitat.' where cfg_prop = 'sistramit.purga.persistenteSinCaducidad';
update STG_CNFGLO set CFG_DESCR = 'Temps (dies) després de la data de purgat després del qual seran definitivament esborrats els purgats.' where cfg_prop = 'sistramit.purga.purgados';
update STG_CNFGLO set CFG_DESCR = 'Contrasenya' where cfg_prop = 'sistramit.rest.pwd';
update STG_CNFGLO set CFG_DESCR = 'Versió Sistra2' where cfg_prop = 'sistra2.version';
update STG_CNFGLO set CFG_DESCR = 'Versió menor de Sistra2 (sistra2.version.x)' where cfg_prop = 'sistra2.version.patch';
update STG_CNFGLO set CFG_DESCR = 'Indica si es pot mostrar el botó de codi font o no al tinymce (només s''hauria d''habilitar temporalment per a depuració). Valors possibles: true/false' where cfg_prop = 'tinymce.code';
update STG_CNFGLO set CFG_DESCR = 'Usuari REST sistramit' where cfg_prop = 'sistramit.rest.user';

