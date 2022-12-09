package es.caib.sistrages.frontend.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistra2.commons.plugins.registro.api.IRegistroPlugin;
import es.caib.sistra2.commons.plugins.registro.api.LibroOficina;
import es.caib.sistra2.commons.plugins.registro.api.OficinaRegistro;
import es.caib.sistra2.commons.plugins.registro.api.RegistroPluginException;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeRegistro;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.comun.FilaImportarTramiteRegistro;
import es.caib.sistrages.core.api.model.types.TypePlugin;
import es.caib.sistrages.core.api.service.ComponenteService;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
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
	private FilaImportarTramiteRegistro data;

	/** Mensaje. **/
	private String mensaje;

	/** Para el paso de registro. **/
	private String oficina;

	/** Para el paso de registro. **/
	private String libro;

	/** Plugin de registro. **/
	private IRegistroPlugin iplugin;

	/** Oficinas. **/
	private List<OficinaRegistro> oficinas;

	/** Libros. **/
	private List<LibroOficina> libros;

	/** Entidad. **/
	private Entidad entidad;

	/** Mostra oficina (solo si en entidad no tiene el registro centralizado. **/
	private boolean mostrarOficina;

	private String portapapeles;

	/**
	 * Inicialización.
	 */
	public void init() {
		setData((FilaImportarTramiteRegistro) UtilJSF.getSessionBean().getMochilaDatos()
				.get(Constantes.CLAVE_MOCHILA_IMPORTAR));

		entidad = entidadService.loadEntidad(UtilJSF.getIdEntidad());
		mostrarOficina = !entidad.isRegistroCentralizado();
		cargarDatosRegistro();
	}

	/**
	 * Carga los datos de registro.
	 */
	private void cargarDatosRegistro() {
		iplugin = (IRegistroPlugin) componenteService.obtenerPluginEntidad(TypePlugin.REGISTRO, UtilJSF.getIdEntidad());
		try {

			if (mostrarOficina) {
				oficinas = iplugin.obtenerOficinasRegistro(entidad.getCodigoDIR3(), TypeRegistro.REGISTRO_ENTRADA);

				boolean encontradoOficina = false;
				if (this.data.getOficina() != null) {
					// Es muy marciano, pero por si el cod oficina registro no existe en el listado
					// de oficinas de la entidad, puede pasar
					for (final OficinaRegistro ofi : oficinas) {
						if (this.data.getOficina().equals(ofi.getCodigo())) {
							encontradoOficina = true;
							libros = iplugin.obtenerLibrosOficina(entidad.getCodigoDIR3(), this.data.getOficina(),
									TypeRegistro.REGISTRO_ENTRADA);
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
			UtilJSF.loggearErrorFront("Error obteniendo informacion de registro", e);
			addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("dialogTramiteImportarRegistro.registro.error"));
		}
	}

	/**
	 * Evento on change de oficina que debería de recalcular el libro.
	 *
	 * @param event
	 */
	public void onChangeOficina() {
		try {
			libros = iplugin.obtenerLibrosOficina(entidad.getCodigoDIR3(), this.data.getOficina(),
					TypeRegistro.REGISTRO_ENTRADA);
		} catch (final RegistroPluginException e) {
			UtilJSF.loggearErrorFront("Error obteniendo informacion de registro", e);
			addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("dialogTramiteImportarRegistro.registro.error"));
		}
	}

	/**
	 * Guardar.
	 */
	public void guardar() {

		UtilJSF.getSessionBean().limpiaMochilaDatos();

		if (mostrarOficina) {
			guardarOficina();
		} else {
			this.data.setOficinaText("");
			this.data.setOficina("");
			this.data.setLibroText("");
			this.data.setLibro("");
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
			if (oficinaRegistro.getCodigo() != null && oficinaRegistro.getCodigo().equals(this.data.getOficina())) {
				this.data.setOficinaText(oficinaRegistro.getNombre());
				break;
			}
		}
		for (final LibroOficina libroRegistro : this.libros) {
			if (libroRegistro.getCodigo() != null && libroRegistro.getCodigo().equals(this.data.getLibro())) {
				this.data.setLibroText(libroRegistro.getNombre());
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

	/**
	 * @return the data
	 */
	public FilaImportarTramiteRegistro getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final FilaImportarTramiteRegistro data) {
		this.data = data;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje the mensaje to set
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
	 * @param oficina the oficina to set
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
	 * @param libro the libro to set
	 */
	public void setLibro(final String libro) {
		this.libro = libro;
	}

	/**
	 * @return the oficinas
	 */
	public List<OficinaRegistro> getOficinas() {
		return oficinas;
	}

	/**
	 * @param oficinas the oficinas to set
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
	 * @param libros the libros to set
	 */
	public void setLibros(final List<LibroOficina> libros) {
		this.libros = libros;
	}

	/**
	 * @return the mostrarOficina
	 */
	public boolean isMostrarOficina() {
		return mostrarOficina;
	}

	/**
	 * @param mostrarOficina the mostrarOficina to set
	 */
	public void setMostrarOficina(final boolean mostrarOficina) {
		this.mostrarOficina = mostrarOficina;
	}

}
