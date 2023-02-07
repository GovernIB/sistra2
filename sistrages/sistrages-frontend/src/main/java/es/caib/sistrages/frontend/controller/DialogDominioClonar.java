package es.caib.sistrages.frontend.controller;

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
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeClonarAccion;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.core.api.service.ConfiguracionAutenticacionService;
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

	/** Servicio. */
	@Inject
	private EntidadService entidadService;

	/** Servicio. */
	@Inject
	private TramiteService tramiteService;

	/** Servicio. */
	@Inject
	private ConfiguracionAutenticacionService configuracionAutenticacionService;

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

	/** Area. **/
	private Area area;

	/** areaID **/
	private Long areaID;

	/** Area del usuario (solo en ambito tipo area). **/
	private List<Area> areas;

	/** Mostrar areas. **/
	private boolean mostrarAreas;

	/** Mostrar fuentes de datos. **/
	private boolean mostrarFDs;
	private boolean mostrarFDreemplazar;
	private boolean fdReemplazar;
	private FuenteDatos fd;
	private String fdLabel;
	private TypeClonarAccion accionFD = TypeClonarAccion.NADA;

	/** Mostrar configuracion autenticacion **/
	private boolean mostrarCAs;
	private boolean mostrarCAreemplazar;
	private boolean caReemplazar;
	ConfiguracionAutenticacion confAut;
	private String caLabel;
	private TypeClonarAccion accionCA = TypeClonarAccion.NADA;

	private String portapapeles;

	private String errorCopiar;

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

			if (!areas.isEmpty()) {
				areaID = areas.get(0).getCodigo();
			}

		} else { // Ambito == TypeAmbito.GLOBAL o TypeAmbito.AREA
			mostrarAreas = false;
		}

		if (data.getTipo() == TypeDominio.FUENTE_DATOS) {

			mostrarFDs = true;

			checkFuenteDatos();

		} else {
			mostrarFDs = false;
		}

		if (data.getConfiguracionAutenticacion() != null) {
			mostrarCAs = true;
			checkConfAut();
		}

		nuevoIdentificador = data.getIdentificador();
	}

	private void checkConfAut() {
		mostrarCAs = data.getConfiguracionAutenticacion() != null;

		if (data.getConfiguracionAutenticacion() != null) {
			confAut = configuracionAutenticacionService.getConfiguracionAutenticacion(data.getAmbito(),
					data.getConfiguracionAutenticacion().getIdentificador(), UtilJSF.getIdEntidad(), this.areaID, null);
			mostrarCAreemplazar = confAut != null;
			caReemplazar = confAut != null;
			if (confAut == null) {
				caLabel = UtilJSF.getLiteral("dialogDominioClonar.confAut.noExiste");
			} else {
				caLabel = UtilJSF.getLiteral("dialogDominioClonar.confAut.existe");
			}
		}
	}

	private void checkFuenteDatos() {
		fd = dominioService.loadFuenteDato(data.getAmbito(), data.getIdentificadorFD(), UtilJSF.getIdEntidad(),
				this.areaID, null);
		mostrarFDreemplazar = fd != null;
		fdReemplazar = fd != null;
		if (fd == null) {
			fdLabel = UtilJSF.getLiteral("dialogDominioClonar.fd.noExiste");
		} else {
			fdLabel = UtilJSF.getLiteral("dialogDominioClonar.fd.existe");
		}
	}

	/**
	 * Si cambia de area y es de tipo FD, entonces hay que recalcular la FD
	 */
	public void onChangeArea() {
		if (data.getTipo() == TypeDominio.FUENTE_DATOS) {
			checkFuenteDatos();
		}

		checkConfAut();
	}

	/**
	 * Clona el dominio.
	 */
	public void clonar() {

		if (data.getAmbito() == TypeAmbito.AREA && areaID == null) {
			addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral(LITERAL_ERROR_OBLIGATORIO));
			return;
		}

		/*
		 * if (data.getTipo() == TypeDominio.FUENTE_DATOS && fuenteID == null) {
		 * addMessageContext(TypeNivelGravedad.ERROR,
		 * UtilJSF.getLiteral(LITERAL_ERROR_OBLIGATORIO)); return; }
		 */

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

