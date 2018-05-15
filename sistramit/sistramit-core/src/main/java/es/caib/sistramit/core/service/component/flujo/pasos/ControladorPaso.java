package es.caib.sistramit.core.service.component.flujo.pasos;

import java.util.List;

import es.caib.sistramit.core.api.model.flujo.DetallePaso;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPaso;
import es.caib.sistramit.core.service.model.flujo.DatosDocumento;
import es.caib.sistramit.core.service.model.flujo.DatosPaso;
import es.caib.sistramit.core.service.model.flujo.RespuestaAccionPaso;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramite;

/**
 * Controlador paso de tramitación. Contiene la lógica asociada al paso.
 *
 * @author Indra
 *
 */
public interface ControladorPaso {

	/**
	 * Inicializa paso. Se invocará la primera vez que se acceda al paso.
	 *
	 * @param datosPaso
	 *            Datos del paso
	 * @param definicionTramite
	 *            Definición del trámite
	 * @param variablesFlujo
	 *            Variables del flujo accesibles desde el trámite (datos sesión,
	 *            documentos de otros pasos, etc.)
	 */
	void inicializarPaso(final DatosPaso datosPaso, final DefinicionTramite definicionTramite,
			final VariablesFlujo variablesFlujo);

	/**
	 * Carga paso. Se invocará al cargar un trámite desde persistencia.
	 *
	 * @param datosPaso
	 *            Datos del paso
	 * @param definicionTramite
	 *            Definición del trámite
	 * @param variablesFlujo
	 *            Variables del flujo accesibles desde el trámite (datos sesión,
	 *            documentos de otros pasos, etc.)
	 */
	void cargarPaso(final DatosPaso datosPaso, final DefinicionTramite definicionTramite,
			final VariablesFlujo variablesFlujo);

	/**
	 * Revisa los datos del paso según la última configuración. Se invocará tras
	 * volver a un paso que había sido marcado un paso como inválido.
	 *
	 * @param datosPaso
	 *            Datos del paso
	 * @param definicionTramite
	 *            Definición del trámite
	 * @param variablesFlujo
	 *            Variables del flujo accesibles desde el trámite (datos sesión,
	 *            documentos de otros pasos, etc.)
	 */
	void revisarPaso(final DatosPaso datosPaso, final DefinicionTramite definicionTramite,
			final VariablesFlujo variablesFlujo);

	/**
	 * Marca el paso para que deba revisarse (se ha modificado algún paso anterior).
	 *
	 * @param datosPaso
	 *            Datos del paso
	 * @param definicionTramite
	 *            Definición del trámite
	 * @param variablesFlujo
	 *            Variables del flujo accesibles desde el trámite (datos sesión,
	 *            documentos de otros pasos, etc.)
	 *
	 */
	void invalidarPaso(final DatosPaso datosPaso, final DefinicionTramite definicionTramite,
			final VariablesFlujo variablesFlujo);

	/**
	 * Permite marcar el paso como solo lectura.
	 *
	 * @param datosPaso
	 *            Datos del paso.
	 * @param definicionTramite
	 *            Definición del trámite
	 * @param soloLectura
	 *            Flag de solo lectura.
	 *
	 */
	void establecerSoloLectura(final DatosPaso datosPaso, final DefinicionTramite definicionTramite,
			final boolean soloLectura);

	/**
	 * Realiza la acción indicada en el paso.
	 *
	 * @param datosPaso
	 *            Datos del paso
	 * @param accionPaso
	 *            Acción a realizar en el paso (depende del paso y de la acción).
	 * @param pParametros
	 *            Parámetros del paso (depende del paso y de la acción).
	 * @param definicionTramite
	 *            Definición del trámite
	 * @param variablesFlujo
	 *            Variables del flujo accesibles desde el trámite (datos sesión,
	 *            documentos de otros pasos, etc.)
	 * @return Parámetros de retorno (depende del paso y de la acción).
	 *
	 */
	RespuestaAccionPaso accionPaso(final DatosPaso datosPaso, final TypeAccionPaso accionPaso,
			final ParametrosAccionPaso pParametros, final DefinicionTramite definicionTramite,
			final VariablesFlujo variablesFlujo);

	/**
	 * Obtiene información sobre el detalle del paso preparada para el front.
	 *
	 * @param datosPaso
	 *            Datos del paso.
	 * @param definicionTramite
	 *            Definición del trámite
	 * @return Detalle del paso preparado para el front.
	 */
	DetallePaso detallePaso(final DatosPaso datosPaso, final DefinicionTramite definicionTramite);

	/**
	 * Obtiene los documentos completados en el paso (el paso debe estar
	 * completado). Se usará para pasáserlos como variables a pasos posteriores del
	 * flujo.
	 *
	 * @param datosPaso
	 *            Datos del paso.
	 * @param definicionTramite
	 *            Definición del trámite
	 * @return Documentos completados del paso.
	 */
	List<DatosDocumento> obtenerDocumentosPaso(final DatosPaso datosPaso, final DefinicionTramite definicionTramite);

}
