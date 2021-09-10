package es.caib.sistrages.frontend.controller;

import java.io.ByteArrayInputStream;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import es.caib.sistrages.core.api.exception.FuenteDatosCSVNoExisteCampoException;
import es.caib.sistrages.core.api.exception.FuenteDatosPkException;
import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.PlantillaEntidad;
import es.caib.sistrages.core.api.model.PlantillaFormateador;
import es.caib.sistrages.core.api.model.PlantillaIdiomaFormulario;
import es.caib.sistrages.core.api.model.comun.CsvDocumento;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.core.api.service.FormularioInternoService;
import es.caib.sistrages.core.api.service.TramiteService;
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

	/**
	 * Servicio tramite.
	 */
	@Inject
	private TramiteService tramiteService;

	@Inject
	FormularioInternoService formIntService;

	/** Id **/
	private String id;

	/** Id **/
	private String idEntidad;

	/** campo fichero. */
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
	 * Documento.
	 */
	private Documento documento;

	/**
	 * existe fichero.
	 */
	private boolean existeFichero;

	/**
	 * Visualiza una fila si para subir un fichero al tipo entidad.
	 */
	private boolean mostrarQuitar;

	/**
	 * nombre fichero.
	 */
	private String nombreFichero;

	/**
	 * extensiones permitidas.
	 */
	private String extensiones;

	private PlantillaIdiomaFormulario plantillaIdiomaFormulario;

	private PlantillaFormateador plantillaFormateador;

	private PlantillaEntidad plantillaEntidad;

	private Long idPlantillaFormulario;

	private Long idPlantillaFormateador;

	private Long idPlantillaEntidad;

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
			mostrarQuitar = true;
			break;
		case FUENTE_ENTIDAD_CSV:
			mostrarQuitar = false;
			break;
		case TRAMITE_DOC:
			documento = tramiteService.getDocumento(Long.valueOf(id));
			entidad = entidadService.loadEntidad(Long.valueOf(idEntidad));
			if (documento == null || documento.getAyudaFichero() == null) {
				mostrarQuitar = false;
			} else {
				mostrarQuitar = true;
			}
			break;
		case PLANTILLA_IDIOMA_FORM:
			final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
			if (!mochilaDatos.isEmpty()) {
				plantillaIdiomaFormulario = (PlantillaIdiomaFormulario) mochilaDatos
						.get(Constantes.CLAVE_MOCHILA_PLTIDIOMAFORM);
			}
			idPlantillaFormulario = Long.valueOf(id);
			mostrarQuitar = true;
			break;
		case PLANTILLA_FORMATEADOR:
			final Map<String, Object> mochilaDatos2 = UtilJSF.getSessionBean().getMochilaDatos();
			if (!mochilaDatos2.isEmpty()) {
				plantillaFormateador = (PlantillaFormateador) mochilaDatos2
						.get(Constantes.CLAVE_MOCHILA_PLANTILLA_FORMATEADOR);
			}
			idPlantillaFormateador = Long.valueOf(id);
			mostrarQuitar = true;
			break;
		case PLANTILLA_ENTIDAD:
			final Map<String, Object> mochilaDatos3 = UtilJSF.getSessionBean().getMochilaDatos();
			if (!mochilaDatos3.isEmpty()) {
				plantillaEntidad = (PlantillaEntidad) mochilaDatos3
						.get(Constantes.CLAVE_MOCHILA_PLANTILLA_ENTIDAD);
				if (plantillaEntidad != null) {
					idPlantillaEntidad = plantillaEntidad.getCodigo();
				}
			}
			mostrarQuitar = true;
			break;
		default:
			mostrarQuitar = true;
			break;
		}

		comprobarFichero();

		asignarExtensiones();
	}

	/**
	 * carga de fichero.
	 *
	 * @param event el evento
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

				entidadService.uploadLogoGestorEntidad(entidad.getCodigo(), fichero, file.getContents());
				break;
			case LOGO_ASISTENTE_ENTIDAD:
				fichero = entidad.getLogoAsistente();
				if (fichero == null) {
					fichero = new Fichero();
					fichero.setPublico(true);
				}
				fichero.setNombre(file.getFileName());

				entidadService.uploadLogoAsistenteEntidad(entidad.getCodigo(), fichero, file.getContents());
				break;
			case CSS_ENTIDAD:
				fichero = entidad.getCss();
				if (fichero == null) {
					fichero = new Fichero();
					fichero.setPublico(true);
				}
				fichero.setNombre(file.getFileName());

				entidadService.uploadCssEntidad(entidad.getCodigo(), fichero, file.getContents());
				break;
			case FUENTE_ENTIDAD_CSV:
				final byte csvContent[] = file.getContents();
				final ByteArrayInputStream bis = new ByteArrayInputStream(csvContent);
				try {
					final CsvDocumento csv = CsvUtil.importar(bis);
					dominioService.importarCSV(Long.valueOf(id), csv);
				} catch (final Exception ex) {
					if (ex.getCause() instanceof FuenteDatosPkException) {
						addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.importarCSV.error.pk"));
						return;
					}
					if (ex.getCause() instanceof FuenteDatosCSVNoExisteCampoException) {
						addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.importarCSV.error.campo"));
						return;
					}
				}

				break;
			case TRAMITE_DOC:
				fichero = documento.getAyudaFichero();
				if (fichero == null) {
					fichero = new Fichero();
					fichero.setPublico(true);
				}
				fichero.setNombre(file.getFileName());

				tramiteService.uploadDocAnexo(documento.getCodigo(), fichero, file.getContents(),
						Long.valueOf(idEntidad));
				break;
			case PLANTILLA_IDIOMA_FORM:
				fichero = plantillaIdiomaFormulario.getFichero();
				if (fichero == null) {
					fichero = new Fichero();
					fichero.setPublico(true);
					plantillaIdiomaFormulario.setFichero(fichero);
				}
				fichero.setNombre(file.getFileName());

				plantillaIdiomaFormulario = formIntService.uploadPlantillaIdiomaFormulario(UtilJSF.getIdEntidad(),
						idPlantillaFormulario, plantillaIdiomaFormulario, file.getContents());
				break;
			case PLANTILLA_FORMATEADOR:
				fichero = plantillaFormateador.getFichero();
				if (fichero == null) {
					fichero = new Fichero();
					fichero.setPublico(false);
					plantillaFormateador.setFichero(fichero);
				}
				fichero.setNombre(file.getFileName());

				plantillaFormateador = formIntService.uploadPlantillaFormateador(UtilJSF.getIdEntidad(),
						idPlantillaFormateador, plantillaFormateador, file.getContents());
				break;
			case PLANTILLA_ENTIDAD:
				fichero = plantillaEntidad.getFichero();
				if (fichero == null) {
					fichero = new Fichero();
					fichero.setPublico(false);
					plantillaEntidad.setFichero(fichero);
				}
				fichero.setNombre(file.getFileName());

				plantillaEntidad = entidadService.uploadPlantillasEmailFin(UtilJSF.getIdEntidad(),
						idPlantillaEntidad, plantillaEntidad, file.getContents());
				break;
			default:
				break;
			}

			if (tipoCampoFichero == TypeCampoFichero.FUENTE_ENTIDAD_CSV) {
				this.cerrarCsv();
			} else if (tipoCampoFichero == TypeCampoFichero.TRAMITE_DOC) {
				addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.fichero.anexar"));
			} else if (tipoCampoFichero == TypeCampoFichero.PLANTILLA_IDIOMA_FORM
					|| tipoCampoFichero == TypeCampoFichero.PLANTILLA_FORMATEADOR
					|| tipoCampoFichero == TypeCampoFichero.PLANTILLA_ENTIDAD) {
				comprobarFichero();
				addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.fichero.anexar"));
			} else {
				entidad = entidadService.loadEntidad(entidad.getCodigo());
				comprobarFichero();

				addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.fichero.anexar"));
			}

		} else {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofitxer"));
		}
	}

	/**
	 * Eliminar fichero.
	 */
	public void eliminar() {
		switch (tipoCampoFichero) {
		case LOGO_GESTOR_ENTIDAD:
			entidadService.removeLogoGestorEntidad(entidad.getCodigo());
			entidad = entidadService.loadEntidad(entidad.getCodigo());
			break;
		case LOGO_ASISTENTE_ENTIDAD:
			entidadService.removeLogoAsistenteEntidad(entidad.getCodigo());
			entidad = entidadService.loadEntidad(entidad.getCodigo());
			break;
		case CSS_ENTIDAD:
			entidadService.removeCssEntidad(entidad.getCodigo());
			entidad = entidadService.loadEntidad(entidad.getCodigo());
			break;
		case TRAMITE_DOC:
			tramiteService.removeDocAnexo(documento.getCodigo());
			documento = tramiteService.getDocumento(Long.valueOf(id));
			break;
		case FUENTE_ENTIDAD_CSV:
		case PLANTILLA_IDIOMA_FORM:
			formIntService.removePlantillaIdiomaFormulario(plantillaIdiomaFormulario);
			plantillaIdiomaFormulario.setCodigo(null);
			plantillaIdiomaFormulario.setFichero(null);
			break;
		case PLANTILLA_FORMATEADOR:
			formIntService.removePlantillaFormateador(plantillaFormateador);
			plantillaFormateador = null;
			this.cerrar();
			break;
		case PLANTILLA_ENTIDAD:
			entidadService.removePlantillaEmailFin(plantillaEntidad);
			plantillaEntidad = null;
			this.cerrar();
			break;
		default:
			break;
		}

		comprobarFichero();
		addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.fichero.quitar"));
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
		case PLANTILLA_IDIOMA_FORM:
			existeFichero = plantillaIdiomaFormulario != null && plantillaIdiomaFormulario.getFichero() != null;
			if (existeFichero) {
				nombreFichero = plantillaIdiomaFormulario.getFichero().getNombre();
			} else {
				nombreFichero = null;
			}
			break;
		case PLANTILLA_FORMATEADOR:
			existeFichero = plantillaFormateador != null && plantillaFormateador.getFichero() != null;
			if (existeFichero) {
				nombreFichero = plantillaFormateador.getFichero().getNombre();
			} else {
				nombreFichero = null;
			}
			break;
		case TRAMITE_DOC:
			existeFichero = documento != null && documento.getAyudaFichero() != null;
			if (existeFichero) {
				nombreFichero = documento.getAyudaFichero().getNombre();
			} else {
				nombreFichero = null;
			}
			break;
		case PLANTILLA_ENTIDAD:
			existeFichero = plantillaEntidad != null && plantillaEntidad.getFichero() != null;
			if (existeFichero) {
				nombreFichero = plantillaEntidad.getFichero().getNombre();
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
		case PLANTILLA_ENTIDAD:
			extensiones = Constantes.EXTENSIONES_FICHEROS_HTML;
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
	 * @param id el nuevo valor de id
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
	 * @param campoFichero el nuevo valor de campoFichero
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
	 * @param tipoCampoFichero el nuevo valor de tipoCampoFichero
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
	 * @param data el nuevo valor de data
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
	 * @param existeFichero el nuevo valor de existeFichero
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
	 * @param extensiones el nuevo valor de extensiones
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
	 * @param nombreFichero el nuevo valor de nombreFichero
	 */
	public void setNombreFichero(final String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	/**
	 * Verifica si es mostrar quitar.
	 *
	 * @return true, si es mostrar quitar
	 */
	public boolean isMostrarQuitar() {
		return mostrarQuitar;
	}

	/**
	 * Establece el valor de mostrarQuitar.
	 *
	 * @param tipoEntidad el nuevo valor de mostrarQuitar
	 */
	public void setMostrarQuitar(final boolean tipoEntidad) {
		this.mostrarQuitar = tipoEntidad;
	}

	/**
	 * @return the idEntidad
	 */
	public String getIdEntidad() {
		return idEntidad;
	}

	/**
	 * @param idEntidad the idEntidad to set
	 */
	public void setIdEntidad(final String idEntidad) {
		this.idEntidad = idEntidad;
	}

	public PlantillaIdiomaFormulario getPlantillaIdiomaFormulario() {
		return plantillaIdiomaFormulario;
	}

	public void setPlantillaIdiomaFormulario(final PlantillaIdiomaFormulario plantillaIdiomaFormulario) {
		this.plantillaIdiomaFormulario = plantillaIdiomaFormulario;
	}

	public Long getIdPlantillaFormulario() {
		return idPlantillaFormulario;
	}

	public void setIdPlantillaFormulario(final Long idPlantillaFormulario) {
		this.idPlantillaFormulario = idPlantillaFormulario;
	}

}
