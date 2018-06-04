package es.caib.sistrages.frontend.controller;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import es.caib.sistrages.core.api.exception.FrontException;
import es.caib.sistrages.core.api.exception.FuenteDatosPkException;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.FuenteDatosCampo;
import es.caib.sistrages.core.api.model.FuenteDatosValores;
import es.caib.sistrages.core.api.model.FuenteFila;
import es.caib.sistrages.core.api.model.comun.CsvDocumento;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.util.CsvUtil;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeCampoFichero;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogFuenteDatos extends DialogControllerBase {

	/** Id elemento a tratar. */
	private String id;

	/** Enlace servicio. */
	@Inject
	private DominioService dominioService;

	/** Datos elemento. */
	private FuenteDatosValores data;

	/** Campos. **/
	private List<FuenteDatosCampo> campos;

	/** Campos en formato JSON. **/
	private String iCampos;

	/** Fila seleccionada. **/
	private FuenteFila valorSeleccionado;

	/**
	 * Inicialización.
	 */
	public void init() {
		final FuenteDatos fuente = dominioService.loadFuenteDato(Long.valueOf(id));
		campos = fuente.getCampos();
		if (campos == null || campos.isEmpty()) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogFuenteDatos.faltanCampos"));
		} else {
			refreshTabla();
		}

	}

	/**
	 * Cargar valores de BBDD
	 */
	private void refreshTabla() {
		data = dominioService.loadFuenteDatoValores(Long.valueOf(id));

	}

	/**
	 * Retorno dialogo de los botones de FuenteDatosCampoes.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		String message = null;

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
				// Refrescamos datos
				final FuenteFila fuenteFilaAlta = (FuenteFila) respuesta.getResult();
				dominioService.addFuenteDatoFila(fuenteFilaAlta, new Long(id));

				// Mensaje
				message = UtilJSF.getLiteral("info.alta.ok");

				refreshTabla();
				break;

			case EDICION:
				// Actualizamos fila actual
				final FuenteFila fuenteFilaEdicion = (FuenteFila) respuesta.getResult();

				try {
					dominioService.updateFuenteDatoFila(fuenteFilaEdicion);
					// Mensaje
					message = UtilJSF.getLiteral("info.modificado.ok");
				} catch (final Exception ex) {
					if (ex.getCause() instanceof FuenteDatosPkException) {
						UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
								UtilJSF.getLiteral("info.importarCSV.error.pk"));
						return;
					}
				}

				refreshTabla();

				break;
			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}

		// Mostramos mensaje
		if (message != null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
		}
	}

	/**
	 * Retorno dialogo del upload.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoUpload(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (respuesta != null && !respuesta.isCanceled()) {
			final String message = UtilJSF.getLiteral("info.importarCSV.ok");
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
			refreshTabla();
		}

	}

	/**
	 * Crea nueva FuenteDatosCampo.
	 */
	public void nuevaFuenteDato() {
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(this.getCampos()));
		// params.put(TypeParametroVentana.DATO2.toString(),
		// UtilJSON.toJSON(this.getData().getCodigo()));
		params.put(TypeParametroVentana.DATO2.toString(), id);
		UtilJSF.openDialog(DialogFuenteFila.class, TypeModoAcceso.ALTA, params, true, 460,
				calcularY(this.getCampos().size()));
	}

	/**
	 * Edita una FuenteDatosCampo.
	 */
	public void editarFuenteDato() {

		if (!verificarFilaSeleccionada())
			return;

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), UtilJSON.toJSON(this.valorSeleccionado.getId()));
		params.put(TypeParametroVentana.DATO2.toString(), id);
		UtilJSF.openDialog(DialogFuenteFila.class, TypeModoAcceso.EDICION, params, true, 460,
				calcularY(this.getCampos().size()));
	}

	/**
	 * Calcula el tamaño según el numero de elementos que tiene de campos.
	 *
	 * @param tamanyo
	 * @return
	 */
	private int calcularY(final int tamanyo) {
		return 70 + (tamanyo * 85);
	}

	/**
	 * Quita una FuenteDatosCampo.
	 */
	public void quitarFuenteDato() {
		if (!verificarFilaSeleccionada())
			return;

		dominioService.removeFuenteFila(this.valorSeleccionado.getId());
		this.refreshTabla();
	}

	/**
	 * Baja la FuenteDatosCampo de posición.
	 */
	public void bajarFuenteDato() {
		if (!verificarFilaSeleccionada())
			return;

		final int posicion = this.data.getFilas().indexOf(this.valorSeleccionado);
		if (posicion >= this.data.getFilas().size() - 1) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverabajo"));
			return;
		}

		final FuenteFila fuenteFila = this.data.getFilas().remove(posicion);
		this.data.getFilas().add(posicion + 1, fuenteFila);
	}

	/**
	 * Sube la FuenteDatosCampo de posición.
	 */
	public void subirFuenteDato() {
		if (!verificarFilaSeleccionada())
			return;

		final int posicion = this.data.getFilas().indexOf(this.valorSeleccionado);
		if (posicion <= 0) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverarriba"));
			return;
		}

		final FuenteFila fuenteFila = this.data.getFilas().remove(posicion);
		this.data.getFilas().add(posicion - 1, fuenteFila);
	}

	/**
	 * Llamamos al dialog de fichero para que se encarga de importar el csv.
	 */
	public void importarCSV() {
		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), id);
		params.put(TypeParametroVentana.CAMPO_FICHERO.toString(), TypeCampoFichero.FUENTE_ENTIDAD_CSV.toString());
		UtilJSF.openDialog(DialogFichero.class, TypeModoAcceso.EDICION, params, true, 450, 350);
	}

	/**
	 * Obtiene el fichero exportado.
	 *
	 * @return
	 */
	public StreamedContent exportarCSV() {
		StreamedContent file = null;
		try {

			final Long idFuenteDato = new Long(id);
			final FuenteDatos fuenteDatos = dominioService.loadFuenteDato(idFuenteDato);
			final FuenteDatosValores fd = dominioService.loadFuenteDatoValores(idFuenteDato);

			final CsvDocumento csv = CsvUtil.getCsvDocumento(fuenteDatos, fd);

			final byte[] contents = CsvUtil.exportar(csv);
			final ByteArrayInputStream bis = new ByteArrayInputStream(contents);
			file = new DefaultStreamedContent(bis, "csv", "Fichero.csv");

		} catch (final Exception ex) {

			throw new FrontException("No se puede generar el csv", ex);
		}

		return file;
	}

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

	/**
	 * Aceptar.
	 */
	public void aceptar() {

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
	public FuenteDatosValores getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final FuenteDatosValores data) {
		this.data = data;
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

	public boolean getPermiteEditar() {
		return (TypeModoAcceso.valueOf(modoAcceso) == TypeModoAcceso.ALTA
				|| TypeModoAcceso.valueOf(modoAcceso) == TypeModoAcceso.EDICION);
	}

	public boolean getPermiteConsulta() {
		return (TypeModoAcceso.valueOf(modoAcceso) == TypeModoAcceso.CONSULTA);
	}

}
