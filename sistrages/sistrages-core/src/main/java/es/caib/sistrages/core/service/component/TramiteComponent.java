package es.caib.sistrages.core.service.component;

import java.util.List;

import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramiteVersion;

/**
 * La interface de TramiteComponent.
 */
public interface TramiteComponent {

	/**
	 * Crea un tramiteVersion con los valores por defecto
	 *
	 * @param pNumVersion
	 *            num. version
	 * @param pIdiomasSoportados
	 *            idiomas soportados
	 * @param pDatosUsuarioBloqueo
	 *            datos del usuario para bloquear
	 * @return TramiteVersion
	 */

	TramiteVersion createTramiteVersionDefault(Integer pNumVersion, String pIdiomasSoportados,
			String pDatosUsuarioBloqueo);

	/**
	 * Crea lista de pasos normalizados.
	 *
	 * @return lista de pasos normalizados
	 */
	List<TramitePaso> createNormalizado();

	/**
	 * Añade un tramite version a un tramite.
	 *
	 * @param tramiteVersion
	 *            tramite version
	 * @param idTramite
	 *            id tramite
	 * @param usuario
	 *            usuario
	 * @return the long
	 */
	Long addTramiteVersion(TramiteVersion tramiteVersion, String idTramite, String usuario);

	/**
	 * Crea un documento con los datos por defecto.
	 *
	 * @return documento
	 */
	Documento createDocumentoDefault();

	/**
	 * Añade un documento a un tramite.
	 *
	 * @param documento
	 *            documento
	 * @param idTramitePaso
	 *            id tramite paso
	 * @return documento
	 */
	Documento addDocumentoTramite(Documento documento, Long idTramitePaso);

	/**
	 * Crea una tasa con los datos por defecto.
	 *
	 * @return tasa
	 */
	Tasa createTasaDefault();

	/**
	 * Añade una tasa a un tramite.
	 *
	 * @param tasa
	 *            tasa
	 * @param idTramitePaso
	 *            id tramite paso
	 * @return tasa
	 */
	Tasa addTasaTramite(Tasa tasa, Long idTramitePaso);

	/**
	 * Crea un formulario tramite con los datos por defecto..
	 *
	 * @return formulario tramite
	 */
	FormularioTramite createFormularioTramiteDefault();

	/**
	 * Añade un formulario tramite.
	 *
	 * @param formularioTramite
	 *            formulario tramite
	 * @param idTramitePaso
	 *            id tramite paso
	 * @return formulario tramite
	 */
	FormularioTramite addFormularioTramite(FormularioTramite formularioTramite, Long idTramitePaso);

}