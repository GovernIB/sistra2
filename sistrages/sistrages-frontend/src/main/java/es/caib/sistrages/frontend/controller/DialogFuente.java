package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.exception.FuenteDatosPkException;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.FuenteDatosCampo;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogFuente extends DialogControllerBase {

	/** Enlace servicio. */
	@Inject
	private DominioService dominioService;

	/** Id elemento a tratar. */
	private String id;

	/** Id area. **/
	private String idArea;

	/** Ambito. **/
	private String ambito;

	/** Datos elemento. */
	private FuenteDatos data;

	/** Valor seleccionado. **/
	private FuenteDatosCampo valorSeleccionado;

	/** Id entidad. */
	private Long idEntidad;

	/**
	 * Inicialización.
	 */
	public void init() {

		// Id entidad
		idEntidad = UtilJSF.getIdEntidad();

		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		UtilJSF.checkSecOpenDialog(modo, id);
		if (modo == TypeModoAcceso.ALTA) {
			data = new FuenteDatos();
			data.setAmbito(TypeAmbito.fromString(ambito));
			data.setCampos(new ArrayList<>());
		} else {
			data = dominioService.loadFuenteDato(Long.valueOf(id));
		}
	}

	/**
	 * Retorno dialogo de los botones de FuenteDatosCampoes.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
				// Refrescamos datos
				final FuenteDatosCampo fuenteDatosCampo = (FuenteDatosCampo) respuesta.getResult();
				this.data.addCampo(fuenteDatosCampo);
				break;

			case EDICION:
				// Actualizamos fila actual
				final FuenteDatosCampo fuenteDatosCampoEdicion = (FuenteDatosCampo) respuesta.getResult();

				// Muestra dialogo
				final int posicion = this.data.getCampos().indexOf(this.valorSeleccionado);

				this.data.getCampos().remove(posicion);
				this.data.getCampos().add(posicion, fuenteDatosCampoEdicion);
				this.valorSeleccionado = fuenteDatosCampoEdicion;

				break;
			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}

	}

	/**
	 * Crea nueva FuenteDatosCampo.
	 */
	public void nuevaFuenteDatosCampo() {
		UtilJSF.openDialog(DialogFuenteCampo.class, TypeModoAcceso.ALTA, null, true, 410, 200);
	}

	/**
	 * Edita una FuenteDatosCampo.
	 */
	public void editarFuenteDatosCampo() {

		if (!verificarFilaSeleccionada())
			return;

		final Map<String, String> params = new HashMap<>();
		final FuenteDatosCampo campo = this.valorSeleccionado;
		params.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(campo));
		UtilJSF.openDialog(DialogFuenteCampo.class, TypeModoAcceso.EDICION, params, true, 410, 200);
	}

	/**
	 * Quita una FuenteDatosCampo.
	 */
	public void quitarFuenteDatosCampo() {
		if (!verificarFilaSeleccionada())
			return;

		this.data.removeCampo(this.valorSeleccionado);

	}

	/**
	 * Baja la FuenteDatosCampo de posición.
	 */
	public void bajarFuenteDatosCampo() {
		if (!verificarFilaSeleccionada())
			return;

		final int posicion = this.data.getCampos().indexOf(this.valorSeleccionado);
		if (posicion >= this.data.getCampos().size() - 1) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverabajo"));
			return;
		}

		final FuenteDatosCampo fuenteDatosCampo = this.data.getCampos().remove(posicion);
		this.data.getCampos().add(posicion + 1, fuenteDatosCampo);
	}

	/**
	 * Sube la FuenteDatosCampo de posición.
	 */
	public void subirFuenteDatosCampo() {
		if (!verificarFilaSeleccionada())
			return;

		final int posicion = this.data.getCampos().indexOf(this.valorSeleccionado);
		if (posicion <= 0) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverarriba"));
			return;
		}

		final FuenteDatosCampo fuenteDatosCampo = this.data.getCampos().remove(posicion);
		this.data.getCampos().add(posicion - 1, fuenteDatosCampo);
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		final Map<String, FuenteDatosCampo> codigoCampos = new HashMap<>();
		if (this.data.getCampos() != null) {
			int orden = 0;
			for (final FuenteDatosCampo campo : data.getCampos()) {
				campo.setOrden(orden);
				orden++;

				// Checkeamos si hay un campo repetido.
				if (codigoCampos.containsKey(campo.getCodigo())) {
					UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
							UtilJSF.getLiteral("dialogFuente.error.camposRepes"));
					return;
				} else {
					codigoCampos.put(campo.getCodigo(), campo);
				}

			}

		}

		// Verifica si hay un campo con codigo repetido
		final List<String> camposNombre = new ArrayList<>();
		if (this.data.getCampos() != null) {
			for (final FuenteDatosCampo fdc : this.data.getCampos()) {
				if (camposNombre.contains(fdc.getCodigo())) {
					UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("error.codigoRepetido"));
					return;
				}
				camposNombre.add(fdc.getCodigo());
			}
		}

		switch (acceso) {
		case ALTA:
			// Verifica unicidad codigo
			if (dominioService.loadFuenteDato(this.data.getIdentificador()) != null) {
				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("error.codigoRepetido"));
				return;
			}

			this.dominioService.addFuenteDato(this.data, Long.valueOf(idArea));
			break;
		case EDICION:
			// Verifica unicidad codigo
			final FuenteDatos d = dominioService.loadFuenteDato(this.data.getIdentificador());
			if (d != null && d.getCodigo().longValue() != this.data.getCodigo().longValue()) {
				UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("error.codigoRepetido"));
				return;
			}
			try {
				this.dominioService.updateFuenteDato(this.data);
			} catch (final Exception ex) {
				if (ex.getCause() instanceof FuenteDatosPkException) {
					UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.importarCSV.error.pk"));
					return;
				}
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

	public boolean getPermiteEditar() {
		return (TypeModoAcceso.valueOf(modoAcceso) == TypeModoAcceso.ALTA
				|| TypeModoAcceso.valueOf(modoAcceso) == TypeModoAcceso.EDICION);
	}

	public boolean getPermiteConsulta() {
		return (TypeModoAcceso.valueOf(modoAcceso) == TypeModoAcceso.CONSULTA);
	}

	// ------- FUNCIONES PRIVADAS ------------------------------
	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionada() {
		boolean filaSeleccionada = true;
		if (this.valorSeleccionado == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	// ------- GETTERS / SETTERS --------------------------------
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
	public FuenteDatos getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final FuenteDatos data) {
		this.data = data;
	}

	/**
	 * @return the valorSeleccionado
	 */
	public FuenteDatosCampo getValorSeleccionado() {
		return valorSeleccionado;
	}

	/**
	 * @param valorSeleccionado
	 *            the valorSeleccionado to set
	 */
	public void setValorSeleccionado(final FuenteDatosCampo valorSeleccionado) {
		this.valorSeleccionado = valorSeleccionado;
	}

	/**
	 * @return the idArea
	 */
	public String getIdArea() {
		return idArea;
	}

	/**
	 * @param idArea
	 *            the idArea to set
	 */
	public void setIdArea(final String idArea) {
		this.idArea = idArea;
	}

	/**
	 * @return the ambito
	 */
	public String getAmbito() {
		return ambito;
	}

	/**
	 * @param ambito
	 *            the ambito to set
	 */
	public void setAmbito(final String ambito) {
		this.ambito = ambito;
	}

}
