package es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2.modelo;

import java.util.Calendar;

import es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2.TypeProcedimientoEstado;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.rolsac2.TypeProcedimientoWorkflow;


/**
 * Procedimiento rolsac.
 *
 * @author Indra
 *
 */
public class RProcedimientoRolsac {

	/** codigo **/
	private long codigo;

	/**  **/
	private String destinatarios;

	/**  **/
	private Calendar fechaActualizacion;

	/**  **/
	private Calendar fechaCaducidad;

	/**  **/
	private Calendar fechaPublicacion;

	/**  **/
	private String observaciones;

	/**  **/
	private String requisitos;

	/**  **/
	private Integer codigoSIA;

	/**  **/
	private boolean estadoSIA;

	/**  **/
	private Calendar fechaSIA;

	private String responsable;

	/** servicioResponsable **/
	private RLink linkUnidadAdministrativaResponsable;

	private Long uaResponsable;

	/** unidadAdministrativa **/
	private RLink linkUnidadAdministrativaCompetente;

	private Long uaCompetente;

	private RLink linkUnidadAdministrativaInstructora;

	private Long uaInstructor;

	/** es comun **/
	private int comun;

	/** Info Adicional **/
	private RLink linkLopdInfoAdicional;

	private String lopdCabecera;

	private String lopdResponsable;

	private String lopdFinalidad;

	private String lopdDestinatario;

	private String lopdDerechos;

	private RLegitimacion lopdLegitimacion;

	private String objeto;

    private Long codigoWF;

    private String tipo;

    private TypeProcedimientoWorkflow workflow;

    private TypeProcedimientoEstado estado;

    private boolean interno;

    private boolean publicado;



    private RInicio iniciacion;

    private RSilencio silencio;

    private RTipoProcedimiento tipoProcedimiento;

    private Long tipoVia;

    private boolean habilitadoApoderado;

    private String habilitadoFuncionario;

    private boolean tieneTasa = false;

    private String responsableEmail;

    private String responsableTelefono;

    private String nombreProcedimientoWorkFlow;



    private String terminoResolucion;

    private boolean tramitElectronica;

    private boolean tramitPresencial;

    private boolean tramitTelefonica;

    private boolean activoLOPD = false;

	/**
	 * @return the codigo
	 */
	public long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the destinatarios
	 */
	public String getDestinatarios() {
		return destinatarios;
	}

	/**
	 * @param destinatarios the destinatarios to set
	 */
	public void setDestinatarios(String destinatarios) {
		this.destinatarios = destinatarios;
	}

	/**
	 * @return the fechaActualizacion
	 */
	public Calendar getFechaActualizacion() {
		return fechaActualizacion;
	}

	/**
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Calendar fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	/**
	 * @return the fechaCaducidad
	 */
	public Calendar getFechaCaducidad() {
		return fechaCaducidad;
	}

