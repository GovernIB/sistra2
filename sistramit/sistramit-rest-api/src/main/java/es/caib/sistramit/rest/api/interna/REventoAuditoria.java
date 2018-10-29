package es.caib.sistramit.rest.api.interna;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "REventoAuditoria", description = "Evento Auditoria")
public class REventoAuditoria {

	/**
	 * id.
	 */
	@ApiModelProperty(value = "Identificador")
	private Long id;

	/**
	 * tipo.
	 */
	@ApiModelProperty(value = "Tipo de evento")
	private String tipo;

	/**
	 * fecha.
	 */
	@ApiModelProperty(value = "fecha del evento")
	private Date fecha;

	/**
	 * descripcion.
	 */
	@ApiModelProperty(value = "Descripcion")
	private String descripcion;

	/**
	 * resultado.
	 */
	@ApiModelProperty(value = "Resultado")
	private String resultado;

	/**
	 * id sesion.
	 */
	@ApiModelProperty(value = "Identificador de la sesión")
	private String idSesion;

	/**
	 * nif.
	 */
	@ApiModelProperty(value = "Nif")
	private String nif;

	/**
	 * id tramite.
	 */
	@ApiModelProperty(value = "Identificador del trámite")
	private String idTramite;

	/**
	 * version tramite.
	 */
	@ApiModelProperty(value = "Versión del trámite")
	private Long versionTramite;

	/**
	 * cod procedimiento.
	 */
	@ApiModelProperty(value = "Código del procedimiento")
	private Long codProcedimiento;

	/**
	 * Instancia un nuevo evento
	 */
	public REventoAuditoria() {
	}

	/**
	 * Para obtener el atributo id.
	 *
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Para establecer el atributo id.
	 *
	 * @param pId
	 *            el nuevo valor para id
	 */
	public void setId(final Long pId) {
		this.id = pId;
	}

	/**
	 * Para obtener el atributo fecha.
	 *
	 * @return fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Para establecer el atributo fecha.
	 *
	 * @param pFecha
	 *            el nuevo valor para fecha
	 */
	public void setFecha(final Date pFecha) {
		this.fecha = pFecha;
	}

	/**
	 * Para obtener el atributo descripcion.
	 *
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Para establecer el atributo descripcion.
	 *
	 * @param pDescripcion
	 *            el nuevo valor para descripcion
	 */
	public void setDescripcion(final String pDescripcion) {
		this.descripcion = pDescripcion;
	}

	/**
	 * Para obtener el atributo resultado.
	 *
	 * @return resultado
	 */
	public String getResultado() {
		return resultado;
	}

	/**
	 * Para establecer el atributo resultado.
	 *
	 * @param pResultado
	 *            el nuevo valor para resultado
	 */
	public void setResultado(final String pResultado) {
		this.resultado = pResultado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	public String getIdSesion() {
		return idSesion;
	}

	public void setIdSesion(final String idSesion) {
		this.idSesion = idSesion;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(final String nif) {
		this.nif = nif;
	}

	public String getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(final String idTramite) {
		this.idTramite = idTramite;
	}

	public Long getVersionTramite() {
		return versionTramite;
	}

	public void setVersionTramite(final Long versionTramite) {
		this.versionTramite = versionTramite;
	}

	public Long getCodProcedimiento() {
		return codProcedimiento;
	}

	public void setCodProcedimiento(final Long codProcedimiento) {
		this.codProcedimiento = codProcedimiento;
	}

}
