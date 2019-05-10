package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistra2.commons.plugins.registro.api.IRegistroPlugin;
import es.caib.sistra2.commons.plugins.registro.api.LibroOficina;
import es.caib.sistra2.commons.plugins.registro.api.OficinaRegistro;
import es.caib.sistra2.commons.plugins.registro.api.RegistroPluginException;
import es.caib.sistra2.commons.plugins.registro.api.TipoAsunto;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeRegistro;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.comun.FilaImportarTramiteVersion;
import es.caib.sistrages.core.api.model.types.TypePlugin;
import es.caib.sistrages.core.api.service.ComponenteService;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTramiteImportarRegistro extends DialogControllerBase {

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DialogTramiteImportarRegistro.class);

	/** Componente service. */
	@Inject
	private ComponenteService componenteService;

	/** Entidad service. */
	@Inject
	private EntidadService entidadService;

	/** FilaImportar. */
	private FilaImportarTramiteVersion data;

	/** Mensaje. **/
	private String mensaje;

	/** Para el paso de registro. **/
	private String oficina;

	/** Para el paso de registro. **/
	private String libro;

	/** Para el paso de registro. **/
	private String tipo;

	/** Plugin de registro. **/
	private IRegistroPlugin iplugin;

	/** Oficinas. **/
	private List<OficinaRegistro> oficinas;

	/** Libros. **/
	private List<LibroOficina> libros;

	/** Tipos. **/
	private List<TipoAsunto> tipos;

	/** Entidad. **/
	private Entidad entidad;

	/** Mostra oficina (solo si en entidad no tiene el registro centralizado. **/
	private boolean mostrarOficina;

	/**
	 * Inicialización.
	 */
	public void init() {
		setData((FilaImportarTramiteVersion) UtilJSF.getSessionBean().getMochilaDatos()
				.get(Constantes.CLAVE_MOCHILA_IMPORTAR));

		entidad = entidadService.loadEntidad(UtilJSF.getIdEntidad());
		mostrarOficina = !entidad.isRegistroCentralizado();

		if (data.getTramiteVersionActual() == null) {

			setMensaje(UtilJSF.getLiteral("dialogTramiteImportarRegistro.estado.noexiste"));

		} else if (data.getTramiteVersionActual().getNumeroVersion() < data.getTramiteVersion().getNumeroVersion()) {

			setMensaje(UtilJSF.getLiteral("dialogTramiteImportarRegistro.estado.existemayor"));

		} else if (data.getTramiteVersionActual().getNumeroVersion() == data.getTramiteVersion().getNumeroVersion()) {

			setMensaje(UtilJSF.getLiteral("dialogTramiteImportarRegistro.estado.existeigual"));

		} else {

			setMensaje(UtilJSF.getLiteral("dialogTramiteImportarRegistro.estado.existemenor"));
		}

		cargarDatosRegistro();
	}

	/**
	 * Carga los datos de registro.
	 */
	private void cargarDatosRegistro() {
		iplugin = (IRegistroPlugin) componenteService.obtenerPluginEntidad(TypePlugin.REGISTRO, UtilJSF.getIdEntidad());
		try {

			tipos = iplugin.obtenerTiposAsunto(entidad.getCodigoDIR3());
			if (mostrarOficina) {
				oficinas = iplugin.obtenerOficinasRegistro(entidad.getCodigoDIR3(), TypeRegistro.REGISTRO_ENTRADA);

				boolean encontradoOficina = false;
				if (this.data.getTramiteVersionResultadoOficina() != null) {
					// Es muy marciano, pero por si el cod oficina registro no existe en el listado
					// de oficinas de la entidad, puede pasar
					for (final OficinaRegistro ofi : oficinas) {
						if (this.data.getTramiteVersionResultadoOficina().equals(ofi.getCodigo())) {
							encontradoOficina = true;
							libros = iplugin.obtenerLibrosOficina(entidad.getCodigoDIR3(),
									this.data.getTramiteVersionResultadoOficina(), TypeRegistro.REGISTRO_ENTRADA);
							break;
						}
					}

				}

				if (!encontradoOficina) {
					libros = iplugin.obtenerLibrosOficina(entidad.getCodigoDIR3(), oficinas.get(0).getCodigo(),
							TypeRegistro.REGISTRO_ENTRADA);
				}
			}

		} catch (final RegistroPluginException e) {
			LOGGER.error("Error obteniendo informacion de registro", e);
			addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("dialogTramiteImportarRegistro.registro.error"));
		}
	}

	/** Consultar. **/
	public void consultarTramiteVersion() {

		if (this.data.getTramiteVersionActual() != null) {
			// Muestra dialogo
			final Map<String, String> params = new HashMap<>();
			params.put(TypeParametroVentana.ID.toString(),
					String.valueOf(this.data.getTramiteVersionActual().getCodigo()));
			UtilJSF.openDialog(ViewDefinicionVersion.class, TypeModoAcceso.CONSULTA, params, true, 520, 160);
		}
	}

	/**
	 * Evento on change de oficina que debería de recalcular el libro.
	 *
	 * @param event
	 */
	public void onChangeOficina() {
		try {
			libros = iplugin.obtenerLibrosOficina(entidad.getCodigoDIR3(),
					this.data.getTramiteVersionResultadoOficina(), TypeRegistro.REGISTRO_ENTRADA);
		} catch (final RegistroPluginException e) {
			LOGGER.error("Error obteniendo informacion de registro", e);
			addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("dialogTramiteImportarRegistro.registro.error"));
		}
	}

	/**
	 * Guardar.
	 */
	public void guardar() {

		UtilJSF.getSessionBean().limpiaMochilaDatos();

		for (final TipoAsunto tipoRegistro : this.tipos) {
			if (tipoRegistro.getCodigo() != null
					&& tipoRegistro.getCodigo().equals(this.data.getTramiteVersionResultadoTipo())) {
				this.data.setTramiteVersionResultadoTipoText(tipoRegistro.getNombre());
				break;
			}
		}

		if (mostrarOficina) {
			guardarOficina();
		} else {
			this.data.setTramiteVersionResultadoOficinaText("");
			this.data.setTramiteVersionResultadoOficina("");
			this.data.setTramiteVersionResultadoLibroText("");
			this.data.setTramiteVersionResultadoLibro("");
		}

		UtilJSF.getSessionBean().getMochilaDatos().put(Constantes.CLAVE_MOCHILA_IMPORTAR, this.data);
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(false);
		result.setResult(data);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Guarda los datos de oficina (oficina registro y libro oficina)
	 */
	private void guardarOficina() {
		// Preparamos los textos (solo se utilizan de info en el xhtml)
		for (final OficinaRegistro oficinaRegistro : this.oficinas) {
			if (oficinaRegistro.getCodigo() != null
					&& oficinaRegistro.getCodigo().equals(this.data.getTramiteVersionResultadoOficina())) {
				this.data.setTramiteVersionResultadoOficinaText(oficinaRegistro.getNombre());
				break;
			}
		}
		for (final LibroOficina libroRegistro : this.libros) {
			if (libroRegistro.getCodigo() != null
					&& libroRegistro.getCodigo().equals(this.data.getTramiteVersionResultadoLibro())) {
				this.data.setTramiteVersionResultadoLibroText(libroRegistro.getNombre());
				break;
			}
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
	 * @return the data
	 */
	public FilaImportarTramiteVersion getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final FilaImportarTramiteVersion data) {
		this.data = data;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje
	 *            the mensaje to set
	 */
	public void setMensaje(final String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return the oficina
	 */
	public String getOficina() {
		return oficina;
	}

	/**
	 * @param oficina
	 *            the oficina to set
	 */
	public void setOficina(final String oficina) {
		this.oficina = oficina;
	}

	/**
	 * @return the libro
	 */
	public String getLibro() {
		return libro;
	}

	/**
	 * @param libro
	 *            the libro to set
	 */
	public void setLibro(final String libro) {
		this.libro = libro;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the oficinas
	 */
	public List<OficinaRegistro> getOficinas() {
		return oficinas;
	}

	/**
	 * @param oficinas
	 *            the oficinas to set
	 */
	public void setOficinas(final List<OficinaRegistro> oficinas) {
		this.oficinas = oficinas;
	}

	/**
	 * @return the libros
	 */
	public List<LibroOficina> getLibros() {
		return libros;
	}

	/**
	 * @param libros
	 *            the libros to set
	 */
	public void setLibros(final List<LibroOficina> libros) {
		this.libros = libros;
	}

	/**
	 * @return the tipos
	 */
	public List<TipoAsunto> getTipos() {
		return tipos;
	}

	/**
	 * @param tipos
	 *            the tipos to set
	 */
	public void setTipos(final List<TipoAsunto> tipos) {
		this.tipos = tipos;
	}

	/**
	 * @return the mostrarOficina
	 */
	public boolean isMostrarOficina() {
		return mostrarOficina;
	}

	/**
	 * @param mostrarOficina
	 *            the mostrarOficina to set
	 */
	public void setMostrarOficina(final boolean mostrarOficina) {
		this.mostrarOficina = mostrarOficina;
	}

}
