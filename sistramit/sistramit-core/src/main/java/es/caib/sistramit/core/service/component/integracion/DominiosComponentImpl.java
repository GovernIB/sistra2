package es.caib.sistramit.core.service.component.integracion;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistrages.rest.api.interna.RDominio;
import es.caib.sistramit.core.api.exception.DominioNoExisteException;
import es.caib.sistramit.core.api.exception.DominioSinDatosException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.model.integracion.ParametrosDominio;
import es.caib.sistramit.core.service.model.integracion.ValoresDominio;
import es.caib.sistramit.core.service.util.UtilsSTG;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Implementación dominios component.
 *
 * @author Indra
 *
 */
@Component("dominiosComponent")
public final class DominiosComponentImpl implements DominiosComponent {

    /** Log. */
    private final Logger log = LoggerFactory.getLogger(getClass());

    /** Nombre de la cache. **/
    private static final String CACHENAME = "cacheDominios";

    /** Separator. **/
    private static final String SEPARATOR = "#@@#";

    /** TIEMPO EN CACHE PARA DOMINIOS */
    public static final int TIEMPO_EN_CACHE = 60 * 60 * 24;

    /** Configuracion. */
    @Autowired
    private ConfiguracionComponent configuracionComponent;

    /** Configuracion. */
    @Autowired
    private DominiosResolucionComponent dominioResolucionComponent;

    /**
     * Se encarga de resolver el dominio
     *
     * @param idDominio
     * @param parametrosDominio
     * @param defTramite
     * @return
     */
    @Override
    public ValoresDominio recuperarDominio(final String idDominio,
            final ParametrosDominio parametrosDominio,
            final DefinicionTramiteSTG defTramite) {

        // Obtenemos informacion dominio
        String cacheKey = null;
        log.debug("Accediendo a dominio {}", idDominio);
        final RDominio dominio = UtilsSTG.devuelveDefinicionDominio(idDominio,
                defTramite);
        if (dominio == null) {
            throw new DominioNoExisteException("No existe dominio " + idDominio
                    + " en la definición del tramite");
        }

        // Comprobamos si el dominio es cacheable y tiene los datos cacheados
        if (dominio.isCachear()) {
        	String hashCode = "";
			if (parametrosDominio != null) {
				hashCode = parametrosDominio.hashCode() + "";
			}
			cacheKey = idDominio + SEPARATOR + hashCode;
			final ValoresDominio cached = (ValoresDominio) getFromCache(
                    cacheKey);
            if (cached != null) {
                cached.setFromCache(true);
                return cached;
            }
        }

        // Accedemos a dominio
        ValoresDominio valoresDominio = null;
        switch (dominio.getTipo()) {
        case RDominio.TIPO_LISTA_LISTA:
            valoresDominio = resuelveDominioLF(dominio);
            break;
        case RDominio.TIPO_CONSULTA_REMOTA:
            valoresDominio = resuelveDominioWS(dominio, parametrosDominio);
            break;
        case RDominio.TIPO_CONSULTA_BD:
            valoresDominio = resuelveDominioSQL(dominio, parametrosDominio);
            break;
        case RDominio.TIPO_FUENTE_DATOS:
            valoresDominio = resuelveDominioFD(dominio);
            break;
        default:
            throw new TipoNoControladoException(
                    "Tipo de dominio no soportado: " + dominio.getTipo());
        }

        // Controlamos que el dominio devuelve datos nulos
        if (valoresDominio == null) {
            throw new DominioSinDatosException("Dominio "
                    + dominio.getIdentificador() + " devuelve datos nulos");
        } else {
            valoresDominio.setFromCache(false);
        }

        // Comprobamos si debe cachearse (si es cacheable y no tiene error)
        if (dominio.isCachear() && !valoresDominio.isError()) {
            this.saveToCache(cacheKey, valoresDominio);
        }

        return valoresDominio;
    }

    /**
     * Resuelve dominio para Dominios de Fuente de Datos.
     *
     * @param dominio
     * @param parametrosDominio
     * @param url
     * @return
     */
    private ValoresDominio resuelveDominioFD(final RDominio dominio) {
        return dominioResolucionComponent.resuelveDominioFD(dominio);
    }

    /**
     * Resuelve dominio para Dominios de Lista Fija
     *
     * @param dominio
     * @param parametrosDominio
     * @param url
     * @return
     */
    private ValoresDominio resuelveDominioLF(final RDominio dominio) {
        return dominioResolucionComponent.resuelveDominioLF(dominio);
    }

    /**
     * Resuelve dominio para Dominios de SQL.
     *
     * @param dominio
     * @param parametrosDominio
     * @param url
     * @return
     */
    private ValoresDominio resuelveDominioSQL(final RDominio dominio,
            final ParametrosDominio parametrosDominio) {
        return dominioResolucionComponent.resuelveDominioSQL(dominio,
                parametrosDominio);
    }

    /**
     * Resuelve dominio para Dominio de Web Service.
     *
     * @param dominio
     * @param parametrosDominio
     * @return
     */
    private ValoresDominio resuelveDominioWS(final RDominio dominio,
            final ParametrosDominio parametrosDominio) {
        return dominioResolucionComponent.resuelveDominioWS(dominio,
                parametrosDominio);
    }

    /**
     * Obtiene el valor de la cache.
     *
     * @param key
     * @return
     * @throws CacheException
     */
    protected Serializable getFromCache(final Serializable key) {
        final Cache cache = getCache();
        final Element element = cache.get(key);
        if (element != null && !cache.isExpired(element)) {
            return element.getValue();
        } else {
            return null;
        }
    }

    /**
     * Obtiene la cache.
     *
     * @return
     * @throws CacheException
     */
    private static Cache getCache() {
        final CacheManager cacheManager = CacheManager.getInstance();
        Cache cache;
        if (cacheManager.cacheExists(CACHENAME)) {
            cache = cacheManager.getCache(CACHENAME);
        } else {
            throw new CacheException("Cache de dominio no esta definida");
        }
        return cache;
    }

    /**
     * Salva la cache.
     *
     * @param key
     * @param value
     * @throws CacheException
     */
    protected void saveToCache(final Serializable key,
            final Serializable value) {
        final Cache cache = getCache();
        cache.put(new Element(key, value));
    }

    /**
     * Limpiar cache.
     */
    public static void limpiarCache() {
        final CacheManager cacheManager = CacheManager.getInstance();
        Cache cache;
        if (cacheManager.cacheExists(CACHENAME)) {
            cache = cacheManager.getCache(CACHENAME);
            final List<String> keys = cache.getKeys();
            for (final Iterator<String> it = keys.iterator(); it.hasNext();) {
                final String key = it.next();
                final int idx = key.indexOf(SEPARATOR);
                if (idx != -1)
                    cache.remove(key);
            }
        }

    }

    @Override
    public void invalidarDominio(final String idDominio) {
        final CacheManager cacheManager = CacheManager.getInstance();
        Cache cache;
        if (cacheManager.cacheExists(CACHENAME)) {
            cache = cacheManager.getCache(CACHENAME);
            final List<String> keys = cache.getKeys();
            for (final String key : keys) {
                // La idea sería borrar sin importar los parametros dominio que
                // van detras del
                // separador.
                if (key.indexOf(idDominio + SEPARATOR) != -1) {
                    cache.remove(key);
                }
            }
        }
    }

}
