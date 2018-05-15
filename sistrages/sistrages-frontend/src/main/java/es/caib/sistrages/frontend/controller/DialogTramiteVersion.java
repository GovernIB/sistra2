package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramitePasoAnexar;
import es.caib.sistrages.core.api.model.TramitePasoDebeSaber;
import es.caib.sistrages.core.api.model.TramitePasoRegistrar;
import es.caib.sistrages.core.api.model.TramitePasoRellenar;
import es.caib.sistrages.core.api.model.TramitePasoTasa;
import es.caib.sistrages.core.api.model.TramiteTipo;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeFlujo;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

@ManagedBean
@ViewScoped
public class DialogTramiteVersion extends DialogControllerBase {

	/** Servicio. */
	@Inject
	private TramiteService tramiteService;

	/** Id elemento a tratar. */
	private String id;

	/** Datos elemento. */
	private Tramite data;

	/** Tramite version. **/
	private TramiteVersion dataVersion;

	/**
	 * Inicialización.
	 */
	public void init() {
		setData(tramiteService.getTramite(Long.valueOf(id)));
		dataVersion = new TramiteVersion();
		dataVersion.setNumeroVersion(1);
		dataVersion.setTipoFlujo(TypeFlujo.NORMAL);
		dataVersion.setActiva(true);
		dataVersion.setDebug(false);
		dataVersion.setAutenticado(true);
		dataVersion.setNoAutenticado(true);
		dataVersion.setIdiomasSoportados(UtilTraducciones.getIdiomasPorDefectoTramite());
		dataVersion.setPersistencia(false);
		dataVersion.setPersistenciaInfinita(false);
		dataVersion.setLimiteTramitacion(false);
		dataVersion.setDesactivacion(false);
		dataVersion.setRelease(1);
		dataVersion.setNivelQAA(2);

	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		if (dataVersion.getTipoFlujo() == TypeFlujo.PERSONALIZADO) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Tipo no implementado");
			return;
		} else {

			final List<TramitePaso> listaPasos = new ArrayList<>();
			/* iniciliza pasos tramite */
			for (final TramiteTipo tipo : this.tramiteService.listTipoTramitePaso()) {

				TramitePaso paso = null;
				if (tipo.getCodigo().equals("DebeSaber")) {
					paso = new TramitePasoDebeSaber();
				} else if (tipo.getCodigo().equals("Rellenar")) {
					paso = new TramitePasoRellenar();
				} else if (tipo.getCodigo().equals("Anexar")) {
					paso = new TramitePasoAnexar();
				} else if (tipo.getCodigo().equals("Tasa")) {
					paso = new TramitePasoTasa();
				} else if (tipo.getCodigo().equals("Registrar")) {
					paso = new TramitePasoRegistrar();
				} else {
					paso = new TramitePaso();
				}

				paso.setCodigo(tipo.getCodigo());
				paso.setDescripcion(tipo.getDescripcion());
				paso.setOrden(tipo.getOrden());
				paso.setTipo(tipo);

				listaPasos.add(paso);
			}

			this.dataVersion.setListaPasos(listaPasos);

		}
		this.tramiteService.addTramiteVersion(this.dataVersion, id);

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
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
	 * @return the data
	 */
	public Tramite getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final Tramite data) {
		this.data = data;
	}

	/**
	 * @return the dataVersion
	 */
	public TramiteVersion getDataVersion() {
		return dataVersion;
	}

	/**
	 * @param dataVersion
	 *            the dataVersion to set
	 */
	public void setDataVersion(final TramiteVersion dataVersion) {
		this.dataVersion = dataVersion;
	}

}
