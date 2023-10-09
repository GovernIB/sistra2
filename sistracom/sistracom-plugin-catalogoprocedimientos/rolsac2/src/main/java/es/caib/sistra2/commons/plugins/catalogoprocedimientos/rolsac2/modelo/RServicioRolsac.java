package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2.modelo;

import java.util.Calendar;

/**
 * Servicios de rolsac.
 *
 * @author Indra
 *
 */
public class RServicioRolsac {

    private long codigo;

    private String codigoSIA;

    private String destinatarios;

    private String estadoSIA;

    private Calendar fechaActualizacion;

    private Calendar fechaPublicacion;

    private Calendar fechaSIA;

    private String nombre;

    private String objeto;

    private String observaciones;

    private String requisitos;

    private Integer comun;

    private String lopdResponsable;

    private String lopdFinalidad;

    private String lopdDestinatario;

    private String lopdDerechos;

    private Long codigoWF;

    private String tipo;

    private String workflow;

    private String estado;

    private boolean interno;

    private boolean publicado;

    private Calendar fechaCaducidad;

    private Long datosPersonalesLegitimacion;

    private Long uaResponsable;

    private Long uaInstructor;

    private boolean habilitadoApoderado;

    private String habilitadoFuncionario;

    private boolean tieneTasa = false;

    private String responsableEmail;

    private String responsableTelefono;

    private String nombreProcedimientoWorkFlow;

    private String terminoResolucion;

    private boolean tramitPresencial;

    private boolean tramitElectronica;

    private boolean tramitTelefonica;

    private boolean activoLOPD;

    public Calendar siaFecha;

    private RLink link_tipoTramitacion;

    private Long tipoTramitacion;

    private RLink link_plantillaSel;

    private Long plantillaSel;

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public String getCodigoSIA() {
		return codigoSIA;
	}

	public void setCodigoSIA(String codigoSIA) {
		this.codigoSIA = codigoSIA;
	}

	public String getDestinatarios() {
		return destinatarios;
	}

	public void setDestinatarios(String destinatarios) {
		this.destinatarios = destinatarios;
	}

	public String getEstadoSIA() {
		return estadoSIA;
	}

	public void setEstadoSIA(String estadoSIA) {
		this.estadoSIA = estadoSIA;
	}

