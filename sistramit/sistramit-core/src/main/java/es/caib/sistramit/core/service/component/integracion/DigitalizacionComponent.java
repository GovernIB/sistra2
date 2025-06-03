package es.caib.sistramit.core.service.component.integracion;

import es.caib.sistramit.core.api.model.flujo.RedireccionDigitalizacion;
import es.caib.sistramit.core.service.model.flujo.VariablesFlujo;
import es.caib.sistramit.core.service.model.integracion.DigitalizacionRespuesta;

/**
 * Acceso a Componente Digitalizacion.
 *
 * @author Indra
 *
 */
public interface DigitalizacionComponent {

	/**
	 * Redirección a digitalización.
	 *
	 * @param nombreDocumento
	 *
	 * @param idEntidad
	 *                           Entidad
	 * @param urlCallBack
	 *                           url callback
	 * @param variablesFlujo
	 * 						 Variables flujo
	 * @return Datos redirección digitalización
	 */
	RedireccionDigitalizacion redireccionDigitalizacion(final String nombreDocumento, final String idEntidad, final String urlCallBack, final VariablesFlujo variablesFlujo);

	/**
	 * Recupera resultado firma externa.
	 *
	 * @param idEntidad
	 *                        Entidad
	 * @param idSesionDigitalizacion
	 *                        Id sesión digitalización
	 * @return Resultado digitalizacion
	 */
	DigitalizacionRespuesta recuperarResultadoDigitalizacionExterna(final String idEntidad, final String idSesionDigitalizacion);

}
