package es.caib.sistrages.core.api.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Diseño formulario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class FormularioInterno extends ModelApi {

	/** Id. */
	private Long id;

	private Fichero logoCabecera;

	private Script scriptPlantilla;

	private Literal textoCabecera;

	private boolean permitirAccionesPersonalizadas;

	private boolean mostrarCabecera;

	/** Páginas formulario. */
	private final List<PaginaFormulario> paginas = new ArrayList<>();

	private final List<PlantillaFormulario> plantillas = new ArrayList<>();

	/**
	 * Crea una nueva instancia de FormularioDisenyo.
	 */
	public FormularioInterno() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public List<PaginaFormulario> getPaginas() {
		return paginas;
	}

	public Fichero getLogoCabecera() {
		return logoCabecera;
	}

	public void setLogoCabecera(final Fichero logoCabecera) {
		this.logoCabecera = logoCabecera;
	}

	public Script getScriptPlantilla() {
		return scriptPlantilla;
	}

	public void setScriptPlantilla(final Script scriptPlantilla) {
		this.scriptPlantilla = scriptPlantilla;
	}

	public Literal getTextoCabecera() {
		return textoCabecera;
	}

	public void setTextoCabecera(final Literal textoCabecera) {
		this.textoCabecera = textoCabecera;
	}

	public boolean isPermitirAccionesPersonalizadas() {
		return permitirAccionesPersonalizadas;
	}

	public void setPermitirAccionesPersonalizadas(final boolean permitirAccionesPersonalizadas) {
		this.permitirAccionesPersonalizadas = permitirAccionesPersonalizadas;
	}

	public boolean isMostrarCabecera() {
		return mostrarCabecera;
	}

	public void setMostrarCabecera(final boolean cabeceraFormulario) {
		this.mostrarCabecera = cabeceraFormulario;
	}

	public List<PlantillaFormulario> getPlantillas() {
		return plantillas;
	}

}
