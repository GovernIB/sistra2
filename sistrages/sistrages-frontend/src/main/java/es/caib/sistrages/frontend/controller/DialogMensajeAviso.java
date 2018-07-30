package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.AvisoEntidad;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.types.TypeAvisoEntidad;
import es.caib.sistrages.core.api.service.AvisoEntidadService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

@ManagedBean
@ViewScoped
public class DialogMensajeAviso extends DialogControllerBase {

	/**
	 * Servicio.
	 */
	@Inject
	private AvisoEntidadService avisoEntidadService;

	/**
	 * Id elemento a tratar.
	 */
	private String id;

	/**
	 * El identificador del tramite.
	 */
	private String tramite;

	/**
	 * El identificador del tramite.
	 */
	private String tramiteVersion;

	/**
	 * Indica si viene del viewDialog o del DialogDefVersionControlAcceso. Si esta
	 * relleno y es DialogDef es el segundo sino el primero.
	 */
	private String dato;

	/**
	 * Id entidad.
	 */
	private Long idEntidad;

	/**
	 * Datos elemento.
	 */
	private AvisoEntidad data;

	/**
	 * literal para mostrar.
	 */
	private String literal;

	/** Tipos. **/
	private List<TypeAvisoEntidad> tipos;

	/**
	 * Es tipo tramite.
	 */
	private boolean tipoTramite;

	/** Disabled desactivado. Afecta a tipo y los tramites. **/
	private boolean disabledActivo = false;

	/**
	 * Inicializacion.
	 */
	public void init() {

		// Id entidad
		idEntidad = UtilJSF.getSessionBean().getEntidad().getCodigo();

		if (tramite == null) {
			tipoTramite = false;
			tipos = new LinkedList<>(Arrays.asList(TypeAvisoEntidad.values()));
			tipos.remove(TypeAvisoEntidad.TRAMITE_VERSION);
		} else {
			tipoTramite = true;
			tipos = new ArrayList<>();
			tipos.add(TypeAvisoEntidad.TRAMITE_VERSION);
		}

		// Modo acceso
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		UtilJSF.checkSecOpenDialog(modo, id);
		if (modo == TypeModoAcceso.ALTA) {
			data = new AvisoEntidad();
			// Si es tipo tramite version, ya tiene unos valores por defecto.
			if (tipoTramite) {
				data.setTipo(TypeAvisoEntidad.TRAMITE_VERSION);
				data.setListaSerializadaTramites(tramite + "-" + tramiteVersion);
			}
		} else {
			if (id != null) {
				data = avisoEntidadService.getAvisoEntidad(Long.valueOf(id));
				if (data != null && data.getMensaje() != null) {
					literal = data.getMensaje().getTraduccion(UtilJSF.getSessionBean().getLang());
				}
				if (data != null && data.getTipo() == TypeAvisoEntidad.TRAMITE_VERSION) {
					setDisabledActivo(true);
					tipos = new ArrayList<>();
					tipos.add(TypeAvisoEntidad.TRAMITE_VERSION);
				}
			}
			if (tramite != null) {
				data = avisoEntidadService.getAvisoEntidadByTramite(tramite + "-" + tramiteVersion);
			}
		}

	}

	/**
	 * Devuelve true si es de tipo tramite.
	 *
	 * @return
	 */
	public boolean isNotTipoTramite() {
		return !tipoTramite;
	}

	public boolean validasFechas() {
		boolean resultado = true;

		if (data.getFechaInicio() != null && data.getFechaFin() != null
				&& data.getFechaInicio().compareTo(data.getFechaFin()) > 0) {
			resultado = false;
		}
		return resultado;
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		if (!validasFechas()) {
			UtilJSF.showMessageDialog(TypeNivelGravedad.INFO, "ERROR",
					UtilJSF.getLiteral("dialogMensajeAviso.error.fechaInicialPosterior"));
			return;
		}

		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		switch (acceso) {
		case ALTA:
			avisoEntidadService.addAvisoEntidad(idEntidad, data);
			break;
		case EDICION:
			avisoEntidadService.updateAvisoEntidad(data);
			break;
		case CONSULTA:
			// No hay que hacer nada
			break;
		}
		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data.getCodigo());
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
	 * Retorno dialogo de los botones de propiedades.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
			final Literal literales = (Literal) respuesta.getResult();
			data.setMensaje(literales);
			literal = literales.getTraduccion(UtilJSF.getSessionBean().getLang());
		}
	}

	/**
	 * Editar descripcion
	 *
	 *
	 */
	public void editarMensaje() {
		final List<String> idiomas = UtilTraducciones.getIdiomasPorDefecto();
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.valueOf(modoAcceso), data.getMensaje(), idiomas, idiomas);
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
	public AvisoEntidad getData() {
		return data;
	}

	/**
	 * Establece el valor de data.
	 *
	 * @param data
	 *            el nuevo valor de data
	 */
	public void setData(final AvisoEntidad data) {
		this.data = data;
	}

	/**
	 * Obtiene el valor de literal.
	 *
	 * @return el valor de literal
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * Establece el valor de literal.
	 *
	 * @param literal
	 *            el nuevo valor de literal
	 */
	public void setLiteral(final String literal) {
		this.literal = literal;
	}

	/**
	 * @return the tipos
	 */
	public List<TypeAvisoEntidad> getTipos() {
		return tipos;
	}

	/**
	 * @param tipos
	 *            the tipos to set
	 */
	public void setTipos(final List<TypeAvisoEntidad> tipos) {
		this.tipos = tipos;
	}

	/**
	 * @return the tramite
	 */
	public String getTramite() {
		return tramite;
	}

	/**
	 * @param tramite
	 *            the tramite to set
	 */
	public void setTramite(final String tramite) {
		this.tramite = tramite;
	}

	/**
	 * @return the tramiteVersion
	 */
	public String getTramiteVersion() {
		return tramiteVersion;
	}

	/**
	 * @param tramiteVersion
	 *            the tramiteVersion to set
	 */
	public void setTramiteVersion(final String tramiteVersion) {
		this.tramiteVersion = tramiteVersion;
	}

	/**
	 * @return the dato
	 */
	public String getDato() {
		return dato;
	}

	/**
	 * @param dato
	 *            the dato to set
	 */
	public void setDato(final String dato) {
		this.dato = dato;
	}

	/**
	 * @return the disabledActivo
	 */
	public boolean isDisabledActivo() {
		return disabledActivo;
	}

	/**
	 * @param disabledActivo
	 *            the disabledActivo to set
	 */
	public void setDisabledActivo(final boolean disabledActivo) {
		this.disabledActivo = disabledActivo;
	}

}
