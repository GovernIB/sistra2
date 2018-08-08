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

import es.caib.sistrages.core.api.model.PlantillaIdiomaFormulario;

/**
 * JPlantillaIdiomaFormulario
 */
@Entity
@Table(name = "STG_FORPLI")
public class JPlantillaIdiomaFormulario implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_FORPLI_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_FORPLI_SEQ", sequenceName = "STG_FORPLI_SEQ")
	@Column(name = "PLI_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "PLI_CODFIC", nullable = false)
	private JFichero fichero;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLI_CODPLT", nullable = false)
	private JPlantillaFormulario plantillaFormulario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLI_CODIDI", nullable = false)
	private JIdioma idioma;

	public JPlantillaIdiomaFormulario() {
		super();
	}

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(final Long codigo) {
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

	public PlantillaIdiomaFormulario toModel() {
		final PlantillaIdiomaFormulario plantilla = new PlantillaIdiomaFormulario();
		plantilla.setCodigo(codigo);

		if (fichero != null) {
			plantilla.setFichero(fichero.toModel());
		}
		plantilla.setIdioma(idioma.getIdentificador());

		return plantilla;
	}

	public static JPlantillaIdiomaFormulario fromModel(final PlantillaIdiomaFormulario model) {
		JPlantillaIdiomaFormulario jModel = null;
		if (model != null) {
			jModel = new JPlantillaIdiomaFormulario();
			if (model.getCodigo() != null) {
				jModel.setCodigo(model.getCodigo());
			}

			if (model.getFichero() != null) {
				jModel.setFichero(JFichero.fromModel(model.getFichero()));
			}

			jModel.setIdioma(new JIdioma(model.getIdioma()));

		}
		return jModel;
	}

	public static JPlantillaIdiomaFormulario clonar(final JPlantillaIdiomaFormulario jplan,
			final JPlantillaFormulario jplantilla) {
		JPlantillaIdiomaFormulario jplantillaIdioma = null;
		if (jplan != null) {
			jplantillaIdioma = new JPlantillaIdiomaFormulario();
			jplantillaIdioma.setFichero(JFichero.clonar(jplan.getFichero()));
			jplantillaIdioma.setIdioma(jplan.getIdioma());
			jplantillaIdioma.setPlantillaFormulario(jplantilla);
		}
		return jplantillaIdioma;
	}

}
