package es.caib.sistramit.core.service.component.integracion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RDominio;
import es.caib.sistrages.rest.api.interna.RVersionTramite;

/**
 * Implementación acceso SISTRAGES.
 *
 * @author Indra
 *
 */
@Component("sistragesComponent")
public final class SistragesComponentImpl implements SistragesComponent {

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private SistragesApiComponent sistragesApiComponent;

	@Override
	public RConfiguracionGlobal obtenerConfiguracionGlobal() {
		return sistragesApiComponent.obtenerConfiguracionGlobal();
	}

	@Override
	public void evictConfiguracionGlobal() {
		log.debug("evictConfiguracionGlobal");
	}

	@Override
	public RConfiguracionEntidad obtenerConfiguracionEntidad(final String idEntidad) {
		return sistragesApiComponent.obtenerConfiguracionEntidad(idEntidad);
	}

	@Override
	public void evictConfiguracionEntidad(final String idEntidad) {
		log.debug("evictConfiguracionEntidad: " + idEntidad);
	}

	@Override
	public RVersionTramite recuperarDefinicionTramite(final String idTramite, final int version, final String idioma) {
		return sistragesApiComponent.recuperarDefinicionTramite(idTramite, version, idioma);
	}

	@Override
	public RVersionTramite recuperarDefinicionTramiteNoCache(String idTramite, int version, String idioma) {
		return sistragesApiComponent.recuperarDefinicionTramite(idTramite, version, idioma);
	}

	@Override
	public void evictDefinicionTramite(final String idTramite, final int version, final String idioma) {
		log.debug("evictDefinicionTramite: " + idTramite + "-" + version + "-" + idioma);
	}

	@Override
	public RAvisosEntidad obtenerAvisosEntidad(final String idEntidad) {
		return sistragesApiComponent.obtenerAvisosEntidad(idEntidad);
	}

	@Override
	public void evictAvisosEntidad(final String idEntidad) {
		log.debug("evictAvisosEntidad: " + idEntidad);
	}

	@Override
	public RDominio recuperarDefinicionDominio(final String idDominio) {
		return sistragesApiComponent.recuperarDefinicionDominio(idDominio);
	}

	@Override
	public void evictDefinicionDominio(final String idDominio) {
		log.debug("evictDefinicionDominio: " + idDominio);
	}

}
