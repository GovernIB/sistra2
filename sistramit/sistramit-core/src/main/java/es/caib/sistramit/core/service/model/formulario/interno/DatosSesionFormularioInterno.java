package es.caib.sistramit.core.service.model.formulario.interno;

import es.caib.sistrages.rest.api.interna.*;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.model.formulario.types.TypeEdicion;
import es.caib.sistramit.core.service.component.formulario.interno.utils.UtilsFormularioInterno;
import es.caib.sistramit.core.service.model.formulario.DatosInicioSesionFormulario;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.util.UtilsFlujo;
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Datos de la sesión del formulario que se mantienen en memoria (datos páginas,
 * definición formulario, etc.).
 *
 * @author Indra
 *
 */
public final class DatosSesionFormularioInterno {

	/**
	 * Definicion del tramite.
	 */
	private DefinicionTramiteSTG definicionTramite;

	/**
	 * Datos iniciales de sesion.
	 */
	private DatosInicioSesionFormulario datosInicioSesion;

	/**
	 * Datos formulario.
	 */
	private DatosFormularioInterno datosFormulario;

	/**
	 * Indica si el formulario se ha finalizado.
	 */
	private boolean finalizada;

	/**
	 * Constructor.
	 *
	 * @param dis Datos inicio sesión
	 * @param defTramite Definición trámite
	 */
	public DatosSesionFormularioInterno(DatosInicioSesionFormulario dis, DefinicionTramiteSTG defTramite) {
		this.datosInicioSesion = dis;
		this.definicionTramite = defTramite;
	}

	/**
	 * Método de acceso a definicionTramite.
	 * 
	 * @return definicionTramite
	 */
	public DefinicionTramiteSTG getDefinicionTramite() {
		return definicionTramite;
	}

	/**
	 * Método de acceso a datosInicioSesion.
	 * 
	 * @return datosInicioSesion
	 */
	public DatosInicioSesionFormulario getDatosInicioSesion() {
		return datosInicioSesion;
	}

	/**
	 * Método de acceso a datosFormulario.
	 * 
	 * @return datosFormulario
	 */
	public DatosFormularioInterno getDatosFormulario() {
		// TODO LEL EL ACCESO A DATOS FORMULARIO DEBE HACERSE A TRAVES DE DATOS SESION PARA NO ACCEDER DIRECTO
		return datosFormulario;
	}

	/**
	 * Método para establecer datosFormulario.
	 * 
	 * @param datosFormulario
	 *            datosFormulario a establecer
	 */
	public void inicializarDatosFormulario(DatosFormularioInterno datosFormulario) {
		if (this.datosFormulario != null) {
			throw new ErrorConfiguracionException("Los datos del formulario ya están inicializados");
		}
		this.datosFormulario = datosFormulario;
	}

	/**
	 * Indica si esta activo debug para sesion formulario.
	 * 
	 * @return debugEnabled
	 */
	public boolean isDebugEnabled() {
		return UtilsSTG.isDebugEnabled(definicionTramite);
	}

	/**
	 * Método de acceso a finalizada.
	 * 
	 * @return finalizada
	 */
	public boolean isFinalizada() {
		return finalizada;
	}

	/**
	 * Método para establecer finalizada.
	 *
	 */
	public void finalizarSesion() {
		this.finalizada = finalizada;
	}

	/**
	 * Genera variables accesibles desde el script.
	 *
	 * @param pIdCampo
	 *                         Si el script es de un campo se indicara el id campo.
	 *                         Si es de la pagina sera null.
	 * @param pElemento
	 *                         Indica si es elemento
	 * @return Variables accesibles desde el script.
	 */
	public VariablesFormulario generarVariablesFormulario(final String pIdCampo, final boolean pElemento) {
		final VariablesFormulario res = new VariablesFormulario();
		res.setIdSesionTramitacion(getDatosInicioSesion().getIdSesionTramitacion());
		res.setIdioma(getDatosInicioSesion().getIdioma());
		res.setParametrosApertura(getDatosInicioSesion().getParametros());
		res.setNivelAutenticacion(getDatosInicioSesion().getInfoAutenticacion().getAutenticacion());
		res.setMetodoAutenticacion(getDatosInicioSesion().getInfoAutenticacion().getMetodoAutenticacion());
		res.setUsuario(UtilsFlujo.getDatosUsuario(getDatosInicioSesion().getInfoAutenticacion()));
		res.setUsuarioAutenticado(getDatosInicioSesion().getInfoAutenticacion());
		res.setDebugEnabled(isDebugEnabled());

		if (!pElemento) {
			// Página de formulario
			res.setEdicion(TypeEdicion.PRINCIPAL);
			if (pIdCampo != null) {
				res.setValoresCampo(getDatosFormulario().obtenerValoresAccesiblesCampoPaginaFormulario(pIdCampo));
			} else {
				res.setValoresCampo(getDatosFormulario().obtenerValoresAccesiblesPaginaFormularioActual());
			}
		} else {
			// Página de elemento
			final String idCampoListaElementos = getDatosFormulario().obtenerEdicionElementoIdCampoListaElementos();
			// Edición: nuevo / detalle
			res.setEdicion(getDatosFormulario().isEdicionElementoElementoNuevo()
					? TypeEdicion.ELEMENTO_NUEVO
					: TypeEdicion.ELEMENTO_MODIFICACION);
			// Campos accesibles elemento
			if (pIdCampo != null) {
				res.setValoresCampo(getDatosFormulario().obtenerEdicionElementoValoresAccesiblesCampo(pIdCampo));
			} else {
				res.setValoresCampo(getDatosFormulario().obtenerEdicionElementoValoresAccesiblesPagina());
			}
		}

		return res;
	}

	/**
	 * Obtener definición formulario.
	 *
	 * @return definición formulario
	 */
	public RFormularioInterno obtenerDefinicionFormularioInterno() {
		final RFormularioTramite defFormulario = UtilsSTG.devuelveDefinicionFormulario(
				getDatosInicioSesion().getIdPaso(), getDatosInicioSesion().getIdFormulario(),
				getDefinicionTramite());
		return defFormulario.getFormularioInterno();
	}

	/**
	 * Obtiene definición página actual.
	 *
	 * @param elemento
	 *                         Indica si es elemento
	 * @return definición página
	 */
	public RPaginaFormulario obtenerDefinicionPaginaActual(final boolean elemento) {

		RPaginaFormulario paginaDef;

		// Obtenemos definición formulario y pagina
		final RFormularioInterno defFormularioInterno = obtenerDefinicionFormularioInterno();
		final PaginaData paginaActual = getDatosFormulario().obtenerPaginaDataActual(false);

		// Obtenemos definicion pagina principal o detalle elemento
		if (!elemento) {
			paginaDef = UtilsFormularioInterno.obtenerDefinicionPaginaFormulario(defFormularioInterno, paginaActual.getIdentificador());
		} else {
			// Pagina elemento
			paginaDef = UtilsFormularioInterno.obtenerDefinicionPaginaElemento(defFormularioInterno, paginaActual.getIdentificador(),
					getDatosFormulario().obtenerEdicionElementoIdCampoListaElementos());
		}

		return paginaDef;
	}


}
