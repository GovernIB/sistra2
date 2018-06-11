package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogBusquedaDominio extends DialogControllerBase {

	/**
	 * id tramite.
	 */
	private String idTramite;

	/** Datos elemento. */
	private List<Dominio> data;

	private Dominio valorSeleccionado;

	private String filtro;

	@Inject
	private DominioService dominioService;

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
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	public void filtrar() {
		data = dominioService.listDominio(Long.valueOf(idTramite), filtro);
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

}