//		Long lIdArea = (idArea == null) ? null : Long.valueOf(idArea);
		Dominio dominioNuevoIdentificador = dominioService.loadDominioByIdentificador(this.data.getAmbito(),
				nuevoIdentificador, this.data.getEntidad(), areaID, null);
		if (dominioNuevoIdentificador != null) {
			Object[] valueHolder = new Object[2];
			valueHolder = mensaje(dominioNuevoIdentificador);
			addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral((String) valueHolder[0], (Object[]) valueHolder[1]));
			return;
		}

		if (mostrarFDs) {
			if (fd == null) {
				accionFD = TypeClonarAccion.CREAR;
			} else if (fdReemplazar) {
				accionFD = TypeClonarAccion.REEMPLAZAR;
			} else {
				accionFD = TypeClonarAccion.MANTENER;
			}
		}

		if (mostrarCAs) {
			if (confAut == null) {
				accionCA = TypeClonarAccion.CREAR;
			} else if (caReemplazar) {
				accionCA = TypeClonarAccion.REEMPLAZAR;
			} else {
				accionCA = TypeClonarAccion.MANTENER;
			}
		}
		dominioService.clonar(id, nuevoIdentificador, areaID, idEntidad, accionFD, fd, accionCA, confAut);

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

	public Object[] mensaje(Dominio dataNuevo) {

		Object[] propiedades = new Object[2];
		Object[] valueHolder = new Object[2];
		if (dataNuevo.getAmbito() == TypeAmbito.AREA && areas.iterator().next().getIdentificador() != null) {
			propiedades[0] = dataNuevo.getArea().getCodigoDIR3Entidad();
			propiedades[1] = dataNuevo.getArea().getIdentificador();
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
	 * Copiado correctamente
	 */
	public void copiadoCorr() {

		if (portapapeles.equals("") || portapapeles.equals(null)) {
			copiadoErr();
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
		}
	}

	/**
	 * @return the errorCopiar
	 */
	public final String getErrorCopiar() {
		return errorCopiar;
	}

	/**
	 * @param errorCopiar the errorCopiar to set
	 */
	public final void setErrorCopiar(String errorCopiar) {
		this.errorCopiar = errorCopiar;
	}

	/**
	 * Copiado error
	 */
	public void copiadoErr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("viewTramites.copiar"));
	}

	public final String getPortapapeles() {
		return portapapeles;
	}

	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
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
	 * @return the mostrarFDreemplazar
	 */
	public boolean isMostrarFDreemplazar() {
		return mostrarFDreemplazar;
	}

	/**
	 * @param mostrarFDreemplazar the mostrarFDreemplazar to set
	 */
	public void setMostrarFDreemplazar(boolean mostrarFDreemplazar) {
		this.mostrarFDreemplazar = mostrarFDreemplazar;
	}

	/**
	 * @return the fdReemplazar
	 */
	public boolean isFdReemplazar() {
		return fdReemplazar;
	}

	/**
	 * @param fdReemplazar the fdReemplazar to set
	 */
	public void setFdReemplazar(boolean fdReemplazar) {
		this.fdReemplazar = fdReemplazar;
	}

	/**
	 * @return the fdLabel
	 */
	public String getFdLabel() {
		return fdLabel;
	}

	/**
	 * @param fdLabel the fdLabel to set
	 */
	public void setFdLabel(String fdLabel) {
		this.fdLabel = fdLabel;
	}

	/**
	 * @return the mostrarCAs
	 */
	public boolean isMostrarCAs() {
		return mostrarCAs;
	}

	/**
	 * @param mostrarCAs the mostrarCAs to set
	 */
	public void setMostrarCAs(boolean mostrarCAs) {
		this.mostrarCAs = mostrarCAs;
	}

	/**
	 * @return the mostrarCAreemplazar
	 */
	public boolean isMostrarCAreemplazar() {
		return mostrarCAreemplazar;
	}

	/**
	 * @param mostrarCAreemplazar the mostrarCAreemplazar to set
	 */
	public void setMostrarCAreemplazar(boolean mostrarCAreemplazar) {
		this.mostrarCAreemplazar = mostrarCAreemplazar;
	}

	/**
	 * @return the caReemplazar
	 */
	public boolean isCaReemplazar() {
		return caReemplazar;
	}

	/**
	 * @param caReemplazar the caReemplazar to set
	 */
	public void setCaReemplazar(boolean caReemplazar) {
		this.caReemplazar = caReemplazar;
	}

	/**
	 * @return the caLabel
	 */
	public String getCaLabel() {
		return caLabel;
	}

	/**
	 * @param caLabel the caLabel to set
	 */
	public void setCaLabel(String caLabel) {
		this.caLabel = caLabel;
	}
}
