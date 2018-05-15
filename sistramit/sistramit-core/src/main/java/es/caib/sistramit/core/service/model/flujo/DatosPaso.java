package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;

import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeEstadoPaso;
import es.caib.sistramit.core.service.model.flujo.types.TypeSubestadoPaso;

/**
 * Datos asociados a un paso de la tramitación (estado, documentos, etc.). Desde
 * el controlador del flujo de tramitación sólo se podrá consultar la
 * información genérica del paso. Desde el controlador del paso se podrá acceder
 * y modificar los datos internos del paso.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosPaso implements Serializable {

	/**
	 * Datos internos del paso, sólo accesibles desde el propio paso.
	 */
	private DatosInternosPaso datosInternosPaso;

	/**
	 * Constructor.
	 *
	 * @param idSesionTramitacion
	 *            Id sesión tramitación
	 *
	 * @param idPaso
	 *            Identificador paso
	 * @param tipo
	 *            Tipo de paso
	 */
	public DatosPaso(final String idSesionTramitacion, final String idPaso, final TypePaso tipo) {
		// Creamos datos internos segun tipo
		switch (tipo) {
		case DEBESABER:
			datosInternosPaso = new DatosInternosPasoDebeSaber(idSesionTramitacion, idPaso);
			break;
		case RELLENAR:
			datosInternosPaso = new DatosInternosPasoRellenar(idSesionTramitacion, idPaso);
			break;
		case ANEXAR:
			datosInternosPaso = new DatosInternosPasoAnexar(idSesionTramitacion, idPaso);
			break;
		case PAGAR:
			datosInternosPaso = new DatosInternosPasoPagar(idSesionTramitacion, idPaso);
			break;
		case REGISTRAR:
			datosInternosPaso = new DatosInternosPasoRegistrar(idSesionTramitacion, idPaso);
			break;
		case GUARDAR:
			datosInternosPaso = new DatosInternosPasoGuardar(idSesionTramitacion, idPaso);
			break;
		default:
			throw new TipoNoControladoException("Tipo de paso no permitido: " + tipo);
		}
	}

	/**
	 * Crea instancia DatosPaso.
	 *
	 * @param idSesionTramitacion
	 *            Id sesión tramitación
	 *
	 * @param idPaso
	 *            Identificador paso
	 * @param tipo
	 *            Tipo de paso
	 * @return DatosPaso
	 */
	public static DatosPaso createNewDatosPaso(final String idSesionTramitacion, final String idPaso,
			final TypePaso tipo) {
		return new DatosPaso(idSesionTramitacion, idPaso, tipo);
	}

	/**
	 * Datos internos del paso. Solo deberia ser accesible desde el propio paso.
	 *
	 * @return datosInternosPaso
	 */
	public DatosInternosPaso internalData() {
		return datosInternosPaso;
	}

	/**
	 * Método de acceso a tipo.
	 *
	 * @return tipo
	 */
	public TypePaso getTipo() {
		return datosInternosPaso.getTipo();
	}

	/**
	 * Método de acceso a idSesionTramitacion.
	 *
	 * @return idPaso
	 */
	public String getIdSesionTramitacion() {
		return datosInternosPaso.getIdSesionTramitacion();
	}

	/**
	 * Método de acceso a idPaso.
	 *
	 * @return idPaso
	 */
	public String getIdPaso() {
		return datosInternosPaso.getIdPaso();
	}

	/**
	 * Método de acceso a estado.
	 *
	 * @return estado
	 */
	public TypeEstadoPaso getEstado() {
		return datosInternosPaso.getEstado();
	}

	/**
	 * Método de acceso a pasoFinal.
	 *
	 * @return pasoFinal
	 */
	public boolean isPasoFinal() {
		return datosInternosPaso.isPasoFinal();
	}

	/**
	 * Método de acceso a subestado.
	 *
	 * @return subestado
	 */
	public TypeSubestadoPaso getSubestado() {
		return datosInternosPaso.getSubestado();
	}

	/**
	 * Indica si es solo lectura.
	 *
	 * @return Indica si es solo lectura.
	 */
	public boolean isSoloLectura() {
		return datosInternosPaso.isSoloLectura();
	}

}
