package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.comun.FilaImportarGestor;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.model.types.TypeImportarEstado;
import es.caib.sistrages.core.api.service.ConfiguracionAutenticacionService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramiteImportarGestor extends DialogControllerBase {

	@Inject
	ConfiguracionAutenticacionService configuracionAutenticacionService;

	/** Fila Importar. */
	private FilaImportarGestor data;

	/** Tramites. **/
	private List<ConfiguracionAutenticacion> configuraciones;

	/** Accion. **/
	private String accion;

	/** Area **/
	private Long area;

	/**
	 * Inicialización.
	 */
	public void init() {
		data = (FilaImportarGestor) UtilJSF.getSessionBean().getMochilaDatos().get(Constantes.CLAVE_MOCHILA_IMPORTAR);
		area = (Long) UtilJSF.getSessionBean().getMochilaDatos().get(Constantes.AREA);
		if (area == null) {
			configuraciones = new ArrayList<>();
		} else {
			configuraciones = configuracionAutenticacionService.listConfiguracionAutenticacion(area,
					TypeIdioma.fromString(UtilJSF.getSessionBean().getLang()), null);
		}
		ConfiguracionAutenticacion configAutSinAutenticacion = new ConfiguracionAutenticacion();
		configAutSinAutenticacion.setCodigo(null);
		configAutSinAutenticacion.setIdentificador(UtilJSF.getLiteral("dialogDominio.sinAutenticacion"));
		configuraciones.add(0, configAutSinAutenticacion);

	}

	/**
	 * Consultar configuración
	 */
	public void consultarConfiguracion() {

		// Muestra dialogo
		if (data.getConfiguracionAutenticacionActual() == null
				|| data.getConfiguracionAutenticacionActual().getCodigo() == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
		} else {
			final Map<String, String> params = new HashMap<>();
			params.put(TypeParametroVentana.AREA.toString(), area.toString());
			params.put(TypeParametroVentana.ID.toString(),
					data.getConfiguracionAutenticacionActual().getCodigo().toString());
			UtilJSF.openDialog(DialogConfiguracionAutenticacion.class, TypeModoAcceso.CONSULTA, params, true, 550, 195);
		}
	}

	/**
	 * Guardar.
	 */
	public void guardar() {

		data.setMensaje(null);
		data.setEstado(TypeImportarEstado.REVISADO);

		UtilJSF.getSessionBean().limpiaMochilaDatos();
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
		mochilaDatos.put(Constantes.CLAVE_MOCHILA_IMPORTAR, this.data);

		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(false);
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
	 * Abre dialogo para nuevo dato.
	 */
	public void nuevo() {
		abrirDlg(TypeModoAcceso.ALTA);
	}

	/**
	 * Abrir dialogo.
	 *
	 * @param modoAccesoDlg Modo acceso
	 */
	private void abrirDlg(final TypeModoAcceso modoAccesoDlg) {

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();

		params.put(TypeParametroVentana.AREA.toString(), this.area.toString());
		UtilJSF.openDialog(DialogConfiguracionAutenticacion.class, modoAccesoDlg, params, true, 550, 195);
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			String message = null;
			if (respuesta.getModoAcceso().equals(TypeModoAcceso.ALTA)) {
				message = UtilJSF.getLiteral("info.alta.ok");
			} else {
				message = UtilJSF.getLiteral("info.modificado.ok");
			}
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
			// Refrescamos datos
			ConfiguracionAutenticacion confNueva = (ConfiguracionAutenticacion) respuesta.getResult();
			configuraciones.add(confNueva);
			data.setConfiguracionAutenticacionActual(confNueva);
			// checkActualizar();
		}
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final FilaImportarGestor data) {
		this.data = data;
	}

	/**
	 * @return the data
	 */
	public FilaImportarGestor getData() {
		return data;
	}

	/**
	 * @return the accion
	 */
	public String getAccion() {
		return accion;
	}

	/**
	 * @param accion the accion to set
	 */
	public void setAccion(final String accion) {
		this.accion = accion;
	}

	/**
	 * @return the area
	 */
	public Long getArea() {
		return area;
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

	/**
	 * @param area the area to set
	 */
	public void setArea(Long area) {
		this.area = area;
	}

}
