UPDATE STG_CNFGLO
SET CFG_DESCR = CFG_DESCR || ' Los trámites se purgan en 2 fases: En la primera fase se borran los documentos asociados y solo se deja la información de los eventos, los elementos de tramitación se marcan para purgar. En la segunda fase se eliminan definitivamente.'
WHERE CFG_PROP = 'sistramit.purga.purgados';

commit;
