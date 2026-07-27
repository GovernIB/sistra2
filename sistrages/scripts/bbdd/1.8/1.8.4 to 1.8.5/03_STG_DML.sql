UPDATE STG_CNFGLO
SET CFG_DESCR = 'Dies, després de la data de marcatge per purgar, a partir dels quals els tràmits marcats per purgar seran esborrats definitivament. Els tràmits es purguen en 2 fases: A la primera fase s''esborren els documents associats i només es deixa la informació dels esdeveniments, els elements de tramitació es marquen per purgar. A la segona fase s''eliminen definitivament.'
WHERE CFG_PROP = 'sistramit.purga.purgados';

commit;