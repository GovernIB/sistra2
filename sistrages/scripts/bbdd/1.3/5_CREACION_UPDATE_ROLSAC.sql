select
t.trm_identi,
'UPDATE rsc_tramit SET tra_idtramtel='''||
e.ent_identi || '.' ||a.are_identi ||'.' || t.trm_identi
|| ''' WHERE tra_idtramtel ='''||t.trm_identi|| ''' and tra_codplt=2'
from stg_tramit t, stg_area a, stg_entida e where t.trm_codare = a.are_codigo and a.are_codent=e.ent_codigo order by a.are_identi;