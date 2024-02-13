create table STH_AVISCONFIG
(
   AVI_CODIGO           NUMBER(18)           not null,
   AVI_NOMBRE           VARCHAR2(20 CHAR)    not null,
   AVI_MAIL           	VARCHAR2(1024 CHAR)	 not null,
   AVI_AREAS           	VARCHAR2(1024 CHAR)	 not null,
   AVI_PERIEVA          NUMBER(5)     		 ,
   AVI_INTEREVA         VARCHAR2(11 CHAR)	 , 
   AVI_EVENTOS         	VARCHAR2(255 CHAR)	 ,
   AVI_ELIMINAR        	VARCHAR2(1 CHAR)	 default 'F'
   
);

comment on table STH_AVISCONFIG is
'Avisos de eventos SistraHelp';

comment on column STH_AVISCONFIG.AVI_CODIGO is
'Código interno';

comment on column STH_AVISCONFIG.AVI_EVENTOS is
'Eventos que lanzan el aviso';

comment on column STH_AVISCONFIG.AVI_NOMBRE is
'Nombre Aviso';

comment on column STH_AVISCONFIG.AVI_MAIL is
'Email Destinatarios del Aviso (separado por ;)';

comment on column STH_AVISCONFIG.AVI_AREAS is
'Lista de áreas a evaluar (separado por ;)';

comment on column STH_AVISCONFIG.AVI_PERIEVA is
'Periodo Evaluación Aviso';

comment on column STH_AVISCONFIG.AVI_INTEREVA is
'Intervalo Evaluación Aviso';

comment on column STH_AVISCONFIG.AVI_EVENTOS is
'Eventos que disparan el aviso';

comment on column STH_AVISCONFIG.AVI_ELIMINAR is
'Avisos que se eliminaran tras la purga';

alter table STH_AVISCONFIG
   add constraint STH_AVISCONFIG_PK primary key (AVI_CODIGO);

create sequence STH_AVISCONFIG_SEQ;
   
create table STH_HISTAVIS
(
   HIST_CODIGO           NUMBER(18)           not null,
   HIST_CODIGOAVIS       NUMBER(18)           not null,
   HIST_EVENTO	         VARCHAR2(20 CHAR) 	  not null, 
   HIST_FECHA	         DATE	     		  not null 
);

comment on table STH_HISTAVIS is
'Historial avisos de eventos SistraHelp';

comment on column STH_HISTAVIS.HIST_CODIGO is
'Código interno';

comment on column STH_HISTAVIS.HIST_CODIGOAVIS is
'Código aviso lanzado';

comment on column STH_HISTAVIS.HIST_EVENTO is
'Evento que ha lanzado el aviso';

comment on column STH_HISTAVIS.HIST_FECHA is
'Fecha del aviso lanzado';

alter table STH_HISTAVIS
   add constraint STH_HISTAVIS_PK primary key (HIST_CODIGO);

alter table STH_HISTAVIS
   add constraint STH_HISTAVIS_AVISO_FK foreign key (HIST_CODIGOAVIS)
      references STH_AVISCONFIG (AVI_CODIGO);

create sequence STH_HISTAVIS_SEQ;

COMMIT;
