package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.DominioTramite;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogFuenteDominios extends DialogControllerBase {

	/** Id elemento a tratar. */
	private String id;

	/** Enlace servicio. */
	@Inject
	private TramiteService tramiteService;

	/** Enlace servicio. */
	@Inject
	private DominioService dominioService;

	@Inject
	private EntidadService entidadService;

	/** Datos elemento. */
	private FuenteDatos data;

	/** listaDominios. **/
	private List<String> listaDominios;

	/** Campos. **/
	private List<Dominio> campos;

	/** Campos en formato JSON. **/
	private String iCampos;

	/** Fila seleccionada. **/
	private Dominio valorSeleccionado;

	/** Ambito. **/
	private String ambito;

	/** Area **/
	private String area;

	private String portapapeles;

	private String errorCopiar;

	/**
	 * Inicialización.
	 */
	public void init() {
		data = dominioService.loadFuenteDato(Long.valueOf(id));
		campos = new ArrayList<Dominio>();
		listaDominios = dominioService.listDominiosByFD(Long.valueOf(id));
		if (data.getArea() != null) {
			buscarDominios();
		} else {
			buscarDominios();
		}
	}

	private void buscarDominios() {
		for (String dom : listaDominios) {
			Dominio dominio = dominioService.loadDominioByIdentificadorCompuesto(dom);
			campos.add(dominio);
		}
	}

	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	public void consultarTramite() {
		if (this.valorSeleccionado == null) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			return;
		}

		// Tener cuidado, si se tiene perfil superadmin, no se puede acceder!!! Y habría
		// que más bien mandar al padre a ello

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.valorSeleccionado.getIdentificador()));

		params.put(TypeParametroVentana.MODO_ACCESO.toString(), TypeModoAcceso.CONSULTA.toString());
		UtilJSF.openDialog(DialogDominio.class, TypeModoAcceso.CONSULTA, params, true, 770, 400);

	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	public String getIdEntidadDominio(Long idEnt) {
		if (idEnt != null) {
			return entidadService.loadEntidad(idEnt).getIdentificador();
		}

		return null;
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
	public FuenteDatos getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final FuenteDatos data) {
		this.data = data;
	}

	/**
	 * @return the valorSeleccionado
	 */
	public Dominio getValorSeleccionado() {
		return valorSeleccionado;
	}

	/**
	 * @param valorSeleccionado the valorSeleccionado to set
	 */
	public void setValorSeleccionado(final Dominio valorSeleccionado) {
		this.valorSeleccionado = valorSeleccionado;
	}

	/**
	 * @return the campos
	 */
	public List<String> getListaDominios() {
		return listaDominios;
	}

	/**
	 * @param campos the campos to set
	 */
	public void setListaDominios(final List<String> listaDominios) {
		this.listaDominios = listaDominios;
	}

	/**
	 * @return the iCampos
	 */
	public String getiCampos() {
		return iCampos;
	}

	/**
	 * @param iCampos the iCampos to set
	 */
	public void setiCampos(final String iCampos) {
		this.iCampos = iCampos;
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
	public void setAmbito(final String ambito) {
		this.ambito = ambito;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(final String area) {
		this.area = area;
	}

	/**
	 * @return the campos
	 */
	public final List<Dominio> getCampos() {
		return campos;
	}

	/**
	 * @param campos the campos to set
	 */
	public final void setCampos(List<Dominio> campos) {
		this.campos = campos;
	}

}
