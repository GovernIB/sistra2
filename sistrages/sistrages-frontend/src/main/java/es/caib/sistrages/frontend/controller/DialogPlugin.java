package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.exception.FrontException;
import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypePlugin;
import es.caib.sistrages.core.api.service.PluginService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogPlugin extends DialogControllerBase {

	/** Servicio. */
	@Inject
	private PluginService pluginService;

	/** Id elemento a tratar. */
	private String id;

	/** Data en formato JSON. */
	private String iData;

	/** Datos elemento. */
	private Plugin data;

	/** Ambito. */
	private String ambito;

	/** Propiedad seleccionada. */
	private Propiedad propiedadSeleccionada;

	/** Tipos de plugin. */
	private List<TypePlugin> tipos;

	/** Max long propiedades. */
	private static final int MAXLENGTH_PROPIEDADES = 4000;

	private TypePlugin tipoInicialEdicion;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		UtilJSF.checkSecOpenDialog(modo, id);
		tipos = new ArrayList<>();

		if (modo == TypeModoAcceso.ALTA) {
			data = new Plugin();
			data.setAmbito(TypeAmbito.fromString(ambito));
		} else {
			data = pluginService.getPlugin(new Long(id));

			tipoInicialEdicion = data.getTipo();
		}
		if (ambito == null) {
			ambito = TypeAmbito.GLOBAL.toString();
		}

		switch (TypeAmbito.fromString(ambito)) {
		case GLOBAL:
			// Tipos plugins
			tipos.add(TypePlugin.LOGIN);
			tipos.add(TypePlugin.EMAIL);
			tipos.add(TypePlugin.REPRESENTACION);
			tipos.add(TypePlugin.DOMINIO_REMOTO);
			tipos.add(TypePlugin.CONVERSION_PDF);
			// No permite plugins multiinstancia
			break;
		case ENTIDAD:
			// Tipos plugins
			tipos.add(TypePlugin.CATALOGO_PROCEDIMIENTOS);
			tipos.add(TypePlugin.FIRMA);
			tipos.add(TypePlugin.PAGOS);
			tipos.add(TypePlugin.FORMULARIOS_EXTERNOS);
			tipos.add(TypePlugin.REGISTRO);
			tipos.add(TypePlugin.VALIDACION_FIRMA_SERVIDOR);
			// Permite plugins multiinstancia (pagos)
			break;
		default:
			throw new FrontException("Tipo ambito no permitido: " + ambito);
		}

	}

	/**
	 * Retorno dialogo de los botones de propiedades.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
				// Refrescamos datos
				final Propiedad propiedad = (Propiedad) respuesta.getResult();

				boolean duplicado = false;

				for (final Propiedad prop : data.getPropiedades()) {
					if (prop.getCodigo().equals(propiedad.getCodigo())) {
						duplicado = true;
						break;
					}
				}

				if (duplicado) {
					addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.duplicated"));
				} else {
					this.data.getPropiedades().add(propiedad);
				}

				break;
			case EDICION:
				// Actualizamos fila actual
				final Propiedad propiedadEdicion = (Propiedad) respuesta.getResult();
				// Muestra dialogo
				final int posicion = this.data.getPropiedades().indexOf(this.propiedadSeleccionada);

				boolean duplicadoEdicion = false;

				for (final Propiedad prop : data.getPropiedades()) {
					if (prop.getCodigo().equals(propiedadEdicion.getCodigo())) {
						duplicadoEdicion = true;
						break;
					}
				}

				if (duplicadoEdicion && !propiedadSeleccionada.getCodigo().equals(propiedadEdicion.getCodigo())) {
					addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.duplicated"));
				} else {
					this.data.getPropiedades().remove(posicion);
					this.data.getPropiedades().add(posicion, propiedadEdicion);
					this.propiedadSeleccionada = propiedadEdicion;
				}
				break;
			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}
	}

	/**
	 * Crea nueva propiedad.
	 */
	public void nuevaPropiedad() {
		UtilJSF.openDialog(DialogPropiedad.class, TypeModoAcceso.ALTA, null, true, 405, 140);
	}

	/**
	 * Edita una propiedad.
	 */
	public void editarPropiedad() {

		if (!verificarFilaSeleccionada())
			return;

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(this.propiedadSeleccionada));

		UtilJSF.openDialog(DialogPropiedad.class, TypeModoAcceso.EDICION, params, true, 405, 120);
	}

	/**
	 * Quita una propiedad.
	 */
	public void quitarPropiedad() {
		if (!verificarFilaSeleccionada())
			return;

		this.data.getPropiedades().remove(this.propiedadSeleccionada);

	}

	/**
	 * Baja la propiedad de posición.
	 */
	public void bajarPropiedad() {
		if (!verificarFilaSeleccionada())
			return;

		final int posicion = this.data.getPropiedades().indexOf(this.propiedadSeleccionada);
		if (posicion >= this.data.getPropiedades().size() - 1) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverabajo"));
			return;
		}

		final Propiedad propiedad = this.data.getPropiedades().remove(posicion);
		this.data.getPropiedades().add(posicion + 1, propiedad);
	}

	/**
	 * Sube la propiedad de posición.
	 */
	public void subirPropiedad() {
		if (!verificarFilaSeleccionada())
			return;

		final int posicion = this.data.getPropiedades().indexOf(this.propiedadSeleccionada);
		if (posicion <= 0) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverarriba"));
			return;
		}

		final Propiedad propiedad = this.data.getPropiedades().remove(posicion);
		this.data.getPropiedades().add(posicion - 1, propiedad);
	}

	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionada() {
		boolean filaSeleccionada = true;

		if (this.propiedadSeleccionada == null) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		switch (acceso) {
		case ALTA:
			if (!verificarGuardar()) {
				return;
			}

			// comprobamos si ya existe para que no haya duplicados
			final List<Plugin> listaPlugin = pluginService.listPlugin(data.getAmbito(), UtilJSF.getIdEntidad(),
					data.getTipo());

			if (listaPlugin != null && !listaPlugin.isEmpty()) {
				addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("error.tipo.duplicado"));
				return;
			}

			pluginService.addPlugin(data, UtilJSF.getIdEntidad());
			break;
		case EDICION:
			if (!verificarGuardar()) {
				return;
			}
			// comprobamos si ya existe para que no haya duplicados
			final List<Plugin> listaPluginEdicion = pluginService.listPlugin(data.getAmbito(), UtilJSF.getIdEntidad(),
					data.getTipo());

			if (listaPluginEdicion != null && !listaPluginEdicion.isEmpty()
					&& !tipoInicialEdicion.equals(data.getTipo())) {
				addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("error.tipo.duplicado"));
				return;
			}

			pluginService.updatePlugin(data);
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

	/**
	 * Verificar precondiciones al guardar.
	 *
	 * @return true, si se cumplen las todas la condiciones
	 */
	private boolean verificarGuardar() {
		if (MAXLENGTH_PROPIEDADES
				- (data.getPropiedades() == null ? 0 : UtilJSON.toJSON(data.getPropiedades()).length()) < 0) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.propiedades.tamanyosuperado"));
			return false;
		}

		return true;
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
	public Plugin getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final Plugin data) {
		this.data = data;
	}

	/**
	 * @return the propiedadSeleccionada
	 */
	public Propiedad getPropiedadSeleccionada() {
		return propiedadSeleccionada;
	}

	/**
	 * @param propiedadSeleccionada the propiedadSeleccionada to set
	 */
	public void setPropiedadSeleccionada(final Propiedad propiedadSeleccionada) {
		this.propiedadSeleccionada = propiedadSeleccionada;
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
	 * @return the iData
	 */
	public String getiData() {
		return iData;
	}

	/**
	 * @param iData the iData to set
	 */
	public void setiData(final String iData) {
		this.iData = iData;
	}

	/**
	 * @return the tipos
	 */
	public List<TypePlugin> getTipos() {
		return tipos;
	}

	/**
	 * @param tipos the tipos to set
	 */
	public void setTipos(final List<TypePlugin> tipos) {
		this.tipos = tipos;
	}

	/** Ayuda. */
	public void ayuda() {
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.AMBITO.toString(), ambito);
		UtilJSF.openHelp("pluginDialog", params);
	}

}
