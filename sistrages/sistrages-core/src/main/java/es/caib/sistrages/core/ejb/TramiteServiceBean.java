package es.caib.sistrages.core.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.AvisoEntidad;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.DominioTramite;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.GestorExternoFormularios;
import es.caib.sistrages.core.api.model.HistorialVersion;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.model.comun.ErrorValidacion;
import es.caib.sistrages.core.api.model.comun.FilaImportar;
import es.caib.sistrages.core.api.model.comun.FilaImportarResultado;
import es.caib.sistrages.core.api.model.comun.ScriptInfo;
import es.caib.sistrages.core.api.model.comun.TramiteSimple;
import es.caib.sistrages.core.api.service.TramiteService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TramiteServiceBean implements TramiteService {

	@Autowired
	TramiteService tramiteService;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#listArea(java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<Area> listArea(final Long idEntidad, final String filtro) {
		return tramiteService.listArea(idEntidad, filtro);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#getArea(java.lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public Area getArea(final Long id) {
		return tramiteService.getArea(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#addArea(java.lang.Long,
	 * es.caib.sistrages.core.api.model.Area)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void addArea(final Long idEntidad, final Area area) {
		tramiteService.addArea(idEntidad, area);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#removeArea(java.lang.Long)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean removeArea(final Long idArea) {
		return tramiteService.removeArea(idArea);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see es.caib.sistrages.core.api.service.TramiteService#updateArea(es.caib.
	 * sistrages. core.api.model.Area)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.SUPER_ADMIN, ConstantesRolesAcceso.ADMIN_ENT })
	public void updateArea(final Area area) {
		tramiteService.updateArea(area);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.caib.sistrages.core.api.service.TramiteService#listTramite(java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<Tramite> listTramite(final Long idArea, final String pFiltro) {
		return tramiteService.listTramite(idArea, pFiltro);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public Tramite getTramite(final Long id) {
		return tramiteService.getTramite(id);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void addTramite(final Long idArea, final Tramite pTramite) {
		tramiteService.addTramite(idArea, pTramite);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean removeTramite(final Long id) {
		return tramiteService.removeTramite(id);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void updateTramite(final Tramite pTramite) {
		tramiteService.updateTramite(pTramite);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<TramiteVersion> listTramiteVersion(final Long idTramite, final String filtro) {
		return tramiteService.listTramiteVersion(idTramite, filtro);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public Long addTramiteVersion(final TramiteVersion tramiteVersion, final String idTramite, final String usuario) {
		return tramiteService.addTramiteVersion(tramiteVersion, idTramite, usuario);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void updateTramiteVersion(final TramiteVersion tramiteVersion) {
		tramiteService.updateTramiteVersion(tramiteVersion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void updateTramiteVersionControlAcceso(final TramiteVersion tramiteVersion, final AvisoEntidad avisoEntidad,
			final Long idEntidad) {
		tramiteService.updateTramiteVersionControlAcceso(tramiteVersion, avisoEntidad, idEntidad);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void removeTramiteVersion(final Long idTramiteVersion, final int numVersion) {
		tramiteService.removeTramiteVersion(idTramiteVersion, numVersion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public TramiteVersion getTramiteVersion(final Long idTramiteVersion) {
		return tramiteService.getTramiteVersion(idTramiteVersion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public Area getAreaTramite(final Long idTramite) {
		return tramiteService.getAreaTramite(idTramite);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<TramitePaso> getTramitePasos(final Long idTramiteVersion) {
		return tramiteService.getTramitePasos(idTramiteVersion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void changeAreaTramite(final Long idArea, final Long idTramite, final Long idAreaAntigua,
			final String usuario) {
		tramiteService.changeAreaTramite(idArea, idTramite, idAreaAntigua, usuario);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<Dominio> getDominioSimpleByTramiteId(final Long idTramiteVersion) {
		return tramiteService.getDominioSimpleByTramiteId(idTramiteVersion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void updateTramitePaso(final TramitePaso tramitePasoRellenar) {
		tramiteService.updateTramitePaso(tramitePasoRellenar);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void removeFormulario(final Long idTramitePaso, final Long idFormulario) {
		tramiteService.removeFormulario(idTramitePaso, idFormulario);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public TramitePaso getTramitePaso(final Long id) {
		return tramiteService.getTramitePaso(id);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public FormularioTramite getFormulario(final Long idFormularioTramite) {
		return tramiteService.getFormulario(idFormularioTramite);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public FormularioTramite addFormularioTramite(final FormularioTramite formularioTramite, final Long idTramitePaso) {
		return tramiteService.addFormularioTramite(formularioTramite, idTramitePaso);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void updateFormularioTramite(final FormularioTramite formularioTramite) {
		tramiteService.updateFormularioTramite(formularioTramite);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public Documento addDocumentoTramite(final Documento documento, final Long idTramitePaso) {
		return tramiteService.addDocumentoTramite(documento, idTramitePaso);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void updateDocumentoTramite(final Documento documento) {
		tramiteService.updateDocumentoTramite(documento);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public Documento getDocumento(final Long idDocumento) {
		return tramiteService.getDocumento(idDocumento);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void removeDocumento(final Long idTramitePaso, final Long idDocumento) {
		tramiteService.removeDocumento(idTramitePaso, idDocumento);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public Tasa getTasa(final Long idTasa) {
		return tramiteService.getTasa(idTasa);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public Tasa addTasaTramite(final Tasa tasa, final Long idTramitePaso) {
		return tramiteService.addTasaTramite(tasa, idTramitePaso);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void updateTasaTramite(final Tasa tasa) {
		tramiteService.updateTasaTramite(tasa);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void removeTasa(final Long idTramitePaso, final Long idTasa) {
		tramiteService.removeTasa(idTramitePaso, idTasa);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void uploadDocAnexo(final Long idDocumento, final Fichero fichero, final byte[] contents,
			final Long idEntidad) {
		tramiteService.uploadDocAnexo(idDocumento, fichero, contents, idEntidad);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void removeDocAnexo(final Long idDocumento) {
		tramiteService.removeDocAnexo(idDocumento);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void bloquearTramiteVersion(final Long idTramiteVersion, final String username) {
		tramiteService.bloquearTramiteVersion(idTramiteVersion, username);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void desbloquearTramiteVersion(final Long idTramiteVersion, final String username, final String detalle) {
		tramiteService.desbloquearTramiteVersion(idTramiteVersion, username, detalle);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<HistorialVersion> listHistorialVersion(final Long idTramiteVersion, final String filtro) {
		return tramiteService.listHistorialVersion(idTramiteVersion, filtro);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public HistorialVersion getHistorialVersion(final Long idHistorialVersion) {
		return tramiteService.getHistorialVersion(idHistorialVersion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean tieneTramiteVersion(final Long idTramite) {
		return tramiteService.tieneTramiteVersion(idTramite);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean tieneTramiteNumVersionRepetida(final Long idTramite, final int numVersion) {
		return tramiteService.tieneTramiteNumVersionRepetida(idTramite, numVersion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public int getTramiteNumVersionMaximo(final Long idTramite) {
		return tramiteService.getTramiteNumVersionMaximo(idTramite);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void clonadoTramiteVersion(final Long idTramiteVersion, final String usuario, final Long idArea,
			final Long idTramite) {
		tramiteService.clonadoTramiteVersion(idTramiteVersion, usuario, idArea, idTramite);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<Long> listTramiteVersionActiva(final Long idArea) {
		return tramiteService.listTramiteVersionActiva(idArea);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<FormateadorFormulario> getFormateadoresTramiteVersion(final Long idTramiteVersion) {
		return tramiteService.getFormateadoresTramiteVersion(idTramiteVersion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<DisenyoFormulario> getFormulariosTramiteVersion(final Long idTramiteVersion) {
		return tramiteService.getFormulariosTramiteVersion(idTramiteVersion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<Fichero> getFicherosTramiteVersion(final Long idTramiteVersion) {
		return tramiteService.getFicherosTramiteVersion(idTramiteVersion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public Area getAreaByIdentificador(final String identificador) {
		return tramiteService.getAreaByIdentificador(identificador);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public Tramite getTramiteByIdentificador(final String identificador) {
		return tramiteService.getTramiteByIdentificador(identificador);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public TramiteVersion getTramiteVersionByNumVersion(final int numVersion, final Long idTramite) {
		return tramiteService.getTramiteVersionByNumVersion(numVersion, idTramite);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public TramiteVersion getTramiteVersionMaxNumVersion(final Long idTramite) {
		return tramiteService.getTramiteVersionMaxNumVersion(idTramite);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void intercambiarFormularios(final Long idFormulario1, final Long idFormulario2) {
		tramiteService.intercambiarFormularios(idFormulario1, idFormulario2);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<DominioTramite> getTramiteVersionByDominio(final Long idDominio) {
		return tramiteService.getTramiteVersionByDominio(idDominio);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<DominioTramite> getTramiteVersionByGfe(final Long Gfe) {
		return tramiteService.getTramiteVersionByGfe(Gfe);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean checkTasaRepetida(final Long idTramiteVersion, final String identificador, final Long idTasa) {
		return tramiteService.checkTasaRepetida(idTramiteVersion, identificador, idTasa);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean checkAnexoRepetido(final Long idTramiteVersion, final String identificador, final Long idAnexo) {
		return tramiteService.checkAnexoRepetido(idTramiteVersion, identificador, idAnexo);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean checkIdentificadorRepetido(final String identificador, final Long codigo) {
		return tramiteService.checkIdentificadorRepetido(identificador, codigo);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean checkIdentificadorAreaRepetido(final String identificador, final Long codigo) {
		return tramiteService.checkIdentificadorAreaRepetido(identificador, codigo);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public FilaImportarResultado importar(final FilaImportar filaImportar) throws Exception {
		return tramiteService.importar(filaImportar);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public boolean checkFormularioRepetido(final Long idTramiteVersion, final String identificador,
			final Long idFormulario) {
		return tramiteService.checkFormularioRepetido(idTramiteVersion, identificador, idFormulario);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public TramiteSimple getTramiteSimple(final String idTramiteVersion) {
		return tramiteService.getTramiteSimple(idTramiteVersion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public String getIdiomasDisponibles(final String idTramiteVersion) {
		return tramiteService.getIdiomasDisponibles(idTramiteVersion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<ErrorValidacion> validarVersionTramite(final Long id, final String idioma) {
		return tramiteService.validarVersionTramite(id, idioma);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<ErrorValidacion> validarVersionTramite(final TramiteVersion pTramiteVersion, final String pIdioma) {
		return tramiteService.validarVersionTramite(pTramiteVersion, pIdioma);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<ErrorValidacion> validarScript(final Script pScript, final List<Dominio> pListaDominios,
			final List<String> pIdiomasTramiteVersion, final String pIdioma) {
		return tramiteService.validarScript(pScript, pListaDominios, pIdiomasTramiteVersion, pIdioma);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<Tramite> listTramiteByEntidad(final Long idEntidad) {
		return tramiteService.listTramiteByEntidad(idEntidad);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public String getIdentificadorByCodigoVersion(final Long codigo) {
		return tramiteService.getIdentificadorByCodigoVersion(codigo);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void permiteSubsanacion(final Long idPaso, final boolean activarSubsanacion) {
		tramiteService.permiteSubsanacion(idPaso, activarSubsanacion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void updateLiteral(final Literal pLiteral) {
		tramiteService.updateLiteral(pLiteral);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void updateScript(final Script pScript) {
		tramiteService.updateScript(pScript);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public TramiteVersion createTramiteVersionDefault(final Integer pNumVersion, final String pIdiomasSoportados,
			final String pDatosUsuarioBloqueo) {
		return tramiteService.createTramiteVersionDefault(pNumVersion, pIdiomasSoportados, pDatosUsuarioBloqueo);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<TramitePaso> createListaPasosNormalizado() {
		return tramiteService.createListaPasosNormalizado();
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public Documento createDocumentoDefault() {
		return tramiteService.createDocumentoDefault();
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public Tasa createTasaDefault() {
		return tramiteService.createTasaDefault();
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public FormularioTramite createFormularioTramiteDefault() {
		return tramiteService.createFormularioTramiteDefault();
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void borradoHistorial(final Long idTramiteVersion, final String username) {
		tramiteService.borradoHistorial(idTramiteVersion, username);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<ScriptInfo> listScriptsInfo(final Long idTramiteVersion) {
		return tramiteService.listScriptsInfo(idTramiteVersion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<GestorExternoFormularios> getGFEByTramiteVersion(final Long idTramiteVersion) {
		return tramiteService.getGFEByTramiteVersion(idTramiteVersion);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public void borrarScriptsVersion(final Long idTramiteVersion, final boolean propiedades, final boolean rellenar,
			final boolean anexo, final boolean tasas, final boolean registrar, final boolean propcaptura) {
		tramiteService.borrarScriptsVersion(idTramiteVersion, propiedades, rellenar, anexo, tasas, registrar,
				propcaptura);

	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<Tramite> listTramite(Long idEntidad, List<Long> areas, String filtro) {
		return tramiteService.listTramite(idEntidad, areas, filtro);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public List<Tramite> listTramite(int first, int pageSize, String sortField, boolean asc, Long idEntidad,
			List<Long> areas, String filtro) {
		return tramiteService.listTramite(first, pageSize, sortField, asc, idEntidad, areas, filtro);
	}

	@Override
	@RolesAllowed({ ConstantesRolesAcceso.ADMIN_ENT, ConstantesRolesAcceso.DESAR })
	public int listTramiteTotal(Long idEntidad, List<Long> areas, String filtro) {
		// TODO Auto-generated method stub
		return tramiteService.listTramiteTotal(idEntidad, areas, filtro);
	}
}
