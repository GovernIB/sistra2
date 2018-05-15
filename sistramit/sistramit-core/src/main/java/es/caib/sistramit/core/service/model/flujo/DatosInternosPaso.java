package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeSubestadoPaso;

/**
 * Datos internos de un paso de la tramitación (estado, documentos, etc.), no
 * deberían ser accesibles directamente desde el controlador de flujo, sólo
 * desde el propio paso.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public abstract class DatosInternosPaso implements Serializable {

	/**
	 * Id sesión tramitación a la que pertenece el paso.
	 */
	private String idSesionTramitacion;

	/**
	 * Tipo de paso.
	 */
	private TypePaso tipo;

	/**
	 * Identificador del paso.
	 */
	private String idPaso;

	/**
	 * Estado del paso.
	 */
	private TypeEstadoPaso estado = TypeEstadoPaso.NO_INICIALIZADO;

	/**
	 * Estado interno del paso (depende de cada paso si debe proporcionar).
	 */
	private TypeSubestadoPaso subestado;

	/**
	 * Indica si este paso se ha marcado como solo lectura (no se pueden modificar
	 * sus elementos: formularios, anexos, etc.).
	 */
	private boolean soloLectura;

	/**
	 * Indica si este paso es final y no se puede ir hacia detrás.
	 */
	private boolean pasoFinal;

	/**
	 * Indica el tipo de paso.
	 *
	 * @return tipo
	 */
	public final TypePaso getTipo() {
		return tipo;
	}

	/**
	 * Indica el tipo de paso.
	 *
	 * @param pTipo
	 *            tipo a establecer
	 */
	public final void setTipo(final TypePaso pTipo) {
		tipo = pTipo;
	}

	/**
	 * Método de acceso a idPaso.
	 *
	 * @return idPaso
	 */
	public final String getIdPaso() {
		return idPaso;
	}

	/**
	 * Método para establecer idPaso.
	 *
	 * @param pIdPaso
	 *            idPaso a establecer
	 */
	protected final void setIdPaso(final String pIdPaso) {
		idPaso = pIdPaso;
	}

	/**
	 * Método de acceso a estado.
	 *
	 * @return estado
	 */
	public final TypeEstadoPaso getEstado() {
		return estado;
	}

	/**
	 * Método para establecer estado.
	 *
	 * @param pEstado
	 *            estado a establecer
	 */
	public final void setEstado(final TypeEstadoPaso pEstado) {
		estado = pEstado;
	}

	/**
	 * Método de acceso a pasoFinal.
	 *
	 * @return pasoFinal
	 */
	public final boolean isPasoFinal() {
		return pasoFinal;
	}

	/**
	 * Método para establecer pasoFinal.
	 *
	 * @param pPasoFinal
	 *            pasoFinal a establecer
	 */
	public final void setPasoFinal(final boolean pPasoFinal) {
		pasoFinal = pPasoFinal;
	}

	/**
	 * Método de acceso a subestado.
	 *
	 * @return subestado
	 */
	public final TypeSubestadoPaso getSubestado() {
		return subestado;
	}

	/**
	 * Método para establecer subestado.
	 *
	 * @param pSubestado
	 *            subestado a establecer
	 */
	public final void setSubestado(final TypeSubestadoPaso pSubestado) {
		subestado = pSubestado;
	}

	/**
	 * Método de acceso a soloLectura.
	 *
	 * @return soloLectura
	 */
	public final boolean isSoloLectura() {
		return soloLectura;
	}

	/**
	 * Método para establecer soloLectura.
	 *
	 * @param pSoloLectura
	 *            soloLectura a establecer
	 */
	public final void setSoloLectura(final boolean pSoloLectura) {
		soloLectura = pSoloLectura;
	}

	/**
	 * Obtiene el id de sesión de tramitación.
	 *
	 * @return idSesionTramitacion
	 */
	public final String getIdSesionTramitacion() {
		return idSesionTramitacion;
	}

	/**
	 * Establece el id de sesión de tramitación.
	 *
	 * @param pIdSesionTramitacion
	 *            idSesionTramitacion a establecer
	 */
	protected final void setIdSesionTramitacion(final String pIdSesionTramitacion) {
		idSesionTramitacion = pIdSesionTramitacion;
	}

}
