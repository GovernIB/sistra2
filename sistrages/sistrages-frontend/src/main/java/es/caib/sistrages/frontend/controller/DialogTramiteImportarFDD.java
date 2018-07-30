package es.caib.sistrages.frontend.controller;

import java.io.ByteArrayInputStream;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistrages.core.api.model.comun.CsvDocumento;
import es.caib.sistrages.core.api.util.CsvUtil;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramiteImportarFDD extends DialogControllerBase {

	/** Csv docuemnto. **/
	private CsvDocumento csvdocumento;

	/**
	 * Inicialización.
	 *
	 * @throws Exception
	 */
	public void init() throws Exception {
		final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
		final byte[] valores = (byte[]) mochila.get(Constantes.CLAVE_MOCHILA_FUENTEVALORES);
		csvdocumento = CsvUtil.importar(new ByteArrayInputStream(valores));
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
	 * @return the csvdocumento
	 */
	public CsvDocumento getCsvdocumento() {
		return csvdocumento;
	}

	/**
	 * @param csvdocumento
	 *            the csvdocumento to set
	 */
	public void setCsvdocumento(final CsvDocumento csvdocumento) {
		this.csvdocumento = csvdocumento;
	}

}
