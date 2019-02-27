package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.FuenteDatosValores;
import es.caib.sistrages.core.api.model.FuenteFila;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.model.comun.CsvDocumento;
import es.caib.sistrages.core.api.model.comun.FilaImportarDominio;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.DominioService;

/**
 * La clase DominioServiceBean.
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class DominioServiceBean implements DominioService {

	/**
	 * dominio service.
	 */
	@Autowired
	DominioService dominioService;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#loadDominio(java.lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public Dominio loadDominio(final Long codDominio) {
		return dominioService.loadDominio(codDominio);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public Dominio loadDominio(final String identificadorDominio) {
		return dominioService.loadDominio(identificadorDominio);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.DominioService#listDominio(es.caib.
	 * sistrages.core.api.model.types.TypeAmbito, java.lang.Long, java.lang.String)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<Dominio> listDominio(final TypeAmbito ambito, final Long id, final String filtro) {
		return dominioService.listDominio(ambito, id, filtro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.DominioService#addDominio(es.caib.
	 * sistrages.core.api.model.Dominio, java.lang.Long, java.lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void addDominio(final Dominio entidad, final Long idEntidad, final Long idArea) {
		dominioService.addDominio(entidad, idEntidad, idArea);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#removeDominio(java.lang.
	 * Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean removeDominio(final Long idDominio) {
		return dominioService.removeDominio(idDominio);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.DominioService#updateDominio(es.caib.
	 * sistrages.core.api.model.Dominio)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void updateDominio(final Dominio dominio) {
		dominioService.updateDominio(dominio);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#loadFuenteDato(java.lang.
	 * Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public FuenteDatos loadFuenteDato(final Long idFuenteDato) {
		return dominioService.loadFuenteDato(idFuenteDato);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public FuenteDatosValores loadFuenteDatoValores(final Long idFuenteDato) {
		return dominioService.loadFuenteDatoValores(idFuenteDato);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.DominioService#addFuenteDato(es.caib.
	 * sistrages.core.api.model.FuenteDatos, java.lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void addFuenteDato(final FuenteDatos fuenteDato, final Long idArea) {
		dominioService.addFuenteDato(fuenteDato, idArea);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#updateFuenteDato(es.caib.
	 * sistrages.core.api.model.FuenteDatos)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void updateFuenteDato(final FuenteDatos fuenteDato) {
		dominioService.updateFuenteDato(fuenteDato);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#removeFuenteDato(java.lang.
	 * Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean removeFuenteDato(final Long idFuenteDato) {
		return dominioService.removeFuenteDato(idFuenteDato);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#listFuenteDato(es.caib.
	 * sistrages.core.api.model.types.TypeAmbito, java.lang.Long, java.lang.String)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<FuenteDatos> listFuenteDato(final TypeAmbito ambito, final Long id, final String filtro) {
		return dominioService.listFuenteDato(ambito, id, filtro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#listFuenteDato(es.caib.
	 * sistrages.core.api.model.types.TypeAmbito, java.lang.Long, java.lang.String)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public FuenteFila loadFuenteDatoFila(final Long idFuenteDatoFila) {
		return dominioService.loadFuenteDatoFila(idFuenteDatoFila);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#listFuenteDato(es.caib.
	 * sistrages.core.api.model.types.TypeAmbito, java.lang.Long, java.lang.String)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void addFuenteDatoFila(final FuenteFila fila, final Long idFuente) {
		dominioService.addFuenteDatoFila(fila, idFuente);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#listFuenteDato(es.caib.
	 * sistrages.core.api.model.types.TypeAmbito, java.lang.Long, java.lang.String)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void updateFuenteDatoFila(final FuenteFila fila) {
		dominioService.updateFuenteDatoFila(fila);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.DominioService#listFuenteDato(es.caib.
	 * sistrages.core.api.model.types.TypeAmbito, java.lang.Long, java.lang.String)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void removeFuenteFila(final Long idFila) {
		dominioService.removeFuenteFila(idFila);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean isCorrectoPK(final FuenteFila fuenteFila, final Long idFuenteDato) {
		return dominioService.isCorrectoPK(fuenteFila, idFuenteDato);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void importarCSV(final Long idFuenteDatos, final CsvDocumento csv) {
		dominioService.importarCSV(idFuenteDatos, csv);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public FuenteDatos loadFuenteDato(final String idFuenteDato) {
		return dominioService.loadFuenteDato(idFuenteDato);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean tieneTramiteVersion(final Long idDominio, final Long idTramiteVersion) {
		return dominioService.tieneTramiteVersion(idDominio, idTramiteVersion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void removeTramiteVersion(final Long idDominio, final Long idTramiteVersion) {
		dominioService.removeTramiteVersion(idDominio, idTramiteVersion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void addTramiteVersion(final Long idDominio, final Long idTramiteVersion) {
		dominioService.addTramiteVersion(idDominio, idTramiteVersion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<Dominio> listDominio(final Long idTramite, final String filtro) {
		return dominioService.listDominio(idTramite, filtro);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void importarDominio(final FilaImportarDominio filaDominio, final Long idEntidad, final Long idArea)
			throws Exception {
		dominioService.importarDominio(filaDominio, idEntidad, idArea);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void clonar(final String dominioID, final String nuevoIdentificador, final Long areaID, final Long fdID,
			final Long idEntidad) {
		dominioService.clonar(dominioID, nuevoIdentificador, areaID, fdID, idEntidad);
	}

}
