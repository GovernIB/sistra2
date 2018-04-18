package es.caib.sistrages.frontend.controller;

import java.io.ByteArrayInputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import es.caib.sistrages.core.api.exception.CSVNoExisteCampoException;
import es.caib.sistrages.core.api.exception.CSVPkException;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.comun.CsvDocumento;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.core.api.util.CsvUtil;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeCampoFichero;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Dialogo fichero.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class DialogFichero extends DialogControllerBase {

	/** Enlace servicio. */
	@Inject
	private DominioService dominioService;

	/**
	 * Servicio entidad.
	 */
	@Inject
	private EntidadService entidadService;

	/** Id **/
	private String id;

	/**
	 * campo fichero.
	 */
	private String campoFichero;

	/**
	 * tipo campo fichero.
	 */
	private TypeCampoFichero tipoCampoFichero;

	/**
	 * entidad.
	 */
	private Entidad entidad;

	/**
	 * existe fichero.
	 */
	private boolean existeFichero;

	/**
	 * Visualiza una fila si para subir un fichero al tipo entidad.
	 */
	private boolean tipoEntidad;

	/**
	 * nombre fichero.
	 */
	private String nombreFichero;

	/**
	 * extensiones permitidas.
	 */
	private String extensiones;

	/**
	 * Inicialización.
	 *
	 */
	public void init() {
		tipoCampoFichero = TypeCampoFichero.valueOf(campoFichero);

		switch (tipoCampoFichero) {
		case LOGO_GESTOR_ENTIDAD:
		case LOGO_ASISTENTE_ENTIDAD:
		case CSS_ENTIDAD:

			if (id == null) {
				entidad = new Entidad();
			} else {
				entidad = entidadService.loadEntidad(Long.valueOf(id));
			}
			tipoEntidad = true;
			break;
		case FUENTE_ENTIDAD_CSV:
			tipoEntidad = false;
			break;
		default:
			break;
		}

		comprobarFichero();

		asignarExtensiones();
	}

	/**
	 * carga de fichero.
	 *
	 * @param event
	 *            el evento
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	public void upload(final FileUploadEvent event) throws Exception {
		if (event != null && event.getFile() != null) {
			final UploadedFile file = event.getFile();
			Fichero fichero = null;
			switch (tipoCampoFichero) {
			case LOGO_GESTOR_ENTIDAD:
				fichero = entidad.getLogoGestor();
				if (fichero == null) {
					fichero = new Fichero();
					fichero.setPublico(false);
				}
				fichero.setNombre(file.getFileName());

				entidadService.uploadLogoGestorEntidad(entidad.getId(), fichero, file.getContents());
				break;
			case LOGO_ASISTENTE_ENTIDAD:
				fichero = entidad.getLogoAsistente();
				if (fichero == null) {
					fichero = new Fichero();
					fichero.setPublico(true);
				}
				fichero.setNombre(file.getFileName());

				entidadService.uploadLogoAsistenteEntidad(entidad.getId(), fichero, file.getContents());
				break;
			case CSS_ENTIDAD:
				fichero = entidad.getCss();
				if (fichero == null) {
					fichero = new Fichero();
					fichero.setPublico(true);
				}
				fichero.setNombre(file.getFileName());

				entidadService.uploadCssEntidad(entidad.getId(), fichero, file.getContents());
				break;
			case FUENTE_ENTIDAD_CSV:
				final byte csvContent[] = file.getContents();
				final ByteArrayInputStream bis = new ByteArrayInputStream(csvContent);
				try {
					final CsvDocumento csv = CsvUtil.importar(bis);
					dominioService.importarCSV(Long.valueOf(id), csv);
				} catch (final CSVPkException exception) {
					UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.importarCSV.error.pk"));
					return;
				} catch (final CSVNoExisteCampoException exception) {
					UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
							UtilJSF.getLiteral("info.importarCSV.error.campo"));
					return;
				}

				break;
			default:
				break;
			}

			if (tipoCampoFichero == TypeCampoFichero.FUENTE_ENTIDAD_CSV) {
				this.cerrarCsv();
			} else {
				entidad = entidadService.loadEntidad(entidad.getId());
				comprobarFichero();

				UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.fichero.anexar"));
			}

		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofitxer"));
		}
	}

	/**
	 * Eliminar fichero.
	 */
	public void eliminar() {
		switch (tipoCampoFichero) {
		case LOGO_GESTOR_ENTIDAD:
			entidadService.removeLogoGestorEntidad(entidad.getId());
			break;
		case LOGO_ASISTENTE_ENTIDAD:
			entidadService.removeLogoAsistenteEntidad(entidad.getId());
			break;
		case CSS_ENTIDAD:
			entidadService.removeCssEntidad(entidad.getId());
			break;
		default:
			break;
		}

		entidad = entidadService.loadEntidad(entidad.getId());
		comprobarFichero();
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.fichero.quitar"));
	}

	/**
	 * Comprobar si existe el fichero y recupera el nombre.
	 */
	public void comprobarFichero() {
		switch (tipoCampoFichero) {
		case LOGO_GESTOR_ENTIDAD:
			existeFichero = entidad.getLogoGestor() != null;
			if (existeFichero) {
				nombreFichero = entidad.getLogoGestor().getNombre();
			} else {
				nombreFichero = null;
			}
			break;
		case LOGO_ASISTENTE_ENTIDAD:
			existeFichero = entidad.getLogoAsistente() != null;
			if (existeFichero) {
				nombreFichero = entidad.getLogoAsistente().getNombre();
			} else {
				nombreFichero = null;
			}
			break;
		case CSS_ENTIDAD:
			existeFichero = entidad.getCss() != null;
			if (existeFichero) {
				nombreFichero = entidad.getCss().getNombre();
			} else {
				nombreFichero = null;
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Recupera las extensiones segun el fichero.
	 */
	public void asignarExtensiones() {
		switch (tipoCampoFichero) {
		case LOGO_GESTOR_ENTIDAD:
			extensiones = Constantes.EXTENSIONES_FICHEROS_LOGO;
			break;
		case LOGO_ASISTENTE_ENTIDAD:
			extensiones = Constantes.EXTENSIONES_FICHEROS_LOGO;
			break;
		case CSS_ENTIDAD:
			extensiones = Constantes.EXTENSIONES_FICHEROS_CSS;
			break;
		case FUENTE_ENTIDAD_CSV:
			extensiones = Constantes.EXTENSIONES_FICHEROS_CSV;
			break;
		default:
			break;
		}
	}

	/**
	 * Cerrar.
	 */
	public void cerrar() {
		UtilJSF.closeDialog(null);
	}

	/**
	 * Cerrar.
	 */
	public void cerrarCsv() {
		final DialogResult dialog = new DialogResult();
		dialog.setCanceled(false);
		if (modoAcceso != null) {
			dialog.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		}
		UtilJSF.closeDialog(dialog);
	}

	/*** GETTER / SETTERS ***/

	/**
	 * Obtiene el valor de id.
	 *
	 * @return el valor de id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Establece el valor de id.
	 *
	 * @param id
	 *            el nuevo valor de id
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * Obtiene el valor de campoFichero.
	 *
	 * @return el valor de campoFichero
	 */
	public String getCampoFichero() {
		return campoFichero;
	}

	/**
	 * Establece el valor de campoFichero.
	 *
	 * @param campoFichero
	 *            el nuevo valor de campoFichero
	 */
	public void setCampoFichero(final String campoFichero) {
		this.campoFichero = campoFichero;
	}

	/**
	 * Obtiene el valor de tipoCampoFichero.
	 *
	 * @return el valor de tipoCampoFichero
	 */
	public TypeCampoFichero getTipoCampoFichero() {
		return tipoCampoFichero;
	}

	/**
	 * Establece el valor de tipoCampoFichero.
	 *
	 * @param tipoCampoFichero
	 *            el nuevo valor de tipoCampoFichero
	 */
	public void setTipoCampoFichero(final TypeCampoFichero tipoCampoFichero) {
		this.tipoCampoFichero = tipoCampoFichero;
	}

	/**
	 * Obtiene el valor de data.
	 *
	 * @return el valor de data
	 */
	public Entidad getData() {
		return entidad;
	}

	/**
	 * Establece el valor de data.
	 *
	 * @param data
	 *            el nuevo valor de data
	 */
	public void setData(final Entidad data) {
		this.entidad = data;
	}

	/**
	 * Verifica si es existe fichero.
	 *
	 * @return true, si es existe fichero
	 */
	public boolean isExisteFichero() {
		return existeFichero;
	}

	/**
	 * Establece el valor de existeFichero.
	 *
	 * @param existeFichero
	 *            el nuevo valor de existeFichero
	 */
	public void setExisteFichero(final boolean existeFichero) {
		this.existeFichero = existeFichero;
	}

	/**
	 * Obtiene el valor de extensiones.
	 *
	 * @return el valor de extensiones
	 */
	public String getExtensiones() {
		return extensiones;
	}

	/**
	 * Establece el valor de extensiones.
	 *
	 * @param extensiones
	 *            el nuevo valor de extensiones
	 */
	public void setExtensiones(final String extensiones) {
		this.extensiones = extensiones;
	}

	/**
	 * Obtiene el valor de nombreFichero.
	 *
	 * @return el valor de nombreFichero
	 */
	public String getNombreFichero() {
		return nombreFichero;
	}

	/**
	 * Establece el valor de nombreFichero.
	 *
	 * @param nombreFichero
	 *            el nuevo valor de nombreFichero
	 */
	public void setNombreFichero(final String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	/**
	 * @return the tipoEntidad
	 */
	public boolean isTipoEntidad() {
		return tipoEntidad;
	}

	/**
	 * @param tipoEntidad
	 *            the tipoEntidad to set
	 */
	public void setTipoEntidad(final boolean tipoEntidad) {
		this.tipoEntidad = tipoEntidad;
	}

}
