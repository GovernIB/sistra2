package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.FormularioSoporte;
import es.caib.sistrages.core.api.model.types.TypeIdioma;

/**
 * Entidad service.
 *
 * @author Indra.
 *
 */
public interface EntidadService {

	/**
	 * Obtener entidad.
	 *
	 * @param idEntidad
	 *            idEntidad
	 * @return entidad
	 */
	public Entidad loadEntidad(Long idEntidad);

	/**
	 * Añade entidad por parte del superadministrador.
	 *
	 * @param entidad
	 *            Entidad con los datos requeridos por superadministrador.
	 */
	public void addEntidad(Entidad entidad);

	/**
	 * Actualización entidad por parte del superadministrador.
	 *
	 * @param entidad
	 *            Entidad con los datos requeridos por superadministrador.
	 */
	public void updateEntidadSuperAdministrador(Entidad entidad);

	/**
	 * Actualización entidad por parte del administrador de entidad.
	 *
	 * @param entidad
	 *            Entidad con los datos requeridos por el administrador de entidad.
	 */
	public void updateEntidadAdministradorEntidad(final Entidad entidad);

	/**
	 * Elimina entidad.
	 *
	 * @param idEntidad
	 *            idEntidad
	 * @return true, si se ha realizado con &eacute;xito
	 */
	public boolean removeEntidad(Long idEntidad);

	/**
	 * Listar entidades.
	 *
	 * @param typeIdioma
	 *            Idioma
	 * @param filtro
	 *            filtro
	 * @return entidades
	 */
	public List<Entidad> listEntidad(TypeIdioma typeIdioma, String filtro);

	/**
	 * Elimina logo gestor entidad.
	 *
	 * @param idEntidad
	 *            idEntidad
	 */
	public void removeLogoGestorEntidad(Long idEntidad);

	/**
	 * Elimina logo asistente entidad.
	 *
	 * @param idEntidad
	 *            idEntidad
	 */
	public void removeLogoAsistenteEntidad(Long idEntidad);

	/**
	 * Elimina css asistente tramitacion entidad.
	 *
	 * @param idEntidad
	 *            idEntidad
	 */
	public void removeCssEntidad(Long idEntidad);

	/**
	 * Incorpora logo gestor entidad.
	 *
	 * @param idEntidad
	 *            idEntidad
	 * @param fichero
	 *            fichero
	 * @param content
	 *            contenido del fichero
	 */
	public void uploadLogoGestorEntidad(Long idEntidad, Fichero fichero, byte[] content);

	/**
	 * Incorpora logo asistente entidad.
	 *
	 * @param idEntidad
	 *            idEntidad
	 * @param fichero
	 *            fichero
	 * @param content
	 *            contenido del fichero
	 */
	public void uploadLogoAsistenteEntidad(Long idEntidad, Fichero fichero, byte[] content);

	/**
	 * Incorpora css entidad.
	 *
	 * @param idEntidad
	 *            idEntidad
	 * @param fichero
	 *            fichero
	 * @param content
	 *            contenido del fichero
	 */
	public void uploadCssEntidad(Long idEntidad, Fichero fichero, byte[] content);

	/**
	 * Lista de opciones de formulario soporte.
	 *
	 * @param idEntidad
	 *            el identicador de entidad.
	 * @return Lista de opciones de formulario soporte.
	 */
	public List<FormularioSoporte> listOpcionesFormularioSoporte(final Long idEntidad);

	/**
	 * Obtener opcion de formulario de soporte.
	 *
	 * @param id
	 *            identificador de la opcion de formulario de soporte.
	 * @return Opcion de formulario de soporte.
	 */
	public FormularioSoporte loadOpcionFormularioSoporte(Long id);

	/**
	 * Añade opcion de formulario de soporte.
	 *
	 * @param idEntidad
	 *            identificador de la entidad.
	 * @param fst
	 *            opcion de formulario de soporte.
	 */
	public void addOpcionFormularioSoporte(Long idEntidad, FormularioSoporte fst);

	/**
	 * Actualización de la opcion de formulario de soporte.
	 *
	 * @param fst
	 *            opcion de formulario de soporte.
	 */
	public void updateOpcionFormularioSoporte(FormularioSoporte fst);

	/**
	 * Elimina la opcion de formulario de soporte.
	 *
	 * @param id
	 *            identificador
	 * @return true, si se ha realizado con &eacute;xito
	 */
	public boolean removeOpcionFormularioSoporte(Long id);

}
