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

	String iniciarTramite(UsuarioAutenticadoInfo usuarioAutenticado, String idTramite, int version, String idioma,
			String idTramiteCatalogo, String urlInicio, Map<String, String> parametrosInicio);

	void cargarTramite(String idSesionTramitacion, UsuarioAutenticadoInfo usuarioAutenticado);

	void recargarTramite(String idSesionTramitacion, UsuarioAutenticadoInfo userInfo);

	DetalleTramite obtenerDetalleTramite();

	DetallePasos obtenerDetallePasos();

	void invalidarFlujoTramicacion();

	ResultadoIrAPaso irAPaso(String idPaso);

	ResultadoIrAPaso irAPasoActual();

	ResultadoAccionPaso accionPaso(String idPaso, TypeAccionPaso accionPaso, ParametrosAccionPaso parametros);

	void cancelarTramite();

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


}