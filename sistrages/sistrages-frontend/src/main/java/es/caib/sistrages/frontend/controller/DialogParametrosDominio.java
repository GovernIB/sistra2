package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistrages.core.api.model.ParametroDominio;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.types.TypeParametroDominio;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogParametrosDominio extends DialogControllerBase {

	/**
	 * Dato elemento en formato JSON.
	 */
	private String iData;

	/**
	 * Datos elemento.
	 */
	private ParametroDominio data;

	private List<Propiedad> listaParametros;

	private String portapapeles;

	/**
	 * Inicialización.
	 */
	@SuppressWarnings("unchecked")
	public void init() {
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();

		if (!mochilaDatos.isEmpty()) {
			setListaParametros((List<Propiedad>) mochilaDatos.get(Constantes.CLAVE_MOCHILA_PARAMDOM));
		}

		if (getListaParametros() == null) {
			setListaParametros(new ArrayList<>());
		}

		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new ParametroDominio();
			data.setCodigo(System.currentTimeMillis() * -1);
		} else {
			data = (ParametroDominio) UtilJSON.fromJSON(iData, ParametroDominio.class);
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		if (data.getTipo() == TypeParametroDominio.BUSQUEDA) {
			data.setValor("-");
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

	/**
	 * @return the data
	 */
	public ParametroDominio getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final ParametroDominio data) {
		this.data = data;
	}

	public List<Propiedad> getListaParametros() {
		return listaParametros;
	}

	public void setListaParametros(List<Propiedad> listaParametros) {
		this.listaParametros = listaParametros;
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

}
