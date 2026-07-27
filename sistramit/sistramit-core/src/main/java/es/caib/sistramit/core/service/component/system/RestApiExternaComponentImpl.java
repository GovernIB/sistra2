package es.caib.sistramit.core.service.component.system;

import java.util.List;

import es.caib.sistra2.commons.utils.NifUtils;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.model.flujo.PersonaDesglosado;
import es.caib.sistramit.core.api.model.system.rest.externo.*;
import es.caib.sistramit.core.api.model.system.types.TypeTicketAcceso;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistramit.core.api.exception.ErrorParametroObligatorioException;
import es.caib.sistramit.core.api.model.security.ConstantesSeguridad;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.repository.dao.RestApiDao;
import es.caib.sistramit.core.service.repository.dao.TicketCDCDao;

@Component("restApiExternaComponent")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class RestApiExternaComponentImpl implements RestApiExternaComponent {

	@Autowired
	private RestApiDao flujoTramiteDao;

	@Autowired
	private TicketCDCDao ticketCDCDao;

	@Autowired
	private ConfiguracionComponent configuracionComponent;

	@Override
	public List<TramitePersistencia> recuperarTramites(final FiltroTramitePersistencia pFiltro) {
		if (pFiltro.getNif() == null && pFiltro.getIdSesionTramitacion() == null) {
			throw new ErrorParametroObligatorioException("El paràmetre <NIF> o <idSesionTramitacion> és obligatori");
		}
		return flujoTramiteDao.recuperarTramitesPersistencia(pFiltro);
	}

	@Override
	public List<Evento> recuperarEventos(final FiltroEvento pFiltro) {
		if (pFiltro.getFecha() == null) {
			throw new ErrorParametroObligatorioException("El paràmetre <Fecha evento> és obligatori");
		}
		return flujoTramiteDao.recuperarEventos(pFiltro);
	}

	@Override
	public String obtenerTicketAcceso(final InfoTicketAcceso pInfoTicketAcceso) {

		// Obligatorio para carpeta
		if (pInfoTicketAcceso.getTipoTicketAcceso() == TypeTicketAcceso.CARPETA && pInfoTicketAcceso.getIdSesionTramitacion() == null) {
			throw new ErrorParametroObligatorioException("El paràmetre <Identificador de la sesión> és obligatori");
		}

		// Obligatorio para FH
		if (pInfoTicketAcceso.getTipoTicketAcceso() == TypeTicketAcceso.FUNCIONARIO_HABILITADO) {
			// - Interesado
			if (pInfoTicketAcceso.getInfoAccesoFH() == null || pInfoTicketAcceso.getInfoAccesoFH().getInteresado() == null ){
				throw new ErrorParametroObligatorioException("El paràmetre <Info acceso FH> és obligatori i ha de contindre el interesat");
			}
			if (pInfoTicketAcceso.getInfoAccesoFH().getInteresado().getNif() == null) {
				throw new ErrorParametroObligatorioException("El paràmetre <Nif interesado> és obligatori");
			}
			if (!NifUtils.esIdentificacion(pInfoTicketAcceso.getInfoAccesoFH().getInteresado().getNif(), true,true,true, true, false)) {
				throw new ErrorParametroObligatorioException("El paràmetre <Nif interesado> no és vàlid");
			}
			if (pInfoTicketAcceso.getInfoAccesoFH().getInteresado().getNombre() == null) {
				throw new ErrorParametroObligatorioException("El paràmetre <Nombre interesado> és obligatori");
			}
			// - Representante
			if (pInfoTicketAcceso.getInfoAccesoFH().getRepresentante() != null) {
				if (pInfoTicketAcceso.getInfoAccesoFH().getRepresentante().getNif() == null) {
					throw new ErrorParametroObligatorioException("El paràmetre <Nif representante> és obligatori");
				}
				if (!NifUtils.esIdentificacion(pInfoTicketAcceso.getInfoAccesoFH().getRepresentante().getNif(), true,true,true, true, false)) {
					throw new ErrorParametroObligatorioException("El paràmetre <Nif representante> no és vàlid");
				}
				if (pInfoTicketAcceso.getInfoAccesoFH().getRepresentante().getNombre() == null) {
					throw new ErrorParametroObligatorioException("El paràmetre <Nombre representante> és obligatori");
				}
			}
			// - Tramite
			if (pInfoTicketAcceso.getInfoAccesoFH().getTramiteFH() == null) {
				throw new ErrorParametroObligatorioException("El paràmetre <Tramite FH> és obligatori");
			}
			if (pInfoTicketAcceso.getInfoAccesoFH().getTramiteFH().getTramite() == null) {
				throw new ErrorParametroObligatorioException("El paràmetre <Tramite FH> ha de contindre el codi del tràmit");
			}
			if (pInfoTicketAcceso.getInfoAccesoFH().getTramiteFH().getIdioma() == null) {
				throw new ErrorParametroObligatorioException("El paràmetre <Tramite FH> ha de contindre l'idioma del tràmit");
			}
			// - Validamos que esté habilitado en la entidad el acceso por FH
			// 		* Obtenemos definicion trámite
			final DefinicionTramiteSTG defTramite = configuracionComponent.recuperarDefinicionTramite(
					pInfoTicketAcceso.getInfoAccesoFH().getTramiteFH().getTramite(),
					pInfoTicketAcceso.getInfoAccesoFH().getTramiteFH().getVersion(),
					pInfoTicketAcceso.getInfoAccesoFH().getTramiteFH().getIdioma());
			// 		* Obtenemos conf entidad
			final RConfiguracionEntidad confEntidad = configuracionComponent
					.obtenerConfiguracionEntidad(defTramite.getDefinicionVersion().getIdEntidad());
			// 		* Comprobamos que la entidad tiene habilitado el acceso por FH
			if (!confEntidad.isModoFuncionarioHabilitado()) {
				throw new ErrorConfiguracionException("L'accés per FH no està habilitat a l'entitat: "
						+ defTramite.getDefinicionVersion().getIdEntidad());
			}
		}


		if (pInfoTicketAcceso.getUsuarioAutenticadoInfo().getMetodoAutenticacion() == null) {
			throw new ErrorParametroObligatorioException("El paràmetre <Metodo Autenticacion> és obligatori");
		}

		if (pInfoTicketAcceso.getUsuarioAutenticadoInfo().getAutenticacion() == TypeAutenticacion.AUTENTICADO) {

			if (pInfoTicketAcceso.getUsuarioAutenticadoInfo().getNif() == null) {
				throw new ErrorParametroObligatorioException("El paràmetre <Nif usuario> és obligatori");
			}

			if (!NifUtils.esIdentificacion(pInfoTicketAcceso.getUsuarioAutenticadoInfo().getNif(), true,true,true, true, false)) {
				throw new ErrorParametroObligatorioException("El paràmetre <Nif usuario> no és vàlid");
			}

			if (pInfoTicketAcceso.getUsuarioAutenticadoInfo().getNombre() == null) {
				throw new ErrorParametroObligatorioException("El paràmetre <Nombre usuario> és obligatori");
			}

			if (pInfoTicketAcceso.getUsuarioAutenticadoInfo().getUsername() == null) {
				throw new ErrorParametroObligatorioException("El paràmetre <Código usuario> és obligatori");
			}

			if (pInfoTicketAcceso.getUsuarioAutenticadoInfo().getQaa() == null) {
				throw new ErrorParametroObligatorioException("El paràmetre <QAA> és obligatori");
			}
		}

		final String ticket = ticketCDCDao.generarTicketAcceso(pInfoTicketAcceso);

		// - Construimos url
		String puntoentradaRetorno = null;
		switch (pInfoTicketAcceso.getTipoTicketAcceso()) {
			case CARPETA:
				puntoentradaRetorno = ConstantesSeguridad.PUNTOENTRADA_RETORNO_CARPETA;
				break;
			case FUNCIONARIO_HABILITADO:
				puntoentradaRetorno = ConstantesSeguridad.PUNTOENTRADA_RETORNO_FH;
				break;
			default:
				throw new ErrorParametroObligatorioException("El paràmetre <Tipo ticket acceso> no té un valor correcte: " + pInfoTicketAcceso.getTipoTicketAcceso().toString());
		}
		final String urlCallback = configuracionComponent.obtenerPropiedadConfiguracion(
				TypePropiedadConfiguracion.SISTRAMIT_URL) + puntoentradaRetorno + "?"
				+ ConstantesSeguridad.PARAM_TICKETAUTH + "=" + ticket;

		return urlCallback;
	}




	@Override
	public List<TramiteFinalizado> recuperarTramitesFinalizados(final FiltroTramiteFinalizado pFiltro) {
		if (pFiltro.getNif() == null && pFiltro.getIdSesionTramitacion() == null) {
			throw new ErrorParametroObligatorioException("El paràmetre <NIF> o <idSesionTramitacion> és obligatori");
		}
		return flujoTramiteDao.recuperarTramitesFinalizados(pFiltro);
	}



}
