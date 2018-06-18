package es.caib.sistramit.core.service.component.integracion;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import es.caib.sistrages.rest.api.ConfiguracionEntidad;
import es.caib.sistrages.rest.api.ConfiguracionGlobal;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;

/**
 * Acceso a componente SISTRAGES para obtener configuración realizando cacheo de
 * información.
 *
 * @author Indra
 *
 */
public interface SistragesComponent {

    // TODO Pendiente Obtener pluging global / Obtener definicion entidad
    // (¿Obtener plugin entidad?) / Obtener avisos
    //

    /**
     * Recupera configuración global.
     */
    @Cacheable(value = "cacheConfiguracion", key = "'configuracionGlobal'")
    ConfiguracionGlobal obtenerConfiguracionGlobal();

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
    ConfiguracionEntidad obtenerConfiguracionEntidad(String idEntidad);

    /**
     * Quita de caché configuracion entidad.
     *
     * @param idEntidad
     * @return configuracion entidad.
     */
    @CacheEvict(value = "cacheConfiguracion", key = "'configuracionEntidad-' + #idEntidad")
    void evictConfiguracionEntidad(String idEntidad);

    /**
     * Recupera definición versión de trámite.
     *
     * @param idTramite
     *            id trámite
     * @param version
     *            versión
     * @param idioma
     *            idioma
     * @return Definición versión trámite
     */
    @Cacheable(value = "cacheTramites", key = "#idTramite + '-' + #version + '-' + #idioma")
    DefinicionTramiteSTG recuperarDefinicionTramite(String idTramite,
            int version, String idioma);

    /**
     * Quita de cache definición versión de trámite.
     *
     * @param idTramite
     *            id trámite
     * @param version
     *            versión
     * @param idioma
     *            idioma
     * @return Definición versión trámite
     */
    @CacheEvict(value = "cacheTramites", key = "#idTramite + '-' + #version + '-' + #idioma")
    void evictDefinicionTramite(String idTramite, int version, String idioma);

}
