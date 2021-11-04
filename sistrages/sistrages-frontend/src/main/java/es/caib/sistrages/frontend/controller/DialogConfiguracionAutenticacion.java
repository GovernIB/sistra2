package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.ConfiguracionAutenticacionService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogConfiguracionAutenticacion extends DialogControllerBase {

	/** Servicio. */
	@Inject
	private ConfiguracionAutenticacionService configuracionAutenticacionService;

	/** Id elemento a tratar. */
	private String id;

	/** Id elemento a tratar. */
	private String area;

	/** Data en formato JSON. */
	private String iData;

	/** Datos elemento. */
	private ConfiguracionAutenticacion data;

	/** Parametro modo importar **/
	private String modoImportar;

	private boolean modoImportarActivo = false;
	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		UtilJSF.checkSecOpenDialog(modo, id);

		if (modo == TypeModoAcceso.ALTA) {
			data = new ConfiguracionAutenticacion();
		} else {
			if (id != null) {
				data = configuracionAutenticacionService.getConfiguracionAutenticacion(new Long(id));
			}
		}

		if (modoImportar != null && "true".equals(modoImportar)) {
			modoImportarActivo = true;
			if (iData != null) {
				data = (ConfiguracionAutenticacion) UtilJSON.fromJSON(iData, ConfiguracionAutenticacion.class);
			} else {
				data = new ConfiguracionAutenticacion();
			}
		} else {
			modoImportarActivo = false;
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		switch (acceso) {
		case ALTA:
			if (!verificarGuardar()) {
				return;
			}
			if (modoImportarActivo) {
				this.data.setCodigoImportacion(-1l);
			} else {
				Long idConf = configuracionAutenticacionService.addConfiguracionAutenticacion(Long.valueOf(area), data);
				this.data.setCodigo(idConf);
			}
			break;
		case EDICION:
			if (!verificarGuardar()) {
				return;
			}

			if (!modoImportarActivo) {
				configuracionAutenticacionService.updateConfiguracionAutenticacion(data);
			}
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
		params.put("AMBITO", TypeAmbito.AREA.toString());
		params.put("AREA", this.area);
		UtilJSF.openDialog(DialogConfiguracionDominio.class, TypeModoAcceso.CONSULTA, params, true, 770, 400);
	}

	/**
	 * Abre dialogo de configuraciones autenticaciones.
	 *
	 */
	public void formsExternos() {

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.data.getCodigo()));
		params.put("AMBITO", TypeAmbito.AREA.toString());
		params.put("AREA", this.area);
		UtilJSF.openDialog(DialogConfiguracionFormulario.class, TypeModoAcceso.CONSULTA, params, true, 770, 400);
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

		if (data.getUsuario() == null || data.getUsuario().isEmpty()) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.obligatorio"));
			return false;
		}

		if (data.getPassword() == null || data.getPassword().isEmpty()) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.obligatorio"));
			return false;
		}

		final boolean existe = configuracionAutenticacionService.existeConfiguracionAutenticacion(data.getIdentificador(), data.getCodigo());

		if (existe) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.identificador.repetido"));
			return false;
		}
		return true;
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
	public ConfiguracionAutenticacion getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final ConfiguracionAutenticacion data) {
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

	/** Ayuda. */
	public void ayuda() {
		UtilJSF.openHelp("configuracionAutenticacionDialog");
	}

}
