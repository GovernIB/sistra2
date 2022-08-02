package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeClonarAccion;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.model.types.TypeRolePermisos;
import es.caib.sistrages.core.api.service.ConfiguracionAutenticacionService;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.service.SecurityService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramiteClonar extends DialogControllerBase {

	/** Literal de campo obligatorio. */
	private static final String LITERAL_ERROR_OBLIGATORIO = "error.obligatorio";

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DialogTramiteClonar.class);

	/** Servicio. */
	@Inject
	private TramiteService tramiteService;

	/** Servicio. **/
	@Inject
	private SessionBean sessionBean;

	/** Servicio. **/
	@Inject
	private DominioService dominioService;

	@Inject
	private ConfiguracionAutenticacionService configuracionAutenticacionService;

	/** Servicio. **/
	@Inject
	private SecurityService securityService;

	/** Id elemento a tratar. */
	private String id;

	/** Id area. **/
	private String idArea;

	/** Dominio. **/
	private TramiteVersion data;

	/** Area. **/
	private Area area;

	/** areaID **/
	private Long areaID;

	/** Area del usuario (solo en ambito tipo area). **/
	private List<Area> areas;

	/** Fuentes de datos del ambito entidad. **/
	private List<Tramite> tramites;

	/** Id. del tramite. **/
	private Long tramiteID;

	/**
	 * Inicialización.
	 */
	public void init() {
		LOGGER.debug("Entrando en dialogTramiteClonar.");
		data = tramiteService.getTramiteVersion(Long.valueOf(id));

		final Tramite tramite = tramiteService.getTramite(data.getIdTramite());

		final List<Area> listaTodasAreas = tramiteService.listArea(UtilJSF.getIdEntidad(), null);

		// filtramos las areas del desarrollador
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {
			areas = listaTodasAreas;
		} else if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {
			areas = new ArrayList<>();
			for (final Area area : listaTodasAreas) {
				final List<TypeRolePermisos> permisos = securityService
						.getPermisosDesarrolladorEntidadByArea(area.getCodigo());

				if (permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)
						|| permisos.contains(TypeRolePermisos.DESARROLLADOR_AREA)) {
					areas.add(area);
				}
			}

		}

		areaID = tramite.getIdArea();

		tramites = tramiteService.listTramite(tramite.getIdArea(), null);
		tramiteID = data.getIdTramite();

	}

	/**
	 * Si cambia de area y es de tipo FD, entonces hay que recalcular la FD
	 */
	public void onChangeArea() {
		tramites = tramiteService.listTramite(areaID, null);
		tramiteID = null;
	}

	/**
	 * Clona el dominio.
	 */
	public void clonar() {

		if (areaID == null) {
			addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral(LITERAL_ERROR_OBLIGATORIO));
			return;
		}

		if (tramiteID == null) {
			addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral(LITERAL_ERROR_OBLIGATORIO));
			return;
		}

		if (this.data.getIdArea() != areaID) {
			List<Dominio> doms = tramiteService.getDominioSimpleByTramiteId(this.data.getCodigo());
			for (Dominio dom : doms) {
				Dominio dominioNuevoIdentificador = dominioService.loadDominioByIdentificador(TypeAmbito.AREA,
						dom.getIdentificador(), sessionBean.getEntidad().getCodigo(), areaID, null);
				if (dominioNuevoIdentificador != null) {
					Object[] valueHolder = new Object[2];
					valueHolder = mensaje(dominioNuevoIdentificador);
					addMessageContext(TypeNivelGravedad.ERROR,
							UtilJSF.getLiteral((String) valueHolder[0], (Object[]) valueHolder[1]));
					return;
				}

			}

			for (Dominio dom : doms) {
				if (dom.getAmbito().equals(TypeAmbito.AREA)) {
					clonarDominio(dom);
				}
			}
		}

		// Clonamos
		this.tramiteService.clonadoTramiteVersion(this.data.getCodigo(), UtilJSF.getSessionBean().getUserName(), areaID,
				tramiteID);

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data);
		UtilJSF.closeDialog(result);

	}

	public Object[] mensaje(Dominio dataNuevo) {

		Object[] propiedades = new Object[2];
		Object[] valueHolder = new Object[2];
		if (dataNuevo.getAmbito() == TypeAmbito.AREA && areas.iterator().next().getIdentificador() != null) {
			propiedades[0] = dataNuevo.getIdentificador();
			propiedades[1] = dataNuevo.getArea().getIdentificador();
			valueHolder[0] = "dialogTramiteClonar.error.duplicated.area";
			valueHolder[1] = propiedades;
		} else {
			valueHolder[0] = "dialogDominio.error.codigoRepetido";
			valueHolder[1] = null;
		}
		return valueHolder;
	}

	public void clonarDominio(Dominio dom) {

		TypeClonarAccion acFd = TypeClonarAccion.CREAR;
		TypeClonarAccion acCa = TypeClonarAccion.CREAR;
//		Long lIdArea = (idArea == null) ? null : Long.valueOf(idArea);

		ConfiguracionAutenticacion confAut = null;

		if (dom.getConfiguracionAutenticacion() != null) {
			confAut = dom.getConfiguracionAutenticacion();
		} else {
			acCa = TypeClonarAccion.NADA;
		}
		FuenteDatos fd = null;
		if (dom.getTipo() == TypeDominio.FUENTE_DATOS) {

			fd = dominioService.loadFuenteDato(dom.getAmbito(), dom.getIdentificadorFD(), UtilJSF.getIdEntidad(),
					this.areaID, null);

		} else {
			acFd = TypeClonarAccion.NADA;
		}

		dominioService.clonar(dom.getCodigo().toString(), dom.getIdentificador(), areaID,
				sessionBean.getEntidad().getCodigo(), acFd, fd, acCa, confAut);

	}

	/**
	 * Cerrar.
	 */
	public void cerrar() {

		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);

	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the idArea
	 */
	public String getIdArea() {
		return idArea;
	}

	/**
	 * @param idArea the idArea to set
	 */
	public void setIdArea(final String idArea) {
		this.idArea = idArea;
	}

	/**
	 * @return the data
	 */
	public TramiteVersion getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final TramiteVersion data) {
		this.data = data;
	}

	/**
	 * @return the area
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(final Area area) {
		this.area = area;
	}

	/**
	 * @return the areaID
	 */
	public Long getAreaID() {
		return areaID;
	}

	/**
	 * @param areaID the areaID to set
	 */
	public void setAreaID(final Long areaID) {
		this.areaID = areaID;
	}

	/**
	 * @return the areas
	 */
	public List<Area> getAreas() {
		return areas;
	}

	/**
	 * @param areas the areas to set
	 */
	public void setAreas(final List<Area> areas) {
		this.areas = areas;
	}

	/**
	 * @return the tramites
	 */
	public List<Tramite> getTramites() {
		return tramites;
	}

	/**
	 * @param tramites the tramites to set
	 */
	public void setTramites(final List<Tramite> tramites) {
		this.tramites = tramites;
	}

	/**
	 * @return the tramiteID
	 */
	public Long getTramiteID() {
		return tramiteID;
	}

	/**
	 * @param tramiteID the tramiteID to set
	 */
	public void setTramiteID(final Long tramiteID) {
		this.tramiteID = tramiteID;
	}

}
