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

import es.caib.sistrages.core.api.model.PlantillaEntidad;
import es.caib.sistrages.core.api.model.types.TypeFichero;

/**
 * JPlantillaEntidad
 */
@Entity
@Table(name = "STG_PLAENT")
public class JPlantillaEntidad implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STG_PLANENT_SEQ")
	@SequenceGenerator(allocationSize = 1, name = "STG_PLANENT_SEQ", sequenceName = "STG_PLANENT_SEQ")
	@Column(name = "PLE_CODIGO", unique = true, nullable = false, precision = 18, scale = 0)
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLE_CODENT", nullable = false)
	private JEntidad entidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLE_IDIOMA", nullable = false)
	private JIdioma idioma;

	@Column(name = "PLE_TIPO", nullable = false)
	private String tipo;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "PLE_CODFIC", nullable = false)
	private JFichero fichero;

	/**
	 * Constructor
	 */
	public JPlantillaEntidad() {
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
	 * @return the entidad
	 */
	public JEntidad getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad the entidad to set
	 */
	public void setEntidad(JEntidad entidad) {
		this.entidad = entidad;
	}

	/**
	 * @return the idioma
	 */
	public JIdioma getIdioma() {
		return idioma;
	}

	/**
	 * @param idioma the idioma to set
	 */
	public void setIdioma(JIdioma idioma) {
		this.idioma = idioma;
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
	 * @return the fichero
	 */
	public JFichero getFichero() {
		return fichero;
	}

	/**
	 * @param fichero the fichero to set
	 */
	public void setFichero(JFichero fichero) {
		this.fichero = fichero;
	}

	/**
	 * toModel.
	 *
	 * @return
	 */
	public PlantillaEntidad toModel() {
		final PlantillaEntidad plantilla = new PlantillaEntidad();
		plantilla.setCodigo(codigo);
		if (fichero != null) {
			plantilla.setFichero(fichero.toModel());
		}
		plantilla.setIdioma(idioma.getIdentificador());
		plantilla.setEntidad(entidad.toModel());
		plantilla.setTipo(TypeFichero.fromString(tipo));
		return plantilla;
	}

	/**
	 *
	 * @param model
	 * @return
	 */
	public static JPlantillaEntidad fromModel(final PlantillaEntidad model) {
		JPlantillaEntidad jModel = null;
		if (model != null) {
			jModel = new JPlantillaEntidad();
			if (model.getCodigo() != null) {
				jModel.setCodigo(model.getCodigo());
			}
			jModel.setFichero(JFichero.fromModel(model.getFichero()));
			jModel.setIdioma(new JIdioma(model.getIdioma()));
			jModel.setTipo(model.getTipo().toString());
		}
		return jModel;
	}

}
