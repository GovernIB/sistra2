package es.caib.sistrages.frontend.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.exception.FrontException;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.ValorListaFija;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

@ManagedBean
@ViewScoped
public class DialogListaValoresFija extends DialogControllerBase {

	/**
	 * Dato elemento en formato JSON.
	 */
	private String iData;

	/**
	 * Datos elemento.
	 */
	private ValorListaFija data;

	private Literal traduccionesEdit;

	private List<String> idiomas;

	private String portapapeles;

	private String errorCopiar;

	/**
	 * Inicialización.
	 */
	@SuppressWarnings("unchecked")
	public void init() {
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();

		if (!mochilaDatos.isEmpty()) {
			idiomas = (List<String>) mochilaDatos.get(Constantes.CLAVE_MOCHILA_IDIOMASXDEFECTO);
		}

		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new ValorListaFija();
			data.setCodigo(System.currentTimeMillis() * -1);
		} else {
			data = (ValorListaFija) UtilJSON.fromJSON(iData, ValorListaFija.class);
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		// Retornamos resultado
		if (data.getDescripcion() == null) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("dialogListaValoresFija.errVacio"));
			return;
		}

		if (!data.getDescripcion().getIdiomas().containsAll(idiomas)) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("dialogListaValoresFija.errIdiomas"));
			return;
		}
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

	public void returnDialogoTraducciones(final SelectEvent event) {
		try {
			final DialogResult respuesta = (DialogResult) event.getObject();
			// Solo tiene sentido cambios para edicion
			if (!respuesta.isCanceled() && respuesta.getModoAcceso() == TypeModoAcceso.EDICION) {
				final Literal traduccionesMod = (Literal) respuesta.getResult();
				if (traduccionesEdit == null) {
					traduccionesEdit = new Literal();
				}
				BeanUtils.copyProperties(getTraduccionesEdit(), traduccionesMod);
			} else if (respuesta.isCanceled() && respuesta.getModoAcceso() == TypeModoAcceso.EDICION) {
				setTraduccionesEdit(null);
			}

			if (traduccionesEdit != null && data.getDescripcion() == null) {
				data.setDescripcion(traduccionesEdit);
			}

		} catch (IllegalAccessException | InvocationTargetException e) {
			UtilJSF.loggearErrorFront("Error establecimiendo traducciones", e);
			throw new FrontException("Error estableciendo traducciones", e);
		}

	}

	/**
	 * Editar texto componente.
	 */
	public void editarTraducciones() {

		if (data.getDescripcion() != null) {
			setTraduccionesEdit(data.getDescripcion());
		}

		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, data.getDescripcion(), idiomas, idiomas);
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
	public ValorListaFija getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final ValorListaFija data) {
		this.data = data;
	}

	public Literal getTraduccionesEdit() {
		return traduccionesEdit;
	}

	public void setTraduccionesEdit(final Literal traduccionesEdit) {
		this.traduccionesEdit = traduccionesEdit;
	}

	public List<String> getIdiomas() {
		return idiomas;
	}

	public void setIdiomas(final List<String> idiomasXdefecto) {
		this.idiomas = idiomasXdefecto;
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
}
