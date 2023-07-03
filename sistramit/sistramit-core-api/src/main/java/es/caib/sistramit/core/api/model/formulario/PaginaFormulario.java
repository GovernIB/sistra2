package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

/**
 *
 * Página formulario: datos necesarios para la carga de la página en asistente.
 *
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class PaginaFormulario implements Serializable {

	/**
	 * Id formulario al que pertenece la pagina.
	 */
	private String idFormulario;

	/**
	 * Id pagina.
	 */
	private String idPagina;

	/**
	 * Título formulario.
	 */
	private String titulo;

	/**
	 * Mostrar titulo.
	 */
	private TypeSiNo mostrarTitulo;

	/**
	 * Pemitir salir guardando.
	 */
	private TypeSiNo permitirGuardar = TypeSiNo.NO;

	/**
	 * Html pagina.
	 */
	private String html;

	/**
	 * Configuracion de los campos.
	 */
	private List<ConfiguracionCampo> configuracion = new ArrayList<>();

	/**
	 * Valores de los campos.
	 */
	private List<ValorCampo> valores = new ArrayList<>();

	/**
	 * Valores posibles de los campos selectores.
	 */
	private List<ValoresPosiblesCampo> valoresPosibles = new ArrayList<>();

	/**
	 * Recursos estáticos del formulario. Se establecen las urls de estos recursos
	 * estáticos para establecerse en el html del formulario.
	 */
	private RecursosFormulario recursos = new RecursosFormulario();

	/**
	 * Acciones del formulario.
	 */
	private List<AccionFormulario> acciones = new ArrayList<>();

	/**
	 * Constructor.
	 *
	 * @param pIdFormulario
	 *                          id formulario
	 */
	public PaginaFormulario(final String pIdFormulario) {
		this.idFormulario = pIdFormulario;
	}

	/**
	 * Método de acceso a idFormulario.
	 *
	 * @return idFormulario
	 */
	public String getIdFormulario() {
		return idFormulario;
	}

	/**
	 * Método para establecer idFormulario.
	 *
	 * @param pIdFormulario
	 *                          idFormulario a establecer
	 */
	public void setIdFormulario(final String pIdFormulario) {
		idFormulario = pIdFormulario;
	}

	/**
	 * Método de acceso a html.
	 *
	 * @return html
	 */
	public String getHtml() {
		return html;
	}

	/**
	 * Método para establecer html.
	 *
	 * @param html
	 *                 html a establecer
	 */
	public void setHtml(final String html) {
		this.html = html;
	}

	/**
	 * Método de acceso a configuracion.
	 *
	 * @return configuracion
	 */
	public List<ConfiguracionCampo> getConfiguracion() {
		return configuracion;
	}

	/**
	 * Método para establecer configuracion.
	 *
	 * @param configuracion
	 *                          configuracion a establecer
	 */
	public void setConfiguracion(final List<ConfiguracionCampo> configuracion) {
		this.configuracion = configuracion;
	}

	/**
	 * Método de acceso a valores.
	 *
	 * @return valores
	 */
	public List<ValorCampo> getValores() {
		return valores;
	}

	/**
	 * Método para establecer valores.
	 *
	 * @param valores
	 *                    valores a establecer
	 */
	public void setValores(final List<ValorCampo> valores) {
		this.valores = valores;
	}

	/**
	 * Método de acceso a valoresPosibles.
	 *
	 * @return valoresPosibles
	 */
	public List<ValoresPosiblesCampo> getValoresPosibles() {
		return valoresPosibles;
	}

	/**
	 * Método para establecer valoresPosibles.
	 *
	 * @param valoresPosibles
	 *                            valoresPosibles a establecer
	 */
	public void setValoresPosibles(final List<ValoresPosiblesCampo> valoresPosibles) {
		this.valoresPosibles = valoresPosibles;
	}

	/**
	 * Método de acceso a acciones.
	 *
	 * @return acciones
	 */
	public List<AccionFormulario> getAcciones() {
		return acciones;
	}

	/**
	 * Método para establecer acciones.
	 *
	 * @param acciones
	 *                     acciones a establecer
	 */
	public void setAcciones(final List<AccionFormulario> acciones) {
		this.acciones = acciones;
	}

	/**
	 * Método de acceso a recursos.
	 *
	 * @return recursos
	 */
	public RecursosFormulario getRecursos() {
		return recursos;
	}

	/**
	 * Método para establecer recursos.
	 *
	 * @param recursos
	 *                     recursos a establecer
	 */
	public void setRecursos(final RecursosFormulario recursos) {
		this.recursos = recursos;
	}

	/**
	 * Obtiene la configuración de un campo.
	 *
	 * @param idCampo
	 *                    Id campo
	 * @return Configuración campo
	 */
	public ConfiguracionCampo getConfiguracion(final String idCampo) {
		ConfiguracionCampo res = null;
		for (final ConfiguracionCampo cc : configuracion) {
			if (cc.getId().equals(idCampo)) {
				res = cc;
				break;
			}
		}
		return res;
	}

	/**
	 * Método de acceso a titulo.
	 *
	 * @return titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Método para establecer titulo.
	 *
	 * @param titulo
	 *                   titulo a establecer
	 */
	public void setTitulo(final String titulo) {
		this.titulo = titulo;
	}

	/**
	 * Método de acceso a mostrarTitulo.
	 *
	 * @return mostrarTitulo
	 */
	public TypeSiNo getMostrarTitulo() {
		return mostrarTitulo;
	}

	/**
	 * Método para establecer mostrarTitulo.
	 *
	 * @param mostrarTitulo
	 *                          mostrarTitulo a establecer
	 */
	public void setMostrarTitulo(final TypeSiNo mostrarTitulo) {
		this.mostrarTitulo = mostrarTitulo;
	}

	/**
	 * Método de acceso a permitirGuardar.
	 *
	 * @return permitirGuardar
	 */
	public TypeSiNo getPermitirGuardar() {
		return permitirGuardar;
	}

	/**
	 * Método para establecer permitirGuardar.
	 *
	 * @param permitirGuardar
	 *                            permitirGuardar a establecer
	 */
	public void setPermitirGuardar(final TypeSiNo permitirGuardar) {
		this.permitirGuardar = permitirGuardar;
	}

	/**
	 * Método de acceso a idPagina.
	 *
	 * @return idPagina
	 */
	public String getIdPagina() {
		return idPagina;
	}

	/**
	 * Método para establecer idPagina.
	 *
	 * @param idPagina
	 *                     idPagina a establecer
	 */
	public void setIdPagina(final String idPagina) {
		this.idPagina = idPagina;
	}

}
