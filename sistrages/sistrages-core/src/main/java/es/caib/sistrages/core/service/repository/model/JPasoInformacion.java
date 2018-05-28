package es.caib.sistrages.core.service.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * JPasoInformacion
 */
@Entity
@Table(name = "STG_PASINF")
public class JPasoInformacion implements IModelApi {

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo. **/
	@Id
	@Column(name = "PIN_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	/** Fichero plantilla **/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PIN_FICPLA")
	private JFichero ficheroPlantilla;

	/** Paso tramitacion. **/
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "PIN_CODIGO")
	private JPasoTramitacion pasoTramitacion;

	/** Script datos. **/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PIN_SCRDAT")
	private JScript scriptDatos;

	/** Constructor. **/
	public JPasoInformacion() {
		super();
	}

	/**
	 * @return the codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the ficheroPlantilla
	 */
	public JFichero getFicheroPlantilla() {
		return ficheroPlantilla;
	}

	/**
	 * @param ficheroPlantilla
	 *            the ficheroPlantilla to set
	 */
	public void setFicheroPlantilla(final JFichero ficheroPlantilla) {
		this.ficheroPlantilla = ficheroPlantilla;
	}

	/**
	 * @return the pasoTramitacion
	 */
	public JPasoTramitacion getPasoTramitacion() {
		return pasoTramitacion;
	}

	/**
	 * @param pasoTramitacion
	 *            the pasoTramitacion to set
	 */
	public void setPasoTramitacion(final JPasoTramitacion pasoTramitacion) {
		this.pasoTramitacion = pasoTramitacion;
	}

	/**
	 * @return the scriptDatos
	 */
	public JScript getScriptDatos() {
		return scriptDatos;
	}

	/**
	 * @param scriptDatos
	 *            the scriptDatos to set
	 */
	public void setScriptDatos(final JScript scriptDatos) {
		this.scriptDatos = scriptDatos;
	}

	/**
	 * Clonar.
	 *
	 * @param pasoInformacion
	 * @return
	 */
	public static JPasoInformacion clonar(final JPasoInformacion origPasoInformacion,
			final JPasoTramitacion jpasoTramitacion) {
		JPasoInformacion jpasoInformacion = null;
		if (origPasoInformacion != null) {
			jpasoInformacion = new JPasoInformacion();
			jpasoInformacion.setCodigo(null);
			jpasoInformacion.setPasoTramitacion(jpasoTramitacion);
			jpasoInformacion.setScriptDatos(JScript.clonar(origPasoInformacion.getScriptDatos()));
			jpasoInformacion.setFicheroPlantilla(JFichero.clonar(origPasoInformacion.getFicheroPlantilla()));
		}
		return jpasoInformacion;
	}

}
