package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.AvisoEntidad;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeAvisoEntidad;
import es.caib.sistrages.core.api.service.AvisoEntidadService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.TramiteMensajeAviso;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

@ManagedBean
@ViewScoped
public class DialogMensajeAviso extends DialogControllerBase {

	/** Servicio. */
	@Inject
	private AvisoEntidadService avisoEntidadService;

	/** Servicio. */
	@Inject
	private TramiteService tramiteService;

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

	/** Tramite seleccionado. **/
	private String tramiteSeleccionado;

	/** Version seleccionado. **/
	private String versionSeleccionado;

	/** Tramites. **/
	private List<Tramite> tramites;

	/** Tramites. **/
	private List<TramiteVersion> versiones;

	/** Lista de tramites en el datagrid. **/
	private List<TramiteMensajeAviso> listaTramites;

	/** Lista de tramites en el datagrid. **/
	private TramiteMensajeAviso tramiteMensajeAvisoSeleccionado;

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
				data.setListaSerializadaTramites(tramite + "#" + tramiteVersion);
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
				data = avisoEntidadService.getAvisoEntidadByTramite(tramite + "#" + tramiteVersion);
			}
		}

		/** Cargamos el grid de trámites. **/
		tramites = tramiteService.listTramiteByEntidad(UtilJSF.getIdEntidad());
		listaTramites = new ArrayList<>();
		if (data != null && data.getListaSerializadaTramites() != null) {
			final String[] trams = data.getListaSerializadaTramites().split(";");
			if (trams.length > 0) {
				for (final String tram : trams) {
					final String tramCodigo = tram.split("#")[0];
					final String tramNumeroVersion = tram.split("#")[1];
					final String tramIdentificador = tramiteService
							.getIdentificadorByCodigoVersion(Long.valueOf(tramCodigo));
					listaTramites.add(new TramiteMensajeAviso(tramCodigo, tramIdentificador, tramNumeroVersion));
				}
				Collections.sort(listaTramites, new Comparator<TramiteMensajeAviso>() {
					@Override
					public int compare(final TramiteMensajeAviso tram1, final TramiteMensajeAviso tram2) {
						if (tram1.getTramite().equals(tram2.getTramite())) {
							return tram1.getVersionTramite().compareTo(tram2.getVersionTramite());
						} else {
							return tram2.getTramite().compareTo(tram1.getTramite());
						}
					}
				});
			}
		}

	}

	/**
	 * Añade un trámite a la lista
	 */
	public void anyadirTram() {
		if (tramiteSeleccionado == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
					UtilJSF.getLiteral("dialogMensajeAviso.error.seleccioneTramite"));
			return;
		}

		if (versionSeleccionado == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
					UtilJSF.getLiteral("dialogMensajeAviso.error.seleccioneVersion"));
			return;
		}

		String codigoVersion = null;
		for (final TramiteVersion version : versiones) {
			if (version.getNumeroVersion() == Integer.parseInt(versionSeleccionado)) {
				codigoVersion = version.getCodigo().toString();
				break;
			}
		}

		for (final TramiteMensajeAviso mensajeAviso : listaTramites) {
			if (mensajeAviso.contains(codigoVersion, versionSeleccionado)) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
						UtilJSF.getLiteral("dialogMensajeAviso.error.yaintroducido"));
				return;
			}
		}

		String tramIdentificador = null;
		for (final Tramite tramit : tramites) {
			if (tramit.getCodigo().compareTo(Long.valueOf(tramiteSeleccionado)) == 0) {
				tramIdentificador = tramit.getIdentificador();
				break;
			}
		}

		listaTramites.add(new TramiteMensajeAviso(codigoVersion, tramIdentificador, versionSeleccionado));
	}

	/**
	 * Comprueba si es del tipo correcto para mostrar la lista de trámites admitido
	 *
	 * @return
	 */
	public boolean mostrarListaTramites() {
		return data.getTipo() == TypeAvisoEntidad.TRAMITE_VERSION || data.getTipo() == TypeAvisoEntidad.LISTA;
	}

	/**
	 * Borra un trámite seleccinado.
	 *
	 * @param tram
	 *
	 */
	public void borrarTramite(final TramiteMensajeAviso tram) {
		listaTramites.remove(tram);
	}

	/**
	 * Actualiza el combo de versiones
	 */
	public void actualizarVersion() {
		if (tramiteSeleccionado == null || tramiteSeleccionado.isEmpty()) {
			versiones = new ArrayList<>();
		} else {
			for (final Tramite tramit : tramites) {
				if (tramit.getCodigo().compareTo(Long.valueOf(tramiteSeleccionado)) == 0) {
					versiones = tramiteService.listTramiteVersion(Long.valueOf(tramiteSeleccionado), null);
					break;
				}
			}
		}
	}

	/**
	 * Devuelve true si es de tipo tramite.
	 *
	 * @return
	 */
	public boolean isNotTipoTramite() {
		return data.getTipo() != TypeAvisoEntidad.TRAMITE_VERSION;
	}

	/**
	 * Comprueba si son válidas dos fechas.
	 *
	 * @return
	 */
	public boolean validasFechas() {
		boolean resultado = true;

		if (data.getFechaInicio() != null && data.getFechaFin() != null
				&& data.getFechaInicio().compareTo(data.getFechaFin()) > 0) {
			resultado = false;
		}
		return resultado;
	}

	/**
	 * Verificar precondiciones al guardar.
	 *
	 * @return true, si se cumplen las todas la condiciones
	 */
	private boolean verificarGuardar() {

		if (!validasFechas()) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
					UtilJSF.getLiteral("dialogMensajeAviso.error.fechaInicialPosterior"));
			return false;
		}

		if (data.getMensaje() == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
					UtilJSF.getLiteral("dialogMensajeAviso.error.descripcionvacia"));
			return false;
		}

		return true;
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		// verificamos precondiciones
		if (!verificarGuardar()) {
			return;
		}

		if (data.getTipo() == TypeAvisoEntidad.LISTA) {
			this.data.setListaSerializadaTramites(getListaSerializada());
		}

		if ((data.getTipo() == TypeAvisoEntidad.LISTA || data.getTipo() == TypeAvisoEntidad.TRAMITE_VERSION)
				&& data.getListaSerializadaTramites().isEmpty()) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
					UtilJSF.getLiteral("dialogMensajeAviso.error.tramitesvacios"));
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
	 * Obtiene la lista serializada a partir de la lista de trámites seleccionados
	 *
	 * @return
	 */
	private String getListaSerializada() {
		final StringBuilder tramitMensaje = new StringBuilder();
		for (final TramiteMensajeAviso tramit : this.listaTramites) {
			tramitMensaje.append(tramit.getTramite() + "#" + tramit.getVersionTramite() + ";");
		}
		String tramiteMensaje = tramitMensaje.toString();
		if (tramiteMensaje.endsWith(";")) {
			tramiteMensaje = tramiteMensaje.substring(0, tramiteMensaje.length() - 1);
		}
		return tramiteMensaje;
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

	/** Ayuda. */
	public void ayuda() {
		UtilJSF.openHelp("mensajeAvisoDialog");
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
		List<String> idiomas = UtilJSF.getSessionBean().getIdiomas();

		if (data.getTipo() == TypeAvisoEntidad.TRAMITE_VERSION) {
			for (final String fila : this.data.getListaSerializadaTramites().split(";")) {
				final String codigo = fila.split("#")[0];
				final TramiteVersion tramVersion = tramiteService.getTramiteVersion(Long.valueOf(codigo));
				idiomas = UtilTraducciones.getIdiomas(tramVersion.getIdiomasSoportados());
				break;
			}
		}
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

	/**
	 * @return the tramiteSeleccionado
	 */
	public String getTramiteSeleccionado() {
		return tramiteSeleccionado;
	}

	/**
	 * @param tramiteSeleccionado
	 *            the tramiteSeleccionado to set
	 */
	public void setTramiteSeleccionado(final String tramiteSeleccionado) {
		this.tramiteSeleccionado = tramiteSeleccionado;
	}

	/**
	 * @return the versionSeleccionado
	 */
	public String getVersionSeleccionado() {
		return versionSeleccionado;
	}

	/**
	 * @param versionSeleccionado
	 *            the versionSeleccionado to set
	 */
	public void setVersionSeleccionado(final String versionSeleccionado) {
		this.versionSeleccionado = versionSeleccionado;
	}

	/**
	 * @return the tramites
	 */
	public List<Tramite> getTramites() {
		return tramites;
	}

	/**
	 * @param tramites
	 *            the tramites to set
	 */
	public void setTramites(final List<Tramite> tramites) {
		this.tramites = tramites;
	}

	/**
	 * @return the versiones
	 */
	public List<TramiteVersion> getVersiones() {
		return versiones;
	}

	/**
	 * @param versiones
	 *            the versiones to set
	 */
	public void setVersiones(final List<TramiteVersion> versiones) {
		this.versiones = versiones;
	}

	/**
	 * @return the listaTramites
	 */
	public final List<TramiteMensajeAviso> getListaTramites() {
		return listaTramites;
	}

	/**
	 * @param listaTramites
	 *            the listaTramites to set
	 */
	public final void setListaTramites(final List<TramiteMensajeAviso> listaTramites) {
		this.listaTramites = listaTramites;
	}

	/**
	 * @return the tramiteMensajeAvisoSeleccionado
	 */
	public TramiteMensajeAviso getTramiteMensajeAvisoSeleccionado() {
		return tramiteMensajeAvisoSeleccionado;
	}

	/**
	 * @param tramiteMensajeAvisoSeleccionado
	 *            the tramiteMensajeAvisoSeleccionado to set
	 */
	public void setTramiteMensajeAvisoSeleccionado(final TramiteMensajeAviso tramiteMensajeAvisoSeleccionado) {
		this.tramiteMensajeAvisoSeleccionado = tramiteMensajeAvisoSeleccionado;
	}

}
