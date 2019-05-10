package es.caib.sistrages.frontend.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.FuenteDatosCampo;
import es.caib.sistrages.core.api.model.FuenteDatosValor;
import es.caib.sistrages.core.api.model.FuenteFila;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogFuenteFila extends DialogControllerBase {

	/** Enlace servicio. */
	@Inject
	private DominioService dominioService;

	/** Id elemento a tratar. */
	private String id;

	/** Fuente de campos JSON. **/
	private String iCampos;

	/** Fuente de campos. **/
	private List<FuenteDatosCampo> campos;

	/** Id fuente datos. **/
	private String idFuenteDato;

	/** Datos elemento en JSON. **/
	private String iData;

	/** Datos elemento. */
	private FuenteFila data;

	/**
	 * Los datos.
	 *
	 * @see Para hacer las columnas mas dinamicas:
	 *      https://www.primefaces.org/showcase/ui/data/datatable/columns.xhtml
	 **/
	private List<FuenteFila> datos;

	/** Fila seleccionada. **/
	private FuenteFila valorSeleccionado;

	/** Literal que indica el valor que tenía la pk. **/
	private String pkOriginal = "";

	/**
	 * Inicialización.
	 *
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			campos = (List<FuenteDatosCampo>) UtilJSON.fromListJSON(iData, FuenteDatosCampo.class);
			data = new FuenteFila();
			for (final FuenteDatosCampo campo : this.getCampos()) {
				final FuenteDatosValor dato = new FuenteDatosValor();
				dato.setIdCampo(campo.getIdentificador());
				dato.setOrdenCampo(campo.getOrden());
				dato.setCampo(campo);
				dato.setValor("");
				data.addFuenteDato(dato);
			}
		} else {
			data = dominioService.loadFuenteDatoFila(Long.valueOf(id));
			data.sortValores();
			pkOriginal = getPk(data);
		}

	}

	/**
	 * Obtiene los valores de filas de tipo pk.
	 *
	 * @param fila
	 * @return
	 */
	private String getPk(final FuenteFila fila) {
		final StringBuilder pk = new StringBuilder();
		for (final FuenteDatosValor dato : fila.getDatos()) {
			if (dato.getCampo().isClavePrimaria()) {
				pk.append(dato.getValor() + "#");
			}
		}
		return pk.toString();
	}

	/**
	 * Aceptar. Se cerrará la ventana si:
	 * <ul>
	 * <li>Los valores de campos de tipo pk no se han alterado.</li>
	 * <li>Los valores de campos de tipo pk se han alterado pero aun así, no se
	 * repiten los valores en otra fila de la FD</li>
	 * </ul>
	 * Si no se produce ninguno de esos casos, se muestra un error.
	 */
	public void aceptar() {

		if (pkOriginal.equals(getPk(data)) || dominioService.isCorrectoPK(data, Long.valueOf(idFuenteDato))) {
			// Retornamos resultado
			final DialogResult result = new DialogResult();
			result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
			result.setResult(data);
			UtilJSF.closeDialog(result);
		} else {
			final String message = UtilJSF.getLiteral("error.fuentedatos.pk");
			addMessageContext(TypeNivelGravedad.INFO, message);
		}
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
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the iCampos
	 */
	public String getiCampos() {
		return iCampos;
	}

	/**
	 * @param iCampos
	 *            the iCampos to set
	 */
	public void setiCampos(final String iCampos) {
		this.iCampos = iCampos;
	}

	/**
	 * @return the campos
	 */
	public List<FuenteDatosCampo> getCampos() {
		return campos;
	}

	/**
	 * @param campos
	 *            the campos to set
	 */
	public void setCampos(final List<FuenteDatosCampo> campos) {
		this.campos = campos;
	}

	/**
	 * @return the iData
	 */
	public String getiData() {
		return iData;
	}

	/**
	 * @param iData
	 *            the iData to set
	 */
	public void setiData(final String iData) {
		this.iData = iData;
	}

	/**
	 * @return the data
	 */
	public FuenteFila getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final FuenteFila data) {
		this.data = data;
	}

	/**
	 * @return the datos
	 */
	public List<FuenteFila> getDatos() {
		return datos;
	}

	/**
	 * @param datos
	 *            the datos to set
	 */
	public void setDatos(final List<FuenteFila> datos) {
		this.datos = datos;
	}

	/**
	 * @return the valorSeleccionado
	 */
	public FuenteFila getValorSeleccionado() {
		return valorSeleccionado;
	}

	/**
	 * @param valorSeleccionado
	 *            the valorSeleccionado to set
	 */
	public void setValorSeleccionado(final FuenteFila valorSeleccionado) {
		this.valorSeleccionado = valorSeleccionado;
	}

	/**
	 * @return the idFuenteDato
	 */
	public String getIdFuenteDato() {
		return idFuenteDato;
	}

	/**
	 * @param idFuenteDato
	 *            the idFuenteDato to set
	 */
	public void setIdFuenteDato(final String idFuenteDato) {
		this.idFuenteDato = idFuenteDato;
	}

	public boolean getPermiteEditar() {
		return (TypeModoAcceso.valueOf(modoAcceso) == TypeModoAcceso.ALTA
				|| TypeModoAcceso.valueOf(modoAcceso) == TypeModoAcceso.EDICION);
	}

	public boolean getPermiteConsulta() {
		return (TypeModoAcceso.valueOf(modoAcceso) == TypeModoAcceso.CONSULTA);
	}
}
