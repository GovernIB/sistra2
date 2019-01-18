package es.caib.sistrages.core.api.model.comun;

import java.util.ArrayList;
import java.util.List;

/**
 * Disenyo de formulario simple
 *
 * @author Indra
 *
 */
public class DisenyoFormularioSimple {

	/** Identificador. **/
	private String identificador;

	/** Paginas simples. **/
	private List<DisenyoFormularioPaginaSimple> paginas = new ArrayList<>();

	/**
	 * Constructor.
	 */
	public DisenyoFormularioSimple() {
		// Vacio
	}

	/**
	 * @return the paginas
	 */
	public List<DisenyoFormularioPaginaSimple> getPaginas() {
		return paginas;
	}

	/**
	 * @param paginas
	 *            the paginas to set
	 */
	public void setPaginas(final List<DisenyoFormularioPaginaSimple> paginas) {
		this.paginas = paginas;
	}

	/**
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador
	 *            the identificador to set
	 */
	public void setIdentificador(final String identificador) {
		this.identificador = identificador;
	}
}
