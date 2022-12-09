package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.EnvioRemoto;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.service.ConfiguracionAutenticacionService;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.core.api.service.EnvioRemotoService;
import es.caib.sistrages.core.api.service.RestApiInternaService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogEnvioRemoto extends DialogControllerBase {

	/** Servicio. */
	@Inject
	private EnvioRemotoService envioRemotoService;

	@Inject
	private TramiteService tramiteService;

	@Inject
	private EntidadService entidadService;

	/** ConfiguracionAutenticacion Service **/
	@Inject
	private ConfiguracionAutenticacionService configuracionAutenticacionService;

	/** Id elemento a tratar. */
	private String id;

	/** Id elemento a tratar. */
	private String area;
	private Long idArea;

	/** Id entidad */
	private String entidad;

	/** Ambito. */
	private String ambito;

	/** Data en formato JSON. */
	private String iData;

	/** Datos elemento. */
	private EnvioRemoto data;

	/** Parametro modo importar **/
	private String modoImportar;

	/** Desactivar botonera. */
	private String desactivarBotonera;

	private boolean desactivarBotoneraActivo = false;

	/** Lista de configuraciones. **/
	private List<ConfiguracionAutenticacion> configuraciones;

	/** Indica si es visible el botón de consultar **/
	private Boolean desactivarConsulta = false;

	private String portapapeles;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		UtilJSF.checkSecOpenDialog(modo, id);

		if (modo == TypeModoAcceso.ALTA) {
			data = new EnvioRemoto();
			data.setAmbito(TypeAmbito.fromString(ambito));
		} else {
			if (id != null) {
				data = envioRemotoService.loadEnvio(Long.valueOf(id));
			}
		}

		// Si no tiene codigo, desactivar botones de dominios / gestores
		if (data == null || data.getCodigo() == null) {
			desactivarBotoneraActivo = true;
		}

		// Si se pasa una propiedad, se fuerza a que no se vean los botones de dominios
		// / gestores
		if (desactivarBotonera != null && "true".equals(desactivarBotonera)) {
			desactivarBotoneraActivo = true;
		}

		if (area != null) {
			idArea = Long.valueOf(area);
		}

		configuraciones = configuracionAutenticacionService.listConfiguracionAutenticacion(
				TypeAmbito.fromString(ambito), idArea, UtilJSF.getIdEntidad(),
				TypeIdioma.fromString(UtilJSF.getSessionBean().getLang()), null);
		final ConfiguracionAutenticacion configAutSinAutenticacion = new ConfiguracionAutenticacion();
		configAutSinAutenticacion.setCodigo(null);
		configAutSinAutenticacion.setIdentificador(UtilJSF.getLiteral("dialogDominio.sinAutenticacion"));
		configuraciones.add(0, configAutSinAutenticacion);
		actualizarConf();
	}

	public void actualizarConf() {
		desactivarConsulta = false;
		if (this.data.getConfiguracionAutenticacion() == null) {
			desactivarConsulta = true;
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		data.setAmbito(TypeAmbito.fromString(ambito));
		if (data.getConfiguracionAutenticacion().getIdentificador()
				.equals(UtilJSF.getLiteral("dialogDominio.sinAutenticacion"))) {
			data.setConfiguracionAutenticacion(null);
		}
		if (TypeAmbito.AREA.toString().equals(ambito)) {
			if (idArea != null) {
				data.setArea(tramiteService.getArea(idArea));
			}
		} else if (TypeAmbito.ENTIDAD.toString().equals(ambito)) {
			data.setEntidad(entidadService.loadEntidad(Long.valueOf(entidad)));
		}
		switch (acceso) {
		case ALTA:
			if (!verificarGuardar()) {
				return;
			}
			Long idConf = envioRemotoService.addEnvio(data, UtilJSF.getIdEntidad(), idArea);
			this.data.setCodigo(idConf);
			break;
		case EDICION:
			if (!verificarGuardar()) {
				return;
			}

			envioRemotoService.updateEnvio(data);

			break;
		case CONSULTA:
			// No hay que hacer nada
			break;
		}
		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Abre dialogo de dominios.
	 *
	 */
	public void dominios() {

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.data.getCodigo()));
		params.put("AMBITO", data.getAmbito().toString());
		params.put("AREA", this.area);
		UtilJSF.openDialog(DialogConfiguracionDominio.class, TypeModoAcceso.CONSULTA, params, true, 770, 400);
	}

	/**
	 * Abre dialogo de tramites.
	 *
	 */
	public void tramites() {

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.data.getCodigo()));
		if (ambito != null) {
			params.put(TypeParametroVentana.AMBITO.toString(), ambito);
			final TypeAmbito typeAmbito = TypeAmbito.fromString(ambito);
			if (typeAmbito == TypeAmbito.AREA) {
				params.put("AREA", Long.toString(this.data.getArea().getCodigo()));
			}
			if (typeAmbito == TypeAmbito.ENTIDAD) {
				params.put("ENTIDAD", Long.toString(this.data.getEntidad().getCodigo()));
			}
		}
		UtilJSF.openDialog(DialogEnvioRemotoTramites.class, TypeModoAcceso.CONSULTA, params, true, 770, 400);
	}

	/**
	 * Verificar precondiciones al guardar.
	 *
	 * @return true, si se cumplen las todas la condiciones
	 */
	private boolean verificarGuardar() {
		if (data.getIdentificador() == null || data.getIdentificador().isEmpty()) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.obligatorio"));
			return false;
		}

		if (data.getDescripcion() == null || data.getDescripcion().isEmpty()) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.obligatorio"));
			return false;
		}

		if (data.getUrl() == null || data.getUrl().isEmpty()) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.obligatorio"));
			return false;
		}

		final boolean existe = envioRemotoService.existeEnvioByIdentificador(TypeAmbito.fromString(ambito),
				data.getIdentificador(), UtilJSF.getIdEntidad(), idArea, data.getCodigo());

		if (existe) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.identificador.repetido"));
			return false;
		}
		return true;
	}

	/**
	 * Consultar configuración
	 */
	public void consultarConfiguracion() {

		if (this.data.getConfiguracionAutenticacion() != null) {
			// Muestra dialogo
			final Map<String, String> params = new HashMap<>();
			params.put(TypeParametroVentana.AMBITO.toString(), ambito);
			if (TypeAmbito.AREA.toString().equals(ambito)) {
				params.put(TypeParametroVentana.AREA.toString(), this.idArea.toString());
			} else if (TypeAmbito.ENTIDAD.toString().equals(ambito)) {
				params.put(TypeParametroVentana.ENTIDAD.toString(), UtilJSF.getIdEntidad().toString());
			}
			params.put(TypeParametroVentana.ID.toString(),
					this.data.getConfiguracionAutenticacion().getCodigo().toString());
			UtilJSF.openDialog(DialogConfiguracionAutenticacion.class, TypeModoAcceso.CONSULTA, params, true, 550, 195);
		}
	}

	/**
	 * Copiado correctamente
	 */
	public void copiadoCorr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
	}

	/**
	 * Copiado error
	 */
	public void copiadoErr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
				UtilJSF.getLiteral("viewAuditoriaTramites.headError") + ' ' + UtilJSF.getLiteral("botones.copiar"));
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
	public EnvioRemoto getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final EnvioRemoto data) {
		this.data = data;
	}

	/**
	 * @return the iData
	 */
	public String getiData() {
		return iData;
	}

	/**
	 * @param iData the iData to set
	 */
	public void setiData(final String iData) {
		this.iData = iData;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @return the modoImportar
	 */
	public String getModoImportar() {
		return modoImportar;
	}

	/**
	 * @param modoImportar the modoImportar to set
	 */
	public void setModoImportar(String modoImportar) {
		this.modoImportar = modoImportar;
	}

	/**
	 * @return the desactivarBotonera
	 */
	public String getDesactivarBotonera() {
		return desactivarBotonera;
	}

	/**
	 * @param desactivarBotonera the desactivarBotonera to set
	 */
	public void setDesactivarBotonera(String desactivarBotonera) {
		this.desactivarBotonera = desactivarBotonera;
	}

	/**
	 * @return the desactivarBotoneraActivo
	 */
	public boolean isDesactivarBotoneraActivo() {
		return desactivarBotoneraActivo;
	}

	/**
	 * @param desactivarBotoneraActivo the desactivarBotoneraActivo to set
	 */
	public void setDesactivarBotoneraActivo(boolean desactivarBotoneraActivo) {
		this.desactivarBotoneraActivo = desactivarBotoneraActivo;
	}

	/**
	 * @return the entidad
	 */
	public String getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad the entidad to set
	 */
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	/**
	 * @return the ambito
	 */
	public String getAmbito() {
		return ambito;
	}

	/**
	 * @param ambito the ambito to set
	 */
	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

	/** Ayuda. */
	public void ayuda() {
		UtilJSF.openHelp("envioRemoto");
	}

	/**
	 * @return the idArea
	 */
	public final Long getIdArea() {
		return idArea;
	}

	/**
	 * @param idArea the idArea to set
	 */
	public final void setIdArea(Long idArea) {
		this.idArea = idArea;
	}

	/**
	 * @return the configuraciones
	 */
	public final List<ConfiguracionAutenticacion> getConfiguraciones() {
		return configuraciones;
	}

	/**
	 * @param configuraciones the configuraciones to set
	 */
	public final void setConfiguraciones(List<ConfiguracionAutenticacion> configuraciones) {
		this.configuraciones = configuraciones;
	}

	/**
	 * @return the desactivarConsulta
	 */
	public final Boolean getDesactivarConsulta() {
		return desactivarConsulta;
	}

	/**
	 * @param desactivarConsulta the desactivarConsulta to set
	 */
	public final void setDesactivarConsulta(Boolean desactivarConsulta) {
		this.desactivarConsulta = desactivarConsulta;
	}

}
