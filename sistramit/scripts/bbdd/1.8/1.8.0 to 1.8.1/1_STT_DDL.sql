/** FH : AÑADIMOS COLUMNAS NECESARIAS PARA PODER AVISAR A RFHAB. **/
ALTER TABLE SISTRAMIT.STT_TRAPER ADD TRP_FHIDAC VARCHAR2(100 CHAR);
comment on column STT_TRAPER.TRP_FHIDAC is 'Funcionario habilitado: Id actuación';

ALTER TABLE SISTRAMIT.STT_TRAFIN ADD TRF_FHIDAC VARCHAR2(100 CHAR);
comment on column STT_TRAFIN.TRF_FHIDAC is 'Funcionario habilitado: Id actuación';
ALTER TABLE SISTRAMIT.STT_TRAFIN ADD TRF_FHAVISO DATE;
comment on column STT_TRAFIN.TRF_FHAVISO is 'Funcionario habilitado: Fecha aviso de fin trámite (si tiene id actuación FH)';
ALTER TABLE SISTRAMIT.STT_TRAFIN ADD TRF_FHAERR VARCHAR2(4000 CHAR);
comment on column STT_TRAFIN.TRF_FHAERR is 'Funcionario habilitado: Error en aviso de fin trámite (si tiene id actuación FH)';

ALTER TABLE SISTRAMIT.STT_TRAPER ADD TRP_ENTIDAD VARCHAR2(20 CHAR);
comment on column STT_TRAPER.TRP_ENTIDAD is 'Código entidad';

ALTER TABLE SISTRAMIT.STT_TRAFIN ADD TRF_ENTIDAD VARCHAR2(20 CHAR);
comment on column STT_TRAFIN.TRF_ENTIDAD is 'Código entidad';