	/**
	 * @param fechaCaducidad the fechaCaducidad to set
	 */
	public void setFechaCaducidad(Calendar fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	/**
	 * @return the fechaPublicacion
	 */
	public Calendar getFechaPublicacion() {
		return fechaPublicacion;
	}

	/**
	 * @param fechaPublicacion the fechaPublicacion to set
	 */
	public void setFechaPublicacion(Calendar fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * @return the requisitos
	 */
	public String getRequisitos() {
		return requisitos;
	}

	/**
	 * @param requisitos the requisitos to set
	 */
	public void setRequisitos(String requisitos) {
		this.requisitos = requisitos;
	}

	/**
	 * @return the codigoSIA
	 */
	public Integer getCodigoSIA() {
		return codigoSIA;
	}

	/**
	 * @param codigoSIA the codigoSIA to set
	 */
	public void setCodigoSIA(Integer codigoSIA) {
		this.codigoSIA = codigoSIA;
	}

	/**
	 * @return the estadoSIA
	 */
	public boolean isEstadoSIA() {
		return estadoSIA;
	}

	/**
	 * @param estadoSIA the estadoSIA to set
	 */
	public void setEstadoSIA(boolean estadoSIA) {
		this.estadoSIA = estadoSIA;
	}

	/**
	 * @return the fechaSIA
	 */
	public Calendar getFechaSIA() {
		return fechaSIA;
	}

	/**
	 * @param fechaSIA the fechaSIA to set
	 */
	public void setFechaSIA(Calendar fechaSIA) {
		this.fechaSIA = fechaSIA;
	}

	/**
	 * @return the responsable
	 */
	public String getResponsable() {
		return responsable;
	}

	/**
	 * @param responsable the responsable to set
	 */
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	/**
	 * @return the linkUnidadAdministrativaResponsable
	 */
	public RLink getLinkUnidadAdministrativaResponsable() {
		return linkUnidadAdministrativaResponsable;
	}

	/**
	 * @param linkUnidadAdministrativaResponsable the linkUnidadAdministrativaResponsable to set
	 */
	public void setLinkUnidadAdministrativaResponsable(RLink linkUnidadAdministrativaResponsable) {
		this.linkUnidadAdministrativaResponsable = linkUnidadAdministrativaResponsable;
	}

	/**
	 * @return the uaResponsable
	 */
	public Long getUaResponsable() {
		return uaResponsable;
	}

	/**
	 * @param uaResponsable the uaResponsable to set
	 */
	public void setUaResponsable(Long uaResponsable) {
		this.uaResponsable = uaResponsable;
	}

	/**
	 * @return the linkUnidadAdministrativaCompetente
	 */
	public RLink getLinkUnidadAdministrativaCompetente() {
		return linkUnidadAdministrativaCompetente;
	}

	/**
	 * @param linkUnidadAdministrativaCompetente the linkUnidadAdministrativaCompetente to set
	 */
	public void setLinkUnidadAdministrativaCompetente(RLink linkUnidadAdministrativaCompetente) {
		this.linkUnidadAdministrativaCompetente = linkUnidadAdministrativaCompetente;
	}

	/**
	 * @return the uaCompetente
	 */
	public Long getUaCompetente() {
		return uaCompetente;
	}

	/**
	 * @param uaCompetente the uaCompetente to set
	 */
	public void setUaCompetente(Long uaCompetente) {
		this.uaCompetente = uaCompetente;
	}

	/**
	 * @return the linkUnidadAdministrativaInstructora
	 */
	public RLink getLinkUnidadAdministrativaInstructora() {
		return linkUnidadAdministrativaInstructora;
	}

	/**
	 * @param linkUnidadAdministrativaInstructora the linkUnidadAdministrativaInstructora to set
	 */
	public void setLinkUnidadAdministrativaInstructora(RLink linkUnidadAdministrativaInstructora) {
		this.linkUnidadAdministrativaInstructora = linkUnidadAdministrativaInstructora;
	}

	/**
	 * @return the uaInstructor
	 */
	public Long getUaInstructor() {
		return uaInstructor;
	}

	/**
	 * @param uaInstructor the uaInstructor to set
	 */
	public void setUaInstructor(Long uaInstructor) {
		this.uaInstructor = uaInstructor;
	}

	/**
	 * @return the comun
	 */
	public int getComun() {
		return comun;
	}

	/**
	 * @param comun the comun to set
	 */
	public void setComun(int comun) {
		this.comun = comun;
	}

	/**
	 * @return the linkLopdInfoAdicional
	 */
	public RLink getLinkLopdInfoAdicional() {
		return linkLopdInfoAdicional;
	}

	/**
	 * @param linkLopdInfoAdicional the linkLopdInfoAdicional to set
	 */
	public void setLinkLopdInfoAdicional(RLink linkLopdInfoAdicional) {
		this.linkLopdInfoAdicional = linkLopdInfoAdicional;
	}


	/**
	 * @return the lopdResponsable
	 */
	public String getLopdResponsable() {
		return lopdResponsable;
	}

	/**
	 * @param lopdResponsable the lopdResponsable to set
	 */
	public void setLopdResponsable(String lopdResponsable) {
		this.lopdResponsable = lopdResponsable;
	}

	/**
	 * @return the lopdFinalidad
	 */
	public String getLopdFinalidad() {
		return lopdFinalidad;
	}

	/**
	 * @param lopdFinalidad the lopdFinalidad to set
	 */
	public void setLopdFinalidad(String lopdFinalidad) {
		this.lopdFinalidad = lopdFinalidad;
	}

	/**
	 * @return the lopdDestinatario
	 */
	public String getLopdDestinatario() {
		return lopdDestinatario;
	}

	/**
	 * @param lopdDestinatario the lopdDestinatario to set
	 */
	public void setLopdDestinatario(String lopdDestinatario) {
		this.lopdDestinatario = lopdDestinatario;
	}

	/**
	 * @return the lopdDerechos
	 */
	public String getLopdDerechos() {
		return lopdDerechos;
	}

	/**
	 * @param lopdDerechos the lopdDerechos to set
	 */
	public void setLopdDerechos(String lopdDerechos) {
		this.lopdDerechos = lopdDerechos;
	}

	/**
	 * @return the objeto
	 */
	public String getObjeto() {
		return objeto;
	}

	/**
	 * @param objeto the objeto to set
	 */
	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}

	/**
	 * @return the codigoWF
	 */
	public Long getCodigoWF() {
		return codigoWF;
	}

	/**
	 * @param codigoWF the codigoWF to set
	 */
	public void setCodigoWF(Long codigoWF) {
		this.codigoWF = codigoWF;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the workflow
	 */
	public TypeProcedimientoWorkflow getWorkflow() {
		return workflow;
	}

	/**
	 * @param workflow the workflow to set
	 */
	public void setWorkflow(TypeProcedimientoWorkflow workflow) {
		this.workflow = workflow;
	}

	/**
	 * @return the estado
	 */
	public TypeProcedimientoEstado getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(TypeProcedimientoEstado estado) {
		this.estado = estado;
	}

	/**
	 * @return the interno
	 */
	public boolean isInterno() {
		return interno;
	}

	/**
	 * @param interno the interno to set
	 */
	public void setInterno(boolean interno) {
		this.interno = interno;
	}

	/**
	 * @return the publicado
	 */
	public boolean isPublicado() {
		return publicado;
	}

	/**
	 * @param publicado the publicado to set
	 */
	public void setPublicado(boolean publicado) {
		this.publicado = publicado;
	}



	/**
	 * @return the iniciacion
	 */
	public RInicio getIniciacion() {
		return iniciacion;
	}

	/**
	 * @param iniciacion the iniciacion to set
	 */
	public void setIniciacion(RInicio iniciacion) {
		this.iniciacion = iniciacion;
	}

	/**
	 * @return the silencio
	 */
	public RSilencio getSilencio() {
		return silencio;
	}

	/**
	 * @param silencio the silencio to set
	 */
	public void setSilencio(RSilencio silencio) {
		this.silencio = silencio;
	}

	/**
	 * @return the tipoProcedimiento
	 */
	public RTipoProcedimiento getTipoProcedimiento() {
		return tipoProcedimiento;
	}

	/**
	 * @param tipoProcedimiento the tipoProcedimiento to set
	 */
	public void setTipoProcedimiento(RTipoProcedimiento tipoProcedimiento) {
		this.tipoProcedimiento = tipoProcedimiento;
	}

	/**
	 * @return the tipoVia
	 */
	public Long getTipoVia() {
		return tipoVia;
	}

	/**
	 * @param tipoVia the tipoVia to set
	 */
	public void setTipoVia(Long tipoVia) {
		this.tipoVia = tipoVia;
	}

	/**
	 * @return the habilitadoApoderado
	 */
	public boolean isHabilitadoApoderado() {
		return habilitadoApoderado;
	}

	/**
	 * @param habilitadoApoderado the habilitadoApoderado to set
	 */
	public void setHabilitadoApoderado(boolean habilitadoApoderado) {
		this.habilitadoApoderado = habilitadoApoderado;
	}

	/**
	 * @return the habilitadoFuncionario
	 */
	public String getHabilitadoFuncionario() {
		return habilitadoFuncionario;
	}

	/**
	 * @param habilitadoFuncionario the habilitadoFuncionario to set
	 */
	public void setHabilitadoFuncionario(String habilitadoFuncionario) {
		this.habilitadoFuncionario = habilitadoFuncionario;
	}

	/**
	 * @return the tieneTasa
	 */
	public boolean isTieneTasa() {
		return tieneTasa;
	}

	/**
	 * @param tieneTasa the tieneTasa to set
	 */
	public void setTieneTasa(boolean tieneTasa) {
		this.tieneTasa = tieneTasa;
	}

	/**
	 * @return the responsableEmail
	 */
	public String getResponsableEmail() {
		return responsableEmail;
	}

	/**
	 * @param responsableEmail the responsableEmail to set
	 */
	public void setResponsableEmail(String responsableEmail) {
		this.responsableEmail = responsableEmail;
	}

	/**
	 * @return the responsableTelefono
	 */
	public String getResponsableTelefono() {
		return responsableTelefono;
	}

	/**
	 * @param responsableTelefono the responsableTelefono to set
	 */
	public void setResponsableTelefono(String responsableTelefono) {
		this.responsableTelefono = responsableTelefono;
	}

	/**
	 * @return the nombreProcedimientoWorkFlow
	 */
	public String getNombreProcedimientoWorkFlow() {
		return nombreProcedimientoWorkFlow;
	}

	/**
	 * @param nombreProcedimientoWorkFlow the nombreProcedimientoWorkFlow to set
	 */
	public void setNombreProcedimientoWorkFlow(String nombreProcedimientoWorkFlow) {
		this.nombreProcedimientoWorkFlow = nombreProcedimientoWorkFlow;
	}


	/**
	 * @return the terminoResolucion
	 */
	public String getTerminoResolucion() {
		return terminoResolucion;
	}

	/**
	 * @param terminoResolucion the terminoResolucion to set
	 */
	public void setTerminoResolucion(String terminoResolucion) {
		this.terminoResolucion = terminoResolucion;
	}

	/**
	 * @return the tramitElectronica
	 */
	public boolean isTramitElectronica() {
		return tramitElectronica;
	}

	/**
	 * @param tramitElectronica the tramitElectronica to set
	 */
	public void setTramitElectronica(boolean tramitElectronica) {
		this.tramitElectronica = tramitElectronica;
	}

	/**
	 * @return the tramitPresencial
	 */
	public boolean isTramitPresencial() {
		return tramitPresencial;
	}

	/**
	 * @param tramitPresencial the tramitPresencial to set
	 */
	public void setTramitPresencial(boolean tramitPresencial) {
		this.tramitPresencial = tramitPresencial;
	}

	/**
	 * @return the tramitTelefonica
	 */
	public boolean isTramitTelefonica() {
		return tramitTelefonica;
	}

	/**
	 * @param tramitTelefonica the tramitTelefonica to set
	 */
	public void setTramitTelefonica(boolean tramitTelefonica) {
		this.tramitTelefonica = tramitTelefonica;
	}

	/**
	 * @return the activoLOPD
	 */
	public boolean isActivoLOPD() {
		return activoLOPD;
	}

	/**
	 * @param activoLOPD the activoLOPD to set
	 */
	public void setActivoLOPD(boolean activoLOPD) {
		this.activoLOPD = activoLOPD;
	}

	public RLegitimacion getLopdLegitimacion() {
		return lopdLegitimacion;
	}

	public void setLopdLegitimacion(RLegitimacion lopdLegitimacion) {
		this.lopdLegitimacion = lopdLegitimacion;
	}

	public String getLopdCabecera() {
		return lopdCabecera;
	}

	public void setLopdCabecera(String lopdCabecera) {
		this.lopdCabecera = lopdCabecera;
	}
}
