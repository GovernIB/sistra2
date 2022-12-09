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
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.model.types.TypeImportarEstado;
import es.caib.sistrages.core.api.service.ConfiguracionAutenticacionService;
import es.caib.sistrages.core.api.util.UtilJSON;
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

	private String portapapeles;

	/**
	 * Inicialización.
	 */
	public void init() {
		data = (FilaImportarGestor) UtilJSF.getSessionBean().getMochilaDatos().get(Constantes.CLAVE_MOCHILA_IMPORTAR);
		area = (Long) UtilJSF.getSessionBean().getMochilaDatos().get(Constantes.AREA);
		if (area == null) {
			configuraciones = new ArrayList<>();
		} else {
			configuraciones = configuracionAutenticacionService.listConfiguracionAutenticacion(TypeAmbito.AREA, area,
					null, TypeIdioma.fromString(UtilJSF.getSessionBean().getLang()), null);
		}
		ConfiguracionAutenticacion configAutSinAutenticacion = new ConfiguracionAutenticacion();
		configAutSinAutenticacion.setCodigo(null);
		configAutSinAutenticacion.setIdentificador(UtilJSF.getLiteral("dialogDominio.sinAutenticacion"));
		configuraciones.add(0, configAutSinAutenticacion);

		if (data.getConfiguracionAutenticacionActual() != null
				&& data.getConfiguracionAutenticacionActual().getCodigoImportacion() != null) {
			configuraciones.add(data.getConfiguracionAutenticacionActual());
		}

	}

	/**
	 * Consultar configuración
	 */
	public void consultarConfiguracion() {

		// Muestra dialogo
		if (data.getConfiguracionAutenticacionActual() == null
				|| data.getConfiguracionAutenticacionActual().getCodigo() == null) {
			if (data.getConfiguracionAutenticacionActual() != null
					&& data.getConfiguracionAutenticacionActual().getCodigoImportacion() == null) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			} else {
				nuevo();
			}
		} else {
			final Map<String, String> params = new HashMap<>();
			if (area != null) {
				params.put(TypeParametroVentana.AREA.toString(), area.toString());
			}
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
		if (data.getConfiguracionAutenticacionActual() != null
				&& data.getConfiguracionAutenticacionActual().getCodigo() == null
				&& data.getConfiguracionAutenticacionActual().getCodigoImportacion() == null) {
			data.setConfiguracionAutenticacionActual(null);
		}

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

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();

		if (area != null) {
			params.put(TypeParametroVentana.AREA.toString(), this.area.toString());
		}

		params.put(TypeParametroVentana.MODO_IMPORTAR.toString(), "true");
		// Como los GFE son solo de ambito area, se puede forzar el ambito a la fuerza
		params.put(TypeParametroVentana.AMBITO.toString(), TypeAmbito.AREA.toString());

		TypeModoAcceso modo = TypeModoAcceso.ALTA;
		if (data.getConfiguracionAutenticacionActual() != null
				&& data.getConfiguracionAutenticacionActual().getCodigoImportacion() != null
				&& data.getConfiguracionAutenticacionActual().getCodigo() == null) {
			// Pasamos la configuracion pasada
			params.put(TypeParametroVentana.DATO.toString(),
					UtilJSON.toJSON(this.data.getConfiguracionAutenticacionActual()));
			modo = TypeModoAcceso.EDICION;
		}
		UtilJSF.openDialog(DialogConfiguracionAutenticacion.class, modo, params, true, 550, 195);
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
			boolean contiene = false;
			// Buscamos por si ya está asignada
			if (configuraciones != null) {
				for (ConfiguracionAutenticacion conf : configuraciones) {
					if (conf.getCodigo() == null && conf.getCodigoImportacion() != null
							&& conf.getCodigoImportacion().compareTo(confNueva.getCodigoImportacion()) == 0) {
						conf.setDescripcion(confNueva.getDescripcion());
						conf.setIdentificador(confNueva.getIdentificador());
						conf.setPassword(confNueva.getPassword());
						conf.setUsuario(confNueva.getUsuario());
						contiene = true;
						break;
					}
				}
			}

			if (!contiene) {
				configuraciones.add(confNueva);
			}
			data.setConfiguracionAutenticacionActual(confNueva);

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
