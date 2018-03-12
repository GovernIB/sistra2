package es.caib.sistrages.core.service.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * JPlantillaIdiomaFormulario
 */
@Entity
@Table(name = "STG_FORPLI")
public class JPlantillaIdiomaFormulario implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_FORPLI_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_FORPLI_SEQ", sequenceName = "STG_FORPLI_SEQ")
	@Column(name = "PLI_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLI_CODFIC", nullable = false)
	private JFichero fichero;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLI_CODPLT", nullable = false)
	private JPlantillaFormulario plantillaFormulario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLI_CODIDI", nullable = false)
	private JIdioma idioma;

	public JPlantillaIdiomaFormulario() {
	}

	public long getPliCodigo() {
		return this.codigo;
	}

	public void setPliCodigo(final long codigo) {
		this.codigo = codigo;
	}

	public JFichero getFichero() {
		return this.fichero;
	}

	public void setFichero(final JFichero fichero) {
		this.fichero = fichero;
	}

	public JPlantillaFormulario getPlantillaFormulario() {
		return this.plantillaFormulario;
	}

	public void setPlantillaFormulario(final JPlantillaFormulario plantillaFormulario) {
		this.plantillaFormulario = plantillaFormulario;
	}

	public JIdioma getIdioma() {
		return this.idioma;
	}

	public void setIdioma(final JIdioma idioma) {
		this.idioma = idioma;
	}

}
