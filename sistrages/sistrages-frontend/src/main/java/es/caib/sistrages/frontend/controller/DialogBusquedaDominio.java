package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogBusquedaDominio extends DialogControllerBase {

	/**
	 * id tramite.
	 */
	private String idTramite;
	/** Tipo : Tramite o seccion **/
	private String tipo;
	/** Id seccion */
	private String idSeccion;

	/** Datos elemento. */
	private List<Dominio> data;

	private Dominio valorSeleccionado;

	private String filtro;

	@Inject
	private DominioService dominioService;

	private String portapapeles;

	/**
	 * Inicialización.
	 */
	public void init() {
		if (data == null) {
			data = new ArrayList<>();
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		if (!verificarFilaSeleccionadaValor()) {
			return;
		}

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(valorSeleccionado);
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
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionadaValor() {
		boolean filaSeleccionada = true;
		if (this.valorSeleccionado == null) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	public void filtrar() {
		if (tipo.equals(TypeParametroVentana.PARAMETRO_DISENYO_TRAMITE.toString())) {
			data = dominioService.listDominio(Long.valueOf(idTramite), filtro);
		} else {
			List<TypeAmbito> ambitos = new ArrayList<>();
			ambitos.add(TypeAmbito.ENTIDAD);
			ambitos.add(TypeAmbito.GLOBAL);
			data = dominioService.listDominio(ambitos, UtilJSF.getIdEntidad(), filtro);
		}
	}

	/**
	 * @return the data
	 */
	public List<Dominio> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final List<Dominio> data) {
		this.data = data;
	}

	public Dominio getValorSeleccionado() {
		return valorSeleccionado;
	}

	public void setValorSeleccionado(final Dominio valorSeleccionado) {
		this.valorSeleccionado = valorSeleccionado;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(final String filtro) {
		this.filtro = filtro;
	}

	public String getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(final String idTramite) {
		this.idTramite = idTramite;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the idSeccion
	 */
	public String getIdSeccion() {
		return idSeccion;
	}

	/**
	 * @param idSeccion the idSeccion to set
	 */
	public void setIdSeccion(String idSeccion) {
		this.idSeccion = idSeccion;
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
