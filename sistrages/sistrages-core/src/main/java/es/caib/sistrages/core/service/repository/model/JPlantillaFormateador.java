package es.caib.sistrages.core.service.repository.model;

import javax.persistence.CascadeType;
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

import es.caib.sistrages.core.api.model.PlantillaFormateador;

/**
 * JPlantillaFormateador
 */
@Entity
@Table(name = "STG_FMTPLI")
public class JPlantillaFormateador implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_FMTPLI_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_FMTPLI_SEQ", sequenceName = "STG_FMTPLI_SEQ")
	@Column(name = "PFI_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PFI_CODFMT", nullable = false)
	private JFormateadorFormulario formateador;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PFI_CODIDI", nullable = false)
	private JIdioma idioma;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "PFI_CODFIC", nullable = false)
	private JFichero fichero;

	/**
	 * Constructor
	 */
	public JPlantillaFormateador() {
		super();
	}

	/**
	 * @return the codigo
	 */
	public final Long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public final void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the formateador
	 */
	public final JFormateadorFormulario getFormateador() {
		return formateador;
	}

	/**
	 * @param formateador
	 *            the formateador to set
	 */
	public final void setFormateador(final JFormateadorFormulario formateador) {
		this.formateador = formateador;
	}

	/**
	 * @return the idioma
	 */
	public final JIdioma getIdioma() {
		return idioma;
	}

	/**
	 * @param idioma
	 *            the idioma to set
	 */
	public final void setIdioma(final JIdioma idioma) {
		this.idioma = idioma;
	}

	/**
	 * @return the fichero
	 */
	public final JFichero getFichero() {
		return fichero;
	}

	/**
	 * @param fichero
	 *            the fichero to set
	 */
	public final void setFichero(final JFichero fichero) {
		this.fichero = fichero;
	}

	/**
	 * toModel.
	 *
	 * @return
	 */
	public PlantillaFormateador toModel() {
		final PlantillaFormateador plantilla = new PlantillaFormateador();
		plantilla.setCodigo(codigo);
		if (fichero != null) {
			plantilla.setFichero(fichero.toModel());
		}
		plantilla.setIdioma(idioma.getIdentificador());
		plantilla.setFormateador(formateador.toModel());
		return plantilla;
	}

	/**
	 *
	 * @param model
	 * @return
	 */
	public static JPlantillaFormateador fromModel(final PlantillaFormateador model) {
		JPlantillaFormateador jModel = null;
		if (model != null) {
			jModel = new JPlantillaFormateador();
			if (model.getCodigo() != null) {
				jModel.setCodigo(model.getCodigo());
			}
			jModel.setFichero(JFichero.fromModel(model.getFichero()));
			jModel.setIdioma(new JIdioma(model.getIdioma()));
			jModel.setFormateador(JFormateadorFormulario.fromModel(model.getFormateador()));
		}
		return jModel;
	}

}
