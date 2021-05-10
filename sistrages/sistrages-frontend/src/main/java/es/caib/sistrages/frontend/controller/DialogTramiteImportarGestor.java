package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.comun.FilaImportarGestor;
import es.caib.sistrages.core.api.model.types.TypeIdioma;
import es.caib.sistrages.core.api.model.types.TypeImportarEstado;
import es.caib.sistrages.core.api.service.ConfiguracionAutenticacionService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
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
			configuraciones = configuracionAutenticacionService.listConfiguracionAutenticacion(area, TypeIdioma.fromString(UtilJSF.getSessionBean().getLang()), null);
		}
		ConfiguracionAutenticacion configAutSinAutenticacion = new ConfiguracionAutenticacion();
		configAutSinAutenticacion.setCodigo(null);
		configAutSinAutenticacion.setIdentificador(UtilJSF.getLiteral("dialogDominio.sinAutenticacion"));
		configuraciones.add(0, configAutSinAutenticacion);

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
