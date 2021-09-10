package es.caib.sistrages.frontend.controller;

import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogDominioClonar extends DialogControllerBase {

	/** Literal de campo obligatorio. */
	private static final String LITERAL_ERROR_OBLIGATORIO = "error.obligatorio";

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DialogDominioClonar.class);

	/** Servicio. */
	@Inject
	private DominioService dominioService;

	@Inject
	private EntidadService entidadService;
	/** Servicio. */
	@Inject
	private TramiteService tramiteService;

	/** Id elemento a tratar. */
	private String id;

	/** Id area. **/
	private String idArea;

	/** Nuevo identificador. **/
	private String nuevoIdentificador;

	/** Dominio. **/
	private Dominio data;

	/** Dominio. **/
	private FuenteDatos fuente;

	/** fuenteID. **/
	private Long fuenteID;

	/** Area. **/
	private Area area;

	/** areaID **/
	private Long areaID;

	/** Area del usuario (solo en ambito tipo area). **/
	private List<Area> areas;

	/** Fuentes de datos del ambito entidad. **/
	private List<FuenteDatos> fuentes;

	/** Mostrar areas. **/
	private boolean mostrarAreas;

	/** Mostrar fuentes de datos. **/
	private boolean mostrarFDs;

	/**
	 * Inicialización.
	 */
	public void init() {
		LOGGER.debug("Entrando en dialogDominioClonar.");
		data = dominioService.loadDominio(Long.valueOf(id));

		if (data.getAmbito() == TypeAmbito.AREA) {
			mostrarAreas = true;
			areas = tramiteService.listArea(UtilJSF.getIdEntidad(), null);
			for (final Area ar : areas) {
				if (ar.getCodigo().toString().equals(idArea)) {
					area = ar;
					break;
				}
			}

		} else { // Ambito == TypeAmbito.GLOBAL o TypeAmbito.AREA
			mostrarAreas = false;
		}

		if (data.getTipo() == TypeDominio.FUENTE_DATOS) {

			mostrarFDs = true;
			if (data.getAmbito() == TypeAmbito.AREA) {
				fuentes = dominioService.listFuenteDato(TypeAmbito.AREA, Long.valueOf(idArea), null);

			} else if (data.getAmbito() == TypeAmbito.ENTIDAD) {
				fuentes = dominioService.listFuenteDato(TypeAmbito.ENTIDAD, UtilJSF.getIdEntidad(), null);
			}

			for (final FuenteDatos fue : fuentes) {
				if (fue.getCodigo().compareTo(data.getIdFuenteDatos()) == 0) {
					fuente = fue;
					break;
				}
			}

		} else {
			mostrarFDs = false;
		}

		nuevoIdentificador = data.getIdentificador();
	}

	/**
	 * Si cambia de area y es de tipo FD, entonces hay que recalcular la FD
	 */
	public void onChangeArea() {
		if (data.getTipo() == TypeDominio.FUENTE_DATOS) {
			fuentes = dominioService.listFuenteDato(TypeAmbito.AREA, areaID, null);
			fuente = null;
		}
	}

	/**
	 * Clona el dominio.
	 */
	public void clonar() {

		if (data.getAmbito() == TypeAmbito.AREA && areaID == null) {
			addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral(LITERAL_ERROR_OBLIGATORIO));
			return;
		}

		if (data.getTipo() == TypeDominio.FUENTE_DATOS && fuenteID == null) {
			addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral(LITERAL_ERROR_OBLIGATORIO));
			return;
		}

		if (nuevoIdentificador == null || nuevoIdentificador.isEmpty()) {
			addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral(LITERAL_ERROR_OBLIGATORIO));
			return;
		}

		Long idEntidad;
		if (data.getAmbito() == TypeAmbito.ENTIDAD) {
			idEntidad = UtilJSF.getIdEntidad();
		} else {
			idEntidad = null;
		}

		if (dominioService.loadDominio(nuevoIdentificador) != null) {
			Object[] valueHolder = new Object[2];
			valueHolder = mensaje(nuevoIdentificador);
			addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral((String) valueHolder[0], (Object[]) valueHolder[1]));
			return;
		}

		dominioService.clonar(id, nuevoIdentificador, areaID, fuenteID, idEntidad);

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data.getIdentificador());
		UtilJSF.closeDialog(result);

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

	public Object[] mensaje(String nuevoIdentificador) {
		Dominio dataNuevo = dominioService.loadDominio(nuevoIdentificador);
		Object[] propiedades = new Object[2];
		Object[] valueHolder = new Object[2];
		Set<Area> areas = dataNuevo.getAreas();
		if (dataNuevo.getAmbito() == TypeAmbito.AREA && areas.iterator().next().getIdentificador() != null) {
			Area elarea = areas.iterator().next();
			propiedades[0] = elarea.getCodigoDIR3Entidad();
			propiedades[1] = elarea.getIdentificador();
			valueHolder[0] = "dialogDominio.error.duplicated.area";
			valueHolder[1] = propiedades;
		} else if (dataNuevo.getAmbito() == TypeAmbito.ENTIDAD && dataNuevo.getEntidad() != null) {
			propiedades[0] = entidadService.loadEntidad(dataNuevo.getEntidad()).getCodigoDIR3();
			valueHolder[0] = "dialogDominio.error.duplicated.entidad";
			valueHolder[1] = propiedades;
		} else {
			valueHolder[0] = "dialogDominio.error.codigoRepetido";
			valueHolder[1] = null;
		}
		return valueHolder;
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
	 * @return the data
	 */
	public Dominio getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final Dominio data) {
		this.data = data;
	}

	/**
	 * @return the fuente
	 */
	public FuenteDatos getFuente() {
		return fuente;
	}

	/**
	 * @param fuente the fuente to set
	 */
	public void setFuente(final FuenteDatos fuente) {
		this.fuente = fuente;
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
	 * @return the fuentes
	 */
	public List<FuenteDatos> getFuentes() {
		return fuentes;
	}

	/**
	 * @param fuentes the fuentes to set
	 */
	public void setFuentes(final List<FuenteDatos> fuentes) {
		this.fuentes = fuentes;
	}

	/**
	 * @return the mostrarAreas
	 */
	public boolean isMostrarAreas() {
		return mostrarAreas;
	}

	/**
	 * @param mostrarAreas the mostrarAreas to set
	 */
	public void setMostrarAreas(final boolean mostrarAreas) {
		this.mostrarAreas = mostrarAreas;
	}

	/**
	 * @return the mostrarFDs
	 */
	public boolean isMostrarFDs() {
		return mostrarFDs;
	}

	/**
	 * @param mostrarFDs the mostrarFDs to set
	 */
	public void setMostrarFDs(final boolean mostrarFDs) {
		this.mostrarFDs = mostrarFDs;
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
	 * @return the nuevoIdentificador
	 */
	public String getNuevoIdentificador() {
		return nuevoIdentificador;
	}

	/**
	 * @param nuevoIdentificador the nuevoIdentificador to set
	 */
	public void setNuevoIdentificador(final String nuevoIdentificador) {
		this.nuevoIdentificador = nuevoIdentificador;
	}

	/**
	 * @return the fuenteID
	 */
	public Long getFuenteID() {
		return fuenteID;
	}

	/**
	 * @param fuenteID the fuenteID to set
	 */
	public void setFuenteID(final Long fuenteID) {
		this.fuenteID = fuenteID;
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
}
