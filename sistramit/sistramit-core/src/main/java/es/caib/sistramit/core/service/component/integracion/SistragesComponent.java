package es.caib.sistramit.core.service.component.integracion;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RDominio;
import es.caib.sistrages.rest.api.interna.RVersionTramite;

/**
 * Acceso a componente SISTRAGES para obtener configuración realizando cacheo de
 * información.
 *
 * @author Indra
 *
 */
public interface SistragesComponent {

	/**
	 * Recupera configuración global.
	 */
	@Cacheable(value = "cacheConfiguracion", key = "'configuracionGlobal'")
	RConfiguracionGlobal obtenerConfiguracionGlobal();

	/**
	 * Quita de caché configuración global.
	 */
	@CacheEvict(value = "cacheConfiguracion", key = "'configuracionGlobal'")
	void evictConfiguracionGlobal();

	/**
	 * Recupera configuracion entidad.
	 *
	 * @param idEntidad
	 * @return configuracion entidad.
	 */
	@Cacheable(value = "cacheConfiguracion", key = "'configuracionEntidad-' + #idEntidad")
	RConfiguracionEntidad obtenerConfiguracionEntidad(String idEntidad);

	/**
	 * Quita de caché configuracion entidad.
	 *
	 * @param idEntidad
	 * @return configuracion entidad.
	 */
	@CacheEvict(value = "cacheConfiguracion", key = "'configuracionEntidad-' + #idEntidad")
	void evictConfiguracionEntidad(String idEntidad);

	/**
	 * Borra caché de configuracion entidad.
	 *
	 * @param idEntidad
	 */
	@CacheEvict(value = "cacheConfiguracion", allEntries = true)
	void evictConfiguracionEntidad();

	/**
	 * Recupera avisos entidad.
	 *
	 * @param idEntidad
	 * @return configuracion entidad.
	 */
	@Cacheable(value = "cacheConfiguracion", key = "'avisosEntidad-' + #idEntidad")
	RAvisosEntidad obtenerAvisosEntidad(String idEntidad);

	/**
	 * Quita de caché avisos entidad.
	 *
	 * @param idEntidad
	 */
	@CacheEvict(value = "cacheConfiguracion", key = "'avisosEntidad-' + #idEntidad")
	void evictAvisosEntidad(String idEntidad);

	/**
	 * Recupera definición versión de trámite.
	 *
	 * @param idTramite
	 *                      id trámite
	 * @param version
	 *                      versión
	 * @param idioma
	 *                      idioma
	 * @return Definición versión trámite
	 */
	@Cacheable(value = "cacheTramites", key = "#idTramite + '-' + #version + '-' + #idioma")
	RVersionTramite recuperarDefinicionTramite(String idTramite, int version, String idioma);

	/**
	 * Recupera definición versión de trámite (sin usar cache).
	 *
	 * @param idTramite
	 *                      id trámite
	 * @param version
	 *                      versión
	 * @param idioma
	 *                      idioma
	 * @return Definición versión trámite
	 */
	RVersionTramite recuperarDefinicionTramiteNoCache(String idTramite, int version, String idioma);

	/**
	 * Quita de cache definición versión de trámite.
	 *
	 * @param idTramite
	 *                      id trámite
	 * @param version
	 *                      versión
	 * @param idioma
	 *                      idioma
	 * @return
	 */
	@CacheEvict(value = "cacheTramites", key = "#idTramite + '-' + #version + '-' + #idioma")
	void evictDefinicionTramite(String idTramite, int version, String idioma);

	/**
	 * Borra cache de trámites.
	 */
	@CacheEvict(value = "cacheTramites", allEntries = true)
	void evictDefinicionTramite();

	/**
	 * Recupera definición dominio.
	 *
	 * @param idDominio
	 * @return Definición versión trámite
	 */
	@Cacheable(value = "cacheDominios", key = "#idDominio")
	RDominio recuperarDefinicionDominio(String idDominio);

	/**
	 * Quita de cache definición dominio.
	 *
	 * @param idDominio
	 */
	@CacheEvict(value = "cacheDominios", key = "#idDominio")
	void evictDefinicionDominio(String idDominio);

	/**
	 * Borra cache definición dominio.
	 */
	@CacheEvict(value = "cacheDominios", allEntries = true)
	void evictDefinicionDominio();
}