	public Calendar getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Calendar fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Calendar getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(Calendar fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public Calendar getFechaSIA() {
		return fechaSIA;
	}

	public void setFechaSIA(Calendar fechaSIA) {
		this.fechaSIA = fechaSIA;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getObjeto() {
		return objeto;
	}

	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getRequisitos() {
		return requisitos;
	}

	public void setRequisitos(String requisitos) {
		this.requisitos = requisitos;
	}

	public Integer getComun() {
		return comun;
	}

	public void setComun(Integer comun) {
		this.comun = comun;
	}

	public String getLopdResponsable() {
		return lopdResponsable;
	}

	public void setLopdResponsable(String lopdResponsable) {
		this.lopdResponsable = lopdResponsable;
	}

	public String getLopdFinalidad() {
		return lopdFinalidad;
	}

	public void setLopdFinalidad(String lopdFinalidad) {
		this.lopdFinalidad = lopdFinalidad;
	}

	public String getLopdDestinatario() {
		return lopdDestinatario;
	}

	public void setLopdDestinatario(String lopdDestinatario) {
		this.lopdDestinatario = lopdDestinatario;
	}

	public String getLopdDerechos() {
		return lopdDerechos;
	}

	public void setLopdDerechos(String lopdDerechos) {
		this.lopdDerechos = lopdDerechos;
	}

	public Long getCodigoWF() {
		return codigoWF;
	}

	public void setCodigoWF(Long codigoWF) {
		this.codigoWF = codigoWF;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getWorkflow() {
		return workflow;
	}

	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean isInterno() {
		return interno;
	}

	public void setInterno(boolean interno) {
		this.interno = interno;
	}

	public boolean isPublicado() {
		return publicado;
	}

	public void setPublicado(boolean publicado) {
		this.publicado = publicado;
	}

	public Calendar getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(Calendar fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public Long getDatosPersonalesLegitimacion() {
		return datosPersonalesLegitimacion;
	}

	public void setDatosPersonalesLegitimacion(Long datosPersonalesLegitimacion) {
		this.datosPersonalesLegitimacion = datosPersonalesLegitimacion;
	}

	public Long getUaResponsable() {
		return uaResponsable;
	}

	public void setUaResponsable(Long uaResponsable) {
		this.uaResponsable = uaResponsable;
	}

	public Long getUaInstructor() {
		return uaInstructor;
	}

	public void setUaInstructor(Long uaInstructor) {
		this.uaInstructor = uaInstructor;
	}

	public boolean isHabilitadoApoderado() {
		return habilitadoApoderado;
	}

	public void setHabilitadoApoderado(boolean habilitadoApoderado) {
		this.habilitadoApoderado = habilitadoApoderado;
	}

	public String getHabilitadoFuncionario() {
		return habilitadoFuncionario;
	}

	public void setHabilitadoFuncionario(String habilitadoFuncionario) {
		this.habilitadoFuncionario = habilitadoFuncionario;
	}

	public boolean isTieneTasa() {
		return tieneTasa;
	}

	public void setTieneTasa(boolean tieneTasa) {
		this.tieneTasa = tieneTasa;
	}

	public String getResponsableEmail() {
		return responsableEmail;
	}

	public void setResponsableEmail(String responsableEmail) {
		this.responsableEmail = responsableEmail;
	}

	public String getResponsableTelefono() {
		return responsableTelefono;
	}

	public void setResponsableTelefono(String responsableTelefono) {
		this.responsableTelefono = responsableTelefono;
	}

	public String getNombreProcedimientoWorkFlow() {
		return nombreProcedimientoWorkFlow;
	}

	public void setNombreProcedimientoWorkFlow(String nombreProcedimientoWorkFlow) {
		this.nombreProcedimientoWorkFlow = nombreProcedimientoWorkFlow;
	}

	public String getTerminoResolucion() {
		return terminoResolucion;
	}

	public void setTerminoResolucion(String terminoResolucion) {
		this.terminoResolucion = terminoResolucion;
	}

	public boolean isTramitPresencial() {
		return tramitPresencial;
	}

	public void setTramitPresencial(boolean tramitPresencial) {
		this.tramitPresencial = tramitPresencial;
	}

	public boolean isTramitElectronica() {
		return tramitElectronica;
	}

	public void setTramitElectronica(boolean tramitElectronica) {
		this.tramitElectronica = tramitElectronica;
	}

	public boolean isTramitTelefonica() {
		return tramitTelefonica;
	}

	public void setTramitTelefonica(boolean tramitTelefonica) {
		this.tramitTelefonica = tramitTelefonica;
	}

	public boolean isActivoLOPD() {
		return activoLOPD;
	}

	public void setActivoLOPD(boolean activoLOPD) {
		this.activoLOPD = activoLOPD;
	}

	public Calendar getSiaFecha() {
		return siaFecha;
	}

	public void setSiaFecha(Calendar siaFecha) {
		this.siaFecha = siaFecha;
	}

	public RLink getLink_tipoTramitacion() {
		return link_tipoTramitacion;
	}

	public void setLink_tipoTramitacion(RLink link_tipoTramitacion) {
		this.link_tipoTramitacion = link_tipoTramitacion;
	}

	public Long getTipoTramitacion() {
		return tipoTramitacion;
	}

	public void setTipoTramitacion(Long tipoTramitacion) {
		this.tipoTramitacion = tipoTramitacion;
	}

	public RLink getLink_plantillaSel() {
		return link_plantillaSel;
	}

	public void setLink_plantillaSel(RLink link_plantillaSel) {
		this.link_plantillaSel = link_plantillaSel;
	}

	public Long getPlantillaSel() {
		return plantillaSel;
	}

	public void setPlantillaSel(Long plantillaSel) {
		this.plantillaSel = plantillaSel;
	}
}
