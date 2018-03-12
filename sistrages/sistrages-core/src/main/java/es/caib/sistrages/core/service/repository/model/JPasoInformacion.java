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
public class JPasoInformacion implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PIN_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PIN_FICPLA")
	private JFichero ficheroPlantilla;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "PIN_CODIGO")
	private JPasoTramitacion pasoTramitacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PIN_SCRDAT")
	private JScript scriptDatos;

	public JPasoInformacion() {
	}

	public long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final long codigo) {
		this.codigo = codigo;
	}

	public JFichero getFicheroPlantilla() {
		return this.ficheroPlantilla;
	}

	public void setFicheroPlantilla(final JFichero ficheroPlantilla) {
		this.ficheroPlantilla = ficheroPlantilla;
	}

	public JPasoTramitacion getPasoTramitacion() {
		return this.pasoTramitacion;
	}

	public void setPasoTramitacion(final JPasoTramitacion pasoTramitacion) {
		this.pasoTramitacion = pasoTramitacion;
	}

	public JScript getScriptDatos() {
		return this.scriptDatos;
	}

	public void setScriptDatos(final JScript scriptDatos) {
		this.scriptDatos = scriptDatos;
	}

}
