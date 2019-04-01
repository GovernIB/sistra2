package es.caib.sistramit.core.service.component.flujo;

import java.util.Map;

import es.caib.sistramit.core.api.model.flujo.AnexoFichero;
import es.caib.sistramit.core.api.model.flujo.DetallePasos;
import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.FlujoTramitacionInfo;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoIrAPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;
import es.caib.sistramit.core.service.model.system.FlujoTramitacionCacheIntf;

public interface FlujoTramitacionComponent extends FlujoTramitacionCacheIntf {

	/**
	 * Iniciar tramite.
	 *
	 * @param usuarioAutenticado
	 *            usuario autenticado
	 * @param idTramite
	 *            id tramite
	 * @param version
	 *            version tramite
	 * @param idioma
	 *            idioma
	 * @param idTramiteCatalogo
	 *            id procedimiento
	 * @param servicioCatalogo
	 *            indica si es servicio
	 * @param urlInicio
	 *            url inicio
	 * @param parametrosInicio
	 *            parametros inicio
	 *
	 */
	String iniciarTramite(UsuarioAutenticadoInfo usuarioAutenticado, String idTramite, int version, String idioma,
			String idTramiteCatalogo, final boolean servicioCatalogo, String urlInicio,
			Map<String, String> parametrosInicio);

	/**
	 * Carga un trámite existente de persistencia.
	 *
	 * @param idSesionTramitacion
	 *            Id sesión de tramitación
	 * @param usuarioAutenticadoInfo
	 *            info usuario
	 */
	void cargarTramite(String idSesionTramitacion, UsuarioAutenticadoInfo usuarioAutenticado);

	/**
	 * Recarga tramite que existe en una sesion de front (vuelta de formularios o
	 * recuperacion errores).
	 *
	 * @param idSesionTramitacion
	 *            Id sesión de tramitación
	 * @param userInfo
	 *            info usuario autenticado
	 */
	void recargarTramite(String idSesionTramitacion, UsuarioAutenticadoInfo userInfo);

	/**
	 * Obtiene detalle trámite.
	 *
	 * @return detalle trámite
	 */
	DetalleTramite obtenerDetalleTramite();

	/**
	 * Obtiene detalle paso actual.
	 *
	 * @return detalle trámite
	 */
	DetallePasos obtenerDetallePasos();

	/**
	 * Invalida flujo.
	 */
	void invalidarFlujoTramicacion();

	/**
	 * Va al paso indicado.
	 *
	 * @param idPaso
	 *            Identificador de paso.
	 * @return Estado pasos
	 */
	ResultadoIrAPaso irAPaso(String idPaso);

	/**
	 * Ir a paso actual.
	 *
	 * @return Estado pasos
	 */
	ResultadoIrAPaso irAPasoActual();

	/**
	 * Realiza la acción indicada en el paso.
	 *
	 * @param idPaso
	 *            Identificador del paso.
	 * @param accionPaso
	 *            Acción a realizar en el paso (depende del paso y de la acción).
	 * @param parametros
	 *            Parámetros del paso (depende del paso y de la acción).
	 * @return Devuelve parámetros de retorno del paso (depende del paso y de la
	 *         acción).
	 */
	ResultadoAccionPaso accionPaso(String idPaso, TypeAccionPaso accionPaso, ParametrosAccionPaso parametros);

	/**
	 * Cancela el trámite provocando su eliminación.
	 *
	 * @return Url redirección tras cancelar
	 */
	String cancelarTramite();

	/**
	 * Función interna que no pasa por interceptor de auditoría. Sirve para obtener
	 * detalle del flujo desde el propio interceptor (solo debe ser usada desde el
	 * interceptor).
	 *
	 * @return detalle trámite
	 */
	FlujoTramitacionInfo obtenerFlujoTramitacionInfo();

	/**
	 * Envío formulario de soporte.
	 *
	 * @param idSesionTramitacion
	 *            id sesión tramitación
	 * @param nif
	 *            nif
	 * @param nombre
	 *            nombre
	 * @param telefono
	 *            telefono
	 * @param email
	 *            email
	 * @param problemaTipo
	 *            problema tipo
	 * @param problemaDesc
	 *            problema descripción
	 * @param anexo
	 *            anexo
	 */
	void envioFormularioSoporte(String nif, String nombre, String telefono, String email, String problemaTipo,
			String problemaDesc, AnexoFichero anexo);

	/**
	 * Obtiene un pdf con la clave.
	 *
	 *
	 * @return pdf
	 *
	 */
	byte[] obtenerClavePdf();

	/**
	 * Realiza logout del trámite.
	 *
	 * @return Url redirección
	 */
	String logoutTramite();

}