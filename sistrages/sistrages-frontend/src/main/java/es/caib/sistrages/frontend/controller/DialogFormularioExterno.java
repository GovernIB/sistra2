package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.DominioTramite;
import es.caib.sistrages.core.api.model.GestorExternoFormularios;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.service.ConfiguracionAutenticacionService;
import es.caib.sistrages.core.api.service.FormularioExternoService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogFormularioExterno extends DialogControllerBase {

	/** Servicio. */
	@Inject
	private FormularioExternoService formularioService;

	/** ConfiguracionAutenticacion Service **/
	@Inject
	private ConfiguracionAutenticacionService configuracionAutenticacionService;

	/** Enlace servicio. */
	@Inject
	private TramiteService tramiteService;

	/** Id elemento a tratar. */
	private String id;

	/** Id elemento a tratar. */
	private String area;

	/** Data en formato JSON. */
	private String iData;

	/** Datos elemento. */
	private GestorExternoFormularios data;

	/** Identificador de Configuracion seleccionada */
	private String identificadorConfSeleccionada;

	/** Lista de configuraciones. */
	private List<ConfiguracionAutenticacion> configuraciones;

	/** Mostrar advertencia al guardar. **/
	private boolean mostrarAdvertencia = false;

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
			data = new GestorExternoFormularios();

		} else {
			data = formularioService.getFormularioExterno(new Long(id));
		}

		configuraciones = configuracionAutenticacionService.listConfiguracionAutenticacion(TypeAmbito.AREA,
				Long.valueOf(area), null, TypeIdioma.fromString(UtilJSF.getSessionBean().getLang()), null);
		ConfiguracionAutenticacion configAutSinAutenticacion = new ConfiguracionAutenticacion();
		configAutSinAutenticacion.setCodigo(null);
		configAutSinAutenticacion.setIdentificador(UtilJSF.getLiteral("dialogDominio.sinAutenticacion"));
		configuraciones.add(0, configAutSinAutenticacion);
		actualizarConf();

		if (id != null) {
			final List<DominioTramite> tramites = tramiteService.getTramiteVersionByGfe(Long.valueOf(id));
			if (tramites == null || tramites.isEmpty()) {
				setMostrarAdvertencia(false);
			} else {
				setMostrarAdvertencia(true);
			}
		}

	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		if (data.getConfiguracionAutenticacion().getIdentificador()
				.equals(UtilJSF.getLiteral("dialogDominio.sinAutenticacion"))) {
			data.setConfiguracionAutenticacion(null);
		}
		switch (acceso) {
		case ALTA:
			if (!verificarGuardar()) {
				return;
			}
			formularioService.addFormularioExterno(Long.valueOf(area), data);
			break;
		case EDICION:
			if (!verificarGuardar()) {
				return;
			}

			formularioService.updateFormularioExterno(data);
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
	 * Consultar configuracion
	 */
	public void consultarConfiguracion() {
		// Consulta
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.AREA.toString(), this.area);
		params.put(TypeParametroVentana.ID.toString(),
				String.valueOf(this.data.getConfiguracionAutenticacion().getCodigo()));
		UtilJSF.openDialog(DialogConfiguracionAutenticacion.class, TypeModoAcceso.CONSULTA, params, true, 550, 195);
	}

	/**
	 * Crea nueva propiedad.
	 */
	public void nuevaConfiguracion() {

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.AREA.toString(), this.area);
		UtilJSF.openDialog(DialogConfiguracionAutenticacion.class, TypeModoAcceso.ALTA, params, true, 550, 195);

	}

	public void actualizarConf() {
		desactivarConsulta = false;
		if (this.data.getConfiguracionAutenticacion() == null) {
			desactivarConsulta = true;
		}
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoConf(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			String message = UtilJSF.getLiteral("info.alta.ok");
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);

			// La ponemos por defecto
			ConfiguracionAutenticacion conf = (ConfiguracionAutenticacion) ((DialogResult) event.getObject())
					.getResult();
			this.data.setConfiguracionAutenticacion(conf);
			this.configuraciones.add(conf);
		}
	}

	/**
	 * Abre dialogo de tramites.
	 *
	 * @param modoAccesoDlg Modo acceso
	 */
	public void tramites() {

		boolean existenTramites = tramiteService.getCountTramiteVersionByGfe(Long.valueOf(id));

		if (existenTramites) {
			String ambito = "A";

			// Muestra dialogo
			final Map<String, String> params = new HashMap<>();
			params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.data.getCodigo()));
			if (ambito != null) {
				params.put(TypeParametroVentana.AMBITO.toString(), ambito);
				final TypeAmbito typeAmbito = TypeAmbito.fromString(ambito);
				if (typeAmbito == TypeAmbito.AREA) {
					params.put("AREA", id);
				}
				if (typeAmbito == TypeAmbito.ENTIDAD) {
					params.put("ENTIDAD", id);
				}
			}
			UtilJSF.openDialog(DialogGestorExternoTramites.class, TypeModoAcceso.CONSULTA, params, true, 770, 400);
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
					UtilJSF.getLiteral("dialogFormularioExterno.error.sinTramitesAsociados"));
		}
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

		final boolean existe = formularioService.existeFormulario(data.getIdentificador(), data.getCodigo(),
				Long.valueOf(area));

		if (existe) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.identificador.repetido"));
			return false;
		}
		return true;
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
	public GestorExternoFormularios getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final GestorExternoFormularios data) {
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
	 * @return the configuraciones
	 */
	public List<ConfiguracionAutenticacion> getConfiguraciones() {
		return configuraciones;
	}

	/**
	 * @param configuraciones the configuraciones to set
	 */
	public void setConfiguraciones(List<ConfiguracionAutenticacion> configuraciones) {
		this.configuraciones = configuraciones;
	}

	/** Ayuda. */
	public void ayuda() {
		UtilJSF.openHelp("formulariosExternosDialog");
	}

	/** Verificar si se utiliza, sino borrarlo **/
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		DialogFormularioExterno that = (DialogFormularioExterno) o;
		return Objects.equals(formularioService, that.formularioService)
				&& Objects.equals(configuracionAutenticacionService, that.configuracionAutenticacionService)
				&& Objects.equals(id, that.id) && Objects.equals(area, that.area) && Objects.equals(iData, that.iData)
				&& Objects.equals(data, that.data)
				&& Objects.equals(identificadorConfSeleccionada, that.identificadorConfSeleccionada)
				&& Objects.equals(configuraciones, that.configuraciones)
				&& Objects.equals(desactivarConsulta, that.desactivarConsulta);
	}

	/** Verificar si se utiliza, sino borrarlo **/
	@Override
	public int hashCode() {
		return Objects.hash(formularioService, configuracionAutenticacionService, id, area, iData, data,
				identificadorConfSeleccionada, configuraciones, desactivarConsulta);
	}

	public Boolean getDesactivarConsulta() {
		return desactivarConsulta;
	}

	public void setDesactivarConsulta(Boolean desactivarConsulta) {
		this.desactivarConsulta = desactivarConsulta;
	}

	/**
	 * @return the mostrarAdvertencia
	 */
	public boolean isMostrarAdvertencia() {
		return mostrarAdvertencia;
	}

	/**
	 * @param mostrarAdvertencia the mostrarAdvertencia to set
	 */
	public void setMostrarAdvertencia(boolean mostrarAdvertencia) {
		this.mostrarAdvertencia = mostrarAdvertencia;
	}
}
